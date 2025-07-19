package Banco;

import Backend.Fiado;
import Backend.Venda;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FiadoDAO {

    // Inserir novo fiado no banco, usando os métodos já existentes
    public static void inserirFiado(Fiado fiado) throws SQLException {
        String sql = "INSERT INTO fiados (id, idvenda, nomecliente, valorrestante, dataquitado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, fiado.getIdFiado());
            stmt.setInt(2, fiado.getIdVenda());
            stmt.setString(3, fiado.getNomeCliente());
            stmt.setDouble(4, fiado.getValorRestante());
            stmt.setDate(5, fiado.getDataQuitado() != null ? Date.valueOf(fiado.getDataQuitado()) : null);

            stmt.executeUpdate();
        }
    }

    // Deletar fiado do banco e liberar o ID para reaproveitamento
    public static void deletarFiado(int idFiado) throws SQLException {
        String sql = "DELETE FROM fiados WHERE id = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFiado);
            int afetados = stmt.executeUpdate();

            if (afetados > 0) {
                Fiado.liberarId(idFiado);
                System.out.println("Fiado deletado com sucesso, nyan~!");
            } else {
                System.out.println("Nenhum fiado encontrado com esse ID...");
            }
        }
    }

    // Atualizar valor restante no banco conforme pagamento feito
    public static void atualizarValorRestante(Fiado fiado) throws SQLException {
        String sql = "UPDATE fiados SET valorrestante = ? WHERE id = ?";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, fiado.getValorRestante());
            stmt.setInt(2, fiado.getIdFiado());

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
                String nome = rs.getString("nomeCliente");
                int idVenda = rs.getInt("idVenda");
                double valor = rs.getDouble("valorRestante");
                Date dataSql = rs.getDate("dataQuitado");
                LocalDate data = dataSql != null ? dataSql.toLocalDate() : null;
                int idFiado = rs.getInt("id");

                Fiado fiado = new Fiado(nome, new Venda(idVenda)); // Dummy venda só pelo id
                fiado.setIdFiado(idFiado);
                fiado.setValorRestante(valor);
                fiado.setQuitado(valor == 0.0);
                fiado.setDataQuitado(data);

                lista.add(fiado);
            }
        }

        return lista;
    }

    public static void excluirFiadosQuitadosComMaisDe30Dias() throws SQLException {
        String sql = "DELETE FROM fiados WHERE valorrestante = 0 AND dataQuitado <= CURRENT_DATE - INTERVAL '30 days'";

        try (Connection conn = ConexaoPostgres.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            int deletados = stmt.executeUpdate();
            System.out.println("Fiados quitados com mais de 30 dias deletados: " + deletados);
        }
    }

    private static void setDataCriacao(Fiado fiado, LocalDate data) {
        try {
            java.lang.reflect.Field field = Fiado.class.getDeclaredField("dataCriacao");
            field.setAccessible(true);
            field.set(fiado, data);
        } catch (Exception e) {
            System.err.println("Erro ao setar dataCriacao via reflection: " + e.getMessage());
        }
    }
}
