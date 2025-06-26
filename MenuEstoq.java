import java.awt.*;
import javax.swing.*;

import Formatacao.LabelBotaoArredondado;

public class MenuEstoq extends JPanel {

    private final SistemaPrincipal framePai;

    public MenuEstoq(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

        jLAdicionar.setFont(fonteLabel);
        jLListar.setFont(fonteLabel);
        jLExcluir.setFont(fonteLabel);

        framePai.estilizarBotaoMaior(jBVoltar);
        framePai.estilizarBotaoMaior(jBPopUpAdicionar);
        framePai.estilizarBotaoMaior(jBPopUpListar);
        framePai.estilizarBotaoMaior(jBpopUpExcluir);
        framePai.estilizarBotaoMaior(jBExportarLista);
    }

    private void initComponents() {
        jLAdicionar = new JLabel("ADICIONAR PRODUTO");
        jLListar = new JLabel("LISTAR PRODUTOS");
        jLExcluir = new JLabel("EXCLUIR PRODUTO");
        jLTituloEstoq = new JLabel("MENU DE ESTOQUE");

        jBPopUpAdicionar = new JButton();
        jBPopUpListar = new JButton();
        jBpopUpExcluir = new JButton();
        jBExportarLista = new JButton("EXPORTAR");
        jBVoltar = new JButton("VOLTAR");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        jLTituloEstoq.setFont(new Font("Segoe UI", Font.BOLD, 32));
        add(jLTituloEstoq, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(jLAdicionar, gbc);
        gbc.gridx = 1;
        jBPopUpAdicionar.setText(" ");
        add(jBPopUpAdicionar, gbc);
        jBPopUpAdicionar.addActionListener(e -> framePai.abrirAdicionarProduto());

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLListar, gbc);
        gbc.gridx = 1;
        jBPopUpListar.setText(" ");
        add(jBPopUpListar, gbc);
        jBPopUpListar.addActionListener(e -> framePai.abrirListarProdutos());

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLExcluir, gbc);
        gbc.gridx = 1;
        jBpopUpExcluir.setText(" ");
        add(jBpopUpExcluir, gbc);
        jBpopUpExcluir.addActionListener(e -> framePai.abrirExcluirProduto());

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(40, 40, 10, 20);
        this.add(jBExportarLista, gbc); // ou outro botão

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

    private JButton jBVoltar;
    private JButton jBPopUpAdicionar;
    private JButton jBPopUpListar;
    private JButton jBpopUpExcluir;
    private JButton jBExportarLista;
    private JLabel jLAdicionar;
    private JLabel jLListar;
    private JLabel jLExcluir;
    private JLabel jLTituloEstoq;
}
