package View.Renderers;

import javax.swing.*;
import java.awt.*;

public class TargetRender extends JComponent {
    private int number;
    private Color circleColor;
    private Color numberColor;
    private int diameter;

    public TargetRender(int number, Color circleColor, Color numberColor, int diameter) {
        this.number = number;
        this.circleColor = circleColor;
        this.numberColor = numberColor;
        this.diameter = diameter;
        setPreferredSize(new Dimension(diameter, diameter));
    }

    public void setTargets(int targets) {
        this.number = targets;
    }

    public void setColor(Color color) {
        this.circleColor = color;
    }

    public void setNumberColor(Color backColor) {
        this.numberColor = backColor;
    }

    public void setDiameter(int size) {
        this.diameter = size;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the circle
        g2d.setColor(circleColor);
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;
        g2d.fillOval(x, y, diameter, diameter);

        // Draw the number
        g2d.setColor(numberColor);
        g2d.setFont(new Font("Baloo Bhaijaan", Font.BOLD, diameter/4*3));
        FontMetrics fm = g2d.getFontMetrics();
        String text = Integer.toString(number);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g2d.drawString(text, x + (diameter - textWidth) / 2, y + (diameter + textHeight) / 2 - fm.getDescent()+diameter/8);
    }
}
