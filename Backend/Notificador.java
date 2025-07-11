package Backend;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Notificador {

    private static final int DIAS_AVISO_VALIDADE = 7;
    private static final int DIAS_AVISO_FIADO = 7;
    private static final int DIAS_AVISO_CONTA = 7;

    public static List<String> gerarNotificacoes() {
        List<String> avisos = new ArrayList<>();

        LocalDate hoje = LocalDate.now();

        // 1. Produtos perto da validade
        for (Produto p : Estoque.getProdutos()) {
            if (p instanceof ProdutoPerecivel) {
                ProdutoPerecivel pp = (ProdutoPerecivel) p;
                LocalDate validade = pp.getDataDeValidade();
                if (validade != null) {
                    long diasParaVencer = ChronoUnit.DAYS.between(hoje, validade);
                    if (diasParaVencer >= 0 && diasParaVencer <= DIAS_AVISO_VALIDADE) {
                        avisos.add(String.format("Produto '%s' vence em %d dia(s) (Validade: %s).",
                                p.getNome(), diasParaVencer, validade));
                    }
                }
            }
        }

        // 2. Fiados vencidos
        LocalDate limiteFiado = hoje.minusDays(DIAS_AVISO_FIADO);
        for (Fiado fiado : FiadoRepositorio.getFiados()) { // CORREÇÃO AQUI
            if (fiado.getDataCriacao().isBefore(limiteFiado) && !fiado.isQuitado()) {
                avisos.add(String.format("Fiado do cliente ID %d está vencido há %d dia(s).",
                        fiado.getIdCliente(), ChronoUnit.DAYS.between(fiado.getDataCriacao(), hoje)));
            }
        }

        // 3. Contas (gastos marcados) não pagas e aviso diário
        for (Gasto g : RepositorioGastos.listarEmpresariais()) {
            if (g.isConta() && !g.isPago()) {
                long diasDesdeData = ChronoUnit.DAYS.between(g.getData(), hoje);
                if (diasDesdeData >= DIAS_AVISO_CONTA || diasDesdeData == 0) {
                    avisos.add(String.format("Conta '%s' não paga, %d dia(s) desde o vencimento.",
                            g.getDescricao(), diasDesdeData));
                }
            }
        }

        return avisos;
    }
}
