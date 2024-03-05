package com.example.Feira_and_Office.Controller;

import com.example.Feira_and_Office.BL.Main.MainFunctions;
import com.example.util.Models.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button btn_sair;

    @FXML
    private ImageView butLogout;

    @FXML
    private Pane dashPane;

    @FXML
    private Label labelNomeCliente;

    @FXML
    void HandlerEncomendas(ActionEvent event) {
        Node conteudo = MainFunctions.AbrirPanes("com/example/Feira_And_Ofiice/dash/ver_encomendas.fxml");
        dashPane.getChildren().setAll(conteudo);
    }

    @FXML
    void HandlerProdutos(ActionEvent event) {
        Node conteudo=MainFunctions.AbrirPanes("com/example/Feira_And_Ofiice/dash/ver_produtos.fxml");
        dashPane.getChildren().setAll(conteudo);
    }


    @FXML
    void HandlerSair(ActionEvent event) {
        MainFunctions.CarregarConteudoLogin();

        Stage stage = (Stage) btn_sair.getScene().getWindow();
        stage.close();
    }

    public void setCliente(Cliente cliente) {
        Node conteudo=MainFunctions.AbrirPanes("com/example/Feira_And_Ofiice/dash/ver_produtos.fxml");
        dashPane.getChildren().setAll(conteudo);
        labelNomeCliente.setText(cliente.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image initialImage = new Image("/img/sair.png");
        ImageView imageView = new ImageView(initialImage);

        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        btn_sair.setGraphic(imageView);

        Image hoverImage = new Image("/img/sairOver.png");

        btn_sair.setOnMouseEntered(e -> {
            ImageView hoverImageView = new ImageView(hoverImage);
            hoverImageView.setFitWidth(30);
            hoverImageView.setFitHeight(30);
            btn_sair.setGraphic(hoverImageView);
        });

        btn_sair.setOnMouseExited(e -> btn_sair.setGraphic(imageView));
    }
}
