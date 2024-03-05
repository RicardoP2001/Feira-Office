package com.example.sheetsandpicks.Models;
/**
 * A classe {@code StockAprov_rejeita} representa uma entidade que armazena informações sobre a aprovação ou rejeição de itens de stock.
 */
public class StockAprov_rejeita {
    /**
     * Identificador único associado à instância da classe {@code StockAprov_rejeita}.
     */
    private int id;

    /**
     * Indicação se o item de stock foi aprovado ou rejeitado.
     */
    private String aprovacao_rejeita;

    /**
     * Construtor que inicializa uma nova instância da classe {@code StockAprov_rejeita} com um identificador único e a indicação de aprovação ou rejeição.
     *
     * @param id                 O identificador único associado à instância da classe.
     * @param aprovacao_rejeita A indicação se o item de stock foi aprovado ou rejeitado.
     */
    public StockAprov_rejeita(int id, String aprovacao_rejeita) {
        this.id = id;
        this.aprovacao_rejeita = aprovacao_rejeita;
    }

    /**
     * Obtém o identificador único associado à instância da classe {@code StockAprov_rejeita}.
     *
     * @return O identificador único associado à instância da classe.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador único associado à instância da classe {@code StockAprov_rejeita}.
     *
     * @param id O identificador único associado à instância da classe.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém a indicação se o item de stock foi aprovado ou rejeitado.
     *
     * @return A indicação se o item de stock foi aprovado ou rejeitado.
     */
    public String getAprovacao_rejeita() {
        return aprovacao_rejeita;
    }

    /**
     * Define a indicação se o item de stock foi aprovado ou rejeitado.
     *
     * @param aprovacao_rejeita A indicação se o item de stock foi aprovado ou rejeitado.
     */
    public void setAprovacao_rejeita(String aprovacao_rejeita) {
        this.aprovacao_rejeita = aprovacao_rejeita;
    }}
