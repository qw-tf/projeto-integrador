package Backend;

import java.time.LocalDate;

public class Gasto {
    private final String descricao;
    private final double valor;
    private final LocalDate data;
    private final boolean pessoal;

    public Gasto(String descricao, double valor, LocalDate data, boolean pessoal) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.pessoal = pessoal;
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
}
    