package com.example.sheetsandpicks.Controllers.Encomenda;

import com.example.sheetsandpicks.BL.util;
import com.example.util.APIOrder;
import com.example.util.Models.OrderResponse;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
/**
 * Controlador responsável pela manipulação da interface de encomendas.
 */
public class EncomendaController implements Initializable {

    @FXML
    private Button btn_confirmar_Encomenda;

    @FXML
    private TableColumn<OrderResponse, String> c_Cliente;

    @FXML
    private TableColumn<OrderResponse, LocalDate> c_Data;

    @FXML
    private TableColumn<OrderResponse, Double> c_Quantidade;

    @FXML
    private TableColumn<OrderResponse, Integer> c_Status;

    @FXML
    private TableColumn<OrderResponse, Double> c_Total;

    @FXML
    private TableColumn<OrderResponse, String> c_id;

    @FXML
    private TableColumn<OrderResponse, String> c_moeda;

    @FXML
    private TableColumn<OrderResponse, Double> c_taxa;

    @FXML
    private TableView<OrderResponse> table_encomenda;

    /**
     * Manipula o evento de clique no botão de confirmação de encomenda.
     * Abre uma nova cena para confirmar as encomendas.
     *
     * @param event O evento de clique no botão de confirmação de encomenda.
     */
    @FXML
    void Handler_confirmar_Encomenda(ActionEvent event) {
        String fxml = "com/example/sheetsandpicks/Encomendas/app_dash_Confirmar_Encomenda.fxml";
        String title = "Confirmação de Encomendas";

        try {
            util.criarCenaComCallback(fxml, title, this::EncherTabela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Preenche a tabela de encomendas com os dados obtidos da API.
     */
    void EncherTabela() {
        List<OrderResponse> listaencomendas = APIOrder.BuscarEncomenda("Ver");

        c_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        c_Data.setCellValueFactory(new PropertyValueFactory<>("Date"));
        c_Cliente.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue().getClientId().get(0).getName()));
        c_moeda.setCellValueFactory(new PropertyValueFactory<>("Currency"));
        c_taxa.setCellValueFactory(new PropertyValueFactory<>("TaxAmount"));
        c_Quantidade.setCellValueFactory(new PropertyValueFactory<>("NetAmount"));
        c_Total.setCellValueFactory(new PropertyValueFactory<>("TotalAmount"));
        c_Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        table_encomenda.setItems(FXCollections.observableArrayList(listaencomendas));
    }
    /**
     * Configura o evento de clique duplo na tabela para abrir detalhes da encomenda.
     */
    private void selectRow(){
        table_encomenda.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()== MouseButton.PRIMARY && mouseEvent.getClickCount()==2){
                OrderResponse selecionado=table_encomenda.getSelectionModel().getSelectedItem();
                if(selecionado!=null){
                    Abririnfo(selecionado);
                }
            }
        });
    }
    /**
     * Inicializa a tabela de encomendas e configura o evento de clique duplo.
     *
     * @param url            A URL não utilizada.
     * @param resourceBundle O ResourceBundle não utilizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EncherTabela();
        selectRow();
    }

    /**
     * Abre uma nova janela exibindo detalhes da encomenda selecionada.
     *
     * @param selected A encomenda selecionada.
     */
    public void Abririnfo(OrderResponse selected){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(EncomendaController.class.getResource("/com/example/sheetsandpicks/Encomendas/info_encomenda.fxml"));
            Parent root = fxmlLoader.load();
            info_Encomenda_controller iec= fxmlLoader.getController();
            iec.setInfo(selected);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
