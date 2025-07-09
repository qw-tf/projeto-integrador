package Banco;

import java.sql.Connection;
import java.sql.Statement;

public class BancoUtils {

    public static void criarTabelaProduto() {
        String sql = "CREATE TABLE IF NOT EXISTS produto ("
                   + "id INT AUTO_INCREMENT PRIMARY KEY, "
                   + "nome VARCHAR(100) NOT NULL, "
                   + "quantidade INT NOT NULL, "
                   + "valor_compra DOUBLE NOT NULL, "
                   + "valor_venda DOUBLE NOT NULL, "
                   + "validade DATE"
                   + ");";

        try (Connection conn = Conexao.conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("✅ Tabela 'produto' criada/verificada.");
        } catch (Exception e) {
            System.err.println("❌ Erro ao criar tabela: " + e.getMessage());
        }
    }
}
