package com.example.sheetsandpicks.BL.Stock;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.BL.util;
import com.example.sheetsandpicks.Controllers.Stock.PopUp_ConfirmarStockController;
import com.example.sheetsandpicks.Controllers.Stock.StockConfirmationController;
import com.example.sheetsandpicks.Controllers.Users.CodSingleton;
import com.example.sheetsandpicks.Models.Stock;
import com.example.sheetsandpicks.Models.StockAprov_rejeita;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
/**
 * Classe que contém funções relacionadas a operações com stock, como obtenção de dados, manipulação de arquivos XML e interação com o banco de dados.
 */
public class StockFunctions {

    /**
     * Obtém uma lista de IDs a partir de uma lista de objetos Stock.
     * Este método percorre a lista de objetos Stock fornecida e extrai os IDs
     * associados a cada objeto, criando e retornando uma lista de IDs.
     *
     * @param listadeid Uma lista observável de objetos Stock.
     * @return Uma ArrayList contendo os IDs extraídos da lista de objetos Stock.
     */
    public static ArrayList<Integer> GetTable(ObservableList<Stock> listadeid){
        ArrayList<Integer> ids = new ArrayList<>();
        for(Stock stock : listadeid){
            int id = stock.getId();
            ids.add(id);
        }
        return ids;
    }

    /**
     * Obtém uma lista de status de aprovação/rejeição a partir de uma lista de objetos Stock.
     *
     * Este método percorre a lista de objetos Stock fornecida e extrai os status de aprovação/rejeição
     * associados a cada objeto, criando e retornando uma lista de status.
     *
     * @param listadeid Uma lista observável de objetos Stock.
     * @return Uma ArrayList contendo os status de aprovação/rejeição extraídos da lista de objetos Stock.
     */
    public static ArrayList<StockAprov_rejeita> GetStockInfo(ObservableList<Stock> listadeid){
        ArrayList<StockAprov_rejeita> stockInfoList = new ArrayList<>();

        for (Stock stock : listadeid) {
            int id = stock.getId();
            String aprovacao = stock.getAprov_rejeitado();
            System.out.println(aprovacao);

            StockAprov_rejeita stockInfo = new StockAprov_rejeita(id, aprovacao);
            stockInfoList.add(stockInfo);
        }

        return stockInfoList;
    }

    /**
     * Extrai dados de um arquivo XML e os envia para um banco de dados.
     *
     * Este método recebe um objeto File representando um arquivo XML contendo informações de encomenda.
     * Ele analisa o arquivo XML, extrai informações relevantes e as envia para um banco de dados usando a classe DBstatements.
     *
     * @param ficheiroselecionado O arquivo XML a ser analisado e enviado para o banco de dados.
     * @throws ParserConfigurationException Se houver problemas de configuração com o parser XML.
     * @throws IOException Se ocorrer um erro de E/S durante a leitura do arquivo XML.
     * @throws SAXException Se houver problemas ao analisar o arquivo XML.
     * @throws SQLException Se ocorrer um erro ao enviar dados para o banco de dados.
     */
public static void Conseguir_dados(File ficheiroselecionado) throws ParserConfigurationException, IOException, SAXException, SQLException {
        float Total = 0;
        float peso_liquido=0;
        String UOM2;
        String UOM3;
        float contagem;
        DocumentBuilderFactory leitor = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = leitor.newDocumentBuilder();
        Document document = db.parse(ficheiroselecionado);
        document.getDocumentElement().normalize();
        String Numero_Encomenda = document.getElementsByTagName("OrderConfirmationReference").item(0).getTextContent();
        String year = document.getElementsByTagName("Year").item(0).getTextContent();
        String month = document.getElementsByTagName("Month").item(0).getTextContent();
        String day = document.getElementsByTagName("Day").item(0).getTextContent();
    try {
        int yearValue = Integer.parseInt(year);
        int monthValue = Integer.parseInt(month);
        int dayValue = Integer.parseInt(day);

        if (monthValue < 1 || monthValue > 12) {
            exibirPopUpErro("Invalid value for Month. Must be in the range 1 - 12.");
            return;
        }
        if (dayValue < 1 || dayValue > 31) {
            exibirPopUpErro("Invalid value for Day. Must be in the range 1 - 31.");
            return;
        }
        LocalDate currentDate = LocalDate.now();
        LocalDate datas = LocalDate.of(yearValue, monthValue, dayValue);
        if (datas.isAfter(currentDate)) {
            exibirPopUpErro("Invalid date. Date cannot be in the future.");
            return;
        }
    } catch (NumberFormatException e) {
        exibirPopUpErro("Invalid numeric format for Year, Month, or Day.");
        return;
    } catch (DateTimeException e) {
        exibirPopUpErro("Invalid date values. " + e.getMessage());
        return;
    }
        LocalDate data = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        String pais = document.getElementsByTagName("Country").item(0).getTextContent();
        Boolean finder = false;
        Boolean finderFornecedor = false;
        finder = DBstatements.numeroencomendafinder(Numero_Encomenda);

        String Nome_fornecedor = document.getElementsByTagName("Name").item(0).getTextContent();
        finderFornecedor = DBstatements.fornecedorfinder(Nome_fornecedor);
        String cod_fornecedor = document.getElementsByTagName("PartyIdentifier").item(0).getTextContent();
        String morada = document.getElementsByTagName("Address1").item(0).getTextContent();

        if (!finder)
        {
            if(finderFornecedor) {
                if (CodSingleton.getInstance().getCod() != null && CodSingleton.getInstance().getCod().equals(cod_fornecedor)) {
                    NodeList nlist = document.getElementsByTagName("OrderConfirmationLineItem");
                    for (int i = 0; i < nlist.getLength(); i++) {
                        Node item = nlist.item(i);
                        if (item.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) item;
                            String Nome_Produto = element.getElementsByTagName("ProductDescription").item(0).getTextContent();
                            String Agencia_buyer = element.getElementsByTagName("ProductIdentifier").item(0).getAttributes().getNamedItem("Agency").getNodeValue();
                            String Agencia_buyer_valor = element.getElementsByTagName("ProductIdentifier").item(0).getTextContent();
                            String Agencia_supplier = element.getElementsByTagName("ProductIdentifier").item(1).getAttributes().getNamedItem("Agency").getNodeValue();
                            String Agencia_supplier_valor = element.getElementsByTagName("ProductIdentifier").item(1).getTextContent();
                            String Preco_uni_txt = element.getElementsByTagName("CurrencyValue").item(0).getTextContent();
                            float Preco_uni = Float.parseFloat(Preco_uni_txt);
                            String Tipo_moeda = element.getElementsByTagName("CurrencyValue").item(0).getAttributes().getNamedItem("CurrencyType").getNodeValue();
                            String taxa_txt = element.getElementsByTagName("TaxPercent").item(0).getTextContent();
                            float taxa = Float.parseFloat(taxa_txt);
                            String v_base_txt = element.getElementsByTagName("CurrencyValue").item(1).getTextContent();
                            float v_base = Float.parseFloat(v_base_txt);
                            String quantidade_txt = element.getElementsByTagName("Value").item(1).getTextContent();
                            int quantidade = Integer.parseInt(quantidade_txt);
                            String v_taxa_txt = element.getElementsByTagName("CurrencyValue").item(2).getTextContent();
                            float v_taxa = Float.parseFloat(v_taxa_txt);
                            String UOM = element.getElementsByTagName("Value").item(1).getAttributes().getNamedItem("UOM").getNodeValue();
                            if (element.getElementsByTagName("Value").getLength() > 2) {
                                String peso_liquido_txt = element.getElementsByTagName("Value").item(2).getTextContent();
                                peso_liquido = Float.parseFloat(peso_liquido_txt);
                                UOM2 = element.getElementsByTagName("Value").item(2).getAttributes().getNamedItem("UOM").getNodeValue();
                            } else {
                                peso_liquido = 0;
                                UOM2 = "Empty";
                            }
                            if (element.getElementsByTagName("Value").getLength() > 3) {
                                String contagem_txt = element.getElementsByTagName("Value").item(3).getTextContent();
                                contagem = Float.parseFloat(contagem_txt);
                                UOM3 = element.getElementsByTagName("Value").item(3).getAttributes().getNamedItem("UOM").getNodeValue();
                            } else {
                                contagem = 0;
                                UOM3 = "Empty";
                            }

                            Total = v_base + v_taxa;

                            DBstatements.enviarDados(Numero_Encomenda, data, Nome_fornecedor, Total, pais, Nome_Produto, Preco_uni, quantidade, peso_liquido, contagem, UOM, UOM2, UOM3, Agencia_buyer, Agencia_buyer_valor, Agencia_supplier, Agencia_supplier_valor, taxa, Tipo_moeda, v_base, v_taxa, morada);
                        }
                    }
                    exibirPopUpSucesso("XML válido.");
                } else {
                    exibirPopUpErro("O Fornecedor que inseriu este XML não é o correto, por favor inserir o Fornecedor correto");
                }
            }
            else{
                exibirPopUpErro("O nome do Fornecedor que inseriu este XML não é o correto, por favor inserir o Fornecedor correto");
            }
        }else{
            exibirPopUpErro("Esta encomenda ja existe");
        }

    }


    /**
     * Exibe uma caixa de diálogo de sucesso.
     *
     * Este método cria e exibe uma caixa de diálogo de sucesso usando a classe Alert do JavaFX.
     * A caixa de diálogo é do tipo INFORMATION e inclui um título fixo ("Sucesso") e uma mensagem personalizável.
     *
     * @param mensagem A mensagem a ser exibida na caixa de diálogo de sucesso.
     */
private static void exibirPopUpSucesso(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Exibe uma caixa de diálogo de erro.
     *
     * Este método cria e exibe uma caixa de diálogo de erro usando a classe Alert do JavaFX.
     * A caixa de diálogo é do tipo ERROR e inclui um título fixo ("Erro") e uma mensagem personalizável.
     *
     * @param mensagem A mensagem de erro a ser exibida na caixa de diálogo.
     */
private static void exibirPopUpErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Permite ao utilizador selecionar um arquivo XML, valida-o contra um esquema XSD e processa os dados se válido.
     *
     * Este método utiliza a biblioteca JavaFX para exibir uma janela de seleção de arquivo XML.
     * Em seguida, valida o XML selecionado contra um esquema XSD e, se a validação for bem-sucedida,
     * chama o método `Conseguir_dados` para processar os dados do XML. Exibe pop-ups de sucesso ou erro
     * com base no resultado da validação.
     */
 public static void Verificarxml(){
        FileChooser ficheiroxml = new FileChooser();
        ficheiroxml.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
        File ficheiroselecionado = ficheiroxml.showOpenDialog(null);

        if(ficheiroselecionado != null){
            try {
                SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                System.out.println(new File("XSDverificator.xsd").getAbsolutePath());
                Schema esquema = factory.newSchema(new StreamSource(new File("SheetsAndPicks/src/main/XSDverificator.xsd")));
                Validator validar = esquema.newValidator();
                validar.validate(new StreamSource(ficheiroselecionado));
                Conseguir_dados(ficheiroselecionado);
            } catch (SAXException | IOException e) {
                exibirPopUpErro("Erro na validação do XML " + e.getMessage());
            } catch (ParserConfigurationException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            exibirPopUpErro("Nenhum arquivo selecionado");
        }
    }

    /**
     * Carrega um arquivo FXML em uma nova janela (Stage) e configura o controlador associado.
     *
     * Este método recebe o nome do arquivo FXML, um título para a janela, um objeto Stock selecionado
     * e uma referência ao controlador StockConfirmationController. Ele carrega o arquivo FXML, configura
     * o controlador associado e exibe o conteúdo em uma nova janela (Stage).
     *
     * @param fxml O caminho do arquivo FXML a ser carregado.
     * @param nome O título da nova janela.
     * @param selectedStock O objeto Stock selecionado a ser passado para o controlador da nova janela.
     * @param a Uma referência ao controlador StockConfirmationController para interação com a tabela.
     */
public static void chamarabridor(String fxml,String nome,Stock selectedStock,StockConfirmationController a){
        try {
            FXMLLoader loader = new FXMLLoader(util.class.getResource("/"+fxml));
            Parent root = loader.load();
            PopUp_ConfirmarStockController targetController = loader.getController();
            targetController.setProduto(selectedStock);
            targetController.setTabelaConfirmar_StockFill(a);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle(nome);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Cria uma janela para a geração de arquivos XML SEPA.
     *
     * Este método carrega um arquivo FXML contendo a interface para geração de arquivos XML SEPA.
     * A janela gerada é utilizada para configurar e criar arquivos XML SEPA.
     *
     * @param fxml O caminho do arquivo FXML a ser carregado.
     * @param mensagem A mensagem a ser exibida na janela de criação de arquivos XML SEPA.
     */
    public static void CriarSepa(String fxml,String mensagem){
        FXMLLoader loader = new FXMLLoader(util.class.getResource("/"+fxml));
        try {
            Parent root = loader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
