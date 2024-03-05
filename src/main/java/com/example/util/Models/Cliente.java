package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe que representa um cliente.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cliente {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Address1")
    private String address1;

    @JsonProperty("Address2")
    private String address2;

    @JsonProperty("PostalCode")
    private String postalCode;

    @JsonProperty("City")
    private String city;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("TaxIdentificationNumber")
    private String taxIdentificationNumber;

    @JsonProperty("Active")
    private boolean active;

    @JsonProperty("Password")
    private String password;

    /**
     * Construtor padrão.
     */
    public Cliente() {
    }

    /**
     * Construtor para inicialização de todos os campos.
     *
     * @param id                      O ID do cliente.
     * @param name                    O nome do cliente.
     * @param email                   O email do cliente.
     * @param address1                O primeiro campo de endereço do cliente.
     * @param address2                O segundo campo de endereço do cliente.
     * @param postalCode              O código postal do cliente.
     * @param city                    A cidade do cliente.
     * @param country                 O país do cliente.
     * @param taxIdentificationNumber O número de identificação fiscal do cliente.
     * @param active                  Indica se o cliente está ativo.
     * @param password                A senha do cliente.
     */
    public Cliente(String id, String name, String email, String address1, String address2, String postalCode, String city, String country, String taxIdentificationNumber, boolean active, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.active = active;
        this.password = password;
    }

    /**
     * Obtém o ID do cliente.
     *
     * @return O ID do cliente.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o ID do cliente.
     *
     * @param id O novo valor para o ID do cliente.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtém o nome do cliente.
     *
     * @return O nome do cliente.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do cliente.
     *
     * @param name O novo valor para o nome do cliente.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtém o email do cliente.
     *
     * @return O email do cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do cliente.
     *
     * @param email O novo valor para o email do cliente.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém o primeiro campo de endereço do cliente.
     *
     * @return O primeiro campo de endereço do cliente.
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Define o primeiro campo de endereço do cliente.
     *
     * @param address1 O novo valor para o primeiro campo de endereço do cliente.
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Obtém o segundo campo de endereço do cliente.
     *
     * @return O segundo campo de endereço do cliente.
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Define o segundo campo de endereço do cliente.
     *
     * @param address2 O novo valor para o segundo campo de endereço do cliente.
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Obtém o código postal do cliente.
     *
     * @return O código postal do cliente.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Define o código postal do cliente.
     *
     * @param postalCode O novo valor para o código postal do cliente.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Obtém a cidade do cliente.
     *
     * @return A cidade do cliente.
     */
    public String getCity() {
        return city;
    }

    /**
     * Define a cidade do cliente.
     *
     * @param city A nova cidade do cliente.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Obtém o país do cliente.
     *
     * @return O país do cliente.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Define o país do cliente.
     *
     * @param country O novo valor para o país do cliente.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Obtém o número de identificação fiscal do cliente.
     *
     * @return O número de identificação fiscal do cliente.
     */
    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    /**
     * Define o número de identificação fiscal do cliente.
     *
     * @param taxIdentificationNumber O novo valor para o número de identificação fiscal do cliente.
     */
    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    /**
     * Verifica se o cliente está ativo.
     *
     * @return true se o cliente estiver ativo, false caso contrário.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Define se o cliente está ativo.
     *
     * @param active O novo valor para o status de ativação do cliente.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Obtém a senha do cliente.
     *
     * @return A senha do cliente.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha do cliente.
     *
     * @param password A nova senha do cliente.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
