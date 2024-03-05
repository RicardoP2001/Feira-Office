package com.example.sheetsandpicks.Controllers.Stock;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.BL.util;
import com.example.sheetsandpicks.Models.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
/**
 * Controlador para a interface de visualização de stock.
 * Esta interface exibe o stock disponível na base de dados,
 * permitindo a confirmação de determinados itens.
 */
public class AppDashVizualizarStock implements Initializable {

    @FXML
    private TableColumn<Stock, String> c_Fornecedor;

    @FXML
    private TableColumn<Stock, Integer> c_Quantidade;

    @FXML
    private TableColumn<Stock, String> c_nome;

    @FXML
    private TableColumn<Stock, Float> c_p_uni;

    @FXML
    private TableColumn<Stock, Float> c_v_base;

    @FXML
    private TableColumn<Stock, String> c_UOM;

    @FXML
    private TableColumn<Stock, String> c_pais;

    @FXML
    private TableView<Stock> table_stock;

    @FXML
    private Button btn_confirmar_stock;

    ArrayList<Stock> Stock_List = Stock.StockList;
    ObservableList<Stock> observableList;

    /**
     * Preenche a tabela de stock com dados obtidos da base de dados.
     * Obtém a lista de stocks utilizando a classe DBstatements, define as propriedades das colunas da tabela
     * e define os itens da tabela com a observablelist de stocks.
     *
     * @throws SQLException Se ocorrer um erro ao acessar a base de dados.
     */
    private void EncherTable() throws SQLException {
        List<Stock> StockList = DBstatements.GetStock();

        Map<String, Stock> StockMap = new HashMap<>();

        for(Stock stock : StockList){
            String nomeProduto = stock.getNome();
            if (StockMap.containsKey(nomeProduto)) {
                Stock existingStock = StockMap.get(nomeProduto);
                existingStock.setQuantidade(existingStock.getQuantidade() + stock.getQuantidade());
                existingStock.setV_base(existingStock.getV_base() + stock.getV_base());
            } else {
                StockMap.put(nomeProduto, stock);
            }
        }
        ArrayList<Stock> StockList2 = new ArrayList<>(StockMap.values());
        c_nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        c_Fornecedor.setCellValueFactory(new PropertyValueFactory<>("Fornecedor"));
        c_Quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        c_p_uni.setCellValueFactory(new PropertyValueFactory<>("Preco_uni"));
        c_v_base.setCellValueFactory(new PropertyValueFactory<>("v_base"));
        c_UOM.setCellValueFactory(new PropertyValueFactory<>("UOM"));
        c_pais.setCellValueFactory(new PropertyValueFactory<>("pais"));

        table_stock.setItems(FXCollections.observableList(StockList2));
    }

    /**
     * Método chamado após a janela de confirmação ser fechada.
     * Atualiza a tabela de stock.
     */
    private void onConfirmarStockJanelaFechada() {
        try {
            EncherTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Manipula o evento de clique no botão "Confirmar Stock".
     * Abre uma janela de confirmação e define um callback para a atualização da tabela após fechamento.
     */
    @FXML
    void Handler_confirmar_Stock() {
        String fxml = "com/example/sheetsandpicks/Stock/app_Confirmar-Stock.fxml";
        String title = "Confirmação do XML";

        try {
            util.criarCenaComCallback(fxml, title, this::onConfirmarStockJanelaFechada);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Configura um evento de clique duplo na tabela de Stock para realizar uma ação quando um item é selecionado.
     * Obtém o Stock selecionado na tabela, mas não executa nenhuma ação específica no método.
     */
    private void selectRow(){
        table_stock.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.PRIMARY && event.getClickCount()==2){
                Stock selectedStock = table_stock.getSelectionModel().getSelectedItem();
            }
        });
    }

    /**
     * Método chamado automaticamente durante a inicialização do controlador FXML.
     * Inicializa a interface do utilizador, removendo itens da lista de stocks a confirmar, definindo um placeholder para
     * a tabela de stocks, configurando a tabela com uma observablelist, preenchendo a tabela com dados da base de dados
     * e definindo eventos de clique na tabela.
     *
     * @param location  O localizador de recursos para a URL, não é utilizado neste método.
     * @param resources Os recursos para a ResourceBundle, não é utilizado neste método.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        btn_confirmar_stock.setVisible(true);
        Stock.StockList.removeAll(Stock_List);
        table_stock.setPlaceholder(new Label("Não existe Stock novo a confirmar"));
        table_stock.setItems(observableList);
        try {
            EncherTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        selectRow();
    }
}

