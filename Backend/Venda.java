package Backend;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Venda {
    private static int proximoId = 1;
    private static List<Integer> idsDisponiveis = new LinkedList<>();

    private int id;
    private List<ItemVenda> itens;
    private LocalDate data;
    private String formaPagamento;
    private double valorVenda;
    private double lucroTotal;
    private String descricao;
    private double gasto;

    public Venda(List<ItemVenda> itens, String formaPagamento) {
        if (!idsDisponiveis.isEmpty()) {
            this.id = idsDisponiveis.remove(0);
        } else {
            this.id = proximoId++;
        }

        this.itens = itens;
        this.data = LocalDate.now();
        this.formaPagamento = formaPagamento;
        this.valorVenda = calcularTotal();
        this.lucroTotal = calcularLucroTotal();
        this.descricao = gerarResumoDosItens();
        this.gasto = getGastoTotal();
    }

    public Venda(int id) {
        this.id = id;
        this.itens = null;
        this.data = null;
        this.formaPagamento = null;
        this.valorVenda = 0.0;
        this.lucroTotal = 0.0;
        this.descricao = null;

        idsDisponiveis.remove(Integer.valueOf(id));
        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }

    public static void liberarId(int id) {
        if (!idsDisponiveis.contains(id)) {
            idsDisponiveis.add(id);
            idsDisponiveis.sort(Integer::compareTo);
        }
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }

    public static void setIdsDisponiveis(List<Integer> ids) {
        idsDisponiveis = ids;
    }

    public void setId(int id) {
        this.id = id;
        idsDisponiveis.remove(Integer.valueOf(id));
        if (id >= proximoId) {
            proximoId = id + 1;
        }
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public void setTotal(double total) {
        this.valorVenda = total;
    }

    public void setGasto(double gasto) {
        this.gasto = gasto;
    }

    public void setLucroTotal(double lucroTotal) {
        this.lucroTotal = lucroTotal;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public double getTotal() {
        return valorVenda;
    }

    public double getValor() {
        return valorVenda;
    }

    public double getLucroTotal() {
        return lucroTotal;
    }

    public double getGasto() {
        return gasto;
    }

    public double getGastoTotal() {
        double total = 0;
        if (itens == null)
            return 0.0;
        for (ItemVenda item : itens) {
            double custoUnitario = item.getProduto().getValorCompra() / item.getProduto().getQuantidadeTotal();
            total += custoUnitario * item.getQuantidade();
        }
        return total;
    }

    private double calcularTotal() {
        if (itens == null)
            return 0.0;
        return itens.stream().mapToDouble(ItemVenda::getSubtotal).sum();
    }

    private double calcularLucroTotal() {
        if (itens == null)
            return 0.0;
        double total = 0;
        for (ItemVenda item : itens) {
            double custoUnitario = item.getProduto().getValorCompra() / item.getProduto().getQuantidadeTotal();
            double lucroPorUnidade = item.getProduto().getValorVenda() - custoUnitario;
            total += lucroPorUnidade * item.getQuantidade();
        }
        return total;
    }

    public int getQuantidadeTotal() {
        return itens != null ? itens.stream().mapToInt(ItemVenda::getQuantidade).sum() : 0;
    }

    public String gerarResumoDosItens() {
        if (itens == null || itens.isEmpty())
            return "Sem itens!";
        StringBuilder sb = new StringBuilder();
        for (ItemVenda item : itens) {
            sb.append(item.getProduto().getNome())
                    .append(" (x").append(item.getQuantidade()).append("), ");
        }
        String resumo = sb.substring(0, sb.length() - 2);
        this.descricao = resumo;
        return resumo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static int getProximoId() {
        return proximoId;
    }

}
