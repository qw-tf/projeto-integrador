package Backend;

import java.util.ArrayList;
import java.util.List;

public class RegistroVendas {
    private static List<Venda> vendas = new ArrayList<>();

    public static void adicionarVenda(List<ItemVenda> itens) {
        // Valida se tem estoque suficiente para todos os produtos
        for (ItemVenda item : itens) {
            Produto p = item.getProduto();
            if (p.getQuantidade() < item.getQuantidade()) {
                throw new IllegalArgumentException("Estoque insuficiente para o produto: " + p.getNome());
            }
        }

        // Desconta o estoque
        for (ItemVenda item : itens) {
            Produto p = item.getProduto();
            p.setQuantidade(p.getQuantidade() - item.getQuantidade());
        }

        // Adiciona nova venda
        vendas.add(new Venda(itens));
    }

    public static List<Venda> getTodasVendas() {
        return new ArrayList<>(vendas);
    }

    public static boolean excluirVenda(int id) {
        return vendas.removeIf(v -> v.getId() == id);
    }
}
