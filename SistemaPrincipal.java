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

    public JDialog criarPopUp(String titulo, JPanel conteudo, int width, int height) {
        JDialog popUp = new JDialog(this, titulo, true); // true = modal
        popUp.setSize(width, height);
        popUp.setResizable(false);
        popUp.setLocationRelativeTo(null);
        popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conteudo.setBackground(new Color(156, 156, 156));

        popUp.setContentPane(conteudo);
        return popUp;
    }

    // Dentro da classe SistemaPrincipal, adicione os métodos abaixo

    public void abrirAdicionarProduto() {
        JPanel panelzao = new JPanel();
        panelzao.setLayout(new BoxLayout(panelzao, BoxLayout.Y_AXIS));
        panelzao.setBackground(new Color(156, 156, 156));

        JDialog popUpAdicionar = criarPopUp("ADICIONAR PRODUTO", panelzao, 800, 400);

        JPanel cabecalhoA = new JPanel(new GridLayout(1, 4));
        cabecalhoA.setBackground(Color.DARK_GRAY);
        String[] titulos = { "DESCRIÇÃO", "PREÇO", "QUANTIDADE", "DATA DE VALIDADE" };
        for (String titulo : titulos) {
            JLabel label = new JLabel(titulo, JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.BLACK);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            cabecalhoA.add(label);
        }
        panelzao.add(cabecalhoA);

        JPanel linhaCampos = new JPanel(new GridLayout(1, 4));
        linhaCampos.setBackground(new Color(200, 200, 200));

        JTextField campoDescricao = new JTextField();
        JTextField campoPreco = new JTextField();
        JTextField campoQuantidade = new JTextField();
        JTextField campoValidade = new JTextField();

        linhaCampos.add(campoDescricao);
        linhaCampos.add(campoPreco);
        linhaCampos.add(campoQuantidade);
        linhaCampos.add(campoValidade);
        panelzao.add(linhaCampos);

        JPanel painelBotoes = new JPanel(new BorderLayout());
        painelBotoes.setBackground(new Color(156, 156, 156));

        JButton botaoConfirmar = new JButton("CONFIRMAR");
        JButton botaoCancelar = new JButton("CANCELAR");

        JPanel painelEsquerda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelEsquerda.setOpaque(false);
        painelEsquerda.add(botaoCancelar);

        JPanel painelDireita = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelDireita.setOpaque(false);
        painelDireita.add(botaoConfirmar);

        painelBotoes.add(painelEsquerda, BorderLayout.WEST);
        painelBotoes.add(painelDireita, BorderLayout.EAST);

        painelBotoes.setMaximumSize(painelBotoes.getPreferredSize());
        painelBotoes.setAlignmentX(Component.RIGHT_ALIGNMENT);

        botaoConfirmar.setBackground(new Color(0, 0, 0));
        botaoConfirmar.setForeground(Color.WHITE);
        botaoCancelar.setBackground(Color.BLACK);
        botaoCancelar.setForeground(Color.WHITE);

        botaoCancelar.addActionListener(e -> popUpAdicionar.dispose());
        botaoConfirmar.addActionListener(e -> {
            // Lógica para adicionar o produto (ex: salvar em lista interna)
            popUpAdicionar.dispose();
        });

        panelzao.add(Box.createVerticalStrut(10));
        panelzao.add(painelBotoes);

        popUpAdicionar.setVisible(true);
    }

    public void abrirListarProdutos() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(156, 156, 156));

        JDialog popUpListar = criarPopUp("LISTA PRODUTOS", panel, 800, 400);

        JPanel cabecalho = new JPanel(new GridLayout(1, 5));
        cabecalho.setBackground(new Color(100, 100, 100));
        String[] titulos = { "ID", "DESCRIÇÃO", "PREÇO", "QUANTIDADE", "VALIDADE" };
        for (String titulo : titulos) {
            JLabel label = new JLabel(titulo, JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.DARK_GRAY);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            cabecalho.add(label);
        }
        panel.add(cabecalho);

        // Exemplo de produto (idealmente usar lista interna)
        adicionarLinhaProduto(panel, "1", "Sabre", "1000", "2", "25/12/3025");

        JScrollPane scroll = new JScrollPane(panel);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        popUpListar.setContentPane(scroll);
        popUpListar.setVisible(true);
    }

    public void abrirExcluirProduto() {
        JPanel panelzao = new JPanel();
        panelzao.setBackground(new Color(156, 156, 156));
        panelzao.setLayout(new GridBagLayout());
        JDialog popUpExcluir = criarPopUp("EXCLUIR PRODUTO", panelzao, 500, 200);

        JLabel label = new JLabel("EXCLUIR PRODUTO (exemplo)");
        panelzao.add(label);

        popUpExcluir.setVisible(true);
    }

    // Utilitário para adicionar linha (use conforme necessidade)
    public void adicionarLinhaProduto(JPanel container, String id, String nome, String preco, String qtd,
            String validade) {
        JPanel linha = new JPanel(new GridLayout(1, 5));
        linha.setBackground(new Color(156, 156, 156));
        linha.add(new JLabel(id));
        linha.add(new JLabel(nome));
        linha.add(new JLabel(preco));
        linha.add(new JLabel(qtd));
        linha.add(new JLabel(validade));
        container.add(linha);
    }

    public JDialog criarPopUp(String titulo, JPanel conteudo) {
        JDialog popUp = new JDialog(this, titulo, true); // true = modal
        popUp.setSize(800, 500);
        popUp.setResizable(false);
        popUp.setLocationRelativeTo(null);
        popUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conteudo.setBackground(new Color(156, 156, 156));

        popUp.setContentPane(conteudo);
        return popUp;
    }

    public void estilizarBotaoMaior(javax.swing.JButton botao) {
        botao.setBackground(Color.BLACK);
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 18));
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setOpaque(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
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

        btnContinuar.addActionListener(e -> trocarTela(new MenuAcess(this)));
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
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
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