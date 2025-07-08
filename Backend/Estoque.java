package Backend;

import java.util.ArrayList;
import java.util.List;

public class Estoque {
    private static List<Produto> produtos = new ArrayList<>();

    public static void adicionarProduto(String nome, int quantidade, double preco, boolean perecivel, java.time.LocalDate validade) throws ValidacaoException {
        if (nome == null || nome.isEmpty()) throw new ValidacaoException("Nome não pode ser vazio.");
        if (quantidade < 0) throw new ValidacaoException("Quantidade não pode ser negativa.");
        if (preco < 0) throw new ValidacaoException("Preço não pode ser negativo.");

        Produto novoProduto;
        if (perecivel) {
            if (validade == null) throw new ValidacaoException("Data de validade obrigatória para produto perecível.");
            novoProduto = new ProdutoPerecivel(nome, quantidade, preco, validade);
        } else {
            novoProduto = new Produto(nome, quantidade, preco);
        }
        produtos.add(novoProduto);
    }

    public static boolean removerProdutoPorId(int codigo, int quantidade) throws ValidacaoException {
        Produto produto = buscarProduto(codigo);
        if (produto != null) {
            if (quantidade <= 0) {
                throw new ValidacaoException("Quantidade de remoção deve ser maior que zero.");
            }
            if (produto.getQuantidade() < quantidade) {
                throw new ValidacaoException("Quantidade em estoque insuficiente!");
            }
            produto.setQuantidade(produto.getQuantidade() - quantidade);
    
            // Se esgotou, remove da lista
            if (produto.getQuantidade() == 0) {
                produtos.remove(produto);
                Produto.liberarCodigo(produto.getCodigo());
            }
    
            return true;
        }
        return false; // Produto não encontrado
    }
    

    public static boolean excluirProduto(int codigo) {
        Produto produto = buscarProduto(codigo);
        if (produto != null) {
            produtos.remove(produto);
            Produto.liberarCodigo(produto.getCodigo());
            return true;
        }
        return false;
    }

    public static Produto buscarProduto(int codigo) {
        for (Produto p : produtos) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }
        return null;
    }

    public static List<Produto> getProdutos() {
        return produtos;
    }
}