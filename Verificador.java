import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Verificador {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void verificarDataValidade(String dataDeValidade) {
        try {
            LocalDate dataValida = LocalDate.parse(dataDeValidade, FORMATO);
            if (dataValida.isBefore(LocalDate.now())) {
                throw new ValidacaoException("Produto já está vencido!");
            }
        } catch (DateTimeParseException e) {
            throw new ValidacaoException("Formato de data inválido! Use dd/MM/yyyy.");
        }
    }

    public void verificarCodigo(int codigo) {
        if (codigo < 1000 || codigo > 9999) {
            throw new ValidacaoException("Código inválido! Deve conter 4 dígitos.");
        }
    }

    public void verificarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidacaoException("Nome não pode ser vazio.");
        }
        if (!nome.matches(".*\\p{L}.*")) {
            throw new ValidacaoException("Nome deve conter pelo menos uma letra.");
        }
    }

    public void verificarPreco(double preco) {
        if (preco <= 0) {
            throw new ValidacaoException("Preço inválido! Deve ser maior que zero.");
        }
    }

    public void verificarQuantidade(int quantidade) {
        if (quantidade < 0 ) {
            throw new ValidacaoException("Quantidade inválida! Deve ser maior que zero!");
        }
    }
    
    public void verificarLista(List<?> lista) {
        if (lista == null || lista.isEmpty()) {
            throw new ValidacaoException("Lista vazia! Nenhum item encontrado.");
        }
    }

    public void validarSenha(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new ValidacaoException("Senha inválida!");
        }
    }

    public void verificarValor(double valor) {
        if (valor < 0) {
            throw new ValidacaoException("Valor inválido! Não pode ser negativo.");
        }
    }
}
