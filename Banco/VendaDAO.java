package Banco;

import Backend.Venda;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    public static void inserirVenda(Venda venda) throws SQLException {
        String sql = "INSERT INTO vendas (id, data, descricao, quantidade, formapagamento, valorTotal) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venda.getId());
            stmt.setDate(2, Date.valueOf(venda.getData()));
            stmt.setString(3, venda.getResumoProdutos()); // "Arroz(x1), Sabão(x2)"
            stmt.setInt(4, venda.getQuantidadeTotal());
            stmt.setString(5, venda.getFormaPagamento());
            stmt.setDouble(6, venda.getTotal());

            stmt.executeUpdate();
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
                        // Bloco de inicialização para forçar os dados
                        super.setId(id); // Usa o setId real
                        super.setData(data);
                        super.setTotal(total);
                    }
                };

                vendas.add(venda);
            }
        }

        return vendas;
    }
}
