package Backend;

import java.util.LinkedList;
import java.util.List;

public class Produto {
    private String nome;
    private int quantidade, codigo;
    private double preco;

    // Controle de códigos
    private static int proximoCodigo = 1;
    private static List<Integer> codigosDisponiveis = new LinkedList<>();

    public Produto(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;

        if (!codigosDisponiveis.isEmpty()) {
            this.codigo = codigosDisponiveis.remove(0);
        } else {
            this.codigo = proximoCodigo++;
        }
    }

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

    public static int getProximoCodigo() {
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

    public static void liberarCodigo(int codigo) {
        codigosDisponiveis.add(codigo);
    }
    
}
