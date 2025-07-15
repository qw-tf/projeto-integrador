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

    public static void atualizar(Produto produto) throws SQLException {
        String sql = "UPDATE produtos SET nome = ?, quantidade = ?, valorCompra = ?, valorVenda = ? WHERE codigo = ?";
        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getValorCompra());
            stmt.setDouble(4, produto.getValorVenda());
            stmt.setInt(5, produto.getCodigo());

            stmt.executeUpdate();
        }
    }

    public static void deletar(int codigo) throws SQLException {
        String selectSql = "SELECT nome FROM produtos WHERE codigo = ?";
        String deleteSql = "DELETE FROM produtos WHERE codigo = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {

            selectStmt.setInt(1, codigo);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                System.out.println("Deletando produto: " + nome);

                deleteStmt.setInt(1, codigo);
                deleteStmt.executeUpdate();

                System.out.println("Produto deletado com sucesso.");
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
}