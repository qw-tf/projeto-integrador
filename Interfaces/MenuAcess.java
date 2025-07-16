package Interfaces;

import Backend.GerenciadorSenha;
import Backend.Notificador;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.*;

public class MenuAcess extends JPanel {

    private final SistemaPrincipal framePai;
    private JDialog popUpNotificacoes = null;
    private JPanel painelNotificacoes;
    private JScrollPane scrollNotificacoes;
    private JButton jBEngrenagem;

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
        SistemaPrincipal.estilizarBotaoMaior(jBEngrenagem);

        iniciarAtualizacaoAutomatica();
    }

    private void initComponents() {
        jLAcessar = new JLabel("O QUE DESEJA ACESSAR?");
        jLCEstoq = new JLabel("CONTROLE DE ESTOQUE");
        jLVendas = new JLabel("CONTROLE DE VENDAS");
        jLFiados = new JLabel("CONTROLE DE FIADOS");
        jLGastos = new JLabel("GASTOS");
        jLGeral = new JLabel("BALANÇO GERAL");

        jBCEstoq = new JButton();
        jBVendas = new JButton();
        jBFiados = new JButton();
        jBGastos = new JButton();
        jBGeral = new JButton();
        jBVoltar = new JButton("VOLTAR");
        jBNotif = new JButton("NOTIFICAÇÕES");
        jBEngrenagem = new JButton("Opções"); // ⚙ = engrenagem unicode
        jBEngrenagem.setPreferredSize(new Dimension(100, 60));
        jBEngrenagem.setFont(new Font("Segoe UI", Font.BOLD, 20));

        jBEngrenagem.addActionListener(e -> {
            JPopupMenu menu = new JPopupMenu();

            JMenuItem opcaoSenha = new JMenuItem("Mudar senha");
            JMenuItem opcaoDica = new JMenuItem("Definir dica");
            JMenuItem opcaoToken = new JMenuItem("Definir token");

            opcaoSenha.addActionListener(ev -> abrirDialogMudarSenha());
            opcaoDica.addActionListener(ev -> abrirDialogDica());
            opcaoToken.addActionListener(ev -> abrirDialogToken());

            menu.add(opcaoSenha);
            menu.add(opcaoDica);
            menu.add(opcaoToken);

            menu.show(jBEngrenagem, 0, jBEngrenagem.getHeight());
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 40, 20, 40);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(jLAcessar, gbc);

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);
        gbc.gridwidth = 1;

        gbc.gridy = 1;
        gbc.gridx = 0;
        jLCEstoq.setFont(fonteLabel);
        add(jLCEstoq, gbc);
        gbc.gridx = 1;
        add(jBCEstoq, gbc);
        jBCEstoq.addActionListener(e -> framePai.trocarTela(new MenuEstoq(framePai)));

        gbc.gridy = 2;
        gbc.gridx = 0;
        jLVendas.setFont(fonteLabel);
        add(jLVendas, gbc);
        gbc.gridx = 1;
        add(jBVendas, gbc);
        jBVendas.addActionListener(e -> framePai.trocarTela(new MenuVendas(framePai)));

        gbc.gridy = 3;
        gbc.gridx = 0;
        jLFiados.setFont(fonteLabel);
        add(jLFiados, gbc);
        gbc.gridx = 1;
        add(jBFiados, gbc);
        jBFiados.addActionListener(e -> framePai.trocarTela(new MenuFiados(framePai)));

        gbc.gridy = 4;
        gbc.gridx = 0;
        jLGastos.setFont(fonteLabel);
        add(jLGastos, gbc);
        gbc.gridx = 1;
        add(jBGastos, gbc);
        jBGastos.addActionListener(e -> framePai.trocarTela(new MenuGastos(framePai)));

        gbc.gridy = 5;
        gbc.gridx = 0;
        jLGeral.setFont(fonteLabel);
        add(jLGeral, gbc);
        gbc.gridx = 1;
        add(jBGeral, gbc);
        jBGeral.addActionListener(e -> framePai.trocarTela(new MenuBalanco(framePai)));

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(50, 40, 10, 10);
        add(jBVoltar, gbc);
        jBVoltar.addActionListener(e -> framePai.montarInterface());

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(50, 20, 10, 80);
        add(jBNotif, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(50, 20, 10, 10);
        add(jBEngrenagem, gbc);

        jBNotif.addActionListener(e -> abrirPopUpNotificacoes());
    }

    private void abrirDialogMudarSenha() {
        JPanel painel = new JPanel(new GridLayout(3, 2, 10, 10));
        painel.setBackground(new Color(156, 156, 156));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPasswordField atual = new JPasswordField();
        JPasswordField nova = new JPasswordField();
        JPasswordField confirmar = new JPasswordField();

        painel.add(new JLabel("Senha atual:"));
        painel.add(atual);
        painel.add(new JLabel("Nova senha:"));
        painel.add(nova);
        painel.add(new JLabel("Confirmar nova:"));
        painel.add(confirmar);

        int res = JOptionPane.showConfirmDialog(framePai, painel, "Alterar Senha", JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) {
            String senhaAtual = new String(atual.getPassword());
            String novaSenha = new String(nova.getPassword());
            String confirmarSenha = new String(confirmar.getPassword());

            String senhaSalva = GerenciadorSenha.carregarSenha();
            if (!senhaAtual.equals(senhaSalva)) {
                JOptionPane.showMessageDialog(framePai, "Senha atual incorreta.");
                return;
            }

            if (!novaSenha.equals(confirmarSenha)) {
                JOptionPane.showMessageDialog(framePai, "Nova senha não coincide.");
                return;
            }

            GerenciadorSenha.salvarSenha(novaSenha);
            JOptionPane.showMessageDialog(framePai, "Senha atualizada!");
        }
    }

    private void abrirDialogDica() {
        String dicaAtual = GerenciadorSenha.carregarDica();
        String novaDica = JOptionPane.showInputDialog(framePai, "Digite uma dica de recuperação:", dicaAtual);
        if (novaDica != null && !novaDica.trim().isEmpty()) {
            GerenciadorSenha.salvarDica(novaDica.trim());
            JOptionPane.showMessageDialog(framePai, "Dica salva com sucesso.");
        }
    }

    private void abrirDialogToken() {
        String tokenAtual = GerenciadorSenha.carregarToken();
        String novoToken = JOptionPane.showInputDialog(framePai, "Digite um token de segurança:", tokenAtual);
        if (novoToken != null && !novoToken.trim().isEmpty()) {
            GerenciadorSenha.salvarToken(novoToken.trim());
            JOptionPane.showMessageDialog(framePai, "Token salvo com sucesso.");
        }
    }

    private void abrirPopUpNotificacoes() {
        if (popUpNotificacoes != null && popUpNotificacoes.isVisible()) {
            popUpNotificacoes.dispose();
            return;
        }

        painelNotificacoes = new JPanel();
        painelNotificacoes.setLayout(new BoxLayout(painelNotificacoes, BoxLayout.Y_AXIS));
        painelNotificacoes.setBackground(Color.BLACK);

        scrollNotificacoes = new JScrollPane(painelNotificacoes);
        scrollNotificacoes.setPreferredSize(new Dimension(400, 300));
        scrollNotificacoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollNotificacoes.getViewport().setBackground(Color.BLACK);
        scrollNotificacoes.setBackground(Color.BLACK);

        popUpNotificacoes = framePai.criarPopUpNotificacoesTopoEsquerdo(scrollNotificacoes);
        popUpNotificacoes.setVisible(true);

        atualizarNotificacoes();

        popUpNotificacoes.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                popUpNotificacoes = null;
            }

            @Override
            public void windowClosed(WindowEvent e) {
                popUpNotificacoes = null;
            }
        });
    }

    private void atualizarNotificacoes() {
        if (painelNotificacoes == null)
            return;

        painelNotificacoes.removeAll();

        List<String> avisos = Notificador.gerarNotificacoes();

        if (avisos.isEmpty()) {
            JLabel label = new JLabel("Sem notificacoes no momento.");
            label.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            label.setForeground(Color.WHITE);
            label.setBackground(Color.BLACK);
            label.setOpaque(true);
            painelNotificacoes.add(label);
        } else {
            for (String aviso : avisos) {
                JTextArea area = new JTextArea(aviso);
                area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setEditable(false);
                area.setOpaque(true);
                area.setForeground(Color.WHITE);
                area.setBackground(Color.BLACK);
                area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                painelNotificacoes.add(area);
            }
        }

        painelNotificacoes.revalidate();
        painelNotificacoes.repaint();
    }

    private void iniciarAtualizacaoAutomatica() {
        new javax.swing.Timer(5, e -> {
            if (popUpNotificacoes != null && popUpNotificacoes.isVisible()) {
                atualizarNotificacoes();
            }
        }).start();
    }

    private JLabel jLAcessar, jLCEstoq, jLVendas, jLFiados, jLGastos, jLGeral;
    private JButton jBCEstoq, jBVendas, jBFiados, jBGastos, jBGeral, jBVoltar, jBNotif;
}