package Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class ConexaoPostgres {
    private static final String URL = "jdbc:postgresql://localhost:5432/comercio?user=admin&password=0109&ssl=false";
    
    public static Connection getConnection() throws SQLException {
        System.out.println("Tentando conectar com: " + URL.replace("password=0109", "password="));
        
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL não encontrado", e);
        }
    }
}