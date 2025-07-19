package Banco;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Backend.Gasto;
import Backend.RepositorioGastos;

public class GastosDAO {

    public static void salvarGasto(Gasto gasto) {
        String sql = "INSERT INTO gastos (descricao, valor, data, pessoal, conta, pago) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, gasto.getDescricao());
            stmt.setDouble(2, gasto.getValor());
            stmt.setDate(3, Date.valueOf(gasto.getData()));
            stmt.setBoolean(4, gasto.isPessoal());
            stmt.setBoolean(5, gasto.isConta());
            stmt.setBoolean(6, gasto.isPago());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Gasto> carregarGastos() {
        List<Gasto> todosGastos = new ArrayList<>();

        String sql = "SELECT * FROM gastos";

        try (Connection conn =  ConexaoPostgres.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                LocalDate data = rs.getDate("data").toLocalDate();
                boolean pessoal = rs.getBoolean("pessoal");
                boolean conta = rs.getBoolean("conta");
                boolean pago = rs.getBoolean("pago");

                Gasto gasto = new Gasto(descricao, valor, data, pessoal, conta);
                if (pago)
                    gasto.marcarComoPago();

                todosGastos.add(gasto);

                // Adiciona ao repositório também (para uso na interface ou balanço)
                RepositorioGastos.adicionarGasto(gasto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return todosGastos;
    }

    public static void marcarComoPago(Gasto gasto) {
        String sql = "UPDATE gastos SET pago = 1 WHERE descricao = ? AND valor = ? AND data = ?";

        try (Connection conn =  ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, gasto.getDescricao());
            stmt.setDouble(2, gasto.getValor());
            stmt.setDate(3, Date.valueOf(gasto.getData()));

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
