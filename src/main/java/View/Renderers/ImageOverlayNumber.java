package View.Renderers;

import javax.swing.*;
import java.awt.*;

public class ImageOverlayNumber extends JComponent {
    final private ImageIcon imageIcon;
    private int number;
    private Font font;

    public ImageOverlayNumber(ImageIcon imageIcon, int number, int width, int height) {
        this.imageIcon = imageIcon;
        this.number = number;
        this.font = new Font("Baloo Bhaijaan", Font.BOLD, 20); // Adjust font size as needed
        setPreferredSize(new Dimension(width, height));
    }

    public void setNumber(int number) {
        this.number = number;
        repaint();
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the image
        if (imageIcon != null) {
            g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Draw the number
        g2d.setFont(font);
        g2d.setColor(new Color(255,255,255,180)); // Set the number color; make it configurable if needed
        String text = String.valueOf(number);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - (fm.getDescent() - getHeight()/5);
        g2d.drawString(text, x, y);
    }
}