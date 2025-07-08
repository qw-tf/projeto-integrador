package Banco;

import Backend.Produto;
import Banco.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProdutoDAO {

    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (codigo, nome, preco, quantidade) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produto.getCodigo());    // código gerado no Java
            stmt.setString(2, produto.getNome());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidade());

            stmt.executeUpdate();

            System.out.println("Produto inserido com sucesso! Código: " + produto.getCodigo());

        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto:");
            e.printStackTrace();
        }
    }

}
