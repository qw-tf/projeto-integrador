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
    private LocalDate dataQuitado;

    // ⚓ Novo construtor para Fiado
    public Fiado(String descricao, Venda venda) {
        this.nomeCliente = descricao;
        this.idVenda = venda.getId();
        this.valorRestante = venda.getValor(); // OU getTotal()
        this.quitado = false;

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

    public LocalDate getDataQuitado() {
        return dataQuitado;
    }

    // Operações de ID global
    public static void liberarId(int id) {
        idsDisponiveis.add(id);
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }


    public void registrarPagamento(double valorPago) {
        if (valorPago <= 0) throw new IllegalArgumentException("Valor tem que ser positivo!");
        if (quitado) throw new IllegalStateException("Venda já quitada!");
    
        valorRestante -= valorPago;
        if (valorRestante <= 0) {
            valorRestante = 0.0;
            quitado = true;
            dataQuitado = LocalDate.now();  // aqui!
        }
    }
    
    public void quitarTotalmente() {
        this.valorRestante = 0.0;
        this.quitado = true;
        this.dataQuitado = LocalDate.now(); // aqui também
    }
    public String getResumoFiado() {
        return "FIADO #" + idFiado +
                " | Cliente: " + nomeCliente +
                " | Venda: " + idVenda +
                " | Valor Restante: " + valorRestante +
                " | Data Quitado: " + (dataQuitado != null ? dataQuitado : "NÃO QUITADO") +
                " | Quitado: " + (quitado ? "SIM" : "NÃO");
    }

    public void setValorRestante(double valorRestante) {
        this.valorRestante = valorRestante;
        this.quitado = (valorRestante == 0.0);
    }

    public void setDataQuitado(LocalDate dataQuitado) {
        this.dataQuitado = dataQuitado;
    }
    
    public void setQuitado(boolean quitado) {
        this.quitado = quitado;
    }

}
