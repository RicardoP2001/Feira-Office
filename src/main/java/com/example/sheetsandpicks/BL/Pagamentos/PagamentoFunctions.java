package com.example.sheetsandpicks.BL.Pagamentos;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.BL.Users.UsersFunctions;
import com.example.sheetsandpicks.Controllers.Pagamento.Aceitar_SepaController;
import com.example.sheetsandpicks.Controllers.Pagamento.DashSepaInfoController;
import com.example.sheetsandpicks.Controllers.app_fornecedor_movimentosController;
import com.example.sheetsandpicks.MainApplication;
import com.example.sheetsandpicks.Models.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PagamentoFunctions {

    private static SepaUpdateCallback sepaUpdateCallback;
    public interface SepaUpdateCallback {
        void onUpdate();
    }
    public static void setSepaUpdateCallback(SepaUpdateCallback callback) {
        sepaUpdateCallback = callback;
    }

    public static void CriarXMLDebito(String cod,String id_pagamento,String mensagem,String Total) {
        Suppliers fornecedor = DBstatements.getSupplier(cod);
        String abreviacao_fornecedor=DBstatements.getabreviacao(fornecedor.getPais());

        LocalDateTime calendarDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSS");
        String dataFormatada = calendarDateTime.format(formatter);
        BigDecimal valorDecimal = new BigDecimal(Total);
        BigDecimal valorArredondado = valorDecimal.setScale(2, RoundingMode.HALF_UP);
        String valorFormatado = valorArredondado.toString();
        LocalDateTime mes1_date = calendarDateTime.plusMonths(1);

        if (calendarDateTime.getMonthValue() == 12 && mes1_date.getMonthValue() == 1) {
            mes1_date = mes1_date.plusYears(1);
        }

        Admins Empresa = DBstatements.GetInfoEmpresa();
        String abreviacao_empresa=DBstatements.getabreviacao(Empresa.getPais());
        String tipo_moeda=DBstatements.getmoedaencomenda(id_pagamento);
        String Date_Sign= String.valueOf(calendarDateTime);
        String Insrt_Id="F&S"+ Empresa.getId()+'/'+fornecedor.getId();
        String End_to_end="F&S"+fornecedor.getId()+'/'+Empresa.getId();
        String MndtId="F&S123";
        String Frqcy="MNTH";
        String Purp="SUPP";
        String ChrgBr="SHAR";
        String SeqTp="RCUR";
        Integer quantidadestock = DBstatements.getquantidade(id_pagamento);
        List<String> moradas = DBstatements.getmorada(id_pagamento);
        List<Float> Totals = DBstatements.gettotal(id_pagamento);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try{
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element rootElement= document.createElement("Document");
        document.appendChild(rootElement);

            rootElement.setAttribute("xmlns", "urn:iso:std:iso:20022:tech:xsd:pain.008.001.02");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

            Element cstmrDrctDbtInitnElement=document.createElement("CstmrDrctDbtInitn");
            rootElement.appendChild(cstmrDrctDbtInitnElement);

                Element GrpHdrElement=document.createElement("GrpHdr");
                cstmrDrctDbtInitnElement.appendChild(GrpHdrElement);

                    adicionarElementoetexto(document,GrpHdrElement,"MsgId",mensagem);
                    adicionarElementoetexto(document,GrpHdrElement,"CreDtTm", dataFormatada);
                    adicionarElementoetexto(document,GrpHdrElement,"NbOfTxs", String.valueOf(quantidadestock));
                    adicionarElementoetexto(document,GrpHdrElement,"CtrlSum",Total);

                    Element initgPtyElement= document.createElement("InitgPty");
                    GrpHdrElement.appendChild(initgPtyElement);

                        adicionarElementoetexto(document,initgPtyElement,"Nm", Empresa.getNome());

                        Element PstlAdrElement = document.createElement("PstlAdr");
                        initgPtyElement.appendChild(PstlAdrElement);

                            adicionarElementoetexto(document,PstlAdrElement,"StrtNm", moradas.get(0));
                            adicionarElementoetexto(document,PstlAdrElement,"PstCd", Empresa.getCod_postal());
                            adicionarElementoetexto(document,PstlAdrElement,"TwnNm",Empresa.getCidade());
                            adicionarElementoetexto(document,PstlAdrElement,"Ctry",abreviacao_empresa);

                Element PmtInfElement= document.createElement("PmtInf");
                cstmrDrctDbtInitnElement.appendChild(PmtInfElement);

                    adicionarElementoetexto(document,PmtInfElement,"PmtInfId",id_pagamento);
                    adicionarElementoetexto(document,PmtInfElement,"PmtMtd","DD");
                    adicionarElementoetexto(document,PmtInfElement,"BtchBookg","false");
                    adicionarElementoetexto(document,PmtInfElement,"ReqdColltnDt", String.valueOf(mes1_date));

                        Element CdtrElement = document.createElement("Cdtr");
                        PmtInfElement.appendChild(CdtrElement);

                            adicionarElementoetexto(document,CdtrElement,"Nm", Empresa.getNome());

                        Element PstlAdrElement2 = document.createElement("PstlAdr");
                        CdtrElement.appendChild(PstlAdrElement2);

                            adicionarElementoetexto(document,PstlAdrElement2,"StrtNm",moradas.get(0)+','+Empresa.getEndereco2());
                            adicionarElementoetexto(document,PstlAdrElement2,"PstCd",Empresa.getCod_postal());
                            adicionarElementoetexto(document,PstlAdrElement2,"TwnNm",Empresa.getCidade());
                            adicionarElementoetexto(document,PstlAdrElement2,"Ctry",abreviacao_empresa);

                        Element CdtrAcctElement = document.createElement("CdtrAcct");
                        PmtInfElement.appendChild(CdtrAcctElement);

                            Element IdElement = document.createElement("Id");
                            CdtrAcctElement.appendChild(IdElement);

                                adicionarElementoetexto(document,IdElement,"IBAN", Empresa.getIban());

                        Element CdtrAgtElement = document.createElement("CdtrAgt");
                        PmtInfElement.appendChild(CdtrAgtElement);

                            Element FinInstnIdElement = document.createElement("FinInstnId");
                            CdtrAgtElement.appendChild(FinInstnIdElement);

                                adicionarElementoetexto(document,FinInstnIdElement,"BIC",Empresa.getBic());
            for (int i = 0; i < quantidadestock; i++) {
                String morada = moradas.get(i % moradas.size());
                Float total = Totals.get(i % Totals.size());

                Element DrctDbtTxInfElement = document.createElement("DrctDbtTxInf");
                PmtInfElement.appendChild(DrctDbtTxInfElement);

                Element PmtIdElement = document.createElement("PmtId");
                DrctDbtTxInfElement.appendChild(PmtIdElement);

                adicionarElementoetexto(document, PmtIdElement, "InstrId", Insrt_Id);
                adicionarElementoetexto(document, PmtIdElement, "EndToEndId", End_to_end);

                Element PmtTpInfElement = document.createElement("PmtTpInf");
                DrctDbtTxInfElement.appendChild(PmtTpInfElement);

                Element SvcLvlElement = document.createElement("SvcLvl");
                PmtTpInfElement.appendChild(SvcLvlElement);

                adicionarElementoetexto(document, SvcLvlElement, "Prtry", Empresa.getNome());

                adicionarElementoetexto(document, PmtTpInfElement, "SeqTp", SeqTp);

                Element InstdAmtElement = document.createElement("InstdAmt");
                InstdAmtElement.setAttribute("Ccy", tipo_moeda);
                InstdAmtElement.appendChild(document.createTextNode(String.valueOf(total)));
                DrctDbtTxInfElement.appendChild(InstdAmtElement);

                adicionarElementoetexto(document, DrctDbtTxInfElement, "ChrgBr", ChrgBr);

                Element DrctDbtTxElement = document.createElement("DrctDbtTx");
                DrctDbtTxInfElement.appendChild(DrctDbtTxElement);

                Element MndtRltdInfElement = document.createElement("MndtRltdInf");
                DrctDbtTxElement.appendChild(MndtRltdInfElement);

                adicionarElementoetexto(document, MndtRltdInfElement, "MndtId", MndtId);
                adicionarElementoetexto(document, MndtRltdInfElement, "DtOfSgntr", Date_Sign);
                adicionarElementoetexto(document, MndtRltdInfElement, "FnlColltnDt", String.valueOf(mes1_date));
                adicionarElementoetexto(document, MndtRltdInfElement, "Frqcy", Frqcy);

                Element DbtrAgtElement = document.createElement("DbtrAgt");
                DrctDbtTxInfElement.appendChild(DbtrAgtElement);

                Element FinInstnIdElement2 = document.createElement("FinInstnId");
                DbtrAgtElement.appendChild(FinInstnIdElement2);

                adicionarElementoetexto(document, FinInstnIdElement2, "BIC", fornecedor.getBic());

                Element DbtrElement = document.createElement("Dbtr");
                DrctDbtTxInfElement.appendChild(DbtrElement);

                adicionarElementoetexto(document, DbtrElement, "Nm", fornecedor.getNome());

                Element PstlAdrElement3 = document.createElement("PstlAdr");
                DbtrElement.appendChild(PstlAdrElement3);

                adicionarElementoetexto(document, PstlAdrElement3, "StrtNm", morada);
                adicionarElementoetexto(document, PstlAdrElement3, "BldgNb", fornecedor.getEndereco2());
                adicionarElementoetexto(document, PstlAdrElement3, "PstCd", fornecedor.getCod_fornecedor());
                adicionarElementoetexto(document, PstlAdrElement3, "TwnNm", fornecedor.getCidade());
                adicionarElementoetexto(document, PstlAdrElement3, "Ctry", abreviacao_fornecedor);

                Element DbtrAcctElement = document.createElement("DbtrAcct");
                DrctDbtTxInfElement.appendChild(DbtrAcctElement);

                Element IdElement2 = document.createElement("Id");
                DbtrAcctElement.appendChild(IdElement2);

                adicionarElementoetexto(document, IdElement2, "IBAN", fornecedor.getIban());

                Element PurpElement = document.createElement("Purp");
                DrctDbtTxInfElement.appendChild(PurpElement);

                adicionarElementoetexto(document, PurpElement, "Cd", Purp);

                Element RmtInfElement = document.createElement("RmtInf");
                DrctDbtTxInfElement.appendChild(RmtInfElement);

                adicionarElementoetexto(document, RmtInfElement, "Ustrd", mensagem);
            }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource domSource = new DOMSource(document);

            File directory = new File("SheetsAndPicks/src/main/Sepa_files/Debito");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String nomeArquivoXMLDebito =fornecedor.getCod_fornecedor() + "DD.xml";
            File outputFile = new File(directory, nomeArquivoXMLDebito);

            StreamResult streamResult = new StreamResult(outputFile);
            transformer.transform(domSource, streamResult);

            if(validarArquivoSEPA(String.valueOf(outputFile), "SheetsAndPicks/src/main/VerificadorDebito.xsd")){
                exibirPopUpSucesso();
                DBstatements.CriacaoCompleta_Sepa_Debito(id_pagamento,calendarDateTime,mes1_date);
            }
            else {
                exibirPopUpErro();
            }
        } catch (TransformerException | ParserConfigurationException e) {
            e.printStackTrace();
            exibirPopUpErro();
        }
    }

    public static void CriarXMLCredito(String cod ,String mensagem,String Total,String id_pagamento){
        Suppliers fornecedor = DBstatements.getSupplier(cod);
        String abreviacao_fornecedor=DBstatements.getabreviacao(fornecedor.getPais());
        LocalDateTime calendar_date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSS");
        String dataFormatada = calendar_date.format(formatter);
        LocalDateTime mes1_date = calendar_date.plusMonths(1);
        Integer quantidadestock = DBstatements.getquantidade(id_pagamento);
        List<String> moradas = DBstatements.getmorada(id_pagamento);
        BigDecimal valorDecimal = new BigDecimal(Total);
        List<Float> Totals = DBstatements.gettotal(id_pagamento);
        BigDecimal valorArredondado = valorDecimal.setScale(2, RoundingMode.HALF_UP);
        String valorFormatado = valorArredondado.toString();

        if (calendar_date.getMonthValue() == 12 && mes1_date.getMonthValue() == 1) {
            mes1_date = mes1_date.plusYears(1);
        }


        Admins Empresa = DBstatements.GetInfoEmpresa();
        String abreviacao_empresa=DBstatements.getabreviacao(Empresa.getPais());
        String Insrt_Id="F&S"+ Empresa.getId()+'/'+fornecedor.getId();
        String End_to_end="F&S"+fornecedor.getId()+'/'+Empresa.getId();
        String metodo=DBstatements.getMetodo(id_pagamento);
        String tipo_moeda=DBstatements.getmoedaencomenda(id_pagamento);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();


            Element rootElement = document.createElement("Document");
            document.appendChild(rootElement);

            rootElement.setAttribute("xmlns", "urn:iso:std:iso:20022:tech:xsd:pain.001.001.03");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            for (int i = 0; i < quantidadestock; i++) {
                String morada = moradas.get(i % moradas.size());
                Float total = Totals.get(i % Totals.size());

                Element cstmrDrctDbtInitnElement = document.createElement("CstmrCdtTrfInitn");
                rootElement.appendChild(cstmrDrctDbtInitnElement);

                Element GrpHdrElement = document.createElement("GrpHdr");
                cstmrDrctDbtInitnElement.appendChild(GrpHdrElement);

                adicionarElementoetexto(document, GrpHdrElement, "MsgId", mensagem);
                adicionarElementoetexto(document, GrpHdrElement, "CreDtTm", dataFormatada);
                adicionarElementoetexto(document, GrpHdrElement, "NbOfTxs", String.valueOf(quantidadestock));
                adicionarElementoetexto(document, GrpHdrElement, "CtrlSum", Total);

                Element initgPtyElement = document.createElement("InitgPty");
                GrpHdrElement.appendChild(initgPtyElement);

                adicionarElementoetexto(document, initgPtyElement, "Nm", Empresa.getNome());

                Element PstlAdrElement = document.createElement("PstlAdr");
                initgPtyElement.appendChild(PstlAdrElement);

                adicionarElementoetexto(document, PstlAdrElement, "StrtNm", morada + ',' + Empresa.getEndereco2());
                adicionarElementoetexto(document, PstlAdrElement, "PstCd", Empresa.getCod_postal());
                adicionarElementoetexto(document, PstlAdrElement, "TwnNm", Empresa.getCidade());
                adicionarElementoetexto(document, PstlAdrElement, "Ctry", abreviacao_empresa);

                Element PmtInfElement = document.createElement("PmtInf");
                cstmrDrctDbtInitnElement.appendChild(PmtInfElement);

                adicionarElementoetexto(document, PmtInfElement, "PmtInfId", id_pagamento);
                adicionarElementoetexto(document, PmtInfElement, "PmtMtd", metodo);
                adicionarElementoetexto(document, PmtInfElement, "BtchBookg", "false");
                adicionarElementoetexto(document, PmtInfElement, "ReqdExctnDt", String.valueOf(calendar_date));

                Element DbtrElement = document.createElement("Dbtr");
                PmtInfElement.appendChild(DbtrElement);

                adicionarElementoetexto(document, DbtrElement, "Nm", Empresa.getNome());

                Element PstlAdrElement3 = document.createElement("PstlAdr");
                DbtrElement.appendChild(PstlAdrElement3);

                adicionarElementoetexto(document, PstlAdrElement3, "StrtNm", morada + ',' + Empresa.getEndereco2());
                adicionarElementoetexto(document, PstlAdrElement3, "PstCd", Empresa.getCod_postal());
                adicionarElementoetexto(document, PstlAdrElement3, "TwnNm", Empresa.getCidade());
                adicionarElementoetexto(document, PstlAdrElement3, "Ctry", abreviacao_empresa);

                Element DbtrAcctElement = document.createElement("DbtrAcct");
                PmtInfElement.appendChild(DbtrAcctElement);

                Element IdElement2 = document.createElement("Id");
                DbtrAcctElement.appendChild(IdElement2);


                adicionarElementoetexto(document, IdElement2, "IBAN", Empresa.getIban());


                Element DbtrAgtElement = document.createElement("DbtrAgt");
                PmtInfElement.appendChild(DbtrAgtElement);

                Element FinInstnIdElement2 = document.createElement("FinInstnId");
                DbtrAgtElement.appendChild(FinInstnIdElement2);

                adicionarElementoetexto(document, FinInstnIdElement2, "BIC", Empresa.getBic());

                Element CdtTrfTxInfElement = document.createElement("CdtTrfTxInf");
                PmtInfElement.appendChild(CdtTrfTxInfElement);

                Element PmtIdElement = document.createElement("PmtId");
                CdtTrfTxInfElement.appendChild(PmtIdElement);

                adicionarElementoetexto(document, PmtIdElement, "InstrId", Insrt_Id);
                adicionarElementoetexto(document, PmtIdElement, "EndToEndId", End_to_end);

                Element AmtElement = document.createElement("Amt");
                CdtTrfTxInfElement.appendChild(AmtElement);

                Element InstdAmtElement = document.createElement("InstdAmt");
                InstdAmtElement.setAttribute("Ccy", tipo_moeda);
                InstdAmtElement.appendChild(document.createTextNode(String.valueOf(total)));
                AmtElement.appendChild(InstdAmtElement);

                Element CdtrAgtElement = document.createElement("CdtrAgt");
                CdtTrfTxInfElement.appendChild(CdtrAgtElement);

                Element FinInstnIdElement = document.createElement("FinInstnId");
                CdtrAgtElement.appendChild(FinInstnIdElement);

                adicionarElementoetexto(document, FinInstnIdElement, "BIC", fornecedor.getBic());

                Element CdtrElement = document.createElement("Cdtr");
                CdtTrfTxInfElement.appendChild(CdtrElement);

                adicionarElementoetexto(document, CdtrElement, "Nm", fornecedor.getNome());

                Element PstlAdrElement2 = document.createElement("PstlAdr");
                CdtrElement.appendChild(PstlAdrElement2);

                adicionarElementoetexto(document, PstlAdrElement2, "StrtNm", morada + ',' + fornecedor.getEndereco2());
                adicionarElementoetexto(document, PstlAdrElement2, "PstCd", fornecedor.getCod_postal());
                adicionarElementoetexto(document, PstlAdrElement2, "TwnNm", fornecedor.getCidade());
                adicionarElementoetexto(document, PstlAdrElement2, "Ctry", abreviacao_fornecedor);

                Element CdtrAcctElement = document.createElement("CdtrAcct");
                CdtTrfTxInfElement.appendChild(CdtrAcctElement);

                Element IdElement = document.createElement("Id");
                CdtrAcctElement.appendChild(IdElement);

                adicionarElementoetexto(document, IdElement, "IBAN", fornecedor.getIban());

                Element RmtInfElement = document.createElement("RmtInf");
                CdtTrfTxInfElement.appendChild(RmtInfElement);

                Element StrdElement = document.createElement("Strd");
                RmtInfElement.appendChild(StrdElement);

                Element RfrdDocInfElement = document.createElement("RfrdDocInf");
                StrdElement.appendChild(RfrdDocInfElement);

                adicionarElementoetexto(document, RfrdDocInfElement, "Nb", id_pagamento);
                adicionarElementoetexto(document, RfrdDocInfElement, "RltdDt", String.valueOf(mes1_date));

            }
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource domSource = new DOMSource(document);

            File directory = new File("SheetsAndPicks/src/main/Sepa_files/Credito");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String nomeArquivoXMLCredito = fornecedor.getCod_fornecedor() + "C.xml";
            File outputFile = new File(directory, nomeArquivoXMLCredito);

            StreamResult streamResult = new StreamResult(outputFile);

            transformer.transform(domSource, streamResult);


        if (validarArquivoSEPA(String.valueOf(outputFile), "SheetsAndPicks/src/main/VerificadorCredito.xsd")) {
            DBstatements.CriacaoCompleta_Sepa_Credito(id_pagamento,calendar_date,mes1_date);
            exibirPopUpSucesso();
        } else {
            exibirPopUpErro();
        }
        } catch (TransformerException e) {
            e.printStackTrace();
            exibirPopUpErro();
        }catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean validarArquivoSEPA(String nomeArquivoXML, String caminhoXSD) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(caminhoXSD));
            Validator validator = schema.newValidator();

            Source source = new StreamSource(new File(nomeArquivoXML));
            validator.validate(source);

            return true;
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void adicionarElementoetexto(Document document, Element parent, String elementName, String textContent) {
        Element element = document.createElement(elementName);
        element.appendChild(document.createTextNode(textContent));
        parent.appendChild(element);
    }

    private static void exibirPopUpSucesso() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Arquivo XML criado com sucesso!");
        alert.showAndWait();
    }

    private static void exibirPopUpErro() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText("Arquivo XML não foi criado com sucesso!");
        alert.showAndWait();
    }

    public static float Listar_v_total(ObservableList<Stock> a){
        float v_total = 0;
        for (Stock stock : a) {
            float v_base = stock.getV_base();
            float v_taxa = stock.getV_taxa();
            float v_total_conta= v_base+v_taxa;
            String moeda = stock.getMoeda();
            System.out.println(moeda);
            float taxa = DBstatements.gettaxaeuro(moeda);
            System.out.println(taxa);
            System.out.println(v_total_conta);
            System.out.println(v_base);
            System.out.println(v_taxa);
            v_total+=v_total_conta * taxa;
        }
        System.out.println(v_total);
        return v_total;
    }

    public static float Listar_v_totalmoeda(ObservableList<Stock> a,String moeda){
        float v_total = 0;
        float taxaeuro = 0;
        if (moeda != null) {
            taxaeuro = DBstatements.gettaxaeuro(moeda);
            System.out.println(taxaeuro);
        }
        for (Stock stock : a) {
            float v_base = stock.getV_base();
            float v_taxa = stock.getV_taxa();
            float v_total_conta= v_base+v_taxa;

            v_total+=v_total_conta;
        }
        v_total= v_total * taxaeuro;
        return v_total;
    }

    public static ArrayList<Integer> Lista_id(ObservableList<Stock> a){
            ArrayList<Integer> stockInfoList = new ArrayList<>();

            for (Stock stock : a) {
                int id = stock.getId();

                stockInfoList.add(id);
            }

            return stockInfoList;
    }

    public static void openAceitar_SepaController(Sepa sepa,String cod, String nome){
        String id_pagamento = sepa.getId_pagamento();
        String Metodo = sepa.getMetodo();
        LocalDate date= sepa.getData();
        String mensagem = sepa.getMensagem();
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Pagamentos/dash_aprovar_Sepa.fxml"));
            Parent root = loader.load();
            Aceitar_SepaController controller = loader.getController();
            controller.setFuncionario(cod,nome,id_pagamento,Metodo,date,mensagem);

            controller.setSepaUpdateCallback(() -> {
                if (sepaUpdateCallback != null) {
                    sepaUpdateCallback.onUpdate();
                }
            });

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Carregarfornecedor(String cod_fornecedor, String name, Button btn_fornecedor_movimentos) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/example/sheetsandpicks/Stock/app_fornecedor_movimentos.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        app_fornecedor_movimentosController app_lista_sepasFornecedorController = fxmlLoader.getController();
        app_lista_sepasFornecedorController.setFornecedor(cod_fornecedor,name);

        Stage stage = new Stage();
        stage.setTitle("Lista Das Sepas");
        stage.setScene(scene);
        stage.setMaximized(true);
        Stage janelaLogin = (Stage) btn_fornecedor_movimentos.getScene().getWindow();
        janelaLogin.close();
        stage.show();
    }

    public static void open_SepaController(Sepa sepa,String cod, String nome){
        String id_pagamento = sepa.getId_pagamento();
        String Metodo = sepa.getMetodo();
        LocalDate date= sepa.getData();
        String mensagem = sepa.getMensagem();
        try {
            FXMLLoader loader = new FXMLLoader(UsersFunctions.class.getResource("/com/example/sheetsandpicks/Pagamentos/DashSepaInfo.fxml"));
            Parent root = loader.load();
            DashSepaInfoController controller = loader.getController();
            controller.setFuncionario(cod,nome,id_pagamento,Metodo,date,mensagem);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
