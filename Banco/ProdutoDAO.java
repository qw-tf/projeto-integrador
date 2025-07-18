package Banco;

import Backend.Produto;

import Backend.ProdutoPerecivel;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public static void inserirProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO produtos (codigo, nome, quantidade, valorCompra, valorVenda, dataDeValidade) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produto.getCodigo());
            stmt.setString(2, produto.getNome());
            stmt.setInt(3, produto.getQuantidade());
            stmt.setDouble(4, produto.getValorCompra());
            stmt.setDouble(5, produto.getValorVenda());

            if (produto instanceof ProdutoPerecivel) {
                ProdutoPerecivel perecivel = (ProdutoPerecivel) produto;
                LocalDate dataStr = perecivel.getDataDeValidade();
                stmt.setDate(6, java.sql.Date.valueOf(dataStr));
            } else {
                stmt.setNull(6, java.sql.Types.DATE);
            }

            stmt.executeUpdate();
        }
    }

    public static void atualizar(int codigo, int novaQuantidade) throws SQLException {
        System.out.println("Código recebido: " + codigo + " | Nova quantidade: " + novaQuantidade);
        String sql = "UPDATE produtos SET quantidade = ? WHERE codigo = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, novaQuantidade); // nova quantidade kawaii desu
            stmt.setInt(2, codigo); // código do produto-chan

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Nenhum produto com esse código foi encontrado!");
            } else {
                System.out.println("Produto atualizado com sucesso!");
            }
        }
    }

    public static void deletar(int codigo) throws SQLException {
        System.out.println("Código recebido: " + codigo);
        String deleteSql = "DELETE FROM produtos WHERE codigo = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            deleteStmt.setInt(1, codigo);
            int linhasAfetadas = deleteStmt.executeUpdate();

            if (linhasAfetadas > 0) {
                Produto.liberarCodigo(codigo); // <<< ESSENCIAL!!!
                System.out.println("Produto com código " + codigo + " deletado com sucesso.");
            } else {
                System.out.println("Produto com código " + codigo + " não encontrado.");
            }
        }
    }

    public List<Produto> listarTodos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produtos";
        try (Connection conn = ConexaoPostgres.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getString("nome"),
                        rs.getInt("quantidade"),
                        rs.getDouble("valorCompra"),
                        rs.getDouble("valorVenda"));
                produto.setCodigo(rs.getInt("codigo"));
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

                Produto produto;
                Date dataValidade = rs.getDate("dataDeValidade");

                if (dataValidade != null) {
                    produto = new ProdutoPerecivel(nome, quantidade, valorCompra, valorVenda,
                            dataValidade.toLocalDate());
                } else {
                    produto = new Produto(nome, quantidade, valorCompra, valorVenda);
                }

                produto.setCodigo(codigo);
                return produto;
            }

            return null;
        }
    }

}