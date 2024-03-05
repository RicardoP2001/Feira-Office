package com.example.sheetsandpicks.Controllers.Produtos;

import com.example.util.APIProduct;
import com.example.util.Models.Products;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import static com.example.util.Pedidos.HandlerErrado;
/**
 * Controlador para a interface para gerir produtos.
 */
public class ProdutoController implements Initializable {

    @FXML
    private Button btn_Alterar_Produto;

    @FXML
    private TableColumn<Products, UUID> c_ID;

    @FXML
    private TableColumn<Products, Double> c_PVP;

    @FXML
    private TableColumn<Products, Double> c_Stock;

    @FXML
    private TableColumn<Products, String> c_Unit;

    @FXML
    private TableColumn<Products, String> c_descricao;

    @FXML
    private TableColumn<Products, Boolean> c_status;

    @FXML
    private TableView<Products> table_Produtos;
    /**
     * Manipulador de evento para o botão de alterar produto.
     * Abre a interface de atualização do produto com o produto selecionado.
     * Exibe uma mensagem de erro se nenhum produto estiver selecionado.
     * @param event O evento acionado pelo botão.
     */
    @FXML
    void Handler_Alterar_Produto(ActionEvent event) {
        Products products = table_Produtos.getSelectionModel().getSelectedItem();
        if(products!=null) {
            AbrirUpdate(products);
        }else{
            HandlerErrado("Seleciona pelo menos 1!");
        }
    }
    /**
     * Preenche a tabela de produtos com os dados obtidos da API.
     */
    public void EncherTabela(){
        List<Products> productsList = APIProduct.BuscarProdutos();
        System.out.println(productsList);

        c_ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        c_descricao.setCellValueFactory(new PropertyValueFactory<>("description"));
        c_Unit.setCellValueFactory(new PropertyValueFactory<>("Unit"));
        c_status.setCellValueFactory(new PropertyValueFactory<>("Active"));
        c_Stock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        c_PVP.setCellValueFactory(new PropertyValueFactory<>("pvp"));

        table_Produtos.setItems(FXCollections.observableArrayList(productsList));
    }
    /**
     * Configura a ação ao clicar duas vezes em uma linha da tabela.
     */
    void SelectRow(){
        table_Produtos.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount()==2 && mouseEvent.getButton() == MouseButton.PRIMARY){
                Products products = table_Produtos.getSelectionModel().getSelectedItem();
                if(products!=null){
                    AbrirInfo(products);
                }
            }
        });
    }
    /**
     * Inicializa o controlador.
     * Preenche a tabela de produtos e configura a ação ao selecionar uma linha.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EncherTabela();
        SelectRow();
    }
    /**
     * Abre a interface de atualização do produto com as informações do produto selecionado.
     * @param selected O produto selecionado.
     */
    private void AbrirUpdate(Products selected){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/com/example/sheetsandpicks/Produtos/app_dash_updateProduto.fxml"));
            Parent root = fxmlLoader.load();
            UpdateProdutoController updateProdutoController = fxmlLoader.getController();
            updateProdutoController.SetInfoProduto(selected);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Abre a interface de informações detalhadas do produto com as informações do produto selecionado.
     * @param selected O produto selecionado.
     */
    private void AbrirInfo(Products selected){
        try{
            FXMLLoader fxmlLoader=new FXMLLoader(this.getClass().getResource("/com/example/sheetsandpicks/Produtos/Info_Produtos.fxml"));
            Parent root = fxmlLoader.load();
            InfoProdutosController ipc = fxmlLoader.getController();
            ipc.SetInfo(selected);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
