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
     private int id_cliente;

    public Venda(List<ItemVenda> itens) {
        this.id = proximoId++;
        this.itens = itens;
        this.data = LocalDate.now();
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
        return itens.stream().mapToDouble(ItemVenda::getSubtotal).sum();
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

    public String getFormaPagamento(){

        return formaPagamento;
    } 
     public double getValor(){

        return valorVenda;
    } 
    public int getIdCliente(){

        return id_cliente;
    } 
}
