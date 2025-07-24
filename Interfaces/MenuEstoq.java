package Interfaces;

import java.sql.SQLException;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import Backend.RegistroProdutos;
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

    private void abrirAdicionarProduto() {
        JPanel panelzao = new JPanel(new GridBagLayout());
        panelzao.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        JLabel labelNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField(20);

        JLabel labelQuantidade = new JLabel("Quantidade:");
        JTextField campoQuantidade = new JTextField(10);

        JLabel labelValorCompra = new JLabel("Valor Compra (R$):");
        JTextField campoValorCompra = new JTextField(10);
        JLabel labelValorCompraUnidade = new JLabel("R$ 0,00 / unidade");
        labelValorCompraUnidade.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        labelValorCompraUnidade.setForeground(new Color(100, 100, 100));

        JLabel labelValorVenda = new JLabel("Valor Venda (R$):");
        JTextField campoValorVenda = new JTextField(10);

        JCheckBox checkPerecivel = new JCheckBox("Produto Perecível");
        JLabel labelValidade = new JLabel("Validade (se perecível):");
        JTextField campoValidade = new JTextField(10);

        // Inicialmente desabilita validade sem alterar layout
        labelValidade.setEnabled(false);
        labelValidade.setForeground(new Color(156, 156, 156));
        campoValidade.setEnabled(false);
        campoValidade.setBackground(new Color(220, 220, 220));

        int y = 0;

        // Linha 0: Nome
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panelzao.add(labelNome, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelzao.add(campoNome, gbc);

        // Linha 1: Quantidade
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panelzao.add(labelQuantidade, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelzao.add(campoQuantidade, gbc);

        // Linha 2: Valor Compra e custo por unidade
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panelzao.add(labelValorCompra, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panelzao.add(campoValorCompra, gbc);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panelzao.add(labelValorCompraUnidade, gbc);
        gbc.anchor = GridBagConstraints.WEST;

        // Linha 3: Valor Venda
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panelzao.add(labelValorVenda, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelzao.add(campoValorVenda, gbc);

        // Linha 4: Checkbox Perecível
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 3;
        panelzao.add(checkPerecivel, gbc);

        // Linha 5: Validade
        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        panelzao.add(labelValidade, gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panelzao.add(campoValidade, gbc);

        // Botões
        JButton confirmar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(confirmar);
        SistemaPrincipal.estilizarBotaoMaior(cancelar);
        Dimension botaoTamanho = new Dimension(120, 30);
        confirmar.setPreferredSize(botaoTamanho);
        cancelar.setPreferredSize(botaoTamanho);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botoes.setOpaque(false);
        botoes.add(cancelar);
        botoes.add(confirmar);

        y++;
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 3;
        panelzao.add(botoes, gbc);

        // Listener para habilitar/desabilitar validade suavemente
        checkPerecivel.addActionListener(e -> {
            boolean ativo = checkPerecivel.isSelected();
            labelValidade.setEnabled(ativo);
            labelValidade.setForeground(ativo ? Color.BLACK : new Color(156, 156, 156));
            campoValidade.setEnabled(ativo);
            campoValidade.setBackground(ativo ? Color.WHITE : new Color(220, 220, 220));
            panelzao.revalidate();
            panelzao.repaint();
        });

        // DocumentListener para custo por unidade
        DocumentListener atualizaCustoUnidade = new DocumentListener() {
            private void atualizar() {
                try {
                    double valor = Double.parseDouble(campoValorCompra.getText().trim().replace(",", "."));
                    int qtd = Integer.parseInt(campoQuantidade.getText().trim());
                    if (qtd > 0) {
                        labelValorCompraUnidade.setText(String.format("R$ %.2f / unidade", valor / qtd));
                    } else {
                        labelValorCompraUnidade.setText("R$ 0,00 / unidade");
                    }
                } catch (Exception ex) {
                    labelValorCompraUnidade.setText("R$ 0,00 / unidade");
                }
            }

            public void insertUpdate(DocumentEvent e) {
                atualizar();
            }

            public void removeUpdate(DocumentEvent e) {
                atualizar();
            }

            public void changedUpdate(DocumentEvent e) {
                atualizar();
            }
        };
        campoQuantidade.getDocument().addDocumentListener(atualizaCustoUnidade);
        campoValorCompra.getDocument().addDocumentListener(atualizaCustoUnidade);

        // Dialog
        JDialog popUp = framePai.criarPopUp("ADICIONAR PRODUTO", panelzao, 450, 320);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        confirmar.addActionListener(e -> {
            try {
                String nome = campoNome.getText().trim();
                Verificador.verificarNome(nome);
                int qtd = Integer.parseInt(campoQuantidade.getText().trim());
                Verificador.verificarQuantidade(qtd);
                double valorCompra = Double.parseDouble(campoValorCompra.getText().trim().replace(',', '.'));
                Verificador.verificarPreco(valorCompra);
                double valorVenda = Double.parseDouble(campoValorVenda.getText().trim().replace(',', '.'));
                Verificador.verificarPreco(valorVenda);

                if (checkPerecivel.isSelected()) {
                    LocalDate validade = LocalDate.parse(campoValidade.getText().trim(), formatter);
                    Verificador.verificarDataValidade(validade);
                    ProdutoPerecivel p = new ProdutoPerecivel(nome, qtd, valorCompra, valorVenda, validade);
                    ProdutoDAO.inserirProduto(p);
                    RegistroProdutos.getProdutos().add(p);
                } else {
                    Produto p = new Produto(nome, qtd, valorCompra, valorVenda);
                    ProdutoDAO.inserirProduto(p);
                    RegistroProdutos.getProdutos().add(p);
                }

                JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!", "SUCESSO",
                        JOptionPane.INFORMATION_MESSAGE);
                popUp.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelar.addActionListener(e -> popUp.dispose());
        popUp.setVisible(true);
    }

    private Object[][] montarDadosTabela() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Produto> produtos = RegistroProdutos.getProdutos();

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
        RegistroProdutos.verificarEExcluirZeradosOuVencidos();

        String[] colunas = { "ID", "NOME", "VALOR COMPRA", "VALOR VENDA", "QUANTIDADE", "PERECÍVEL", "VALIDADE" };
        Object[][] dados = montarDadosTabela();

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaProdutos = new JTable(modelo);
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

        // 🔍 Campo de busca
        JTextField campoBusca = new JTextField();
        campoBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoBusca.setPreferredSize(new Dimension(200, 30));

        campoBusca.addActionListener(e -> {
            String termo = campoBusca.getText().toLowerCase().trim();
            Object[][] todosDados = montarDadosTabela();
            List<Object[]> filtrados = new ArrayList<>();

            for (Object[] linha : todosDados) {
                String nome = linha[1].toString().toLowerCase(); // Coluna do nome
                if (nome.contains(termo)) {
                    filtrados.add(linha);
                }
            }

            DefaultTableModel novoModelo = new DefaultTableModel(
                    filtrados.toArray(new Object[0][]), colunas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            tabelaProdutos.setModel(novoModelo);
            tabelaProdutos.setRowSorter(new TableRowSorter<>(novoModelo));
        });

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(new Color(156, 156, 156));
        painelBusca.add(new JLabel("Buscar por nome:"));
        painelBusca.add(campoBusca);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(156, 156, 156));
        painelPrincipal.add(painelBusca, BorderLayout.NORTH);
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
            String[] colunas = { "ID", "NOME", "PREÇO", "QUANTIDADE", "PERECÍVEL", "VALIDADE" };

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
                Produto p = RegistroProdutos.buscarProduto(id);
                if (p != null) {
                    produtoSelecionado[0] = p;
                    labelNomeProduto.setText("Nome: " + p.getNome() + " (ID: " + p.getCodigo() + ")");
                } else {
                    JOptionPane.showMessageDialog(popUpExcluir, "Produto com ID não encontrado.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ignored) {
                List<Produto> correspondentes = RegistroProdutos.getProdutos().stream()
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
                        Produto escolhido = RegistroProdutos.buscarProduto(idEscolhido);
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
            try {
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean excluiu = RegistroProdutos.excluirProduto(produto.getCodigo());
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
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro no Banco de Dados: " + ex.getMessage(),
                        JOptionPane.ERROR_MESSAGE);
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
