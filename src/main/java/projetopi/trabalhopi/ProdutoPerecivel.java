public class ProdutoPerecivel extends Produto{
    //variaveis extras da classe
    private String dataDeValidade;

    //construtor base
    public ProdutoPerecivel(String nome, int quantidade, double preco, String dataDeValidade){
        super(nome, quantidade, preco);
        this.dataDeValidade = dataDeValidade;
    }

    //gets e sets
    public String getDataDeValidade() {
        return dataDeValidade;
    }
    public void setDataDeValidade(String dataDeValidade) {
        this.dataDeValidade = dataDeValidade;
    }
}
