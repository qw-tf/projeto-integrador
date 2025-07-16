package Interfaces;

import java.sql.SQLException;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import Backend.Estoque;
import Backend.Produto;
import Backend.ProdutoPerecivel;
import Backend.ValidacaoException;
import Backend.Verificador;
import Banco.ProdutoDAO;

public class MenuEstoq extends JPanel {

    private final SistemaPrincipal framePai;
    private JTable tabelaProdutos;
    private JDialog popUpListar;

    public MenuEstoq(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

        jLAdicionar.setFont(fonteLabel);
        jLListar.setFont(fonteLabel);
        jLExcluir.setFont(fonteLabel);

        SistemaPrincipal.estilizarBotaoMaior(jBVoltar);
        SistemaPrincipal.estilizarBotaoMenor(jBPopUpAdicionar);
        SistemaPrincipal.estilizarBotaoMenor(jBPopUpListar);
        SistemaPrincipal.estilizarBotaoMenor(jBpopUpExcluir);

        jBPopUpAdicionar.addActionListener(e -> abrirAdicionarProduto());
        jBPopUpListar.addActionListener(e -> abrirListarProdutos());
        jBpopUpExcluir.addActionListener(e -> abrirExcluirProduto());
    }

    private void initComponents() {
        jLAdicionar = new JLabel("ADICIONAR PRODUTO");
        jLListar = new JLabel("LISTAR PRODUTOS");
        jLExcluir = new JLabel("EXCLUIR PRODUTO");
        jLTituloEstoq = new JLabel("MENU DE ESTOQUE");

        jBPopUpAdicionar = new JButton();
        jBPopUpListar = new JButton();
        jBpopUpExcluir = new JButton();
        jBVoltar = new JButton("VOLTAR");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        jLTituloEstoq.setFont(new Font("Segoe UI", Font.BOLD, 32));
        add(jLTituloEstoq, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(jLAdicionar, gbc);
        gbc.gridx = 1;
        jBPopUpAdicionar.setText(" ");
        add(jBPopUpAdicionar, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLListar, gbc);
        gbc.gridx = 1;
        jBPopUpListar.setText(" ");
        add(jBPopUpListar, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLExcluir, gbc);
        gbc.gridx = 1;
        jBpopUpExcluir.setText(" ");
        add(jBpopUpExcluir, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(40, 30, 10, 10);
        add(jBVoltar, gbc);

        jBVoltar.addActionListener(e -> framePai.trocarTela(new MenuAcess(framePai)));
    }

    // Dentro da classe MenuEstoq
    private void abrirAdicionarProduto() {
        JPanel panelzao = new JPanel(new GridBagLayout());
        panelzao.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        JLabel labelNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField(20);
        JLabel labelValorCompra = new JLabel("Valor Compra:");
        JTextField campoValorCompra = new JTextField(10);
        JLabel labelValorVenda = new JLabel("Valor Venda:");
        JTextField campoValorVenda = new JTextField(10);
        JLabel labelQuantidade = new JLabel("Quantidade:");
        JTextField campoQuantidade = new JTextField(10);
        JCheckBox checkPerecivel = new JCheckBox("Produto Perecível");
        JLabel labelValidade = new JLabel("Validade (se perecível):");
        JTextField campoValidade = new JTextField(10);

        // Inicialmente invisível validade
        labelValidade.setVisible(false);
        campoValidade.setVisible(false);

        // Adiciona componentes no painel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelzao.add(labelNome, gbc);
        gbc.gridx = 1;
        panelzao.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelzao.add(labelValorCompra, gbc);
        gbc.gridx = 1;
        panelzao.add(campoValorCompra, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelzao.add(labelValorVenda, gbc);
        gbc.gridx = 1;
        panelzao.add(campoValorVenda, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelzao.add(labelQuantidade, gbc);
        gbc.gridx = 1;
        panelzao.add(campoQuantidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panelzao.add(checkPerecivel, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panelzao.add(labelValidade, gbc);
        gbc.gridx = 1;
        panelzao.add(campoValidade, gbc);

        // Botões
        JButton confirmar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(confirmar);
        SistemaPrincipal.estilizarBotaoMaior(cancelar);

        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelzao.add(cancelar, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelzao.add(confirmar, gbc);

        Dimension botaoTamanho = new Dimension(120, 30);
        confirmar.setPreferredSize(botaoTamanho);
        cancelar.setPreferredSize(botaoTamanho);

        // Mostrar ou esconder validade
        checkPerecivel.addActionListener(e -> {
            boolean selecionado = checkPerecivel.isSelected();
            labelValidade.setVisible(selecionado);
            campoValidade.setVisible(selecionado);
            panelzao.revalidate();
            panelzao.repaint();
        });

        JDialog popUp = framePai.criarPopUp("ADICIONAR PRODUTO", panelzao, 450, 300);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        confirmar.addActionListener(e -> {
            try {
                String nome = campoNome.getText().trim();
                Verificador.verificarNome(nome);
                int qtd = Integer.parseInt(campoQuantidade.getText().trim());
                Verificador.verificarQuantidade(qtd);

                String valorCompraStr = campoValorCompra.getText().trim().replace(',', '.');
                double valorCompra = Double.parseDouble(valorCompraStr);
                Verificador.verificarPreco(valorCompra);

                String valorVendaStr = campoValorVenda.getText().trim().replace(',', '.');
                double valorVenda = Double.parseDouble(valorVendaStr);
                Verificador.verificarPreco(valorVenda);

                if (checkPerecivel.isSelected()) {
                    String validadeStr = campoValidade.getText().trim();
                    LocalDate dataValidade = LocalDate.parse(validadeStr, formatter);
                    Verificador.verificarDataValidade(dataValidade);

                    ProdutoPerecivel novoProduto = new ProdutoPerecivel(nome, qtd, valorCompra, valorVenda,
                            dataValidade);
                    ProdutoDAO.inserirProduto(novoProduto);
                    Estoque.getProdutos().add(novoProduto);
                } else {
                    Produto novoProduto = new Produto(nome, qtd, valorCompra, valorVenda);
                    ProdutoDAO.inserirProduto(novoProduto);
                    Estoque.getProdutos().add(novoProduto);
                }

                JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!", "SUCESSO",
                        JOptionPane.INFORMATION_MESSAGE);
                popUp.dispose();

            } catch (ValidacaoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valores inválidos!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Erro no Banco de dados!", "Erro", JOptionPane.ERROR_MESSAGE);
            }catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro inesperado: " + ex.getMessage(), "ERRO GRAVE",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelar.addActionListener(e -> popUp.dispose());
        popUp.setVisible(true);
    }

    private Object[][] montarDadosTabela() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Produto> produtos = Estoque.getProdutos();

        Object[][] dados = new Object[produtos.size()][7];

        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            dados[i][0] = p.getCodigo();
            dados[i][1] = p.getNome();
            dados[i][2] = String.format("R$ %.2f", p.getValorCompra());
            dados[i][3] = String.format("R$ %.2f", p.getValorVenda());
            dados[i][4] = p.getQuantidade();

            if (p instanceof ProdutoPerecivel perecivel) {
                dados[i][5] = "Sim";
                dados[i][6] = perecivel.getDataDeValidade().format(formatter);
            } else {
                dados[i][5] = "Não";
                dados[i][6] = "-";
            }
        }
        return dados;
    }

    private void abrirListarProdutos() {
        Estoque.verificarEExcluirZeradosOuVencidos(); // Limpa produtos zerados ou vencidos antes de listar

        String[] colunas = {"ID", "NOME", "VALOR COMPRA", "VALOR VENDA", "QUANTIDADE", "PERECÍVEL", "VALIDADE"};
        Object[][] dados = montarDadosTabela();

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaProdutos = new JTable(modelo);
        // restante do método permanece igual...

        tabelaProdutos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaProdutos.setRowHeight(22);
        tabelaProdutos.setGridColor(new Color(120, 120, 120));
        tabelaProdutos.setShowGrid(true);
        tabelaProdutos.setAutoCreateRowSorter(true);

        JTableHeader header = tabelaProdutos.getTableHeader();
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 32));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel label = new JLabel(value.toString(), JLabel.CENTER);
                label.setOpaque(true);
                label.setBackground(Color.DARK_GRAY);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.getViewport().setBackground(new Color(156, 156, 156));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(156, 156, 156));
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        if (popUpListar != null && popUpListar.isVisible()) {
            popUpListar.toFront();
        } else {
            popUpListar = framePai.criarPopUp("LISTA PRODUTOS", painelPrincipal, 900, 450);
            popUpListar.setVisible(true);
        }
    }

    private void atualizarListaProdutos() {
        if (tabelaProdutos != null) {
            Object[][] dadosAtualizados = montarDadosTabela();
            String[] colunas = {"ID", "NOME", "PREÇO", "QUANTIDADE", "PERECÍVEL", "VALIDADE"};

            DefaultTableModel modelo = new DefaultTableModel(dadosAtualizados, colunas);
            tabelaProdutos.setModel(modelo);
            tabelaProdutos.setRowSorter(new TableRowSorter<>(modelo)); // 🔄 Ordenação reativada está
        }
    }

    private void abrirExcluirProduto() {
        JPanel panelzao = new JPanel(new GridBagLayout());
        panelzao.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelBusca = new JLabel("Nome ou ID do Produto:");
        JTextField campoBusca = new JTextField(15);
        JLabel labelNomeProduto = new JLabel("Nome: ");
        labelNomeProduto.setForeground(Color.black);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelzao.add(labelBusca, gbc);

        gbc.gridx = 1;
        panelzao.add(campoBusca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelzao.add(labelNomeProduto, gbc);

        JButton confirmar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(confirmar);
        SistemaPrincipal.estilizarBotaoMaior(cancelar);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelzao.add(cancelar, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelzao.add(confirmar, gbc);

        Dimension botaoTamanho = new Dimension(120, 30);
        confirmar.setPreferredSize(botaoTamanho);
        cancelar.setPreferredSize(botaoTamanho);

        JDialog popUpExcluir = framePai.criarPopUp("EXCLUIR PRODUTO", panelzao, 500, 220);

        final Produto[] produtoSelecionado = new Produto[1];

        // Reutilizável: lógica de buscar e selecionar produto
        Runnable buscarProduto = () -> {
            String entrada = campoBusca.getText().trim().toLowerCase();
            if (entrada.isEmpty()) {
                return;
            }

            try {
                int id = Integer.parseInt(entrada);
                Produto p = Estoque.buscarProduto(id);
                if (p != null) {
                    produtoSelecionado[0] = p;
                    labelNomeProduto.setText("Nome: " + p.getNome() + " (ID: " + p.getCodigo() + ")");
                } else {
                    JOptionPane.showMessageDialog(popUpExcluir, "Produto com ID não encontrado.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ignored) {
                List<Produto> correspondentes = Estoque.getProdutos().stream()
                        .filter(p -> p.getNome().toLowerCase().contains(entrada))
                        .toList();

                if (correspondentes.isEmpty()) {
                    JOptionPane.showMessageDialog(popUpExcluir, "Nenhum produto encontrado com esse nome.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                } else if (correspondentes.size() == 1) {
                    Produto p = correspondentes.get(0);
                    produtoSelecionado[0] = p;
                    labelNomeProduto.setText("Nome: " + p.getNome() + " (ID: " + p.getCodigo() + ")");
                } else {
                    String[] opcoes = correspondentes.stream()
                            .map(p -> p.getNome() + " (ID: " + p.getCodigo() + ")")
                            .toArray(String[]::new);

                    String escolha = (String) JOptionPane.showInputDialog(popUpExcluir,
                            "Vários produtos encontrados, escolha um:", "Selecionar Produto",
                            JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

                    if (escolha != null) {
                        int idEscolhido = Integer.parseInt(escolha.replaceAll(".*ID: (\\d+).*", "$1"));
                        Produto escolhido = Estoque.buscarProduto(idEscolhido);
                        if (escolhido != null) {
                            produtoSelecionado[0] = escolhido;
                            labelNomeProduto
                                    .setText("Nome: " + escolhido.getNome() + " (ID: " + escolhido.getCodigo() + ")");
                        }
                    }
                }
            }
        };

        campoBusca.addActionListener(e -> buscarProduto.run());

        confirmar.addActionListener(e -> {
            buscarProduto.run(); // Busca primeiro
            Produto produto = produtoSelecionado[0];
            if (produto == null) {
                JOptionPane.showMessageDialog(popUpExcluir, "Nenhum produto selecionado.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(popUpExcluir,
                    "Deseja realmente excluir o produto:\n"
                    + produto.getNome() + " (ID: " + produto.getCodigo() + ")?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);
            try{
            if (confirm == JOptionPane.YES_OPTION) {
                boolean excluiu = Estoque.excluirProduto(produto.getCodigo());
                if (excluiu) {
                    JOptionPane.showMessageDialog(popUpExcluir,
                            "Produto removido com sucesso!\nNome: " + produto.getNome(),
                            "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    ProdutoDAO.deletar(produto.getCodigo());
                    popUpExcluir.dispose();
                    atualizarListaProdutos();
                } else {
                    JOptionPane.showMessageDialog(popUpExcluir,
                            "Erro ao excluir o produto.", "ERRO", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro no Banco de Dados", JOptionPane.ERROR_MESSAGE);
        } 
        });

        cancelar.addActionListener(e -> popUpExcluir.dispose());
        popUpExcluir.setVisible(true);
    }

    private JButton jBVoltar;
    private JButton jBPopUpAdicionar;
    private JButton jBPopUpListar;
    private JButton jBpopUpExcluir;
    private JLabel jLAdicionar;
    private JLabel jLListar;
    private JLabel jLExcluir;
    private JLabel jLTituloEstoq;
}
