package Banco;
import Backend.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProdutoDAO {

    public void inserirProduto(Produto produto) {
        String sql = "INSERT INTO produto (nome, quantidade, valor_compra, valor_venda) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getValorCompra());
            stmt.setDouble(4, produto.getValorVenda());

            stmt.executeUpdate();
            System.out.println("Produto inserido no banco com sucesso.");

        } catch (Exception e) {
            System.err.println("Erro ao inserir produto: " + e.getMessage());
        }
    }
}
