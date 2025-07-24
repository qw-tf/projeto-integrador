package Banco;

import Backend.Produto;
import Backend.ProdutoPerecivel;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    public static void inserirProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (codigo, nome, quantidade, quantidadeTotal, valorCompra, valorVenda, dataDeValidade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produto.getCodigo());
            stmt.setString(2, produto.getNome());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setInt(4, produto.getQuantidadeTotal()); // 🌟 novo campo mágico!
            stmt.setDouble(5, produto.getValorCompra());
            stmt.setDouble(6, produto.getValorVenda());

            if (produto instanceof ProdutoPerecivel perecivel) {
                LocalDate validade = perecivel.getDataDeValidade();
                stmt.setDate(7, validade != null ? Date.valueOf(validade) : null);
            } else {
                stmt.setNull(7, Types.DATE);
            }

            stmt.executeUpdate();
        }
    }

    public static void atualizar(int codigo, int novaQuantidade) throws SQLException {
        if (novaQuantidade == 0) {
            deletar(codigo);
            return;
        }

        String sql = "UPDATE produtos SET quantidade = ? WHERE codigo = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, codigo);
            stmt.executeUpdate();
        }
    }

    public static void deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM produtos WHERE codigo = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                Produto.liberarCodigo(codigo);
            }
        }
    }

    public static List<Produto> listarTodos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";

        try (Connection conn = ConexaoPostgres.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                int quantidadeTotal = rs.getInt("quantidadeTotal");
                double valorCompra = rs.getDouble("valorCompra");
                double valorVenda = rs.getDouble("valorVenda");
                int codigo = rs.getInt("codigo");
                Date validade = rs.getDate("dataDeValidade");

                Produto produto;
                if (validade != null) {
                    produto = new ProdutoPerecivel(nome, quantidade, valorCompra, valorVenda, validade.toLocalDate());
                } else {
                    produto = new Produto(nome, quantidade, valorCompra, valorVenda);
                }

                produto.setCodigo(codigo);
                produto.setQuantidadeTotal(quantidadeTotal); // ESSA LINHA FAZIA FALTA
                produtos.add(produto);
            }
        }

        return produtos;
    }

    public static Produto buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM produtos WHERE codigo = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                int quantidade = rs.getInt("quantidade");
                double valorCompra = rs.getDouble("valorCompra");
                double valorVenda = rs.getDouble("valorVenda");
                Date validade = rs.getDate("dataDeValidade");

                Produto produto;
                if (validade != null) {
                    produto = new ProdutoPerecivel(nome, quantidade, valorCompra, valorVenda, validade.toLocalDate());
                } else {
                    produto = new Produto(nome, quantidade, valorCompra, valorVenda);
                }

                produto.setCodigo(codigo);
                return produto;
            }
        }

        return null;
    }
}
