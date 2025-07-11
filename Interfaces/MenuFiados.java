package Interfaces;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

import Backend.Fiado;
import Backend.FiadoRepositorio;

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

        JLabel labelDescricao = new JLabel("Descrição:");
        JTextField campoDescricao = new JTextField(20);

        JLabel labelIdVenda = new JLabel("ID Venda:");
        JTextField campoIdVenda = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelzao.add(labelDescricao, gbc);
        gbc.gridx = 1;
        panelzao.add(campoDescricao, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelzao.add(labelIdVenda, gbc);
        gbc.gridx = 1;
        panelzao.add(campoIdVenda, gbc);

        JButton confirmar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(confirmar);
        SistemaPrincipal.estilizarBotaoMaior(cancelar);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panelzao.add(cancelar, gbc);
        gbc.gridx = 1;
        panelzao.add(confirmar, gbc);

        JDialog popUp = framePai.criarPopUp("ADICIONAR FIADO", panelzao, 400, 200);

        confirmar.addActionListener(e -> {
            try {
                String descricao = campoDescricao.getText().trim();
                int idVenda = Integer.parseInt(campoIdVenda.getText().trim());

                // BUSCA A VENDA EXISTENTE — ESSA É A PARTE NOVA!
                Backend.Venda venda = null;
                for (Backend.Venda v : Backend.RegistroVendas.getTodasVendas()) {
                    if (v.getId() == idVenda) {
                        venda = v;
                        break;
                    }
                }

                if (venda == null) {
                    throw new IllegalArgumentException("Venda não encontrada! Não é possível criar fiado sem venda real!");
                }

                // Remove venda da lista de vendas e cria fiado
                Backend.RegistroVendas.converterVendaEmFiado(venda);
                JOptionPane.showMessageDialog(panelzao, "Fiado registrado com sucesso!", "SUCESSO",
                        JOptionPane.INFORMATION_MESSAGE);
                popUp.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "ERRO",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelar.addActionListener(e -> popUp.dispose());
        popUp.setVisible(true);
    }

    private Object[][] montarDadosTabela() {
        List<Fiado> fiados = FiadoRepositorio.getFiados();

        Object[][] dados = new Object[fiados.size()][6];
        for (int i = 0; i < fiados.size(); i++) {
            Fiado f = fiados.get(i);
            dados[i][0] = f.getIdFiado();
            dados[i][1] = f.getDescricao();
            dados[i][2] = f.getIdCliente();
            dados[i][3] = f.getIdVenda();
            dados[i][4] = f.getValorRestante();
            dados[i][5] = f.isQuitado() ? "Sim" : "Não";
        }
        return dados;
    }

    private void abrirListarFiados() {
        String[] colunas = { "ID", "DESCRIÇÃO", "ID CLIENTE", "ID VENDA", "VALOR RESTANTE", "QUITADO" };
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
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelId = new JLabel("ID Fiado:");
        JTextField campoId = new JTextField(10);

        JCheckBox checkParcial = new JCheckBox("Valor pago parcialmente");
        JLabel labelValor = new JLabel("Valor Pago:");
        JTextField campoValor = new JTextField(10);

        // Por padrão, escondidos
        labelValor.setVisible(false);
        campoValor.setVisible(false);

        // Listener pra alternar visibilidade
        checkParcial.addActionListener(e -> {
            boolean parcial = checkParcial.isSelected();
            labelValor.setVisible(parcial);
            campoValor.setVisible(parcial);
            painel.revalidate();
            painel.repaint();
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(labelId, gbc);
        gbc.gridx = 1;
        painel.add(campoId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        painel.add(checkParcial, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        painel.add(labelValor, gbc);
        gbc.gridx = 1;
        painel.add(campoValor, gbc);

        JButton confirmar = new JButton("Confirmar");
        JButton cancelar = new JButton("Cancelar");
        SistemaPrincipal.estilizarBotaoMaior(confirmar);
        SistemaPrincipal.estilizarBotaoMaior(cancelar);

        gbc.gridy = 3;
        gbc.gridx = 0;
        painel.add(cancelar, gbc);
        gbc.gridx = 1;
        painel.add(confirmar, gbc);

        JDialog popUp = framePai.criarPopUp("QUITAR FIADO", painel, 400, 250);

        confirmar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText().trim());
                Fiado fiado = FiadoRepositorio.buscarPorId(id);

                if (fiado != null) {
                    if (checkParcial.isSelected()) {
                        double valor = Double.parseDouble(campoValor.getText().trim().replace(',', '.'));
                        fiado.registrarPagamento(valor);
                    } else {
                        fiado.quitarTotalmente();
                    }

                    JOptionPane.showMessageDialog(this, "Pagamento registrado!", "SUCESSO",
                            JOptionPane.INFORMATION_MESSAGE);
                    popUp.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Fiado não encontrado!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "ERRO",
                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        cancelar.addActionListener(e -> popUp.dispose());
        popUp.setVisible(true);
    }
}
