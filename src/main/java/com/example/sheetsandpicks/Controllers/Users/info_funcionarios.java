package com.example.sheetsandpicks.Controllers.Users;

import com.example.sheetsandpicks.BL.Users.UsersFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class info_funcionarios {

    @FXML
    private Button btn_ver_movimentos;

    @FXML
    private ImageView img;

    @FXML
    private Label lb_Cidade;

    @FXML
    private Label lb_Endereco;

    @FXML
    private Label lb_Endereco2;

    @FXML
    private Label lb_cod_fornecedor;

    @FXML
    private Label lb_cod_fornecedor_name;

    @FXML
    private Label lb_Nome;

    @FXML
    private Label lb_Pais;

    @FXML
    private Label lb_Telefone;

    @FXML
    private Label lb_cod_postal;

    @FXML
    private Label lb_data_nascimento;

    @FXML
    private Label lb_email;

    @FXML
    private Label lb_tipo_funcionario;

    private String funcionario;

    private Integer idear;

    public void setFuncionario(String Funcionario){
        lb_tipo_funcionario.setText(Funcionario);
        this.funcionario = Funcionario;
        if(!(lb_tipo_funcionario.getText().equals("Fornecedor"))){
            btn_ver_movimentos.setVisible(false);
            lb_cod_fornecedor.setVisible(false);
            lb_cod_fornecedor_name.setVisible(false);
        }
    }

    @FXML
    void Handler_ver_movimentos(ActionEvent event) {
        String cod = lb_cod_fornecedor.getText();
        String nome = lb_Nome.getText();

        UsersFunctions.openAppDashMovimentosUtilizador(cod,nome);
    }

    public void setdata(Integer id, String nome, String endereco, String pais,String endereco2, String cidade, Integer telefone, String email, String codPostal, String dataNascimento,String cod_fornecedor) throws SQLException {
        idear =id;
        lb_Nome.setText(nome);
        lb_Endereco.setText(endereco);
        lb_Endereco2.setText(endereco2);
        lb_Cidade.setText(cidade);
        lb_Pais.setText(pais);
        lb_Telefone.setText(String.valueOf(telefone));
        lb_email.setText(email);
        lb_cod_postal.setText(codPostal);
        lb_data_nascimento.setText(dataNascimento);
        if(lb_tipo_funcionario.getText().equals("Fornecedor") || lb_tipo_funcionario.getText().equals("Sepa")) {
            lb_cod_fornecedor.setText(cod_fornecedor);
        }
    }


}

