package com.example.sheetsandpicks.api;

import com.example.sheetsandpicks.BL.MainFunctions.MainFunctions;
import com.example.sheetsandpicks.Controllers.Users.ClientsController;
import com.example.sheetsandpicks.Controllers.app_dashboard.UpdateClientesController;
import com.example.util.Models.Cliente;
import com.example.sheetsandpicks.Controllers.Users.Info_ClienteController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * API para interação com funcionalidades relacionadas aos clientes.
 */
public class APICliente {

    /**
     * Abre a interface para atualizar informações de um cliente específico.
     *
     * @param cliente            O cliente cujas informações serão atualizadas.
     * @param clientsController  O controlador de clientes associado.
     */
    public static void AbrirCliente(Cliente cliente, ClientsController clientsController){
        try {
            FXMLLoader loader = new FXMLLoader(MainFunctions.class.getResource("/com/example/sheetsandpicks/Users/app_dash_updateClientes.fxml"));
            Parent root = loader.load();
            UpdateClientesController controller = loader.getController();
            controller.ClienteAtivado(cliente, clientsController);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Abre a interface para criar um novo cliente.
     */
    public static void Abrir_criarCliente(){
        try {
            FXMLLoader loader = new FXMLLoader(APICliente.class.getResource("/com/example/sheetsandpicks/Users/app_dash_Clientes.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Abre a interface para exibir informações detalhadas de um cliente.
     *
     * @param cliente O cliente cujas informações serão exibidas.
     */
    public static void Abrir_infoCliente(Cliente cliente){
        try {
            FXMLLoader loader = new FXMLLoader(APICliente.class.getResource("/com/example/sheetsandpicks/Info_Cliente.fxml"));
            Parent root = loader.load();
            Info_ClienteController infcc=loader.getController();
            infcc.setInfo(cliente);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
