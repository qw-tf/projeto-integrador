package Banco;

import Backend.Fiado;
import Backend.FiadoRepositorio;
import Backend.Venda;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FiadoDAO {

    public static void inserirFiado(Fiado fiado) throws SQLException {
        String sql = "INSERT INTO fiados (id, idVenda, nomeCliente, valorRestante, dataCriacao) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fiado.getIdFiado());
            stmt.setInt(2, fiado.getIdVenda());
            stmt.setString(3, fiado.getNomeCliente());
            stmt.setDouble(4, fiado.getValorRestante());
            stmt.setDate(5, Date.valueOf(fiado.getDataCriacao()));

            stmt.executeUpdate();
        }
    }

    public static void deletarFiado(int idFiado) throws SQLException {
        String sql = "DELETE FROM fiados WHERE id = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFiado);
            int afetados = stmt.executeUpdate();

            if (afetados > 0) {
                Fiado.liberarId(idFiado); // MUITO IMPORTANTE IGUAL NO ProdutoDAO!!
                System.out.println("Fiado deletado com sucesso, nyan~!");
            } else {
                System.out.println("Nenhum fiado encontrado com esse ID...");
            }
        }
    }

    public static List<Fiado> listarFiados() throws SQLException {
        List<Fiado> lista = new ArrayList<>();
        String sql = "SELECT * FROM fiados";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nomeCliente");
                int idVenda = rs.getInt("idVenda");
                double valor = rs.getDouble("valorRestante");
                LocalDate data = rs.getDate("dataCriacao").toLocalDate();
                int idFiado = rs.getInt("id");

                Fiado fiado = new Fiado(nome, new Venda(idVenda)); // Dummy venda só pelo id
                fiado.setIdFiado(idFiado);
                fiado.quitarTotalmente(); // vai sobrescrever depois
                fiado.registrarPagamento(fiado.getValorRestante() - valor); // corrige valorRestante
                lista.add(fiado);
            }
        }

        return lista;
    }

    public static void atualizarValorRestante(Fiado fiado) throws SQLException {
        String sql = "UPDATE fiados SET valor_restante = ? WHERE id = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, fiado.getValorRestante());
            stmt.setInt(2, fiado.getIdFiado());

            stmt.executeUpdate();
        }
    }
}
