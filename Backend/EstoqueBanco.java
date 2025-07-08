package Backend;

import java.util.LinkedList;
import java.util.List;

public class Estoque {
    private static int proximoId = 1;
    private static List<Integer> idsDisponiveis = new LinkedList<>();

    private int codEstoque;
    private int idProduto;
    private int quantidade;

    public Estoque(int idProduto, int quantidade) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;

        if (!idsDisponiveis.isEmpty()) {
            this.codEstoque = idsDisponiveis.remove(0);
        } else {
            this.codEstoque = proximoId++;
        }
    }

    // Getters e setters
    public int getCodEstoque() {
        return codEstoque;
    }

    public void setCodEstoque(int codEstoque) {
        this.codEstoque = codEstoque;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    // Controle de IDs
    public static void liberarId(int id) {
        idsDisponiveis.add(id);
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }
}
