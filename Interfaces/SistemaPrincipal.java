// SistemaPrincipal.java
package Interfaces;

import Backend.Estoque;
import Backend.FiadoRepositorio;
import Backend.GerenciadorSenha;
import Backend.RegistroVendas;

import java.awt.*;
import java.sql.SQLException;

import javax.swing.*;

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
        JDialog popUp = new JDialog(this, "Notificacoes", false);
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
        labelVindo.setFont(new Font("Arial", Font.BOLD, 50));
        labelVindo.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel labelSenha = new JLabel("SENHA:");
        labelSenha.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelSenha.setForeground(Color.BLACK);

        JPasswordField campoSenha = new JPasswordField(20);
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        campoSenha.setHorizontalAlignment(JTextField.CENTER);

        JButton btnContinuar = new JButton("Continuar");
        JButton btnSair = new JButton("Sair");
        JButton btnRecuperar = new JButton("R.Senha");

        Dimension mesmoTamanho = new Dimension(160, 40);
        btnContinuar.setPreferredSize(mesmoTamanho);
        btnSair.setPreferredSize(mesmoTamanho);
        btnRecuperar.setPreferredSize(mesmoTamanho);

        SistemaPrincipal.estilizarBotaoMaior(btnContinuar);
        SistemaPrincipal.estilizarBotaoMaior(btnSair);
        SistemaPrincipal.estilizarBotaoMaior(btnRecuperar);
        btnSair.setForeground(new Color(255, 50, 50));

        // Pressionar ENTER dentro do campo senha executa o botão Continuar
        campoSenha.addActionListener(e -> btnContinuar.doClick());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painelBotoes.setBackground(new Color(156, 156, 156));
        painelBotoes.add(btnSair);
        painelBotoes.add(btnContinuar);

        btnContinuar.addActionListener(e -> {
            String senha = new String(campoSenha.getPassword());
            if (senha.equals(GerenciadorSenha.carregarSenha())) {
                trocarTela(new MenuAcess(this));
            } else {
                JOptionPane.showMessageDialog(this, "Senha incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                campoSenha.setText("");
            }
        });

        btnSair.addActionListener(e -> System.exit(0));

        btnRecuperar.addActionListener(e -> {
            JTextField dica = new JTextField();
            JTextField token = new JTextField();
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Dica:"));
            panel.add(dica);
            panel.add(new JLabel("Token:"));
            panel.add(token);

            int result = JOptionPane.showConfirmDialog(this, panel, "Recuperar Senha",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                if (GerenciadorSenha.validarRecuperacao(dica.getText(), token.getText())) {
                    JOptionPane.showMessageDialog(this, "Senha atual: " + GerenciadorSenha.carregarSenha());
                } else {
                    JOptionPane.showMessageDialog(this, "Dados incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 0;
        telaInicial.add(labelVindo, gbc);

        gbc.gridy++;
        telaInicial.add(labelSenha, gbc);

        gbc.gridy++;
        telaInicial.add(campoSenha, gbc);

        gbc.gridy++;
        telaInicial.add(painelBotoes, gbc);

        gbc.gridy++;
        telaInicial.add(btnRecuperar, gbc);

        trocarTela(telaInicial);
    }

    private void abrirRecuperacaoSenha() {
        // Painel com 2 linhas: dica e token
        JPanel painel = new JPanel(new GridLayout(2, 1, 10, 10));
        painel.setBackground(new Color(156, 156, 156));

        // 1) JLabel com a dica
        JLabel campoDica = new JLabel("Dica: " + GerenciadorSenha.carregarDica());
        campoDica.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        painel.add(campoDica);

        // 2) JTextField para digitar o token
        JTextField campoToken = new JTextField();
        campoToken.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoToken.setBorder(BorderFactory.createTitledBorder("Digite o token"));
        painel.add(campoToken);

        // exibe o confirm dialog usando o panel que já contém o label e o text field
        int resultado = JOptionPane.showConfirmDialog(
                this,
                painel,
                "Recuperar Senha",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String token = campoToken.getText().trim();
            String dica = GerenciadorSenha.carregarDica();

            // valida corretamente: dica + token
            if (!token.isEmpty() &&
                    GerenciadorSenha.validarRecuperacao(dica, token)) {

                String senhaAtual = GerenciadorSenha.carregarSenha();
                JOptionPane.showMessageDialog(
                        this,
                        "Sua senha atual é: " + senhaAtual,
                        "Recuperação bem-sucedida",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // não mostra nada se vazio OU incorreto?
                // Mas aqui mostra erro apenas se não vazio
                if (!token.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Token incorreto!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            FiadoRepositorio.carregarFiadosDoBanco();
            RegistroVendas.carregarVendasDoBanco();
            Estoque.carregarDoBanco();
        } catch (SQLException ex) {
            System.out.println("Erro ao carregar do banco.");
        } catch (Exception ex) {
            System.out.println("Erro ao definir tema.");
        }
        UIManager.put("Button.select", new Color(20, 20, 20));
        SistemaPrincipal telas = new SistemaPrincipal();
        telas.setExtendedState(JFrame.MAXIMIZED_BOTH);
        telas.setVisible(true);
        telas.montarInterface();
    }
}
