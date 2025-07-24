package Backend;

import java.util.LinkedList;
import java.util.List;

import Banco.ProdutoDAO;

public class Produto {
    private String nome;
    private int quantidade, codigo, quantidadeTotal;
    private double valorCompra;
    private double valorVenda;

    private static int proximoCodigo = 1;
    private static List<Integer> codigosDisponiveis = new LinkedList<>();

    public Produto(String nome, int quantidade, double valorCompra, double valorVenda) {
        Verificador.verificarNome(nome);
        Verificador.verificarQuantidade(quantidade);
        Verificador.verificarPreco(valorCompra);
        Verificador.verificarPreco(valorVenda);

        this.nome = nome;
        this.quantidade = quantidade;
        this.quantidadeTotal = quantidade;
        this.valorCompra = valorCompra;
        this.valorVenda = valorVenda;

        if (!codigosDisponiveis.isEmpty()) {
            this.codigo = codigosDisponiveis.remove(0);
        } else {
            this.codigo = proximoCodigo++;
        }
    }

    public void removerQuantidade(int qt) {
        if (qt <= 0) {
            throw new IllegalArgumentException("Quantidade a remover deve ser positiva!");
        }
        if (this.quantidade < qt) {
            throw new IllegalArgumentException("Quantidade insuficiente no estoque!");
        }
        this.quantidade -= qt;
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

    public double getValorCompra() {
        return valorCompra;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setCodigo(int codigo) {
        Verificador.verificarCodigo(codigo);
        this.codigo = codigo;

        // Remove da lista de disponíveis caso esteja lá
        codigosDisponiveis.remove(Integer.valueOf(codigo));

        // Atualiza o próximo código se necessário
        if (codigo >= proximoCodigo) {
            proximoCodigo = codigo + 1;
        }
    }

    public void setNome(String nome) {
        Verificador.verificarNome(nome);
        this.nome = nome;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    public void setValorCompra(double valorCompra) {
        Verificador.verificarPreco(valorCompra);
        this.valorCompra = valorCompra;
    }

    public void setValorVenda(double valorVenda) {
        Verificador.verificarPreco(valorVenda);
        this.valorVenda = valorVenda;
    }

    public static void liberarCodigo(int codigo) {
        if (!codigosDisponiveis.contains(codigo)) {
            codigosDisponiveis.add(codigo);
            codigosDisponiveis.sort(Integer::compareTo);
        }
    }

    public static void setProximoCodigo(int codigo) {
        proximoCodigo = codigo;
    }

    public static int getProximoCodigo() {
        return proximoCodigo;
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d | Qtd: %d | R$ %.2f)", nome, codigo, quantidade, valorVenda);
    }

    public static void setCodigosDisponiveis(List<Integer> codigos) {
        codigosDisponiveis = codigos;
    }

}
