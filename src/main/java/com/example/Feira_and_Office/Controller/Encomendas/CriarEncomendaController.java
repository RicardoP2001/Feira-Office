package com.example.Feira_and_Office.Controller.Encomendas;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.util.APIOrder;
import com.example.util.APIProduct;
import com.example.util.Models.Address;
import com.example.util.Models.OrderLine;
import com.example.util.Models.Products;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static com.example.util.Pedidos.HandlerErrado;

public class CriarEncomendaController {

    @FXML
    private Button butCancelar;

    @FXML
    private Button butConfirmar;

    @FXML
    private ComboBox<String> cmb_Pais;

    @FXML
    private ComboBox<String> cmb_Pais_paga;

    @FXML
    private CheckBox ch_check;

    @FXML
    private TextField txt_cidade;

    @FXML
    private TextField txt_cidade_paga;

    @FXML
    private TextField txt_endereco;

    @FXML
    private TextField txt_endereco2;

    @FXML
    private TextField txt_endereco2_paga;

    @FXML
    private TextField txt_endereco_paga;

    @FXML
    private TextField txt_postalcode;

    @FXML
    private TextField txt_postalcode_paga;

    private List<OrderLine> orderLineList;

    private boolean escolha;

    private double Total;

    private double Quantidade;
    private List<Products> productsLineList;

    @FXML
    void HandlerCancelar(ActionEvent event) {
        HandlerPergunta();
    }

    @FXML
    void HandlerConfirmar(ActionEvent event) {
        Address billing;
        OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX");
        String dataFormatada = offsetDateTime.format(formatter);
        Address delivery = new Address(txt_endereco.getText(), txt_endereco2.getText(), txt_postalcode.getText(), txt_cidade.getText(), cmb_Pais.getValue());
        if (escolha) {
            billing = delivery;
        } else {
            billing = new Address(txt_endereco_paga.getText(), txt_endereco2_paga.getText(), txt_postalcode.getText(), txt_cidade.getText(), cmb_Pais_paga.getValue());
        }
        if (orderLineList != null && !orderLineList.isEmpty()) {
            for (OrderLine orderLine : orderLineList) {
                String descobrir = String.valueOf(productsLineList.stream().filter(products -> products.getCode().equals(orderLine.getProductCode())).map(Products::getId).findFirst().orElse(null));
                System.out.println("Aqui é este:"+descobrir);
                double stockAtual = APIProduct.BuscarStockProdutoId(descobrir);
                if (stockAtual != 0) {
                    double stockModificado = stockAtual - orderLine.getQuantity();
                    System.out.println(stockAtual);
                    APIProduct.AlterarProdutosStock(descobrir, stockModificado);
                }
            }
            if(APIOrder.CriarEncomenda(dataFormatada,delivery,billing,orderLineList)){
                Stage stage= (Stage) butCancelar.getScene().getWindow();
                stage.close();
            }else{
                HandlerErrado("Nao executou direito!");
            }
        }
    }

    @FXML
    void handlerCheck(ActionEvent event) {
        if(ch_check.isSelected()) {
            txt_endereco_paga.setDisable(true);
            txt_endereco2_paga.setDisable(true);
            txt_cidade_paga.setDisable(true);
            txt_postalcode_paga.setDisable(true);
            cmb_Pais_paga.setDisable(true);
            this.escolha=true;
        }else{
            txt_endereco_paga.setDisable(false);
            txt_endereco2_paga.setDisable(false);
            txt_cidade_paga.setDisable(false);
            txt_postalcode_paga.setDisable(false);
            cmb_Pais_paga.setDisable(false);
            this.escolha=false;
        }
    }

    private void HandlerPergunta(){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Tem a certeza?");
        a.setContentText("Que quer sair do carrinho?");
        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                Stage stage = (Stage) butCancelar.getScene().getWindow();
                stage.close();
            }
        }
    }

    public void setInfo(List<OrderLine> orderLines, double Total, double Quantidade, List<Products> products){
        this.orderLineList=orderLines;
        this.Total=Total;
        this.Quantidade=Quantidade;
        this.productsLineList=products;
        Definircombo();
    }

    public void Definircombo(){
        try {
            cmb_Pais.setItems(DBstatements.getPais());
            cmb_Pais_paga.setItems(DBstatements.getPais());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
