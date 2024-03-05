package com.example.Feira_and_Office.BL.Main;

import com.example.Feira_and_Office.Controller.MainController;
import com.example.sheetsandpicks.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFunctions {
    public static Node AbrirPanes(String fxml){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("/"+fxml));
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void CarregarConteudoLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/" + "com/example/Feira_And_Ofiice/Login/app_login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
            Stage stage = new Stage();
            stage.setTitle("Login - Feira&Office");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
