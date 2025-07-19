package Backend;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Fiado {
    private static int proximoId = 1;
    private static List<Integer> idsDisponiveis = new LinkedList<>();

    private int idFiado;
    private String nomeCliente;
    private int idVenda;

    private double valorRestante;
    private boolean quitado;
    private LocalDate dataCriacao;
    private LocalDate dataQuitado;  // NOVO campo para data que o fiado foi quitado

    public Fiado(String descricao, Venda venda) {
        this.nomeCliente = descricao;
        this.idVenda = venda.getId();
        this.valorRestante = venda.getValor();
        this.quitado = false;
        this.dataCriacao = LocalDate.now();
        this.dataQuitado = null; // ainda não quitado

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

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataQuitado() {
        return dataQuitado;
    }

    public void setDataQuitado(LocalDate dataQuitado) {
        this.dataQuitado = dataQuitado;
    }

    // Métodos para atualizar pagamento e quitado
    public void registrarPagamento(double valorPago) {
        if (valorPago <= 0) throw new IllegalArgumentException("Valor tem que ser positivo!");
        if (quitado) throw new IllegalStateException("Venda já quitada!");

        valorRestante -= valorPago;
        if (valorRestante <= 0) {
            valorRestante = 0.0;
            quitado = true;
            dataQuitado = LocalDate.now();  // marca a data do pagamento completo
        }
    }

    public void quitarTotalmente() {
        this.valorRestante = 0.0;
        this.quitado = true;
        this.dataQuitado = LocalDate.now();  // marca a data do pagamento completo
    }

    public String getResumoFiado() {
        return "FIADO #" + idFiado +
                " | Cliente: " + nomeCliente +
                " | Venda: " + idVenda +
                " | Valor Restante: " + valorRestante +
                " | Data Criação: " + dataCriacao +
                " | Quitado: " + (quitado ? "SIM" : "NÃO") +
                " | Data Quitado: " + (dataQuitado != null ? dataQuitado : "NÃO QUITADO");
    }

    public void setValorRestante(double valorRestante) {
        this.valorRestante = valorRestante;
        this.quitado = (valorRestante == 0.0);
        if (this.quitado && this.dataQuitado == null) {
            this.dataQuitado = LocalDate.now();
        }
    }

    public void setQuitado(boolean quitado) {
        this.quitado = quitado;
        if (quitado && this.dataQuitado == null) {
            this.dataQuitado = LocalDate.now();
        } else if (!quitado) {
            this.dataQuitado = null;
        }
    }

    public static void liberarId(int id) {
        idsDisponiveis.add(id);
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }
}
