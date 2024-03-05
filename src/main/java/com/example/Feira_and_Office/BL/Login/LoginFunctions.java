package com.example.Feira_and_Office.BL.Login;

import com.example.Feira_and_Office.Controller.MainController;
import com.example.util.Models.Cliente;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFunctions {
    public static void Abrirpanes(String fxml){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginFunctions.class.getResource("/"+fxml));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Feira&Office - Escolha");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void MandarParaaMain(Cliente cliente){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(LoginFunctions.class.getResource("/com/example/Feira_And_Ofiice/dash/main_dash.fxml"));
            Parent root = fxmlLoader.load();

            MainController mainController = fxmlLoader.getController();
            mainController.setCliente(cliente);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Feira&Office - APP");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Node abrirjanelas(String fxml){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginFunctions.class.getResource("/"+fxml));

            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
