package com.example.util;

import com.example.util.Models.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
/**
 * Classe que gerencia as operações de comunicação com a API, como criação, leitura, atualização e exclusão (CRUD).
 */
public class Pedidos {
    /**
     * Envia uma solicitação HTTP para a API com base no método especificado (POST, GET, DELETE, LOGIN).
     *
     * @param dadosJson Os dados a serem enviados no formato JSON.
     * @param url       A URL da API.
     * @param Method    O método HTTP a ser usado (POST, GET, DELETE, LOGIN).
     * @return A resposta da solicitação HTTP.
     */
    public static HttpResponse<String> pedidos(String dadosJson, String url, String Method) {
        HttpClient httpClient = HttpClient.newHttpClient();

        String username = "EG2";
        String password = "SJ$pEgYO(Y";

        switch (Method) {
            case "POST" -> {
                if (dadosJson != null) {
                    try {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .header("Content-Type", "application/json")
                                .header("Accept", "application/json")
                                .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                                .POST(HttpRequest.BodyPublishers.ofString(dadosJson))
                                .build();
                        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    } catch (IOException | InterruptedException e) {
                        System.out.printf(e.getMessage());
                    }
                }
            }
            case "GET" -> {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                            .GET()
                            .build();

                    return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                }catch (IOException|InterruptedException e) {
                    System.out.println("Erro:"+e.getMessage());
                }
            }
            case "DELETE"->{
                try {
                    HttpRequest getRequest = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                            .build();
                    HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

                    if (getResponse.statusCode() != 200) {
                        System.out.println("Falha ao obter dados do cliente. Código de status: " + getResponse.statusCode());
                        return null;
                    }

                    HttpRequest putRequest = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Content-Type", "application/json")
                            .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                            .DELETE()
                            .build();
                    return httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());
                }catch (IOException|InterruptedException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
            case "LOGIN"->{
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                            .POST(HttpRequest.BodyPublishers.ofString(dadosJson))
                            .build();
                    return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                } catch (IOException | InterruptedException e) {
                    System.out.println("erro:"+e.getMessage());
                }
            }
        }
        return null;
    }
    /**
     * Envia uma solicitação HTTP para a API para realizar operações de atualização (PUT) em recursos específicos.
     *
     * @param tipo   O tipo de recurso a ser atualizado (Cliente, ORDER, Produtos).
     * @param opcao  A opção de atualização (true/false).
     * @param url    A URL do recurso específico.
     * @param status O status a ser atualizado (para ORDER).
     * @param id O id a ser utilizado (para ORDER).
     * @return A resposta da solicitação HTTP.
     */
    public static HttpResponse<String> pedidosAlterar(String tipo,boolean opcao,String url,int status,String id){
        HttpClient httpClient = HttpClient.newHttpClient();

        String dadosJson;

        String username = "EG2";
        String password = "SJ$pEgYO(Y";

        switch (tipo) {
            case "Cliente" -> {
                try {
                    HttpRequest getRequest = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                            .build();
                    HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

                    if (getResponse.statusCode() != 200) {
                        System.out.println("Falha ao obter dados da Encomenda. Código de status: " + getResponse.statusCode());
                        return null;
                    }

                    Cliente clienteAtual = new ObjectMapper().readValue(getResponse.body(), Cliente.class);

                    clienteAtual.setActive(opcao);

                    dadosJson = new ObjectMapper().writeValueAsString(clienteAtual);

                    HttpRequest putRequest = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Content-Type", "application/json")
                            .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                            .PUT(HttpRequest.BodyPublishers.ofString(dadosJson))
                            .build();
                    return httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());
                } catch (IOException | InterruptedException e) {
                    HandlerErrado("Erro: " + e.getMessage());
                    System.out.println("Erro: " + e.getMessage());
                    return null;
                }
            }
            case "ORDER"->{
                try{
                    HttpRequest getRequest = HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .header("Content-Type", "application/json")
                            .header("Accept", "application/json")
                            .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                            .build();
                    HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

                    if (getResponse.statusCode() != 200) {
                        System.out.println("Falha ao obter dados do cliente. Código de status: " + getResponse.statusCode());
                        return null;
                    }

                    APIResponseOrder orderatual = new ObjectMapper().readValue(getResponse.body(), APIResponseOrder.class);

                    for(OrderResponse order : orderatual.getOrder()){
                        if(order.getId().equals(id)){
                            order.setStatus(status);
                            dadosJson = new ObjectMapper().writeValueAsString(order);

                            HttpRequest putRequest = HttpRequest.newBuilder()
                                    .uri(URI.create(url))
                                    .header("Content-Type", "application/json")
                                    .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                                    .PUT(HttpRequest.BodyPublishers.ofString(dadosJson))
                                    .build();
                            return httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());

                        }
                    }
                } catch (IOException | InterruptedException e) {
                    HandlerErrado("Erro: " + e.getMessage());
                    System.out.println("Erro: " + e.getMessage());

                }
            }
            default -> {
                return null;
            }
        }
        return null;
    }
    public static HttpResponse<String> Alterarpedidos_product(String url,double Pvp,boolean opcao,double Stock,String Unit) {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            String dadosJson;

            String username = "EG2";
            String password = "SJ$pEgYO(Y";
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                    .build();
            HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());

            if (getResponse.statusCode() != 200) {
                System.out.println("Falha ao obter dados do Produto. Código de status: " + getResponse.statusCode());
                return null;
            }

            Products products = new ObjectMapper().readValue(getResponse.body(), Products.class);
            products.setActive(opcao);
            products.setPvp(Pvp);
            products.setStock(Stock);
            products.setUnit(Unit);
            dadosJson = new ObjectMapper().writeValueAsString(products);

            HttpRequest putRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Basic " + base64Encode(username + ":" + password))
                    .PUT(HttpRequest.BodyPublishers.ofString(dadosJson))
                    .build();
            return httpClient.send(putRequest, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException e) {
            HandlerErrado("Erro:"+e.getMessage());
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Codifica uma string para Base64.
     *
     * @param value A string a ser codificada.
     * @return A string codificada em Base64.
     */
    private static String base64Encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
    /**
     * Manipula a exibição de uma mensagem de erro por meio de um diálogo de alerta.
     *
     * @param mensagem A mensagem de erro a ser exibida.
     */
    public static void HandlerErrado(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    /**
     * Manipula a exibição de uma mensagem de confirmação por meio de um diálogo de alerta.
     */
    public static void HandlerConfirmado() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("SUCESSO");
        a.setContentText("A ação foi feita com sucesso");
        a.showAndWait();
    }
}
