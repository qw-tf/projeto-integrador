// SistemaPrincipal.java
package Interfaces;

import Backend.Estoque;
import Backend.RepositorioFiados;
import Backend.GerenciadorSenha;
import Backend.ItemVenda;
import Backend.Produto;
import Backend.RegistroVendas;
import Backend.Venda;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

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
            abrirRecuperacaoSenha();
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

    // Dentro da sua classe de UI (por exemplo, MenuRecuperacaoSenha)
    private void abrirRecuperacaoSenha() {
        Font fonte = new Font("Segoe UI", Font.PLAIN, 14);

        // Painel de conteúdo com BorderLayout
        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.setBackground(new Color(156, 156, 156));
        conteudo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel central com GridBagLayout
        JPanel grid = new JPanel(new GridBagLayout());
        grid.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelDica = new JLabel("Dica:");
        JLabel textoDica = new JLabel(GerenciadorSenha.carregarDica());
        JLabel labelToken = new JLabel("Token:");
        JTextField campoToken = new JTextField(5);

        // Estilo do campo
        campoToken.setFont(fonte);
        campoToken.setBackground(Color.WHITE);
        campoToken.setForeground(Color.BLACK);
        campoToken.setPreferredSize(new Dimension(80, 18));
        campoToken.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(2, 4, 2, 4)));

        labelDica.setFont(fonte);
        textoDica.setFont(fonte);
        labelToken.setFont(fonte);

        // Adiciona "Dica:"
        gbc.gridx = 0;
        gbc.gridy = 0;
        grid.add(labelDica, gbc);

        // Adiciona texto da dica
        gbc.gridx = 1;
        grid.add(textoDica, gbc);

        // Adiciona "Token:"
        gbc.gridx = 0;
        gbc.gridy = 1;
        grid.add(labelToken, gbc);

        // Adiciona campo do token com espaçamento superior maior
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 5, 5, 5); // margem superior maior
        grid.add(campoToken, gbc);

        // Painel de botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        botoes.setBackground(new Color(156, 156, 156));
        JButton btnCancelar = new JButton("CANCELAR");
        JButton btnRecuperar = new JButton("RECUPERAR");
        estilizarBotaoMaior(btnCancelar);
        estilizarBotaoMaior(btnRecuperar);
        botoes.add(btnCancelar);
        botoes.add(btnRecuperar);

        // Monta conteúdo
        conteudo.add(grid, BorderLayout.CENTER);
        conteudo.add(botoes, BorderLayout.SOUTH);

        // Cria pop-up
        JDialog popup = criarPopUp("RECUPERAR SENHA", conteudo, 350, 180);

        // Ações dos botões
        btnCancelar.addActionListener(e -> popup.dispose());
        btnRecuperar.addActionListener(e -> {
            String token = campoToken.getText().trim();
            if (token.isEmpty()) {
                JOptionPane.showMessageDialog(popup, "Por favor, informe o token!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!GerenciadorSenha.validarTokenRecuperacao(token)) {
                JOptionPane.showMessageDialog(popup, "Token incorreto!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String senha = GerenciadorSenha.carregarSenha();
            JOptionPane.showMessageDialog(popup, "Senha atual: " + senha, "Recuperação bem-sucedida",
                    JOptionPane.INFORMATION_MESSAGE);
            popup.dispose();
        });

        popup.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            RepositorioFiados.carregarFiadosDoBanco();
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
