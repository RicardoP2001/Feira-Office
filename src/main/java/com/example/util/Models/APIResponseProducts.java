package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Classe que representa a resposta da API para operações relacionadas a produtos.
 */
public class APIResponseProducts {
    @JsonProperty("Status")
    private String status;

    @JsonProperty("Products")
    private List<Products> products;

    /**
     * Obtém o status da resposta da API.
     *
     * @return O status da resposta da API.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define o status da resposta da API.
     *
     * @param status O novo valor para o status da resposta da API.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Obtém a lista de produtos associada à resposta da API.
     *
     * @return A lista de produtos.
     */
    public List<Products> getProducts() {
        return products;
    }

    /**
     * Define a lista de produtos associada à resposta da API.
     *
     * @param products A nova lista de produtos.
     */
    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
