package com.example.sheetsandpicks.Models;

import java.util.ArrayList;
import java.util.Date;
/**
 * Classe que representa um operador no sistema.
 * Estende a classe abstrata Dados_pessoais.
 */
public class Operators extends Dados_pessoais {
    /**
     * Identificador da permissão associada ao operador.
     */
    private int id_permissao;

    /**
     * Estado de ativação do operador.
     */
    private String ativo;
    /**
     * Lista estática que armazena todos os operadores.
     */
    private static ArrayList<Operators> operatorsList = new ArrayList<>();
    /**
     * Construtor para a classe Operators.
     *
     * @param id              Identificador único do operador.
     * @param ativo           Estado de ativação do operador.
     * @param nome            Nome do operador.
     * @param email           Endereço de email do operador.
     * @param cod_postal      Código postal do operador.
     * @param endereco2       Segunda linha de endereço do operador.
     * @param cidade          Cidade do operador.
     * @param id_permissao    Identificador da permissão associada ao operador.
     * @param endereco        Endereço do operador.
     * @param data_nascimento Data de nascimento do operador.
     * @param telefone        Número de telefone do operador.
     * @param Pais            País do operador.
     * @param iban            Número IBAN do operador.
     * @param bic             Código BIC do operador.
     */
    public Operators(int id, String ativo,String nome, String email, String cod_postal, String endereco2,String cidade ,int id_permissao, String endereco, Date data_nascimento ,int telefone,String Pais, String iban, String bic) {
        super(id,nome,email,cod_postal,endereco,telefone,endereco2,cidade,data_nascimento,Pais, iban, bic);
        this.id_permissao=id_permissao;
        this.ativo = ativo;

        operatorsList.add(this);
    }
    /**
     * Obtém o identificador da permissão associada ao operador.
     *
     * @return O identificador da permissão.
     */
    public int getId_permissao() {
        return id_permissao;
    }
    /**
     * Define o identificador da permissão associada ao operador.
     *
     * @param id_permissao Novo identificador da permissão.
     */
    public void setId_permissao(int id_permissao) {
        this.id_permissao = id_permissao;
    }
    /**
     * Retorna uma representação em string do objeto Operators.
     *
     * @return Uma string representando o objeto.
     */
    @Override
    public String toString() {
        return "Operators{" +
                "id_permissao=" + id_permissao +
                ", ativo='" + ativo + '\'' +
                '}';
    }

    /**
     * Obtém a lista estática de todos os operadores.
     *
     * @return A lista de operadores.
     */
    public static ArrayList<Operators> getOperatorsList() {
        return operatorsList;
    }

}
