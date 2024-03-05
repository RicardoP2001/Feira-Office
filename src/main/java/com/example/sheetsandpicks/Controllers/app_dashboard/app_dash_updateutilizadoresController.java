package com.example.sheetsandpicks.Controllers.app_dashboard;

import com.example.sheetsandpicks.BL.Database.DBstatements;
import com.example.sheetsandpicks.Controllers.Users.AdminsController;
import com.example.sheetsandpicks.Controllers.Users.OperatorsController;
import com.example.sheetsandpicks.Controllers.Users.SuppliersController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Controlador para a interface de atualização de utilizadores no painel de controle da aplicação.
 */
public class app_dash_updateutilizadoresController {
    @FXML
    private Button btn_Cancelar;

    @FXML
    private Label lblusername;

    @FXML
    private Label lblpassword;

    @FXML
    private Button btn_adicionar;

    @FXML
    private ComboBox<String> cmb_pais;

    @FXML
    private DatePicker dpicker_data;

    @FXML
    private Label lb_titulo;

    @FXML
    private TextField txt_cidade;

    @FXML
    private TextField txt_cod_postal;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_endereco;


    @FXML
    private Label lbl_cod_fornecedor;

    @FXML
    private TextField txt_cod_fornecedor;

    @FXML
    private TextField txt_endereco2;

    @FXML
    private TextField txt_nome;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_telefone;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_BIC;

    @FXML
    private TextField txt_IBAN;

    private String funcionario;

    public String username;
    public String passwordMain;
    public Integer idear;

    private Callback<DatePicker, DateCell> definirdata() {
        /**
         * Cria e retorna uma nova célula de data com a lógica para desabilitar datas futuras.
         *
         * @param datePicker O componente DatePicker associado à célula.
         * @return A célula de data criada.
         */

        /**
         * Atualiza o conteúdo da célula de data.
         *
         * @param item  A data associada à célula.
         * @param empty Indica se a célula está vazia.
         */
        return datePicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        };
    }
    
    /**
     * Gera a hash SHA-256 para uma senha fornecida.
     *
     * @param password A senha a ser hashada.
     * @return A representação hexadecimal da hash SHA-256 da senha.
     */
    public static String hashWithSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Define o tipo de funcionário para a janela de criação e atualiza os elementos da interface de utilizador
     * com base no tipo de funcionário especificado.
     *
     * @param Funcionario O tipo de funcionário (Operador, Fornecedor ou Admin ).
     * @throws SQLException Se ocorrer um erro ao obter informações do banco de dados.
     */
    public void setFuncionario(String Funcionario) throws SQLException {
        lb_titulo.setText(Funcionario);
        this.funcionario = Funcionario;
        cmb_pais.setItems(DBstatements.getPais());
        dpicker_data.setDayCellFactory(definirdata());

        if(!funcionario.equals("Fornecedor")){
            lbl_cod_fornecedor.setManaged(false);
            txt_cod_fornecedor.setManaged(false);
        }
    }

    /**
     * Manipula a ação de alterar informações do funcionário/cliente e atualiza os dados na base de dados.
     * Exibe uma mensagem de sucesso após a conclusão.
     *
     * @param event O evento de ação associado à ação de alterar.
     * @throws SQLException Se ocorrer um erro ao acessar ou atualizar o banco de dados.
     */
    public void HandlerAlterar(ActionEvent event) throws SQLException {
        String password;
        String cod_fornecedor=null;
        String Nome = txt_nome.getText();
        String endereco = txt_endereco.getText();
        String endereco2 = txt_endereco2.getText();
        String cidade = txt_cidade.getText();
        String pais = String.valueOf(cmb_pais.getValue());
        String telefone = txt_telefone.getText();
        String email = txt_email.getText();
        String cod_postal = txt_cod_postal.getText();
        LocalDate Data_nascimento = dpicker_data.getValue();
        String IBAN = txt_IBAN.getText();
        String BIC = txt_BIC.getText();
        String username = txt_username.getText();
        System.out.println(Nome+","+endereco+","+endereco2+","+cidade+","+pais+","+telefone+","+email+","+cod_postal+","+ Data_nascimento +","+username+","+cod_fornecedor+","+IBAN+","+BIC+"!");

        if (Nome.isEmpty() || endereco.isEmpty() || cidade.isEmpty() || pais.isEmpty() || telefone.isEmpty()
                || email.isEmpty() || cod_postal.isEmpty() || Data_nascimento == null || username.isEmpty() || IBAN.isEmpty() || BIC.isEmpty()) {
            HandlerErrado("Preencha tudo corretamente");
            return;
        }
        int telefones;
        try {
             telefones = Integer.parseInt(telefone);
        } catch (NumberFormatException e) {
        HandlerErrado("Formato inválido para número de telefone.");
        return;
        }

        if (!isValidEmail(email)) {
            HandlerErrado("Preencha o Email corretamente");
            return;
        }

        if(txt_cod_fornecedor.isManaged() && txt_cod_fornecedor.getText() != null){
            boolean verificar_cod_fornecedor=DBstatements.verificarcod(cod_fornecedor);
            if(!verificar_cod_fornecedor){
                HandlerErrado("Já existe esse cod_fornecedor forneça outro!");
                return;
            }
        }

        if (!isValidLength(username, 4) || !isValidLength(Nome, 4) || !isValidLength(endereco, 4) || !isValidLength(endereco2, 4) || !isValidLength(cidade, 4)) {
            HandlerErrado("O Username, Nome, Endereco, Endereco2 e Cidade deve ter pelo menos 4 caracteres.");
            return;
        }

        if (hasLeadingSpace(Nome) || hasLeadingSpace(endereco) || hasLeadingSpace(cidade) || hasLeadingSpace(pais) || hasLeadingSpace(telefone)
                || hasLeadingSpace(email) || hasLeadingSpace(cod_postal) || Data_nascimento == null || hasLeadingSpace(username) || hasLeadingSpace(BIC) || hasLeadingSpace(IBAN)) {
            HandlerErrado("Preencha tudo corretamente, sem espaços.");
            return;
        }

        if (!isValidPhoneNumber(telefone)) {
            HandlerErrado("O telefone deve conter exatamente 9 dígitos numéricos.");
            return;
        }


        if (containsSpecialCharacters(Nome) || containsSpecialCharacters(endereco) || containsSpecialCharacters(cidade) || containsSpecialCharacters(telefone) || containsSpecialCharacters(BIC) || containsSpecialCharacters(IBAN)) {
            HandlerErrado("Nome, Endereço, Cidade,Telefone, IBAN e Bic não devem conter caracteres especiais.");
            return;
        }

        if (!isValidPostalCode(cod_postal)) {
            HandlerErrado("O formato do Código Postal deve ser XXXX-XXX (4 dígitos, seguido de '-', e mais 3 dígitos).");
            return;
        }

        if (!isValidUsername(username)) {
            HandlerErrado("O Username não pode começar com um caractere especial.");
            return;
        }

        if(!isValidBIC(BIC)){
            HandlerErrado("BIC inválido");
            return;
        }

        if(!isValidIBAN(IBAN)){
            HandlerErrado("IBAN inválido");
            return;
        }
        if (cod_fornecedor != null && isValidCodFornecedor(cod_fornecedor)) {
            HandlerErrado("O Código do Fornecedor não pode começar com um caractere especial.");
            return;
        }

        if(funcionario.equals("Cliente")) {

        }
        else {
            if (!txt_password.getText().isEmpty()){
                password = hashWithSHA256(txt_password.getText()) ;
                if (hasLeadingSpace(password)){
                    HandlerErrado("Não use espaços para criar uma palavra-passe");
                    return;
                }
            }
            else{
                password = passwordMain;
            }
            DBstatements.Updateusers(idear, Nome, endereco, endereco2, cidade, pais, telefones, email, cod_postal, Data_nascimento, username, password, cod_fornecedor, IBAN, BIC);
            HandlerConfirmado();
            if (adminsController != null) {
                adminsController.updatetable();
            }
            if (operatorsController != null) {
                operatorsController.updatetable();
            } else {
                suppliersController.updatetable();
            }
        }
    }

    /**
     * Manipula a ação de cancelar a alteração de informações do funcionário/cliente.
     * Exibe uma mensagem de confirmação antes de fechar a janela de alteração.
     *
     * @param event O evento de ação associado à ação de cancelar.
     */
    @FXML
    void HandlerCancelar(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText("Confirmação de ação");
        a.setContentText("Deseja mesmo sair da Alteração de " + funcionario + " ?");

        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {

            if (option.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
                stage.close();
            }
        }
    }

    /**
     * Manipula a confirmação bem-sucedida da alteração de informações do funcionário/cliente.
     * Exibe uma mensagem de sucesso antes de fechar a janela de alteração.
     */
    public void HandlerConfirmado() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("SUCESSO");
        a.setContentText("O " + funcionario + " foi alterado com sucesso");

        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {

            if (option.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
                stage.close();
            }
        }
    }
    /**
     * Exibe uma caixa de diálogo de erro com a mensagem fornecida.
     *
     * @param mensagem A mensagem de erro a ser exibida.
     */
    public void HandlerErrado(String mensagem) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(mensagem);
        a.setContentText("O " + funcionario + " não foi adicionado");

        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                a.close();
            }
        }
    }
    /**
     * Verifica se o BIC possui o formato correto.
     *
     * @param bic O BIC a ser verificado.
     * @return true se o BIC for válido, false caso contrário.
     */
    public static boolean isValidBIC(String bic) {
        String bicRegex = "^[A-Za-z]{6}[A-Za-z0-9]{2}([A-Za-z0-9]{3})?$";
        return Pattern.matches(bicRegex, bic);
    }
    /**
     * Verifica se o IBAN possui o formato correto.
     *
     * @param iban O IBAN a ser verificado.
     * @return true se o IBAN for válido, false caso contrário.
     */
    public static boolean isValidIBAN(String iban) {
        iban = iban.replaceAll("\\s", "").replaceAll("-", "");
        String regex = "^[A-Z]{2}\\d{2}[A-Z0-9]{4}\\d{7}([A-Z0-9]?){0,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(iban);
        return matcher.matches();
    }
    /**
     * Verifica se o código postal possui o formato correto.
     *
     * @param postalCode O código postal a ser verificado.
     * @return true se o código postal for válido, false caso contrário.
     */
    public boolean isValidPostalCode(String postalCode) {
        return postalCode.matches("\\d{4}-\\d{3}");
    }
    /**
     * Verifica se o número de telefone possui o formato correto.
     *
     * @param phone O número de telefone a ser verificado.
     * @return true se o número de telefone for válido, false caso contrário.
     */
    public boolean isValidPhoneNumber(String phone) {
        return phone.matches("[0-9]{9}");
    }
    /**
     * Verifica se a string contém caracteres especiais.
     *
     * @param input A string a ser verificada.
     * @return true se a string não contiver caracteres especiais, false caso contrário.
     */
    public boolean containsSpecialCharacters(String input) {
        return !input.matches("[a-zA-Z0-9 ]*");
    }
    /**
     * Verifica se a string possui espaços no início.
     *
     * @param input A string a ser verificada.
     * @return true se a string não possuir espaços no início, false caso contrário.
     */
    public boolean hasLeadingSpace(String input) {
        return input.startsWith(" ") || input.isBlank();
    }
    /**
     * Verifica se o endereço de email possui o formato correto.
     *
     * @param email O endereço de email a ser verificado.
     * @return true se o endereço de email for válido, false caso contrário.
     */
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
    /**
     * Verifica se o username possui o formato correto.
     *
     * @param username O username a ser verificado.
     * @return true se o username for válido, false caso contrário.
     */
    public boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z0-9].*");
    }

    /**
     * Verifica se o código do fornecedor possui o formato correto.
     *
     * @param codFornecedor O código do fornecedor a ser verificado.
     * @return true se o código do fornecedor for válido, false caso contrário.
     */
    public boolean isValidCodFornecedor(String codFornecedor) {
        return codFornecedor.matches("[a-zA-Z0-9].*");
    }
    /**
     * Verifica se a string possui um comprimento mínimo.
     *
     * @param input  A string a ser verificada.
     * @param length O comprimento mínimo permitido.
     * @return true se a string atender ao comprimento mínimo, false caso contrário.
     */
    public boolean isValidLength(String input, int length) {
        return input.length() >= length;
    }

    /**
     * Define os dados do utilizador ou funcionário nos campos de entrada.
     *
     * @param id            O ID do utilizador ou funcionário.
     * @param nome          O nome do utilizador ou funcionário.
     * @param endereco      O endereço do utilizador ou funcionário.
     * @param pais          O país do utilizador ou funcionário.
     * @param endereco2     O segundo endereço do utilizador ou funcionário.
     * @param cidade        A cidade do utilizador ou funcionário.
     * @param telefone      O número de telefone do utilizador ou funcionário.
     * @param email         O endereço de e-mail do utilizador ou funcionário.
     * @param codPostal     O código postal do utilizador ou funcionário.
     * @param dataNascimento A data de nascimento do utilizador ou funcionário no formato "yyyy-MM-dd".
     * @throws SQLException Se ocorrer um erro ao acessar o banco de dados.
     */
    public void setdata(Integer id, String nome, String endereco, String pais,String endereco2, String cidade, Integer telefone, String email, String codPostal, String dataNascimento, String iban, String bic) throws SQLException {
        idear =id;
        username = DBstatements.getuser(id);
        passwordMain = DBstatements.getpass(id);

        txt_password.setText("");
        txt_username.setText(username);
        txt_nome.setText(nome);
        txt_endereco.setText(endereco);
        txt_endereco2.setText(endereco2);
        txt_cidade.setText(cidade);
        cmb_pais.setValue(pais);
        txt_telefone.setText(String.valueOf(telefone));
        txt_email.setText(email);
        txt_cod_postal.setText(codPostal);
        dpicker_data.setValue(LocalDate.parse(dataNascimento));
        txt_BIC.setText(bic);
        txt_IBAN.setText(iban);
    }
    /**
     * Configura o campo de texto do código do fornecedor com o código obtido do banco de dados.
     *
     * @throws SQLException Se ocorrer um erro ao acessar o banco de dados.
     */
    public void setdatacod_fornecedor() throws SQLException {
        String cod_fornecedor=DBstatements.getcodigo(username);

        txt_cod_fornecedor.setText(cod_fornecedor);
    }

    private AdminsController adminsController;
    /**
     * Define o controlador de administração associado a esta instância.
     *
     * @param adminsController O controlador de administração a ser associado.
     */
    public void setAdminscontroller(AdminsController adminsController) {
        this.adminsController = adminsController;
    }

    private OperatorsController operatorsController;
    /**
     * Define o controlador de operadores associado a esta instância.
     *
     * @param operatorsController O controlador de operadores a ser associado.
     */
    public void setOperatorsController(OperatorsController operatorsController) {
        this.operatorsController = operatorsController;
    }

    private SuppliersController suppliersController;
    /**
     * Define o controlador de fornecedores associado a esta instância.
     *
     * @param suppliersController O controlador de fornecedores a ser associado.
     */
    public void setSuppliersController(SuppliersController suppliersController) {
        this.suppliersController = suppliersController;
    }
}
