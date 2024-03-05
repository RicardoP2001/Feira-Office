package com.example.sheetsandpicks.Controllers.Pagamento;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.BL.Pagamentos.PagamentoFunctions;
import com.example.sheetsandpicks.Models.Sepa;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
/**
 * Controlador para a visualização da lista de transações SEPA (Single Euro Payments Area).
 * Implementa a interface {@link Initializable} e {@link PageCloseObserver}.
 */
public class Sepa_ListaController implements Initializable, PageCloseObserver {

    @FXML
    private TableColumn<Sepa, Integer> c_Quantidade_Stock;

    @FXML
    private TableColumn<Sepa, String> c_Metodo;

    @FXML
    private TableColumn<Sepa, Float> c_Total;

    @FXML
    private TableColumn<Sepa, LocalDate> c_data_criacao;

    @FXML
    private TableColumn<Sepa, String> c_id_pagamento;

    @FXML
    private Label lb_Funcionario;

    @FXML
    private TextField pesquisarSepa;

    @FXML
    private TableView<Sepa> tableView;

    private List<PageCloseObserver> observers = new ArrayList<>();

    private String cod;

    private String nome;
    /**
     * Método chamado quando uma tecla é pressionada na caixa de pesquisa.
     * @param event O evento de tecla pressionada.
     */
    @FXML
    void textboxPress(KeyEvent event) {

    }
    /**
     * Método chamado quando a página está prestes a ser fechada.
     * Notifica todos os observadores registrados.
     */
    @Override
    public void onPageClose() {
        observers.forEach(PageCloseObserver::onPageClose);
    }
    /**
     * Adiciona um observador para o fechamento da página.
     * @param observer O observador a ser adicionado.
     */
    public void addObserver(PageCloseObserver observer) {
        observers.add(observer);
    }
    /**
     * Preenche a tabela com informações sobre as transações SEPA.
     */
    private void EncherTabela(){
        List<Sepa> ListSepa = DBstatements.GetSepaInfo(cod);

        c_id_pagamento.setCellValueFactory(new PropertyValueFactory<>("id_pagamento"));
        c_data_criacao.setCellValueFactory(new PropertyValueFactory<>("Data"));
        c_Quantidade_Stock.setCellValueFactory(new PropertyValueFactory<>("qtd_Stock"));
        c_Total.setCellValueFactory(new PropertyValueFactory<>("Total"));
        c_Metodo.setCellValueFactory(new PropertyValueFactory<>("Metodo"));

        tableView.setItems(FXCollections.observableArrayList(ListSepa));
    }
    /**
     * Define as informações do funcionário.
     * @param cod Código do funcionário.
     * @param nome Nome do funcionário.
     */
    public void SetFuncionario(String cod,String nome){
        lb_Funcionario.setText(nome);
        this.cod=cod;
        this.nome=nome;
        initialize(null,null);
    }


    /**
     * Inicializa o controlador.
     * Registra um callback para atualização da tabela e configura a seleção de linhas na tabela.
     * @param location A URL de inicialização.
     * @param resources Os recursos a serem utilizados durante a inicialização.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PagamentoFunctions.setSepaUpdateCallback(this::EncherTabela);
        EncherTabela();
        selectRow();
    }
    /**
     * Configura a seleção de linhas na tabela.
     * Ao clicar duas vezes em uma transação SEPA, abre a visualização detalhada da transação.
     */
    private void selectRow(){
        tableView.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.PRIMARY && event.getClickCount() == 2){
                Sepa sepa = tableView.getSelectionModel().getSelectedItem();
                if(sepa!=null){
                    PagamentoFunctions.openAceitar_SepaController(sepa,cod,nome);
                }
            }
        });
    }


}
