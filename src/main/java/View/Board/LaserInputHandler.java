package View.Board;

import java.awt.event.KeyEvent;

import Model.Logic.Board;
import javax.swing.*;


public class LaserInputHandler extends BoardInputHandler {

    public LaserInputHandler(Board board, JPanel panel,int yOffset){
        super(board, panel, yOffset);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e); // Call super to handle common key events
        if (e.getKeyCode() == KeyEvent.VK_L) {
            board.fireLaser();
            panel.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e); // Call super to handle common key release events
        if (e.getKeyCode() == KeyEvent.VK_L) {
            board.resetLaser();
            panel.repaint();
        }
    }

}