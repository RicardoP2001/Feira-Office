package com.example.util;

import com.example.Feira_and_Office.Controller.SingletoneCliente;
import com.example.util.Models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.util.Pedidos.*;
/**
 * Classe que realiza operações relacionadas a encomendas através de requisições HTTP.
 */
public class APIOrder {
    /**
     * Cria uma nova encomenda através de uma solicitação HTTP POST.
     *
     * @param dataFormatada A data da encomenda
     * @param delivery Os dados de Entrega da encomenda
     * @param billing Os dados de Pagamento da encomenda
     * @param orderLineList A encomenda a ser criada.
     * @return true se a operação foi bem-sucedida, false caso contrário.
     */
    public static boolean CriarEncomenda(String dataFormatada, Address delivery, Address billing, List<OrderLine> orderLineList){
        double netAmount=0,taxAmount = 0;
        for(OrderLine orderLine : orderLineList){
            netAmount+=orderLine.price;
            taxAmount += (orderLine.price*0.23);
        }
            Order order = new Order("string", dataFormatada, SingletoneCliente.getId(), delivery, billing, netAmount, taxAmount, netAmount+taxAmount, "EUR",orderLineList, 1);
        try {

            String url = "https://services.inapa.com/feiraoffice/api/order";
            String dadosJson = new ObjectMapper().writeValueAsString(order);
            HttpResponse<String> response = pedidos(dadosJson,url,"POST");
            assert response != null;
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                HandlerConfirmado();
                return true;
            } else {
                HandlerErrado("Cliente não foi criado com sucesso"+response.statusCode());
                return false;
            }
        } catch (Exception ex) {
            HandlerErrado("Erro de código!"+ex.getMessage());
            System.out.println(ex.getMessage());
            return false;
        }
    }
    /**
     * Busca todas as encomendas ou filtra por status através de uma solicitação HTTP GET.
     *
     * @param sitio O local da aplicação (Ver, Confirmar) para filtrar por status.
     * @return Uma lista de encomendas se a operação foi bem-sucedida, uma lista vazia caso contrário.
     */
    public static ArrayList<OrderResponse> BuscarEncomenda(String sitio) {
        ArrayList<OrderResponse> Orders;
        try {
            String url = "https://services.inapa.com/FeiraOffice/api/order";

            HttpResponse<String> response = pedidos(null, url, "GET");
            assert response != null;
            if (response.statusCode() == 200) {
                String responseData = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                APIResponseOrder apiResponseOrder = objectMapper.readValue(responseData, APIResponseOrder.class);


                if ("OK".equals(apiResponseOrder.getStatus())) {
                    if(sitio.equals("Ver")) {
                        Orders = apiResponseOrder.getOrder().stream().filter(order -> order.getStatus() == 2).collect(Collectors.toCollection(ArrayList::new));
                    }else if(sitio.equals("Confirmar")){
                        Orders = apiResponseOrder.getOrder().stream().filter(order -> order.getStatus() == 1).collect(Collectors.toCollection(ArrayList::new));
                    }else{
                        Orders=null;
                    }
                    return Orders;
                } else {
                    System.out.println("AQUI!Falha na operação. Status: " + apiResponseOrder.getStatus());
                    return new ArrayList<>();
                }
            } else {
                System.out.println("AQUI!!!Falha na operação. Código de status: " + response.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
            return new ArrayList<>();
        }
    }
    /**
     * Altera o status de uma encomenda através de uma solicitação HTTP PUT.
     *
     * @param id     O ID da encomenda a ser alterada.
     * @param status O novo status da encomenda.
     */
    public static void AlterarEncomenda(String id,int status){
        String url = "https://services.inapa.com/FeiraOffice/api/order/" + id;

        HttpResponse<String> response = pedidosAlterar("ORDER",false,url,status,id);

        assert response != null;
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            HandlerConfirmado();
        } else {
            HandlerErrado("Falha ao alterar dados da Encomenda. Código de status: " + response.statusCode());
        }
    }

    public static ArrayList<OrderResponse> BuscarEncomendaporCliente(String IdCliente){
        try {
            String url = "https://services.inapa.com/feiraoffice/api/order/client/" + IdCliente;

            HttpResponse<String> response = pedidos(null, url, "GET");
            assert response != null;
            if (response.statusCode() == 200) {
                String responseData = response.body();
                ArrayList<OrderResponse> Orders;
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                APIResponseOrder apiResponseOrder = objectMapper.readValue(responseData, APIResponseOrder.class);

                if("OK".equals(apiResponseOrder.getStatus())){
                    Orders=apiResponseOrder.getOrder().stream().filter(orderResponse -> orderResponse.getStatus()==2).collect(Collectors.toCollection(ArrayList::new));
                    return Orders;
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
