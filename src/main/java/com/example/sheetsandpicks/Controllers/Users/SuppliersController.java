    package com.example.sheetsandpicks.Controllers.Users;

    import com.example.sheetsandpicks.BL.Database.DBstatements;
    import com.example.sheetsandpicks.BL.Users.UsersFunctions;
    import com.example.sheetsandpicks.Controllers.Pagamento.PageCloseObserver;
    import com.example.sheetsandpicks.Models.Suppliers;
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

    public class SuppliersController implements PageCloseObserver {

    @FXML
    private Button butAdicionar;

    @FXML
    private Button butAlterar;

    @FXML
    private Button butRemover;

    @FXML
    private Label labelFuncionario;
    @FXML
    private TextField pesquisarfornecedores;

    @FXML
    private TableView<Suppliers> tableView;

    @FXML
    private TableColumn<Suppliers, String> nomeColumn;

        @FXML
        private TableColumn<Suppliers, String> emailColumn;

        @FXML
        private TableColumn<Suppliers, String> enderecoColumn;

        @FXML
        private TableColumn<Suppliers, String> telefoneColumn;

        @FXML
        private TableColumn<Suppliers, String> cod_postalColumn;

        private boolean sepa=false;

        ObservableList<Suppliers> observableSuppliersList = FXCollections.observableArrayList();

        /**
         * Define o tipo de funcionário associado a este controlador.
         *
         * @param Funcionario O tipo de funcionário a ser associado a este controlador.
         */
    public void setFuncionario(String Funcionario,boolean sepa) {
        if(sepa){
            this.sepa= true;
            butAdicionar.setManaged(false);
            butAlterar.setManaged(false);
            butRemover.setManaged(false);
        }
        labelFuncionario.setText(Funcionario);

        initialize();

    }

        /**
         * Manipula o evento de pressionar a tecla Enter na caixa de texto.
         * Chama o método `Enter` para realizar a ação associada à tecla Enter neste contexto.
         */
    @FXML
    void textboxPress() {
        Enter();
    }

        /**
         * Método chamado automaticamente durante a inicialização do controlador FXML.
         * Inicializa a tabela de fornecedores, configura as colunas da tabela, busca os fornecedores da base de dados,
         * e popula a tabela com os dados dos fornecedores. Também configura eventos de clique na tabela.
         */
    @FXML
    public void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
        cod_postalColumn.setCellValueFactory(new PropertyValueFactory<>("cod_postal"));
        enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        List<Suppliers> fornecedores;
        if(!sepa) {
            fornecedores = DBstatements.buscarFornecedores();
        }else{
            fornecedores= DBstatements.buscarFornecedoresSepa();
        }
        tableView.setItems(FXCollections.observableArrayList(fornecedores));

        selectrow();
    }

        /**
         * Manipula o evento de alteração de fornecedor.
         * Obtém os detalhes do fornecedor selecionado na tabela, incluindo o ID do fornecedor, e chama o método para abrir
         * a janela de atualização de fornecedores com esses detalhes.
         *
         * @param ignoredEvent O evento de ação associado ao clique no botão de alteração de fornecedor.
         * @throws SQLException Se ocorrer uma exceção durante a execução de operações SQL ao obter dados do fornecedor.
         */
    @FXML
    void alterar_uti(ActionEvent ignoredEvent) throws SQLException {
        Suppliers suppliers = tableView.getSelectionModel().getSelectedItem();

        Integer id = suppliers.getId();
        String pais = String.valueOf(DBstatements.getPaisid(id));
        String nome = suppliers.getNome();
        String endereco = suppliers.getEndereco();
        String endereco2 = suppliers.getEndereco2();
        String cidade = suppliers.getCidade();
        Integer telefone = suppliers.getTelefone();
        String email = suppliers.getEmail();
        String cod_postal = suppliers.getCod_postal();
        Date data_nascimento = suppliers.getData_nascimento();
        String iban = suppliers.getIban();
        String bic = suppliers.getBic();

        UsersFunctions.openAppDashUpdateUtilizadores_sup(labelFuncionario.getText(),id,nome,endereco,pais ,endereco2,cidade,telefone,email,cod_postal, String.valueOf(data_nascimento),this, iban, bic);
    }

    void abrir_uti(Suppliers selected) throws SQLException {
        if(!sepa) {
            int id = selected.getId();
            String pais = String.valueOf(DBstatements.getPaisid(id));
            String nome = selected.getNome();
            String endereco = selected.getEndereco();
            String endereco2 = selected.getEndereco2();
            String cidade = selected.getCidade();
            int telefone = selected.getTelefone();
            String email = selected.getEmail();
            String cod_postal = selected.getCod_postal();
            Date data_nascimento = selected.getData_nascimento();
            String cod_fornecedor = selected.getCod_fornecedor();

            UsersFunctions.openAppDashInfoUtilizadores(labelFuncionario.getText(), id, nome, endereco, pais, endereco2, cidade, telefone, email, cod_postal, String.valueOf(data_nascimento), cod_fornecedor);
        }else{
            String cod_fornecedor = selected.getCod_fornecedor();
            String nome = selected.getNome();

            UsersFunctions.openSepaLista(cod_fornecedor, nome);

        }
    }

        /**
         * Manipula o evento de criação de um novo utilizador/fornecedor.
         * Chama o método para abrir a janela de criação de utilizadores/fornecedores, passando o tipo de funcionário associado.
         *
         * @param ignoredEvent O evento de ação associado ao clique no botão de criação de utilizadores/fornecedores.
         */
    @FXML
    void criar_uti(ActionEvent ignoredEvent) {
        UsersFunctions.openAppDashUtilizadores_sup(labelFuncionario.getText(),this);
    }

        /**
         * Manipula o evento de remoção de um utilizador/fornecedor.
         * Obtém o fornecedor selecionado na tabela, extrai o ID do fornecedor e chama o método para exibir uma pergunta de confirmação.
         *
         * @param ignoredEvent O evento de ação associado ao clique no botão de remoção de utilizador/fornecedor.
         * @throws SQLException Se ocorrer uma exceção durante a execução de operações SQL ao obter o ID do fornecedor.
         */
    @FXML
    void remover_uti(ActionEvent ignoredEvent) throws SQLException {
            Suppliers suppliers = tableView.getSelectionModel().getSelectedItem();
            Integer id = suppliers.getId();
            HandlerPergunta(id);
            updatetable();
    }

        /**
         * Manipula o evento de pergunta de confirmação para remover um fornecedor.
         * Exibe um diálogo de confirmação perguntando se o utilizador deseja remover o fornecedor com base no ID fornecido.
         * Se o utilizador confirmar, chama o método para remover o fornecedor da base de dados e exibe uma mensagem de confirmação.
         *
         * @param id O ID do fornecedor a ser removido.
         * @throws SQLException Se ocorrer uma exceção durante a execução de operações SQL ao remover o fornecedor.
         */
        private void HandlerPergunta(Integer id) throws SQLException {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Tem a certeza?");
            a.setContentText("QUER REMOVER o FORNECEDOR?");
            Optional<ButtonType> option = a.showAndWait();
            if (option.isPresent()) {
                if (option.get() == ButtonType.OK) {
                    DBstatements.Eliminar_Funcionario(id);
                    HandlerConfirmado();
                }
            }
        }

        /**
         * Manipula o evento de confirmação bem-sucedida após a remoção de um fornecedor.
         * Exibe um diálogo de informação indicando que o fornecedor foi removido com sucesso.
         */
        public void HandlerConfirmado() {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("SUCESSO");
            a.setContentText("O FORNECEDOR foi removido com sucesso");
        }

        public void Enter(){
            observableSuppliersList = tableView.getItems();

            FilteredList<Suppliers> filteredList = new FilteredList<>(observableSuppliersList, b -> true);
            pesquisarfornecedores.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(suppliers -> {
                    if (newValue.isEmpty() || newValue.isBlank()) {
                        return true;
                    }
                    String palavra = newValue.toLowerCase();

                    return suppliers.getCod_postal().toLowerCase().contains(palavra)
                            || suppliers.getNome().toLowerCase().contains(palavra)
                            || String.valueOf(suppliers.getEndereco()).toLowerCase().contains(palavra)
                            || String.valueOf(suppliers.getEndereco2()).toLowerCase().contains(palavra)
                            || String.valueOf(suppliers.getCidade()).toLowerCase().contains(palavra)
                            || suppliers.getTelefone().toString().toLowerCase().contains(palavra)
                            || suppliers.getEmail().toLowerCase().contains(palavra)
                            || suppliers.getData_nascimento().toString().toLowerCase().contains(palavra);
                });

            });
            SortedList<Suppliers> sortedData = new SortedList<>(filteredList);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        }

        private void selectrow(){
            tableView.setOnMouseClicked(event -> {
                if(event.getButton()== MouseButton.PRIMARY && event.getClickCount() == 2){
                    Suppliers suppliers = tableView.getSelectionModel().getSelectedItem();
                    if(suppliers!=null){
                        try {
                            abrir_uti(suppliers);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }

        public void updatetable() {
            nomeColumn.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
            cod_postalColumn.setCellValueFactory(new PropertyValueFactory<>("cod_postal"));
            enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
            telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("Telefone"));

            List<Suppliers> fornecedores = DBstatements.buscarFornecedores();

            tableView.setItems(FXCollections.observableArrayList(fornecedores));
        }

        @Override
        public void onPageClose() {
            tableView.setItems(FXCollections.observableArrayList(DBstatements.buscarFornecedoresSepa()));

        }
    }
