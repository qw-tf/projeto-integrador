package Backend;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Estoque {

    // A LISTA ABSOLUTA, GLOBAL, INTOCÁVEL E DE MILHÕES
    private static final List<Produto> produtos = new ArrayList<>();

    // Impede que alguém instancie essa deusa
    private Estoque() {
    }

    // Retorna a lista em versão IMUTÁVEL (só leitura)
    public static List<Produto> getProdutos() {
        return Collections.unmodifiableList(produtos);
    }

    // Adiciona produto com validações
    public static void adicionarProduto(String nome, int quantidade, double preco, boolean perecivel,
            LocalDate dataValidade)
            throws ValidacaoException {
        Verificador.verificarNome(nome);
        Verificador.verificarQuantidade(quantidade);
        Verificador.verificarPreco(preco);

        Produto produto;
        if (perecivel) {
            Verificador.verificarDataValidade(dataValidade);
            produto = new ProdutoPerecivel(nome, quantidade, preco, dataValidade);
        } else {
            produto = new Produto(nome, quantidade, preco);
        }

        produtos.add(produto);
        GerarLogs.logAutomatic("Produto cadastrado com sucesso!");
    }

    // Remove produto pelo código
    public static boolean excluirProduto(int codigo) throws ValidacaoException {
        Verificador.verificarCodigo(codigo);
        Produto produto = buscarProduto(codigo);

        if (produto != null) {
            produtos.remove(produto);
            GerarLogs.logAutomatic("Produto removido: " + produto.getNome());
            return true;
        }

        GerarLogs.logAutomatic("Tentativa de exclusão falhou. Produto não encontrado.");
        return false;
    }

    // Busca produto por código
    public static Produto buscarProduto(int codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }

    // Atualiza quantidade de produto
    public static void removerProduto(int codigo, int quantidade) throws ValidacaoException {
        Produto produto = buscarProduto(codigo);
        if (produto != null) {
            int novaQuantidade = produto.getQuantidade() - quantidade;
            if (novaQuantidade < 0) {
                throw new ValidacaoException("Não é possível remover mais do que o estoque atual!");
            }
            produto.setQuantidade(novaQuantidade);
            GerarLogs
                    .logAutomatic("Produto atualizado: " + produto.getNome() + " | Nova quantidade: " + novaQuantidade);
        } else {
            throw new ValidacaoException("Produto não encontrado!");
        }
    }
}
