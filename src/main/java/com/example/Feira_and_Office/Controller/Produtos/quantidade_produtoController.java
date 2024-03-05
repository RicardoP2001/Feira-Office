package com.example.Feira_and_Office.Controller.Produtos;

import com.example.util.Models.Products;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class quantidade_produtoController {

    @FXML
    private Button butCancelar;

    @FXML
    private Button butConfirmar;

    @FXML
    private Label labelNomeProduto;

    @FXML
    private Spinner<Double> spinner;

    private ProdutoController produtoController;

    private Products products;

    @FXML
    void HandlerCancelar(ActionEvent event) {
        Stage scene= (Stage) butCancelar.getScene().getWindow();
        scene.close();
    }

    @FXML
    void HandlerConfirmar(ActionEvent event) {
        produtoController.setCarrinho(products,spinner.getValue());
        Stage scene= (Stage) butCancelar.getScene().getWindow();
        scene.close();
    }

    public void setInfo(Products products){
        labelNomeProduto.setText(products.getDescription());
        this.products=products;

        if (spinner.getValueFactory() == null) {
            SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(
                    0.0, Double.MAX_VALUE, 1.0, 0.1
            );
            spinner.setValueFactory(valueFactory);
        }
        spinner.getValueFactory().setValue(1.0);
    }

    public void setProdutoController(ProdutoController produtoController){
        this.produtoController=produtoController;
    }

}
