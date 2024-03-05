package com.example.Feira_and_Office.Controller.Produtos;

import com.example.Feira_and_Office.BL.Product.ProductFunctions;
import com.example.util.APIProduct;
import com.example.util.Models.OrderLine;
import com.example.util.Models.Products;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import static com.example.util.Pedidos.HandlerErrado;

public class ProdutoController implements Initializable {

    @FXML
    private Button butCarrinho;

    @FXML
    private TableColumn<Products, Boolean> c_Active;

    @FXML
    private TableColumn<Products, String> c_Description;

    @FXML
    private TableColumn<Products, UUID> c_ID;

    @FXML
    private TableColumn<Products, Double> c_PVP;

    @FXML
    private TableColumn<Products, Double> c_Stock;

    @FXML
    private TableColumn<Products, String> c_Unit;

    @FXML
    private TableView<Products> tableProdutos;

    private ArrayList<OrderLine> carrinho;

    private ArrayList<Products> ID;

    private int contador=1;



    @FXML
    void HandlerCarrinho(ActionEvent event) {
        if(!carrinho.isEmpty()) {
            ProductFunctions.OpenCarrinho(carrinho,ID);
        }else{
            HandlerErrado("Não tens nenhum item adicionado!");
        }
    }


    private void EncherTabela(){
        List<Products> productslist = APIProduct.BuscarProdutosparaFront();
        c_ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        c_Description.setCellValueFactory(new PropertyValueFactory<>("Description"));
        c_PVP.setCellValueFactory(new PropertyValueFactory<>("pvp"));
        c_Active.setCellValueFactory(new PropertyValueFactory<>("Active"));
        c_Stock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        c_Unit.setCellValueFactory(new PropertyValueFactory<>("Unit"));

        tableProdutos.setItems(FXCollections.observableArrayList(productslist));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID = new ArrayList<>();
        carrinho = new ArrayList<>();
        EncherTabela();
        SelectRow();
    }

    public void setCarrinho(Products product,double quantity){
        OrderLine orderLine = new OrderLine(contador, product.getCode(), quantity,"EUR", product.getPvp()*quantity);
        carrinho.add(orderLine);
        ID.add(product);
        contador++;
    }

    private void SelectRow(){
        tableProdutos.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton()== MouseButton.PRIMARY && mouseEvent.getClickCount()==2){
                Products products = tableProdutos.getSelectionModel().getSelectedItem();
                if(products!=null){
                    ProductFunctions.OpenQuantidade(this,products);
                }
            }
        });
    }
}
