package com.example.Feira_and_Office.Controller.Encomendas;

import com.example.Feira_and_Office.BL.Product.ProductFunctions;
import com.example.util.APIProduct;
import com.example.util.Models.OrderLine;
import com.example.util.Models.Products;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarrinhoController{

    @FXML
    private Button butCancelar;

    @FXML
    private Button butCriar;

    @FXML
    private TableColumn<Products, String> c_Produto;

    @FXML
    private TableColumn<OrderLine, Double> c_Quantidade;

    @FXML
    private TableColumn<OrderLine, Double> c_Total;

    @FXML
    private TableColumn<OrderLine, String> c_moeda;

    @FXML
    private TableView<OrderLine> tableProdutos;

    private static List<OrderLine> productsList;

    private static List<Products> productsList2;

    private double Total;

    private double Quantidade;

    @FXML
    void HandlerCancelar(ActionEvent event) {
        HandlerPergunta();
    }

    @FXML
    void HandlerCriar(ActionEvent event) {
        ProductFunctions.OpenCriarEncomenda(productsList,Total,Quantidade,productsList2);
    }

    void EncherTabela(){
        c_Produto.setCellValueFactory(cellData -> new SimpleStringProperty(productsList2.get(0).getDescription()));
        tableProdutos.getItems().remove(c_Produto);

        TableColumn<Products, String> c_Produt = new TableColumn<>("Produto");
        c_Produt.setCellValueFactory(cellData -> {
            String produtoID = String.valueOf(cellData.getValue().getId());
            System.out.println(produtoID);
            String produto = APIProduct.BuscarProdutoporId(produtoID);
            System.out.println(produto);
            return new SimpleStringProperty(produto);
        });
        c_moeda.setCellValueFactory(new PropertyValueFactory<>("Unit"));
        c_Quantidade.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        c_Total.setCellValueFactory(new PropertyValueFactory<>("Price"));

        tableProdutos.setItems(FXCollections.observableArrayList(productsList));
    }

    public void setInfo(ArrayList<OrderLine> orderLines,ArrayList<Products> products) {
        if (productsList == null) {
            productsList = new ArrayList<>();
        }

        if(productsList2== null){
            productsList2 = new ArrayList<>();
        }
        productsList.clear();
        productsList.addAll(orderLines);
        productsList2.clear();
        productsList2.addAll(products);
        tableProdutos.setItems(FXCollections.observableArrayList(productsList));
        EncherTabela();
        for(OrderLine totais:orderLines){
            Total+=totais.getPrice();
            Quantidade+=totais.getQuantity();
        }
    }

    private void HandlerPergunta(){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Tem a certeza?");
        a.setContentText("Que quer sair do carrinho?");
        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                Stage stage = (Stage) butCriar.getScene().getWindow();
                stage.close();
            }
        }
    }


}
