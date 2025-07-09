package Backend;

import java.util.ArrayList;
import java.util.List;

public class FiadoRepositorio {
    private static final List<Fiado> fiados = new ArrayList<>();

    public static void adicionarFiado(Fiado f) {
        fiados.add(f);
    }

    public static List<Fiado> getFiados() {
        return fiados;
    }

    public static Fiado buscarPorId(int id) {
        return fiados.stream().filter(f -> f.getIdFiado() == id).findFirst().orElse(null);
    }
}
