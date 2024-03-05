package com.example.sheetsandpicks.Models;

import java.util.Date;
/**
 * Classe que representa informações de pagamento associadas a um utilizador.
 * Estende a classe abstrata Dados_pessoais.
 */
public class pagamento extends Dados_pessoais{

    /**
     * Número IBAN associado ao pagamento.
     */
    String IBAN;
    /**
     * Código BIC associado ao pagamento.
     */
    String BIC;
    /**
     * Construtor para a classe Pagamento.
     *
     * @param id              Identificador único do pagamento.
     * @param nome            Nome do utilizador associado ao pagamento.
     * @param email           Endereço de email do utilizador associado ao pagamento.
     * @param cod_postal      Código postal do utilizador associado ao pagamento.
     * @param endereco        Endereço do utilizador associado ao pagamento.
     * @param telefone        Número de telefone do utilizador associado ao pagamento.
     * @param endereco2       Segunda linha de endereço do utilizador associado ao pagamento.
     * @param cidade          Cidade do utilizador associado ao pagamento.
     * @param data_nascimento Data de nascimento do utilizador associado ao pagamento.
     * @param Pais            País do utilizador associado ao pagamento.
     * @param IBAN            Número IBAN associado ao pagamento.
     * @param BIC             Código BIC associado ao pagamento.
     */
    public pagamento(int id, String nome, String email, String cod_postal, String endereco, Integer telefone, String endereco2, String cidade, Date data_nascimento, String Pais, String IBAN, String BIC) {
        super(id, nome, email, cod_postal, endereco, telefone, endereco2, cidade, data_nascimento, Pais, IBAN, BIC);
        this.IBAN = IBAN;
        this.BIC = BIC;
    }
    /**
     * Obtém o número IBAN associado ao pagamento.
     *
     * @return O número IBAN.
     */
    public String getIBAN() {
        return IBAN;
    }
    /**
     * Define o número IBAN associado ao pagamento.
     *
     * @param IBAN Novo número IBAN.
     */
    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }
    /**
     * Obtém o código BIC associado ao pagamento.
     *
     * @return O código BIC.
     */
    public String getBIC() {
        return BIC;
    }
    /**
     * Define o código BIC associado ao pagamento.
     *
     * @param BIC Novo código BIC.
     */
    public void setBIC(String BIC) {
        this.BIC = BIC;
    }
}
