package Backend;

public class ItemVenda {
    private Produto produto;
    private int quantidade;
    private int quantidadeTotal;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.quantidadeTotal = quantidade;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getSubtotal() {
        return produto.getValorVenda() * quantidade;
    }

}
