package com.example.sheetsandpicks.Models;

import java.util.ArrayList;
import java.util.Date;
/**
 * Representa um objeto Admin que herda informações pessoais básicas.
 * Mantém informações específicas para administradores, como a permissão e o status de ativação.
 */
public class Admins extends Dados_pessoais{
    /**
     * Identificador único da permissão atribuída ao administrador.
     */
    private int id_permissao;
    /**
     * Status de ativação do administrador, indicando se está ativo ou inativo.
     */
    private String ativo;

    /**
     * Lista estática que mantém todas as instâncias de objetos Admins criadas.
     */
    private static ArrayList<Admins> adminList = new ArrayList<>();
    /**
     * Construtor para criar um objeto Admin com informações específicas.
     *
     * @param id             O identificador único do administrador.
     * @param ativo          O status de ativação do administrador.
     * @param nome           O nome do administrador.
     * @param email          O endereço de e-mail do administrador.
     * @param cod_postal     O código postal do administrador.
     * @param endereco2      Informações adicionais sobre o endereço do administrador.
     * @param cidade         A cidade do administrador.
     * @param id_permissao   O identificador único da permissão atribuída ao administrador.
     * @param endereco       O endereço do administrador.
     * @param data_nascimento A data de nascimento do administrador.
     * @param telefone       O número de telefone do administrador.
     * @param Pais           O país do administrador.
     * @param iban           O número de identificação bancária internacional do administrador.
     * @param bic            O código bancário internacional do administrador.
     */
    public Admins(int id, String ativo,String nome, String email, String cod_postal, String endereco2,String cidade ,int id_permissao, String endereco, Date data_nascimento ,int telefone,String Pais, String iban, String bic) {
        super(id,nome,email,cod_postal,endereco,telefone,endereco2,cidade,data_nascimento,Pais, iban, bic);
        this.ativo = ativo;
        this.id_permissao=id_permissao;
        adminList.add(this);
    }
    /**
     * Obtém o identificador único da permissão do administrador.
     *
     * @return O identificador único da permissão.
     */
    public int getId_permissao() {
        return id_permissao;
    }
    /**
     * Define o identificador único da permissão do administrador.
     *
     * @param id_permissao O identificador único da permissão.
     */
    public void setId_permissao(int id_permissao) {
        this.id_permissao = id_permissao;
    }
    /**
     * Obtém o status de ativação do administrador.
     *
     * @return O status de ativação (ativo ou inativo).
     */
    public String getAtivo() {
        return ativo;
    }
    /**
     * Define o status de ativação do administrador.
     *
     * @param ativo O status de ativação (ativo ou inativo).
     */
    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
}
