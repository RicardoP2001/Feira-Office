package com.example.sheetsandpicks.Controllers.Users;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.BL.Users.UsersFunctions;
import com.example.sheetsandpicks.Models.Admins;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AdminsController {

    @FXML
    private Button butAdicionar;

    @FXML
    private Button butAlterar;

    @FXML
    private Button butRemover;

    @FXML
    private Label labelFuncionario;

    @FXML
    private String tipo;

    @FXML
    private TableView<Admins> tableView;

    @FXML
    private TableColumn<Admins, String> nomeColumn;

    @FXML
    private TableColumn<Admins, String> emailColumn;

    @FXML
    private TableColumn<Admins, String> enderecoColumn;

    @FXML
    private TextField pesquisaradmins;

    @FXML
    private TableColumn<Admins, String> telefoneColumn;

    @FXML
    private TableColumn<Admins, String> cod_postalColumn;

    ObservableList<Admins> observableAdminsList = FXCollections.observableArrayList();

    /**
     * Define o tipo de funcionário associado a este controlador.
     *
     * @param Funcionario O tipo de funcionário a ser associado a este controlador.
     */
    public void setFuncionario(String Funcionario) {
        labelFuncionario.setText(Funcionario);
        tipo = Funcionario;
    }

    /**
     * Inicializa a tabela de administradores na interface gráfica.
     * Configura as colunas da tabela e popula-a com dados dos administradores da base de dados.
     * Verifica se a lista de administradores não está vazia antes de adicionar à TableView.
     * Chama o método para configurar eventos de seleção de linha na tabela.
     */
    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        cod_postalColumn.setCellValueFactory(new PropertyValueFactory<>("cod_postal"));
        enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("Telefone"));

        List<Admins> admins = DBstatements.buscarAdmins();

        if (admins == null) {
            admins = DBstatements.buscarAdmins();

            if (admins != null) {
                tableView.setItems(FXCollections.observableArrayList(admins));
            }       else {
                System.out.println("Nenhum fornecedor encontrado ou ocorreu um erro ao buscar os fornecedores.");
            }
        } else {
            tableView.setItems(FXCollections.observableArrayList(admins));
        }
        selectrow();
    }

    public void updatetable(){
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        cod_postalColumn.setCellValueFactory(new PropertyValueFactory<>("cod_postal"));
        enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("Telefone"));

        List<Admins> admins = DBstatements.buscarAdmins();

        if (admins == null) {
            admins = DBstatements.buscarAdmins();

            if (admins != null) {
                tableView.setItems(FXCollections.observableArrayList(admins));
            }       else {
                System.out.println("Nenhum fornecedor encontrado ou ocorreu um erro ao buscar os fornecedores.");
            }
        } else {
            tableView.setItems(FXCollections.observableArrayList(admins));
        }
    }

    /**
     * Manipula o evento de alteração de um administrador selecionado na tabela.
     * Obtém os detalhes do administrador selecionado, como ID, nome, endereço, etc.
     * Abre a interface gráfica para atualização de utilizadores com os detalhes do administrador selecionado.
     *
     * @param event O evento acionado quando o botão de alteração de utilizador é clicado.
     * @throws SQLException Se ocorrer uma exceção SQL ao obter informações adicionais do administrador.
     * @throws RuntimeException Se ocorrer uma exceção durante a abertura da interface gráfica de atualização de utilizadores.
     */
    @FXML
    void alterar_uti(ActionEvent event) throws SQLException {
        Admins admins = tableView.getSelectionModel().getSelectedItem();

        Integer id = admins.getId();
        String pais = String.valueOf(DBstatements.getPaisid(id));
        String nome = admins.getNome();
        String endereco = admins.getEndereco();
        String endereco2 = admins.getEndereco2();
        String cidade = admins.getCidade();
        Integer telefone = Integer.valueOf(admins.getTelefone());
        String email = admins.getEmail();
        String cod_postal = admins.getCod_postal();
        Date data_nascimento = admins.getData_nascimento();
        String bic = admins.getBic();
        String iban = admins.getIban();

        UsersFunctions.openAppDashUpdateUtilizadores_Admins(labelFuncionario.getText(),id,nome,endereco,pais ,endereco2,cidade,telefone,email,cod_postal, String.valueOf(data_nascimento),this, iban, bic);
    }

    void abrir_uti(Admins selected) throws SQLException {

        int id = selected.getId();
        String pais = String.valueOf(DBstatements.getPaisid(id));
        String nome = selected.getNome();
        String endereco = selected.getEndereco();
        String endereco2 = selected.getEndereco2();
        String cidade = selected.getCidade();
        int telefone = Integer.valueOf(selected.getTelefone());
        String email = selected.getEmail();
        String cod_postal = selected.getCod_postal();
        Date data_nascimento = selected.getData_nascimento();
        String cod_fornecedor=null;


        UsersFunctions.openAppDashInfoUtilizadores(labelFuncionario.getText(), id,nome,endereco,pais,endereco2,cidade,telefone,email,cod_postal, String.valueOf(data_nascimento),cod_fornecedor);
    }

    /**
     * Manipula o evento de criação de um novo utilizador/Administrador.
     * Chama o método para abrir a janela de criação de utilizadores/Administradores, passando o tipo de funcionário associado.
     *
     * @param event O evento de ação associado ao clique no botão de criação de utilizadores/Administradores.
     */
    @FXML
    void criar_uti(ActionEvent event) {
        UsersFunctions.openAppDashUtilizadores_admins(labelFuncionario.getText(),this);
    }

    /**
     * Manipula o evento de remoção de um utilizador/Administrador.
     * Obtém o Administrador selecionado na tabela, extrai o ID do Administrador e chama o método para exibir uma pergunta de confirmação.
     *
     * @param event O evento de ação associado ao clique no botão de remoção de utilizador/Administrador.
     * @throws SQLException Se ocorrer uma exceção durante a execução de operações SQL ao obter o ID do Administrador.
     */
    @FXML
    void remover_uti(ActionEvent event) throws SQLException {

        Admins admins = tableView.getSelectionModel().getSelectedItem();
        Integer id = admins.getId();
        HandlerPergunta(id);
        updatetable();
    }

    /**
     * Manipula o evento de pergunta de confirmação para remover um Administrador.
     * Exibe um diálogo de confirmação perguntando se o utilizador deseja remover o Administrador com base no ID fornecido.
     * Se o utilizador confirmar, chama o método para remover o Administrador da base de dados e exibe uma mensagem de confirmação.
     *
     * @param id O ID do Administrador a ser removido.
     * @throws SQLException Se ocorrer uma exceção durante a execução de operações SQL ao remover o Administrador.
     */
    private void HandlerPergunta(Integer id) throws SQLException {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Tem a certeza?");
            a.setContentText("QUER REMOVER o ADMIN?");
            Optional<ButtonType> option = a.showAndWait();
            if (option.isPresent()) {
                if (option.get() == ButtonType.OK) {
                    DBstatements.Eliminar_Funcionario(id);
                    HandlerConfirmado();
                } else {

                }
            }
    }

    /**
     * Manipula o evento de confirmação bem-sucedida após a remoção de um Administrador.
     * Exibe um diálogo de informação indicando que o Administrador foi removido com sucesso.
     */
    public void HandlerConfirmado() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("SUCESSO");
        a.setContentText("O ADMIN foi removido com sucesso");
        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {

            }
        }


    }

    public void textboxPress() {
        Enter();
    }

    /**
     * Configura a funcionalidade de pesquisa dinâmica na tabela de Administradores.
     * Filtra os Administradores com base no texto inserido na caixa de pesquisa, considerando várias colunas.
     * Os resultados filtrados são exibidos na tabela.
     */
    private void Enter() {
        observableAdminsList = tableView.getItems();

        FilteredList<Admins> filteredList = new FilteredList<>(observableAdminsList, b -> true);
        pesquisaradmins.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(operators -> {
                if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                    return true;
                }
                String palavra = newValue.toLowerCase();

                return operators.getCod_postal().toLowerCase().contains(palavra)
                        || operators.getNome().toLowerCase().contains(palavra)
                        || String.valueOf(operators.getEndereco()).toLowerCase().contains(palavra)
                        || String.valueOf(operators.getEndereco2()).toLowerCase().contains(palavra)
                        || String.valueOf(operators.getCidade()).toLowerCase().contains(palavra)
                        || operators.getTelefone().toString().toLowerCase().contains(palavra)
                        || operators.getEmail().toLowerCase().contains(palavra)
                        || operators.getData_nascimento().toString().toLowerCase().contains(palavra);
            });

        });
        SortedList<Admins> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
    private void selectrow(){
        tableView.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.PRIMARY && event.getClickCount() == 2){
                Admins admins = tableView.getSelectionModel().getSelectedItem();
                if(admins!=null){
                    try {
                        abrir_uti(admins);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
