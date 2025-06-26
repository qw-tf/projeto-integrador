import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MenuEstoq extends JPanel {

    private final SistemaPrincipal framePai;
    private JScrollPane painelScroll;

    public MenuEstoq(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        estilizarBotaoMaior(jBVoltar);
        estilizarBotaoMaior(jBPopUpAdicionar);
        estilizarBotaoMaior(jBPopUpListar);
        estilizarBotaoMaior(JBpopUpExcluir);
    }

    private void estilizarBotaoMaior(javax.swing.JButton botao) {
        botao.setBackground(new Color(0, 0, 0));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 18));
        botao.setOpaque(true);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
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
        jBPopUpAdicionar.setText("fdodases???");
        this.add(jBPopUpAdicionar, gbc);
        jBPopUpAdicionar.addActionListener(e -> {
            Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

            JPanel panelzao = new JPanel();
            JDialog popUpAdicionar = framePai.criarPopUp("ADICIONAR PRODUTO", panelzao);
            popUpAdicionar.setLayout(new GridBagLayout());
            javax.swing.JLabel labelProduto = new javax.swing.JLabel("PRODUTO");
            labelProduto.setFont(fonteLabel);
            labelProduto.setForeground(Color.BLACK); // opcional, caso queira forçar cor

            labelProduto.setOpaque(false); // O SEGREDO PRA ACABAR COM O HIGHLIGHT HORRENDO
            panelzao.add(labelProduto);

            popUpAdicionar.setVisible(true);

        });

        jBPopUpListar.setText("fdodases!!!");
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(jBPopUpListar, gbc);
        jBPopUpListar.addActionListener(e -> {
            // Painel principal que vai dentro do JScrollPane
            JPanel panel = new JPanel();
            JDialog popUpListar = framePai.criarPopUp("LISTAR PRODUTOS", panel);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(156, 156, 156));

            // 🔶 Cabeçalho
            JPanel cabecalho = new JPanel(new GridLayout(1, 5));
            cabecalho.setBackground(new Color(100, 100, 100));
            cabecalho.add(new JLabel("ID"));
            cabecalho.add(new JLabel("NOME DO PRODUTO"));
            cabecalho.add(new JLabel("PREÇO"));
            cabecalho.add(new JLabel("QUANTIDADE"));
            cabecalho.add(new JLabel("DATA DE VALIDADE"));
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

        JBpopUpExcluir.setText("fdodases!?!");
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(JBpopUpExcluir, gbc);
        JBpopUpExcluir.addActionListener(e -> {
            Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);
            setLayout(new GridBagLayout());
            JPanel panelzao = new JPanel();
            JDialog popUpExcluir = framePai.criarPopUp("EXCLUIR PRODUTO", panelzao);
            panelzao.setBackground(new Color(156, 156, 156));
            panelzao.setLayout(new GridBagLayout());

            JButton jBExcluir = new JButton("EXCLUIR");
            framePai.estilizarBotaoMaior(jBExcluir);
            gbc.gridx = 0;
            gbc.gridy = 6;
            panelzao.add(jBExcluir, gbc);

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