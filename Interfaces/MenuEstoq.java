package Interfaces;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.*;

import Backend.Produto;
import Backend.ProdutoPerecivel;
import Backend.Estoque;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

        SistemaPrincipal.estilizarBotaoMaior(jBVoltar);
        SistemaPrincipal.estilizarBotaoMaior(jBPopUpAdicionar);
        SistemaPrincipal.estilizarBotaoMaior(jBPopUpListar);
        SistemaPrincipal.estilizarBotaoMaior(jBpopUpExcluir);
        SistemaPrincipal.estilizarBotaoMaior(jBExportarLista);

        jBPopUpAdicionar.addActionListener(e -> abrirAdicionarProduto());
        jBPopUpListar.addActionListener(e -> abrirListarProdutos());
        jBpopUpExcluir.addActionListener(e -> abrirExcluirProduto());
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

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLListar, gbc);
        gbc.gridx = 1;
        jBPopUpListar.setText(" ");
        add(jBPopUpListar, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLExcluir, gbc);
        gbc.gridx = 1;
        jBpopUpExcluir.setText(" ");
        add(jBpopUpExcluir, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(40, 40, 10, 20);
        this.add(jBExportarLista, gbc);

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

    private void abrirAdicionarProduto() {
        JPanel panelzao = new JPanel(new GridBagLayout());
        panelzao.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // margens menores para equilíbrio
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Campos
        JLabel labelNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField(20);
        JLabel labelPreco = new JLabel("Preço:");
        JTextField campoPreco = new JTextField(10);
        JLabel labelQuantidade = new JLabel("Quantidade:");
        JTextField campoQuantidade = new JTextField(10);
        JCheckBox checkPerecivel = new JCheckBox("Produto Perecível");
        JLabel labelValidade = new JLabel("Validade (se perecível):");
        JTextField campoValidade = new JTextField(10);

        // Inicialmente invisível validade
        labelValidade.setVisible(false);
        campoValidade.setVisible(false);

        // Adiciona os componentes com posicionamento
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelzao.add(labelNome, gbc);
        gbc.gridx = 1;
        panelzao.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelzao.add(labelPreco, gbc);
        gbc.gridx = 1;
        panelzao.add(campoPreco, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelzao.add(labelQuantidade, gbc);
        gbc.gridx = 1;
        panelzao.add(campoQuantidade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelzao.add(checkPerecivel, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panelzao.add(labelValidade, gbc);
        gbc.gridx = 1;
        panelzao.add(campoValidade, gbc);

        // Botões
        JButton confirmar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(confirmar);
        SistemaPrincipal.estilizarBotaoMaior(cancelar);

        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botão Cancelar - à esquerda
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelzao.add(cancelar, gbc);

        // Botão Confirmar - à direita
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panelzao.add(confirmar, gbc);
        
        Dimension botaoTamanho = new Dimension(120, 30); // ou o tamanho que preferires
        confirmar.setPreferredSize(botaoTamanho);
        cancelar.setPreferredSize(botaoTamanho);

        // Listener para checkbox mostrar/esconder validade
        checkPerecivel.addActionListener(e -> {
            boolean selecionado = checkPerecivel.isSelected();
            labelValidade.setVisible(selecionado);
            campoValidade.setVisible(selecionado);
            panelzao.revalidate();
            panelzao.repaint();
        });

        JDialog popUp = framePai.criarPopUp("ADICIONAR PRODUTO", panelzao, 450, 280);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        confirmar.addActionListener(e -> {
            try {
                String nome = campoNome.getText();
                int qtd = Integer.parseInt(campoQuantidade.getText());
                double preco = Double.parseDouble(campoPreco.getText());

                if (checkPerecivel.isSelected()) {
                    String validadeStr = campoValidade.getText();
                    LocalDate dataValidade = LocalDate.parse(validadeStr, formatter);
                    ProdutoPerecivel perecivel = new ProdutoPerecivel(nome, qtd, preco, dataValidade);
                    Estoque.getProdutos().add(perecivel);
                } else {
                    Estoque.getProdutos().add(new Produto(nome, qtd, preco));
                }

                popUp.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço ou quantidade inválidos!", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelar.addActionListener(e -> popUp.dispose());
        popUp.setVisible(true);
    }

    private void abrirListarProdutos() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(156, 156, 156));

        JPanel cabecalho = new JPanel(new GridLayout(1, 6));
        cabecalho.setBackground(new Color(100, 100, 100));
        String[] titulos = { "ID", "NOME DO PRODUTO", "PREÇO", "QUANTIDADE", "PERECÍVEL", "VALIDADE" };

        for (String titulo : titulos) {
            JLabel label = new JLabel(titulo, JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.DARK_GRAY);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Segoe UI", Font.BOLD, 14));
            cabecalho.add(label);
        }
        panel.add(cabecalho);

        for (int i = 0; i < Estoque.getProdutos().size(); i++) {
            Produto p = Estoque.getProdutos().get(i);
            JPanel linha = new JPanel(new GridLayout(1, 6));
            linha.setBackground(new Color(180, 180, 180));

            linha.add(new JLabel(String.valueOf(p.getCodigo())));
            linha.add(new JLabel(p.getNome()));
            linha.add(new JLabel(String.format("R$ %.2f", p.getPreco())));
            linha.add(new JLabel(String.valueOf(p.getQuantidade())));

            if (p instanceof ProdutoPerecivel perecivel) {
                linha.add(new JLabel("Sim"));
                DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                linha.add(new JLabel(formato.format(perecivel.getDataDeValidade())));

            } else {
                linha.add(new JLabel("Não"));
                linha.add(new JLabel("-"));
            }

            panel.add(linha);
        }

        JScrollPane painelScroll = new JScrollPane(panel);
        painelScroll.getVerticalScrollBar().setUnitIncrement(16);

        JDialog popUpListar = framePai.criarPopUp("LISTA PRODUTOS", painelScroll, 800, 400);
        popUpListar.setVisible(true);
    }

    private void abrirExcluirProduto() {
        JPanel panelzao = new JPanel();
        panelzao.setLayout(new GridBagLayout());
        panelzao.setBackground(new Color(156, 156, 156));

        JLabel labelProduto = new JLabel("PRODUTO");
        labelProduto.setBackground(new Color(30, 144, 255));
        labelProduto.setForeground(Color.WHITE);
        panelzao.add(labelProduto);

        JDialog popUpExcluir = framePai.criarPopUp("EXCLUIR PRODUTO", panelzao, 600, 300);
        popUpExcluir.setVisible(true);
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
