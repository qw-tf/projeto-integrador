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
        for (Produto p : RegistroProdutos.getProdutos()) {
            if (p instanceof ProdutoPerecivel) {
                ProdutoPerecivel pp = (ProdutoPerecivel) p;
                LocalDate validade = pp.getDataDeValidade();
                if (validade != null) {
                    long diasParaVencer = ChronoUnit.DAYS.between(hoje, validade);
                    if (diasParaVencer >= 0 && diasParaVencer <= DIAS_AVISO_VALIDADE) {
                        avisos.add(String.format("  ♦  Produto '%s' vence em %d dia(s) (Validade: %s).",
                                p.getNome(), diasParaVencer, validade));
                    }
                }
            }
        }

        // 2. Fiados vencidos
        LocalDate limiteFiado = hoje.minusDays(DIAS_AVISO_FIADO);
        for (Fiado fiado : RepositorioFiados.getFiados()) {
            if (fiado.getDataCriacao().isBefore(limiteFiado) && !fiado.isQuitado()) {
                avisos.add(String.format(
                        "  ♣  Fiado do cliente '%s' está vencido há %d dia(s).",
                        fiado.getNomeCliente(), ChronoUnit.DAYS.between(fiado.getDataCriacao(), hoje)));
            }
        }

        // 3. Estoque baixo (menos que 20% do estoque total)
        for (Produto p : RegistroProdutos.getProdutos()) {
            int atual = p.getQuantidade();
            int total = p.getQuantidadeTotal();

            if (total > 0) {
                double percentual = (double) atual / total;
                if (percentual <= 0.2) {
                    avisos.add(String.format("  ♠  Estoque baixo do produto '%s': %.0f%% restante (%d de %d).",
                            p.getNome(), percentual * 100, atual, total));
                }
            }
        }

        return avisos;
    }
}
// ♦
// ♠
// ♥
// ♣