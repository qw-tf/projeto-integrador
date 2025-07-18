package Interfaces;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

import Backend.Fiado;
import Backend.FiadoRepositorio;
import Backend.Venda;
import Backend.RegistroVendas;
import Banco.FiadoDAO;

public class MenuFiados extends JPanel {

    private final SistemaPrincipal framePai;
    private JTable tabelaFiados;
    private JDialog popUpListar;

    private JButton jBVoltar;
    private JButton jBPopUpAdicionar;
    private JButton jBPopUpListar;
    private JButton jBPopUpQuitar;
    private JLabel jLAdicionar;
    private JLabel jLListar;
    private JLabel jLQuitar;
    private JLabel jLTituloFiados;

    public MenuFiados(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

        jLAdicionar.setFont(fonteLabel);
        jLListar.setFont(fonteLabel);
        jLQuitar.setFont(fonteLabel);

        SistemaPrincipal.estilizarBotaoMaior(jBVoltar);
        SistemaPrincipal.estilizarBotaoMenor(jBPopUpAdicionar);
        SistemaPrincipal.estilizarBotaoMenor(jBPopUpListar);
        SistemaPrincipal.estilizarBotaoMenor(jBPopUpQuitar);

        jBPopUpAdicionar.addActionListener(e -> abrirAdicionarFiado());
        jBPopUpListar.addActionListener(e -> abrirListarFiados());
        jBPopUpQuitar.addActionListener(e -> abrirQuitarFiado());
    }

    private void initComponents() {
        jLAdicionar = new JLabel("ADICIONAR FIADO");
        jLListar = new JLabel("LISTAR FIADOS");
        jLQuitar = new JLabel("QUITAR FIADO");
        jLTituloFiados = new JLabel("MENU DE FIADOS");

        jBPopUpAdicionar = new JButton();
        jBPopUpListar = new JButton();
        jBPopUpQuitar = new JButton();
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
        add(jLAdicionar, gbc);
        gbc.gridx = 1;
        add(jBPopUpAdicionar, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(jLListar, gbc);
        gbc.gridx = 1;
        add(jBPopUpListar, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(jLQuitar, gbc);
        gbc.gridx = 1;
        add(jBPopUpQuitar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(40, 30, 10, 10);
        add(jBVoltar, gbc);

        jBVoltar.addActionListener(e -> framePai.trocarTela(new MenuAcess(framePai)));
    }

    private void abrirAdicionarFiado() {
        JPanel panelzao = new JPanel(new GridBagLayout());
        panelzao.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelNomeCliente = new JLabel("Nome Cliente:");
        JTextField campoNomeCliente = new JTextField(20);

        JLabel labelVenda = new JLabel("Venda:");
        JComboBox<String> comboVendas = new JComboBox<>();

        // Ordena as vendas da mais recente para a mais antiga (por ID decrescente)
        List<Venda> vendasOrdenadas = RegistroVendas.getTodasVendas().stream()
                .sorted(Comparator.comparingInt(Venda::getId).reversed())
                .collect(Collectors.toList());

        for (Venda v : vendasOrdenadas) {
            // Exibir: ID, data, total e resumo dos produtos
            String texto = String.format("ID: %d | %s | R$ %.2f | %s",
                    v.getId(),
                    v.getData() != null ? v.getData().toString() : "Data N/D",
                    v.getTotal(),
                    v.getResumoProdutos());
            comboVendas.addItem(texto);
        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelzao.add(labelNomeCliente, gbc);
        gbc.gridx = 1;
        panelzao.add(campoNomeCliente, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelzao.add(labelVenda, gbc);
        gbc.gridx = 1;
        panelzao.add(comboVendas, gbc);

        JButton confirmar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(confirmar);
        SistemaPrincipal.estilizarBotaoMaior(cancelar);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panelzao.add(cancelar, gbc);
        gbc.gridx = 1;
        panelzao.add(confirmar, gbc);

        JDialog popUp = framePai.criarPopUp("ADICIONAR FIADO", panelzao, 500, 300);

        confirmar.addActionListener(e -> {
            try {
                String nomeCliente = campoNomeCliente.getText().trim();
                if (nomeCliente.isEmpty()) {
                    JOptionPane.showMessageDialog(panelzao, "O nome do cliente não pode ficar vazio!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (comboVendas.getSelectedIndex() == -1) {
                    JOptionPane.showMessageDialog(panelzao, "Nenhuma venda disponível para associar ao fiado.", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Pega o ID da venda selecionada no combo (string: "ID: x | ...")
                String selecionado = (String) comboVendas.getSelectedItem();
                int idVendaSelecionada = Integer
                        .parseInt(selecionado.substring(4, selecionado.indexOf('|') - 1).trim());

                Venda venda = null;
                for (Venda v : RegistroVendas.getTodasVendas()) {
                    if (v.getId() == idVendaSelecionada) {
                        venda = v;
                        break;
                    }
                }

                if (venda == null) {
                    JOptionPane.showMessageDialog(panelzao, "Venda selecionada não encontrada!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Fiado fiado = new Fiado(nomeCliente, venda);
                FiadoRepositorio.adicionarFiado(fiado);
                FiadoDAO.inserirFiado(fiado); // Salva no banco também!!!

                JOptionPane.showMessageDialog(panelzao, "Fiado registrado com sucesso!", "SUCESSO",
                        JOptionPane.INFORMATION_MESSAGE);
                popUp.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelzao, "Erro no formato do número.", "ERRO",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelzao, "Erro: " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelar.addActionListener(e -> popUp.dispose());

        popUp.setVisible(true);
    }

    private Object[][] montarDadosTabela() {
        List<Fiado> fiados = FiadoRepositorio.getFiados();

        Object[][] dados = new Object[fiados.size()][5];
        for (int i = 0; i < fiados.size(); i++) {
            Fiado f = fiados.get(i);
            dados[i][0] = f.getIdFiado();
            dados[i][1] = f.getIdVenda();
            dados[i][2] = f.getNomeCliente();
            dados[i][3] = f.getValorRestante();
            dados[i][4] = f.isQuitado() ? "Sim" : "Não";
        }
        return dados;
    }

    private void abrirListarFiados() {
        String[] colunas = { "ID", "ID VENDA", "NOME DO CLIENTE", "VALOR RESTANTE", "QUITADO" };
        Object[][] dados = montarDadosTabela();

        DefaultTableModel modelo = new DefaultTableModel(dados, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaFiados = new JTable(modelo);
        tabelaFiados.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabelaFiados.setRowHeight(22);

        JTableHeader header = tabelaFiados.getTableHeader();
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 32));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString(), JLabel.CENTER);
                label.setOpaque(true);
                label.setBackground(Color.DARK_GRAY);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Segoe UI", Font.BOLD, 14));
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabelaFiados);
        scrollPane.getViewport().setBackground(new Color(156, 156, 156));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(156, 156, 156));
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        if (popUpListar != null && popUpListar.isVisible()) {
            popUpListar.toFront();
        } else {
            popUpListar = framePai.criarPopUp("LISTAR FIADOS", painelPrincipal, 800, 450);
            popUpListar.setVisible(true);
        }
    }

    private void abrirQuitarFiado() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(156, 156, 156));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelBusca = new JLabel("Nome ou parte do nome do cliente:");
        labelBusca.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JTextField campoBusca = new JTextField(20);
        campoBusca.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton btnConsultar = new JButton("Consultar Fiados");
        JButton btnQuitarTotal = new JButton("Quitar Totalmente");
        JButton btnQuitarParcial = new JButton("Quitar Parcialmente");
        JButton btnCancelar = new JButton("Cancelar");

        btnQuitarTotal.setEnabled(false);
        btnQuitarParcial.setEnabled(false);

        JTextArea areaResumo = new JTextArea(10, 35);
        areaResumo.setEditable(false);
        areaResumo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaResumo.setLineWrap(true);
        areaResumo.setWrapStyleWord(true);
        JScrollPane scrollResumo = new JScrollPane(areaResumo);
        scrollResumo.setBorder(BorderFactory.createTitledBorder("Resumo do Fiado"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        painel.add(labelBusca, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        painel.add(campoBusca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JPanel painelBotoesConsulta = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelBotoesConsulta.setBackground(new Color(156, 156, 156));
        painelBotoesConsulta.add(btnConsultar);
        painel.add(painelBotoesConsulta, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1;
        painel.add(scrollResumo, gbc);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        JPanel painelBotoesAcao = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelBotoesAcao.setBackground(new Color(156, 156, 156));
        painelBotoesAcao.add(btnQuitarTotal);
        painelBotoesAcao.add(btnQuitarParcial);
        painelBotoesAcao.add(btnCancelar);
        painel.add(painelBotoesAcao, gbc);

        SistemaPrincipal.estilizarBotaoMaior(btnConsultar);
        SistemaPrincipal.estilizarBotaoMaior(btnQuitarTotal);
        SistemaPrincipal.estilizarBotaoMaior(btnQuitarParcial);
        SistemaPrincipal.estilizarBotaoMaior(btnCancelar);

        final List<Fiado>[] fiadosEncontrados = new List[] { null };
        final Fiado[] fiadoSelecionado = new Fiado[1];
        fiadoSelecionado[0] = null;

        btnConsultar.addActionListener(e -> {
            String textoBusca = campoBusca.getText().trim().toLowerCase();
            if (textoBusca.isEmpty()) {
                JOptionPane.showMessageDialog(painel, "Informe um nome para busca.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<Fiado> encontrados = FiadoRepositorio.getFiados().stream()
                    .filter(f -> f.getNomeCliente().toLowerCase().contains(textoBusca) && !f.isQuitado())
                    .collect(Collectors.toList());

            if (encontrados.isEmpty()) {
                areaResumo.setText("");
                fiadosEncontrados[0] = null;
                fiadoSelecionado[0] = null;
                btnQuitarTotal.setEnabled(false);
                btnQuitarParcial.setEnabled(false);
                JOptionPane.showMessageDialog(painel, "Nenhum fiado encontrado para essa busca.", "Aviso",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            fiadosEncontrados[0] = encontrados;

            // Mostrar lista resumida para escolher
            StringBuilder sb = new StringBuilder();
            sb.append("Fiados encontrados:\n\n");
            for (int i = 0; i < encontrados.size(); i++) {
                Fiado f = encontrados.get(i);
                sb.append(String.format("%d - ID: %d | Cliente: %s | Valor Restante: R$ %.2f\n",
                        i + 1, f.getIdFiado(), f.getNomeCliente(), f.getValorRestante()));
            }
            sb.append("\nColoque o ID do fiado para visualizar detalhes na caixa de texto e habilitar a quitação.");

            areaResumo.setText(sb.toString());

            fiadoSelecionado[0] = null;
            btnQuitarTotal.setEnabled(false);
            btnQuitarParcial.setEnabled(false);
        });

        // Para selecionar o fiado a partir do número digitado na área de texto
        campoBusca.addActionListener(e -> {
            if (fiadosEncontrados[0] == null || fiadosEncontrados[0].isEmpty())
                return;
            String texto = campoBusca.getText().trim();
            try {
                int num = Integer.parseInt(texto);
                if (num >= 1 && num <= fiadosEncontrados[0].size()) {
                    fiadoSelecionado[0] = fiadosEncontrados[0].get(num - 1);
                    Fiado f = fiadoSelecionado[0];
                    StringBuilder detalhes = new StringBuilder();
                    detalhes.append(String.format("Detalhes do Fiado ID %d:\n", f.getIdFiado()));
                    detalhes.append(String.format("Cliente: %s\n", f.getNomeCliente()));
                    detalhes.append(String.format("ID Venda: %d\n", f.getIdVenda()));
                    detalhes.append(String.format("Valor Restante: R$ %.2f\n", f.getValorRestante()));
                    detalhes.append(String.format("Quitado: %s\n\n", f.isQuitado() ? "Sim" : "Não"));
                    detalhes.append("Itens da Venda:\n");

                    Venda venda = RegistroVendas.getTodasVendas().stream()
                            .filter(v -> v.getId() == f.getIdVenda())
                            .findFirst()
                            .orElse(null);

                    if (venda != null) {
                        venda.getItens().forEach(item -> {
                            detalhes.append(String.format("- %s | Qtd: %d | Preço Unit: R$ %.2f | Subtotal: R$ %.2f\n",
                                    item.getProduto().getNome(),
                                    item.getQuantidade(),
                                    item.getProduto().getValorVenda(),
                                    item.getSubtotal()));
                        });
                        detalhes.append(String.format("\nTotal Venda: R$ %.2f", venda.getTotal()));
                    } else {
                        detalhes.append("Venda associada não encontrada.\n");
                    }

                    areaResumo.setText(detalhes.toString());
                    btnQuitarTotal.setEnabled(true);
                    btnQuitarParcial.setEnabled(true);
                }
            } catch (NumberFormatException ex) {
                // Ignorar entrada inválida
            }
        });

        btnQuitarTotal.addActionListener(e -> {
            try {
                if (fiadoSelecionado[0] == null) {
                    JOptionPane.showMessageDialog(painel, "Selecione um fiado válido primeiro.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int resposta = JOptionPane.showConfirmDialog(painel,
                        "Confirma quitar o fiado completamente?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    fiadoSelecionado[0].quitarTotalmente();
                    FiadoDAO.atualizarValorRestante(fiadoSelecionado[0]);
                    JOptionPane.showMessageDialog(painel, "Fiado quitado com sucesso!", "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    popUpListar = null;
                    SwingUtilities.getWindowAncestor(painel).dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(painel, "Erro no banco de dados: " + ex.getMessage(), "ERRO",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painel, "Erro: " + ex.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }

        });

        btnQuitarParcial.addActionListener(e -> {
            if (fiadoSelecionado[0] == null) {
                JOptionPane.showMessageDialog(painel, "Selecione um fiado válido primeiro.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            String valor = JOptionPane.showInputDialog(painel, "Informe o valor a pagar parcialmente:",
                    "Quitar Parcialmente", JOptionPane.PLAIN_MESSAGE);
            if (valor != null) {
                try {
                    double v = Double.parseDouble(valor.replace(',', '.'));
                    if (v <= 0 || v > fiadoSelecionado[0].getValorRestante()) {
                        JOptionPane.showMessageDialog(painel, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    fiadoSelecionado[0].registrarPagamento(v);
                    FiadoDAO.atualizarValorRestante(fiadoSelecionado[0]);
                    JOptionPane.showMessageDialog(painel, "Pagamento parcial registrado com sucesso!", "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    popUpListar = null;
                    SwingUtilities.getWindowAncestor(painel).dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(painel, "Erro no banco de dados: " + ex.getMessage(), "Erro",
                            JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(painel, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(painel, "Erro:" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCancelar.addActionListener(e -> SwingUtilities.getWindowAncestor(painel).dispose());

        JDialog popUp = framePai.criarPopUp("QUITAR FIADO", painel, 600, 450);
        popUp.setVisible(true);
    }
}
