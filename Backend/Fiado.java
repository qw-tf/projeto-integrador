package Backend;

import java.util.LinkedList;
import java.util.List;

public class Fiado {
    private static int proximoId = 1;
    private static List<Integer> idsDisponiveis = new LinkedList<>();

    private int idFiado;
    private String descricao;
    private int idCliente;
    private int idVenda;

    public Fiado(String descricao, int idCliente, int idVenda) {
        this.descricao = descricao;
        this.idCliente = idCliente;
        this.idVenda = idVenda;

        if (!idsDisponiveis.isEmpty()) {
            this.idFiado = idsDisponiveis.remove(0);
        } else {
            this.idFiado = proximoId++;
        }
    }

    // Getters e setters
    public int getIdFiado() {
        return idFiado;
    }

    public void setIdFiado(int idFiado) {
        this.idFiado = idFiado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    // Controle do próximo ID
    public static void liberarId(int id) {
        idsDisponiveis.add(id);
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }
}
