package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Classe que representa a resposta da API para operações relacionadas a pedidos (orders).
 */
public class APIResponseOrder {
    @JsonProperty("Status")
    private String status;

    @JsonProperty("Orders")
    private List<OrderResponse> order;

    /**
     * Construtor padrão que inicializa a lista de pedidos.
     */
    public APIResponseOrder() {
    }

    /**
     * Construtor que permite a definição inicial do status e da lista de pedidos.
     *
     * @param status O status da resposta da API.
     * @param orders A lista de pedidos associada à resposta da API.
     */
    public APIResponseOrder(String status, List<OrderResponse> order) {
        this.status = status;
        this.order = order;
    }

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
     * Obtém a lista de pedidos associada à resposta da API.
     *
     * @return A lista de pedidos.
     */
   public List<OrderResponse> getOrder() {
        return order;
    }

    /**
     * Define a lista de pedidos associada à resposta da API.
     *
     * @param order A nova lista de pedidos.
     */
    public void setOrder(List<OrderResponse> order) {
        this.order = order;
    }
}
