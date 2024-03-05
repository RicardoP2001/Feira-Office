package com.example.sheetsandpicks.Controllers;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.BL.Stock.StockFunctions;
import com.example.sheetsandpicks.BL.util;
import com.example.sheetsandpicks.Controllers.Stock.AppDashSepa;
import com.example.sheetsandpicks.Controllers.Users.UserSingleton;
import com.example.sheetsandpicks.MainApplication;
import com.example.sheetsandpicks.Models.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
/**
 * Controlador para a interface do utilizador relacionada aos movimentos de um fornecedor.
 * Permite visualizar e gerenciar os movimentos de stock associados a um fornecedor específico.
 */
public class app_fornecedor_movimentosController implements Initializable{

    @FXML
    private TableColumn<Stock, String> fornecedorcolumn;
    @FXML
    private Button btn_criar_Sepa;
    @FXML
    private Button btn_adicionar_stock;
    @FXML
    private TableColumn<Stock, String> moedacolumn;
    @FXML
    private TableColumn<Stock, String> nomeprodutocolumn;
    @FXML
    private TableColumn<Stock, String> paiscolumn;
    @FXML
    private TableColumn<Stock, Integer> preco_unicolumn;
    @FXML
    private TableColumn<Stock, Integer> quantidadecolumn;
    @FXML
    private TableView<Stock> tableView;
    @FXML
    private TableColumn<Stock, String> tipo_UOMcolumn;
    @FXML
    private TableColumn<Stock, String> v_basecolumn;
    @FXML
    private Label labelnome;

    public String name;
    public String cod;
    @FXML
    private TextField pesquisaramovimentos;
    @FXML
    private TableColumn<Stock, LocalDate> datacolumn;
    @FXML
    private TableColumn<Stock, String> encomendacolumn;
    @FXML
    private Label valorbasetotal;
    @FXML
    private Label valortotal;
    @FXML
    private TableColumn<Stock, Integer> idfornecedorcolumn;
    @FXML
    private TableColumn<Stock, Integer> ivacolumn;
    @FXML
    private Button btn_sepas;

    private boolean usf=false;


    ObservableList<Stock> observableStockList = FXCollections.observableArrayList();
    /**
     * Manipula o evento de clique no botão "Criar SEPA".
     * Abre uma nova janela para criar uma ordem de pagamento SEPA.
     *
     * @param event O evento de ação associado ao clique no botão "Criar SEPA".
     */
    @FXML
    void Handler_criar_Sepa(ActionEvent event) {
        Abrir_dados("com/example/sheetsandpicks/Pagamentos/app_dash_Sepa.fxml","SEPA");
    }
    /**
     * Manipula o evento de clique no botão "SEPAs".
     * Carrega e exibe a lista de ordens de pagamento SEPA associadas ao fornecedor.
     *
     * @param event O evento de ação associado ao clique no botão "SEPAs".
     * @throws IOException Se ocorrer um erro durante o carregamento da lista de SEPAs.
     * @throws SQLException Se ocorrer um erro ao acessar o banco de dados para obter informações sobre SEPAs.
     */
    @FXML
    void Handler_Sepas(ActionEvent event) throws IOException, SQLException {
        CarregarListaSepa();
    }
    /**
     * Inicializa a interface do utilizador preenchendo a tabela de movimentos de stock associados ao fornecedor.
     * Configura a pesquisa dinâmica na tabela e calcula os valores totais.
     *
     * @param location  O localizador de recursos para a URL, não é utilizado neste método.
     * @param resources Os recursos para a ResourceBundle, não é utilizado neste método.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomeprodutocolumn.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        encomendacolumn.setCellValueFactory(new PropertyValueFactory<>("n_Encomenda"));
        quantidadecolumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        datacolumn.setCellValueFactory(new PropertyValueFactory<>("Data"));
        preco_unicolumn.setCellValueFactory(new PropertyValueFactory<>("Preco_uni"));
        moedacolumn.setCellValueFactory(new PropertyValueFactory<>("moeda"));
        tipo_UOMcolumn.setCellValueFactory(new PropertyValueFactory<>("UOM"));
        fornecedorcolumn.setCellValueFactory(new PropertyValueFactory<>("Fornecedor"));
        v_basecolumn.setCellValueFactory(new PropertyValueFactory<>("v_base"));
        paiscolumn.setCellValueFactory(new PropertyValueFactory<>("pais"));
        ivacolumn.setCellValueFactory(new PropertyValueFactory<>("V_taxa"));
        idfornecedorcolumn.setCellValueFactory(new PropertyValueFactory<>("cod_fornecedor"));

        if(name==null) {
            name = UserSingleton.getInstance().getUsername();
        }
        List<Stock> stocks = DBstatements.buscarStocks(cod);
        labelnome.setText(name);
        Map<String, Stock> StockMap = new HashMap<>();

        double valorTotalVBase = calcularValorTotalVBase(stocks);
        valorbasetotal.setText(String.format("%.2f €", valorTotalVBase));

        double valorTotalComIVA = calcularValorTotalComIVA(stocks);
        valortotal.setText(String.format("%.2f €", valorTotalComIVA));

        for(Stock stock : stocks){
            String nomeProduto = stock.getNome();
            String n_encomenda = stock.getN_Encomenda();
            String chave = nomeProduto + "_" + n_encomenda;

            if (StockMap.containsKey(chave)) {
                Stock existingStock = StockMap.get(chave);
                existingStock.setQuantidade(existingStock.getQuantidade() + stock.getQuantidade());
                existingStock.setV_base(existingStock.getV_base() + stock.getV_base());
            } else {
                StockMap.put(chave, stock);
            }
        }
        ArrayList<Stock> StockList2 = new ArrayList<>(StockMap.values());
        tableView.setItems(FXCollections.observableArrayList(StockList2));

    }
    /**
     * Configura a funcionalidade de pesquisa dinâmica na tabela de movimentos de stock.
     * Filtra os movimentos de stock com base no texto inserido na caixa de pesquisa, considerando várias colunas.
     * Os resultados filtrados são exibidos na tabela.
     */
    public void textboxPress() {
        Enter();
    }

    /**
     * Configura a funcionalidade de pesquisa dinâmica na tabela de Administradores.
     * Filtra os Administradores com base no texto inserido na caixa de pesquisa, considerando várias colunas.
     * Os resultados filtrados são exibidos na tabela.
     */
    private void Enter() {
        observableStockList = tableView.getItems();

        FilteredList<Stock> filteredList = new FilteredList<>(observableStockList, b -> true);
        pesquisaramovimentos.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(stocks -> {
            if (newValue.isEmpty() || newValue.isBlank()) {
                return true;
            }
            String palavra = newValue.toLowerCase();

            return stocks.getN_Encomenda().toLowerCase().contains(palavra)
                    || stocks.getData().toString().contains(palavra)
                    || String.valueOf(stocks.getId()).toLowerCase().contains(palavra)
                    || String.valueOf(stocks.getNome()).toLowerCase().contains(palavra)
                    || String.valueOf(stocks.getQuantidade()).toLowerCase().contains(palavra)
                    || stocks.getFornecedor().toLowerCase().contains(palavra)
                    || String.valueOf(stocks.getV_base()).toLowerCase().contains(palavra)
                    || stocks.getUOM().toLowerCase().contains(palavra)
                    || String.valueOf(stocks.getPreco_uni()).toLowerCase().contains(palavra)
                    || stocks.getMoeda().toLowerCase().contains(palavra)
                    || stocks.getPais().toLowerCase().contains(palavra)
                    || stocks.getAprov_rejeitado().toLowerCase().contains(palavra)
                    || String.valueOf(stocks.getV_taxa()).toLowerCase().contains(palavra)
                    || stocks.getCod_fornecedor().toLowerCase().contains(palavra);
        }));
        SortedList<Stock> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }
    /**
     * Calcula o valor total considerando o IVA de uma lista de movimentos de stock.
     *
     * @param stocks A lista de movimentos de stock.
     * @return O valor total com IVA.
     */
    private double calcularValorTotalComIVA(List<Stock> stocks) {
        double totalComIVA = 0.0;
        for (Stock stock : stocks) {
            double valorComIVA = stock.getV_base()  + stock.getV_taxa();
            totalComIVA += valorComIVA;
        }
        return totalComIVA;
    }
    /**
     * Calcula o valor total de v_base de uma lista de movimentos de stock.
     *
     * @param stocks A lista de movimentos de stock.
     * @return O valor total de v_base.
     */
    private double calcularValorTotalVBase(List<Stock> stocks) {
        double total = 0.0;
        for (Stock stock : stocks) {
            total += stock.getV_base();
        }
        return total;
    }

    /**
     * Configuração inicial da interface do utilizador para um fornecedor específico.
     * Preenche a tabela de movimentos de stock, calcula os valores totais e configura a pesquisa dinâmica.
     *
     * @param cod  O código do fornecedor.
     * @param nome O nome do fornecedor.
     */
    public void setFornecedor(String cod,String nome){
        this.cod=cod;
        this.name=nome;

        if (usf) {
            btn_adicionar_stock.setManaged(false);
            btn_criar_Sepa.setManaged(false);
        }

        System.out.println(this.cod);
        System.out.println(this.name);
        initialize(null,null);
    }
    /**
     * Configura o controlador para a interface do utilizador do fornecedor.
     */
    public void setControllerUserController(){
        this.usf=true;
    }

    /**
     * Manipula o evento de adição de stock.
     * Chama o método estático da classe StockFunctions para verificar um arquivo XML relacionado aos stocks.
     *
     * @param ignoredEvent O evento de ação associado ao clique no botão de adição de stock.
     */
    @FXML
    void Handler_adicionar_Stock(ActionEvent ignoredEvent) {
        StockFunctions.Verificarxml();
    }
    /**
     * Abre uma nova janela para visualizar os dados relacionados a uma ordem de pagamento SEPA.
     *
     * @param fxml     O caminho do arquivo FXML da nova janela.
     * @param mensagem A mensagem para a nova janela.
     */
    void Abrir_dados(String fxml, String mensagem){
        try {
            FXMLLoader loader = new FXMLLoader(util.class.getResource("/"+fxml));
            Parent root = loader.load();
            AppDashSepa appDashSepa=loader.getController();
            appDashSepa.setFornecedor();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Carrega e exibe a lista de ordens de pagamento SEPA associadas ao fornecedor.
     *
     * @throws IOException  Se ocorrer um erro durante o carregamento da lista de SEPAs.
     * @throws SQLException Se ocorrer um erro ao acessar o banco de dados para obter informações sobre SEPAs.
     */
    private void CarregarListaSepa() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/sheetsandpicks/Pagamentos/app_lista_SepasFornecedor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        app_lista_SepasFornecedorController app_lista_sepasFornecedorController = fxmlLoader.getController();
        app_lista_sepasFornecedorController.setFornecedor();
        Stage stage = new Stage();
        stage.setTitle("Lista Das Sepas");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }


}




