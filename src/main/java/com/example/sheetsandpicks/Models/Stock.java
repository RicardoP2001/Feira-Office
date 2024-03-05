package com.example.sheetsandpicks.Models;

import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Classe que representa informações relacionadas a um item de stock.
 * Estende a classe abstrata Encomenda.
 */
public class Stock extends Encomenda {
    /**
     * Identificador único do item de stock.
     */
    private int id;
    /**
     * Nome do item de stock.
     */
    private String Nome;
    /**
     * Quantidade disponível do item de stock.
     */
    private int quantidade;
    /**
     * Nome do fornecedor associado ao item de stock.
     */

    private String Fornecedor;
    /**
     * Valor base do item de stock.
     */
    private  float v_base;
    /**
     * Unidade de medida do item de stock.
     */

    private String UOM;
    /**
     * Preço unitário do item de stock.
     */

    private float Preco_uni;
    /**
     * Moeda utilizada para o preço do item de stock.
     */
    private String moeda;
    /**
     * País de origem do item de stock.
     */
    private String pais;

    /**
     * Indicação se o item foi aprovado ou rejeitado.
     */
    private String aprov_rejeitado;
    /**
     * Valor da taxa associada ao item de stock.
     */
    private float V_taxa;
    /**
     * Resultado associado ao item de stock.
     */
    private float resultado;
    /**
     * Valor da taxa em euros associada ao item de stock.
     */
    private float taxaeuro;
    /**
     * Lista de objetos da classe Stock.
     */
    public static ArrayList<Stock> StockList = new ArrayList<>();
    /**
     * Código do fornecedor associado ao item de stock.
     */
    private String cod_fornecedor;
    /**
     * Identificador único global do item de stock.
     */
    private String GUID;
    /**
     * Construtor para a classe Stock.
     *
     * @param cod_fornecedor  Código do fornecedor associado ao item de stock.
     * @param n_Encomenda     Número da encomenda associada ao item de stock.
     * @param data            Data da encomenda.
     * @param id              Identificador único do item de stock.
     * @param nome            Nome do item de stock.
     * @param quantidade      Quantidade disponível do item de stock.
     * @param fornecedor      Nome do fornecedor associado ao item de stock.
     * @param v_base          Valor base do item de stock.
     * @param UOM             Unidade de medida do item de stock.
     * @param preco_uni       Preço unitário do item de stock.
     * @param moeda           Moeda utilizada para o preço do item de stock.
     * @param pais            País de origem do item de stock.
     * @param aprov_rejeitado Indicação se o item foi aprovado ou rejeitado.
     * @param V_taxa          Valor da taxa associada ao item de stock.
     * @param resultado       Resultado associado ao item de stock.
     * @param GUID            Identificador único global do item de stock.
     */
    public Stock(String cod_fornecedor, String n_Encomenda, LocalDate data, int id, String nome, int quantidade, String fornecedor, float v_base, String UOM, float preco_uni, String moeda, String pais,String aprov_rejeitado ,float V_taxa,float resultado,String GUID) {
        super(n_Encomenda, data);
        this.id = id;
        Nome = nome;
        this.quantidade = quantidade;
        Fornecedor = fornecedor;
        this.v_base = v_base;
        this.UOM = UOM;
        Preco_uni = preco_uni;
        this.moeda = moeda;
        this.pais = pais;
        this.aprov_rejeitado=aprov_rejeitado;
        this.V_taxa = V_taxa;
        this.cod_fornecedor = cod_fornecedor;
        this.resultado=resultado;
        this.GUID=GUID;
    }

    /**
     * Obtém o identificador único do item de stock.
     *
     * @return O identificador único do item de stock.
     */
    public int getId() {
        return id;
    }

    /**
     * Define o identificador único do item de stock.
     *
     * @param id O identificador único do item de stock.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtém o código do fornecedor associado ao item de stock.
     *
     * @return O código do fornecedor associado ao item de stock.
     */
    public String getCod_fornecedor() {
        return cod_fornecedor;
    }

    /**
     * Define o código do fornecedor associado ao item de stock.
     *
     * @param cod_fornecedor O código do fornecedor associado ao item de stock.
     */
    public void setCod_fornecedor(String cod_fornecedor) {
        this.cod_fornecedor = cod_fornecedor;
    }

    /**
     * Obtém o valor da taxa em euros associada ao item de stock.
     *
     * @return O valor da taxa em euros associada ao item de stock.
     */
    public float getTaxaeuro() {
        return taxaeuro;
    }

    /**
     * Define o valor da taxa em euros associada ao item de stock.
     *
     * @param taxaeuro O valor da taxa em euros associada ao item de stock.
     */
    public void setTaxaeuro(float taxaeuro) {
        this.taxaeuro = taxaeuro;
    }

    /**
     * Obtém o nome do item de stock.
     *
     * @return O nome do item de stock.
     */
    public String getNome() {
        return Nome;
    }

    /**
     * Define o nome do item de stock.
     *
     * @param nome O nome do item de stock.
     */
    public void setNome(String nome) {
        Nome = nome;
    }

    /**
     * Obtém a quantidade disponível do item de stock.
     *
     * @return A quantidade disponível do item de stock.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade disponível do item de stock.
     *
     * @param quantidade A quantidade disponível do item de stock.
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Obtém o preço unitário do item de stock.
     *
     * @return O preço unitário do item de stock.
     */
    public float getPreco_uni() {
        return Preco_uni;
    }

    /**
     * Define o preço unitário do item de stock.
     *
     * @param preco_uni O preço unitário do item de stock.
     */
    public void setPreco_uni(float preco_uni) {
        Preco_uni = preco_uni;
    }

    /**
     * Obtém o valor da taxa associada ao item de stock.
     *
     * @return O valor da taxa associada ao item de stock.
     */
    public float getV_taxa() {
        return V_taxa;
    }

    /**
     * Define o valor da taxa associada ao item de stock.
     *
     * @param V_taxa O valor da taxa associada ao item de stock.
     */
    public void setV_taxa(float V_taxa) {
        this.V_taxa = V_taxa;
    }

    /**
     * Obtém a moeda utilizada para o preço do item de stock.
     *
     * @return A moeda utilizada para o preço do item de stock.
     */
    public String getMoeda() {
        return moeda;
    }

    /**
     * Define a moeda utilizada para o preço do item de stock.
     *
     * @param moeda A moeda utilizada para o preço do item de stock.
     */
    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    /**
     * Obtém o nome do fornecedor associado ao item de stock.
     *
     * @return O nome do fornecedor associado ao item de stock.
     */
    public String getFornecedor() {
        return Fornecedor;
    }

    /**
     * Define o nome do fornecedor associado ao item de stock.
     *
     * @param fornecedor O nome do fornecedor associado ao item de stock.
     */
    public void setFornecedor(String fornecedor) {
        Fornecedor = fornecedor;
    }

    /**
     * Obtém a unidade de medida do item de stock.
     *
     * @return A unidade de medida do item de stock.
     */
    public String getUOM() {
        return UOM;
    }

    /**
     * Define a unidade de medida do item de stock.
     *
     * @param UOM A unidade de medida do item de stock.
     */
    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    /**
     * Obtém o valor base do item de stock.
     *
     * @return O valor base do item de stock.
     */
    public float getV_base() {
        return v_base;
    }

    /**
     * Define o valor base do item de stock.
     *
     * @param v_base O valor base do item de stock.
     */
    public void setV_base(float v_base) {
        this.v_base = v_base;
    }

    /**
     * Obtém o país de origem do item de stock.
     *
     * @return O país de origem do item de stock.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Define o país de origem do item de stock.
     *
     * @param pais O país de origem do item de stock.
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Obtém a indicação se o item foi aprovado ou rejeitado.
     *
     * @return A indicação se o item foi aprovado ou rejeitado.
     */
    public String getAprov_rejeitado() {
        return aprov_rejeitado;
    }

    /**
     * Define a indicação se o item foi aprovado ou rejeitado.
     *
     * @param aprov_rejeitado A indicação se o item foi aprovado ou rejeitado.
     */
    public void setAprov_rejeitado(String aprov_rejeitado) {
        this.aprov_rejeitado = aprov_rejeitado;
    }

    /**
     * Obtém o resultado associado ao item de stock.
     *
     * @return O resultado associado ao item de stock.
     */
    public float getResultado() {
        return resultado;
    }

    /**
     * Define o resultado associado ao item de stock.
     *
     * @param resultado O resultado associado ao item de stock.
     */
    public void setResultado(float resultado) {
        this.resultado = resultado;
    }

    /**
     * Obtém o identificador único global do item de stock.
     *
     * @return O identificador único global do item de stock.
     */
    public String getGUID() {
        return GUID;
    }

    /**
     * Define o identificador único global do item de stock.
     *
     * @param GUID O identificador único global do item de stock.
     */
    public void setGUID(String GUID) {
        this.GUID = GUID;
    }
}
