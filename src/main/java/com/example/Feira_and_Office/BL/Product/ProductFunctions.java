package com.example.Feira_and_Office.BL.Product;

import com.example.Feira_and_Office.Controller.Encomendas.CarrinhoController;
import com.example.Feira_and_Office.Controller.Encomendas.CriarEncomendaController;
import com.example.Feira_and_Office.Controller.Produtos.ProdutoController;
import com.example.Feira_and_Office.Controller.Produtos.quantidade_produtoController;
import com.example.util.Models.OrderLine;
import com.example.util.Models.Products;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductFunctions {
    public static void OpenQuantidade(ProdutoController produtoController, Products selected){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ProductFunctions.class.getResource("/com/example/Feira_And_Ofiice/encomenda/quantidade_produto.fxml"));
            Parent root = fxmlLoader.load();

            quantidade_produtoController quantidade_produtoController = fxmlLoader.getController();
            quantidade_produtoController.setProdutoController(produtoController);
            quantidade_produtoController.setInfo(selected);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Adicionar ao carrinho");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void OpenCarrinho(ArrayList<OrderLine> orderLines,ArrayList<Products> products){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(ProductFunctions.class.getResource("/com/example/Feira_And_Ofiice/encomenda/carrinho.fxml"));
            Parent root = fxmlLoader.load();

            CarrinhoController carrinhoController = fxmlLoader.getController();
            carrinhoController.setInfo(orderLines,products);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Adicionar ao carrinho");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void OpenCriarEncomenda(List<OrderLine> orderLines, double Total, double quantidade, List<Products> products){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ProductFunctions.class.getResource("/com/example/Feira_And_Ofiice/encomenda/criar_encomenda.fxml"));
            Parent root = fxmlLoader.load();
            CriarEncomendaController criarEncomendaController = fxmlLoader.getController();
            criarEncomendaController.setInfo(orderLines,Total,quantidade,products);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Criar Encomenda");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
