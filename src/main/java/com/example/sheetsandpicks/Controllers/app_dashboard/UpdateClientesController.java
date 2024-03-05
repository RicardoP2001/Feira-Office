package com.example.sheetsandpicks.Controllers.app_dashboard;

import com.example.sheetsandpicks.Controllers.Users.ClientsController;
import com.example.util.Models.Cliente;
import com.example.util.APICliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Controlador responsável pela interface de atualização de clientes.
 */
public class UpdateClientesController {

    @FXML
    private Button btn_Cancelar;

    @FXML
    private Button btn_adicionar;

    @FXML
    private Label lb_titulo;

    @FXML
    private CheckBox ch_ativar;

    private ClientsController clientsController;

    private String id;

    /**
     * Manipula o evento de alteração do estado de ativação do cliente.
     *
     * @param event O evento associado à ação.
     */
    @FXML
    void HandlerAlterar(ActionEvent event) {
        APICliente.AlterarCliente(id, ch_ativar.isSelected());
        Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
        stage.close();
    }

    /**
     * Manipula o evento de cancelamento da alteração do cliente.
     * Exibe uma mensagem de confirmação e fecha a janela de alteração caso o utilizador confirme a ação.
     *
     * @param event O evento associado à ação.
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
     * Ativa a interface de atualização com as informações do cliente.
     *
     * @param cliente           O cliente a ser atualizado.
     * @param clientsController O controlador de clientes associado.
     */
    public void ClienteAtivado(Cliente cliente, ClientsController clientsController) {
        lb_titulo.setText("Cliente: " + cliente.getName());
        this.clientsController = clientsController;
        this.id = cliente.getId();
        ch_ativar.setSelected(cliente.isActive());
    }

}
