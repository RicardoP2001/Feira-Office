package com.example.sheetsandpicks.Controllers.app_login;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.Controllers.MainController;
import com.example.sheetsandpicks.Controllers.Users.CodSingleton;
import com.example.sheetsandpicks.Controllers.Users.UserSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controlador responsável pela interface de login.
 */
public class loginController implements Initializable {

    @FXML
    private Button butLogin;

    @FXML
    private PasswordField passField;

    @FXML
    private Button btn_return;

    @FXML
    private Button btn_Cliente;

    @FXML
    private Button btn_Funcionario;

    @FXML
    private Hyperlink href_Register;

    @FXML
    private TextField txtUtilizador;

    @FXML
    private StackPane pane;

    /**
     * Manipula o evento de pressionar o botão. Fecha a janela atual e abre o menu principal se a tecla Enter for pressionada.
     */
    @FXML
    void onPress() {
        pane.setOnKeyPressed(event -> {
            KeyCombination keyCombination = KeyCombination.keyCombination("Ctrl+ENTER");
            if (keyCombination.match(event) ) {
                String nivelPermissao;
                try {
                    nivelPermissao = DBstatements.getNivelAcesso("admin");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                mostrarMainViewComNivelPermissao(nivelPermissao);
                fecharJanelaAtual();
            }
            if (event.getCode() == KeyCode.ENTER && !keyCombination.match(event)) {
                String user = txtUtilizador.getText();
                String password = passField.getText();
                try {
                    String nome = DBstatements.getnome(user);
                    String cod = DBstatements.getcodigo(user);
                    UserSingleton.getInstance().setUsername(nome);
                    CodSingleton.getInstance().setCod(cod);
                    boolean isValidLogin;
                    isValidLogin = DBstatements.verifyLogin(user, password);
                    if (isValidLogin) {
                        String nivelPermissao = DBstatements.getNivelAcesso(user);
                        mostrarMainViewComNivelPermissao(nivelPermissao);
                    } else {
                        showLoginFailedAlert();
                        txtUtilizador.setText("");
                        passField.setText("");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Manipula o evento de clique no botão de login.
     * Obtém as informações de login do utilizador, verifica a validade das credenciais e,
     * se válidas, exibe a tela principal com base no nível de permissão do utilizador.
     *
     * @param ignoredEvent O evento de clique no botão de login.
     * @throws SQLException Se ocorrer uma exceção ao acessar a base de dados.
     */
    @FXML
    void fazerLogin(ActionEvent ignoredEvent) throws SQLException {
        String username = txtUtilizador.getText();
        String password = passField.getText();
        String nome = DBstatements.getnome(username);
        String cod = DBstatements.getcodigo(username);
        System.out.println(nome);
        System.out.println(cod);
        UserSingleton.getInstance().setUsername(nome);
        CodSingleton.getInstance().setCod(cod);
        boolean isValidLogin = DBstatements.verifyLogin(username, password);
        if (isValidLogin) {
            String nivelPermissao = DBstatements.getNivelAcesso(username);
            mostrarMainViewComNivelPermissao(nivelPermissao);
        } else {
            txtUtilizador.setText("");
            passField.setText("");
            handlerErrado("Dados Incorretos");
        }
    }

    /**
     * Manipula a ação errada exibindo uma caixa de diálogo de alerta.
     *
     * @param mensagem A mensagem a ser exibida na caixa de diálogo de alerta.
     */
    public static void handlerErrado(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Fecha a janela atual.
     * Obtém a referência da janela atual através de um controlo na cena
     * e fecha a janela ao utilizar o método close().
     */
    private void fecharJanelaAtual() {
        Stage janelaLogin = (Stage) butLogin.getScene().getWindow();
        janelaLogin.close();
    }

    /**
     * Exibe a página principal com base no nível de permissão do utilizador.
     * Carrega o arquivo FXML da página principal, obtém o controlador da página principal,
     * passa o nível de permissão para o controlador e configura e exibe o palco (Stage).
     * Por fim, fecha a janela de login atual.
     *
     * @param nivelPermissao O nível de permissão do utilizador.
     */
    private void mostrarMainViewComNivelPermissao(String nivelPermissao) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sheetsandpicks/Dashboard/main-view.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println("ERRO!");
            e.printStackTrace();
            return;
        }
        MainController mainController = loader.getController();
        mainController.atualizarComboUti(nivelPermissao);
        System.out.println(nivelPermissao);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Feira&Office - APP");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        fecharJanelaAtual();
    }
    
    /**
     * Método chamado automaticamente durante a inicialização do controlador.
     * Inicializa a lógica necessária quando a interface gráfica associada a este controlador é carregada.
     * Neste caso, chama o método onPress() para realizar ações específicas na inicialização.
     *
     * @param location   A localização relativa do FXML especificado no método FXMLLoader.load().
     * @param resources  Os recursos específicos do local que podem ser usados para internacionalização.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pane.setDisable(false);
        onPress();
    }

    /**
     * Exibe uma caixa de diálogo de alerta para um login inválido.
     */
    private void showLoginFailedAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText("Utilizador ou password incorreto. Por favor tente novamente!");

        alert.showAndWait();
    }

    /**
     * Manipula o evento de clique no botão de retorno.
     * Carrega a tela de seleção do aplicativo.
     *
     * @param event O evento associado à ação.
     */
    @FXML
    void HandlerReturn(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/Geral/app_select.fxml"));
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
        Stage stage = (Stage) butLogin.getScene().getWindow();
        stage.close();

    }
}
