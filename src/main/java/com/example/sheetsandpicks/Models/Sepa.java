package com.example.sheetsandpicks.Models;

import java.time.LocalDate;
/**
 * Classe que representa informações relacionadas a uma transação SEPA (Single Euro Payments Area).
 */
public class Sepa {
    /**
     * Identificador da transação de pagamento.
     */
    private String id_pagamento;

    /**
     * Método de pagamento utilizado na transação SEPA.
     */
    private String Metodo;
    /**
     * Data da transação SEPA.
     */
    private LocalDate Data;
    /**
     * Quantidade de itens em stock associada à transação.
     */
    private int qtd_Stock;
    /**
     * Valor total da transação SEPA.
     */
    private float Total;
    /**
     * Mensagem associada à transação SEPA.
     */
    private String mensagem;
    /**
     * Construtor para a classe Sepa.
     *
     * @param id_pagamento Identificador da transação de pagamento.
     * @param metodo       Método de pagamento utilizado na transação SEPA.
     * @param data         Data da transação SEPA.
     * @param qtd_Stock    Quantidade de itens em stock associada à transação.
     * @param total        Valor total da transação SEPA.
     * @param mensagem     Mensagem associada à transação SEPA.
     */
    public Sepa(String id_pagamento, String metodo, LocalDate data, int qtd_Stock, float total, String mensagem) {
        this.id_pagamento = id_pagamento;
        Metodo = metodo;
        Data = data;
        this.qtd_Stock = qtd_Stock;
        Total = total;
        this.mensagem = mensagem;
    }
    /**
     * Obtém o identificador da transação de pagamento.
     *
     * @return O identificador da transação de pagamento.
     */
    public String getId_pagamento() {
        return id_pagamento;
    }

    /**
     * Define o identificador da transação de pagamento.
     *
     * @param id_pagamento Novo identificador da transação de pagamento.
     */
    public void setId_pagamento(String id_pagamento) {
        this.id_pagamento = id_pagamento;
    }
    /**
     * Obtém o método de pagamento utilizado na transação SEPA.
     *
     * @return O método de pagamento.
     */
    public String getMetodo() {
        return Metodo;
    }
    /**
     * Define o método de pagamento utilizado na transação SEPA.
     *
     * @param metodo Novo método de pagamento.
     */
    public void setMetodo(String metodo) {
        Metodo = metodo;
    }
    /**
     * Obtém a data da transação SEPA.
     *
     * @return A data da transação SEPA.
     */
    public LocalDate getData() {
        return Data;
    }
    /**
     * Define a data da transação SEPA.
     *
     * @param data Nova data da transação SEPA.
     */
    public void setData(LocalDate data) {
        Data = data;
    }
    /**
     * Obtém a quantidade de itens em stock associada à transação.
     *
     * @return A quantidade de itens em stock.
     */
    public int getQtd_Stock() {
        return qtd_Stock;
    }
    /**
     * Define a quantidade de itens em stock associada à transação.
     *
     * @param qtd_Stock Nova quantidade de itens em stock.
     */
    public void setQtd_Stock(int qtd_Stock) {
        this.qtd_Stock = qtd_Stock;
    }
    /**
     * Obtém o valor total da transação SEPA.
     *
     * @return O valor total da transação SEPA.
     */
    public float getTotal() {
        return Total;
    }
    /**
     * Define o valor total da transação SEPA.
     *
     * @param total Novo valor total da transação SEPA.
     */
    public void setTotal(float total) {
        Total = total;
    }

    /**
     * Obtém a mensagem associada à transação SEPA.
     *
     * @return A mensagem da transação SEPA.
     */
    public String getMensagem() {
        return mensagem;
    }
    /**
     * Define a mensagem associada à transação SEPA.
     *
     * @param mensagem Nova mensagem da transação SEPA.
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
