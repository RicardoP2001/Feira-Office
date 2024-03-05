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
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Controlador para a interface gráfica de adição de novos utilizadores (Operador, Fornecedor, Admin).
 */
public class app_dash_utilizadoresController{

    @FXML
    private Button btn_Cancelar;

    @FXML
    private Label lb_titulo;

    @FXML
    private Label lbl_IBAN;

    @FXML
    private Label lbl_cod_fornecedor;

    @FXML
    private Label lbl_BIC;

    @FXML
    private Label lbldata;

    @FXML
    private Label lblendereco2;

    @FXML
    private Label lbltelefone;

    @FXML
    private Label lblpassword;

    @FXML
    private Label lblusername;

    @FXML
    private Button btn_adicionar;

    @FXML
    private ComboBox<String> cmb_pais;

    @FXML
    private DatePicker dpicker_data;

    @FXML
    private TextField txt_cidade;

    @FXML
    private TextField txt_cod_postal;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_endereco;

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
    private TextField txt_cod_fornecedor;

    @FXML
    private TextField txt_BIC;

    @FXML
    private TextField txt_IBAN;

    private String funcionario;

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
            System.out.println("ERRO DE ENCRIPTAÇÃO");
            return null;
        }
    }

    /**
     * Define as informações do funcionário e realiza a inicialização de elementos da interface gráfica.
     *
     * @param Funcionario O tipo de funcionário (ex: "Fornecedores").
     * @throws SQLException Se ocorrer uma exceção ao acessar o banco de dados.
     */
    public void setFuncionario(String Funcionario) throws SQLException {
            lb_titulo.setText(Funcionario);
            this.funcionario = Funcionario;
            cmb_pais.setItems(DBstatements.getPais());
            dpicker_data.setDayCellFactory(definirdata());
        System.out.println(Funcionario);

            if(!Objects.equals(funcionario, "Fornecedor")){
                lbl_cod_fornecedor.setManaged(false);
                txt_cod_fornecedor.setManaged(false);
            }
            if(Objects.equals(funcionario, "Clientes")){
                lbl_IBAN.setManaged(false);
                txt_IBAN.setManaged(false);
                lblusername.setManaged(false);
                txt_username.setManaged(false);
                lblpassword.setManaged(false);
                txt_password.setManaged(false);
                lbldata.setManaged(false);
                dpicker_data.setManaged(false);
                lblendereco2.setManaged(false);
                txt_endereco2.setManaged(false);
                lbltelefone.setManaged(false);
                txt_telefone.setManaged(false);
                lbl_BIC.setManaged(false);
                txt_BIC.setManaged(false);
            }
    }


    /**
     * Manipula o evento de adição de um novo funcionário (Operador, Fornecedor ou Admin).
     * Coleta os dados dos campos de entrada, chama o método apropriado para criar o funcionário na base de dados
     * e exibe uma mensagem de confirmação.
     *
     * @throws SQLException Se ocorrer uma exceção ao acessar o banco de dados.
     */
    @FXML
    void HandlerAdicionar(ActionEvent event) throws SQLException {
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
        String username = txt_username.getText();
        String password = hashWithSHA256(txt_password.getText());
        String IBAN = txt_IBAN.getText();
        String BIC = txt_BIC.getText();
        if(funcionario.equals("Fornecedor") && !txt_cod_fornecedor.getText().isEmpty()) {
            cod_fornecedor = txt_cod_fornecedor.getText();
        }
        System.out.println(Nome+","+endereco+","+endereco2+","+cidade+","+pais+","+telefone+","+email+","+cod_postal+","+ Data_nascimento +","+username+","+password+","+cod_fornecedor+","+IBAN+","+BIC+"!");

        if (Nome.isEmpty() || endereco.isEmpty() || cidade.isEmpty() || pais.isEmpty() || telefone.isEmpty()
                || email.isEmpty() || cod_postal.isEmpty() || Data_nascimento == null || username.isEmpty() || Objects.requireNonNull(password).isEmpty() || BIC.isEmpty() || IBAN.isEmpty()) {
            HandlerErrado("Preencha tudo corretamente");
            return;
        }

        int telefones = 0;
        try {
            telefones = Integer.parseInt(txt_telefone.getText());
        } catch (NumberFormatException e) {
            HandlerErrado("Formato inválido para número de telefone.");
            return;
        }


        if (!isValidEmail(email)) {
            HandlerErrado("Preencha o Email corretamente");
            return;
        }

        if(cod_fornecedor!=null && txt_cod_fornecedor.isManaged()){
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
                || hasLeadingSpace(email) || hasLeadingSpace(cod_postal) || Data_nascimento == null || hasLeadingSpace(username) || Objects.requireNonNull(password).isEmpty() || hasLeadingSpace(password) || hasLeadingSpace(IBAN) || hasLeadingSpace(BIC)) {
            HandlerErrado("Preencha tudo corretamente, sem espaços no início.");
            return;
        }

        if (!isValidPhoneNumber(telefone)) {
            HandlerErrado("O telefone deve conter exatamente 9 dígitos numéricos.");
            return;
        }


        if (containsSpecialCharacters(Nome) || containsSpecialCharacters(endereco) || containsSpecialCharacters(cidade) || containsSpecialCharacters(telefone)) {
            HandlerErrado("Nome, Endereço, Cidade e Telefone não devem conter caracteres especiais.");
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
        if (cod_fornecedor != null && !isValidCodFornecedor(cod_fornecedor)) {
            HandlerErrado("O Código do Fornecedor não pode começar com um caractere especial.");
            return;
        }

        switch (funcionario) {
            case "Operador" -> {
                DBstatements.CriarOperador(Nome, endereco, endereco2, cidade, pais, telefones, email, cod_postal, Data_nascimento, username, password, BIC, IBAN);
                HandlerConfirmado();
                operatorsController.updatetable();
            }
            case "Fornecedor" -> {
                if (cod_fornecedor != null) {
                    DBstatements.CriarFornecedor(Nome, endereco, endereco2, cidade, pais, telefones, email, cod_postal, Data_nascimento, username, password, cod_fornecedor, BIC, IBAN);
                    HandlerConfirmado();
                    suppliersController.updatetable();
                } else {
                    HandlerErrado("Falta preencher o campo de cod_fornecedor!");
                }
            }
            case "Admin" -> {
                DBstatements.CriarAdmin(Nome, endereco, endereco2, cidade, pais, telefones, email, cod_postal, Data_nascimento, username, password, BIC, IBAN);
                HandlerConfirmado();
                adminsController.updatetable();
            }
            default -> System.out.println("ERRO , não reconheço o tipo");
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
     * Verifica se uma senha atende aos critérios de complexidade.
     *
     * @param passwordhere A senha a ser verificada.
     * @return {@code true} se a senha atende aos critérios, {@code false} caso contrário.
     */
    public static boolean isValidPassword(String passwordhere) {
        String pattern = "^(?=.*[a-zA-Z0-9])(?=.*[!@#$%^&*()-+=])(?=.*[0-9])(?=.*[A-Z]).{8,}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(passwordhere);
        return m.matches();
    }
    /**
     * Verifica se um código postal está no formato correto XXXX-XXX.
     *
     * @param postalCode O código postal a ser verificado.
     * @return {@code true} se o código postal estiver no formato correto, {@code false} caso contrário.
     */
    public boolean isValidPostalCode(String postalCode) {
        return postalCode.matches("\\d{4}-\\d{3}");
    }
    /**
     * Verifica se um número de telefone tem exatamente 9 dígitos numéricos.
     *
     * @param phone O número de telefone a ser verificado.
     * @return {@code true} se o número de telefone tiver 9 dígitos numéricos, {@code false} caso contrário.
     */
    public boolean isValidPhoneNumber(String phone) {
        return phone.matches("[0-9]{9}");
    }
    /**
     * Verifica se uma string contém caracteres especiais.
     *
     * @param input A string a ser verificada.
     * @return {@code true} se a string contiver caracteres especiais, {@code false} caso contrário.
     */
    public boolean containsSpecialCharacters(String input) {
        return !input.matches("[a-zA-Z0-9 ]*");
    }
    /**
     * Verifica se uma string começa com espaço em branco ou está em branco.
     *
     * @param input A string a ser verificada.
     * @return {@code true} se a string começar com espaço em branco ou estiver em branco, {@code false} caso contrário.
     */
    public boolean hasLeadingSpace(String input) {
        return input.startsWith(" ") || input.isBlank();
    }
    /**
     * Verifica se um endereço de email está no formato correto.
     *
     * @param email O endereço de email a ser verificado.
     * @return {@code true} se o endereço de email estiver no formato correto, {@code false} caso contrário.
     */
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
    /**
     * Verifica se um nome de utilizador está no formato correto.
     *
     * @param username O nome de utilizador a ser verificado.
     * @return {@code true} se o nome de utilizador estiver no formato correto, {@code false} caso contrário.
     */
    public boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z0-9].*");
    }

    /**
     * Verifica se um código de fornecedor começa com letra ou número.
     *
     * @param codFornecedor O código do fornecedor a ser verificado.
     * @return {@code true} se o código do fornecedor começar com letra ou número, {@code false} caso contrário.
     */
    public boolean isValidCodFornecedor(String codFornecedor) {
        return codFornecedor.matches("[a-zA-Z0-9].*");
    }
    /**
     * Verifica se uma string tem pelo menos o comprimento especificado.
     *
     * @param input  A string a ser verificada.
     * @param length O comprimento mínimo permitido.
     * @return {@code true} se a string tiver pelo menos o comprimento especificado, {@code false} caso contrário.
     */
    public boolean isValidLength(String input, int length) {
        return input.length() >= length;
    }

    /**
     * Manipula o evento de cancelamento da criação de um novo funcionário (Operador, Fornecedor ou Admin).
     * Exibe uma mensagem de confirmação e fecha a janela de criação caso o utilizador confirme a ação.
     */
    @FXML
    void HandlerCancelar() {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setHeaderText("Confirmação de ação");
        a.setContentText("Deseja mesmo sair da Criaço de " + funcionario + " ?");

        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {

            if (option.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
                stage.close();
            }
        }
    }

    private AdminsController adminsController;

    public void setAdminsController(AdminsController adminsController) {
        this.adminsController = adminsController;
    }

    public void HandlerConfirmado() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText("SUCESSO");
        a.setContentText("O " + funcionario + " foi adicionado com sucesso");

        Optional<ButtonType> option = a.showAndWait();
        if (option.isPresent()) {

            if (option.get() == ButtonType.OK) {
                Stage stage = (Stage) btn_Cancelar.getScene().getWindow();
                stage.close();
            }
        }
    }

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


    private SuppliersController suppliersController;
    public void setSuppliersController(SuppliersController suppliersController) {
        this.suppliersController = suppliersController;
    }
    private OperatorsController operatorsController;
    public void setOperatorsController(OperatorsController operatorsController) {
        this.operatorsController = operatorsController;
    }
}


