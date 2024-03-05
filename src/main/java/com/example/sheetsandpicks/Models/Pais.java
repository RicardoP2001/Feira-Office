package com.example.sheetsandpicks.Models;
/**
 * Classe que representa informações sobre um país, incluindo seu nome, taxa de câmbio e tipo de moeda.
 */
public class Pais {
    /**
     * Nome do país.
     */
    private String Pais;

    /**
     * Taxa de câmbio associada ao país.
     */
    private float taxa;
    /**
     * Tipo de moeda utilizada no país.
     */
    private String Tipo_moeda;
    /**
     * Construtor para a classe Pais.
     *
     * @param pais       Nome do país.
     * @param taxa       Taxa de câmbio associada ao país.
     * @param tipo_moeda Tipo de moeda utilizada no país.
     */
    public Pais(String pais, float taxa, String tipo_moeda) {
        Pais = pais;
        this.taxa = taxa;
        Tipo_moeda = tipo_moeda;
    }
    /**
     * Obtém o nome do país.
     *
     * @return O nome do país.
     */
    public String getPais() {
        return Pais;
    }
    /**
     * Define o nome do país.
     *
     * @param pais Novo nome do país.
     */
    public void setPais(String pais) {
        Pais = pais;
    }
    /**
     * Obtém a taxa de câmbio associada ao país.
     *
     * @return A taxa de câmbio.
     */
    public float getTaxa() {
        return taxa;
    }

    /**
     * Define a taxa de câmbio associada ao país.
     *
     * @param taxa Nova taxa de câmbio.
     */
    public void setTaxa(float taxa) {
        this.taxa = taxa;
    }
    /**
     * Obtém o tipo de moeda utilizada no país.
     *
     * @return O tipo de moeda.
     */
    public String getTipo_moeda() {
        return Tipo_moeda;
    }
    /**
     * Define o tipo de moeda utilizada no país.
     *
     * @param tipo_moeda Novo tipo de moeda.
     */
    public void setTipo_moeda(String tipo_moeda) {
        Tipo_moeda = tipo_moeda;
    }
}
