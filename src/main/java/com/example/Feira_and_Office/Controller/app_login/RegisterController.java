package com.example.Feira_and_Office.Controller.app_login;

import com.example.Feira_and_Office.BL.Login.LoginFunctions;
import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.util.Models.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.example.util.APICliente.CriarCliente;
import static com.example.util.Pedidos.HandlerConfirmado;
import static com.example.util.Pedidos.HandlerErrado;

/**
 * Controlador responsável pela lógica da interface de registo.
 */
public class RegisterController implements Initializable {

    @FXML
    private ComboBox<String> cmb_pais;

    @FXML
    private HBox pane;

    @FXML
    private TextField txt_NIF;

    @FXML
    private TextField txt_cidade;

    @FXML
    private TextField txt_cod_postal;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_endereco;

    @FXML
    private TextField txt_endereco2;

    @FXML
    private TextField txt_nome;

    @FXML
    private PasswordField passField;

    /**
     * Método chamado quando o botão de registo é acionado.
     * Cria um novo cliente com as informações fornecidas e tenta realizar o registo.
     */
    @FXML
    void fazerRegister() {
        String id = "";
        boolean active = false;
        Cliente cliente = new Cliente(id, txt_nome.getText(), txt_email.getText(), txt_endereco.getText(), txt_endereco2.getText(), txt_cod_postal.getText(), txt_cidade.getText(), cmb_pais.getValue(), txt_NIF.getText(), active, passField.getText());
        if (CriarCliente(cliente)) {
            SendToLogin();
           HandlerConfirmado();
        }
        else{
            HandlerErrado("Cliente não foi criado com sucesso");
        }
    }

    /**
     * Método chamado para navegar de volta para o ecrã de login.
     */
    @FXML
    void SendToLogin() {
        LoginFunctions.Abrirpanes("com/example/Feira_And_Ofiice/Login/app_login.fxml");
        fecharJanelaAtual();
    }

    /**
     * Inicializa o controlador, preenchendo o ComboBox com a lista de países.
     *
     * @param url            O URL de localização para encontrar o arquivo de recurso.
     * @param resourceBundle O ResourceBundle contendo as localizações específicas do utilizador.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Encher_cmb();
    }

    /**
     * Preenche o ComboBox de países com os dados provenientes da base de dados.
     */
    private void Encher_cmb() {
        try {
            cmb_pais.setItems(DBstatements.getPais());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void fecharJanelaAtual() {
        Stage janelaLogin = (Stage) txt_cidade.getScene().getWindow();
        janelaLogin.close();
    }
}
