package com.example.sheetsandpicks.BL.Database;

import com.example.sheetsandpicks.Controllers.app_login.LoginService;
import com.example.sheetsandpicks.Controllers.database.DBConnect;
import com.example.sheetsandpicks.Models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBstatements {
    /**
     * Recupera uma lista de objetos Stock da base de dados com base no chamador.
     * Este método utiliza a classe DBConnect para obter uma conexão com a base de dados e
     * executa uma consulta SQL específica com base no chamador do método. A lista de objetos
     * Stock resultante é retornada, e o método é projetado para ser utilizado em contextos
     * específicos, como nas classes StockConfirmationController e AppDashVizualizarStock.
     *
     * @return Uma lista observável de objetos Stock recuperados da base de dados.
     */
    public static List<Stock> GetStock() {
        Connection conn = DBConnect.getInstance().getConnection();
        ObservableList<Stock> observableList = FXCollections.observableArrayList();
        ArrayList<Stock> StockConfirmar_List = new ArrayList<>();

        String query = "";
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length > 2) {
            StackTraceElement caller = stackTrace[2];
            switch (caller.getClassName()) {
                case "com.example.sheetsandpicks.Controllers.Stock.StockConfirmationController" -> query = "SELECT * FROM Listar_Stock_Confirmar ORDER BY id DESC;";
                case "com.example.sheetsandpicks.Controllers.Stock.AppDashVizualizarStock" -> query = "SELECT * FROM Listar_Stock ORDER BY id DESC;";
                default -> {
                    return StockConfirmar_List;
                }
            }
        }
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                Stock stock1 = AdicionarStock(resultSet);
                StockConfirmar_List.add(stock1);
            }
            observableList.addAll(StockConfirmar_List);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return observableList;
    }

    /**
     * Confirma ou rejeita itens de stock com base em uma lista de IDs e um status de aprovação.
     * Este método executa procedimentos armazenados na base de dados para confirmar ou rejeitar itens
     * de stock com base nos IDs fornecidos e no status de aprovação. Utiliza a classe DBConnect para
     * obter uma conexão com a base de dados e executa consultas SQL preparadas.
     *
     * @param Aprov O status de aprovação, onde "rejeitado" indica rejeição e outros valores indicam aprovação.
     * @throws SQLException Se ocorrer um erro durante a execução das consultas SQL.
     */
    public static void ConfirmarStock(StockAprov_rejeita Aprov) throws SQLException {
        String query;
        if (!Objects.equals(Aprov.getAprovacao_rejeita(), "Rejeitado")) {
            query = "EXEC ConfirmacaodeStock @id = ?";
        } else {
            query = "EXEC RejeitarStock @id = ?";
        }
        Connection connection = DBConnect.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        try {
            preparedStatement.setInt(1, Aprov.getId());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("ERRO ao processar o ID: " + Aprov.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
    }

    /**
     * Insere dados de uma encomenda na base de dados.
     * Este método executa um procedimento armazenado na base de dados para criar uma nova encomenda.
     * Utiliza a classe DBConnect para obter uma conexão com a base de dados e executa uma consulta SQL preparada.
     *
     * @param numeroEncomenda      O número da encomenda.
     * @param data                 A data da encomenda.
     * @param nomeFornecedor       O nome do fornecedor.
     * @param total                O valor total da encomenda.
     * @param pais                 O país da encomenda.
     * @param nome                 O nome do produto.
     * @param precoUni             O preço unitário do produto.
     * @param quantidade           A quantidade de produtos na encomenda.
     * @param pesoLiquido          O peso líquido do produto.
     * @param contagem             A contagem do produto.
     * @param UOM                  A unidade de medida do produto.
     * @param UOM2                 A segunda unidade de medida do produto.
     * @param UOM3                 A terceira unidade de medida do produto.
     * @param agenciaBuyer         A agência do comprador.
     * @param agenciaBuyerValor    O valor da agência do comprador.
     * @param agenciaSupplier      A agência do fornecedor.
     * @param agenciaSupplierValor O valor da agência do fornecedor.
     * @throws SQLException Se ocorrer um erro durante a execução da consulta SQL.
     */
    public static void enviarDados(String numeroEncomenda, LocalDate data, String nomeFornecedor, float total, String pais, String nome, float precoUni, int quantidade, float pesoLiquido, float contagem, String UOM, String UOM2, String UOM3, String agenciaBuyer, String agenciaBuyerValor, String agenciaSupplier, String agenciaSupplierValor, float taxa, String tipo_moeda, float v_base, float v_taxa, String morada) throws SQLException {
        String query = "EXEC Criar_Encomenda " +
                "@id=?, " +
                "@data=?, " +
                "@nome_fornecedor=?, " +
                "@Total=?, " +
                "@pais=?, " +
                "@taxa=?," +
                "@tipo_moeda=?," +
                "@nome=?, " +
                "@preco_uni=?, " +
                "@quantidade=?, " +
                "@peso_liquido=?, " +
                "@contagem=?, " +
                "@UOM=?, " +
                "@UOM2=?, " +
                "@UOM3=?, " +
                "@Agencia_buyer=?, " +
                "@Agencia_buyer_valor=?, " +
                "@Agencia_supplier=?, " +
                "@Agencia_supplier_valor=?," +
                "@v_base=?," +
                "@v_taxa=?," +
                "@morada=?;";

        Connection connection = DBConnect.getInstance().getConnection();
        PreparedStatement callableStatement = connection.prepareStatement(query);
        try {
            callableStatement.setString(1, numeroEncomenda);
            callableStatement.setDate(2, Date.valueOf(data));
            callableStatement.setString(3, nomeFornecedor);
            callableStatement.setFloat(4, total);
            callableStatement.setString(5, pais);
            callableStatement.setFloat(6, taxa);
            callableStatement.setString(7, tipo_moeda);
            callableStatement.setString(8, nome);
            callableStatement.setFloat(9, precoUni);
            callableStatement.setInt(10, quantidade);
            callableStatement.setFloat(11, pesoLiquido);
            callableStatement.setFloat(12, contagem);
            callableStatement.setString(13, UOM);
            callableStatement.setString(14, UOM2);
            callableStatement.setString(15, UOM3);
            callableStatement.setString(16, agenciaBuyer);
            callableStatement.setString(17, agenciaBuyerValor);
            callableStatement.setString(18, agenciaSupplier);
            callableStatement.setString(19, agenciaSupplierValor);
            callableStatement.setFloat(20, v_base);
            callableStatement.setFloat(21, v_taxa);
            callableStatement.setString(22, morada);

            int hasResults = callableStatement.executeUpdate();
            if (hasResults == 0) {
                System.out.println("ERRO!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
    }

    /**
     * Busca e retorna uma lista de objetos Suppliers da base de dados.
     * Este método utiliza o método obterDadosUsers para buscar dados do tipo "Fornecedor"
     * da base de dados e cria uma lista de objetos Suppliers com base nos resultados obtidos.
     *
     * @return Uma lista de objetos Suppliers representando fornecedores na base de dados.
     */
    public static List<Suppliers> buscarFornecedores() {
        List<Suppliers> fornecedores = new ArrayList<>();
        String tipo = "Fornecedor";
        try {
            ResultSet rs = obterDadosUsers(tipo);
            while (Objects.requireNonNull(rs).next()) {
                Suppliers fornecedor = criarFornecedor(rs);
                fornecedores.add(fornecedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return fornecedores;
    }

    /**
     * Obtém uma lista observável de nomes de países da base de dados.
     * Este método executa uma consulta SQL na base de dados para obter os nomes dos países
     * e cria uma lista observável de strings com base nos resultados obtidos.
     *
     * @return Uma lista observável de strings representando nomes de países.
     * @throws SQLException Se ocorrer um erro durante a execução da consulta SQL.
     */
    public static ObservableList<String> getPais() throws SQLException {
        ObservableList<String> Pais = FXCollections.observableArrayList();
        Connection conn = DBConnect.getInstance().getConnection();
        String query = "SELECT Pais FROM Ver_pais";
        PreparedStatement pstmt = conn.prepareStatement(query);
        ResultSet rs = pstmt.executeQuery();
        try {
            while (rs.next()) {
                String pais = rs.getString("Pais");
                Pais.add(pais);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return Pais;
    }

    /**
     * Obtém dados de utilizadores com base no tipo de permissão especificado.
     * Este método executa uma sequência de consultas SQL na base de dados para obter dados pessoais
     * de utilizadores com um determinado tipo de permissão e ativos. Utiliza a classe DBConnect para obter
     * uma conexão com a base de dados e executa consultas preparadas.
     *
     * @param tipo O tipo de permissão dos utilizadores a serem obtidos.
     * @return Um conjunto de resultados contendo os dados pessoais dos utilizadores.
     * @throws SQLException Se ocorrer um erro durante a execução das consultas SQL.
     */
    private static ResultSet obterDadosUsers(String tipo) throws SQLException {

        String query2 = "SELECT id FROM permissao WHERE Tipo = ? ";
        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query2);
        stmt.setString(1, tipo);
        ResultSet rs = stmt.executeQuery();
        try {
            if (rs.next()) {

                int id = rs.getInt("id");
                String query = "SELECT d.id, d.Nome, d.Endereco, d.Endereco2, d.Cidade, d.Telefone, d.Email, d.cod_postal, d.Data_nascimento, f.id_permissao, f.ativo, p.Pais, dp.IBAN , dp.BIC " +
                        "FROM Dados_Pessoais d " +
                        "JOIN Funcionarios f ON d.id = f.id_Dados " +
                        "JOIN Pais p ON p.id=d.id_pais " +
                        "JOIN Dados_pagamento dp ON d.id = dp.id_dados " +
                        "WHERE f.id_permissao = ? AND f.ativo = 'S' ; ";
                if (tipo.equals("Fornecedor")) {
                    query = "SELECT d.id, d.Nome, d.Endereco, d.Endereco2, d.Cidade, d.Telefone, d.Email, d.cod_postal, d.Data_nascimento, f.id_permissao, f.ativo, cf.cod_fornecedor, p.Pais, dp.IBAN , dp.BIC " +
                            "FROM Dados_Pessoais d " +
                            "JOIN Funcionarios f ON d.id = f.id_Dados " +
                            "JOIN Pais p ON p.id= d.id_pais " +
                            "JOIN Dados_pagamento dp ON d.id = dp.id_dados " +
                            "JOIN codigo_Fornecedor cf ON cf.id_funcionario = f.id " +
                            "WHERE f.id_permissao = ? AND f.ativo = 'S' ; ";
                }

                PreparedStatement stmt1 = conn.prepareStatement(query);
                stmt1.setInt(1, id);
                return stmt1.executeQuery();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Cria um objeto Suppliers com base nos dados obtidos da base de dados.
     * Este método recebe um conjunto de resultados (ResultSet) contendo dados de um fornecedor
     * e cria um objeto Suppliers com base nesses dados.
     *
     * @param rs Um conjunto de resultados contendo os dados do fornecedor.
     * @return Um objeto Suppliers criado com base nos dados do fornecedor.
     * @throws SQLException Se ocorrer um erro ao acessar os dados no ResultSet.
     */
    private static Suppliers criarFornecedor(ResultSet rs) throws SQLException {
        return new Suppliers(
                rs.getInt("id"),
                rs.getString("ativo"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("cod_postal"),
                rs.getString("endereco2"),
                rs.getString("cidade"),
                rs.getInt("id_permissao"),
                rs.getString("endereco"),
                rs.getDate("data_nascimento"),
                rs.getInt("telefone"),
                rs.getString("cod_fornecedor"),
                rs.getString("pais"),
                rs.getString("IBAN"),
                rs.getString("BIC")
        );
    }

    /**
     * Cria um novo operador (funcionário) na base de dados.
     * Este método executa um procedimento armazenado na base de dados para criar um novo funcionário
     * com o nível de permissão de operador. Utiliza a classe DBConnect para obter uma conexão com a
     * base de dados e executa uma consulta SQL preparada.
     *
     * @param Nome            O nome do operador.
     * @param Endereco        O endereço do operador.
     * @param Endereco2       O segundo endereço do operador.
     * @param Cidade          A cidade do operador.
     * @param pais            O país do operador.
     * @param Telefone        O número de telefone do operador.
     * @param Email           O endereço de e-mail do operador.
     * @param cod_postal      O código postal do operador.
     * @param Data_nascimento A data de nascimento do operador.
     * @param username        O nome de utilizador do operador.
     * @param password        A senha do operador.
     * @throws SQLException Se ocorrer um erro durante a execução da consulta SQL.
     */
    public static void CriarOperador(String Nome, String Endereco, String Endereco2, String Cidade, String pais, int Telefone,
                                     String Email, String cod_postal, LocalDate Data_nascimento, String username, String password, String BIC, String IBAN) throws SQLException {
        String query = "EXEC Criar_Funcionario" +
                " @Nome=?," +
                " @Endereco=?," +
                " @Endereco2=?," +
                " @Cidade=?," +
                " @pais=?," +
                " @Telefone=?," +
                " @Email=?," +
                " @cod_postal=?," +
                " @Data_nascimento=?," +
                " @username=?," +
                " @password=?," +
                " @nv_permissao=?," +
                " @cod_fornecedor=?," +
                "@Bic=?,"+
                "@IBAN=?;";
        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        try {

            pstmt.setString(1, Nome);
            pstmt.setString(2, Endereco);
            pstmt.setString(3, Endereco2);
            pstmt.setString(4, Cidade);
            pstmt.setString(5, pais);
            pstmt.setInt(6, Telefone);
            pstmt.setString(7, Email);
            pstmt.setString(8, cod_postal);
            pstmt.setDate(9, java.sql.Date.valueOf(Data_nascimento));
            pstmt.setString(10, username);
            pstmt.setString(11, password);
            pstmt.setInt(12, 5);
            pstmt.setString(13, null);
            pstmt.setString(14,BIC);
            pstmt.setString(15,IBAN);

            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                System.out.println("Erro!!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
    }

    /**
     * Cria um novo fornecedor na base de dados.
     * Este método executa um procedimento armazenado na base de dados para criar um novo funcionário
     * com o nível de permissão de fornecedor. Utiliza a classe DBConnect para obter uma conexão com o
     * base de dados e executa uma consulta SQL preparada.
     *
     * @param Nome            O nome do fornecedor.
     * @param Endereco        O endereço do fornecedor.
     * @param Endereco2       O segundo endereço do fornecedor.
     * @param Cidade          A cidade do fornecedor.
     * @param pais            O país do fornecedor.
     * @param Telefone        O número de telefone do fornecedor.
     * @param Email           O endereço de e-mail do fornecedor.
     * @param cod_postal      O código postal do fornecedor.
     * @param Data_nascimento A data de nascimento do fornecedor.
     * @param username        O nome de utilizador do fornecedor.
     * @param password        A senha do fornecedor.
     * @throws SQLException Se ocorrer um erro durante a execução da consulta SQL.
     */
    public static void CriarFornecedor(String Nome, String Endereco, String Endereco2, String Cidade, String pais, int Telefone,
                                       String Email, String cod_postal, LocalDate Data_nascimento, String username, String password, String cod_fornecedor, String BIC, String IBAN) throws SQLException {
        String query = "EXEC Criar_Funcionario" +
                " @Nome=?," +
                " @Endereco=?," +
                " @Endereco2=?," +
                " @Cidade=?," +
                " @pais=?," +
                " @Telefone=?," +
                " @Email=?," +
                " @cod_postal=?," +
                " @Data_nascimento=?," +
                " @username=?," +
                " @password=?," +
                " @nv_permissao=?," +
                " @cod_fornecedor=?,"+
                "@Bic=?,"+
                "@Iban=?;";
        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        try {

            pstmt.setString(1, Nome);
            pstmt.setString(2, Endereco);
            pstmt.setString(3, Endereco2);
            pstmt.setString(4, Cidade);
            pstmt.setString(5, pais);
            pstmt.setInt(6, Telefone);
            pstmt.setString(7, Email);
            pstmt.setString(8, cod_postal);
            pstmt.setDate(9, java.sql.Date.valueOf(Data_nascimento));
            pstmt.setString(10, username);
            pstmt.setString(11, password);
            pstmt.setInt(12, 4);
            pstmt.setString(13, cod_fornecedor);
            pstmt.setString(14,BIC);
            pstmt.setString(15,IBAN);

            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                System.out.println("Erro!!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
    }

    /**
     * Busca e retorna uma lista de objetos Operators do tipo "Operador" da base de dados.
     * Este método utiliza o método obterDadosUsers para buscar dados de utilizadores do tipo "Operador"
     * da base de dados e cria uma lista de objetos Operators com base nos resultados obtidos.
     *
     * @return Uma lista de objetos Operators representando operadores na base de dados.
     */
    public static List<Operators> buscarOperadores() {
        List<Operators> operadores = new ArrayList<>();
        String tipo = "Operador";
        try {
            ResultSet rs = obterDadosUsers(tipo);
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                Operators operador = criarOperador(rs);
                operadores.add(operador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return operadores;
    }

    /**
     * Cria e retorna um objeto Operators com base nos dados obtidos da base de dados.
     * Este método recebe um conjunto de resultados (ResultSet) contendo dados de um operador
     * e cria um objeto Operators com base nesses dados.
     *
     * @param rs Um conjunto de resultados contendo os dados do operador.
     * @return Um objeto Operators criado com base nos dados do operador.
     * @throws SQLException Se ocorrer um erro ao acessar os dados no ResultSet.
     */
    private static Operators criarOperador(ResultSet rs) throws SQLException {
        return new Operators(
                rs.getInt("id"),
                rs.getString("ativo"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("cod_postal"),
                rs.getString("endereco2"),
                rs.getString("cidade"),
                rs.getInt("id_permissao"),
                rs.getString("endereco"),
                rs.getDate("data_nascimento"),
                rs.getInt("telefone"),
                rs.getString("Pais"),
                rs.getString("IBAN"),
                rs.getString("BIC")
        );
    }

    /**
     * Busca e retorna uma lista de objetos Admins do tipo "Admin" da base de dados.
     * Este método utiliza o método obterDadosUsers para buscar dados de utilizadores do tipo "Admin"
     * da base de dados e cria uma lista de objetos Admins com base nos resultados obtidos.
     *
     * @return Uma lista de objetos Admins representando administradores na base de dados.
     */
    public static List<Admins> buscarAdmins() {
        List<Admins> admins = new ArrayList<>();
        String tipo = "Admin";
        try {
            ResultSet rs = obterDadosUsers(tipo);
            while (Objects.requireNonNull(rs).next()) {
                Admins admin = criarAdmin(rs);
                admins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return admins;
    }

    /**
     * Cria e retorna um objeto Admins com base nos dados obtidos da base de dados.
     * Este método recebe um conjunto de resultados (ResultSet) contendo dados de um administrador
     * e cria um objeto Admins com base nesses dados.
     *
     * @param rs Um conjunto de resultados contendo os dados do administrador.
     * @return Um objeto Admins criado com base nos dados do administrador.
     * @throws SQLException Se ocorrer um erro ao acessar os dados no ResultSet.
     */
    private static Admins criarAdmin(ResultSet rs) throws SQLException {
        return new Admins(
                rs.getInt("id"),
                rs.getString("ativo"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("cod_postal"),
                rs.getString("endereco2"),
                rs.getString("cidade"),
                rs.getInt("id_permissao"),
                rs.getString("endereco"),
                rs.getDate("data_nascimento"),
                rs.getInt("telefone"),
                rs.getString("Pais"),
                rs.getString("IBAN"),
                rs.getString("BIC")
        );
    }

    /**
     * Cria um novo administrador na base de dados.
     * Este método executa um procedimento armazenado na base de dados para criar um novo administrador.
     * Utiliza a classe DBConnect para obter uma conexão com a base de dados e executa uma consulta SQL preparada.
     *
     * @param Nome            O nome do administrador.
     * @param Endereco        O endereço do administrador.
     * @param Endereco2       O segundo endereço do administrador.
     * @param Cidade          A cidade do administrador.
     * @param pais            O país do administrador.
     * @param Telefone        O número de telefone do administrador.
     * @param Email           O endereço de e-mail do administrador.
     * @param cod_postal      O código postal do administrador.
     * @param Data_nascimento A data de nascimento do administrador.
     * @param username        O nome de utilizador do administrador.
     * @param password        A senha do administrador.
     * @throws SQLException Se ocorrer um erro durante a execução da consulta SQL.
     */
    public static void CriarAdmin(String Nome, String Endereco, String Endereco2, String Cidade, String pais, int Telefone,
                                  String Email, String cod_postal, LocalDate Data_nascimento, String username, String password, String BIC, String IBAN) throws SQLException {
        String query = "EXEC Criar_Funcionario" +
                " @Nome=?," +
                " @Endereco=?," +
                " @Endereco2=?," +
                " @Cidade=?," +
                " @pais=?," +
                " @Telefone=?," +
                " @Email=?," +
                " @cod_postal=?," +
                " @Data_nascimento=?," +
                " @username=?," +
                " @password=?," +
                " @nv_permissao=?," +
                " @cod_fornecedor=?," +
                "@Bic=?,"+
                "@Iban=?;";

        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        try {

            pstmt.setString(1, Nome);
            pstmt.setString(2, Endereco);
            pstmt.setString(3, Endereco2);
            pstmt.setString(4, Cidade);
            pstmt.setString(5, pais);
            pstmt.setInt(6, Telefone);
            pstmt.setString(7, Email);
            pstmt.setString(8, cod_postal);
            pstmt.setDate(9, java.sql.Date.valueOf(Data_nascimento));
            pstmt.setString(10, username);
            pstmt.setString(11, password);
            pstmt.setInt(12, 6);
            pstmt.setString(13, null);
            pstmt.setString(14,BIC);
            pstmt.setString(15,IBAN);

            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                System.out.println("Erro!!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
    }

    /**
     * Atualiza as informações de um utilizador na base de dados.
     * Este método executa um procedimento armazenado na base de dados para alterar as informações de um utilizador.
     * Utiliza a classe DBConnect para obter uma conexão com a base de dados e executa uma consulta SQL preparada.
     *
     * @param id             O ID do utilizador a ser atualizado.
     * @param nome           O novo nome do utilizador.
     * @param endereco       O novo endereço do utilizador.
     * @param endereco2      O novo segundo endereço do utilizador.
     * @param cidade         A nova cidade do utilizador.
     * @param pais           O novo país do utilizador.
     * @param telefone       O novo número de telefone do utilizador.
     * @param email          O novo endereço de e-mail do utilizador.
     * @param codPostal      O novo código postal do utilizador.
     * @param dataNascimento A nova data de nascimento do utilizador.
     * @param username       O novo nome de utilizador do utilizador.
     * @param password       A nova senha do utilizador.
     * @throws SQLException Se ocorrer um erro durante a execução da consulta SQL.
     */
    public static void Updateusers(Integer id, String nome, String endereco, String endereco2, String cidade, String pais, int telefone, String email, String codPostal, LocalDate dataNascimento, String username, String password, String cod_fornecedor, String IBAN, String BIC) throws SQLException {
        System.out.println(id+","+nome+","+endereco+","+endereco2+","+cidade+","+pais+","+telefone+","+email+","+codPostal+","+ dataNascimento +","+username+","+password+","+cod_fornecedor+","+IBAN+","+BIC+"!");
        String query = "EXEC Alterar_Funcionario" +
                " @id=?," +
                " @Nome=?," +
                " @Endereco=?," +
                " @Endereco2=?," +
                " @Cidade=?," +
                " @pais=?," +
                " @Telefone=?," +
                " @Email=?," +
                " @cod_postal=?," +
                " @Data_nascimento=?," +
                " @username=?," +
                " @password=?," +
                " @cod_fornecedor=?,"+
                " @IBAN=?,"+
                " @BIC=?;";

        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        try {

            pstmt.setInt(1, id);
            pstmt.setString(2, nome);
            pstmt.setString(3, endereco);
            pstmt.setString(4, endereco2);
            pstmt.setString(5, cidade);
            pstmt.setString(6, pais);
            pstmt.setInt(7, telefone);
            pstmt.setString(8, email);
            pstmt.setString(9, codPostal);
            pstmt.setDate(10, Date.valueOf(dataNascimento));
            pstmt.setString(11, username);
            pstmt.setString(12, password);
            pstmt.setString(13, cod_fornecedor);
            pstmt.setString(14,IBAN);
            pstmt.setString(15,BIC);

            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                System.out.println("Erro!!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
    }

    /**
     * Elimina um funcionário da base de dados com base no ID de dados fornecido.
     * Este método executa um procedimento armazenado na base de dados para eliminar um funcionário com base no ID de dados.
     * Utiliza a classe DBConnect para obter uma conexão com a base de dados e executa uma consulta SQL preparada.
     *
     * @param id_dados O ID de dados do funcionário a ser eliminado.
     * @throws SQLException Se ocorrer um erro durante a execução da consulta SQL.
     */
    public static void Eliminar_Funcionario(int id_dados) throws SQLException {
        String query = "EXEC Eliminar_Funcionario" +
                " @id_dados=?";

        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query);
        try {

            pstmt.setInt(1, id_dados);

            int rs = pstmt.executeUpdate();
            if (rs == 0) {
                System.out.println("Erro!!!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
    }

    public static String getPaisid(Integer id) throws SQLException {
        String query2 = "SELECT id_pais FROM Dados_pessoais WHERE id = ?";
        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query2);
        try {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                int id_pais = rs.getInt("id_pais");
                String query = "SELECT Pais FROM Pais WHERE id = ?";

                PreparedStatement stmt1 = conn.prepareStatement(query);
                stmt1.setInt(1, id_pais);
                ResultSet rs2 = stmt1.executeQuery();
                if (rs2.next()) {
                    String pf = rs2.getString("Pais");
                    DBConnect.closeConnection();
                    return pf;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
        return null;
    }

    /**
     * Obtém o nome de utilizador associado a um funcionário com base no ID do funcionário.
     * Este método realiza consultas SQL na base de dados para obter o ID de acesso associado ao ID do funcionário
     * e, em seguida, recupera o nome de utilizador correspondente a partir da tabela de acesso.
     *
     * @param id O ID do funcionário para o qual se deseja obter o nome de utilizador.
     * @return O nome de utilizador associado ao funcionário, ou null se não encontrado.
     * @throws SQLException Se ocorrer um erro durante a execução das consultas SQL.
     */
    public static String getuser(Integer id) throws SQLException {
        String query2 = "SELECT id_acesso FROM Funcionarios WHERE id_Dados = ?";
        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query2);

        try {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_acesso = rs.getInt("id_acesso");
                String query = "SELECT username FROM acesso WHERE id = ?";

                PreparedStatement stmt1 = conn.prepareStatement(query);
                stmt1.setInt(1, id_acesso);
                ResultSet rs2 = stmt1.executeQuery();
                if (rs2.next()) {
                    String pf = rs2.getString("username");
                    DBConnect.closeConnection();
                    return pf;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
        return null;
    }

    /**
     * Obtém a senha associada a um funcionário com base no ID do funcionário.
     * Este método realiza consultas SQL na base de dados para obter o ID de acesso associado ao ID do funcionário
     * e, em seguida, recupera a senha correspondente a partir da tabela de acesso.
     *
     * @param id O ID do funcionário para o qual se deseja obter a senha.
     * @return A senha associada ao funcionário, ou null se não encontrada.
     * @throws SQLException Se ocorrer um erro durante a execução das consultas SQL.
     */
    public static String getpass(Integer id) throws SQLException {
        String query2 = "SELECT id_acesso FROM Funcionarios WHERE id_Dados = ?";
        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement stmt = conn.prepareStatement(query2);
        try {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id_acesso = rs.getInt("id_acesso");
                String query = "SELECT password FROM acesso WHERE id = ?";

                PreparedStatement stmt1 = conn.prepareStatement(query);
                stmt1.setInt(1, id_acesso);
                ResultSet rs2 = stmt1.executeQuery();
                if (rs2.next()) {
                    String pf = rs2.getString("password");
                    DBConnect.closeConnection();
                    return pf;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
        return null;
    }

    /**
     * Verifica se o login é válido para o nome de utilizador e palavra-passe fornecidos.
     *
     * @param username O nome de utilizador inserido.
     * @param password A palavra-passe inserida (ainda está criptografada com SHA-256).
     * @return true se o login for válido, false caso contrário.
     */
    public static boolean verifyLogin(String username, String password) {
        String query = "SELECT password FROM acesso WHERE username = ?";
        Connection conn = DBConnect.getInstance().getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("password");
                String inputHash = LoginService.hashPassword(password);

                if (storedHash.equals(inputHash)) {
                    DBConnect.closeConnection();
                    System.out.println("Login bem-sucedido.");
                    return true;
                } else {
                    System.out.println("Senha incorreta.");
                }
            } else {
                System.out.println("utilizador não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }

        return false;
    }

    /**
     * Obtém o nível de acesso de um utilizador com base no nome de utilizador.
     *
     * @param username O nome de utilizador do qual se deseja obter o nível de acesso.
     * @return O nível de acesso do utilizador ou null se não for encontrado.
     * @throws SQLException Se ocorrer uma exceção ao executar a consulta SQL.
     */
    public static String getNivelAcesso(String username) throws SQLException {
        Connection connection = DBConnect.getInstance().getConnection();
        String query = "SELECT p.Tipo AS nivel_permissao " +
                "FROM acesso a " +
                "JOIN Funcionarios f ON a.id = f.id_acesso " +
                "JOIN permissao p ON f.id_permissao = p.id " +
                "WHERE a.username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("SQL Query: " + query);
            if (resultSet.next()) {
                String pf = resultSet.getString("nivel_permissao");
                DBConnect.closeConnection();
                return pf;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return null;
    }

    public static List<Stock> buscarStocks(String nome) {
        List<Stock> stocks = new ArrayList<>();
        try {
            ResultSet rs = fornecedor_movimentos(nome);
            while (rs.next()) {
                Stock stock = criarMovimento(rs);
                stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return stocks;
    }

    public static ResultSet fornecedor_movimentos(String usernamesetter) throws SQLException {
        String query3 = "SELECT cod_fornecedor,id_stocks, Nome_Produto, quantidade, Preco_uni,Tipo_moeda,Tipo_UOM, Nome, Preco_uni * quantidade AS v_base ,Pais, id_Encomenda, data, rejeitado, V_taxa,Produto.GUID " +
                "FROM Stock " +
                "JOIN Produto ON Stock.id_produto=Produto.id " +
                "JOIN Encomenda ON Produto.id_Encomenda=Encomenda.id " +
                "JOIN Pais On Pais.id=Encomenda.id_pais " +
                "JOIN rel_UOM_Stock ON rel_UOM_Stock.id_stock = Stock.id_stocks " +
                "JOIN UOM ON UOM.id = rel_UOM_Stock.id_UOM " +
                "JOIN Funcionarios ON Encomenda.id_funcionario=Funcionarios.id " +
                "JOIN Dados_pessoais ON Dados_pessoais.id=Funcionarios.id_Dados " +
                "JOIN codigo_fornecedor ON Funcionarios.id = codigo_fornecedor.id_funcionario " +
                "JOIN Stock_Preco ON Stock.id_stocks = Stock_Preco.id_stock " +
                "WHERE codigo_fornecedor.cod_fornecedor = ? AND Stock.rejeitado = 'N'; ";
        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement stmt1 = conn.prepareStatement(query3);
        try {
            stmt1.setString(1, usernamesetter);
            return stmt1.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static Stock criarMovimento(ResultSet rs) throws SQLException {
        float v_taxa, v_base;
        return new Stock(
                rs.getString("cod_fornecedor"),
                rs.getString("id_Encomenda"),
                rs.getDate("data").toLocalDate(),
                rs.getInt("id_stocks"),
                rs.getString("Nome_Produto"),
                rs.getInt("quantidade"),
                rs.getString("Nome"),
                v_base = rs.getFloat("v_base"),
                rs.getString("Tipo_UOM"),
                rs.getFloat("Preco_uni"),
                rs.getString("Tipo_moeda"),
                rs.getString("Pais"),
                rs.getString("rejeitado"),
                v_taxa = rs.getFloat("V_taxa"),
                v_taxa + v_base,
                rs.getString("GUID")
                );
    }

    public static String getnome(String cod_fornecedor) throws SQLException {
        Connection connection = DBConnect.getInstance().getConnection();
        String query = "SELECT dp.Nome FROM Funcionarios f " +
                "JOIN Dados_pessoais dp ON dp.id=f.id_dados " +
                "JOIN codigo_fornecedor cd ON cd.id_funcionario=f.id " +
                "WHERE cd.cod_fornecedor = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setString(1, cod_fornecedor);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nome = resultSet.getString("Nome");
                System.out.println(nome);
                DBConnect.closeConnection();
                return nome;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }

        return null;

    }

    public static String getcodigo(String username) throws SQLException {
        Connection connection = DBConnect.getInstance().getConnection();
        String query = "SELECT id FROM acesso WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        try {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id_acesso = resultSet.getInt("id");
                System.out.println(id_acesso);
                String query2 = "SELECT id FROM Funcionarios WHERE id_acesso = ?";
                PreparedStatement stmt1 = connection.prepareStatement(query2);
                stmt1.setInt(1, id_acesso);
                ResultSet rs2 = stmt1.executeQuery();
                while (rs2.next()) {
                    int id_func = rs2.getInt("id");
                    System.out.println(id_func);
                    String query3 = "SELECT cod_fornecedor FROM codigo_Fornecedor WHERE id_funcionario = ?";
                    PreparedStatement stmt2 = connection.prepareStatement(query3);
                    try {
                        stmt2.setInt(1, id_func);
                        ResultSet rs3 = stmt2.executeQuery();
                        if (rs3.next()) {
                            String nome = rs3.getString("cod_fornecedor");
                            System.out.println(nome);
                            DBConnect.closeConnection();
                            return nome;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }

        return null;

    }

    public static Boolean numeroencomendafinder(String numeroEncomenda) {
        Connection connection = DBConnect.getInstance().getConnection();
        String query = "SELECT id FROM Encomenda WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, numeroEncomenda);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                DBConnect.closeConnection();
                return true;
            } else {
                DBConnect.closeConnection();
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
    }

    public static Boolean fornecedorfinder(String nomeFornecedor) {
        Connection connection = DBConnect.getInstance().getConnection();
        String query = "SELECT id FROM Dados_pessoais WHERE Nome = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, nomeFornecedor);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                DBConnect.closeConnection();
                return true;
            } else {
                DBConnect.closeConnection();
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
    }

    public static Admins GetInfoEmpresa() {
        int id = 52;
        Connection connection = DBConnect.getInstance().getConnection();
        String query = "SELECT Dados_pessoais.id as id , Nome, Endereco, Endereco2, Cidade, Telefone, Email, cod_postal, Data_nascimento , Pais.pais , Dados_pagamento.IBAN , Dados_pagamento.BIC , Funcionarios.ativo , Funcionarios.id_permissao FROM Dados_pessoais " +
                "                                  JOIN Funcionarios ON Funcionarios.id_dados=Dados_pessoais.id         " +
                "                                  JOIN Pais ON Pais.id=Dados_pessoais.id_pais     " +
                "                                  JOIN Dados_pagamento ON Dados_pessoais.id = Dados_pagamento.id_dados     " +
                "                                  WHERE Dados_pessoais.id= ? ;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Admins pf = criarAdmin(resultSet);
                DBConnect.closeConnection();
                return pf;
            }

        } catch (SQLException e) {
            System.out.println("ERRO!:" + e.getMessage());
        } finally {
            DBConnect.closeConnection();
        }
        return null;
    }


    public static void dadosficheirocredito(int id_empresa, int id_fornecedor, LocalDateTime atual, LocalDateTime finale, String Metodo, String Identificador, String End_to_end, String mensagem, float Total, String id_pagamento, String tipomoeda) {
        String query = "EXEC Registar_pagamento_Credito " +
                "@id_empresa = ?," +
                "@id_fornecedor = ?," +
                "@atual = ?," +
                "@finale = ?," +
                "@metodo = ?," +
                "@identificador = ?," +
                "@end_to_end = ?," +
                "@id_pagamento = ?," +
                "@mensagem = ?," +
                "@Total = ?," +
                "@tipomoeda = ?;";
        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(query);


            statement.setInt(1, id_empresa);
            statement.setInt(2, id_fornecedor);
            statement.setTimestamp(3, Timestamp.valueOf(atual));
            statement.setTimestamp(4, Timestamp.valueOf(finale));
            statement.setString(5, Metodo);
            statement.setString(6, Identificador);
            statement.setString(7, End_to_end);
            statement.setString(8, id_pagamento);
            statement.setString(9, mensagem);
            statement.setFloat(10, Total);
            statement.setString(11, tipomoeda);

            int rs = statement.executeUpdate();
            if (rs == 0) {
                System.out.println("Erro!!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
    }

    private static pagamento criarpagamento(ResultSet rs) throws SQLException {
        return new pagamento(
                rs.getInt("id"),
                rs.getString("Nome"),
                rs.getString("Email"),
                rs.getString("cod_postal"),
                rs.getString("Endereco"),
                rs.getInt("Telefone"),
                rs.getString("Endereco2"),
                rs.getString("Cidade"),
                rs.getDate("Data_nascimento"),
                rs.getString("pais"),
                rs.getString("IBAN"),
                rs.getString("BIC")
        );
    }

    public static Integer getultimopagamento(String tipo) {
        Connection connection = DBConnect.getInstance().getConnection();
        int pf = 0;

        String query = "SELECT MAX(p.id) AS id FROM pagamento p " +
                "JOIN Metodo_pagamento mp ON mp.id=p.id_metodo;";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);

                if (!rs.wasNull()) {
                    pf = id;
                }

                DBConnect.closeConnection();
                return pf != 0 ? pf + 1 : 0 + 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
        return null;
    }

    public static void dadosficheirodebito(int id_empresa, int id_fornecedor, LocalDateTime atual, LocalDateTime finale, String Metodo, String Identificador, String End_to_end, String mensagem, float Total, String id_pagamento, String encargo, String id_mandato, LocalDateTime data_mandato, LocalDateTime data_mandato_final, String frequencia, String proposito, String text, String num_proprietario, String Sequencia, String tipomoeda) {
        String query = "EXEC Registar_pagamento_Debito" +
                "    @id_empresa=?," +
                "    @id_fornecedor=?," +
                "    @atual=?," +
                "    @finale=?," +
                "    @metodo=?," +
                "    @identificador=?," +
                "    @end_to_end=?," +
                "    @id_pagamento=?," +
                "    @mensagem=?," +
                "    @Total=?," +
                "    @encargo=?," +
                "    @id_mandato=?," +
                "    @data_mandato=?," +
                "    @data_final_mandato=?," +
                "    @frequencia=?," +
                "    @proposito=?," +
                "    @detalhes=?," +
                "    @num_proprietario=?," +
                "    @Sequencia=?," +
                "    @tipomoeda=?;";

        Connection conn = DBConnect.getInstance().getConnection();
        PreparedStatement statement;

        try {
            statement = conn.prepareStatement(query);

            statement.setInt(1, id_empresa);
            statement.setInt(2, id_fornecedor);
            statement.setTimestamp(3, Timestamp.valueOf(atual));
            statement.setTimestamp(4, Timestamp.valueOf(finale));
            statement.setString(5, Metodo);
            statement.setString(6, Identificador);
            statement.setString(7, End_to_end);
            statement.setString(8, id_pagamento);
            statement.setString(9, mensagem);
            statement.setFloat(10, Total);
            statement.setString(11, encargo);
            statement.setString(12, id_mandato);
            statement.setTimestamp(13, Timestamp.valueOf(data_mandato));
            statement.setTimestamp(14, Timestamp.valueOf(data_mandato_final));
            statement.setString(15, frequencia);
            statement.setString(16, proposito);
            statement.setString(17, text);
            statement.setString(18, num_proprietario);
            statement.setString(19, Sequencia);
            statement.setString(20, tipomoeda);


            int rs = statement.executeUpdate();
            if (rs == 0) {
                System.out.println("Erro!!!!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
    }

    public static boolean verificarcod(String cod_fornecedor) {
        String query = "SELECT cod_fornecedor FROM codigo_fornecedor WHERE cod_fornecedor= ? ;";
        Connection conn = DBConnect.getInstance().getConnection();
        try {
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, cod_fornecedor);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                DBConnect.closeConnection();
                return false;
            }
            DBConnect.closeConnection();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
    }

    public static List<Suppliers> buscarFornecedoresSepa() {
        List<Suppliers> fornecedores = new ArrayList<>();
        try {
            ResultSet rs = obterDadosSepa();
            while (Objects.requireNonNull(rs).next()) {
                Suppliers fornecedor = criarFornecedor(rs);
                fornecedores.add(fornecedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return fornecedores;
    }

    public static ResultSet obterDadosSepa() {
        String query2 = "SELECT id FROM permissao WHERE Tipo = ? ";
        Connection conn = DBConnect.getInstance().getConnection();
        try {
            PreparedStatement stmt = conn.prepareStatement(query2);
            stmt.setString(1, "Fornecedor");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {

                int id = rs.getInt("id");

                String query = "SELECT d.id, d.Nome, d.Endereco, d.Endereco2, d.Cidade, d.Telefone, d.Email, d.cod_postal, d.Data_nascimento, f.id_permissao, f.ativo, p.Pais, cf.cod_fornecedor, dp.BIC , dp.IBAN " +
                        "FROM Dados_Pessoais d  " +
                        " JOIN Funcionarios f ON d.id = f.id_Dados    " +
                        " JOIN codigo_fornecedor cf ON cf.id_funcionario= f.id " +
                        " JOIN Pais p ON p.id = d.id_pais " +
                        " JOIN Encomenda e ON e.id_funcionario = f.id     " +
                        " JOIN Produto pro ON pro.id_Encomenda = e.id     " +
                        " JOIN Stock s ON s.id_produto = pro.id " +
                         " JOIN Dados_pagamento dp ON dp.id_dados=d.id " +
                        " WHERE f.id_permissao = ? AND f.ativo = ? AND s.id_stocks IN (SELECT rel_pagamento_stock.id_stock FROM rel_pagamento_stock WHERE rel_pagamento_stock.id_stock=s.id_stocks AND rel_pagamento_stock.Aprovado IS NULL) " +
                        " GROUP BY d.id, d.Nome, d.Endereco, d.Endereco2, d.Cidade, d.Telefone, d.Email, d.cod_postal, d.Data_nascimento, f.id_permissao, f.ativo, p.Pais, cf.cod_fornecedor,dp.BIC,dp.IBAN ;";
                PreparedStatement stmt1 = conn.prepareStatement(query);
                stmt1.setInt(1, id);
                stmt1.setString(2, "S");
                return stmt1.executeQuery();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static int getid(String cod_fornecedor) {
        int id = '0';
        String query = "SELECT dp.id FROM Dados_pessoais dp " +
                "JOIN Funcionarios f ON f.id_Dados=dp.id " +
                "JOIN codigo_fornecedor cf ON f.id=cf.id_funcionario " +
                "WHERE cf.cod_fornecedor=?; ";

        Connection conn = DBConnect.getInstance().getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, cod_fornecedor);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
        return id;
    }

    public static void enviarstock(int id, String id_pagamento) {
        System.out.println(id_pagamento);
        String query = "EXEC Enviar_dados_pagamento " +
                "@id=?, " +
                "@id_pagamento=? ; ";
        Connection conn = DBConnect.getInstance().getConnection();

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, id_pagamento);

            int rs = statement.executeUpdate();

            if (rs == 0) {
                System.out.println("ERRO!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnect.closeConnection();
        }
    }

    public static List<Stock> GetStock2(String cod_fornecedor) {
        Connection conn = DBConnect.getInstance().getConnection();
        ObservableList<Stock> observableList = FXCollections.observableArrayList();
        ArrayList<Stock> StockConfirmar_List = new ArrayList<>();

        String query = "SELECT Produto.Nome_Produto AS Nome, " +
                "       Stock.id_stocks AS id, " +
                "       Encomenda.id AS N_Encomenda, " +
                "       Encomenda.data AS Data_encomenda, " +
                "       Dados_pessoais.Nome AS Fornecedor, " +
                "       Stock.quantidade, " +
                "       UOM.Tipo_UOM AS UOM, " +
                "       Produto.Preco_uni, " +
                "       Pais.Pais, " +
                "       Pais.Tipo_moeda AS moeda, " +
                "       Stock.rejeitado, " +
                "       Stock_Preco.V_taxa, " +
                "       codigo_fornecedor.cod_fornecedor, " +
                "       Produto.GUID " +
                "FROM Stock " +
                "JOIN Produto ON Stock.id_produto = Produto.id " +
                "JOIN Encomenda ON Encomenda.id = Produto.id_encomenda " +
                "JOIN Funcionarios ON Encomenda.id_funcionario = Funcionarios.id " +
                "JOIN Dados_pessoais ON Funcionarios.id_Dados = Dados_pessoais.id " +
                "JOIN rel_UOM_Stock ON Stock.id_stocks = rel_UOM_Stock.id_stock " +
                "JOIN UOM ON rel_UOM_Stock.id_UOM = UOM.id " +
                "JOIN Pais ON Dados_pessoais.id_pais = Pais.id " +
                "JOIN Stock_Preco ON Stock.id_stocks = Stock_Preco.id_stock " +
                "JOIN codigo_fornecedor ON Funcionarios.id = codigo_fornecedor.id_funcionario " +
                "WHERE Stock.rejeitado = 'N' " +
                "  AND Stock.id_stocks NOT IN ( " +
                "                     SELECT rel_pagamento_stock.id_stock " +
                "                     FROM rel_pagamento_stock " +
                "                     WHERE rel_pagamento_stock.id_stock = Stock.id_stocks)   " +
                " AND NOT EXISTS ( " +
                "                     SELECT rel_pagamento_stock.id_stock " +
                "                     FROM rel_pagamento_stock " +
                "                     WHERE rel_pagamento_stock.id_stock = Stock.id_stocks  " +
                "                       AND rel_pagamento_stock.Aprovado <> 'S') " +
                "  AND codigo_fornecedor.cod_fornecedor = ?";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, cod_fornecedor);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Stock stock1 = AdicionarStock(resultSet);
                StockConfirmar_List.add(stock1);
            }
            observableList.addAll(StockConfirmar_List);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return observableList;
    }

    private static Stock AdicionarStock(ResultSet rs) {
        try {
            int id = rs.getInt("id");
            String Nome = rs.getString("Nome");
            String Fornecedor = rs.getString("Fornecedor");
            int quantidade = rs.getInt("quantidade");
            String UOM = rs.getString("UOM");
            float Preco_uni = rs.getFloat("Preco_uni");
            String moeda = rs.getString("moeda");
            float v_base = Preco_uni * quantidade;
            String pais = rs.getString("pais");
            String n_encomenda = rs.getString("N_Encomenda");
            LocalDate data = rs.getDate("Data_encomenda").toLocalDate();
            String Aprov_Rejeitado = rs.getString("rejeitado");
            float V_taxa = rs.getInt("V_taxa");
            float resultado = V_taxa + v_base;
            String cod_fornecedor = rs.getString("cod_fornecedor");
            String GUID = rs.getString("GUID");
            return new Stock(cod_fornecedor, n_encomenda, data, id, Nome, quantidade, Fornecedor, v_base, UOM, Preco_uni, moeda, pais, Aprov_Rejeitado, V_taxa, resultado,GUID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<Stock> GetStockSepa(String id_pagamento) {
        Connection conn = DBConnect.getInstance().getConnection();
        ArrayList<Stock> StockConfirmar_List = new ArrayList<>();

        String query = "SELECT Produto.Nome_Produto AS 'Nome'," +
                "           Stock.id_stocks      as id," +
                "           Encomenda.id         AS 'N_Encomenda'," +
                "           Encomenda.data       AS 'Data_encomenda'," +
                "           Dados_pessoais.Nome  AS 'Fornecedor'," +
                "           Stock.quantidade," +
                "           UOM.Tipo_UOM         AS 'UOM'," +
                "           Produto.Preco_uni," +
                "           Pais.Pais," +
                "           pagamento.tipo_moeda      as 'moeda'," +
                "           Stock.rejeitado," +
                "           Stock_Preco.V_taxa," +
                "           codigo_fornecedor.cod_fornecedor, " +
                "Metodo_pagamento.Metodo," +
                "pagamento.id_pagamento," +
                "Produto.GUID" +
                "    FROM Stock " +
                "             JOIN Produto ON Stock.id_produto = Produto.id" +
                "             JOIN Encomenda ON Encomenda.id = Produto.id_encomenda" +
                "             JOIN Funcionarios ON Encomenda.id_funcionario = Funcionarios.id" +
                "             JOIN Dados_pessoais ON Funcionarios.id_Dados = Dados_pessoais.id" +
                "             JOIN rel_UOM_Stock ON Stock.id_stocks = rel_UOM_Stock.id_stock" +
                "             JOIN UOM ON rel_UOM_Stock.id_UOM = UOM.id" +
                "             JOIN Pais ON Dados_pessoais.id_pais = Pais.id" +
                "             JOIN Stock_Preco ON Stock.id_stocks = Stock_Preco.id_stock" +
                "             JOIN codigo_fornecedor ON Funcionarios.id = codigo_fornecedor.id_funcionario " +
                "             JOIN rel_pagamento_stock ON Stock.id_stocks = rel_pagamento_stock.id_stock" +
                "             JOIN pagamento ON rel_pagamento_stock.id_pagamento = pagamento.id" +
                "             JOIN Metodo_pagamento ON pagamento.id_metodo = Metodo_pagamento.id" +
                "    WHERE Stock.rejeitado = 'N'" +
                "      AND Stock.id_stocks IN (SELECT rel_pagamento_stock.id_stock" +
                "                                  FROM rel_pagamento_stock" +
                "                                  WHERE rel_pagamento_stock.id_stock = Stock.id_stocks) AND pagamento.id_pagamento = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, id_pagamento);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Stock stock1 = AdicionarStock(rs);
                StockConfirmar_List.add(stock1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return StockConfirmar_List;

    }

    public static List<Sepa> GetSepaInfo(String cod_fornecedor) {
        Connection conn = DBConnect.getInstance().getConnection();
        ArrayList<Sepa> Stock_Lista = new ArrayList<>();
        String query = "SELECT  " +
                "    p.id_pagamento,  " +
                "    dp.Data_hora_criacao,  " +
                "    COUNT(s.id_stocks) AS Quantidade,  " +
                "    p.Total AS Total,  " +
                "    mp.Metodo,  " +
                "    p.mensagem  " +
                "FROM pagamento p " +
                "JOIN Datas_pagamento dp ON p.id_datas = dp.id  " +
                "JOIN rel_pagamento_stock rps ON p.id = rps.id_pagamento  " +
                "JOIN Stock s ON rps.id_stock = s.id_stocks  " +
                "JOIN Metodo_pagamento mp ON p.id_metodo = mp.id  " +
                "JOIN Produto pr ON s.id_produto = pr.id  " +
                "JOIN Encomenda e ON pr.id_encomenda = e.id  " +
                "JOIN Funcionarios f ON e.id_funcionario = f.id  " +
                "JOIN Dados_pessoais dpessoais ON f.id_Dados = dpessoais.id  " +
                "JOIN codigo_fornecedor cf ON f.id = cf.id_funcionario  " +
                "WHERE s.rejeitado = ? AND cf.cod_fornecedor = ? AND (rps.Aprovado IS NULL) " +
                "GROUP BY  " +
                "    p.id_pagamento,  " +
                "    dp.Data_hora_criacao,  " +
                "    p.Total,  " +
                "    mp.Metodo,  " +
                "    p.mensagem;";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "N");
            statement.setString(2, cod_fornecedor);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String id_pagamento = rs.getString("id_pagamento");
                LocalDate data = rs.getDate("Data_hora_criacao").toLocalDate();
                int qtd_stock = rs.getInt("Quantidade");
                float Total = rs.getFloat("Total");
                String Metodo = rs.getString("Metodo");
                String mensagem = rs.getString("mensagem");

                Sepa sepa1 = new Sepa(id_pagamento, Metodo, data, qtd_stock, Total, mensagem);
                Stock_Lista.add(sepa1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }

        return Stock_Lista;
    }



    public static List<Sepa> GetSepaInfo2(String cod_fornecedor) {
        Connection conn = DBConnect.getInstance().getConnection();
        ArrayList<Sepa> Stock_Lista = new ArrayList<>();
        String query = "SELECT  " +
                "    pagamento.id_pagamento,  " +
                "    Datas_pagamento.Data_hora_criacao,  " +
                "    COUNT(Stock.id_stocks) AS Quantidade,  " +
                "    pagamento.Total AS Total,  " +
                "    Metodo_pagamento.Metodo,  " +
                "    pagamento.mensagem  " +
                "FROM Stock " +
                "JOIN Produto ON Stock.id_produto = Produto.id  " +
                "JOIN Encomenda ON Encomenda.id = Produto.id_encomenda  " +
                "JOIN Funcionarios ON Encomenda.id_funcionario = Funcionarios.id  " +
                "JOIN Dados_pessoais ON Funcionarios.id_Dados = Dados_pessoais.id  " +
                "JOIN rel_UOM_Stock ON Stock.id_stocks = rel_UOM_Stock.id_stock  " +
                "JOIN UOM ON rel_UOM_Stock.id_UOM = UOM.id  " +
                "JOIN Pais ON Dados_pessoais.id_pais = Pais.id  " +
                "JOIN Stock_Preco ON Stock.id_stocks = Stock_Preco.id_stock  " +
                "JOIN codigo_fornecedor ON Funcionarios.id = codigo_fornecedor.id_funcionario  " +
                "JOIN rel_pagamento_stock ON Stock.id_stocks = rel_pagamento_stock.id_stock  " +
                "JOIN pagamento ON rel_pagamento_stock.id_pagamento = pagamento.id  " +
                "JOIN Datas_pagamento ON pagamento.id_datas = Datas_pagamento.id  " +
                "JOIN Metodo_pagamento ON pagamento.id_metodo = Metodo_pagamento.id  " +
                "WHERE Stock.rejeitado = ? AND codigo_fornecedor.cod_fornecedor = ? AND (rel_pagamento_stock.Aprovado = 'S')" +
                "GROUP BY  " +
                "    pagamento.id_pagamento,  " +
                "    Datas_pagamento.Data_hora_criacao,  " +
                "    Metodo_pagamento.Metodo,  " +
                "    pagamento.Total, " +
                "    pagamento.mensagem;";

        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, "N");
            statement.setString(2, cod_fornecedor);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String id_pagamento = rs.getString("id_pagamento");
                LocalDate data = rs.getDate("Data_hora_criacao").toLocalDate();
                int qtd_stock = rs.getInt("Quantidade");
                float Total = rs.getFloat("Total");
                String Metodo = rs.getString("Metodo");
                String mensagem = rs.getString("mensagem");

                Sepa sepa1 = new Sepa(id_pagamento, Metodo, data, qtd_stock, Total, mensagem);
                Stock_Lista.add(sepa1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }

        return Stock_Lista;
    }

    public static void rejeitarsepa(String idPagamento) {
        Connection conn = DBConnect.getInstance().getConnection();

        String selectIdQuery = "SELECT id FROM pagamento WHERE id_pagamento = ?";
        int pagamentoId = -1;

        try {
            PreparedStatement selectIdStatement = conn.prepareStatement(selectIdQuery);
            selectIdStatement.setString(1, idPagamento);
            ResultSet idResultSet = selectIdStatement.executeQuery();

            if (idResultSet.next()) {
                pagamentoId = idResultSet.getInt("id");
                String deleteQuery = "DELETE FROM rel_pagamento_stock WHERE id_pagamento = ?";

                PreparedStatement deleteStatement = conn.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, pagamentoId);
                deleteStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
    }

    public static Suppliers getSupplier(String cod){
        Connection conn = DBConnect.getInstance().getConnection();
       String query = "SELECT d.id, d.Nome, d.Endereco, d.Endereco2, d.Cidade, d.Telefone, d.Email, d.cod_postal, d.Data_nascimento, f.id_permissao, f.ativo, cf.cod_fornecedor, p.Pais, dp.IBAN , dp.BIC " +
                "FROM Dados_Pessoais d " +
                "JOIN Funcionarios f ON d.id = f.id_Dados " +
                "JOIN Pais p ON p.id= d.id_pais " +
                "JOIN codigo_Fornecedor cf ON cf.id_funcionario = f.id " +
                "JOIN Dados_pagamento dp ON d.id = dp.id_dados " +
                "WHERE cf.cod_fornecedor = ?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,cod);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Suppliers fornecedor = criarFornecedor(rs);
                DBConnect.closeConnection();
                return fornecedor;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return null;
    }

    public static void CriacaoCompleta_Sepa_Credito(String id_pagamento, LocalDateTime data1, LocalDateTime data2){
        Connection conn= DBConnect.getInstance().getConnection();
        String query = "EXEC Atualizar_Dados_Sepa_Credito " +
                "@id_pagamento = ?, " +
                "@data_now = ?, " +
                "@data_final = ?;";

        try {
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1,id_pagamento);
            statement.setTimestamp(2, Timestamp.valueOf(data1));
            statement.setTimestamp(3, Timestamp.valueOf(data2));

            int rs = statement.executeUpdate();
            if(rs==0){
                System.out.println("ERRO!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            DBConnect.closeConnection();
        }
    }

    public static List<String> getMetodos() {
        ArrayList<String> Metodo = new ArrayList<>();

        Connection conn = DBConnect.getInstance().getConnection();

        String query="SELECT Metodo FROM Metodo_pagamento";

        try {
            Statement statement = conn.createStatement();

            ResultSet rs=statement.executeQuery(query);
            while(rs.next()){
              Metodo.add(rs.getString("Metodo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBConnect.closeConnection();
        }
        return Metodo;
    }

    public static String getabreviacao(String pais) {
        Connection conn = DBConnect.getInstance().getConnection();
        String query="SELECT abreviacao FROM Pais WHERE Pais=?;";

        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1,pais);

            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                String abreviacao=rs.getString("abreviacao");
                DBConnect.closeConnection();
                return abreviacao;
            }
        }catch(SQLException e ){
            System.out.println("ERRO! "+e.getMessage());
        }finally {
            DBConnect.closeConnection();
        }
        return null;
    }

    public static void CriacaoCompleta_Sepa_Debito(String id_pagamento, LocalDateTime date , LocalDateTime date_final) {
        Connection conn=DBConnect.getInstance().getConnection();

        String query="EXEC Atualizar_Dados_Sepa_Debito " +
                "@id_pagamento = ?," +
                "@data = ?, " +
                "@data_final= ?;";

        try {
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1,id_pagamento);
            statement.setTimestamp(2, Timestamp.valueOf(date));
            statement.setTimestamp(3, Timestamp.valueOf(date_final));

            int rs = statement.executeUpdate();
            if(rs==0){
                System.out.println("ERRO!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBConnect.closeConnection();
        }
    }

    public static String getMetodo(String id_pagamento){
        Connection conn= DBConnect.getInstance().getConnection();

        String query = "SELECT Metodo_pagamento.Metodo FROM pagamento JOIN Metodo_pagamento ON Metodo_pagamento.id=pagamento.id_metodo WHERE pagamento.id_pagamento=?;";
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1,id_pagamento);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String metodo = rs.getString("Metodo");
                DBConnect.closeConnection();
                return metodo;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBConnect.closeConnection();
        }
        return null;
    }

    public static List<String> getmoeda() {
        Connection conn= DBConnect.getInstance().getConnection();

        List<String> moedas = new ArrayList<>();

        String query = "SELECT DISTINCT Tipo_moeda FROM Pais";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                moedas.add(rs.getString("Tipo_moeda"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBConnect.closeConnection();
        }
        return moedas;
    }

    public static float gettaxaeuro(String moeda) {
        Connection conn= DBConnect.getInstance().getConnection();
        String query = "SELECT DISTINCT taxa_euro FROM Pais WHERE Tipo_moeda = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,moeda);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                    Float taxaeuro = rs.getFloat("taxa_euro");
                DBConnect.closeConnection();
                return taxaeuro;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBConnect.closeConnection();
        }
        return 0;
    }

    public static String getmoedaencomenda(String idPagamento) {
        Connection conn= DBConnect.getInstance().getConnection();
        String query = "SELECT tipo_moeda FROM pagamento WHERE id_pagamento = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,idPagamento);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String moeda = rs.getString("tipo_moeda");
                DBConnect.closeConnection();
                return moeda;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBConnect.closeConnection();
        }
        return null;
    }

    public static Integer getquantidade(String idPagamento) {
        Connection conn = DBConnect.getInstance().getConnection();
        Integer quantidade = null;

        String selectIdQuery = "SELECT id FROM pagamento WHERE id_pagamento = ?";
        int pagamentoId = -1;

        try {
            PreparedStatement selectIdStatement = conn.prepareStatement(selectIdQuery);
            selectIdStatement.setString(1, idPagamento);
            ResultSet idResultSet = selectIdStatement.executeQuery();

            if (idResultSet.next()) {
                pagamentoId = idResultSet.getInt("id");
                String countQuerry = "SELECT COUNT(*) FROM rel_pagamento_stock WHERE id_pagamento = ?";

                PreparedStatement countStatement = conn.prepareStatement(countQuerry);
                countStatement.setInt(1, pagamentoId);
                ResultSet countResultSet = countStatement.executeQuery();

                if (countResultSet.next()) {
                    quantidade = countResultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }
        return quantidade;
    }

    public static List<String> getmorada(String idPagamento) {
        Connection conn = DBConnect.getInstance().getConnection();
        List<String> moradas = new ArrayList<>();

        try {
            String queryPagamento = "SELECT id FROM pagamento WHERE id_pagamento = ?";
            PreparedStatement stmtPagamento = conn.prepareStatement(queryPagamento);
            stmtPagamento.setString(1, idPagamento);
            ResultSet rsPagamento = stmtPagamento.executeQuery();

            if (rsPagamento.next()) {
                int idPagamentoBanco = rsPagamento.getInt("id");
                String queryRelPagamentoStock = "SELECT id_stock FROM rel_pagamento_stock WHERE id_pagamento = ?";
                PreparedStatement stmtRelPagamentoStock = conn.prepareStatement(queryRelPagamentoStock);
                stmtRelPagamentoStock.setInt(1, idPagamentoBanco);
                ResultSet rsRelPagamentoStock = stmtRelPagamentoStock.executeQuery();
                while (rsRelPagamentoStock.next()) {

                        int idStock = rsRelPagamentoStock.getInt("id_stock");
                        String queryStock = "SELECT id_produto FROM Stock WHERE id_stocks = ?";
                        PreparedStatement stmtStock = conn.prepareStatement(queryStock);
                        stmtStock.setInt(1, idStock);
                        ResultSet rsStock = stmtStock.executeQuery();

                        if (rsStock.next()) {
                            int idProduto = rsStock.getInt("id_produto");
                            String queryProduto = "SELECT id_Encomenda FROM Produto WHERE id = ?";
                            PreparedStatement stmtProduto = conn.prepareStatement(queryProduto);
                            stmtProduto.setInt(1, idProduto);
                            ResultSet rsProduto = stmtProduto.executeQuery();

                            if (rsProduto.next()) {
                                String idEncomenda = rsProduto.getString("id_Encomenda");
                                String queryEncomenda = "SELECT morada FROM Encomenda WHERE id = ?";
                                PreparedStatement stmtEncomenda = conn.prepareStatement(queryEncomenda);
                                stmtEncomenda.setString(1, idEncomenda);
                                ResultSet rsEncomenda = stmtEncomenda.executeQuery();

                                if (rsEncomenda.next()) {
                                    String morada = rsEncomenda.getString("morada");
                                    moradas.add(morada);
                                }
                            }
                        }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }

        return moradas;
    }

    public static List<Float> gettotal(String idPagamento) {
        Connection conn = DBConnect.getInstance().getConnection();
        List<Float> totals = new ArrayList<>();
        System.out.println(idPagamento);
        try {
            String queryPagamento = "SELECT id FROM pagamento WHERE id_pagamento = ?";
            PreparedStatement stmtPagamento = conn.prepareStatement(queryPagamento);
            stmtPagamento.setString(1, idPagamento);
            ResultSet rsPagamento = stmtPagamento.executeQuery();

            if (rsPagamento.next()) {
                int idPagamentoBanco = rsPagamento.getInt("id");
                System.out.println(idPagamentoBanco);
                String queryRelPagamentoStock = "SELECT id_stock FROM rel_pagamento_stock WHERE id_pagamento = ?";
                PreparedStatement stmtRelPagamentoStock = conn.prepareStatement(queryRelPagamentoStock);
                stmtRelPagamentoStock.setInt(1, idPagamentoBanco);
                ResultSet rsRelPagamentoStock = stmtRelPagamentoStock.executeQuery();

                while (rsRelPagamentoStock.next()) {
                    int idStock = rsRelPagamentoStock.getInt("id_stock");
                    String queryStock = "SELECT Total FROM Stock_Preco WHERE id_stock = ?";
                    PreparedStatement stmtStock = conn.prepareStatement(queryStock);
                    stmtStock.setInt(1, idStock);
                    ResultSet rsStock = stmtStock.executeQuery();
                    System.out.println(idStock);
                    if (rsStock.next()) {
                        Float total = rsStock.getFloat("Total");
                        System.out.println(total);
                        totals.add(total);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnect.closeConnection();
        }

        return totals;
    }

    public static Stock GetStockAPI(int id) {
        Stock Produto;
        Connection conn = DBConnect.getInstance().getConnection();

        String query="SELECT Produto.Nome_Produto AS 'Nome'," +
                "           Stock.id_stocks      as id," +
                "           Encomenda.id         AS 'N_Encomenda'," +
                "           Encomenda.data       AS 'Data_encomenda'," +
                "           Dados_pessoais.Nome  AS 'Fornecedor'," +
                "           Stock.quantidade," +
                "           UOM.Tipo_UOM         AS 'UOM'," +
                "           Produto.Preco_uni," +
                "           Pais.Pais," +
                "           Pais.tipo_moeda      as 'moeda'," +
                "           Stock.rejeitado," +
                "           Stock_Preco.V_taxa," +
                "           codigo_fornecedor.cod_fornecedor," +
                "Produto.GUID " +
                "    FROM Stock " +
                "             JOIN Produto ON Stock.id_produto = Produto.id" +
                "             JOIN Encomenda ON Encomenda.id = Produto.id_encomenda" +
                "             JOIN Funcionarios ON Encomenda.id_funcionario = Funcionarios.id" +
                "             JOIN Dados_pessoais ON Funcionarios.id_Dados = Dados_pessoais.id" +
                "             JOIN rel_UOM_Stock ON Stock.id_stocks = rel_UOM_Stock.id_stock" +
                "             JOIN UOM ON rel_UOM_Stock.id_UOM = UOM.id" +
                "             JOIN Pais ON Dados_pessoais.id_pais = Pais.id" +
                "             JOIN Stock_Preco ON Stock.id_stocks = Stock_Preco.id_stock" +
                "             JOIN codigo_fornecedor ON Funcionarios.id = codigo_fornecedor.id_funcionario " +
                "    WHERE Stock.id_stocks=? ;";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Produto=AdicionarStock(rs);
                DBConnect.closeConnection();
                return Produto;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DBConnect.closeConnection();
        }
        return null;
    }

    public static double getpreco(String GUID) {
        Connection conn = DBConnect.getInstance().getConnection();

        String query = "SELECT SP.Total FROM Stock s " +
                "JOIN Produto p ON p.id=s.id_produto " +
                "JOIN Stock_Preco SP ON SP.id_stock=s.id_stocks " +
                "WHERE p.GUID = ? ";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,GUID);

            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                double total = rs.getFloat("Total");
                DBConnect.closeConnection();
                return total;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }finally {
            DBConnect.closeConnection();
        }
        return 0;
    }
}
