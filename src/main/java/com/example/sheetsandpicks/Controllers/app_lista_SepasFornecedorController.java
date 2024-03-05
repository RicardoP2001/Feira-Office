package com.example.sheetsandpicks.Controllers;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.BL.Pagamentos.PagamentoFunctions;
import com.example.sheetsandpicks.Controllers.Users.CodSingleton;
import com.example.sheetsandpicks.Models.Sepa;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static com.example.sheetsandpicks.BL.Database.DBstatements.getnome;
/**
 * Controlador para a interface do utilizador relacionada à lista de ordens de pagamento SEPA de um fornecedor.
 * Permite visualizar e gerenciar as ordens de pagamento SEPA associadas a um fornecedor específico.
 */
public class app_lista_SepasFornecedorController implements Initializable {

    @FXML
    private Button btn_fornecedor_movimentos;

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
    private Label labelnome;

    @FXML
    private TextField pesquisaramovimentos;

    @FXML
    private TableView<Sepa> tableView;

    @FXML
    private Label valorbasetotal;

    @FXML
    private Label valortotal;

    private String cod_fornecedor;

    private int id;
    private String name;

    /**
     * Inicializa a interface do utilizador preenchendo a tabela de ordens de pagamento SEPA associadas ao fornecedor.
     * Configura a seleção de linha na tabela para abrir detalhes de uma ordem de pagamento SEPA ao clicar duas vezes.
     *
     * @param location  O localizador de recursos para a URL, não é utilizado neste método.
     * @param resources Os recursos para a ResourceBundle, não é utilizado neste método.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EncherTabela();
        selectRow();
    }
    /**
     * Configura o fornecedor associado ao controlador usando o código do fornecedor e o nome do fornecedor.
     *
     * @throws SQLException Se ocorrer um erro ao acessar o banco de dados para obter informações sobre o fornecedor.
     */
    public void setFornecedor() throws SQLException {
        this.cod_fornecedor = CodSingleton.getInstance().getCod();
        this.name = getnome(cod_fornecedor);
        initialize(null,null);
    }
    /**
     * Preenche a tabela de ordens de pagamento SEPA com dados obtidos da base de dados.
     *
     */
    private void EncherTabela(){
        List<Sepa> ListSepa = DBstatements.GetSepaInfo2(cod_fornecedor);

        c_id_pagamento.setCellValueFactory(new PropertyValueFactory<>("id_pagamento"));
        c_data_criacao.setCellValueFactory(new PropertyValueFactory<>("Data"));
        c_Quantidade_Stock.setCellValueFactory(new PropertyValueFactory<>("qtd_Stock"));
        c_Total.setCellValueFactory(new PropertyValueFactory<>("Total"));
        c_Metodo.setCellValueFactory(new PropertyValueFactory<>("Metodo"));

        tableView.setItems(FXCollections.observableArrayList(ListSepa));
    }
    /**
     * Manipula o evento de clique no botão "Movimentos do Fornecedor".
     * Carrega e exibe a interface do utilizador relacionada aos movimentos de stock de um fornecedor.
     *
     * @param event O evento de ação associado ao clique no botão "Movimentos do Fornecedor".
     * @throws SQLException Se ocorrer um erro ao acessar o banco de dados para obter informações sobre os movimentos de stock.
     * @throws IOException  Se ocorrer um erro durante o carregamento da interface do utilizador dos movimentos do fornecedor.
     */
    @FXML
    void Handler_fornecedor_movimentos(ActionEvent event) throws SQLException, IOException {
        PagamentoFunctions.Carregarfornecedor(cod_fornecedor,name,btn_fornecedor_movimentos);
    }
    /**
     * Manipula o evento de pressionar uma tecla na caixa de pesquisa.
     *
     * @param event O evento de tecla associado à pressão de uma tecla na caixa de pesquisa.
     */
    @FXML
    void textboxPress(KeyEvent event) {

    }


    /**
     * Configura a seleção de linha na tabela para abrir detalhes de uma ordem de pagamento SEPA ao clicar duas vezes.
     */
    private void selectRow(){
        tableView.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.PRIMARY && event.getClickCount() == 2){
                Sepa sepa = tableView.getSelectionModel().getSelectedItem();
                if(sepa!=null){
                    PagamentoFunctions.open_SepaController(sepa,cod_fornecedor,name);
                }
            }
        });
    }

}
