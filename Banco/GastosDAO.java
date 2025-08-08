
package Banco;

import Backend.Gasto;
import Backend.RepositorioGastos;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GastosDAO {
    public static void salvarGasto(Gasto gasto) {
        String sql = "INSERT INTO gastos (descricao, valor, data, pessoal) VALUES (?, ?, ?, ?)";
    
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    
            stmt.setString(1, gasto.getDescricao());
            stmt.setDouble(2, gasto.getValor());
            stmt.setDate(3, Date.valueOf(gasto.getData()));
            stmt.setBoolean(4, gasto.isPessoal());
    
            int affectedRows = stmt.executeUpdate();
    
            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir gasto, nenhuma linha afetada.");
            }
    
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idGerado = generatedKeys.getInt(1);
                    System.out.println("Gasto inserido com sucesso! ID gerado: " + idGerado);
                   
                } else {
                    throw new SQLException("Falha ao obter ID gerado para o gasto.");
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static List<Gasto> carregarGastos() {
        List<Gasto> todosGastos = new ArrayList<>();
        String sql = "SELECT * FROM gastos";

        try (Connection conn = ConexaoPostgres.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                LocalDate data = rs.getDate("data").toLocalDate();
                boolean pessoal = rs.getBoolean("pessoal");

                Gasto gasto = new Gasto(descricao, valor, data, pessoal);
                todosGastos.add(gasto);
                RepositorioGastos.adicionarGasto(gasto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todosGastos;
    }
}
