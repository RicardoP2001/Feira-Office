package com.example.Feira_and_Office.Controller.app_login;

import com.example.Feira_and_Office.BL.Login.LoginFunctions;
import com.example.Feira_and_Office.Controller.SingletoneCliente;
import com.example.util.APICliente;
import com.example.util.Models.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.util.Pedidos.HandlerErrado;

/**
 * Controlador responsável pela lógica da interface de login.
 */
public class LoginController implements Initializable {

    @FXML
    private Button btn_Cliente;

    @FXML
    private Button btn_return;

    @FXML
    private Button btn_Funcionario;

    @FXML
    private Button butLogin;

    @FXML
    private Hyperlink href_register;

    @FXML
    private StackPane pane;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField txtUtilizador;

     /**
     * Método chamado quando o botão de registo é acionado.
     * Navega para o ecrã de registo.
     *
     * @param event O evento de clique do botão.
     */
    @FXML
    void SendToRegister(ActionEvent event) {
        LoginFunctions.Abrirpanes("com/example/Feira_And_Ofiice/Login/app_register.fxml");
        fecharJanelaAtual();
    }

    /**
     * Método chamado quando o botão de login é acionado.
     * Realiza a tentativa de login do cliente.
     *
     * @param ignoredEvent O evento de clique do botão.
     */
    @FXML
    void fazerLogin(ActionEvent ignoredEvent) {
            Cliente cliente=APICliente.LoginCliente(txtUtilizador.getText(),passField.getText());
        if((cliente.getName()!=null) && butLogin.getScene().getWindow().isShowing()){
            SingletoneCliente.setId(cliente.getId());
            LoginFunctions.MandarParaaMain(cliente);
            fecharJanelaAtual();
        }else{
            HandlerErrado("Falhou");
        }
    }

    /**
     * Método chamado quando o botão de retorno é acionado.
     * Navega de volta para o ecrã de seleção geral.
     *
     * @param event O evento de clique do botão.
     */
    @FXML
    void HandlerReturn(ActionEvent event) {
        LoginFunctions.Abrirpanes("com/example/Geral/app_select.fxml");
        fecharJanelaAtual();
    }

    private void fecharJanelaAtual() {
        Stage janelaLogin = (Stage) butLogin.getScene().getWindow();
        janelaLogin.close();
    }

    void onPress(){
        pane.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode()== KeyCode.ENTER){
                fazerLogin(null);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onPress();
    }
}
