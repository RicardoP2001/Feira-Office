package com.example.util;

import com.example.util.Models.ApiResponseCliente;
import com.example.util.Models.Cliente;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Optional;

import static com.example.util.Pedidos.*;

/**
 * Classe que realiza operações relacionadas a clientes através de requisições HTTP.
 */

public class APICliente {
    /**
     * Cria um novo cliente através de uma solicitação HTTP POST.
     *
     * @param cliente O cliente a ser criado.
     * @return true se a operação foi bem-sucedida, false caso contrário.
     */
    public static boolean CriarCliente(Cliente cliente){
        try {
            String url = "https://services.inapa.com/FeiraOffice/api/client";
            String dadosJson = new ObjectMapper().writeValueAsString(cliente);
            HttpResponse<String> response = pedidos(dadosJson,url,"POST");
            assert response != null;
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }
    /**
     * Busca todos os clientes através de uma solicitação HTTP GET.
     *
     * @return Uma lista de clientes se a operação foi bem-sucedida, uma lista vazia caso contrário.
     */
    public static ArrayList<Cliente> BuscarCliente() {
        ArrayList<Cliente> clientes;
        try {
            String url = "https://services.inapa.com/FeiraOffice/api/client";

            HttpResponse<String> response = pedidos(null, url, "GET");
            assert response != null;
            if (response.statusCode() == 200) {
                String responseData = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ApiResponseCliente apiResponseCliente = objectMapper.readValue(responseData, ApiResponseCliente.class);

                if ("OK".equals(apiResponseCliente.getStatus())) {
                    clientes = (ArrayList<Cliente>) apiResponseCliente.getClients();
                    return clientes;
                } else {
                    System.out.println("Falha na operação. Status: " + apiResponseCliente.getStatus());
                    return new ArrayList<>();
                }
            } else {
                System.out.println("Falha na operação. Código de status: " + response.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception ex) {
            HandlerErrado("Erro: " + ex.getMessage());
            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }
    /**
     * Busca um cliente pelo ID através de uma solicitação HTTP GET.
     *
     * @param id O ID do cliente a ser buscado.
     * @return O nome do cliente se a operação foi bem-sucedida, null caso contrário.
     */
    public static String BuscarClienteporId(String id){
        try {
            String url = "https://services.inapa.com/FeiraOffice/api/client/"+id;

            HttpResponse<String> response = pedidos(null, url, "GET");
            assert response != null;
            if (response.statusCode() == 200) {
                String responseData = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                ApiResponseCliente apiResponseCliente = objectMapper.readValue(responseData, ApiResponseCliente.class);

                if ("OK".equals(apiResponseCliente.getStatus())) {
                    Cliente cliente = apiResponseCliente.getClients().get(0);
                    return cliente.getName();
                } else {
                    System.out.println("Falha na operação. Status: " + apiResponseCliente.getStatus());
                    return null;
                }
            } else {
                System.out.println("Falha na operação. Código de status: " + response.statusCode());
                return null;
            }

        } catch (Exception ex) {
            HandlerErrado("Erro: " + ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }
    }
    /**
     * Altera o status de ativação de um cliente através de uma solicitação HTTP PUT.
     *
     * @param id     O ID do cliente a ser alterado.
     * @param opcao  A opção de ativação/desativação do cliente.
     */
    public static void AlterarCliente(String id,boolean opcao){
        String url = "https://services.inapa.com/FeiraOffice/api/client/" + id;

        HttpResponse<String> response = pedidosAlterar("Cliente",opcao,url,0,id);

        assert response != null;
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            HandlerConfirmado();
        } else {
            HandlerErrado("Falha ao alterar dados do cliente. Código de status: " + response.statusCode());
        }
    }
    /**
     * Apaga um cliente através de uma solicitação HTTP DELETE, exibindo uma confirmação.
     *
     * @param id O ID do cliente a ser apagado.
     */
    public static void ApagarCliente(String id){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Tem a certeza?");
        a.setContentText("QUER REMOVER o FORNECEDOR?");
        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                String url = "https://services.inapa.com/FeiraOffice/api/client/" + id;
                HttpResponse<String> response = pedidos(null, url, "DELETE");

                assert response != null;
                if (response.statusCode() >= 200 && response.statusCode() < 300) {
                    HandlerConfirmado();
                } else {
                    HandlerErrado("Falha ao eliminar dados do cliente. Código de status: " + response.statusCode());
                }
            }
        }
    }
    /**
     * Realiza o login de um cliente através de uma solicitação HTTP LOGIN.
     *
     * @param email    O email do cliente.
     * @param password A senha do cliente.
     * @return true se o login foi bem-sucedido, false caso contrário.
     */
    public static Cliente LoginCliente(String email,String password){
        try{
            String url = "https://services.inapa.com/FeiraOffice/api/client/login";
            String dadosJson = "{\"Email\": \"" + email + "\", \"Password\": \"" + password + "\"}";
            HttpResponse<String> response = pedidos(dadosJson,url,"LOGIN");
            assert response != null;
            if(response.statusCode()>=200 && response.statusCode()<300){
                String responseBody = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(responseBody);

                if (jsonResponse.has("Client") && !jsonResponse.get("Client").isNull()) {
                    JsonNode clientsArray = jsonResponse.get("Client");

                    return objectMapper.readValue(clientsArray.get(0).toString(), Cliente.class);
                }else{
                    return null;
                }
            }else{
                HandlerErrado("Email e palavra-passe Errada");
                return null;
            }
        }catch(Exception e){
            HandlerErrado("Erro de código!" + e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }
}
