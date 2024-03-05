package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe que representa uma linha de pedido (OrderLine).
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLine {

    @JsonProperty("LineNumber")
    public int lineNumber;
    @JsonProperty("ProductCode")
    public String productCode;
    @JsonProperty("Quantity")
    public double quantity;
    @JsonProperty("Unit")
    public String unit;
    @JsonProperty("Price")
    public double price;

    public OrderLine(){}

    public OrderLine(int lineNumber, String productCode, double quantity, String unit, double price) {
        this.lineNumber = lineNumber;
        this.productCode = productCode;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
    }

    /**
     * Obtém o número da linha do pedido.
     *
     * @return O número da linha do pedido.
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Define o número da linha do pedido.
     *
     * @param lineNumber O novo valor para o número da linha do pedido.
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Obtém o código do produto da linha do pedido.
     *
     * @return O código do produto da linha do pedido.
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * Define o código do produto da linha do pedido.
     *
     * @param productCode O novo valor para o código do produto da linha do pedido.
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Obtém a quantidade da linha do pedido.
     *
     * @return A quantidade da linha do pedido.
     */
     public double getQuantity() {
        return quantity;
    }

    /**
     * Define a quantidade da linha do pedido.
     *
     * @param quantity O novo valor para a quantidade da linha do pedido.
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Obtém a unidade da linha do pedido.
     *
     * @return A unidade da linha do pedido.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Define a unidade da linha do pedido.
     *
     * @param unit A nova unidade da linha do pedido.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Obtém o preço da linha do pedido.
     *
     * @return O preço da linha do pedido.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Define o preço da linha do pedido.
     *
     * @param price O novo valor para o preço da linha do pedido.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    
}
