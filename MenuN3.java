import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MenuN3 extends JPanel {

    private final SistemaPrincipal framePai;
    private JScrollPane painelScroll2;

    public MenuN3(SistemaPrincipal frame) {
        this.framePai = frame;
        setLayout(new GridBagLayout());
        setBackground(new Color(156, 156, 156));
        initComponents();

        estilizarBotaoMaior(jBVoltar);
        estilizarBotaoMaior(jBPopUpAdicionar);
    }

    private void estilizarBotaoMaior(javax.swing.JButton botao) {
        botao.setBackground(new Color(0, 0, 0));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 18));
        botao.setOpaque(true);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
    }

    private void adicionarLinhaProduto(JPanel container, String id, String nome, String preco, String qtd,
            String validade) {
        JPanel linha = new JPanel(new GridLayout(1, 5));
        linha.setBackground(Color.WHITE);

        linha.add(new JLabel(id));
        linha.add(new JLabel(nome));
        linha.add(new JLabel(preco));
        linha.add(new JLabel(qtd));
        linha.add(new JLabel(validade));

        container.add(linha);
    }

    private void initComponents() {
        jBVoltar = new javax.swing.JButton("VOLTAR");
        jBPopUpAdicionar = new javax.swing.JButton();
        jBPopUpL = new javax.swing.JButton();
        JBpopUpE = new javax.swing.JButton();

        GridBagConstraints gbc = new GridBagConstraints();

    }

    private javax.swing.JButton jBVoltar;
    private javax.swing.JButton jBPopUpAdicionar;
    private javax.swing.JButton jBPopUpL;
    private javax.swing.JButton JBpopUpE;

}
