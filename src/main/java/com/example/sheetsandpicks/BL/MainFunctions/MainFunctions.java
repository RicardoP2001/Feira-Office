package com.example.sheetsandpicks.BL.MainFunctions;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.Controllers.Users.*;
import com.example.sheetsandpicks.Controllers.app_fornecedor_movimentosController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Classe que contém funções principais relacionadas à manipulação da interface do utilizador.
 */
public class MainFunctions {

    /**
     * Carrega o conteúdo do arquivo FXML na janela principal, substituindo o conteúdo existente,
     * conforme o tipo de utilizador especifico.
     *
     * @param fxmlFile O caminho do arquivo FXML a ser carregado.
     * @param userType O tipo de utilizador que determinará qual o controlador FXML a ser utilizado.
     * @return O conteúdo (Node) carregado do arquivo FXML.
     */
    public static Node carregarConteudo(String fxmlFile, String userType) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFunctions.class.getResource("/" + fxmlFile));
            Node conteudo = loader.load();
            switch (userType) {
                case "Fornecedor" -> {
                    SuppliersController userController = loader.getController();
                    boolean sepa=false;
                    userController.setFuncionario(userType,sepa);
                }
                case "Operador" -> {
                    OperatorsController userController = loader.getController();
                    userController.setFuncionario(userType);
                }
                case "Admin" -> {
                    AdminsController userController = loader.getController();
                    userController.setFuncionario(userType);
                }
                case "Cliente" -> {
                    ClientsController userController = loader.getController();
                    userController.setFuncionario(userType);
                }
                case "Sepa" ->{
                    SuppliersController userController = loader.getController();
                    boolean sepa=true;
                    userController.setFuncionario(userType,sepa);
                }
            }
            return conteudo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Carrega o conteúdo de um arquivo FXML na janela principal, substituindo o conteúdo existente.
     *
     * @param fxmlFile O caminho do arquivo FXML a ser carregado.
     * @return O conteúdo (Node) carregado do arquivo FXML.
     * @throws RuntimeException Se ocorrer um erro de IO durante o carregamento do arquivo FXML.
     */
    public static Node CarregarConteudo2(String fxmlFile){
        try {
            FXMLLoader loader = new FXMLLoader(MainFunctions.class.getResource("/"+fxmlFile));
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Carrega o conteúdo de um arquivo FXML relacionado a stock na janela principal,
     * substituindo o conteúdo existente.
     *
     * @param fxmlFile O caminho do arquivo FXML a ser carregado.
     * @param userType O tipo de utilizador que determinará qual o controlador FXML a ser utilizado.
     * @return O conteúdo (Node) carregado do arquivo FXML.
     */
    public static Node carregarConteudoStock(String fxmlFile, String userType) {
        try {
            FXMLLoader loader = new FXMLLoader(MainFunctions.class.getResource("/" + fxmlFile));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Carrega o conteúdo de um arquivo FXML relacionado a fornecedor na janela principal.
     *
     * @param fxml      O caminho do arquivo FXML a ser carregado.
     * @param usertype  O tipo de utilizador que determinará qual o controlador FXML a ser utilizado.
     * @return O conteúdo (Node) carregado do arquivo FXML.
     */
    public static Node carregarConteudoFornecedor(String fxml, String usertype){
        try {
            FXMLLoader loader = new FXMLLoader(MainFunctions.class.getResource("/" + fxml));
            Node conteudo = null;
            conteudo = loader.load();
            app_fornecedor_movimentosController userController = loader.getController();
            String cod_fornecedor = CodSingleton.getInstance().getCod();
            String nome = null;

            nome = DBstatements.getnome(cod_fornecedor);

            userController.setFornecedor(cod_fornecedor, nome);
            return conteudo;
        }catch(SQLException|IOException e){
            System.out.println("Erro!"+e.getMessage());
        }
        return null;
    }
}
