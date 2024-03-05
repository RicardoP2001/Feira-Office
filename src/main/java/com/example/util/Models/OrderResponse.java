package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse {
        @JsonProperty("OrderNumber")
        public String id;
        @JsonProperty("Date")
        public String date;
        @JsonProperty("Client")
        public List<Cliente> clientId;
        @JsonProperty("DeliveryAddress")
        public List<AddressResponse_Delivery> deliveryAddress;
        @JsonProperty("BillingAddress")
        public List<AddressResponse_Billing> billingAddress;
        @JsonProperty("NetAmount")
        public double netAmount;
        @JsonProperty("TaxAmount")
        public double taxAmount;
        @JsonProperty("TotalAmount")
        public double totalAmount;
        @JsonProperty("Currency")
        public String currency;
        @JsonProperty("OrderLines")
        public List<OrderLine> lines;
        @JsonProperty("Status")
        public int status;

        public OrderResponse(){
        }

    public OrderResponse(String id, String date, List<Cliente> clientId, List<AddressResponse_Delivery> deliveryAddress, List<AddressResponse_Billing> billingAddress, double netAmount, double taxAmount, double totalAmount, String currency, List<OrderLine> lines, int status) {
        this.id = id;
        this.date = date;
        this.clientId = clientId;
        this.deliveryAddress = deliveryAddress;
        this.billingAddress = billingAddress;
        this.netAmount = netAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.lines = lines;
        this.status = status;
    }

    public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    public List<Cliente> getClientId() {
        return clientId;
    }

    public void setClientId(List<Cliente> clientId) {
        this.clientId = clientId;
    }

    public List<AddressResponse_Delivery> getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(List<AddressResponse_Delivery> deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<AddressResponse_Billing> getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(List<AddressResponse_Billing> billingAddress) {
        this.billingAddress = billingAddress;
    }

    public double getNetAmount() {
            return netAmount;
        }

        public void setNetAmount(double netAmount) {
            this.netAmount = netAmount;
        }

        public double getTaxAmount() {
            return taxAmount;
        }

        public void setTaxAmount(double taxAmount) {
            this.taxAmount = taxAmount;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public List<OrderLine> getLines() {
            return lines;
        }

        public void setLines(List<OrderLine> lines) {
            this.lines = lines;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
}
