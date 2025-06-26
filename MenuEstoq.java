import java.awt.*;
import javax.swing.*;

import Formatacao.LabelBotaoArredondado;

public class MenuEstoq extends JPanel {

    private final SistemaPrincipal framePai;
    private JScrollPane painelScroll;

    public MenuEstoq(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        framePai.estilizarBotaoMaior(jBVoltar);
        framePai.estilizarBotaoMaior(jBPopUpAdicionar);
        framePai.estilizarBotaoMaior(jBPopUpListar);
        framePai.estilizarBotaoMaior(JBpopUpExcluir);
    }

    private void adicionarLinhaProduto(JPanel container, String id, String nome, String preco, String qtd,
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

    private void initComponents() {
        jBVoltar = new javax.swing.JButton("VOLTAR");
        jBPopUpAdicionar = new javax.swing.JButton();
        jBPopUpListar = new javax.swing.JButton();
        JBpopUpExcluir = new javax.swing.JButton();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 20, 10, 20);
        jBPopUpAdicionar.setText("ADICIONAR");
        this.add(jBPopUpAdicionar, gbc);
        jBPopUpAdicionar.addActionListener(e -> {
            Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

            JPanel panelzao = new JPanel();
            panelzao.setLayout(new BoxLayout(panelzao, BoxLayout.Y_AXIS));
            panelzao.setBackground(new Color(156, 156, 156));

            JDialog popUpAdicionar = framePai.criarPopUp("ADICIONAR PRODUTO", panelzao, 800, 400);

            // Cabeçalho
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

            // Linha de campos
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

            // Painel dos botões
            JPanel painelBotoes = new JPanel(new BorderLayout());
            painelBotoes.setBackground(new Color(156, 156, 156));

            // Botões
            JButton botaoConfirmar = new JButton("CONFIRMAR");
            JButton botaoCancelar = new JButton("CANCELAR");

            // Painel do botão esquerdo
            JPanel painelEsquerda = new JPanel(new FlowLayout(FlowLayout.LEFT));
            painelEsquerda.setOpaque(false); // herda o fundo
            painelEsquerda.add(botaoCancelar);

            // Painel do botão direito
            JPanel painelDireita = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            painelDireita.setOpaque(false);
            painelDireita.add(botaoConfirmar);

            // Adiciona os dois lados
            painelBotoes.add(painelEsquerda, BorderLayout.WEST);
            painelBotoes.add(painelDireita, BorderLayout.EAST);

            // Restringe o tamanho ao necessário
            painelBotoes.setMaximumSize(painelBotoes.getPreferredSize());

            // 🧠 O segredo Jedi:
            painelBotoes.setMaximumSize(painelBotoes.getPreferredSize());
            painelBotoes.setAlignmentX(Component.RIGHT_ALIGNMENT);

            botaoConfirmar.setBackground(new Color(0, 0, 0));
            botaoConfirmar.setForeground(Color.WHITE);
            botaoCancelar.setBackground(Color.BLACK);
            botaoCancelar.setForeground(Color.WHITE);

            // Ação do botão "CONFIRMAR"
            botaoConfirmar.addActionListener(ev -> {
                String descricao = campoDescricao.getText().trim();
                String preco = campoPreco.getText().trim();
                String quantidade = campoQuantidade.getText().trim();
                String validade = campoValidade.getText().trim();
            });

            // Ação do botão "CANCELAR"
            botaoCancelar.addActionListener(ev -> popUpAdicionar.dispose());

            painelBotoes.add(botaoCancelar);
            painelBotoes.add(botaoConfirmar);

            panelzao.add(Box.createVerticalStrut(10)); // espaço entre campos e botões
            panelzao.add(painelBotoes);

            popUpAdicionar.setVisible(true);
        });

        jBPopUpListar.setText("LISTAR PRODUTOS");
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(jBPopUpListar, gbc);
        jBPopUpListar.addActionListener(e -> {
            // Painel principal que vai dentro do JScrollPane
            JPanel panel = new JPanel();
            JDialog popUpListar = framePai.criarPopUp("LISTA PRODUTOS", panel);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(156, 156, 156));

            // 🔶 Cabeçalho
            JPanel cabecalho = new JPanel(new GridLayout(1, 5));
            cabecalho.setBackground(new Color(100, 100, 100));
            String[] titulos = { "ID", "NOME DO PRODUTO", "PREÇO", "QUANTIDADE", "DATA DE VALIDADE" };

            for (String titulo : titulos) {
                JLabel label = new JLabel(titulo, JLabel.CENTER);
                label.setOpaque(true);
                label.setBackground(Color.DARK_GRAY);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                cabecalho.add(label);
            }

            panel.add(cabecalho);

            // 🔷 Linhas de produto
            adicionarLinhaProduto(panel, "1", "Sabre de Luz", "R$ 999.99", "2", "25/12/3025");
            adicionarLinhaProduto(panel, "2", "Capa Jedi", "R$ 199.90", "5", "30/06/3027");
            adicionarLinhaProduto(panel, "3", "Holocron", "R$ 450.00", "1", "01/01/3030");

            // 🔽 Scroll
            JScrollPane painelScroll = new JScrollPane(panel);
            painelScroll.getVerticalScrollBar().setUnitIncrement(16);

            popUpListar.setContentPane(painelScroll);
            popUpListar.setVisible(true);
        });

        JBpopUpExcluir.setText("EXCLUIR PRODUTO");
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(JBpopUpExcluir, gbc);
        JBpopUpExcluir.addActionListener(e -> {
            setLayout(new GridBagLayout());
            JPanel panelzao = new JPanel();
            JDialog popUpExcluir = framePai.criarPopUp("EXCLUIR PRODUTO", panelzao);
            panelzao.setBackground(new Color(156, 156, 156));
            panelzao.setLayout(new GridBagLayout());
            LabelBotaoArredondado labelProduto = new LabelBotaoArredondado("PRODUTO", new Color(30, 144, 255),
                    Color.WHITE);
            panelzao.add(labelProduto);
            popUpExcluir.setVisible(true);

        });

        // Botão Voltar no canto inferior esquerdo
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(40, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;
        this.add(jBVoltar, gbc);

        jBVoltar.addActionListener(e -> framePai.trocarTela(new MenuAcess(framePai)));
    }

    private javax.swing.JButton jBVoltar;
    private javax.swing.JButton jBPopUpAdicionar;
    private javax.swing.JButton jBPopUpListar;
    private javax.swing.JButton JBpopUpExcluir;

}