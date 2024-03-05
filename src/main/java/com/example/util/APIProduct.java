package com.example.util;

import com.example.sheetsandpicks.Models.Stock;
import com.example.util.Models.APIResponseOnlyOneProducts;
import com.example.util.Models.APIResponseProducts;
import com.example.util.Models.Products;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.util.Pedidos.*;
/**
 * Classe que realiza operações relacionadas a produtos através de requisições HTTP.
 */
public class APIProduct {
    /**
     * Cria um novo produto através de uma solicitação HTTP POST.
     *
     * @param elements Os elementos necessários para criar o produto.
     * @return true se a operação foi bem-sucedida, false caso contrário.
     */
    public static boolean CriarProduto(Stock elements){
        try {
            String url = "https://services.inapa.com/feiraoffice/api/Product";
            BigDecimal pvpBigDecimal = BigDecimal.valueOf((elements.getV_taxa()+ elements.getV_base())*1.20);
            BigDecimal pvpArredondadoBigDecimal = pvpBigDecimal.setScale(2, RoundingMode.HALF_UP);
            double pvpArredondado = pvpArredondadoBigDecimal.doubleValue();
            Products products = new Products(UUID.fromString(elements.getGUID()), elements.getGUID(),elements.getNome(),pvpArredondado ,elements.getQuantidade(),elements.getMoeda(),true);
            String dadosJson = new ObjectMapper().writeValueAsString(products);
            System.out.println(dadosJson);
            HttpResponse<String> response = pedidos(dadosJson,url,"POST");
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return true;
            } else {
                HandlerErrado("Produto não foi criado com sucesso"+response.statusCode());
                return false;
            }
        } catch (Exception ex) {
            HandlerErrado("Erro de código!"+ex.getMessage());
            System.out.println(ex.getMessage());
            return false;
        }
    }
    /**
     * Busca todos os produtos através de uma solicitação HTTP GET.
     *
     * @return Uma lista de produtos se a operação foi bem-sucedida, uma lista vazia caso contrário.
     */
    public static ArrayList<Products> BuscarProdutos(){
        ArrayList<Products> products;
        try{
            String url = "https://services.inapa.com/feiraoffice/api/Product";
            HttpResponse<String> response = pedidos(null, url, "GET");
            System.out.println(response.statusCode());
            if (response.statusCode() == 200) {
                String responseData = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                APIResponseProducts apiResponseProducts = objectMapper.readValue(responseData, APIResponseProducts.class);

                if ("OK".equals(apiResponseProducts.getStatus())) {
                        products = (ArrayList<Products>) apiResponseProducts.getProducts();
                        if(products!=null) {
                            return products;
                        }
                } else {
                    System.out.println("Falha na operação. Status: " + apiResponseProducts.getStatus());
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
        return new ArrayList<>();
    }
    /**
     * Altera os dados de um produto através de uma solicitação HTTP PUT.
     *
     * @param id    O ID do produto a ser alterado.
     * @param opcao A opção para a alteração do produto.
     * @param PVP   O novo preço de venda ao público do produto.
     * @param v
     * @param value
     */
    public static void AlterarProdutos(String id, boolean opcao, double PVP, double v, String value){
        String url = "https://services.inapa.com/FeiraOffice/api/Product/" + id;

        HttpResponse<String> response = Alterarpedidos_product(url,PVP,opcao,v,value);

        assert response != null;
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            HandlerConfirmado();
        } else {
            HandlerErrado("Falha ao alterar dados do Produto. Código de status: " + response.statusCode());
        }
    }

    public static List<Products> BuscarProdutosparaFront() {
        ArrayList<Products> products;
        try{
            String url = "https://services.inapa.com/feiraoffice/api/Product";
            HttpResponse<String> response = pedidos(null, url, "GET");
            System.out.println(response.statusCode());
            if (response.statusCode() == 200) {
                String responseData = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                APIResponseProducts apiResponseProducts = objectMapper.readValue(responseData, APIResponseProducts.class);

                if ("OK".equals(apiResponseProducts.getStatus())) {
                    products = apiResponseProducts.getProducts().stream().filter(Products::isActive).collect(Collectors.toCollection(ArrayList::new));
                    return products;
                } else {
                    System.out.println("Falha na operação. Status: " + apiResponseProducts.getStatus());
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

    public static String BuscarProdutoporId(String id){
        try {
            String url = "https://services.inapa.com/FeiraOffice/api/Product/"+id;

            HttpResponse<String> response = pedidos(null, url, "GET");
            assert response != null;
            if (response.statusCode() == 200) {
                String responseData = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                APIResponseProducts apiResponseCliente = objectMapper.readValue(responseData, APIResponseProducts.class);

                if ("OK".equals(apiResponseCliente.getStatus())) {
                    List<Products> productsList = apiResponseCliente.getProducts();

                    if (!productsList.isEmpty()) {
                        Products products = productsList.get(0);
                        System.out.println(products.getDescription());
                        return products.getDescription();
                    }else{
                        System.out.println("No products found for ID: " + id);
                        return null;
                    }
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

    public static double BuscarStockProdutoId(String id) {
        try {
            String url = "https://services.inapa.com/FeiraOffice/api/Product/" + id;

            HttpResponse<String> response = pedidos(null, url, "GET");
            assert response != null;
            if (response.statusCode() == 200) {
                String responseData = response.body();

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                APIResponseOnlyOneProducts apiResponseProducts = objectMapper.readValue(responseData, APIResponseOnlyOneProducts.class);

                if ("OK".equals(apiResponseProducts.getStatus())) {
                    List<Products> productsList = apiResponseProducts.getProduct();

                    if (!productsList.isEmpty()) {
                        Products product = productsList.get(0);
                        System.out.println("Stock do produto com ID " + id + ": " + product.getStock());
                        return product.getStock();
                    } else {
                        System.out.println("Nenhum produto encontrado para o ID: " + id);
                    }
                } else {
                    System.out.println("Falha na operação. Status: " + apiResponseProducts.getStatus());
                }
            } else {
                System.out.println("Falha na operação. Código de status: " + response.statusCode());
            }
        } catch (IOException ex) {
            HandlerErrado("Erro de E/S: " + ex.getMessage());
            System.out.println("Erro de E/S: " + ex.getMessage());
        } catch (Exception ex) {
            HandlerErrado("Erro: " + ex.getMessage());
            System.out.println("Erro: " + ex.getMessage());
        }
        return 0;
    }


    public static void AlterarProdutosStock(String id,double Stock){
        System.out.println("Mas dentro do alterar"+id);
        String url = "https://services.inapa.com/FeiraOffice/api/Product/" + id;

        HttpResponse<String> response = Alterarpedidos_product(url,20,true,Stock,"EUR");
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            HandlerConfirmado();
        } else {
            HandlerErrado("Falha ao alterar dados do Produto. Código de status: " + response.statusCode());
        }
    }
}
