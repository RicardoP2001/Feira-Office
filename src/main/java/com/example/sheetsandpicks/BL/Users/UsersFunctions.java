package com.example.sheetsandpicks.BL.Users;

import com.example.sheetsandpicks.Controllers.Pagamento.PageCloseObserver;
import com.example.sheetsandpicks.Controllers.Pagamento.Sepa_ListaController;
import com.example.sheetsandpicks.Controllers.Users.*;
import com.example.sheetsandpicks.Controllers.app_dashboard.app_dash_updateutilizadoresController;
import com.example.sheetsandpicks.Controllers.app_dashboard.app_dash_utilizadoresController;
import com.example.sheetsandpicks.Controllers.app_fornecedor_movimentosController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
/**
 * Classe que contém funções relacionadas às operações envolvendo utilizadores, como abertura de interfaces gráficas,
 * atualização de dados e controle de movimentos.
 */
public class UsersFunctions {
    private static PageCloseObserver observer;
    /**
     * Abre o painel de controle de utilizadores para administradores.
     *
     * @param userType O tipo de utilizador (por exemplo, "Admin").
     * @param adm      O controlador associado aos administradores.
     */
    public static void openAppDashUtilizadores_admins(String userType, AdminsController adm) {
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Users/app_dash_utilizadores.fxml"));
            Parent root = loader.load();
            app_dash_utilizadoresController controller = loader.getController();
            controller.setFuncionario(userType);
            controller.setAdminsController(adm);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Abre a interface gráfica para atualização de utilizadores/administradores.
     *
     * @param text      O tipo de utilizador (por exemplo, "Admin").
     * @param id        O ID do utilizador/administrador a ser atualizado.
     * @param nome      O nome do utilizador/administrador.
     * @param endereco  O endereço do utilizador/administrador.
     * @param pais      O país do utilizador/administrador.
     * @param endereco2 O segundo endereço do utilizador/administrador.
     * @param cidade    A cidade do utilizador/administrador.
     * @param telefone  O número de telefone do utilizador/administrador.
     * @param email     O endereço de e-mail do utilizador/administrador.
     * @param cod_postal O código postal do utilizador/administrador.
     * @param data      A data de nascimento do utilizador/administrador no formato de string.
     * @param iban      O número IBAN associado ao utilizador/administrador.
     * @param bic       O código BIC associado ao utilizador/administrador.
     * @param adm       O controlador associado aos administradores.
     */
    public static void openAppDashUpdateUtilizadores_Admins(String text, Integer id, String nome, String endereco, String pais,String endereco2, String cidade, Integer telefone,String email, String cod_postal, String data,AdminsController adm, String iban, String bic ) {
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Users/app_dash_updateutilizadores.fxml"));
            Parent root = loader.load();
            app_dash_updateutilizadoresController controller = loader.getController();
            controller.setFuncionario(text);
            controller.setdata(id,nome,endereco, pais,endereco2,cidade,telefone,email,cod_postal,data, iban, bic);
            controller.setAdminscontroller(adm);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Abre a janela do painel de controle de utilizadores.
     *
     * @param userType O tipo de utilizador para o qual o painel de controle será aberto.
     *                 Pode ser "Admin", "Fornecedor", "Operador", ou qualquer outro valor desejado.
     *                 Este tipo de utilizador será passado para o controlador do painel de controlo.
     * @throws RuntimeException Se ocorrer uma exceção SQLException durante a execução.
     */
    public static void openAppDashUtilizadores_Operators(String userType, OperatorsController op) {
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Users/app_dash_utilizadores.fxml"));
            Parent root = loader.load();
            app_dash_utilizadoresController controller = loader.getController();
            controller.setFuncionario(userType);
            controller.setOperatorsController(op);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Abre a janela de atualização de utilizadores/Operadores.
     *
     * @param text        O texto associado ao tipo de funcionário (Admin, Fornecedor, Operador).
     * @param id          O ID do utilizador/Operador a ser atualizado.
     * @param nome        O nome do utilizador/Operador.
     * @param endereco    O endereço do utilizador/Operador.
     * @param pais        O país do utilizador/Operador.
     * @param endereco2   O segundo endereço do utilizador/Operador.
     * @param cidade      A cidade do utilizador/Operador.
     * @param telefone    O número de telefone do utilizador/Operador.
     * @param email       O endereço de e-mail do utilizador/Operador.
     * @param cod_postal  O código postal do utilizador/Operador.
     * @param data        A data de nascimento do utilizador/Operador no formato de string.
     * @throws RuntimeException Se ocorrer uma exceção IOException ou SQLException durante a execução.
     */
    public static void openAppDashUpdateUtilizadores_operators(String text, Integer id, String nome, String endereco, String pais,String endereco2, String cidade, Integer telefone,String email, String cod_postal, String data,OperatorsController op, String iban, String bic ) {
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Users/app_dash_updateutilizadores.fxml"));
            Parent root = loader.load();
            app_dash_updateutilizadoresController controller = loader.getController();
            controller.setFuncionario(text);
            controller.setdata(id,nome,endereco, pais,endereco2,cidade,telefone,email,cod_postal,data, iban , bic);
            controller.setOperatorsController(op);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Abre a janela do painel de controle de utilizadores.
     *
     * @param userType O tipo de utilizador para o qual o painel de controle será aberto.
     *                 Pode ser "Admin", "Fornecedor", "Operador", ou qualquer outro valor desejado.
     *                 Este tipo de utilizador será passado para o controlador do painel de controlo.
     * @throws RuntimeException Se ocorrer uma exceção SQLException durante a execução.
     */
    public static void openAppDashUtilizadores_sup(String userType, SuppliersController sup) {
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Users/app_dash_utilizadores.fxml"));
            Parent root = loader.load();
            app_dash_utilizadoresController controller = loader.getController();
            controller.setFuncionario(userType);
            controller.setSuppliersController(sup);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Abre a janela de atualização de utilizadores/fornecedores.
     *
     * @param text        O texto associado ao tipo de funcionário (Admin, Fornecedor, Operador).
     * @param id          O ID do utilizador/fornecedor a ser atualizado.
     * @param nome        O nome do utilizador/fornecedor.
     * @param endereco    O endereço do utilizador/fornecedor.
     * @param pais        O país do utilizador/fornecedor.
     * @param endereco2   O segundo endereço do utilizador/fornecedor.
     * @param cidade      A cidade do utilizador/fornecedor.
     * @param telefone    O número de telefone do utilizador/fornecedor.
     * @param email       O endereço de e-mail do utilizador/fornecedor.
     * @param cod_postal  O código postal do utilizador/fornecedor.
     * @param data        A data de nascimento do utilizador/fornecedor no formato de string.
     * @throws RuntimeException Se ocorrer uma exceção IOException ou SQLException durante a execução.
     */
    public static void openAppDashUpdateUtilizadores_sup(String text, Integer id, String nome, String endereco, String pais,String endereco2, String cidade, Integer telefone,String email, String cod_postal, String data, SuppliersController sup, String iban, String bic ) {
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Users/app_dash_updateutilizadores.fxml"));
            Parent root = loader.load();
            app_dash_updateutilizadoresController controller = loader.getController();
            controller.setFuncionario(text);
            controller.setdata(id,nome,endereco, pais,endereco2,cidade,telefone,email,cod_postal,data, iban, bic);
            controller.setdatacod_fornecedor();
            controller.setSuppliersController(sup);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openAppDashInfoUtilizadores(String text, Integer id, String nome, String endereco, String pais,String endereco2, String cidade, Integer telefone,String email, String cod_postal, String data, String cod_fornecedor){
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Info_Funcionarios.fxml"));
            Parent root = loader.load();
            info_funcionarios controller = loader.getController();
            controller.setFuncionario(text);
            controller.setdata(id,nome,endereco, pais,endereco2,cidade,telefone,email,cod_postal,data,cod_fornecedor);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Abre a janela de controle de movimentos para um utilizador/fornecedor.
     *
     * @param cod  O código associado ao utilizador/fornecedor.
     * @param nome O nome do utilizador/fornecedor.
     */
    public static void openAppDashMovimentosUtilizador(String cod, String nome){
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Stock/app_fornecedor_movimentos.fxml"));
            Parent root = loader.load();
            app_fornecedor_movimentosController controller = loader.getController();
            controller.setControllerUserController();
            controller.setFornecedor(cod,nome);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Abre a janela para a lista de pagamentos SEPA associada a um utilizador.
     *
     * @param cod  O código associado ao utilizador.
     * @param nome O nome do utilizador.
     */
    public static void openSepaLista(String cod, String nome){
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Pagamentos/app_lista_Sepa.fxml"));
            Parent root = loader.load();
            Sepa_ListaController controller = loader.getController();
            controller.addObserver(observer);
            controller.SetFuncionario(cod,nome);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
