
package Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:postgresql://db:5432/seu_banco";
    private static final String USUARIO = "user_default";
    private static final String SENHA = "0000";

    public static Connection conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
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
