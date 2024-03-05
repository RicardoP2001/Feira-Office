package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Classe que representa um pedido (Order).
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order{
    @JsonProperty("Id")
    public String id;
    @JsonProperty("Date")
    public String date;
    @JsonProperty("ClientId")
    public String clientId;
    @JsonProperty("DeliveryAddress")
    public Address deliveryAddress;
    @JsonProperty("BillingAddress")
    public Address billingAddress;
    @JsonProperty("NetAmount")
    public double netAmount;
    @JsonProperty("TaxAmount")
    public double taxAmount;
    @JsonProperty("TotalAmount")
    public double totalAmount;
    @JsonProperty("Currency")
    public String currency;
    @JsonProperty("Lines")
    public List<OrderLine> lines;
    @JsonProperty("Status")
    public int status;


    public Order(){
    }

    public Order(String id, String date, String clientId, Address deliveryAddress,Address billingAddress, double netAmount, double taxAmount, double totalAmount, String currency, List<OrderLine> lines, int status) {
        this.id = id;
        this.date = date;
        this.clientId = clientId;
        this.deliveryAddress=deliveryAddress;
        this.billingAddress=billingAddress;
        this.netAmount = netAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.lines = lines;
        this.status = status;
    }

    /**
     * Obtém o ID do pedido.
     *
     * @return O ID do pedido.
     */
    public String getId() {
        return id;
    }

    /**
     * Define o ID do pedido.
     *
     * @param id O novo valor para o ID do pedido.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtém a data do pedido.
     *
     * @return A data do pedido.
     */
    public String getDate() {
        return date;
    }

    /**
     * Define a data do pedido.
     *
     * @param date A nova data do pedido.
     */
    public void setDate(String date) {
        this.date = date;
    }

    
    /**
     * Obtém o ID do cliente associado ao pedido.
     *
     * @return O ID do cliente.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Define o ID do cliente associado ao pedido.
     *
     * @param clientId O novo valor para o ID do cliente.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Obtém o endereço de entrega do pedido.
     *
     * @return O endereço de entrega do pedido.
     */
    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Define o endereço de entrega do pedido.
     *
     * @param deliveryAddress O novo valor para o endereço de entrega do pedido.
     */
    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Obtém o endereço de faturação do pedido.
     *
     * @return O endereço de faturação do pedido.
     */
    public Address getBillingAddress() {
        return billingAddress;
    }

    /**
     * Define o endereço de faturação do pedido.
     *
     * @param billingAddress O novo valor para o endereço de faturação do pedido.
     */
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * Obtém o valor líquido do pedido.
     *
     * @return O valor líquido do pedido.
     */
    public double getNetAmount() {
        return netAmount;
    }

    /**
     * Define o valor líquido do pedido.
     *
     * @param netAmount O novo valor para o valor líquido do pedido.
     */
    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    /**
     * Obtém o valor do imposto do pedido.
     *
     * @return O valor do imposto do pedido.
     */
     public double getTaxAmount() {
        return taxAmount;
    }

    /**
     * Define o valor do imposto do pedido.
     *
     * @param taxAmount O novo valor para o valor do imposto do pedido.
     */
    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**
     * Obtém o valor total do pedido.
     *
     * @return O valor total do pedido.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Define o valor total do pedido.
     *
     * @param totalAmount O novo valor para o valor total do pedido.
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Obtém a moeda do pedido.
     *
     * @return A moeda do pedido.
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Define a moeda do pedido.
     *
     * @param currency A nova moeda do pedido.
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Obtém a lista de linhas do pedido.
     *
     * @return A lista de linhas do pedido.
     */
    public List<OrderLine> getLines() {
        return lines;
    }

    /**
     * Define a lista de linhas do pedido.
     *
     * @param lines A nova lista de linhas do pedido.
     */
    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
    }

    /**
     * Obtém o status do pedido.
     *
     * @return O status do pedido.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Define o status do pedido.
     *
     * @param status O novo valor para o status do pedido.
     */
    public void setStatus(int status) {
        this.status = status;
    }
}
