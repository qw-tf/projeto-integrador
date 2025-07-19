package Banco;

import Backend.Venda;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {
    public static void inserirVenda(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (id, data, descricao, quantidade, formapagamento, valorTotal, ganhoBruto) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            String resumo = venda.gerarResumoDosItens();
            double lucro = venda.getLucroTotal();
    
            stmt.setInt(1, venda.getId());
            stmt.setDate(2, Date.valueOf(venda.getData()));
            stmt.setString(3, resumo); // descrição tipo "Arroz (x1), Feijão (x2)"
            stmt.setInt(4, venda.getQuantidadeTotal());
            stmt.setString(5, venda.getFormaPagamento());
            stmt.setDouble(6, venda.getTotal());
            stmt.setDouble(7, lucro); // o novo campo lindo e lucrativo 💸💸💸
    
            venda.setDescricao(resumo);
            venda.setLucroTotal(lucro); // só pra garantir que o objeto tá sincronizadinho ✨
    
            stmt.executeUpdate();
        }
    }
    
    public static boolean deletarVendaPorId(int idVenda) {
        String sqlItens = "DELETE FROM itens_venda WHERE id_venda = ?";
        String sqlVenda = "DELETE FROM vendas WHERE id = ?";
    
        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmtItens = conn.prepareStatement(sqlItens);
             PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda)) {
    
            stmtItens.setInt(1, idVenda);
            stmtItens.executeUpdate();
    
            stmtVenda.setInt(1, idVenda);
            int linhasAfetadas = stmtVenda.executeUpdate();
    
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
                String descricao = rs.getString("descricao"); // Ex: "Arroz(x2), Feijão(x1)"
                int qtd = rs.getInt("quantidade");
                String formaPagamento = rs.getString("formapagamento");
                double total = rs.getDouble("valorTotal");

                // Cria venda fake com os dados. Como os produtos reais não são salvos, cria uma
                // venda com descrição textual.
                Venda venda = new Venda(new ArrayList<>(), formaPagamento) {
                    {
                        super.setId(id);
                        super.setData(data);
                        super.setTotal(total);
                        super.setDescricao(descricao); // ← ESSENCIAL, SENPAIIII~!!
                    }
                };

                vendas.add(venda);
            }
        }

        return vendas;
    }
}
