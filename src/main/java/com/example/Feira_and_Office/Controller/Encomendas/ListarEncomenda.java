package com.example.Feira_and_Office.Controller.Encomendas;

import com.example.Feira_and_Office.Controller.SingletoneCliente;
import com.example.sheetsandpicks.Controllers.Encomenda.EncomendaController;
import com.example.sheetsandpicks.Controllers.Encomenda.info_Encomenda_controller;
import com.example.util.APIOrder;
import com.example.util.Models.OrderResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListarEncomenda implements Initializable {

    @FXML
    private TableColumn<OrderResponse, String> c_Date;

    @FXML
    private TableColumn<OrderResponse, String> c_Id;

    @FXML
    private TableColumn<OrderResponse, String> c_currency;

    @FXML
    private TableColumn<OrderResponse, Integer> c_status;

    @FXML
    private TableColumn<OrderResponse, Double> c_total;

    @FXML
    private TableView<OrderResponse> tableEncomendas;

    private void EncherTabela(){
        List<OrderResponse> orderResponses = APIOrder.BuscarEncomendaporCliente(SingletoneCliente.getId());

        c_Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        c_currency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        c_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        c_total.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        c_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableEncomendas.setItems(FXCollections.observableArrayList(orderResponses));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EncherTabela();
        SelectRow();
    }

    void SelectRow(){
        tableEncomendas.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount()==2 & mouseEvent.getButton()== MouseButton.PRIMARY){
                OrderResponse order = tableEncomendas.getSelectionModel().getSelectedItem();
                if(order!=null){
                    Abririnfo(order);
                }
            }
        });
    }

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
