package Formatacao;

import javax.swing.*;
import java.awt.*;

public class LabelBotaoArredondado extends JLabel {
    private Color corDeFundo;
    private Color corTexto;
    private Font fonteLabel = new Font("Segoe UI", Font.BOLD, 22);

    public LabelBotaoArredondado(String texto, Color corDeFundo, Color corTexto) {
        super(texto, SwingConstants.CENTER);
        this.corDeFundo = corDeFundo;
        this.corTexto = corTexto;
        setFont(fonteLabel);
        setOpaque(false); // IMPORTANTE! Vamos pintar manualmente
        setForeground(corTexto);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public void setFonte(Font fonte) {
        setFont(fonte);
        repaint();
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Ativa suavização do desenho (pra não ficar serrilhado)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Desenha fundo arredondado
        g2.setColor(corDeFundo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // os dois últimos definem o ARREDONDAMENTO

        // Chama o paint padrão pra desenhar o texto
        super.paintComponent(g);

        g2.dispose();
    }
}
