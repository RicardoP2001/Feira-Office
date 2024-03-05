package com.example.util.Models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Classe que representa um produto (Products).
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Products {

    @JsonProperty("Id")
    private UUID id;

    @JsonProperty("Code")
    private String code;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("PVP")
    private double pvp;

    @JsonProperty("Stock")
    private double stock;

    @JsonProperty("Unit")
    private String unit;

    @JsonProperty("Active")
    private boolean active;

    /**
     * Construtor padrão.
     */
    public Products() {
    }

    /**
     * Construtor com parâmetros.
     *
     * @param id          O ID do produto.
     * @param code        O código do produto.
     * @param description A descrição do produto.
     * @param pvp         O preço de venda ao público do produto.
     * @param stock       O stock do produto.
     * @param unit        A unidade do produto.
     * @param active      O status de ativação do produto.
     */
    public Products(UUID id, String code, String description, double pvp, double stock, String unit, boolean active) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.pvp = pvp;
        this.stock = stock;
        this.unit = unit;
        this.active = active;
    }

    /**
     * Obtém o ID do produto.
     *
     * @return O ID do produto.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Define o ID do produto.
     *
     * @param id O novo valor para o ID do produto.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Obtém o código do produto.
     *
     * @return O código do produto.
     */
    public String getCode() {
        return code;
    }

    /**
     * Define o código do produto.
     *
     * @param code O novo valor para o código do produto.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Obtém a descrição do produto.
     *
     * @return A descrição do produto.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define a descrição do produto.
     *
     * @param description A nova descrição do produto.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtém o preço de venda ao público do produto.
     *
     * @return O preço de venda ao público do produto.
     */
    public double getPvp() {
        return pvp;
    }

    /**
     * Define o preço de venda ao público do produto.
     *
     * @param pvp O novo valor para o preço de venda ao público do produto.
     */
    public void setPvp(double pvp) {
        this.pvp = pvp;
    }

    /**
     * Obtém o stock do produto.
     *
     * @return O stock do produto.
     */
    public double getStock() {
        return stock;
    }

    /**
     * Define o stock do produto.
     *
     * @param stock O novo valor para o stock do produto.
     */
    public void setStock(double stock) {
        this.stock = stock;
    }

    /**
     * Obtém a unidade do produto.
     *
     * @return A unidade do produto.
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Define a unidade do produto.
     *
     * @param unit A nova unidade do produto.
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Verifica se o produto está ativo.
     *
     * @return true se o produto estiver ativo, false caso contrário.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Define o status de ativação do produto.
     *
     * @param active O novo valor para o status de ativação do produto.
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
