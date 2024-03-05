package com.example.sheetsandpicks.Controllers;
import com.example.sheetsandpicks.BL.MainFunctions.MainFunctions;
import com.example.sheetsandpicks.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Controlador principal da aplicação responsável pela gestão da interface do utilizador e navegação entre funcionalidades.
 * Ele controla a exibição dinâmica de diferentes interfaces com base nas ações do utilizador e nas permissões concedidas.
 */
public class MainController implements Initializable {

    @FXML
    private ComboBox<String> comboStock;

    @FXML
    private ComboBox<String> comboUti;

    @FXML
    private AnchorPane pane;

    private String nv = "";

    @FXML
    private Button btn_sair;
    /**
     * Manipula o evento de clique no botão "Sair", carregando o conteúdo da tela de login e fechando a janela principal.
     *
     * @param ignoredEvent O evento de ação associado ao clique no botão "Sair".
     * @throws IOException Se ocorrer um erro durante o carregamento da interface do utilizador de login.
     */
    @FXML
    void HandlerSair(ActionEvent ignoredEvent) throws IOException {
        CarregarConteudoLogin();
        Stage stage = (Stage) btn_sair.getScene().getWindow();
        stage.close();
    }



    /**
     * Inicia o controlador do FXML, chamando-o automaticamente após o carregar o respectivo arquivo FXML.
     *
     * @param location  O URL de onde o arquivo FXML foi carregado. Pode ser nulo se o arquivo não foi carregado de um URL.
     * @param resources Os recursos locais que foram injetados neste controlador. Pode ser nulo se nenhum recurso for especificado.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image initialImage = new Image(getClass().getResource("/img/sair.png").toExternalForm());        ImageView imageView = new ImageView(initialImage);

        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        btn_sair.setGraphic(imageView);
        Image hoverImage = new Image(getClass().getResource("/img/sairOver.png").toExternalForm());        btn_sair.setOnMouseEntered(e -> {
            ImageView hoverImageView = new ImageView(hoverImage);
            hoverImageView.setFitWidth(30);
            hoverImageView.setFitHeight(30);
            btn_sair.setGraphic(hoverImageView);
        });

        btn_sair.setOnMouseExited(e -> btn_sair.setGraphic(imageView));

        comboStock.getItems().addAll("- Stock","- Sepa","- Encomendas","- Produtos");
        comboUti.setOnAction(event -> {
            String selectedItem = comboUti.getValue();

            switch(selectedItem){
                case "+ Fornecedor"-> chamarpane("com/example/sheetsandpicks/Users/app_suppliers.fxml", "Fornecedor");

                case "+ Operador"->chamarpane("com/example/sheetsandpicks/Users/app_operators.fxml", "Operador");

                case "+ Admin"->chamarpane("com/example/sheetsandpicks/Users/app_admins.fxml", "Admin");

                case "+ Cliente"->chamarpane("com/example/sheetsandpicks/Users/app_client.fxml", "Cliente");
            }
        });

        comboStock.setOnAction(event -> {
            String selectedItem = comboStock.getValue();
            if ("+ Ver Stock".equals(selectedItem) && nv.equals("Fornecedor")) {
                chamarpaneStock("com/example/sheetsandpicks/Stock/app_dash_vizualizarStock.fxml","Fornecedor");
            } else if(nv.equals("Fornecedor")){
                chamarpaneStock("com/example/sheetsandpicks/Stock/app_dash_vizualizarStock.fxml","Fornecedor");
            }
            else if("- Sepa".equals(selectedItem)){
                chamarpane("com/example/sheetsandpicks/Users/app_suppliers.fxml","Sepa");
            } else if("- Encomendas".equals(selectedItem)){
                chamarpane2("com/example/sheetsandpicks/Encomendas/app_dash_verEncomenda.fxml");
            }else if("- Produtos".equals(selectedItem)){
                chamarpane2("com/example/sheetsandpicks/Produtos/app_dash_VizualizarProdutosAPI.fxml");
            }
            else{
                chamarpane2("com/example/sheetsandpicks/Stock/app_dash_vizualizarStock.fxml");
            }
        });
    }

    /**
     * Atualiza as opções da combobox "comboUti" com base no nível de permissão fornecido.
     *
     * @param nivelPermissao O nível de permissão do utilizador para determinar as opções disponíveis na combobox.
     */
    public void atualizarComboUti(String nivelPermissao) {
        comboUti.getItems().clear();
        nv=nivelPermissao;
        if(!nv.equals("Fornecedor")) {
            chamarpaneStock("com/example/sheetsandpicks/Stock/app_dash_vizualizarStock.fxml", nv);
        }else{
            chamarpaneStock("com/example/sheetsandpicks/Stock/app_fornecedor_movimentos.fxml", nv);
        }
        switch (nivelPermissao) {
            case "Admin" -> comboUti.getItems().addAll( "+ Cliente","+ Fornecedor", "+ Operador", "+ Admin");
            case "Fornecedor" -> {
                comboUti.setManaged(false);
                comboStock.setManaged(false);
            }
            case "Operador" -> comboUti.getItems().addAll("+ Cliente","+ Fornecedor");
        }

        comboUti.getSelectionModel().selectFirst();
    }

    /**
     * Carrega e exibe o conteúdo de uma interface do utilizador baseado no arquivo FXML e no tipo de utilizador.
     *
     * @param fxml      O caminho do arquivo FXML que define a interface do utilizador.
     * @param usertype  O tipo de utilizador para determinar o conteúdo específico a ser carregado.
     */
    private void chamarpane(String fxml,String usertype){
        Node conteudo = MainFunctions.carregarConteudo(fxml,usertype);
        pane.getChildren().setAll(conteudo);
    }
    /**
     * Carrega e exibe o conteúdo de uma interface do utilizador baseado no arquivo FXML sem considerar o tipo de utilizador.
     *
     * @param fxml O caminho do arquivo FXML que define a interface do utilizador.
     */
    private void chamarpane2(String fxml){
        Node conteudo = MainFunctions.CarregarConteudo2(fxml);
        pane.getChildren().setAll(conteudo);
    }

    /**
     * Carrega e exibe o conteúdo de uma interface do utilizador relacionada ao stock com base no arquivo FXML e no tipo de utilizador.
     * Se o tipo de utilizador for "Fornecedor", carrega um conteúdo específico para fornecedores.
     *
     * @param fxml      O caminho do arquivo FXML que define a interface do utilizador relacionada ao stock.
     * @param usertype  O tipo de utilizador para determinar o conteúdo específico a ser carregado.
     */
    private void chamarpaneStock(String fxml,String usertype){
        Node conteudo;
        if(!usertype.equals("Fornecedor")) {
            conteudo = MainFunctions.carregarConteudoStock(fxml, usertype);
        }else{
            conteudo=MainFunctions.carregarConteudoFornecedor(fxml,usertype);
        }

        pane.getChildren().setAll(conteudo);
    }

    /**
     * Carrega e exibe o conteúdo da interface do utilizador de login em uma nova janela.
     *
     * @throws IOException Se ocorrer um erro durante o carregamento da interface do utilizador de login.
     */
    private void CarregarConteudoLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/" + "com/example/sheetsandpicks/Login/app_login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280,720 );
        Stage stage = new Stage();
        stage.setTitle("Login - Feira&Office");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}