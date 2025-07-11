package Backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FiadoRepositorio {

    private static final List<Fiado> fiados = new ArrayList<>();

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
