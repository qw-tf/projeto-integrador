package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import Banco.ConexaoPostgres;
import Banco.ProdutoDAO;

public class RegistroProdutos {

    private static List<Produto> produtos = new ArrayList<>();

    public static void adicionarProduto(String nome, int quantidade, double valorCompra, double valorVenda,
            boolean perecivel, LocalDate validade) throws ValidacaoException {

        Verificador.verificarNome(nome);
        Verificador.verificarQuantidade(quantidade);
        Verificador.verificarPreco(valorCompra);
        Verificador.verificarPreco(valorVenda);

        Produto novoProduto;
        if (perecivel) {
            if (validade == null) {
                throw new ValidacaoException("Data de validade obrigatória para produto perecível.");
            }
            Verificador.verificarDataValidade(validade);
            novoProduto = new ProdutoPerecivel(nome, quantidade, valorCompra, valorVenda, validade);
        } else {
            novoProduto = new Produto(nome, quantidade, valorCompra, valorVenda);
        }

        produtos.add(novoProduto);
    }

    public static boolean removerProdutoPorId(int codigo, int quantidade) throws ValidacaoException, SQLException {
        Produto produto = buscarProduto(codigo);
        if (produto != null) {
            Verificador.verificarQuantidade(quantidade);
            if (produto.getQuantidade() < quantidade) {
                throw new ValidacaoException("Quantidade em estoque insuficiente!");
            }

            produto.setQuantidade(produto.getQuantidade() - quantidade);
            ProdutoDAO.atualizar(codigo, produto.getQuantidade());

            if (produto.getQuantidade() == 0) {
                produtos.remove(produto);
                Produto.liberarCodigo(produto.getCodigo());
                ProdutoDAO.deletar(codigo);
            }
            System.out.println("deu sim, caralho");
            return true;
        }
        System.out.println("não deu, porra");
        return false;
    }

    public static boolean excluirProduto(int codigo) throws SQLException {
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

    public static List<Produto> getProdutos() {
        return produtos;
    }

    public static void setProdutos(List<Produto> lista) {
        produtos = lista;
    }

    public static void carregarDoBanco() throws SQLException {
        List<Produto> lista = ProdutoDAO.listarTodos(); 
        lista.sort(Comparator.comparingInt(Produto::getCodigo));
        produtos = lista;

        Set<Integer> codigosUsados = lista.stream()
                .map(Produto::getCodigo)
                .collect(Collectors.toSet());

        int maiorCodigo = codigosUsados.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);

        List<Integer> codigosDisponiveis = new LinkedList<>();
        for (int i = 1; i < maiorCodigo; i++) {
            if (!codigosUsados.contains(i)) {
                codigosDisponiveis.add(i);
            }
        }

        Produto.setProximoCodigo(maiorCodigo + 1);
        Produto.setCodigosDisponiveis(codigosDisponiveis);
    }
}
