package Interfaces;

import java.awt.*;
import javax.swing.*;

public class MenuVendas extends JPanel {

    private final SistemaPrincipal framePai;

    public MenuVendas(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

        jLAdicionarVendas.setFont(fonteLabel);
        jLListarVendas.setFont(fonteLabel);
        jLExcluirVendas.setFont(fonteLabel);

        framePai.estilizarBotaoMaior(jBVoltar);
        framePai.estilizarBotaoMaior(jBPopUpVendasAdicionar);
        framePai.estilizarBotaoMaior(jBPopUpVendasListar);
        framePai.estilizarBotaoMaior(jBPopUpVendasExcluir);

    }

    private void initComponents() {
        jLAdicionarVendas = new JLabel("ADICIONAR VENDAS");
        jLListarVendas = new JLabel("LISTAR VENDAS");
        jLExcluirVendas = new JLabel("EXCLUIR VENDAS");
        jLTituloVendas = new JLabel("MENU DE VENDAS");

        jBPopUpVendasAdicionar = new JButton();
        jBPopUpVendasListar = new JButton();
        jBPopUpVendasExcluir = new JButton();
        jBVoltar = new JButton("VOLTAR");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        jLTituloVendas.setFont(new Font("Segoe UI", Font.BOLD, 32));
        add(jLTituloVendas, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(jLAdicionarVendas, gbc);
        gbc.gridx = 1;
        jBPopUpVendasAdicionar.setText(" ");
        add(jBPopUpVendasAdicionar, gbc);
        // jBPopUpAdicionar.addActionListener(e -> framePai.abrirAdicionarProduto());

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLListarVendas, gbc);
        gbc.gridx = 1;
        jBPopUpVendasListar.setText(" ");
        add(jBPopUpVendasListar, gbc);
        // jBPopUpListar.addActionListener(e -> framePai.abrirListarProdutos());

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLExcluirVendas, gbc);
        gbc.gridx = 1;
        jBPopUpVendasExcluir.setText(" ");
        add(jBPopUpVendasExcluir, gbc);
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
    private javax.swing.JButton jBPopUpVendasAdicionar;
    private javax.swing.JButton jBPopUpVendasListar;
    private javax.swing.JButton jBPopUpVendasExcluir;
    private JLabel jLAdicionarVendas;
    private JLabel jLListarVendas;
    private JLabel jLExcluirVendas;
    private JLabel jLTituloVendas;
}
