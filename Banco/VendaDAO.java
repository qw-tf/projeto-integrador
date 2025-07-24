package Banco;

import Backend.Venda;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    public static void inserirVenda(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (id, data, descricao, quantidade, formapagamento, valorTotal, ganhoBruto, gasto) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venda.getId());
            stmt.setDate(2, Date.valueOf(venda.getData()));
            stmt.setString(3, venda.gerarResumoDosItens()); // descrição tipo "Arroz (x1), Feijão (x2)"
            stmt.setInt(4, venda.getQuantidadeTotal());
            stmt.setString(5, venda.getFormaPagamento());
            stmt.setDouble(6, venda.getTotal());
            stmt.setDouble(7, venda.getLucroTotal()); // o novo campo lindo e lucrativo 💸💸💸
            stmt.setDouble(8, venda.getGastoTotal()); // o gasto que você quer salvar

            venda.setDescricao(venda.gerarResumoDosItens());
            venda.setLucroTotal(venda.getLucroTotal()); // só pra garantir que o objeto tá sincronizadinho ✨

            stmt.executeUpdate();
        }
    }

    public static boolean deletarVendaPorId(int idVenda) {
        String sqlVenda = "DELETE FROM vendas WHERE id = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda)) {

            int linhasAfetadas = 0;

            conn.setAutoCommit(false);

            stmtVenda.setInt(1, idVenda);
            linhasAfetadas = stmtVenda.executeUpdate();

            if (linhasAfetadas > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // ← Mostra o erro real no console!
            return false;
        }
    }

    public static List<Venda> listarVendas() throws SQLException {
        List<Venda> vendas = new ArrayList<>();

        String sql = "SELECT * FROM vendas";
        try (Connection conn = ConexaoPostgres.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDate data = rs.getDate("data").toLocalDate();
                String descricao = rs.getString("descricao");
                int qtd = rs.getInt("quantidade");
                String formaPagamento = rs.getString("formapagamento");
                double total = rs.getDouble("valorTotal");
                double lucro = rs.getDouble("ganhoBruto");
                double gasto = rs.getDouble("gasto");

                Venda venda = new Venda(new ArrayList<>(), formaPagamento) {
                    {
                        super.setId(id);
                        super.setData(data);
                        super.setTotal(total);
                        super.setDescricao(descricao);
                        super.setLucroTotal(lucro);
                        super.setGasto(gasto);
                    }
                };

                vendas.add(venda);
            }
        }

        return vendas;
    }

}
