// Produto.java
package Backend;

import java.util.LinkedList;
import java.util.List;

public class Produto {
    private String nome;
    private int quantidade, codigo;
    private double preco;
    private double valorCompra;
    private double valorVenda;

    private static int proximoCodigo = 1;
    private static List<Integer> codigosDisponiveis = new LinkedList<>();

    public Produto(String nome, int quantidade, double valorCompra, double valorVenda) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorCompra = valorCompra;
        this.valorVenda = valorVenda;
        this.preco = valorVenda; // preco exibe valorVenda no sistema

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

    public double getValorCompra() {
        return valorCompra;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setValorCompra(double valorCompra) {
        this.valorCompra = valorCompra;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
        this.preco = valorVenda; // Atualiza o preco mostrado
    }

    public static int getProximoCodigo() {
        return proximoCodigo;
    }

    public static void setProximoCodigo(int codigo) {
        proximoCodigo = codigo;
    }

    public static void liberarCodigo(int codigo) {
        codigosDisponiveis.add(codigo);
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d | Qtd: %d | R$ %.2f)", nome, codigo, quantidade, valorVenda);
    }

}
