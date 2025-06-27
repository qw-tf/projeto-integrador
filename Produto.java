public class Produto {
    // atributos pedidos no arquivo,
    // escolhemos adicionar código do produto e limite maximo de estoque.
    private String nome;
    private int quantidade, codigo;
    private double preco;

    // variavel static para contar a quantidade total de produtos, e a
    // variavel para ajudar a carregar os codigos dos produtos corretamente"
    private static int proximoCodigo = 0;

    // construtor da classe
    public Produto(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;

        codigo = proximoCodigo++; // automaticamente da um novo codigo a um produto
    }

    // gets e sets dos atributos
    public int getQuantidade() {
        return quantidade;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getProximoCodigo() {
        return proximoCodigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public static void setProximoCodigo(int codigo) {
        proximoCodigo = codigo;
    }
}