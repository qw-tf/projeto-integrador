package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Banco.ConexaoPostgres;
import Banco.VendaDAO;

public class RegistroVendas {
    private static List<Venda> vendas = new ArrayList<>();

    // Método modificado: remove o parâmetro idCliente
    public static void adicionarVenda(List<ItemVenda> itens, String formaPagamento) throws Exception {
        Venda venda = new Venda(itens, formaPagamento);
        vendas.add(venda); // Mantém na memória se quiser
        VendaDAO.inserirVenda(venda); // Salva no banco AGORA mesmoooo, nyah ✨

        // Atualiza estoque após venda
        for (ItemVenda item : itens) {
            item.getProduto().removerQuantidade(item.getQuantidade());
        }
    }

    public static void adicionarVendaDireto(Venda v) {
        vendas.add(v);
    }

    public static List<Venda> getTodasVendas() {
        return new ArrayList<>(vendas);
    }

    public static boolean excluirVenda(int id) {
        return vendas.removeIf(v -> v.getId() == id);
    }

    public static boolean removerVenda(Venda v) {
        return vendas.remove(v);
    }

    public static void quitarFiadoERegistrarVenda(Fiado f, List<ItemVenda> itens, String formaPagamento) {
        f.quitarTotalmente();
        FiadoRepositorio.removerFiado(f);
        // Como o idCliente vem do fiado, e agora não usamos, vamos criar a venda sem
        // idCliente
        Venda novaVenda = new Venda(itens, formaPagamento);
        adicionarVendaDireto(novaVenda);
    }

    public static Map<LocalDate, Double[]> calcularBalancoPorDia(LocalDate inicio, LocalDate fim) {
        Map<LocalDate, Double[]> resultado = new TreeMap<>();

        for (Venda venda : vendas) {
            LocalDate data = venda.getData();
            if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                double lucro = venda.getLucroTotal();
                double gasto = venda.getGastoTotal();

                resultado.putIfAbsent(data, new Double[] { 0.0, 0.0 });
                Double[] valores = resultado.get(data);
                valores[0] += lucro;
                valores[1] += gasto;
            }
        }

        return resultado;
    }

    public static Map<Integer, Double[]> calcularBalancoPorMes(LocalDate inicio, LocalDate fim) {
        Map<Integer, Double[]> resultado = new TreeMap<>();

        for (Venda venda : vendas) {
            LocalDate data = venda.getData();
            if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                int mes = data.getMonthValue();
                double lucro = venda.getLucroTotal();
                double gasto = venda.getGastoTotal();

                resultado.putIfAbsent(mes, new Double[] { 0.0, 0.0 });
                Double[] valores = resultado.get(mes);
                valores[0] += lucro;
                valores[1] += gasto;
            }
        }

        return resultado;
    }

    public static void adicionarVenda(Venda venda) {
        vendas.add(venda);
    }

    public static void carregarVendasDoBanco() {
        try {
            List<Venda> vendasDoBanco = new ArrayList<>();

            String sql = "SELECT * FROM vendas";
            try (Connection conn = ConexaoPostgres.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    String descricao = rs.getString("descricao");
                    int quantidade = rs.getInt("quantidade");
                    String formaPagamento = rs.getString("formapagamento");
                    double total = rs.getDouble("valortotal");

                    // Como você não tem itens reais, passa uma lista vazia
                    Venda venda = new Venda(new ArrayList<>(), formaPagamento) {
                        {
                            setId(id);
                            setData(data);
                            setTotal(total);
                            // Se quiser, sobrescreve getResumoProdutos() para usar a descrição
                        }
                    };

                    vendasDoBanco.add(venda);
                }
            }

            vendas.clear();
            vendas.addAll(vendasDoBanco);

            System.out.println("Vendas carregadas SEM itemzinhos, tudo com descrição pronta ✨🔥");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar vendas do banco, gomen gomen gomen 😭");
        }
    }

}
