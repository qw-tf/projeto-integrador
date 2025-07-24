package Interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import Backend.RegistroProdutos;
import Backend.ItemVenda;
import Backend.Produto;
import Backend.RegistroVendas;
import Backend.Venda;

public class MenuVendas extends JPanel {

    private final SistemaPrincipal framePai;
    private JTable tabelaVendas;
    private JDialog popUpListar;

    public MenuVendas(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

        jLAdicionarVendas.setFont(fonteLabel);
        jLListarVendas.setFont(fonteLabel);
        jLExcluirVendas.setFont(fonteLabel);

        framePai.estilizarBotaoMaior(jBVoltar);
        framePai.estilizarBotaoMenor(jBPopUpVendasAdicionar);
        framePai.estilizarBotaoMenor(jBPopUpVendasListar);
        framePai.estilizarBotaoMenor(jBPopUpVendasExcluir);

        jBPopUpVendasAdicionar.addActionListener(e -> abrirAdicionarVenda());
        jBPopUpVendasListar.addActionListener(e -> abrirListarVendas());
        jBPopUpVendasExcluir.addActionListener(e -> abrirExcluirVenda());
    }

    private void initComponents() {
        jLAdicionarVendas = new JLabel("ADICIONAR VENDAS");
        jLListarVendas = new JLabel("LISTAR VENDAS");
        jLExcluirVendas = new JLabel("EXCLUIR VENDAS");
        jLTituloVendas = new JLabel("MENU DE VENDAS");

        jBPopUpVendasAdicionar = new JButton(" ");
        jBPopUpVendasListar = new JButton(" ");
        jBPopUpVendasExcluir = new JButton(" ");
        jBVoltar = new JButton("VOLTAR");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0;
        gbc.gridx = 0;
        jLTituloVendas.setFont(new Font("Segoe UI", Font.BOLD, 32));
        add(jLTituloVendas, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(jLAdicionarVendas, gbc);
        gbc.gridx = 1;
        add(jBPopUpVendasAdicionar, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLListarVendas, gbc);
        gbc.gridx = 1;
        add(jBPopUpVendasListar, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLExcluirVendas, gbc);
        gbc.gridx = 1;
        add(jBPopUpVendasExcluir, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(40, 30, 10, 10);
        add(jBVoltar, gbc);

        jBVoltar.addActionListener(e -> framePai.trocarTela(new MenuAcess(framePai)));
    }

    private void abrirAdicionarVenda() {
        JPanel panelzao = new JPanel(new GridBagLayout());
        panelzao.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        List<JPanel> linhasProdutos = new ArrayList<>();
        JPanel painelLinhas = new JPanel();
        painelLinhas.setLayout(new BoxLayout(painelLinhas, BoxLayout.Y_AXIS));
        painelLinhas.setBackground(new Color(156, 156, 156));

        JButton btnRemoverLinha = new JButton("- Produto");
        estilizarBotaoPequeno(btnRemoverLinha);

        JButton btnAdicionarLinha = new JButton("+ Produto");
        estilizarBotaoPequeno(btnAdicionarLinha);

        JButton confirmar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(confirmar);
        SistemaPrincipal.estilizarBotaoMaior(cancelar);

        JComboBox<String> comboPagamento = new JComboBox<>(new String[] {
                "Dinheiro", "Cartão Débito", "Cartão Crédito", "Pix", "Boleto"
        });
        comboPagamento.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel painelPagamento = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelPagamento.setBackground(new Color(156, 156, 156));
        painelPagamento.add(new JLabel("Forma de Pagamento:"));
        painelPagamento.add(comboPagamento);

        JPanel painelAddRemove = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelAddRemove.setBackground(new Color(156, 156, 156));
        painelAddRemove.add(btnRemoverLinha);
        painelAddRemove.add(btnAdicionarLinha);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBackground(new Color(156, 156, 156));
        painelBotoes.add(cancelar);
        painelBotoes.add(confirmar);

        Runnable adicionarLinha = () -> {
            JPanel linha = new JPanel(new GridBagLayout());
            linha.setBackground(new Color(156, 156, 156));
            GridBagConstraints gbcLinha = new GridBagConstraints();
            gbcLinha.insets = new Insets(5, 5, 5, 5);
            gbcLinha.fill = GridBagConstraints.HORIZONTAL;

            JLabel labelNome = new JLabel("Nome ou ID:");
            JTextField campoNome = new JTextField(15);

            JLabel labelQtd = new JLabel("Qtd Venda:");
            JTextField campoQtd = new JTextField(5);

            gbcLinha.gridx = 0;
            linha.add(labelNome, gbcLinha);
            gbcLinha.gridx = 1;
            linha.add(campoNome, gbcLinha);

            gbcLinha.gridx = 2;
            linha.add(labelQtd, gbcLinha);
            gbcLinha.gridx = 3;
            linha.add(campoQtd, gbcLinha);

            campoNome.addActionListener(e -> {
                String entrada = campoNome.getText().trim();
                List<Produto> encontrados = new ArrayList<>();
                try {
                    int id = Integer.parseInt(entrada);
                    for (Produto p : RegistroProdutos.getProdutos()) {
                        if (p.getCodigo() == id) {
                            encontrados.add(p);
                            break;
                        }
                    }
                } catch (NumberFormatException ex) {
                    String nomeBuscado = entrada.toLowerCase();
                    for (Produto p : RegistroProdutos.getProdutos()) {
                        if (p.getNome().toLowerCase().contains(nomeBuscado)) {
                            encontrados.add(p);
                        }
                    }
                }

                if (encontrados.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Produto selecionado = (Produto) JOptionPane.showInputDialog(
                        null,
                        "Selecione o produto:",
                        "Escolher Produto",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        encontrados.toArray(),
                        encontrados.get(0));
                if (selecionado != null) {
                    campoNome.setText(selecionado.getNome());
                    linha.putClientProperty("produtoSelecionado", selecionado);
                }
            });

            linha.putClientProperty("campoNome", campoNome);
            linha.putClientProperty("campoQtd", campoQtd);

            linhasProdutos.add(linha);
            painelLinhas.add(linha);
            painelLinhas.revalidate();
            painelLinhas.repaint();
        };

        btnAdicionarLinha.addActionListener(e -> adicionarLinha.run());

        btnRemoverLinha.addActionListener(e -> {
            if (linhasProdutos.size() > 1) {
                JPanel ultima = linhasProdutos.remove(linhasProdutos.size() - 1);
                painelLinhas.remove(ultima);
                painelLinhas.revalidate();
                painelLinhas.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Não pode ficar sem produtos na venda!", "AVISO",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        confirmar.addActionListener(e -> {
            try {
                List<ItemVenda> itensVenda = new ArrayList<>();

                for (JPanel linha : linhasProdutos) {
                    JTextField campoQtd = (JTextField) linha.getClientProperty("campoQtd");
                    Produto produto = (Produto) linha.getClientProperty("produtoSelecionado");

                    if (produto == null) {
                        JOptionPane.showMessageDialog(null, "Você deve selecionar um produto!", "ERRO",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int qtdVenda;
                    try {
                        qtdVenda = Integer.parseInt(campoQtd.getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Quantidade inválida para: " + produto.getNome(),
                                "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (qtdVenda <= 0) {
                        JOptionPane.showMessageDialog(null,
                                "Quantidade deve ser maior que zero para: " + produto.getNome(),
                                "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (produto.getQuantidade() < qtdVenda) {
                        JOptionPane.showMessageDialog(null,
                                "Estoque insuficiente para: " + produto.getNome(),
                                "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    itensVenda.add(new ItemVenda(produto, qtdVenda));
                }

                String formaPagamento = (String) comboPagamento.getSelectedItem();

                RegistroVendas.adicionarVenda(itensVenda, formaPagamento);

                JOptionPane.showMessageDialog(null, "Venda registrada com sucesso!", "SUCESSO",
                        JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(panelzao).dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelar.addActionListener(e -> SwingUtilities.getWindowAncestor(panelzao).dispose());

        adicionarLinha.run();

        JScrollPane scrollPaneLinhas = new JScrollPane(painelLinhas);
        scrollPaneLinhas.setPreferredSize(new Dimension(400, 200));
        scrollPaneLinhas.setBorder(null);
        scrollPaneLinhas.getVerticalScrollBar().setUnitIncrement(16);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panelzao.add(scrollPaneLinhas, gbc);

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        panelzao.add(painelPagamento, gbc);

        gbc.gridy++;
        panelzao.add(painelAddRemove, gbc);

        gbc.gridy++;
        panelzao.add(painelBotoes, gbc);

        JDialog popUp = framePai.criarPopUp("REGISTRAR VENDA", panelzao, 500, 500);
        popUp.setVisible(true);
    }

    private void abrirExcluirVenda() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelId = new JLabel("ID da Venda:");
        labelId.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JTextField campoId = new JTextField(15);
        campoId.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnConsultar = new JButton("Consultar Itens");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnCancelar = new JButton("Cancelar");

        framePai.estilizarBotaoMaior(btnConsultar);
        framePai.estilizarBotaoMaior(btnExcluir);
        framePai.estilizarBotaoMaior(btnCancelar);

        JTextArea areaResumo = new JTextArea(8, 30);
        areaResumo.setEditable(false);
        areaResumo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaResumo.setLineWrap(true);
        areaResumo.setWrapStyleWord(true);
        JScrollPane scrollResumo = new JScrollPane(areaResumo);
        scrollResumo.setBorder(BorderFactory.createTitledBorder("Resumo da Venda"));

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        painel.add(labelId, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        painel.add(campoId, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelBotoes.setBackground(new Color(156, 156, 156));
        painelBotoes.add(btnConsultar);
        painelBotoes.add(btnExcluir);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        painel.add(painelBotoes, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        painel.add(scrollResumo, gbc);

        JPanel painelCancelar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelCancelar.setBackground(new Color(156, 156, 156));
        painelCancelar.add(btnCancelar);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        painel.add(painelCancelar, gbc);

        JDialog popUp = framePai.criarPopUp("EXCLUIR VENDA", painel, 500, 500);

        final Venda[] vendaSelecionada = new Venda[1];

        btnConsultar.addActionListener(e -> {
            String textoId = campoId.getText().trim();
            if (textoId.isEmpty()) {
                JOptionPane.showMessageDialog(popUp, "Informe o ID da venda.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(textoId);
                Venda v = RegistroVendas.getTodasVendas().stream()
                        .filter(ve -> ve.getId() == id)
                        .findFirst()
                        .orElse(null);
                if (v == null) {
                    areaResumo.setText("");
                    vendaSelecionada[0] = null;
                    JOptionPane.showMessageDialog(popUp, "Venda não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    vendaSelecionada[0] = v;

                    // 🍡 Usa a descrição simples ao invés de tentar listar os itens reais
                    StringBuilder sb = new StringBuilder();
                    sb.append("Venda ID ").append(id).append(":\n\n");
                    sb.append("Descrição: ").append(v.getDescricao()).append("\n");
                    sb.append("Data: ").append(v.getData()).append("\n");
                    sb.append("Forma de Pagamento: ").append(v.getFormaPagamento()).append("\n");
                    sb.append(String.format("Total: R$ %.2f\n", v.getTotal()));
                    sb.append(String.format("Ganho Bruto: R$ %.2f\n", v.getLucroTotal()));

                    areaResumo.setText(sb.toString());
                }
            } catch (NumberFormatException ex) {
                areaResumo.setText("");
                vendaSelecionada[0] = null;
                JOptionPane.showMessageDialog(popUp, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            if (vendaSelecionada[0] == null) {
                JOptionPane.showMessageDialog(popUp, "Consulte uma venda válida antes de excluir.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            int resposta = JOptionPane.showOptionDialog(popUp,
                    "Tem certeza que deseja excluir esta venda?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[] { "Sim", "Não" },
                    "Não");
            if (resposta == JOptionPane.YES_OPTION) {
                boolean removido = RegistroVendas.removerVenda(vendaSelecionada[0]);
                if (removido) {
                    JOptionPane.showMessageDialog(popUp, "Venda excluída com sucesso!", "SUCESSO",
                            JOptionPane.INFORMATION_MESSAGE);
                    popUp.dispose();
                } else {
                    JOptionPane.showMessageDialog(popUp, "Falha ao excluir a venda.", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> popUp.dispose());

        popUp.setVisible(true);
    }

    private void abrirListarVendas() {
        String[] colunas = { "ID", "Produtos", "Data", "Total", "Pagamento", "Ganho Bruto" };
        List<Venda> vendas = RegistroVendas.getTodasVendas();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Object[][] dados = new Object[vendas.size()][6];

        for (int i = 0; i < vendas.size(); i++) {
            Venda v = vendas.get(i);
            dados[i][0] = v.getId();
            dados[i][1] = v.getDescricao(); // agora isso tá vindo do banco direitinho!
            dados[i][2] = v.getData().format(formatter);
            dados[i][3] = String.format("R$ %.2f", v.getTotal());
            dados[i][4] = v.getFormaPagamento();
            dados[i][5] = String.format("R$ %.2f", v.getLucroTotal()); // sem recalcular, yayyy!
        }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabelaVendas = new JTable(modelo);
        tabelaVendas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaVendas.setRowHeight(22);
        tabelaVendas.setGridColor(Color.BLACK);
        tabelaVendas.setShowGrid(true);
        tabelaVendas.setAutoCreateRowSorter(true);
        tabelaVendas.getColumnModel().getColumn(1).setPreferredWidth(300);
        tabelaVendas.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabelaVendas.getColumnModel().getColumn(5).setPreferredWidth(100);

        JTableHeader header = tabelaVendas.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setOpaque(true);
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString(), JLabel.CENTER);
                label.setOpaque(true);
                label.setBackground(Color.BLACK);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                return label;
            }
        });

        // --- AQUI ADICIONAMOS O CAMPO DE BUSCA ---
        JTextField campoBusca = new JTextField();
        campoBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoBusca.setPreferredSize(new Dimension(200, 30));

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tabelaVendas.setRowSorter(sorter);

        campoBusca.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void filtrar() {
                String texto = campoBusca.getText().trim().toLowerCase();
                if (texto.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    // Filtra coluna 1 ("Produtos" / descrição)
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1));
                }
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrar();
            }
        });

        JScrollPane scroll = new JScrollPane(tabelaVendas);
        scroll.getViewport().setBackground(new Color(156, 156, 156));
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(new Color(156, 156, 156));

        // Painel para busca no topo
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(new Color(156, 156, 156));
        painelBusca.add(new JLabel("Buscar: "));
        painelBusca.add(campoBusca);

        painel.add(painelBusca, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);

        if (popUpListar != null) {
            popUpListar.dispose();
            popUpListar = null;
        }

        popUpListar = framePai.criarPopUp("LISTAR VENDAS", painel, 900, 450);
        popUpListar.setVisible(true);
    }

    public static void estilizarBotaoPequeno(javax.swing.JButton botao) {
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setOpaque(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private JButton jBVoltar;
    private JButton jBPopUpVendasAdicionar;
    private JButton jBPopUpVendasListar;
    private JButton jBPopUpVendasExcluir;
    private JLabel jLAdicionarVendas;
    private JLabel jLListarVendas;
    private JLabel jLExcluirVendas;
    private JLabel jLTituloVendas;
}
