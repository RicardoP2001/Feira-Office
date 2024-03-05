package com.example.sheetsandpicks.Controllers.Produtos;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.util.APIProduct;
import com.example.util.Models.Products;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

import static com.example.util.Pedidos.HandlerErrado;

/**
 * Controlador para a interface de atualização de produtos.
 */
public class UpdateProdutoController {

    @FXML
    private Button btn_Cancelar;

    @FXML
    private Button btn_adicionar;

    @FXML
    private ComboBox<String> cmb_Unit;

    @FXML
    private TextField txt_Stock;

    @FXML
    private CheckBox ch_ativar;

    @FXML
    private Label lb_titulo;

    @FXML
    private TextField txt_pvp;

    private Products products;
    /**
     * Manipulador de evento para o botão de alterar produto.
     * Chama o método da API para alterar os dados do produto com base nos campos preenchidos.
     * Fecha a janela de atualização do produto.
     * @param event O evento acionado pelo botão.
     */
    @FXML
    void HandlerAlterar(ActionEvent event) {
        double preco = DBstatements.getpreco(products.getId().toString());
        if(Double.parseDouble(txt_pvp.getText())>= preco) {
            APIProduct.AlterarProdutos(products.getId().toString(), ch_ativar.isSelected(), Double.parseDouble(txt_pvp.getText()), Double.parseDouble(txt_Stock.getText()), cmb_Unit.getValue());
            Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
            stage.close();
        }else{
            HandlerErrado("Não podes inserir um valor abaixo ao preço que compras-te. O valor é de:"+preco);
        }
    }
    /**
     * Manipulador de evento para o botão de cancelar a atualização do produto.
     * Exibe um diálogo de confirmação antes de fechar a janela de atualização.
     * @param event O evento acionado pelo botão.
     */
    @FXML
    void HandlerCancelar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText("Confirmação de ação");
        a.setContentText("Deseja mesmo sair da Alteração do Cliente ?");

        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {

            if (option.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
                stage.close();
            }
        }
    }
    /**
     * Define as informações do produto a serem exibidas na interface de atualização.
     * @param selectedProduto O produto selecionado para atualização.
     */
    public void SetInfoProduto(Products selectedProduto){
        this.products= selectedProduto;
        txt_pvp.setText(String.valueOf(selectedProduto.getPvp()));
        lb_titulo.setText("Alterar Produto:"+selectedProduto.getDescription());
        ch_ativar.setSelected(selectedProduto.isActive());
        txt_Stock.setText(String.valueOf(selectedProduto.getStock()));
        encher_cmb();
        cmb_Unit.setValue(selectedProduto.getUnit());
    }

    void encher_cmb(){
        cmb_Unit.setItems(FXCollections.observableArrayList(DBstatements.getmoeda()));
    }

}
