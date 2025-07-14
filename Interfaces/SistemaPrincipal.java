// SistemaPrincipal.java
package Interfaces;

import javax.swing.*;

import Backend.Estoque;
import Backend.Produto;

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

    public JDialog criarPopUp(String titulo, JPanel conteudo, int width, int height) {
        JDialog popUp = new JDialog(this, titulo, true);
        popUp.setSize(width, height);
        popUp.setResizable(false);
        popUp.setLocationRelativeTo(null);
        popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conteudo.setBackground(new Color(156, 156, 156));
        popUp.setContentPane(conteudo);
        return popUp;
    }

    public JDialog criarPopUp(String titulo, JScrollPane conteudo, int width, int height) {
        JDialog popUp = new JDialog(this, titulo, true);
        popUp.setSize(width, height);
        popUp.setResizable(false);
        popUp.setLocationRelativeTo(null);
        popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conteudo.setBackground(new Color(156, 156, 156));
        popUp.setContentPane(conteudo);
        return popUp;
    }

    public JDialog criarPopUpNotificacoesTopoEsquerdo(JScrollPane conteudo) {
        JDialog popUp = new JDialog(this, "Notificações", false);
        popUp.setSize(400, 300);
        popUp.setResizable(true);
        popUp.setLocation(20, 20);
        popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conteudo.setBorder(BorderFactory.createEmptyBorder());
        popUp.setContentPane(conteudo);
        return popUp;
    }

    public static void estilizarBotaoMaior(JButton botao) {
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 18));
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setOpaque(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void estilizarBotaoMenor(JButton botao) {
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 24));
        botao.setBorderPainted(false);
        botao.putClientProperty("JButton.arc", 20);
        botao.setText("  ►  ");
        botao.setFocusPainted(false);
        botao.setOpaque(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void initComponents() {
        setTitle("Canaã");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));
        getContentPane().setLayout(new GridBagLayout());
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

        btnContinuar.addActionListener(e -> trocarTela(new MenuAcess(this)));
        btnSair.addActionListener(e -> System.exit(0));

        painelBotoes.add(btnSair);
        painelBotoes.add(btnContinuar);

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

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Erro ao definir tema.");
        }
        UIManager.put("Button.select", new Color(20, 20, 20));
        SistemaPrincipal telas = new SistemaPrincipal();
        Estoque.adicionarProduto("fdases?", 100, 10, 2.50, false, null);
        telas.setExtendedState(JFrame.MAXIMIZED_BOTH);
        telas.setVisible(true);
        telas.montarInterface();
    }
}
