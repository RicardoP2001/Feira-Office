package com.example.sheetsandpicks.Controllers.Users;

import com.example.util.Models.Cliente;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Info_ClienteController {

    @FXML
    private Label lb_Ativo;

    @FXML
    private Label lb_Cidade;

    @FXML
    private Label lb_Endereco;

    @FXML
    private Label lb_Endereco2;

    @FXML
    private Label lb_NIF;

    @FXML
    private Label lb_Nome;

    @FXML
    private Label lb_Pais;

    @FXML
    private Label lb_cod_postal;

    @FXML
    private Label lb_email;

    public void setInfo(Cliente cliente){
        lb_Nome.setText(cliente.getName());
        lb_Endereco.setText(cliente.getAddress1());
        lb_Endereco2.setText(cliente.getAddress2());
        lb_NIF.setText(cliente.getTaxIdentificationNumber());
        lb_cod_postal.setText(cliente.getPostalCode());
        lb_Cidade.setText(cliente.getCity());
        lb_Pais.setText(cliente.getCountry());
        lb_email.setText(cliente.getEmail());
        if(cliente.isActive()){
            lb_Ativo.setText("Ativo");
        }else{
            lb_Ativo.setText("Não Ativo");
        }
    }
}
