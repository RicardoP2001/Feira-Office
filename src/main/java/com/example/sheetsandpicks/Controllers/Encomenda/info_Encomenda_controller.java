package com.example.sheetsandpicks.Controllers.Encomenda;

import com.example.util.Models.AddressResponse_Billing;
import com.example.util.Models.AddressResponse_Delivery;
import com.example.util.Models.OrderLine;
import com.example.util.Models.OrderResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

/**
 * Controlador responsável pela exibição de detalhes de uma encomenda.
 */
public class info_Encomenda_controller {

    @FXML
    private TableColumn<OrderLine, Integer> c_LineNumber;

    @FXML
    private TableColumn<OrderLine, Double> c_NetAmount;

    @FXML
    private TableColumn<OrderLine, Double> c_Price;

    @FXML
    private TableColumn<OrderLine, String> c_Productcode;

    @FXML
    private TableColumn<OrderLine, String> c_Unit;

    @FXML
    private Label lb_Cliente;

    @FXML
    private Label lb_Id;

    @FXML
    private Label lb_PostaCode_entrega;

    @FXML
    private Label lb_Status;

    @FXML
    private Label lb_Taxa;

    @FXML
    private Label lb_Total;

    @FXML
    private Label lb_cidadeEntrega;

    @FXML
    private Label lb_cidade_paga;

    @FXML
    private Label lb_data_Encomenda;

    @FXML
    private Label lb_endereco_entrega;

    @FXML
    private Label lb_endereco_paga;

    @FXML
    private Label lb_pais_entrega;

    @FXML
    private Label lb_pais_paga;

    @FXML
    private Label lb_postalcode_paga;

    @FXML
    private TableView<OrderLine> tableEncomenda;

    /**
     * Configura as informações da encomenda na interface gráfica.
     *
     * @param SelectedEncomenda A encomenda selecionada.
     */
   public void setInfo(OrderResponse SelectedEncomenda){

           lb_Id.setText(SelectedEncomenda.getId());
           lb_Cliente.setText(SelectedEncomenda.getClientId().get(0).getName());
           for(AddressResponse_Delivery Order : SelectedEncomenda.getDeliveryAddress()) {
               lb_PostaCode_entrega.setText(Order.getPostalCode());
               lb_cidadeEntrega.setText(Order.getCity());
               lb_endereco_entrega.setText(Order.getAddress1() + ',' + Order.getAddress2());
               lb_pais_entrega.setText(Order.getCountry());
           }

            for(AddressResponse_Billing Order : SelectedEncomenda.getBillingAddress()) {
                lb_cidade_paga.setText(Order.getCity());
                lb_endereco_paga.setText(Order.getAddress1() + ',' + Order.getAddress2());
                lb_pais_paga.setText(Order.getCountry());
                lb_postalcode_paga.setText(Order.getPostalCode());
            }


           lb_data_Encomenda.setText(String.valueOf(SelectedEncomenda.getDate()));
           switch (SelectedEncomenda.getStatus()) {
               case 1 -> lb_Status.setText("Criado!");

               case 2 -> lb_Status.setText("Aprovado");

               case 3 -> lb_Status.setText("Rejeitado");

               default -> lb_Status.setText("Desconhecido");
           }
           lb_Taxa.setText(String.valueOf(SelectedEncomenda.getTaxAmount()));

           lb_Total.setText(SelectedEncomenda.getTotalAmount() + SelectedEncomenda.getCurrency());
            List<OrderLine> Order = SelectedEncomenda.getLines();
           c_LineNumber.setCellValueFactory(new PropertyValueFactory<>("lineNumber"));
           c_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
           c_Unit.setCellValueFactory(new PropertyValueFactory<>("unit"));
           c_Productcode.setCellValueFactory(new PropertyValueFactory<>("productCode"));
           c_NetAmount.setCellValueFactory(new PropertyValueFactory<>("quantity"));

           tableEncomenda.setItems(FXCollections.observableArrayList(Order));
       }
    }
