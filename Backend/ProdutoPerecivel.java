package Backend;
import java.time.LocalDate;

public class ProdutoPerecivel extends Produto {
    // variaveis extras da classe
    private LocalDate dataDeValidade;

    // construtor base
    public ProdutoPerecivel(String nome, int quantidade, double preco, LocalDate dataDeValidade) {
        super(nome, quantidade, preco);
        this.dataDeValidade = dataDeValidade;
    }

    // gets e sets
    public LocalDate getDataDeValidade() {
        return dataDeValidade;
    }

    public void setDataDeValidade(LocalDate dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }
}
