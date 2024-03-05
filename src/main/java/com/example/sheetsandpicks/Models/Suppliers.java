package com.example.sheetsandpicks.Models;

import java.util.ArrayList;
import java.util.Date;
/**
 * A classe {@code Suppliers} representa uma entidade que armazena informações sobre fornecedores.
 */
public class Suppliers extends Dados_pessoais {

    /**
     * Indica se o fornecedor está ativo.
     */
    private String ativo;

    /**
     * Identificador de permissão associado ao fornecedor.
     */
    private Integer id_permissao;

    /**
     * Código único associado ao fornecedor.
     */
    private String cod_fornecedor;
    private static ArrayList<Suppliers> suppliersList = new ArrayList<>();

    /**
     * Construtor que inicializa uma nova instância da classe {@code Suppliers} com informações do fornecedor.
     *
     * @param id             O identificador único associado ao fornecedor.
     * @param ativo          Indica se o fornecedor está ativo.
     * @param nome           O nome do fornecedor.
     * @param email          O endereço de e-mail do fornecedor.
     * @param cod_postal     O código postal do fornecedor.
     * @param endereco2      O segundo endereço do fornecedor.
     * @param cidade         A cidade do fornecedor.
     * @param id_permissao   O identificador de permissão associado ao fornecedor.
     * @param endereco       O endereço do fornecedor.
     * @param data_nascimento A data de nascimento do fornecedor.
     * @param telefone       O número de telefone do fornecedor.
     * @param cod_fornecedor O código único associado ao fornecedor.
     * @param Pais           O país do fornecedor.
     * @param iban           O número IBAN do fornecedor.
     * @param bic            O número BIC do fornecedor.
     */
    public Suppliers(int id, String ativo,String nome, String email, String cod_postal, String endereco2,String cidade ,int id_permissao, String endereco, Date data_nascimento ,int telefone,String cod_fornecedor,String Pais, String iban, String bic) {
        super(id,nome,email,cod_postal,endereco,telefone,endereco2,cidade,data_nascimento,Pais,iban,bic);
        this.ativo = ativo;
        this.id_permissao =id_permissao;
        this.cod_fornecedor=cod_fornecedor;
        suppliersList.add(this);
    }


    /**
     * Obtém o status de ativação do fornecedor.
     *
     * @return Uma string indicando se o fornecedor está ativo.
     */
    public String getAtivo() {
        return ativo;
    }

    /**
     * Define o status de ativação do fornecedor.
     *
     * @param ativo Uma string indicando se o fornecedor está ativo.
     */
    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    /**
     * Obtém o identificador de permissão associado ao fornecedor.
     *
     * @return O identificador de permissão do fornecedor.
     */
    public Integer getId_permissao() {
        return id_permissao;
    }

    /**
     * Define o identificador de permissão associado ao fornecedor.
     *
     * @param id_permissao O identificador de permissão do fornecedor.
     */
    public void setId_permissao(Integer id_permissao) {
        this.id_permissao = id_permissao;
    }

    /**
     * Obtém o código único associado ao fornecedor.
     *
     * @return O código único do fornecedor.
     */
    public String getCod_fornecedor() {
        return cod_fornecedor;
    }

    /**
     * Define o código único associado ao fornecedor.
     *
     * @param cod_fornecedor O código único do fornecedor.
     */
    public void setCod_fornecedor(String cod_fornecedor) {
        this.cod_fornecedor = cod_fornecedor;
    }

    /**
     * Obtém a lista de fornecedores.
     *
     * @return A lista de fornecedores.
     */
    public static ArrayList<Suppliers> getSuppliersList() {
        return suppliersList;
    }

    /**
     * Representação em string da classe {@code Suppliers}, exibindo os detalhes do fornecedor.
     *
     * @return Uma string que representa a instância atual da classe.
     */
    @Override
    public String toString() {
        return "Suppliers{" +
                "ativo='" + ativo + '\'' +
                ", id_permissao=" + id_permissao +
                ", cod_fornecedor='" + cod_fornecedor + '\'' +
                '}';
    }
}
