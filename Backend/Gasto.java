package Backend;

import java.time.LocalDate;

public class Gasto {
    private final String descricao;
    private final double valor;
    private final LocalDate data;
    private final boolean pessoal;

    private final boolean conta;
    private boolean pago;

    public Gasto(String descricao, double valor, LocalDate data, boolean pessoal, boolean conta) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.pessoal = pessoal;
        this.conta = conta;
        this.pago = false; // por padrão, ainda não está paga
    }

    // Novo construtor para facilitar criação com conta = false
    public Gasto(String descricao, double valor, LocalDate data, boolean pessoal) {
        this(descricao, valor, data, pessoal, false);
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public boolean isPessoal() {
        return pessoal;
    }

    public boolean isConta() {
        return conta;
    }

    public boolean isPago() {
        return pago;
    }

    public void marcarComoPago() {
        this.pago = true;
    }
}
