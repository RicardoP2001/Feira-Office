package com.example.sheetsandpicks.Models;

import java.util.Date;
/**
 * Classe abstrata que representa dados pessoais genéricos.
 * Contém atributos comuns a diferentes tipos de entidades que possuem dados pessoais.
 */
public abstract class Dados_pessoais {
    /**
     * Identificador único associado aos dados pessoais.
     */
    private int id;
    /**
     * Nome associado aos dados pessoais.
     */
    private String nome;
    /**
     * Endereço de e-mail associado aos dados pessoais.
     */
    private String email;
    /**
     * Código postal associado aos dados pessoais.
     */
    private String cod_postal;
    /**
     * Endereço associado aos dados pessoais.
     */
    private String endereco;
    /**
     * Número de telefone associado aos dados pessoais.
     */
    private Integer telefone;
    /**
     * Informações adicionais sobre o endereço associado aos dados pessoais.
     */
    private String endereco2;
    /**
     * Cidade associada aos dados pessoais.
     */
    private String cidade;
    /**
     * Data de nascimento associada aos dados pessoais.
     */
    private Date data_nascimento;
    /**
     * País associado aos dados pessoais.
     */
    private String Pais;
    /**
     * Número de identificação bancária internacional (IBAN) associado aos dados pessoais.
     */
    private String iban;
    /**
     * Código bancário internacional (BIC) associado aos dados pessoais.
     */
    private String bic;
    /**
     * Construtor para criar um objeto Dados_pessoais com informações específicas.
     *
     * @param id              O identificador único associado aos dados pessoais.
     * @param nome            O nome associado aos dados pessoais.
     * @param email           O endereço de e-mail associado aos dados pessoais.
     * @param cod_postal      O código postal associado aos dados pessoais.
     * @param endereco        O endereço associado aos dados pessoais.
     * @param telefone        O número de telefone associado aos dados pessoais.
     * @param endereco2       Informações adicionais sobre o endereço associado aos dados pessoais.
     * @param cidade          A cidade associada aos dados pessoais.
     * @param data_nascimento A data de nascimento associada aos dados pessoais.
     * @param pais            O país associado aos dados pessoais.
     * @param iban            O número de identificação bancária internacional (IBAN) associado aos dados pessoais.
     * @param bic             O código bancário internacional (BIC) associado aos dados pessoais.
     */

    public Dados_pessoais(int id, String nome, String email, String cod_postal, String endereco, Integer telefone, String endereco2, String cidade, Date data_nascimento, String pais, String iban, String bic) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cod_postal = cod_postal;
        this.endereco = endereco;
        this.telefone = telefone;
        this.endereco2 = endereco2;
        this.cidade = cidade;
        this.data_nascimento = data_nascimento;
        Pais = pais;
        this.iban = iban;
        this.bic = bic;
    }
    /**
     * Obtém o identificador único associado aos dados pessoais.
     *
     * @return O identificador único.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador único associado aos dados pessoais.
     *
     * @param id O novo identificador único.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém o nome associado aos dados pessoais.
     *
     * @return O nome.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Define o nome associado aos dados pessoais.
     *
     * @param nome O novo nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Obtém o endereço de e-mail associado aos dados pessoais.
     *
     * @return O endereço de e-mail.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Define o endereço de e-mail associado aos dados pessoais.
     *
     * @param email O novo endereço de e-mail.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Obtém o código postal associado aos dados pessoais.
     *
     * @return O código postal.
     */
    public String getCod_postal() {
        return cod_postal;
    }
    /**
     * Define o código postal associado aos dados pessoais.
     *
     * @param cod_postal O novo código postal.
     */
    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }
    /**
     * Obtém o endereço associado aos dados pessoais.
     *
     * @return O endereço.
     */
    public String getEndereco() {
        return endereco;
    }
    /**
     * Define o endereço associado aos dados pessoais.
     *
     * @param endereco O novo endereço.
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    /**
     * Obtém o número de telefone associado aos dados pessoais.
     *
     * @return O número de telefone.
     */
    public Integer getTelefone() {
        return telefone;
    }
    /**
     * Define o número de telefone associado aos dados pessoais.
     *
     * @param telefone O novo número de telefone.
     */
    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }
    /**
     * Obtém informações adicionais sobre o endereço associado aos dados pessoais.
     *
     * @return Informações adicionais sobre o endereço.
     */
    public String getEndereco2() {
        return endereco2;
    }

    /**
     * Define informações adicionais sobre o endereço associado aos dados pessoais.
     *
     * @param endereco2 Novas informações adicionais sobre o endereço.
     */
    public void setEndereco2(String endereco2) {
        this.endereco2 = endereco2;
    }
    /**
     * Obtém a cidade associada aos dados pessoais.
     *
     * @return A cidade.
     */
    public String getCidade() {
        return cidade;
    }
    /**
     * Define a cidade associada aos dados pessoais.
     *
     * @param cidade A nova cidade.
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    /**
     * Obtém a data de nascimento associada aos dados pessoais.
     *
     * @return A data de nascimento.
     */
    public Date getData_nascimento() {
        return data_nascimento;
    }
    /**
     * Define a data de nascimento associada aos dados pessoais.
     *
     * @param data_nascimento A nova data de nascimento.
     */
    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    /**
     * Obtém o país associado aos dados pessoais.
     *
     * @return O país.
     */
    public String getPais() {
        return Pais;
    }
    /**
     * Define o país associado aos dados pessoais.
     *
     * @param pais O novo país.
     */
    public void setPais(String pais) {
        Pais = pais;
    }
    /**
     * Obtém o número de identificação bancária internacional (IBAN) associado aos dados pessoais.
     *
     * @return O número de identificação bancária internacional (IBAN).
     */
    public String getIban() {
        return iban;
    }
    /**
     * Define o número de identificação bancária internacional (IBAN) associado aos dados pessoais.
     *
     * @param iban O novo número de identificação bancária internacional (IBAN).
     */
    public void setIban(String iban) {
        this.iban = iban;
    }
    /**
     * Obtém o código bancário internacional (BIC) associado aos dados pessoais.
     *
     * @return O código bancário internacional (BIC).
     */
    public String getBic() {
        return bic;
    }
    /**
     * Define o código bancário internacional (BIC) associado aos dados pessoais.
     *
     * @param bic O novo código bancário internacional (BIC).
     */
    public void setBic(String bic) {
        this.bic = bic;
    }
}
