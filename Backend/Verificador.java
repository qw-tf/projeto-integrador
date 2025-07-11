package Backend;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Verificador {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void verificarDataValidade(LocalDate dataDeValidade) {
        if (dataDeValidade.isBefore(LocalDate.now())) {
            throw new ValidacaoException("Produto já está vencido!");
        }
    }

    public static void verificarCodigo(int codigo) {
        if (codigo < 0 || codigo > 9999999) {
            throw new ValidacaoException("Código inválido! Deve conter 4 dígitos.");
        }
    }

    public static void verificarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome não pode ser vazio.");
        }
        if (!nome.matches(".*\\p{L}.*")) {
            throw new ValidacaoException("Nome deve conter pelo menos uma letra.");
        }
    }

    public static void verificarPreco(double preco) {
        if (preco <= 0) {
            throw new ValidacaoException("Preço inválido! Deve ser maior que zero.");
        }
    }

    public static void verificarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new ValidacaoException("Quantidade inválida! Deve ser maior que zero!");
        }
    }

    public static void verificarLista(List<?> lista) {
        if (lista == null || lista.isEmpty()) {
            throw new ValidacaoException("Lista vazia! Nenhum item encontrado.");
        }
    }

    public static void validarSenha(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new ValidacaoException("Senha inválida!");
        }
    }

    public static void verificarValor(double valor) {
        if (valor < 0) {
            throw new ValidacaoException("Valor inválido! Não pode ser negativo.");
        }
    }
}
