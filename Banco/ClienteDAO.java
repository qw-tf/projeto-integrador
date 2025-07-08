
package Banco;

import Backend.Cliente;
import Banco.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAO {

    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (id, nome, cpf) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getId());               // ID gerado no Java
            stmt.setString(2, cliente.getNome());
            
            if (cliente.getCpf() != null && !cliente.getCpf().isEmpty()) {
                stmt.setString(3, cliente.getCpf());
            } else {
                stmt.setNull(3, java.sql.Types.VARCHAR);   // CPF opcional
            }

            stmt.executeUpdate();
            System.out.println("Cliente inserido com sucesso! ID: " + cliente.getId());

        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente:");
            e.printStackTrace();
        }
    }
}