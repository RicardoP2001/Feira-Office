package com.example.sheetsandpicks.Controllers.Stock;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.BL.Stock.StockFunctions;
import com.example.sheetsandpicks.Models.Stock;
import com.example.sheetsandpicks.Models.StockAprov_rejeita;
import com.example.util.APIProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.util.Pedidos.HandlerConfirmado;
import static com.example.util.Pedidos.HandlerErrado;
/**
 * Controlador para a janela de confirmação de stock.
 * Permite ao utilizador visualizar, confirmar ou retornar stocks.
 */
public class StockConfirmationController implements Initializable {

    @FXML
    private Button btn_Cancelar;

    @FXML
    private TableColumn<Stock, String> c_Aceite_Rejeitado;

    @FXML
    private TableColumn<Stock, String> c_n_encomenda;

    @FXML
    private TableColumn<Stock, String> c_n_encomenda_confirmado;

    @FXML
    private Button btn_Confirmar;

    @FXML
    private Button btn_confirmar_Stock;

    @FXML
    private TableColumn<Stock, String> c_Fornecedor_confirmado;

    @FXML
    private TableColumn<Stock,String> c_Fornecedor;

    @FXML
    private TableColumn<Stock, String> c_Nome_confirmado;

    @FXML
    private TableColumn<Stock, String> c_Nome;

    @FXML
    private TableColumn<Stock, Integer> c_Quantidade;

    @FXML
    private TableColumn<Stock, Integer> c_Quantidade_confirmado;

    @FXML
    private TableView<Stock> table_Stocks;

    @FXML
    private TableView<Stock> table_confirmados;

    ArrayList<Stock> StockConfirmar_List = Stock.StockList;
    ObservableList<Stock> observableList;

    private ObservableList<Stock> observableListconfirmados;

    private AppDashVizualizarStock appvizualiza=new AppDashVizualizarStock();

    /**
     * Manipula o evento de clique no botão "Cancelar" na janela de confirmação de ação.
     *
     */
    @FXML
    void Handler_Cancelar_Confirmacao() {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Confirmação de ação");
        a.setContentText("Deseja mesmo sair da Confirmação de Stock?");

        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {

            if (option.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * Manipula o evento de clique no botão de confirmação de stock.
     * Obtém o item selecionado na tabela de stocks e chama um método para abrir um pop-up de confirmação.
     *
     */
    @FXML
    void Handler_Confirma_Stock() {
        Stock selectedStock = table_Stocks.getSelectionModel().getSelectedItem();
        if(selectedStock!=null){
            String fxml= "com/example/sheetsandpicks/Stock/Pop-UP_ConfirmarStock.fxml";
            String nome="Pop-UP Confirmar";
            StockFunctions.chamarabridor(fxml,nome,selectedStock,this);
        }
    }

    /**
     * Manipula o evento de clique no botão "Terminar" na janela de confirmação de stock.
     * Obtém a lista de itens confirmados na tabela, extrai os IDs , aprova e confirma o stock na base de dados.
     * Fecha a janela de confirmação de stock após a conclusão do processo.
     *
     */
    @FXML
    void Handler_terminar_Confirmacao() {
        ObservableList<Stock> listadeids= table_confirmados.getItems();

        if (listadeids.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum produto selecionado");
            alert.setContentText("Por favor, selecione pelo menos um produto para confirmar.");
            alert.showAndWait();
            return;
        }

        ArrayList<StockAprov_rejeita> Aprov = StockFunctions.GetStockInfo(listadeids);



        for(StockAprov_rejeita apro : Aprov) {
            try {
                System.out.println(apro.getId());
                Stock stock=DBstatements.GetStockAPI(apro.getId());
                System.out.println(stock.getGUID());
                if(APIProduct.CriarProduto(stock)){
                    DBstatements.ConfirmarStock(apro);
                    HandlerConfirmado();
                }else{
                    HandlerErrado("Não funcionou! API Não enviada");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        Stage stage = (Stage) btn_Confirmar.getScene().getWindow();
        stage.close();
    }

    /**
     * Preenche a tabela de stock com dados obtidos da base de dados.
     * Utiliza a classe DBstatements para obter a lista de stock, configura as propriedades das colunas
     * e define os itens da tabela com a ObservableList de stock.
     */
    public void Encher_tabela() throws SQLException {
        List<Stock> StockList = DBstatements.GetStock();
        observableList=FXCollections.observableArrayList();

        c_Nome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        c_Fornecedor.setCellValueFactory(new PropertyValueFactory<>("Fornecedor"));
        c_Quantidade.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));
        c_n_encomenda.setCellValueFactory(new PropertyValueFactory<>("N_Encomenda"));

        table_Stocks.setItems(FXCollections.observableList(StockList));

        observableList.addAll(StockList);
    }


    /**
     * Configura eventos de clique duplo e tecla Enter nas tabelas de stock para abrir pop-ups de confirmação ou
     * retornar itens à tabela principal, dependendo da tabela em que o evento ocorreu.
     * Os eventos são defenidos tanto para a tabela principal quanto para a tabela de itens confirmados.
     */
    private void selectRow(){

        table_Stocks.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table_Stocks.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) & event.getClickCount()==2){
                ObservableList<Stock> selectedStocks = table_Stocks.getSelectionModel().getSelectedItems();
                for (Stock selectedStock : selectedStocks) {
                    String fxml = "com/example/sheetsandpicks/Stock/Pop-UP_ConfirmarStock.fxml";
                    String nome = "Pop-UP Confirmar";
                    StockFunctions.chamarabridor(fxml, nome, selectedStock, this);
                }
            }
        });

        table_Stocks.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                ObservableList<Stock> selectedStocks = table_Stocks.getSelectionModel().getSelectedItems();
                for (Stock selectedStock : selectedStocks) {
                    String fxml = "com/example/sheetsandpicks/Stock/Pop-UP_ConfirmarStock.fxml";
                    String nome = "Pop-UP Confirmar";
                    StockFunctions.chamarabridor(fxml, nome, selectedStock, this);
                }
            }
        });
        table_confirmados.setOnMouseClicked(event -> {
            if(event.getButton()==MouseButton.PRIMARY && event.getClickCount()==2){
                Stock selectedStock =  table_confirmados.getSelectionModel().getSelectedItem();
                if(selectedStock!=null){
                    Retornar_tabela_Stock(selectedStock);
                }
            }
        });
        table_confirmados.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                Stock selectedStock =  table_confirmados.getSelectionModel().getSelectedItem();
                if(selectedStock!=null){
                    Retornar_tabela_Stock(selectedStock);
                }
            }
        });
    }


    /**
     * Preenche a tabela de itens confirmados com o stock selecionado.
     * Se a tabela não estiver inicializada, cria a estrutura da tabela e a associa aos controladores de coluna.
     * Adiciona o stock à lista observável da tabela de itens confirmados e remove-o da tabela principal, se aplicável.
     *
     * @param selectedStock O stock selecionado para ser adicionado à tabela de itens confirmados.
     */
    public void TabelaConfirmar_StockFill(Stock selectedStock){
        if(observableListconfirmados == null){
            observableListconfirmados = FXCollections.observableArrayList();

            c_Nome_confirmado.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            c_Fornecedor_confirmado.setCellValueFactory(new PropertyValueFactory<>("Fornecedor"));
            c_Quantidade_confirmado.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));
            c_n_encomenda_confirmado.setCellValueFactory(new PropertyValueFactory<>("N_Encomenda"));
            c_Aceite_Rejeitado.setCellValueFactory(new PropertyValueFactory<>("aprov_rejeitado"));

            table_confirmados.setItems(observableListconfirmados);
        }

        if(!observableListconfirmados.contains(selectedStock)){
            observableListconfirmados.add(selectedStock);

            ObservableList<Stock> tableStockconfirmado = FXCollections.observableArrayList(table_Stocks.getItems());
            if (tableStockconfirmado.contains(selectedStock)) {
                tableStockconfirmado.remove(selectedStock);
                observableList.remove(selectedStock);
                table_Stocks.setItems(tableStockconfirmado);
            }
        }
    }


    /**
     * Retorna um item selecionado à tabela principal de stocks.
     * Adiciona o item de volta à lista observável da tabela principal e remove-o da tabela de itens confirmados.
     *
     * @param selectedStock O stock selecionado para ser retornado à tabela principal.
     */
    private void Retornar_tabela_Stock(Stock selectedStock){
        observableList.add(selectedStock);
        List<Stock> itensStocks = new ArrayList<>();
        itensStocks.add(selectedStock);

        table_confirmados.getItems().removeAll(itensStocks);
        table_Stocks.setItems(observableList);
    }


    /**
     * Método chamado automaticamente na inicialização do controlador FXML.
     * Inicializa a interface do utilizador, preenchendo a tabela principal de stocks, definindo eventos ao clicar na tabela,
     * e removendo itens da lista de stocks confirmados.
     *
     * @param location  O localizador de recursos para a URL, não é utilizado neste método.
     * @param resources Os recursos para a ResourceBundle, não é utilizado neste método.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stock.StockList.removeAll(StockConfirmar_List);
        try {
            Encher_tabela();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        selectRow();
    }
}