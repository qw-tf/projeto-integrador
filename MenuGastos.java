import java.awt.*;
import javax.swing.*;

public class MenuGastos extends JPanel {
    private final SistemaPrincipal framePai;
    private JScrollPane painelScroll3;

    public MenuGastos(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        framePai.estilizarBotaoMaior(jBVoltar);
        framePai.estilizarBotaoMaior(jBPopUpListarGastosP);
        framePai.estilizarBotaoMaior(jBPopUpListarGastosE);
        framePai.estilizarBotaoMaior(jBPopUpAdicionarGastos);
    }

    private void initComponents() {
        jBVoltar = new javax.swing.JButton("VOLTAR");
        jBPopUpListarGastosP = new javax.swing.JButton();
        jBPopUpListarGastosE = new javax.swing.JButton();
        jBPopUpAdicionarGastos = new javax.swing.JButton();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 40, 10, 10);
        jBPopUpAdicionarGastos.setText("ADICIONAR GASTOS");
        this.add(jBPopUpAdicionarGastos, gbc);
        jBPopUpAdicionarGastos.addActionListener(e -> {
            Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

            JPanel panelzao = new JPanel();
            JDialog popUpAdicionarGastos = framePai.criarPopUp("ADICIONAR GASTO", panelzao);
            popUpAdicionarGastos.setLayout(new GridBagLayout());
            javax.swing.JLabel labelProduto = new javax.swing.JLabel("GASTO");
            labelProduto.setFont(fonteLabel);
            labelProduto.setForeground(Color.BLACK); // opcional, caso queira forçar cor

            labelProduto.setOpaque(false); // O SEGREDO PRA ACABAR COM O HIGHLIGHT HORRENDO
            panelzao.add(labelProduto);

            popUpAdicionarGastos.setVisible(true);
        });

        jBPopUpListarGastosP.setText("GASTOS PESSOAIS");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 40, 10, 10);
        this.add(jBPopUpListarGastosP, gbc);
        jBPopUpListarGastosP.addActionListener(e -> {

            JPanel panel = new JPanel();
            JDialog popUpListarP = framePai.criarPopUp("LISTA GASTOS PESSOAIS", panel);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);

            JPanel cabecalho = new JPanel(new GridLayout(1, 5));
            cabecalho.setBackground(Color.LIGHT_GRAY);
            cabecalho.add(new JLabel("ID"));
            cabecalho.add(new JLabel("NOME Do fiados"));
            cabecalho.add(new JLabel("sla"));
            cabecalho.add(new JLabel("vaitoamnocu"));
            cabecalho.add(new JLabel("DATA DA MINHA ROLA"));
            panel.add(cabecalho);

            // 🔷 Linhas de produto
            adicionarLinhaProduto(panel, "1", "Sabre de Luz", "R$ 999.99", "2", "25/12/3025");
            adicionarLinhaProduto(panel, "2", "Capa Jedi", "R$ 199.90", "5", "30/06/3027");
            adicionarLinhaProduto(panel, "3", "Holocron", "R$ 450.00", "1", "01/01/3030");

            // 🔽 Scroll
            JScrollPane painelScroll = new JScrollPane(panel);
            painelScroll.getVerticalScrollBar().setUnitIncrement(16);

            popUpListarP.setContentPane(painelScroll);
            popUpListarP.setVisible(true);
        });
        jBPopUpListarGastosE.setText("GASTOS EMPRESARIAIS");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 40, 10, 10);
        this.add(jBPopUpListarGastosE, gbc);
        jBPopUpListarGastosE.addActionListener(e -> {
            JPanel panel = new JPanel();
            JDialog popUpListarGastosE = framePai.criarPopUp("LISTA GASTOS EMPRESARIAIS", panel);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);

            JPanel cabecalho = new JPanel(new GridLayout(1, 5));
            cabecalho.setBackground(Color.LIGHT_GRAY);
            cabecalho.add(new JLabel("ID"));
            cabecalho.add(new JLabel("NOME Do fiatroçodos"));
            cabecalho.add(new JLabel("lklklklkl"));
            cabecalho.add(new JLabel("vaitoamnocu"));
            cabecalho.add(new JLabel("njhkjhkjhhjh"));
            panel.add(cabecalho);

            // 🔷 Linhas de produto
            adicionarLinhaProduto(panel, "1", "Sabre de Luz", "R$ 999.99", "2", "25/12/3025");
            adicionarLinhaProduto(panel, "2", "Capa Jedi", "R$ 199.90", "5", "30/06/3027");
            adicionarLinhaProduto(panel, "3", "Holocron", "R$ 450.00", "1", "01/01/3030");

            // 🔽 Scroll
            JScrollPane painelScroll = new JScrollPane(panel);
            painelScroll.getVerticalScrollBar().setUnitIncrement(16);

            popUpListarGastosE.setContentPane(painelScroll);
            popUpListarGastosE.setVisible(true);
        });

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(50, 10, 10, 10);
        this.add(jBVoltar, gbc);
        jBVoltar.addActionListener(e -> framePai.trocarTela(new MenuAcess(framePai)));
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

    private javax.swing.JButton jBVoltar;
    private javax.swing.JButton jBPopUpListarGastosP;
    private javax.swing.JButton jBPopUpListarGastosE;
    private javax.swing.JButton jBPopUpAdicionarGastos;
}