package Controller;

import java.awt.event.KeyEvent;

import Model.Logic.Board;
import View.Pages.BoardPage;


/**
 * Laser input handler class.
 * This class is responsible for handling the input from the user on the board when the laser is being used.
 */
public class LaserInputHandler extends BoardInputHandler {

    BoardPage panel;

    /**
     * Constructor for the LaserInputHandler class.
     * See BoardInputHandler for more information on the parameters.
     */
    public LaserInputHandler(Board board, BoardPage panel, int yOffset, boolean removeTileAfterPlace){
        super(board, panel, yOffset, removeTileAfterPlace);
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e); // Call super to handle common key events
        if (e.getKeyCode() == KeyEvent.VK_L) {
            board.setLaserWasFired(true);
            panel.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e); // Call super to handle common key release events
        if (e.getKeyCode() == KeyEvent.VK_L) {
            board.setLaserWasFired(false);
            panel.repaint();
            panel.updateWinStatus();


        }
    }

}