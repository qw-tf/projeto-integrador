package Banco;

import Backend.Conta;
import Banco.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContaDAO {

    public void inserir(Conta conta) {
        String sql = "INSERT INTO conta (id, descricao, valor) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, conta.getId());
            stmt.setString(2, conta.getDescricao());
            stmt.setDouble(3, conta.getValor()); // valor obrigatório, não pode ser null

            stmt.executeUpdate();
            System.out.println("Conta inserida com sucesso! ID: " + conta.getId());

        } catch (SQLException e) {
            System.out.println("Erro ao inserir conta:");
            e.printStackTrace();
        }
    }
}
