package Interfaces;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import Backend.Gasto;
import Backend.RepositorioGastos;
import Banco.GastosDAO;

public class MenuGastos extends JPanel {

    private final SistemaPrincipal framePai;

    private JTable tabelaGastos;
    private JDialog popUpListar;

    public MenuGastos(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

        jLListarGastosPer.setFont(fonteLabel);
        jLListarGastosEmp.setFont(fonteLabel);
        jLAdicionarGastos.setFont(fonteLabel);

        SistemaPrincipal.estilizarBotaoMaior(jBVoltar);
        SistemaPrincipal.estilizarBotaoMenor(jBPopUpListarGastosPer);
        SistemaPrincipal.estilizarBotaoMenor(jBPopUpListarGastosEmp);
        SistemaPrincipal.estilizarBotaoMenor(jBPopUpAdicionarGastos);

        jBPopUpAdicionarGastos.addActionListener(e -> abrirAdicionarGasto());
        jBPopUpListarGastosPer.addActionListener(e -> abrirListarGastos(true));
        jBPopUpListarGastosEmp.addActionListener(e -> abrirListarGastos(false));
        jBVoltar.addActionListener(e -> framePai.trocarTela(new MenuAcess(framePai)));
    }

    private void initComponents() {
        jLAdicionarGastos = new JLabel("ADICIONAR GASTO");
        jLListarGastosPer = new JLabel("LISTAR GASTOS PESSOAIS");
        jLListarGastosEmp = new JLabel("LISTAR GASTOS EMPRESARIAIS");
        jLTituloGastos = new JLabel("MENU DE GASTOS");

        jBPopUpAdicionarGastos = new JButton(" ");
        jBPopUpListarGastosPer = new JButton(" ");
        jBPopUpListarGastosEmp = new JButton(" ");
        jBVoltar = new JButton("VOLTAR");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        jLTituloGastos.setFont(new Font("Segoe UI", Font.BOLD, 32));
        add(jLTituloGastos, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(jLAdicionarGastos, gbc);
        gbc.gridx = 1;
        add(jBPopUpAdicionarGastos, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLListarGastosPer, gbc);
        gbc.gridx = 1;
        add(jBPopUpListarGastosPer, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLListarGastosEmp, gbc);
        gbc.gridx = 1;
        add(jBPopUpListarGastosEmp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(40, 30, 10, 10);
        add(jBVoltar, gbc);
    }
    
    private void abrirAdicionarGasto() {
        JPanel panelzao = new JPanel(new GridBagLayout());
        panelzao.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
    
        JLabel labelDescricao = new JLabel("Descrição:");
    
        // JTextArea normal
        JTextArea campoDescricao = new JTextArea();
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);
        campoDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    
        // JScrollPane fixo em 300x100, scrollbar só quando necessário
        JScrollPane scrollDescricao = new JScrollPane(campoDescricao,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDescricao.setPreferredSize(new Dimension(300, 100));
    
        JLabel labelValor = new JLabel("Valor:");
        JTextField campoValor = new JTextField(10);
    
        JLabel labelData = new JLabel("Data (dd/MM/yyyy):");
        JTextField campoData = new JTextField(10);
        JCheckBox checkHoje = new JCheckBox("Usar data de hoje");
        checkHoje.setBackground(new Color(156, 156, 156));
        checkHoje.addActionListener(e -> {
            if (checkHoje.isSelected()) {
                campoData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                campoData.setEditable(false);
            } else {
                campoData.setEditable(true);
                campoData.setText("");
            }
        });
    
        JLabel labelTipo = new JLabel("Tipo de Gasto:");
        JComboBox<String> comboTipo = new JComboBox<>(new String[] { "Pessoal", "Empresarial" });
    
        // Layout
        gbc.gridx = 0; gbc.gridy = 0; panelzao.add(labelDescricao, gbc);
        gbc.gridx = 1; panelzao.add(scrollDescricao, gbc);
    
        gbc.gridy = 1; gbc.gridx = 0; panelzao.add(labelValor, gbc);
        gbc.gridx = 1; panelzao.add(campoValor, gbc);
    
        gbc.gridy = 2; gbc.gridx = 0; panelzao.add(labelData, gbc);
        gbc.gridx = 1; panelzao.add(campoData, gbc);
    
        gbc.gridy = 3; gbc.gridx = 1; panelzao.add(checkHoje, gbc);
    
        gbc.gridy = 4; gbc.gridx = 0; panelzao.add(labelTipo, gbc);
        gbc.gridx = 1; panelzao.add(comboTipo, gbc);
    
        JButton confirmar = new JButton("Confirmar");
        JButton cancelar  = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(confirmar);
        SistemaPrincipal.estilizarBotaoMaior(cancelar);
    
        gbc.gridy = 5; gbc.gridx = 0; gbc.anchor = GridBagConstraints.WEST;
        panelzao.add(cancelar, gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.EAST;
        panelzao.add(confirmar, gbc);
    
        Dimension botaoTamanho = new Dimension(120, 30);
        confirmar.setPreferredSize(botaoTamanho);
        cancelar .setPreferredSize(botaoTamanho);
    
        // Cria e fixa o diálogo
        JDialog popUp = framePai.criarPopUp("ADICIONAR GASTO", panelzao, 480, 350);
        popUp.setSize(480, 350);
        popUp.setMinimumSize(new Dimension(480, 350));
        popUp.setResizable(true);
        popUp.setLocationRelativeTo(framePai);
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
        confirmar.addActionListener(e -> {
            try {
                String descricao = campoDescricao.getText().trim();
                if (descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(popUp, "Descrição é obrigatória!", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                double valor = Double.parseDouble(campoValor.getText().trim().replace(',', '.'));
                if (valor <= 0) {
                    JOptionPane.showMessageDialog(popUp, "Valor deve ser maior que zero!", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                LocalDate data = LocalDate.parse(campoData.getText().trim(), formatter);
                boolean pessoal = "Pessoal".equals(comboTipo.getSelectedItem());
                Gasto novoGasto = new Gasto(descricao, valor, data, pessoal);
    
                GastosDAO.salvarGasto(novoGasto);
                RepositorioGastos.adicionarGasto(novoGasto);
    
                JOptionPane.showMessageDialog(popUp, "Gasto adicionado com sucesso!", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                popUp.dispose();
    
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(popUp, "Valor inválido!", "ERRO", JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(popUp, "Data inválida! Use dd/MM/yyyy.", "ERRO", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(popUp, "Erro inesperado: " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    
        cancelar.addActionListener(e -> popUp.dispose());
        popUp.setVisible(true);
    }
    
    private void abrirListarGastos(boolean pessoal) {
        List<Gasto> lista = pessoal ? RepositorioGastos.listarPessoais() : RepositorioGastos.listarEmpresariais();

        String titulo = pessoal ? "LISTA DE GASTOS PESSOAIS" : "LISTA DE GASTOS EMPRESARIAIS";

        String[] colunas = { "Descrição", "Valor", "Data" };
        Object[][] dados = new Object[lista.size()][3];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < lista.size(); i++) {
            Gasto g = lista.get(i);
            dados[i][0] = g.getDescricao();
            dados[i][1] = String.format("R$ %.2f", g.getValor());
            dados[i][2] = g.getData().format(formatter);
        }

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaGastos = new JTable(modelo) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 0) {
                    return new TextAreaRenderer(); // 👈 agora usa a nova classe
                }
                return super.getCellRenderer(row, column);
            }
        };
        tabelaGastos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaGastos.setGridColor(new Color(120, 120, 120));
        tabelaGastos.setShowGrid(true);
        tabelaGastos.setAutoCreateRowSorter(true);

        JTableHeader header = tabelaGastos.getTableHeader();
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

        JScrollPane scrollPane = new JScrollPane(tabelaGastos);
        scrollPane.getViewport().setBackground(new Color(156, 156, 156));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(156, 156, 156));
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        if (popUpListar != null && popUpListar.isVisible()) {
            popUpListar.toFront();
        } else {
            popUpListar = framePai.criarPopUp(titulo, painelPrincipal, 600, 400);
            popUpListar.setVisible(true);
        }
    }

    class TextAreaRenderer extends JTextArea implements TableCellRenderer {
        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            setText(value != null ? value.toString() : "");
            setForeground(Color.BLACK);
            setBackground(row % 2 == 0 ? new Color(220, 220, 220) : Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            // 👇 ESSENCIAL: define largura antes de calcular altura preferida
            setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);
            int alturaLinha = getPreferredSize().height;

            if (table.getRowHeight(row) != alturaLinha) {
                table.setRowHeight(row, alturaLinha);
            }

            return this;
        }
    }

    private javax.swing.JButton jBVoltar;
    private javax.swing.JButton jBPopUpListarGastosPer;
    private javax.swing.JButton jBPopUpListarGastosEmp;
    private javax.swing.JButton jBPopUpAdicionarGastos;
    private JLabel jLListarGastosEmp;
    private JLabel jLListarGastosPer;
    private JLabel jLAdicionarGastos;
    private JLabel jLTituloGastos;
}
