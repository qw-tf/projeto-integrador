package Backend;

import java.time.LocalDate;
import java.util.List;

public class Venda {
    private static int proximoId = 1;

    private int id;
    private List<ItemVenda> itens;
    private LocalDate data;
    private String formaPagamento;
    private double valorVenda;
    private int idCliente; // ⚓ ISSO AQUI TEM QUE EXISTIR!
    // Na classe Venda

    public Venda(List<ItemVenda> itens, String formaPagamento) {
        this.id = proximoId++;
        this.itens = itens;
        this.data = LocalDate.now();
        this.formaPagamento = formaPagamento;
        this.valorVenda = calcularTotal();
        this.idCliente = -1; // ou 0, ou algum valor padrão indicando "sem cliente"
    }

    public int getIdCliente() { // ⚓ ISSO AQUI TEM QUE EXISTIR!
        return idCliente;
    }

    private double calcularTotal() {
        return itens.stream().mapToDouble(ItemVenda::getSubtotal).sum();
    }

    public int getId() {
        return id;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public LocalDate getData() {
        return data;
    }

    public double getTotal() {
        return valorVenda;
    }

    public double getValor() {
        return valorVenda;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public String getResumoProdutos() {
        StringBuilder sb = new StringBuilder();
        for (ItemVenda item : itens) {
            sb.append(item.getProduto().getNome())
                    .append(" (x").append(item.getQuantidade()).append("), ");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
    }

    public int getQuantidadeTotal() {
        return itens.stream().mapToInt(ItemVenda::getQuantidade).sum();
    }

    public double getLucroTotal() {
        double total = 0;
        for (ItemVenda item : itens) {
            double lucroPorUnidade = item.getProduto().getValorVenda() - item.getProduto().getValorCompra();
            total += lucroPorUnidade * item.getQuantidade();
        }
        return total;
    }

    public double getGastoTotal() {
        double total = 0;
        for (ItemVenda item : itens) {
            total += item.getProduto().getValorCompra() * item.getQuantidade();
        }
        return total;
    }
}
