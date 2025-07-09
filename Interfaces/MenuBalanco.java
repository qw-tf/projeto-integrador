package Interfaces;

import Backend.RegistroVendas;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MenuBalanco extends JPanel {

    private final SistemaPrincipal framePai;
    private JDialog popUpResultado;

    public MenuBalanco(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);
        jLBalancoAnual.setFont(fonteLabel);
        jLBalancoMensal.setFont(fonteLabel);
        jLBalancoVendas.setFont(fonteLabel);

        SistemaPrincipal.estilizarBotaoMaior(jBVoltar);
        SistemaPrincipal.estilizarBotaoMenor(jBBalancoMensal);
        SistemaPrincipal.estilizarBotaoMenor(jBBalancoAnual);
        SistemaPrincipal.estilizarBotaoMenor(jBBalancoVendas);
    }

    private void initComponents() {
        jLBalancoVendas = new JLabel("BALANÇO DE VENDAS");
        jLBalancoAnual = new JLabel("BALANÇO ANUAL");
        jLBalancoMensal = new JLabel("BALANÇO MENSAL");
        jLTituloBalanco = new JLabel("MENU DE BALANÇO");

        jBBalancoVendas = new JButton("●");
        jBBalancoMensal = new JButton("●");
        jBBalancoAnual = new JButton("●");
        jBVoltar = new JButton("VOLTAR");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        jLTituloBalanco.setFont(new Font("Segoe UI", Font.BOLD, 32));
        add(jLTituloBalanco, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(jLBalancoVendas, gbc);
        gbc.gridx = 1;
        add(jBBalancoVendas, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLBalancoMensal, gbc);
        gbc.gridx = 1;
        add(jBBalancoMensal, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLBalancoAnual, gbc);
        gbc.gridx = 1;
        add(jBBalancoAnual, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(40, 30, 10, 10);
        add(jBVoltar, gbc);

        jBVoltar.addActionListener(e -> framePai.trocarTela(new MenuAcess(framePai)));
        jBBalancoMensal.addActionListener(e -> mostrarBalancoMensal());
        jBBalancoAnual.addActionListener(e -> mostrarBalancoAnual());
        jBBalancoVendas.addActionListener(e -> mostrarBalancoDiario());
    }

    private void mostrarBalancoMensal() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicio = hoje.minusDays(29);
        Map<LocalDate, Double[]> dados = RegistroVendas.calcularBalancoPorDia(inicio, hoje);

        String[][] linhas = new String[dados.size()][4];
        int i = 0;
        double totalLucro = 0, totalGasto = 0;
        for (Map.Entry<LocalDate, Double[]> entry : dados.entrySet()) {
            double lucro = entry.getValue()[0];
            double gasto = entry.getValue()[1];
            double patrimonio = lucro - gasto;
            linhas[i][0] = entry.getKey().format(DateTimeFormatter.ofPattern("dd/MM"));
            linhas[i][1] = String.format("R$ %.2f", lucro);
            linhas[i][2] = String.format("R$ %.2f", gasto);
            linhas[i][3] = String.format("R$ %.2f", patrimonio);
            totalLucro += lucro;
            totalGasto += gasto;
            i++;
        }

        String[] colunas = { "Data", "Lucro", "Gasto", "Patrimônio Líquido" };
        String resumo = String.format("""
                Período: %s até %s
                TOTAL PATRIMÔNIO MENSAL: R$ %.2f
                """, inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                (totalLucro - totalGasto));

        mostrarResultado("BALANÇO MENSAL", linhas, colunas, resumo);
    }

    private void mostrarBalancoAnual() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioAno = LocalDate.of(hoje.getYear(), 1, 1);
        Map<Integer, Double[]> dados = RegistroVendas.calcularBalancoPorMes(inicioAno, hoje);

        String[][] linhas = new String[12][4];
        double totalLucro = 0, totalGasto = 0;
        for (int mes = 1; mes <= 12; mes++) {
            Double[] valores = dados.getOrDefault(mes, new Double[] { 0.0, 0.0 });
            double lucro = valores[0];
            double gasto = valores[1];
            double patrimonio = lucro - gasto;
            linhas[mes - 1][0] = String.format("%02d", mes);
            linhas[mes - 1][1] = String.format("R$ %.2f", lucro);
            linhas[mes - 1][2] = String.format("R$ %.2f", gasto);
            linhas[mes - 1][3] = String.format("R$ %.2f", patrimonio);
            totalLucro += lucro;
            totalGasto += gasto;
        }

        String[] colunas = { "Mês", "Lucro", "Gasto", "Patrimônio Líquido" };
        String resumo = String.format("""
                Período: 01/01/%d até %s
                TOTAL PATRIMÔNIO ANUAL: R$ %.2f
                """, hoje.getYear(),
                hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                (totalLucro - totalGasto));

        mostrarResultado("BALANÇO ANUAL", linhas, colunas, resumo);
    }

    private void mostrarBalancoDiario() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicio = hoje.minusDays(6);
        Map<LocalDate, Double[]> dados = RegistroVendas.calcularBalancoPorDia(inicio, hoje);

        String[][] linhas = new String[dados.size()][4];
        int i = 0;
        double totalLucro = 0, totalGasto = 0;
        for (Map.Entry<LocalDate, Double[]> entry : dados.entrySet()) {
            double lucro = entry.getValue()[0];
            double gasto = entry.getValue()[1];
            double patrimonio = lucro - gasto;
            linhas[i][0] = entry.getKey().format(DateTimeFormatter.ofPattern("dd/MM"));
            linhas[i][1] = String.format("R$ %.2f", lucro);
            linhas[i][2] = String.format("R$ %.2f", gasto);
            linhas[i][3] = String.format("R$ %.2f", patrimonio);
            totalLucro += lucro;
            totalGasto += gasto;
            i++;
        }

        String[] colunas = { "Data", "Lucro", "Gasto", "Patrimônio Líquido" };
        String resumo = String.format("""
                Período: %s até %s
                TOTAL PATRIMÔNIO DOS 7 DIAS: R$ %.2f
                """, inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                hoje.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                (totalLucro - totalGasto));

        mostrarResultado("BALANÇO DE VENDAS", linhas, colunas, resumo);
    }

    private void mostrarResultado(String titulo, String[][] dadosTabela, String[] colunas, String resumo) {
        DefaultTableModel modelo = new DefaultTableModel(dadosTabela, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabela = new JTable(modelo);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setRowHeight(22);
        tabela.setGridColor(Color.BLACK);
        tabela.setShowGrid(true);
        tabela.setAutoCreateRowSorter(true);

        JTableHeader header = tabela.getTableHeader();
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

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.getViewport().setBackground(new Color(156, 156, 156));
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JTextArea rodape = new JTextArea(resumo);
        rodape.setFont(new Font("Segoe UI", Font.BOLD, 14));
        rodape.setEditable(false);
        rodape.setBackground(new Color(110, 110, 110)); // cor mais escura
        rodape.setForeground(Color.BLACK); // texto branco
        rodape.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(156, 156, 156));
        painelPrincipal.add(scroll, BorderLayout.CENTER);
        painelPrincipal.add(rodape, BorderLayout.SOUTH);

        if (popUpResultado != null && popUpResultado.isVisible()) {
            popUpResultado.toFront();
        } else {
            popUpResultado = framePai.criarPopUp(titulo, painelPrincipal, 800, 403);
            popUpResultado.setVisible(true);
        }
    }

    private JButton jBVoltar;
    private JButton jBBalancoMensal;
    private JButton jBBalancoAnual;
    private JButton jBBalancoVendas;
    private JLabel jLBalancoMensal;
    private JLabel jLBalancoAnual;
    private JLabel jLBalancoVendas;
    private JLabel jLTituloBalanco;
}