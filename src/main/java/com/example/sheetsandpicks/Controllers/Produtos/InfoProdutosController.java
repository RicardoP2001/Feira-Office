package com.example.sheetsandpicks.Controllers.Produtos;

import com.example.util.Models.Products;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * Controlador para a visualização de informações detalhadas de um produto.
 */
public class InfoProdutosController {

    @FXML
    private Label lb_Code;

    @FXML
    private Label lb_Pvp;

    @FXML
    private Label lb_Stock;

    @FXML
    private Label lb_Unit;

    @FXML
    private Label lb_descricao;
    /**
     * Configura as informações do produto nos elementos visuais.
     * @param selected O produto selecionado.
     */
    public void SetInfo(Products selected){
        lb_descricao.setText(selected.getDescription());
        lb_Code.setText(selected.getCode());
        lb_Pvp.setText(String.valueOf(selected.getPvp()));
        lb_Unit.setText(selected.getUnit());
        lb_Stock.setText(String.valueOf(selected.getStock()));
    }

}