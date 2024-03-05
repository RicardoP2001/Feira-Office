package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Classe que representa a resposta da API para operações relacionadas a clientes.
 */
public class ApiResponseCliente {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Clients")
    private List<Cliente> clients;

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
     * Obtém a lista de clientes da resposta da API.
     *
     * @return A lista de clientes da resposta da API.
     */
    public List<Cliente> getClients() {
        return clients;
    }

    /**
     * Define a lista de clientes da resposta da API.
     *
     * @param clients A nova lista de clientes da resposta da API.
     */
    public void setClients(List<Cliente> clients) {
        this.clients = clients;
    }
}
