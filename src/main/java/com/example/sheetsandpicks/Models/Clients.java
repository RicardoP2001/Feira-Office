package com.example.sheetsandpicks.Models;

import java.sql.Date;
import java.util.ArrayList;
/**
 * Representa um objeto Cliente que herda informações pessoais básicas.
 * Mantém informações específicas para clientes, como o status de ativação.
 */
public class Clients extends Dados_pessoais {
    /**
     * Status de ativação do cliente, indicando se está ativo ou inativo.
     */
    private String ativo;
    /**
     * Lista estática que mantém todas as instâncias de objetos Clients criadas.
     */
    private static ArrayList<Clients> clientsList = new ArrayList<>();
    /**
     * Construtor para criar um objeto Cliente com informações específicas.
     *
     * @param id             O identificador único do cliente.
     * @param ativo          O status de ativação do cliente.
     * @param nome           O nome do cliente.
     * @param endereco       O endereço do cliente.
     * @param endereco2      Informações adicionais sobre o endereço do cliente.
     * @param cidade         A cidade do cliente.
     * @param telefone       O número de telefone do cliente.
     * @param email          O endereço de e-mail do cliente.
     * @param cod_postal     O código postal do cliente.
     * @param data_nascimento A data de nascimento do cliente.
     * @param Pais           O país do cliente.
     * @param iban           O número de identificação bancária internacional do cliente.
     * @param bic            O código bancário internacional do cliente.
     */
    public Clients(int id, String ativo, String nome, String endereco, String endereco2, String cidade, int telefone, String email, String cod_postal, Date data_nascimento,String Pais, String iban, String bic) {
        super(id,nome,email,cod_postal,endereco,telefone,endereco2,cidade, data_nascimento,Pais, iban, bic);
        this.ativo = ativo;
    }
    /**
     * Obtém o status de ativação do cliente.
     *
     * @return O status de ativação (ativo ou inativo).
     */
    public String getAtivo() {
        return ativo;
    }

    /**
     * Obtém a lista de todos os clientes.
     *
     * @return A lista de clientes.
     */
    public static ArrayList<Clients> getClientsList() {
        return clientsList;
    }
    /**
     * Adiciona um novo cliente à lista de clientes.
     *
     * @param client O cliente a ser adicionado.
     */
    public static void addClient(Clients client) {
        clientsList.add(client);
    }
    /**
     * Remove um cliente da lista de clientes.
     *
     * @param client O cliente a ser removido.
     */
    public static void removeClient(Clients client) {
        clientsList.remove(client);
    }
}
