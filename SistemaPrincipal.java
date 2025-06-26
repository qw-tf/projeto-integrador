import javax.swing.*;

import java.awt.*;

public class SistemaPrincipal extends javax.swing.JFrame {

    public SistemaPrincipal() {
        initComponents();
    }

    public void trocarTela(JPanel novaTela) {
        setContentPane(novaTela);
        revalidate();
        repaint();
    }

    public void estilizarBotaoMaior(javax.swing.JButton botao) {
        botao.setBackground(new Color(0, 0, 0));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 18));
        botao.setOpaque(true);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
    }

    private void initComponents() {
        setTitle("Canaã");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1000, 700));
        getContentPane().setLayout(new java.awt.GridBagLayout());
        getContentPane().setBackground(new Color(156, 156, 156));
        pack();
    }

    public void montarInterface() {
        JPanel telaInicial = new JPanel(new GridBagLayout());
        telaInicial.setBackground(new Color(156, 156, 156));

        JLabel labelVindo = new JLabel("SEJA BEM VINDO!");
        JButton btnContinuar = new JButton("Continuar");
        JButton btnSair = new JButton("Sair");
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painelBotoes.setBackground(new Color(156, 156, 156));

        labelVindo.setFont(new Font("Arial", Font.BOLD, 50));
        labelVindo.setHorizontalAlignment(SwingConstants.CENTER);

        btnContinuar.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btnSair.setFont(new Font("Segoe UI", Font.BOLD, 20));

        Dimension mesmoTamanho = new Dimension(160, 40);
        btnContinuar.setPreferredSize(mesmoTamanho);
        btnSair.setPreferredSize(mesmoTamanho);

        estilizarBotaoMaior(btnContinuar);
        estilizarBotaoMaior(btnSair);
        btnSair.setForeground(new Color(255, 50, 50));

        btnContinuar.addActionListener(e -> trocarTela(new MenuN1(this)));
        btnSair.addActionListener(e -> System.exit(0));

        painelBotoes.add(btnContinuar);
        painelBotoes.add(btnSair);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        telaInicial.add(labelVindo, gbc);

        gbc.gridy = 1;
        telaInicial.add(painelBotoes, gbc);

        trocarTela(telaInicial);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
        }
        UIManager.put("Button.select", new Color(20, 20, 20)); // Cor do clique
        SistemaPrincipal telas = new SistemaPrincipal();
        telas.setExtendedState(JFrame.MAXIMIZED_BOTH);
        telas.setVisible(true);
        telas.montarInterface(); // já troca pra tela inicial
    }

}