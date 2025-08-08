package Backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import Banco.ConexaoPostgres;
import Banco.ProdutoDAO;
import Banco.VendaDAO;

public class RegistroVendas {
    private static List<Venda> vendas = new ArrayList<>();

    public static void adicionarVenda(List<ItemVenda> itens, String formaPagamento) throws Exception {
        Venda venda = new Venda(itens, formaPagamento);
        vendas.add(venda); 
        VendaDAO.inserirVenda(venda); 

        for (ItemVenda item : itens) {
            item.getProduto().removerQuantidade(item.getQuantidade());
            ProdutoDAO.atualizar(item.getProduto().getCodigo(), item.getProduto().getQuantidade());
        }
    }

    public static void adicionarVendaDireto(Venda v) {
        vendas.add(v);
    }

    public static List<Venda> getTodasVendas() {
        return new ArrayList<>(vendas);
    }

    public static boolean excluirVenda(int id) {
        boolean removido = vendas.removeIf(v -> v.getId() == id);
        if (removido) {
            Venda.liberarId(id);
        }
        return removido;
    }

    public static boolean removerVenda(Venda venda) {
        boolean removidoDoBanco = VendaDAO.deletarVendaPorId(venda.getId());

        if (removidoDoBanco) {
            List<Fiado> fiadosParaRemover = new ArrayList<>();
            for (Fiado f : RepositorioFiados.getFiados()) {
                if (f.getIdVenda() == venda.getId()) {
                    fiadosParaRemover.add(f);
                }
            }

            for (Fiado f : fiadosParaRemover) {
                RepositorioFiados.removerFiado(f); 
                Fiado.liberarId(f.getIdFiado()); 
            }

            Venda paraRemover = null;
            for (Venda v : vendas) {
                if (v.getId() == venda.getId()) {
                    paraRemover = v;
                    break;
                }
            }

            if (paraRemover != null) {
                vendas.remove(paraRemover);
                Venda.liberarId(paraRemover.getId());
                return true;
            }
        }

        return false;
    }

    public static void quitarFiadoERegistrarVenda(Fiado f, List<ItemVenda> itens, String formaPagamento) {
        f.quitarTotalmente();
        RepositorioFiados.removerFiado(f);
        Venda novaVenda = new Venda(itens, formaPagamento);
        adicionarVendaDireto(novaVenda);
    }

    public static Map<LocalDate, Double[]> calcularBalancoPorDia(LocalDate inicio, LocalDate fim) {
        Map<LocalDate, Double[]> resultado = new TreeMap<>();

        for (Venda venda : vendas) {
            LocalDate data = venda.getData();
            if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                double lucro = venda.getLucroTotal();
                double gasto = venda.getGasto();

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
                double gasto = venda.getGasto();

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

            Venda.setProximoId(1);
            Venda.setIdsDisponiveis(new LinkedList<>());

            String sql = "SELECT * FROM vendas";
            try (Connection conn = ConexaoPostgres.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    LocalDate data = rs.getDate("data").toLocalDate();
                    String descricao = rs.getString("descricao");
                    String formaPagamento = rs.getString("formaPagamento");
                    double total = rs.getDouble("valorTotal");
                    double ganhoBruto = rs.getDouble("ganhoBruto");
                    double gasto = rs.getDouble("gasto");

                    Venda venda = new Venda(new ArrayList<>(), formaPagamento);
                    venda.setId(id);
                    venda.setData(data);
                    venda.setTotal(total);
                    venda.setDescricao(descricao);
                    venda.setLucroTotal(ganhoBruto);
                    venda.setGasto(gasto);

                    vendasDoBanco.add(venda);
                }
            }

            vendasDoBanco.sort(Comparator.comparingInt(Venda::getId));

            vendas.clear();
            vendas.addAll(vendasDoBanco);

            Set<Integer> idsUsados = vendasDoBanco.stream()
                    .map(Venda::getId)
                    .collect(Collectors.toSet());
            int maiorId = idsUsados.stream().mapToInt(i -> i).max().orElse(0);

            List<Integer> idsDisponiveis = new LinkedList<>();
            for (int i = 1; i < maiorId; i++) {
                if (!idsUsados.contains(i)) {
                    idsDisponiveis.add(i);
                }
            }
            Venda.setIdsDisponiveis(idsDisponiveis);

            Venda.setProximoId(maiorId + 1);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar vendas do banco!");
        }
    }

}