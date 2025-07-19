package Backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioFiados {

    private static final List<Fiado> fiados = new ArrayList<>();

    public static void carregarFiadosDoBanco() {
        try {
            List<Fiado> fiadosDoBanco = Banco.FiadoDAO.listarFiados();
            fiados.clear();
            fiados.addAll(fiadosDoBanco);
            System.out.println("Fiados carregados com sucesso do banco!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar fiados do banco: " + e.getMessage());
            e.printStackTrace();
        }
    }    

    public static void adicionarFiado(Fiado fiado) {
        fiados.add(fiado);
    }

    public static boolean removerFiado(Fiado fiado) {
        return fiados.remove(fiado);
    }

    public static List<Fiado> getFiados() {
        return Collections.unmodifiableList(fiados);
    }

    public static Fiado buscarPorId(int id) {
        for (Fiado fiado : fiados) {
            if (fiado.getIdFiado() == id) {
                return fiado;
            }
        }
        return null; // se não encontrar
    }
}
