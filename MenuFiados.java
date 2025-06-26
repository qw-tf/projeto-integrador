import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MenuFiados extends JPanel {

    private final SistemaPrincipal framePai;
    private JScrollPane painelScroll2;

    public MenuFiados(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        estilizarBotaoMaior(jBVoltar);
        estilizarBotaoMaior(jBPopUpFiadosListarP);
        estilizarBotaoMaior(jBPopUpFiadosAdicionarP);
        estilizarBotaoMaior(jBPopUpFiadosQuitarP);

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
        linha.setBackground(Color.WHITE);

        linha.add(new JLabel(id));
        linha.add(new JLabel(nome));
        linha.add(new JLabel(preco));
        linha.add(new JLabel(qtd));
        linha.add(new JLabel(validade));

        container.add(linha);
    }

    private void initComponents() {
        jBVoltar = new javax.swing.JButton("VOLTAR");
        jBPopUpFiadosAdicionarP = new javax.swing.JButton();
        jBPopUpFiadosListarP = new javax.swing.JButton();
        jBPopUpFiadosQuitarP = new javax.swing.JButton();

        GridBagConstraints gbc = new GridBagConstraints();

        jBPopUpFiadosListarP.setText("Baphomet");
        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.insets = new Insets(50, 40, 10, 10);
        this.add(jBPopUpFiadosListarP, gbc);
        jBPopUpFiadosListarP.addActionListener(e -> {
            JFrame popUpFiadosListar = new JFrame("LISTAR VENDAS");
            popUpFiadosListar.setSize(800, 500);
            popUpFiadosListar.setResizable(false);
            popUpFiadosListar.setLocationRelativeTo(null);
            popUpFiadosListar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);

            JPanel cabecalho = new JPanel(new GridLayout(1, 5));
            cabecalho.setBackground(Color.LIGHT_GRAY);
            cabecalho.add(new JLabel("ID"));
            cabecalho.add(new JLabel("NOME DA VENDA"));
            cabecalho.add(new JLabel("PREÇO"));
            cabecalho.add(new JLabel("QUANTIDADE"));
            cabecalho.add(new JLabel("DATA DA VENDA"));
            panel.add(cabecalho);

            // 🔷 Linhas de produto
            adicionarLinhaProduto(panel, "1", "Sabre de Luz", "R$ 999.99", "2", "25/12/3025");
            adicionarLinhaProduto(panel, "2", "Capa Jedi", "R$ 199.90", "5", "30/06/3027");
            adicionarLinhaProduto(panel, "3", "Holocron", "R$ 450.00", "1", "01/01/3030");

            // 🔽 Scroll
            JScrollPane painelScroll = new JScrollPane(panel);
            painelScroll.getVerticalScrollBar().setUnitIncrement(16);

            popUpFiadosListar.setContentPane(painelScroll);
            popUpFiadosListar.setVisible(true);
        });

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        jBPopUpFiadosAdicionarP.setText("bundasbundasbundas");
        this.add(jBPopUpFiadosAdicionarP, gbc);
        jBPopUpFiadosAdicionarP.addActionListener(e -> {
            JFrame popUpFiadosAdicionar = new JFrame("ADICIONAR VENDA");
            Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);
            setLayout(new GridBagLayout());
            JPanel panelzao = new JPanel();
            panelzao.setBackground(new Color(156, 156, 156)); // Aqui sim, diva!
            panelzao.setLayout(new GridBagLayout()); // Agora é dentro do painel
            popUpFiadosAdicionar.setSize(800, 500); // Tamanho da janelinha
            popUpFiadosAdicionar.setResizable(false);
            popUpFiadosAdicionar.setLocationRelativeTo(null); // Centralizadinha, igual diva em spotlight
            popUpFiadosAdicionar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Só fecha ela, não o app todo
            popUpFiadosAdicionar.add(panelzao);

            JPanel jLNome = new JPanel();
            jLNome.add(new javax.swing.JLabel("VENDA"));
            jLNome.setFont(fonteLabel);
            panelzao.add(jLNome);

            popUpFiadosAdicionar.setVisible(true); // Agora SIM, BIXAA! Ela aparece!
        });

        jBPopUpFiadosQuitarP.setText("KusKuskus");
        gbc.gridx = 2;
        gbc.gridy = 0;
        framePai.estilizarBotaoMaior(jBPopUpFiadosQuitarP);
        this.add(jBPopUpFiadosQuitarP, gbc);
        jBPopUpFiadosQuitarP.addActionListener(e -> {
            JFrame popUpFiadosQuitar = new JFrame("EXCLUIR VENDA");
            Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);
            setLayout(new GridBagLayout());
            JPanel panelzao = new JPanel();
            panelzao.setBackground(new Color(156, 156, 156)); // Aqui sim, diva!
            panelzao.setLayout(new GridBagLayout()); // Agora é dentro do painel
            popUpFiadosQuitar.setSize(800, 500); // Tamanho da janelinha
            popUpFiadosQuitar.setResizable(false);
            popUpFiadosQuitar.setLocationRelativeTo(null); // Centralizadinha, igual diva em spotlight
            popUpFiadosQuitar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Só fecha ela, não o app todo
            popUpFiadosQuitar.add(panelzao);

            JPanel jLNome = new JPanel();
            jLNome.add(new javax.swing.JLabel("VENDA EXCLUIR"));
            jLNome.setFont(fonteLabel);
            panelzao.add(jLNome);

            popUpFiadosQuitar.setVisible(true);

        });

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        this.add(jBVoltar, gbc);

        jBVoltar.addActionListener(e -> framePai.trocarTela(new MenuAcess(framePai)));

    }

    private javax.swing.JButton jBVoltar;
    private javax.swing.JButton jBPopUpFiadosAdicionarP;
    private javax.swing.JButton jBPopUpFiadosListarP;
    private javax.swing.JButton jBPopUpFiadosQuitarP;

}
