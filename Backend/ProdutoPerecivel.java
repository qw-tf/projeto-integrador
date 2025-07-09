package Backend;

import java.time.LocalDate;

public class ProdutoPerecivel extends Produto {
    private LocalDate dataDeValidade;

    public ProdutoPerecivel(String nome, int quantidade, double valorCompra, double valorVenda, LocalDate dataDeValidade) {
        super(nome, quantidade, valorCompra, valorVenda);
        this.dataDeValidade = dataDeValidade;
    }

    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(LocalDate dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }
}
