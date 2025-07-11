package Interfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Backend.Notificador;

public class MenuAcess extends JPanel {

    private final SistemaPrincipal framePai;

    public MenuAcess(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        jLAcessar.setForeground(Color.BLACK);
        jLAcessar.setFont(new Font("Segoe UI", Font.BOLD, 32));

        SistemaPrincipal.estilizarBotaoMenor(jBCEstoq);
        SistemaPrincipal.estilizarBotaoMenor(jBVendas);
        SistemaPrincipal.estilizarBotaoMenor(jBFiados);
        SistemaPrincipal.estilizarBotaoMenor(jBGastos);
        SistemaPrincipal.estilizarBotaoMenor(jBGeral);
        SistemaPrincipal.estilizarBotaoMaior(jBVoltar);
        SistemaPrincipal.estilizarBotaoMaior(jBNotif);
    }

    private void initComponents() {
        jLAcessar = new javax.swing.JLabel();
        jLCEstoq = new javax.swing.JLabel();
        jLVendas = new javax.swing.JLabel();
        jLFiados = new javax.swing.JLabel();
        jLGastos = new javax.swing.JLabel();
        jLGeral = new javax.swing.JLabel();

        jBCEstoq = new javax.swing.JButton();
        jBVendas = new javax.swing.JButton();
        jBFiados = new javax.swing.JButton();
        jBGastos = new javax.swing.JButton();
        jBGeral = new javax.swing.JButton();
        jBVoltar = new javax.swing.JButton("VOLTAR");
        jBNotif = new javax.swing.JButton("NOTIFICAÇÕES");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;

        jLAcessar.setText("O QUE DESEJA ACESSAR?");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(jLAcessar, gbc);

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);
        gbc.gridwidth = 1;

        gbc.gridy = 1;
        gbc.gridx = 0;
        jLCEstoq.setText("CONTROLE DE ESTOQUE");
        jLCEstoq.setFont(fonteLabel);
        this.add(jLCEstoq, gbc);
        gbc.gridx = 1;
        this.add(jBCEstoq, gbc);
        jBCEstoq.addActionListener(e -> framePai.trocarTela(new MenuEstoq(framePai)));

        gbc.gridy = 2;
        gbc.gridx = 0;
        jLVendas.setText("CONTROLE DE VENDAS");
        jLVendas.setFont(fonteLabel);
        this.add(jLVendas, gbc);
        gbc.gridx = 1;
        this.add(jBVendas, gbc);
        jBVendas.addActionListener(e -> framePai.trocarTela(new MenuVendas(framePai)));

        gbc.gridy = 3;
        gbc.gridx = 0;
        jLFiados.setText("CONTROLE DE FIADOS");
        jLFiados.setFont(fonteLabel);
        this.add(jLFiados, gbc);
        gbc.gridx = 1;
        this.add(jBFiados, gbc);
        jBFiados.addActionListener(e -> framePai.trocarTela(new MenuFiados(framePai)));

        gbc.gridy = 4;
        gbc.gridx = 0;
        jLGastos.setText("GASTOS");
        jLGastos.setFont(fonteLabel);
        this.add(jLGastos, gbc);
        gbc.gridx = 1;
        this.add(jBGastos, gbc);
        jBGastos.addActionListener(e -> framePai.trocarTela(new MenuGastos(framePai)));

        gbc.gridy = 5;
        gbc.gridx = 0;
        jLGeral.setText("BALANÇO GERAL");
        jLGeral.setFont(fonteLabel);
        this.add(jLGeral, gbc);
        gbc.gridx = 1;
        this.add(jBGeral, gbc);
        jBGeral.addActionListener(e -> framePai.trocarTela(new MenuBalanco(framePai)));

        // Botão Voltar no canto inferior esquerdo
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(50, 40, 10, 10);
        this.add(jBVoltar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(50, 20, 10, 40);
        this.add(jBNotif, gbc);

        jBVoltar.addActionListener(e -> framePai.montarInterface());

        // 🎯 AÇÃO DO BOTÃO DE NOTIFICAÇÕES
        jBNotif.addActionListener(e -> {
            List<String> avisos = Notificador.gerarNotificacoes();

            JPanel painel = new JPanel(new GridBagLayout());
            painel.setBackground(new Color(156, 156, 156));
            GridBagConstraints gbcNotif = new GridBagConstraints();
            gbcNotif.gridx = 0;
            gbcNotif.gridy = 0;
            gbcNotif.insets = new Insets(10, 10, 10, 10);
            gbcNotif.anchor = GridBagConstraints.WEST;

            if (avisos.isEmpty()) {
                JLabel semAvisos = new JLabel("Sem notificações no momento.");
                semAvisos.setFont(new Font("Segoe UI", Font.ITALIC, 18));
                painel.add(semAvisos, gbcNotif);
            } else {
                for (String aviso : avisos) {
                    JLabel label = new JLabel("• " + aviso);
                    label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                    painel.add(label, gbcNotif);
                    gbcNotif.gridy++;
                }
            }

            JDialog popUp = framePai.criarPopUp("Notificações", painel, 700, 400);
            popUp.setVisible(true);
        });
    }

    private javax.swing.JLabel jLAcessar;
    private javax.swing.JLabel jLCEstoq;
    private javax.swing.JLabel jLVendas;
    private javax.swing.JLabel jLFiados;
    private javax.swing.JLabel jLGastos;
    private javax.swing.JLabel jLGeral;
    private javax.swing.JButton jBCEstoq;
    private javax.swing.JButton jBVendas;
    private javax.swing.JButton jBFiados;
    private javax.swing.JButton jBGastos;
    private javax.swing.JButton jBGeral;
    private javax.swing.JButton jBVoltar;
    private javax.swing.JButton jBNotif;
}
