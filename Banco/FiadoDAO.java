package Banco;

import Backend.Fiado;
import Backend.Venda;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FiadoDAO {

    public static void inserirFiado(Fiado fiado) throws SQLException {
        String sql = "INSERT INTO fiados (id, idvenda, nomecliente, valorrestante, datacriacao, dataquitado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fiado.getIdFiado());
            stmt.setInt(2, fiado.getIdVenda());
            stmt.setString(3, fiado.getNomeCliente());
            stmt.setDouble(4, fiado.getValorRestante());
            stmt.setDate(5, Date.valueOf(fiado.getDataCriacao()));
            if (fiado.getDataQuitado() != null) {
                stmt.setDate(6, Date.valueOf(fiado.getDataQuitado()));
            } else {
                stmt.setNull(6, Types.DATE);
            }

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
                Fiado.liberarId(idFiado);
                System.out.println("Fiado deletado com sucesso");
            } else {
                System.out.println("Nenhum fiado encontrado com esse ID...");
            }
        }
    }

    public static void atualizarValorRestante(Fiado fiado) throws SQLException {
        String sql = "UPDATE fiados SET valorrestante = ?, dataquitado = ? WHERE id = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, fiado.getValorRestante());
            if (fiado.getDataQuitado() != null) {
                stmt.setDate(2, Date.valueOf(fiado.getDataQuitado()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setInt(3, fiado.getIdFiado());

            stmt.executeUpdate();
        }
    }

    public static List<Fiado> listarFiados() throws SQLException {
        List<Fiado> lista = new ArrayList<>();
        String sql = "SELECT * FROM fiados";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nomecliente");
                int idVenda = rs.getInt("idvenda");
                double valor = rs.getDouble("valorrestante");
                LocalDate dataCriacao = rs.getDate("datacriacao").toLocalDate();
                Date dtQuitadoSql = rs.getDate("dataquitado");
                LocalDate dataQuitado = (dtQuitadoSql != null) ? dtQuitadoSql.toLocalDate() : null;
                int idFiado = rs.getInt("id");

                Fiado fiado = new Fiado(nome, new Venda(idVenda));
                fiado.setIdFiado(idFiado);
                fiado.setValorRestante(valor);
                fiado.setQuitado(valor == 0.0);
                fiado.setDataCriacao(dataCriacao);
                fiado.setDataQuitado(dataQuitado);

                lista.add(fiado);
            }
        }

        return lista;
    }

    public static void excluirFiadosQuitadosComMaisDe30Dias() throws SQLException {
        String sql = "DELETE FROM fiados WHERE valorrestante = 0 AND dataquitado <= CURRENT_DATE - INTERVAL '30 days'";

        try (Connection conn = ConexaoPostgres.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int deletados = stmt.executeUpdate();
            System.out.println("Fiados quitados com mais de 30 dias deletados: " + deletados);
        }
    }
}
