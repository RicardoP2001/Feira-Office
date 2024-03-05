package com.example.sheetsandpicks.Controllers.Stock;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.BL.Pagamentos.PagamentoFunctions;
import com.example.sheetsandpicks.Controllers.Users.CodSingleton;
import com.example.sheetsandpicks.Models.Admins;
import com.example.sheetsandpicks.Models.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
/**
 * Controlador para a interface de criação de Sepa.
 */
public class AppDashSepa implements Initializable {

    @FXML
    private TableView<Stock> Table_lista_sepa;

    @FXML
    private TableView<Stock> Table_lista_stock;

    @FXML
    private Button butSepa;

    @FXML
    private TableColumn<Stock, String> c_listar_Encomenda;

    @FXML
    private TableColumn<Stock, String> c_listar_Produto;

    @FXML
    private TableColumn<Stock, Float> c_listar_V_Total;

    @FXML
    private TableColumn<Stock, LocalDate> c_listar_data_Encomenda;

    @FXML
    private TableColumn<Stock, String> c_sepa_Encomenda;

    @FXML
    private TableColumn<Stock, String> c_sepa_Produto;

    @FXML
    private TableColumn<Stock, LocalDate> c_sepa_data_Encomenda;

    @FXML
    private TableColumn<Stock, Float> c_sepa_v_total;

    @FXML
    private ComboBox<String> cmb_tipo;

    @FXML
    private Label lb_Total;

    private String cod_fornecedor;

    @FXML
    private Label lb_Total_por_pagar;


    @FXML
    private ComboBox<String> cmb_tipomoeda;


    private int id;

    ArrayList<Stock> Sepa_Stock_List = Stock.StockList;
    ObservableList<Stock> observableList;

    private ObservableList<Stock> observableListconfirmados;
    /**
     * Manipula o evento de criar uma Sepa.
     * Valida a seleção de produtos e tipo de pagamento antes de criar a Sepa.
     * Envia os dados necessários para o backend para processar a criação da Sepa.
     */
    @FXML
    void Handler_Criar_Sepa() {

        if (Table_lista_sepa.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Nenhum produto selecionado");
            alert.setContentText("Por favor, selecione pelo menos um produto para criar uma separação.");
            alert.showAndWait();
            return;
        }

        if (cmb_tipo.getValue() == null || cmb_tipo.getValue().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("Tipo de Pagamento não selecionado");
            alert.setContentText("Por favor, selecione um tipo de pagamento.");
            alert.showAndWait();
            return;
        }
        Admins Empresa = DBstatements.GetInfoEmpresa();
        LocalDateTime calendarDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String dataFormatada = calendarDateTime.format(formatter);

        LocalDateTime mes1_date = calendarDateTime.plusMonths(1);

        if (calendarDateTime.getMonthValue() == 12 && mes1_date.getMonthValue() == 1) {
            mes1_date = mes1_date.plusYears(1);
        }
        String Insrt_Id="F&S"+ Empresa.getId()+'/'+id;
        String End_to_end="F&S"+id+'/'+Empresa.getId();
        String ChrgBr="SHAR";
        String MndtId="F&S123";
        String SeqTp="RCUR";
        int id_paga = DBstatements.getultimopagamento(cmb_tipo.getValue());
        ObservableList<Stock> listadeids= Table_lista_sepa.getItems();
        ArrayList<Integer> ids=PagamentoFunctions.Lista_id(listadeids);
        Float Total = Float.parseFloat(lb_Total.getText());
        if(cmb_tipo.getValue().equals("DD")) {
            String id_pagamento = "F&S/"+ id_paga;
            DBstatements.dadosficheirodebito(Empresa.getId(),id, LocalDateTime.parse(dataFormatada), mes1_date,cmb_tipo.getValue(),Insrt_Id,End_to_end,"Pagamento",Total ,id_pagamento,ChrgBr,MndtId,LocalDateTime.parse(dataFormatada),mes1_date,"Mensal","pagamento","pagamento",Empresa.getNome(),SeqTp, cmb_tipomoeda.getValue());
            for(Integer id: ids) {
                DBstatements.enviarstock(id,id_pagamento);
            }
        }
        else{
            String id_pagamento = "F&S/"+ id_paga;
            System.out.println(id_pagamento);
            DBstatements.dadosficheirocredito(Empresa.getId(),id, LocalDateTime.parse(dataFormatada), mes1_date,cmb_tipo.getValue(),Insrt_Id,End_to_end,"Pagamento de Stock", Total,id_pagamento, cmb_tipomoeda.getValue());
            for(Integer ida: ids) {
                DBstatements.enviarstock(ida,id_pagamento);
            }
        }
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Separação Criada com Sucesso");
        successAlert.setHeaderText("A SEPA foi criada com sucesso.");
        successAlert.showAndWait();

        Stage stage = (Stage) butSepa.getScene().getWindow();
        stage.close();
    }

    /**
     * Inicializa a interface e configura os componentes necessários.
     * Preenche tabelas, define listeners e inicializa variáveis.
     *
     * @param location  A URL de inicialização.
     * @param resources Os recursos a serem usados durante a inicialização.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Stock.StockList.removeAll(Sepa_Stock_List);
        Encher_tabela();
        definirlabel_por_pagar();
        Encher_moeda();
        selectRow();
        definirlabel_total();

        cmb_tipomoeda.valueProperty().addListener((observable, oldValue, newValue) -> {
            definirlabel_por_pagar();
        });

        cmb_tipomoeda.valueProperty().addListener((observable, oldValue, newValue) -> {
            definirlabel_total();
        });
    }

    ObservableList<String> setObservableList(){
        ObservableList<String> a= FXCollections.observableArrayList();
        List<String> b= DBstatements.getMetodos();
        a.addAll(b);
        return a;
    }


    /**
     * Preenche a tabela de stock com os produtos disponíveis para separação.
     */
    void Encher_tabela(){
            List<Stock> StockList = DBstatements.GetStock2(cod_fornecedor);
            observableList=FXCollections.observableArrayList();

            c_listar_Encomenda.setCellValueFactory(new PropertyValueFactory<>("N_Encomenda"));
            c_listar_Produto.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            c_listar_V_Total.setCellValueFactory(new PropertyValueFactory<>("resultado"));
            c_listar_data_Encomenda.setCellValueFactory(new PropertyValueFactory<>("data"));

            Table_lista_stock.setItems(FXCollections.observableArrayList(StockList));

            observableList.addAll(StockList);
    }
    /**
     * Preenche a lista suspensa de moedas com as moedas disponíveis.
     */
     void Encher_moeda(){
         List<String> moedas = DBstatements.getmoeda();
         if (!moedas.isEmpty()) {
             cmb_tipomoeda.getItems().clear();
             cmb_tipomoeda.getItems().addAll(moedas);
             cmb_tipomoeda.setValue(moedas.get(0));
         } else {
             System.out.println("A lista de moedas está vazia.");
         }

    }
    /**
     * Adiciona um produto à tabela de Sepa e atualiza os totais.
     *
     * @param selectedStock O produto selecionado para adicionar à Sepa.
     */
    private void Encher_TabelaSepa(Stock selectedStock){
        if(observableListconfirmados == null){
            observableListconfirmados=FXCollections.observableArrayList();

            c_sepa_Encomenda.setCellValueFactory(new PropertyValueFactory<>("N_Encomenda"));
            c_sepa_Produto.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            c_sepa_v_total.setCellValueFactory(new PropertyValueFactory<>("resultado"));
            c_sepa_data_Encomenda.setCellValueFactory(new PropertyValueFactory<>("data"));

            Table_lista_sepa.setItems(observableListconfirmados);
        }

        if(!observableListconfirmados.contains(selectedStock)){
            observableListconfirmados.add(selectedStock);

            ObservableList<Stock> tableStocksepa = FXCollections.observableArrayList(Table_lista_stock.getItems());
            if (tableStocksepa.contains(selectedStock)) {
                tableStocksepa.remove(selectedStock);
                observableList.remove(selectedStock);
                Table_lista_stock.setItems(tableStocksepa);
            }
        }
        float taxaeuro = DBstatements.gettaxaeuro(cmb_tipomoeda.getValue());
        float v_total= Float.parseFloat(lb_Total.getText())+(selectedStock.getResultado() * taxaeuro);
        double valorArredondado = Math.round(v_total*100.00)/100.00;
        lb_Total.setText(String.valueOf(valorArredondado));
        float sub=Float.parseFloat(lb_Total_por_pagar.getText())-(selectedStock.getResultado() * taxaeuro);
        double valorArredondado_por_pagar = Math.round(sub*100.00)/100.00;
        lb_Total_por_pagar.setText(String.valueOf(valorArredondado_por_pagar));
    }
    /**
     * Atualiza a etiqueta de total a pagar com base nos produtos selecionados e na moeda escolhida.
     */
    void definirlabel_por_pagar(){
        ObservableList<Stock> v_total_listar =Table_lista_stock.getItems();
        String moeda = cmb_tipomoeda.getValue();
        lb_Total_por_pagar.setText(String.valueOf(PagamentoFunctions.Listar_v_totalmoeda(v_total_listar, moeda)));
    }
    /**
     * Atualiza a etiqueta de total com base nos produtos na Sepa e na moeda escolhida.
     */
    void definirlabel_total(){
        ObservableList<Stock> v_total_listar =Table_lista_sepa.getItems();
        String moeda = cmb_tipomoeda.getValue();
        lb_Total.setText(String.valueOf(PagamentoFunctions.Listar_v_totalmoeda(v_total_listar, moeda)));
    }
    /**
     * Configura o fornecedor e inicializa a interface.
     */
    public void setFornecedor(){
        cmb_tipo.setItems(setObservableList());
        this.cod_fornecedor = CodSingleton.getInstance().getCod();
        this.id=DBstatements.getid(cod_fornecedor);
        initialize(null,null);
    }
    /**
     * Configura a seleção de linhas nas tabelas para manipulação de produtos.
     */
    private void selectRow(){

        Table_lista_stock.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Table_lista_stock.setOnMouseClicked(event -> {
            if(event.getButton()== MouseButton.PRIMARY && event.getClickCount()==2){
                Stock selectedStocks = Table_lista_stock.getSelectionModel().getSelectedItem();
                if(selectedStocks!=null) {
                    Encher_TabelaSepa(selectedStocks);
                }
            }
        });

        Table_lista_stock.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                Stock selectedStocks = Table_lista_stock.getSelectionModel().getSelectedItem();
                if(selectedStocks!=null) {
                    Encher_TabelaSepa(selectedStocks);
                }
            }
        });
        Table_lista_sepa.setOnMouseClicked(event -> {
            if(event.getButton()==MouseButton.PRIMARY && event.getClickCount()==2){
                Stock selectedStock =  Table_lista_sepa.getSelectionModel().getSelectedItem();
                if(selectedStock!=null){
                    Retornar_tabela_Stock(selectedStock);
                }
            }
        });
        Table_lista_sepa.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                Stock selectedStock =  Table_lista_sepa.getSelectionModel().getSelectedItem();
                if(selectedStock!=null){
                    Retornar_tabela_Stock(selectedStock);
                }
            }
        });
    }
    /**
     * Remove um produto da Sepa e retorna à tabela de stock.
     *
     * @param selectedStock O produto selecionado para retornar à tabela de stock.
     */
    private void Retornar_tabela_Stock(Stock selectedStock){
        observableList.add(selectedStock);
        List<Stock> itensStocks = new ArrayList<>();
        itensStocks.add(selectedStock);

        Table_lista_sepa.getItems().removeAll(itensStocks);
        Table_lista_stock.setItems(observableList);

        float taxaeuro = DBstatements.gettaxaeuro(cmb_tipomoeda.getValue());
        float v_total= Float.parseFloat(lb_Total_por_pagar.getText())+(selectedStock.getResultado() * taxaeuro);
        v_total = (float) (Math.round(v_total*100.00)/100.00);
        lb_Total_por_pagar.setText(String.valueOf(v_total));
        float sub=Float.parseFloat(lb_Total.getText())-(selectedStock.getResultado() * taxaeuro);
        sub = (float) (Math.round(sub*100.00)/100.00);
        lb_Total.setText(String.valueOf(sub));
    }
}
