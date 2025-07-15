package Backend;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Banco.ProdutoDAO;

public class Estoque {

    private static List<Produto> produtos = new ArrayList<>();

    public static void adicionarProduto(String nome, int quantidade, double valorCompra, double valorVenda,
            boolean perecivel, LocalDate validade) throws ValidacaoException {
        if (nome == null || nome.isEmpty()) {
            throw new ValidacaoException("Nome não pode ser vazio.");
        }
        if (quantidade < 0) {
            throw new ValidacaoException("Quantidade não pode ser negativa.");
        }
        if (valorCompra < 0) {
            throw new ValidacaoException("Valor de compra não pode ser negativo.");
        }
        if (valorVenda < 0) {
            throw new ValidacaoException("Valor de venda não pode ser negativo.");
        }

        Produto novoProduto;
        if (perecivel) {
            if (validade == null) {
                throw new ValidacaoException("Data de validade obrigatória para produto perecível.");
            }
            novoProduto = new ProdutoPerecivel(nome, quantidade, valorCompra, valorVenda, validade);
        } else {
            novoProduto = new Produto(nome, quantidade, valorCompra, valorVenda);
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

            if (produto.getQuantidade() == 0) {
                produtos.remove(produto);
                Produto.liberarCodigo(produto.getCodigo());
            }

            return true;
        }
        return false;
    }

    public static boolean excluirProduto(int codigo) throws SQLException{
        Produto produto = buscarProduto(codigo);
        if (produto != null) {
            produtos.remove(produto);
            Produto.liberarCodigo(produto.getCodigo());
            ProdutoDAO.deletar(codigo);
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

    // ⚓ NOVO MÉTODO: VERIFICADOR DE PRODUTO ZERADO ⚓
    public static void verificarEExcluirZerados() {
        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto p = iterator.next();
            if (p.getQuantidade() == 0) {
                iterator.remove();
                Produto.liberarCodigo(p.getCodigo());
            }
        }
    }

    public static void verificarEExcluirZeradosOuVencidos() {
        Iterator<Produto> iterator = produtos.iterator();
        while (iterator.hasNext()) {
            Produto p = iterator.next();
            boolean vencido = false;

            if (p instanceof ProdutoPerecivel perecivel) {
                vencido = perecivel.getDataDeValidade().isBefore(LocalDate.now());
            }

            if (p.getQuantidade() == 0 || vencido) {
                iterator.remove();
                Produto.liberarCodigo(p.getCodigo());
            }
        }
    }

}
