package Banco;

import Backend.Venda;
import Banco.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VendaDAO {

    public void inserir(Venda venda) {
        String sql = "INSERT INTO venda (id_venda, descricao, forma_pagamento, data_da_venda, valor, id_cliente) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venda.getId());
            stmt.setString(3, venda.getFormaPagamento());
            stmt.setDate(4, Date.valueOf(venda.getData()));
            stmt.setDouble(5, venda.getValor());
            stmt.setInt(6, venda.getIdCliente());

            stmt.executeUpdate();

            System.out.println("Venda inserida com sucesso! ID: " + venda.getId());

        } catch (SQLException e) {
            System.out.println("Erro ao inserir venda:");
            e.printStackTrace();
        }
    }
}
