package Backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RegistroVendas {
    private static List<Venda> vendas = new ArrayList<>();

    public static void adicionarVenda(List<ItemVenda> itens, String formaPagamento) {
        // Valida estoque
        for (ItemVenda item : itens) {
            Produto p = item.getProduto();
            if (p.getQuantidade() < item.getQuantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + p.getNome());
            }
        }

        // Desconta do estoque
        for (ItemVenda item : itens) {
            Produto p = item.getProduto();
            p.setQuantidade(p.getQuantidade() - item.getQuantidade());
        }

        // Cria e salva a venda
        Venda novaVenda = new Venda(itens, formaPagamento);
        vendas.add(novaVenda);
    }

    public static List<Venda> getTodasVendas() {
        return new ArrayList<>(vendas);
    }

    public static boolean excluirVenda(int id) {
        return vendas.removeIf(v -> v.getId() == id);
    }

    // 🔹 BALANÇO POR DIA
    public static Map<LocalDate, Double[]> calcularBalancoPorDia(LocalDate inicio, LocalDate fim) {
        Map<LocalDate, Double[]> resultado = new TreeMap<>();

        for (Venda venda : vendas) {
            LocalDate data = venda.getData();
            if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                double lucro = venda.getLucroTotal();
                double gasto = venda.getGastoTotal();

                resultado.putIfAbsent(data, new Double[]{0.0, 0.0});
                Double[] valores = resultado.get(data);
                valores[0] += lucro;
                valores[1] += gasto;
            }
        }

        return resultado;
    }

    // 🔹 BALANÇO POR MÊS
    public static Map<Integer, Double[]> calcularBalancoPorMes(LocalDate inicio, LocalDate fim) {
        Map<Integer, Double[]> resultado = new TreeMap<>();

        for (Venda venda : vendas) {
            LocalDate data = venda.getData();
            if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                int mes = data.getMonthValue();
                double lucro = venda.getLucroTotal();
                double gasto = venda.getGastoTotal();

                resultado.putIfAbsent(mes, new Double[]{0.0, 0.0});
                Double[] valores = resultado.get(mes);
                valores[0] += lucro;
                valores[1] += gasto;
            }
        }

        return resultado;
    }
}
