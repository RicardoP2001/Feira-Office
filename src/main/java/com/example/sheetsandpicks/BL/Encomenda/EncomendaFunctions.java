package com.example.sheetsandpicks.BL.Encomenda;

import com.example.sheetsandpicks.BL.util;
import com.example.sheetsandpicks.Controllers.Encomenda.ConfirmaEncomendaController;
import com.example.sheetsandpicks.Controllers.Encomenda.Pop_up_ConfirmarEncomenda;
import com.example.util.Models.OrderResponse;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe que contém funções relacionadas à manipulação de encomendas.
 */
public class EncomendaFunctions {

    /**
     * Abre uma nova janela (Stage) com o layout carregado do arquivo FXML especificado,
     * passando dados relevantes para o controlador da nova janela.
     *
     * @param fxml     O caminho para o arquivo FXML que define o layout da nova janela.
     * @param nome     O título da nova janela.
     * @param selectedItem A encomenda selecionada a ser passada para o controlador da nova janela.
     * @param a        O controlador da janela de confirmação de encomenda a ser referenciado na nova janela.
     */
    public static void chamarabridor(String fxml, String nome, OrderResponse selectedItem, ConfirmaEncomendaController a){
        try {
            FXMLLoader loader = new FXMLLoader(util.class.getResource("/"+fxml));
            Parent root = loader.load();
            Pop_up_ConfirmarEncomenda targetController = loader.getController();
            targetController.setEncomenda(selectedItem);
            targetController.setTabelaConfirmar_StockFill(a);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(nome);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
