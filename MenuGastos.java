import java.awt.*;
import javax.swing.*;

import Formatacao.LabelBotaoArredondado;

public class MenuGastos extends JPanel {

    private final SistemaPrincipal framePai;

    public MenuGastos(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

        jLListarGastosPer.setFont(fonteLabel);
        jLListarGastosEmp.setFont(fonteLabel);
        jLAdicionarGastos.setFont(fonteLabel);

        framePai.estilizarBotaoMaior(jBVoltar);
        framePai.estilizarBotaoMaior(jBPopUpListarGastosPer);
        framePai.estilizarBotaoMaior(jBPopUpListarGastosEmp);
        framePai.estilizarBotaoMaior(jBPopUpAdicionarGastos);
    }

    private void initComponents() {
        jLAdicionarGastos = new JLabel("ADICIONAR GASTO");
        jLListarGastosPer = new JLabel("LISTAR GASTOS PESSOAIS");
        jLListarGastosEmp = new JLabel("LISTAR GASTOS EMPRESARIAIS");
        jLTituloGastos = new JLabel("MENU DE GASTOS");

        jBPopUpAdicionarGastos = new JButton();
        jBPopUpListarGastosPer = new JButton();
        jBPopUpListarGastosEmp = new JButton();
        jBVoltar = new JButton("VOLTAR");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        jLTituloGastos.setFont(new Font("Segoe UI", Font.BOLD, 32));
        add(jLTituloGastos, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(jLAdicionarGastos, gbc);
        gbc.gridx = 1;
        jBPopUpAdicionarGastos.setText(" ");
        add(jBPopUpAdicionarGastos, gbc);
        // jBPopUpAdicionar.addActionListener(e -> framePai.abrirAdicionarProduto());

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLListarGastosPer, gbc);
        gbc.gridx = 1;
        jBPopUpListarGastosPer.setText(" ");
        add(jBPopUpListarGastosPer, gbc);
        // jBPopUpListar.addActionListener(e -> framePai.abrirListarProdutos());

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLListarGastosEmp, gbc);
        gbc.gridx = 1;
        jBPopUpListarGastosEmp.setText(" ");
        add(jBPopUpListarGastosEmp, gbc);
        // JBpopUpExcluir.addActionListener(e -> framePai.abrirExcluirProduto());

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(40, 30, 10, 10);
        add(jBVoltar, gbc);

        jBVoltar.addActionListener(e -> framePai.trocarTela(new MenuAcess(framePai)));
    }

    private javax.swing.JButton jBVoltar;
    private javax.swing.JButton jBPopUpListarGastosPer;
    private javax.swing.JButton jBPopUpListarGastosEmp;
    private javax.swing.JButton jBPopUpAdicionarGastos;
    private JLabel jLListarGastosEmp;
    private JLabel jLListarGastosPer;
    private JLabel jLAdicionarGastos;
    private JLabel jLTituloGastos;
}
