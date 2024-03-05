package com.example.sheetsandpicks.Controllers.Users;

import com.example.util.Models.Cliente;
import com.example.sheetsandpicks.api.APICliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

import java.util.List;

import static com.example.sheetsandpicks.api.APICliente.AbrirCliente;
import static com.example.util.APICliente.*;

public class ClientsController {

    @FXML
    private Button butAdicionar;

    @FXML
    private Button butAlterar;

    @FXML
    private Button butRemover;

    @FXML
    private TableColumn<Cliente, String> cod_postalColumn;

    @FXML
    private TableColumn<Cliente, String> emailColumn;

    @FXML
    private TableColumn<Cliente, String> enderecoColumn;

    @FXML
    private Label labelFuncionario;

    @FXML
    private TableColumn<Cliente, String> nifColumn;

    @FXML
    private TableColumn<Cliente, String> nomeColumn;

    @FXML
    private TextField pesquisarfornecedores;

    @FXML
    private TableView<Cliente> tableView;
    @FXML
    private String tipo;

    ObservableList<Cliente> observableClientesList = FXCollections.observableArrayList();


    public void setFuncionario(String Funcionario) {
        labelFuncionario.setText(Funcionario);
        tipo = Funcionario;
        EncherCliente();
        SelectedRow();
    }
    @FXML
    void alterar_uti(ActionEvent event) {
        Cliente cliente = tableView.getSelectionModel().getSelectedItem();
        if(cliente!=null){
            ClientsController controller = new ClientsController();
            AbrirCliente(cliente,controller);
        }
    }

    @FXML
    void criar_uti(ActionEvent event) {
        APICliente.Abrir_criarCliente();
    }

    @FXML
    void remover_uti(ActionEvent event) {
        Cliente cliente = tableView.getSelectionModel().getSelectedItem();
        if(cliente!=null){
            ApagarCliente(cliente.getId());
        }
    }

    @FXML
    void textboxPress(KeyEvent event) {
        Enter();
    }

    public void EncherCliente(){
        List<Cliente> clientes = BuscarCliente();

        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cod_postalColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("address1"));
        nifColumn.setCellValueFactory(new PropertyValueFactory<>("taxIdentificationNumber"));

        tableView.setItems(FXCollections.observableArrayList(clientes));
    }

    public void SelectedRow(){
        tableView.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount()==2){
                Cliente cliente = tableView.getSelectionModel().getSelectedItem();
                if(cliente!=null){
                    APICliente.Abrir_infoCliente(cliente);
                }
            }
        });
    }

    public void Enter(){
        observableClientesList = tableView.getItems();

        FilteredList<Cliente> filteredList = new FilteredList<>(observableClientesList, b -> true);
        pesquisarfornecedores.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(cliente -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String palavra = newValue.toLowerCase();

                return cliente.getPostalCode().toLowerCase().contains(palavra)
                        || cliente.getName().toLowerCase().contains(palavra)
                        || String.valueOf(cliente.getAddress1()).toLowerCase().contains(palavra)
                        || String.valueOf(cliente.getAddress2()).toLowerCase().contains(palavra)
                        || String.valueOf(cliente.getCity()).toLowerCase().contains(palavra)
                        || cliente.getTaxIdentificationNumber().toString().toLowerCase().contains(palavra)
                        || cliente.getEmail().toLowerCase().contains(palavra);
            });

        });
        SortedList<Cliente> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

}
