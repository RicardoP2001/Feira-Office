package com.example.sheetsandpicks.Controllers.Encomenda;

import com.example.util.Models.OrderResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * Controlador para a janela pop-up de confirmação de encomenda.
 */
public class Pop_up_ConfirmarEncomenda {

    @FXML
    private Button btn_ok;

    @FXML
    private ComboBox<String> cmb_aceept_reject;

    @FXML
    private Label lb_produto;

    private ConfirmaEncomendaController a;

    private OrderResponse encomenda;
    /**
     * Manipula a ação do utilizador ao escolher uma opção de confirmação.
     * Define o status da encomenda com base na opção escolhida e atualiza a tabela principal.
     * Fecha a janela pop-up.
     *
     * @param event O evento de ação gerado pela escolha do utilizador.
     */
    

    @FXML
    void Handle_Confirmation_Option(ActionEvent event) {
        if(cmb_aceept_reject.getValue().equals("Aprovado")) {
            encomenda.setStatus(2);
        }else{
            encomenda.setStatus(3);
        }
        a.TabelaConfirmar_Encomenda(encomenda);
        Stage stage = (Stage) btn_ok.getScene().getWindow();
        stage.close();
    }
    /**
     * Define a instância do controlador principal para atualizações na tabela principal.
     *
     * @param TabelaConfirmar_StockFill A instância do controlador principal.
     */
    public void setTabelaConfirmar_StockFill(ConfirmaEncomendaController TabelaConfirmar_StockFill){
        this.a = TabelaConfirmar_StockFill;
    }
    /**
     * Define a encomenda associada à janela pop-up e exibe informações na interface gráfica.
     *
     * @param encomenda A encomenda associada.
     */
    public void setEncomenda(OrderResponse encomenda){
        this.encomenda=encomenda;
        lb_produto.setText("Encomenda:"+encomenda.getId());
    }


}
