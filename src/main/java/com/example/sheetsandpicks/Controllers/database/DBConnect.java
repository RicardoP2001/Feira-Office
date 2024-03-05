package com.example.sheetsandpicks.Controllers.database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A classe DBConnect é responsável por estabelecer e gerenciar a conexão com o banco de dados SQL Server.
 * Utiliza o padrão Singleton para garantir uma única instância da conexão.
 */
public class DBConnect {
    private static DBConnect instance;
    private static Connection connection;

    /**
     * Construtor privado da classe DBConnect responsável por estabelecer a conexão com o banco de dados SQL Server.
     * Utiliza as informações de URL de conexão, driver, nome de utilizador e palavra-passe fornecida para conectar a base de dados.
     * Imprime uma mensagem indicando se a conexão foi bem-sucedida ou falhou, juntamente com qualquer exceção lançada.
     */
    private DBConnect() {
        try {
            String[] fileContent = readDatabaseVariables();
            if (fileContent.length >= 4) {
                String server = fileContent[0];
                String databaseName = fileContent[1];
                String username = fileContent[2];
                String password = fileContent[3];

                String connectionUrl = "jdbc:sqlserver://" + server + ";databaseName=" + databaseName +
                        ";user=" + username + ";password=" + password;

                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(connectionUrl);
            } else {
                System.out.println("Insufficient data in Database_Variables.txt");
            }
        } catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
            System.out.println("Connection to the SQL Server database failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lê as variáveis do banco de dados do arquivo Database_Variables.txt.
     *
     * @return Um array de strings contendo as variáveis do banco de dados.
     * @throws FileNotFoundException Se o arquivo Database_Variables.txt não for encontrado.
     */
    private String[] readDatabaseVariables() throws FileNotFoundException {
        List<String> variablesList = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Database_Variables.txt");

        if (inputStream != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = br.readLine()) != null) {
                    variablesList.add(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Database_Variables.txt not found.");
        }

        return variablesList.toArray(new String[0]);
    }

    /**
     * Obtém a instância única da classe DBConnect usando o padrão Singleton.
     * Se a instância ainda não foi criada, este método cria uma nova instância da classe DBConnect
     * e a retorna. Caso contrário, retorna a instância existente.
     *
     * @return A instância única da classe DBConnect.
     */
    public static synchronized DBConnect getInstance() {
        if (instance == null) {
            instance = new DBConnect();
        }
        return instance;
    }

    /**
     * Obtém a conexão ativa com o banco de dados.
     *
     * @return A conexão ativa com o banco de dados.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Fecha a conexão ativa com o banco de dados.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                instance = null;
            }
        } catch (SQLException e) {
            System.out.println("Error closing the database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
