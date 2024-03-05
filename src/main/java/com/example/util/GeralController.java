package com.example.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Controlador para a interface geral da aplicação.
 */
public class GeralController {

    @FXML
    private Button btn_Cliente;

    @FXML
    private Button btn_Funcionario;

    @FXML
    private StackPane pane;
    /**
     * Manipula o evento de mudança para a interface do funcionário.
     *
     * @param event O evento de clique do botão.
     */
    @FXML
    void HandlerMudarFuncionario(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sheetsandpicks/Login/app_login.fxml"));
            Parent root= loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Feira&Office - Login");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) btn_Funcionario.getScene().getWindow();
        stage.close();
    }
    /**
     * Manipula o evento de mudança para a interface do cliente.
     *
     * @param event O evento de clique do botão.
     */
    @FXML
    void HandlerMudar_Cliente(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Feira_And_Ofiice/Login/app_login.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Feira&Office - Login");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) btn_Funcionario.getScene().getWindow();
        stage.close();
    }
}
