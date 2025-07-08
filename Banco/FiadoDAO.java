package Banco;

import Backend.Fiado;
import Banco.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FiadoDAO {

    public void inserir(Fiado fiado) {
        String sql = "INSERT INTO fiado (id_fiado, descricao, id_cliente, id_venda) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fiado.getIdFiado());
            stmt.setString(2, fiado.getDescricao());
            stmt.setInt(3, fiado.getIdCliente());
            stmt.setInt(4, fiado.getIdVenda());

            stmt.executeUpdate();
            System.out.println("Fiado inserido com sucesso! ID: " + fiado.getIdFiado());

        } catch (SQLException e) {
            System.out.println("Erro ao inserir fiado:");
            e.printStackTrace();
        }
    }
}
