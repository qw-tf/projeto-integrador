package Backend;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Fiado {
    // Controle de IDs
    private static int proximoId = 1;
    private static List<Integer> idsDisponiveis = new LinkedList<>();

    // Campos principais
    private int idFiado;
    private String nomeCliente;
    private int idVenda;

    private double valorRestante;
    private boolean quitado;
    private LocalDate dataCriacao;

    // ⚓ Novo construtor para Fiado
    public Fiado(String descricao, Venda venda) {
        this.nomeCliente = descricao;
        this.idVenda = venda.getId();
        this.valorRestante = venda.getValor(); // OU getTotal()
        this.quitado = false;
        this.dataCriacao = LocalDate.now();

        if (!idsDisponiveis.isEmpty()) {
            this.idFiado = idsDisponiveis.remove(0);
        } else {
            this.idFiado = proximoId++;
        }
    }

    // Getters e Setters
    public int getIdFiado() {
        return idFiado;
    }

    public void setIdFiado(int idFiado) {
        this.idFiado = idFiado;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String descricao) {
        this.nomeCliente = descricao;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public double getValorRestante() {
        return valorRestante;
    }

    public boolean isQuitado() {
        return quitado;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    // Operações de ID global
    public static void liberarId(int id) {
        idsDisponiveis.add(id);
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }

    // Métodos de lógica de pagamento
    public void registrarPagamento(double valorPago) {
        if (valorPago <= 0) {
            throw new IllegalArgumentException("NÃO ME VEM COM TROCO DE PIRATA, VALOR TEM QUE SER POSITIVO!");
        }
        if (quitado) {
            throw new IllegalStateException("JÁ TÁ QUITADO, MARUJO. PÁRA DE MEXER NESSE OURO!");
        }

        valorRestante -= valorPago;
        if (valorRestante <= 0) {
            valorRestante = 0.0;
            quitado = true;
        }
    }

    public void quitarTotalmente() {
        this.valorRestante = 0.0;
        this.quitado = true;
    }

    public String getResumoFiado() {
        return "FIADO #" + idFiado +
                " | Cliente: " + nomeCliente +
                " | Venda: " + idVenda +
                " | Valor Restante: " + valorRestante +
                " | Data: " + dataCriacao +
                " | Quitado: " + (quitado ? "SIM" : "NÃO");
    }
}
