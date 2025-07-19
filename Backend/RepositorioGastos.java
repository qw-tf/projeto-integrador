// Backend/RepositorioGastos.java
package Backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepositorioGastos {
    private static final List<Gasto> gastosPessoais = new ArrayList<>();
    private static final List<Gasto> gastosEmpresariais = new ArrayList<>();

    public static void adicionarGasto(Gasto gasto) {
        if (gasto.isPessoal()) {
            gastosPessoais.add(gasto);
        } else {
            gastosEmpresariais.add(gasto);
        }
    }

    public static List<Gasto> listarPessoais() {
        return Collections.unmodifiableList(gastosPessoais);
    }

    public static List<Gasto> listarEmpresariais() {
        return Collections.unmodifiableList(gastosEmpresariais);
    }
}
