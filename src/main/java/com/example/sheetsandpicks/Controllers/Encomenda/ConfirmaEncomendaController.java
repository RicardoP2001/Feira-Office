package com.example.sheetsandpicks.Controllers.Encomenda;

import com.example.sheetsandpicks.BL.Encomenda.EncomendaFunctions;
import com.example.util.APIOrder;
import com.example.util.Models.OrderResponse;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Controlador para a interface gráfica de confirmação de encomendas.
 */
public class ConfirmaEncomendaController implements Initializable {

    @FXML
    private Button btn_Cancelar;

    @FXML
    private Button btn_Confirmar;

    @FXML
    private Button btn_confirmar_Encomenda;

    @FXML
    private TableColumn<OrderResponse, String> c_Cliente;

    @FXML
    private TableColumn<OrderResponse, String> c_Cliente_confirmado;

    @FXML
    private TableColumn<OrderResponse, Double> c_Quantidade;

    @FXML
    private TableColumn<OrderResponse, Double> c_Quantidade_Confirmado;

    @FXML
    private TableColumn<OrderResponse, Double> c_Total;

    @FXML
    private TableColumn<OrderResponse, String> c_aprov_rejeitado;

    @FXML
    private TableColumn<OrderResponse, String> c_id_encomenda;

    @FXML
    private TableColumn<OrderResponse, String> c_id_encomenda_confirmado;

    @FXML
    private TableColumn<OrderResponse,String> c_moeda;

    @FXML
    private TableColumn<OrderResponse, String> c_moeda_confirmado;

    @FXML
    private TableColumn<OrderResponse, Double> c_total_confirmado;

    @FXML
    private TableView<OrderResponse> table_Encomenda;

    @FXML
    private TableView<OrderResponse> table_confirmados;

    private ObservableList<OrderResponse> observableListconfirmados;

    private ObservableList<OrderResponse> observableList;
    /**
     * Manipula o evento de clique no botão "Cancelar Confirmação".
     * Exibe um pop-up de confirmação e fecha a janela se o utilizador confirmar.
     *
     * @param event O evento de clique no botão.
     */
    
    @FXML
    void Handler_Cancelar_Confirmacao(ActionEvent event) {
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
     * Manipula o evento de clique no botão "Confirma Encomenda".
     * Obtém a encomenda selecionada na tabela, abre um pop-up para confirmação de stock e passa a encomenda selecionada.
     *
     * @param event O evento de clique no botão.
     */
    @FXML
    void Handler_Confirma_Encomenda(ActionEvent event) {
        OrderResponse selectedItem = table_Encomenda.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            String fxml= "com/example/sheetsandpicks/Stock/Pop-UP_ConfirmarStock.fxml";
            String nome="Pop-UP Confirmar";
            EncomendaFunctions.chamarabridor(fxml,nome,selectedItem,this);
        }
    }
    /**
     * Manipula o evento de clique no botão "Terminar Confirmação".
     * Obtém a lista de encomendas confirmadas na tabela, exibe um alerta se nenhuma encomenda estiver selecionada
     * e, em seguida, altera o estado de cada encomenda na API.
     *
     * @param event O evento de clique no botão.
     */
    @FXML
    void Handler_terminar_Confirmacao(ActionEvent event) {
        ObservableList<OrderResponse> list = table_confirmados.getItems();

        if(list.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum produto selecionado");
            alert.setContentText("Por favor, selecione pelo menos um produto para confirmar.");
            alert.showAndWait();
            return;
        }

        for(OrderResponse apro : list){
            APIOrder.AlterarEncomenda(apro.getId(),apro.getStatus());
        }
    }
    /**
     * Preenche a tabela de encomendas com dados recuperados da API.
     */
    void EncherTabela() {
        List<OrderResponse> listaencomendas = APIOrder.BuscarEncomenda("Confirmar");
        observableList=FXCollections.observableArrayList();
        c_id_encomenda.setCellValueFactory(new PropertyValueFactory<>("Id"));
        c_Cliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClientId().get(0).getName()));
        c_moeda.setCellValueFactory(new PropertyValueFactory<>("Currency"));
        c_Quantidade.setCellValueFactory(new PropertyValueFactory<>("NetAmount"));
        c_Total.setCellValueFactory(new PropertyValueFactory<>("TotalAmount"));
        table_Encomenda.setItems(FXCollections.observableArrayList(listaencomendas));

        observableList.addAll(listaencomendas);
    }
    /**
     * Adiciona a encomenda selecionada à tabela de encomendas confirmadas.
     * Remove a encomenda da tabela de encomendas a confirmar.
     *
     * @param selectedEncomenda A encomenda selecionada para confirmação.
     */
    public void TabelaConfirmar_Encomenda(OrderResponse selectedEncomenda){
        if(observableListconfirmados == null){
            observableListconfirmados = FXCollections.observableArrayList();

            c_id_encomenda_confirmado.setCellValueFactory(new PropertyValueFactory<>("Id"));
            c_Cliente_confirmado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClientId().get(0).getName()));
            c_moeda_confirmado.setCellValueFactory(new PropertyValueFactory<>("Currency"));
            c_Quantidade_Confirmado.setCellValueFactory(new PropertyValueFactory<>("NetAmount"));
            c_total_confirmado.setCellValueFactory(new PropertyValueFactory<>("TotalAmount"));
            c_aprov_rejeitado.setCellValueFactory(new PropertyValueFactory<>("Status"));

            table_confirmados.setItems(observableListconfirmados);
        }

        if(!observableListconfirmados.contains(selectedEncomenda)){
            observableListconfirmados.add(selectedEncomenda);

            ObservableList<OrderResponse> tableStockconfirmado = FXCollections.observableArrayList(table_Encomenda.getItems());
            if (tableStockconfirmado.contains(selectedEncomenda)) {
                tableStockconfirmado.remove(selectedEncomenda);
                observableList.remove(selectedEncomenda);
                table_Encomenda.setItems(tableStockconfirmado);
            }
        }
    }
    /**
     * Retorna a encomenda selecionada da tabela de encomendas confirmadas
     * para a tabela de encomendas a confirmar.
     *
     * @param selectedEncomenda A encomenda a ser retornada à tabela de encomendas a confirmar.
     */
    private void Retornar_tabela_Encomenda(OrderResponse selectedEncomenda){
        observableList.add(selectedEncomenda);
        List<OrderResponse> itensStocks = new ArrayList<>();
        itensStocks.add(selectedEncomenda);

        table_confirmados.getItems().removeAll(itensStocks);
        table_Encomenda.setItems(observableList);
    }
    /**
     * Configura a seleção de linhas nas tabelas e os eventos de clique e tecla pressionada.
     */
    public void selectRow(){
        table_Encomenda.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table_Encomenda.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.PRIMARY && event.getClickCount()==2){
                ObservableList<OrderResponse> selectedItems = table_Encomenda.getSelectionModel().getSelectedItems();
                for (OrderResponse selectedItem : selectedItems) {
                    String fxml = "com/example/sheetsandpicks/Encomendas/Pop-UP_ConfirmarEncomenda.fxml";
                    String nome = "Pop-UP Confirmar";
                    EncomendaFunctions.chamarabridor(fxml, nome, selectedItem, this);
                }
            }
        });

        table_Encomenda.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                ObservableList<OrderResponse> selectedStocks = table_Encomenda.getSelectionModel().getSelectedItems();
                for (OrderResponse selectedItem : selectedStocks) {
                    String fxml = "com/example/sheetsandpicks/Encomendas/Pop-UP_ConfirmarEncomenda.fxml";
                    String nome = "Pop-UP Confirmar";
                    EncomendaFunctions.chamarabridor(fxml, nome, selectedItem, this);
                }
            }
        });
        table_confirmados.setOnMouseClicked(event -> {
            if(event.getButton()==MouseButton.PRIMARY && event.getClickCount()==2){
                OrderResponse selectedStock =  table_confirmados.getSelectionModel().getSelectedItem();
                if(selectedStock!=null){
                    Retornar_tabela_Encomenda(selectedStock);
                }
            }
        });
        table_confirmados.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                OrderResponse selectedStock =  table_confirmados.getSelectionModel().getSelectedItem();
                if(selectedStock!=null){
                    Retornar_tabela_Encomenda(selectedStock);
                }
            }
        });
    }
    /**
     * Inicializa a tabela e configura a seleção de linhas.
     *
     * @param url            O URL relativo do arquivo FXML.
     * @param resourceBundle Os recursos específicos do local que podem ser usados para internacionalização.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EncherTabela();
        selectRow();
    }
}
