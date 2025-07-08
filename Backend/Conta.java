package Backend;

import java.util.LinkedList;
import java.util.List;

public class Conta {
    private int id;
    private String descricao;
    private double valor;

    private static int proximoId = 1;
    private static List<Integer> idsDisponiveis = new LinkedList<>();

    public Conta(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;

        if (!idsDisponiveis.isEmpty()) {
            this.id = idsDisponiveis.remove(0);
        } else {
            this.id = proximoId++;
        }
    }

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public static void liberarId(int id) {
        idsDisponiveis.add(id);
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }
}
