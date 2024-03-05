package com.example.sheetsandpicks.Controllers.app_dashboard;

import com.example.util.Models.Cliente;
import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.Controllers.Users.ClientsController;
import com.example.util.APICliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.util.Pedidos.HandlerErrado;
/**
 * Controlador para a interface de criação de clientes no painel de controle da aplicação.
 */
public class app_dash_criarCliente implements Initializable {

    @FXML
    private Button btn_Cancelar;

    @FXML
    private Button btn_adicionar;

    @FXML
    private ComboBox<String> cmb_pais;

    @FXML
    private Label lb_titulo;

    @FXML
    private Label lbl_cod_fornecedor2;

    @FXML
    private Label lblpassword;

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
    private TextField txt_password;

    private ClientsController clientsController;
    /**
     * Manipulador de evento para o botão "Adicionar". Cria um novo cliente com base nos dados fornecidos e
     * adiciona-o à base de dados. Fecha a janela de criação de cliente se bem-sucedido, exibe uma mensagem de erro caso contrário.
     *
     * @param event O evento associado ao clique no botão "Adicionar".
     */
    @FXML
    void HandlerAdicionar(ActionEvent event) {
        String id="";
        boolean active=false;
        Cliente cliente=new Cliente(id,txt_nome.getText(),txt_email.getText(),txt_endereco.getText(),txt_endereco2.getText(),txt_cod_postal.getText(),txt_cidade.getText(),cmb_pais.getValue(),txt_NIF.getText(),active,txt_password.getText());
        if(APICliente.CriarCliente(cliente)) {
            //clientsController.updatetable();
            Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
            stage.close();
        }else{
            HandlerErrado("Erro na Criação do Cliente");
        }
    }
    /**
     * Manipulador de evento para o botão "Cancelar". Exibe uma caixa de diálogo de confirmação e fecha a janela de criação de cliente se confirmado.
     *
     * @param event O evento associado ao clique no botão "Cancelar".
     */
    @FXML
    void HandlerCancelar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText("Confirmação de ação");
        a.setContentText("Deseja mesmo sair da Criação do Cliente ?");

        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {

            if (option.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
                stage.close();
            }
        }
    }
    /**
     * Preenche o ComboBox com a lista de países disponíveis.
     */
    private void Encher_cmb(){
        try {
            cmb_pais.setItems(DBstatements.getPais());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Método chamado durante a inicialização do controlador para preencher o ComboBox com países disponíveis.
     *
     * @param url            A URL relativa ao local do arquivo FXML.
     * @param resourceBundle O ResourceBundle que pode ser usado para localizar chaves específicas em um arquivo de propriedades.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Encher_cmb();
    }
}
