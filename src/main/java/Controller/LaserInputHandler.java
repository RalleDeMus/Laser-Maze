package Controller;

import java.awt.event.KeyEvent;

import Model.Logic.Board;
import View.Pages.BoardPage;


public class LaserInputHandler extends BoardInputHandler {

    BoardPage panel;

    public LaserInputHandler(Board board, BoardPage panel, int yOffset, boolean removeTileAfterPlace){
        super(board, panel, yOffset, removeTileAfterPlace);
        this.panel = panel;
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
            panel.updateWinStatus();

        }
    }

}