package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe abstrata que representa um endereço genérico.
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address
{
    @JsonProperty("Address1")
    public String address1;
    @JsonProperty("Address2")
    public String address2;
    @JsonProperty("PostalCode")
    public String postalCode;
    @JsonProperty("City")
    public String city;
    @JsonProperty("Country")
    public String country;

    public Address() {
    }

    public Address(String address1, String address2, String postalCode, String city, String country) {
        this.address1 = address1;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    /**
     * Obtém o primeiro campo de endereço.
     *
     * @return O primeiro campo de endereço.
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * Define o primeiro campo de endereço.
     *
     * @param address1 O novo valor do primeiro campo de endereço.
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * Obtém o segundo campo de endereço.
     *
     * @return O segundo campo de endereço.
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * Define o segundo campo de endereço.
     *
     * @param address2 O novo valor do segundo campo de endereço.
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * Obtém o código postal.
     *
     * @return O código postal.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Define o código postal.
     *
     * @param postalCode O novo valor do código postal.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Obtém a cidade.
     *
     * @return A cidade.
     */
    public String getCity() {
        return city;
    }

    /**
     * Define a cidade.
     *
     * @param city A nova cidade.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Obtém o país.
     *
     * @return O país.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Define o país.
     *
     * @param country O novo valor do país.
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
