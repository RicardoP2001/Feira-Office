package com.example.sheetsandpicks.Models;

import java.time.LocalDate;
/**
 * Classe abstrata que representa uma encomenda genérica.
 */
public abstract class  Encomenda {
    /**
     * Número único associado à encomenda.
     */
    private String N_Encomenda;
    /**
     * Data de criação da encomenda.
     */
    private LocalDate data;
    /**
     * Construtor para a classe Encomenda.
     *
     * @param n_Encomenda Número único associado à encomenda.
     * @param data        Data de criação da encomenda.
     */
    public Encomenda(String n_Encomenda, LocalDate data) {
        N_Encomenda = n_Encomenda;
        this.data = data;
    }
    /**
     * Obtém o número único associado à encomenda.
     *
     * @return O número único da encomenda.
     */
    public String getN_Encomenda() {
        return N_Encomenda;
    }
    /**
     * Define o número único associado à encomenda.
     *
     * @param n_Encomenda Novo número único da encomenda.
     */
    public void setN_Encomenda(String n_Encomenda) {
        N_Encomenda = n_Encomenda;
    }
    /**
     * Obtém a data de criação da encomenda.
     *
     * @return A data de criação da encomenda.
     */
    public LocalDate getData() {
        return data;
    }
    /**
     * Define a data de criação da encomenda.
     *
     * @param data Nova data de criação da encomenda.
     */
    public void setData(LocalDate data) {
        this.data = data;
    }
}
