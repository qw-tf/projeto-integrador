package Interfaces;
import java.awt.*;
import javax.swing.*;

public class MenuFiados extends JPanel {

    private final SistemaPrincipal framePai;

    public MenuFiados(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

        jLAdicionarFiados.setFont(fonteLabel);
        jLListarFiados.setFont(fonteLabel);
        jLExcluirFiados.setFont(fonteLabel);

        framePai.estilizarBotaoMaior(jBVoltar);
        framePai.estilizarBotaoMaior(jBPopUpFiadosAdicionarP);
        framePai.estilizarBotaoMaior(jBPopUpFiadosListarP);
        framePai.estilizarBotaoMaior(jBPopUpFiadosQuitarP);

    }

    private void initComponents() {
        jLAdicionarFiados = new JLabel("ADICIONAR FIADOS");
        jLListarFiados = new JLabel("LISTAR FIADOS");
        jLExcluirFiados = new JLabel("QUITAR FIADOS");
        jLTituloFiados = new JLabel("MENU DE FIADOS");

        jBPopUpFiadosAdicionarP = new JButton();
        jBPopUpFiadosListarP = new JButton();
        jBPopUpFiadosQuitarP = new JButton();
        jBVoltar = new JButton("VOLTAR");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;

        gbc.gridy = 0;
        gbc.gridx = 0;
        jLTituloFiados.setFont(new Font("Segoe UI", Font.BOLD, 32));
        add(jLTituloFiados, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(jLAdicionarFiados, gbc);
        gbc.gridx = 1;
        jBPopUpFiadosAdicionarP.setText(" ");
        add(jBPopUpFiadosAdicionarP, gbc);
        // jBPopUpAdicionar.addActionListener(e -> framePai.abrirAdicionarProduto());

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLListarFiados, gbc);
        gbc.gridx = 1;
        jBPopUpFiadosListarP.setText(" ");
        add(jBPopUpFiadosListarP, gbc);
        // jBPopUpListar.addActionListener(e -> framePai.abrirListarProdutos());

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLExcluirFiados, gbc);
        gbc.gridx = 1;
        jBPopUpFiadosQuitarP.setText(" ");
        add(jBPopUpFiadosQuitarP, gbc);
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
    private javax.swing.JButton jBPopUpFiadosAdicionarP;
    private javax.swing.JButton jBPopUpFiadosListarP;
    private javax.swing.JButton jBPopUpFiadosQuitarP;
    private JLabel jLAdicionarFiados;
    private JLabel jLListarFiados;
    private JLabel jLExcluirFiados;
    private JLabel jLTituloFiados;
}
