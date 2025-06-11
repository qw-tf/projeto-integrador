import java.util.ArrayList;
import java.util.List;

public class Estoque {

    private List<Produto> produtos = new ArrayList<>();
    Verificador verificador = new Verificador();

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void adicionarProduto(String nome, int quantidade, double preco, boolean perecivel, String dataValidade) throws ValidacaoException {
        verificador.verificarNome(nome);
        verificador.verificarQuantidade(quantidade);
        verificador.verificarPreco(preco);

        Produto produto;
        if (perecivel) {
            verificador.verificarDataValidade(dataValidade);
            produto = new ProdutoPerecivel(nome, quantidade, preco, dataValidade);
        } else {
            produto = new Produto(nome, quantidade, preco);
        }

        produtos.add(produto);
        GerarLogs.logAutomatic("Produto cadastrado com sucesso!");
    }

    public boolean excluirProduto(int codigo) throws ValidacaoException {
        verificador.verificarCodigo(codigo);
        Produto produto = buscarProduto(codigo);
        
        if (produto != null) {
            produtos.remove(produto);
            GerarLogs.logAutomatic("Produto removido: " + produto.getNome());
            return true;
        }

        GerarLogs.logAutomatic("Tentativa de exclusão falhou. Produto não encontrado.");
        return false;
    }

    public void inserirLista(Produto produto) {
        produtos.add(produto);
    }

    public Produto buscarProduto(int codigo) throws ValidacaoException {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }

    public void removerProduto(int codigo, int quantidade) throws ValidacaoException {
        Produto produto = buscarProduto(codigo);
        if (produto != null) {
            produto.setQuantidade(produto.getQuantidade() - quantidade);
            System.out.println("Produto " + produto.getNome() + " atualizado no estoque. Nova quantidade: " + produto.getQuantidade());
        }
    }
}
