package com.example.sheetsandpicks.Controllers.Pagamento;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.BL.Pagamentos.PagamentoFunctions;
import com.example.sheetsandpicks.Models.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
/**
 * Controlador para a janela de informações sobre transações SEPA no painel de controle.
 */
public class DashSepaInfoController implements Initializable {
    @FXML
    private TableColumn<Stock, LocalDate> c_Data;

    @FXML
    private TableColumn<Stock, String> c_Encomenda;

    @FXML
    private TableColumn<Stock, String> c_Pais;

    @FXML
    private TableColumn<Stock, String> c_Produto;

    @FXML
    private TableColumn<Stock, String> c_cod_Fornecedor;

    @FXML
    private TableColumn<Stock, String> c_moeda;

    @FXML
    private TableColumn<Stock, Float> c_preco_unit;

    @FXML
    private TableColumn<Stock, Float> c_v_produto;

    @FXML
    private Label lb_Total;

    @FXML
    private Label lb_data;

    @FXML
    private Label lb_fornecedor;

    @FXML
    private Label lb_mensagem;

    @FXML
    private Label lb_tipo_pagamento;

    @FXML
    private TableView<Stock> tableSepa;

    private String id_pagamento;

    private String cod;

    private String Metodo;

    @FXML
    private Button but_sair;

    /**
     * Preenche a tabela com os detalhes da transação SEPA.
     */
    private void Encher_tabela(){
        List<Stock> StockList = DBstatements.GetStockSepa(id_pagamento);

        c_cod_Fornecedor.setCellValueFactory(new PropertyValueFactory<>("cod_fornecedor"));
        c_Data.setCellValueFactory(new PropertyValueFactory<>("data"));
        c_Encomenda.setCellValueFactory(new PropertyValueFactory<>("n_Encomenda"));
        c_moeda.setCellValueFactory(new PropertyValueFactory<>("moeda"));
        c_Pais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        c_preco_unit.setCellValueFactory(new PropertyValueFactory<>("Preco_uni"));
        c_Produto.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        c_v_produto.setCellValueFactory(new PropertyValueFactory<>("resultado"));


        tableSepa.setItems(FXCollections.observableArrayList(StockList));
    }
    /**
     * Inicializa o controlador.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Encher_tabela();
        setValor_total();
    }
    /**
     * Define informações da transação SEPA e atualiza a interface gráfica.
     *
     * @param cod      Código do fornecedor.
     * @param nome     Nome do fornecedor.
     * @param id_pagamento   ID da transação de pagamento.
     * @param Metodo   Método de pagamento (DD ou outro).
     * @param Data     Data da transação.
     * @param mensagem Mensagem associada à transação.
     */
    public void setFuncionario(String cod,String nome,String id_pagamento,String Metodo,LocalDate Data,String mensagem){
        lb_fornecedor.setText(nome);
        this.id_pagamento=id_pagamento;
        this.cod=cod;
        this.Metodo=Metodo;
        lb_tipo_pagamento.setText(Metodo);
        lb_data.setText(String.valueOf(Data));
        lb_mensagem.setText(mensagem);
        initialize(null,null);
    }
    /**
     * Calcula e define o valor total da transação.
     */
    private void setValor_total(){
        ObservableList<Stock> lista= tableSepa.getItems();
        lb_Total.setText(String.valueOf(PagamentoFunctions.Listar_v_total(lista)));
    }

    /**
     * Fecha a janela atual.
     */

    private void closeWindow() {
        Stage stage = (Stage) but_sair.getScene().getWindow();
        stage.close();
    }
    /**
     * Manipula o evento de sair da janela.
     * Fecha a janela.
     *
     * @param event O evento de ação gerado pelo botão de sair.
     */
    @FXML
    void Handler_Sair(ActionEvent event) {
closeWindow();
    }

}
