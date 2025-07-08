package Interfaces;

import Backend.Venda;
import Backend.Estoque;
import Backend.ItemVenda;
import Backend.Produto;
import Backend.RegistroVendas;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

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
        gbc.gridwidth = 1;

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
    
        JButton btnAdicionarLinha = new JButton("+ Produto");
        estilizarBotaoPequeno(btnAdicionarLinha);
    
        JButton btnRemoverLinha = new JButton("- Produto");
        estilizarBotaoPequeno(btnRemoverLinha);
    
        JButton confirmar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(confirmar);
        SistemaPrincipal.estilizarBotaoMaior(cancelar);
    
        // Painel para agrupar botões +/–
        JPanel painelAddRemove = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelAddRemove.setBackground(new Color(156, 156, 156));
        painelAddRemove.add(btnAdicionarLinha);
        painelAddRemove.add(btnRemoverLinha);
    
        // Painel para Confirmar/Cancelar
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.setBackground(new Color(156, 156, 156));
        painelBotoes.add(cancelar);
        painelBotoes.add(confirmar);
    
        // Método pra criar nova linha de venda
        Runnable adicionarLinha = () -> {
            JPanel linha = new JPanel(new GridBagLayout());
            linha.setBackground(new Color(156, 156, 156));
            GridBagConstraints gbcLinha = new GridBagConstraints();
            gbcLinha.insets = new Insets(5, 5, 5, 5);
            gbcLinha.fill = GridBagConstraints.HORIZONTAL;
    
            JLabel labelID = new JLabel("ID Produto:");
            JTextField campoID = new JTextField(7);
    
            JLabel labelQtd = new JLabel("Qtd Venda:");
            JTextField campoQtd = new JTextField(5);
    
            gbcLinha.gridx = 0;
            linha.add(labelID, gbcLinha);
            gbcLinha.gridx = 1;
            linha.add(campoID, gbcLinha);
    
            gbcLinha.gridx = 2;
            linha.add(labelQtd, gbcLinha);
            gbcLinha.gridx = 3;
            linha.add(campoQtd, gbcLinha);
    
            linha.putClientProperty("campoID", campoID);
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
                JOptionPane.showMessageDialog(null, "Não pode ficar sem produtos na venda, marujo!", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
    
        confirmar.addActionListener(e -> {
            try {
                for (JPanel linha : linhasProdutos) {
                    JTextField campoID = (JTextField) linha.getClientProperty("campoID");
                    JTextField campoQtd = (JTextField) linha.getClientProperty("campoQtd");
    
                    int idProduto = Integer.parseInt(campoID.getText().trim());
                    int qtdVenda = Integer.parseInt(campoQtd.getText().trim());
    
                    boolean sucesso = Estoque.removerProdutoPorId(idProduto, qtdVenda);
                    if (!sucesso) {
                        JOptionPane.showMessageDialog(null,
                                "Erro ao registrar venda: Produto não encontrado ou quantidade insuficiente!",
                                "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
    
                JOptionPane.showMessageDialog(null, "Venda registrada com sucesso, marujo!", "SUCESSO",
                        JOptionPane.INFORMATION_MESSAGE);
    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    
        cancelar.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(panelzao).dispose();
        });
    
        adicionarLinha.run(); // Cria a primeira linha
    
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
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panelzao.add(painelAddRemove, gbc);
    
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelzao.add(painelBotoes, gbc);
    
        JDialog popUp = framePai.criarPopUp("REGISTRAR VENDA", panelzao, 500, 400);
        popUp.setVisible(true);
    }
    
    
   private void abrirListarVendas() {
    String[] colunas = { "ID", "Produtos", "Data", "Total" };
    List<Venda> vendas = RegistroVendas.getTodasVendas();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    Object[][] dados = new Object[vendas.size()][4];

    for (int i = 0; i < vendas.size(); i++) {
        Venda v = vendas.get(i);
        dados[i][0] = v.getId();
        dados[i][1] = v.getResumoProdutos();
        dados[i][2] = v.getData().format(formatter);
        dados[i][3] = String.format("R$ %.2f", v.getTotal());
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
    tabelaVendas.getColumnModel().getColumn(1).setPreferredWidth(300); // Produtos

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

    JScrollPane scroll = new JScrollPane(tabelaVendas);
    scroll.getViewport().setBackground(new Color(156, 156, 156));
    scroll.setBorder(BorderFactory.createEmptyBorder());

    JPanel painel = new JPanel(new BorderLayout());
    painel.setBackground(new Color(156, 156, 156));
    painel.add(scroll, BorderLayout.CENTER);

    // Força fechamento do popup antigo, se existir
    if (popUpListar != null) {
        popUpListar.dispose();
        popUpListar = null;
    }

    popUpListar = framePai.criarPopUp("LISTAR VENDAS", painel, 800, 450);
    popUpListar.setVisible(true);
}





    private void abrirExcluirVenda() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel labelId = new JLabel("ID da Venda:");
        JTextField campoId = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(labelId, gbc);
        gbc.gridx = 1;
        painel.add(campoId, gbc);

        JButton confirmar = new JButton("Excluir");
        JButton cancelar = new JButton("Cancelar");
        framePai.estilizarBotaoMaior(confirmar);
        framePai.estilizarBotaoMaior(cancelar);

        gbc.gridy = 1;
        gbc.gridx = 0;
        painel.add(cancelar, gbc);
        gbc.gridx = 1;
        gbc.insets = new Insets(5, 40, 5, 0);
        painel.add(confirmar, gbc);

        JDialog popUp = framePai.criarPopUp("EXCLUIR VENDA", painel, 400, 180);
        confirmar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText().trim());
                boolean ok = RegistroVendas.excluirVenda(id);
                if (ok) {
                    JOptionPane.showMessageDialog(popUp, "Venda excluída com sucesso!", "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    popUp.dispose();
                } else {
                    JOptionPane.showMessageDialog(popUp, "Venda não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(popUp, "ID inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelar.addActionListener(e -> popUp.dispose());
        popUp.setVisible(true);
    }

    public static void estilizarBotaoPequeno(javax.swing.JButton botao) {
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14)); // MENOR que 18!
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
