package com.example.sheetsandpicks.BL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe utilitária que contém métodos comuns para interação com interfaces gráficas.
 */
public class util {

    /**
     * Cria e exibe uma nova cena com base no arquivo FXML fornecido.
     *
     * @param fxml   O caminho do arquivo FXML.
     * @param title  O título da janela.
     * @param callback Um objeto de interface que fornece um método de retorno de chamada a ser executado quando a janela for fechada.
     */
    public static void criarCenaComCallback(String fxml, String title, Callback callback) {
        try {
            FXMLLoader loader = new FXMLLoader(util.class.getClassLoader().getResource(fxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnHidden(event -> callback.call());

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Interface funcional que define um método de retorno de chamada.
     */
    public interface Callback {
        /**
         * Método a ser implementado para fornecer a lógica a ser executada quando o callback for acionado.
         */
        void call();
    }
}
