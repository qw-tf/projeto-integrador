import java.awt.*;
import javax.swing.*;

import Formatacao.LabelBotaoArredondado;

public class MenuBalanco extends JPanel {

    private final SistemaPrincipal framePai;

    public MenuBalanco(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

        jLBalancoAnual.setFont(fonteLabel);
        jLBalancoMensal.setFont(fonteLabel);
        jLBalancoVendas.setFont(fonteLabel);

        framePai.estilizarBotaoMaior(jBVoltar);
        framePai.estilizarBotaoMaior(jBBalancoMensal);
        framePai.estilizarBotaoMaior(jBBalancoAnual);
        framePai.estilizarBotaoMaior(jBBalancoVendas);
    }

    private void initComponents() {
        jLBalancoVendas = new JLabel("BALANÇO DE VENDAS");
        jLBalancoAnual = new JLabel("BALANÇO ANUAL");
        jLBalancoMensal = new JLabel("BALANÇO MENSAL");
        jLTituloBalanco = new JLabel("MENU DE BALANÇO");

        jBBalancoVendas = new JButton();
        jBBalancoMensal = new JButton();
        jBBalancoAnual = new JButton();
        jBVoltar = new JButton("VOLTAR");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        jLTituloBalanco.setFont(new Font("Segoe UI", Font.BOLD, 32));
        add(jLTituloBalanco, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(jLBalancoVendas, gbc);
        gbc.gridx = 1;
        jBBalancoVendas.setText(" ");
        add(jBBalancoVendas, gbc);
        // jBPopUpAdicionar.addActionListener(e -> framePai.abrirAdicionarProduto());

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLBalancoMensal, gbc);
        gbc.gridx = 1;
        jBBalancoMensal.setText(" ");
        add(jBBalancoMensal, gbc);
        // jBPopUpListar.addActionListener(e -> framePai.abrirListarProdutos());

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLBalancoAnual, gbc);
        gbc.gridx = 1;
        jBBalancoAnual.setText(" ");
        add(jBBalancoAnual, gbc);
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
    private javax.swing.JButton jBBalancoMensal;
    private javax.swing.JButton jBBalancoAnual;
    private javax.swing.JButton jBBalancoVendas;
    private JLabel jLBalancoMensal;
    private JLabel jLBalancoAnual;
    private JLabel jLBalancoVendas;
    private JLabel jLTituloBalanco;
}
