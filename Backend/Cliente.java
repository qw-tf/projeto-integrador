package Backend;

import java.util.LinkedList;
import java.util.List;

public class Cliente {
    private String nome;
    private String cpf;
    private int id;

    private static int proximoId = 1;
    private static List<Integer> idsDisponiveis = new LinkedList<>();

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;

        if (!idsDisponiveis.isEmpty()) {
            this.id = idsDisponiveis.remove(0);
        } else {
            this.id = proximoId++;
        }
    }

    public Cliente(String nome) {
        this(nome, null); // CPF opcional
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public static void liberarId(int id) {
        idsDisponiveis.add(id);
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }
}
