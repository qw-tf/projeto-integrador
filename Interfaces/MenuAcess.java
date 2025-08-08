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
        jBEngrenagem = new JButton("OPÇÕES"); 
        jBEngrenagem.setPreferredSize(new Dimension(99, 38));
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

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        painelRodape.setBackground(new Color(156, 156, 156));
        painelRodape.add(jBVoltar);
        painelRodape.add(jBNotif);
        painelRodape.add(jBEngrenagem);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 5, 10, 10);
        add(painelRodape, gbc);

        jBVoltar.addActionListener(e -> framePai.montarInterface());
        jBNotif.addActionListener(e -> abrirPopUpNotificacoes());
    }

    private void abrirDialogMudarSenha() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(156, 156, 156));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPasswordField atual = new JPasswordField(15);
        JPasswordField nova = new JPasswordField(15);
        JPasswordField confirmar = new JPasswordField(15);

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 16);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 16);

        JLabel lblAtual = new JLabel("Senha atual:");
        JLabel lblNova = new JLabel("Nova senha:");
        JLabel lblConfirmar = new JLabel("Confirmar nova:");

        lblAtual.setFont(fonteLabel);
        lblNova.setFont(fonteLabel);
        lblConfirmar.setFont(fonteLabel);

        atual.setFont(fonteCampo);
        nova.setFont(fonteCampo);
        confirmar.setFont(fonteCampo);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(lblAtual, gbc);
        gbc.gridx = 1;
        painel.add(atual, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        painel.add(lblNova, gbc);
        gbc.gridx = 1;
        painel.add(nova, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painel.add(lblConfirmar, gbc);
        gbc.gridx = 1;
        painel.add(confirmar, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setBackground(new Color(156, 156, 156));

        JButton btnOk = new JButton("Confirmar");
        JButton btnCancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(btnOk);
        SistemaPrincipal.estilizarBotaoMaior(btnCancelar);

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnOk);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(painelBotoes, gbc);

        JDialog popup = framePai.criarPopUp("Alterar Senha", painel, 380, 280);

        btnCancelar.addActionListener(e -> popup.dispose());

        btnOk.addActionListener(e -> {
            String senhaAtual = new String(atual.getPassword());
            String novaSenha = new String(nova.getPassword());
            String confirmarSenha = new String(confirmar.getPassword());

            String senhaSalva = GerenciadorSenha.carregarSenha();
            if (!senhaAtual.equals(senhaSalva)) {
                JOptionPane.showMessageDialog(popup, "Senha atual incorreta.", "ERRO", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!novaSenha.equals(confirmarSenha)) {
                JOptionPane.showMessageDialog(popup, "Nova senha não coincide.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            GerenciadorSenha.salvarSenha(novaSenha);
            JOptionPane.showMessageDialog(popup, "Senha atualizada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            popup.dispose();
        });

        popup.setLocationRelativeTo(framePai);
        popup.setVisible(true);
    }

    private void abrirDialogDica() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(156, 156, 156));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblDica = new JLabel("Dica de recuperação:");
        JTextField campoDica = new JTextField(20);
        campoDica.setText(GerenciadorSenha.carregarDica());

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 16);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 16);

        lblDica.setFont(fonteLabel);
        campoDica.setFont(fonteCampo);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(lblDica, gbc);
        gbc.gridx = 1;
        painel.add(campoDica, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setBackground(new Color(156, 156, 156));

        JButton btnOk = new JButton("Confirmar");
        JButton btnCancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(btnOk);
        SistemaPrincipal.estilizarBotaoMaior(btnCancelar);

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnOk);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(painelBotoes, gbc);

        JDialog popup = framePai.criarPopUp("Definir Dica", painel, 380, 180);

        btnCancelar.addActionListener(e -> popup.dispose());

        btnOk.addActionListener(e -> {
            String novaDica = campoDica.getText().trim();
            if (novaDica.isEmpty()) {
                JOptionPane.showMessageDialog(popup, "A dica não pode ficar vazia.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            GerenciadorSenha.salvarDica(novaDica);
            JOptionPane.showMessageDialog(popup, "Dica salva com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            popup.dispose();
        });

        popup.setLocationRelativeTo(framePai);
        popup.setVisible(true);
    }

    private void abrirDialogToken() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(156, 156, 156));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblToken = new JLabel("Token de segurança:");
        JTextField campoToken = new JTextField(20);
        campoToken.setText(GerenciadorSenha.carregarToken());

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 16);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 16);

        lblToken.setFont(fonteLabel);
        campoToken.setFont(fonteCampo);

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(lblToken, gbc);
        gbc.gridx = 1;
        painel.add(campoToken, gbc);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        painelBotoes.setBackground(new Color(156, 156, 156));

        JButton btnOk = new JButton("Confirmar");
        JButton btnCancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(btnOk);
        SistemaPrincipal.estilizarBotaoMaior(btnCancelar);

        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnOk);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painel.add(painelBotoes, gbc);

        JDialog popup = framePai.criarPopUp("Definir Token", painel, 380, 180);

        btnCancelar.addActionListener(e -> popup.dispose());

        btnOk.addActionListener(e -> {
            String novoToken = campoToken.getText().trim();
            if (novoToken.isEmpty()) {
                JOptionPane.showMessageDialog(popup, "O token não pode ficar vazio.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            GerenciadorSenha.salvarToken(novoToken);
            JOptionPane.showMessageDialog(popup, "Token salvo com sucesso.", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            popup.dispose();
        });

        popup.setLocationRelativeTo(framePai);
        popup.setVisible(true);
    }

    private void abrirPopUpNotificacoes() {

        if (popUpNotificacoes != null && popUpNotificacoes.isShowing()) {

            popUpNotificacoes.toFront();
            popUpNotificacoes.requestFocus();
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

        atualizarNotificacoes();

        popUpNotificacoes.setVisible(true);
    }

    private void atualizarNotificacoes() {
        if (painelNotificacoes == null)
            return;

        painelNotificacoes.removeAll();

        List<String> avisos = Notificador.gerarNotificacoes();

        if (avisos.isEmpty()) {
            JLabel label = new JLabel("Sem notificações no momento.");
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
        new javax.swing.Timer(5000, e -> {
            if (popUpNotificacoes != null && popUpNotificacoes.isVisible()) {
                System.out.println("Atualizando notificações...");
                atualizarNotificacoes();
            }
        }).start();
    }

    private JLabel jLAcessar, jLCEstoq, jLVendas, jLFiados, jLGastos, jLGeral;
    private JButton jBCEstoq, jBVendas, jBFiados, jBGastos, jBGeral, jBVoltar, jBNotif;
}
