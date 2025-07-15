
package Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/comercio";
    private static final String USUARIO = "admin";
    private static final String SENHA = "0109";

    public static Connection conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL não encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
        }
        return null;
    }
}
