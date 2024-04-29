package Controller;

import Model.Logic.Board;

import javax.swing.*;
import java.awt.event.*;
/**
 * The BoardInputHandler class is responsible for handling the input from the user on the board.
 */
public class BoardInputHandler extends MouseAdapter implements KeyListener{
    protected Board board;
    protected JPanel panel;

    protected int yOffset;

    final private boolean removeTileAfterPlace; //

    /**
     * Constructor for the BoardInputHandler class.
     * @param board The board to handle input for.
     * @param panel The panel to handle input for.
     * @param yOffset The offset of the board because we have a back button.
     * @param removeTileAfterPlace Whether to remove the tile after placing it. We do this in a normal game but not in the level maker.
     */
    public BoardInputHandler(Board board, JPanel panel,int yOffset, boolean removeTileAfterPlace) {
        this.board = board;
        this.panel = panel;
        this.yOffset = yOffset;
        this.removeTileAfterPlace = removeTileAfterPlace;
        setupListeners();
    }

    /**
     * Sets up the listeners for the panel.
     */
    private void setupListeners() {
        panel.setFocusable(true);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(e.getX()<board.getBoardSize()*board.getSquareSize()) {
                    board.setCursorLocation(e.getX(), e.getY() - yOffset);
                    panel.repaint();
                }
            }
        });
        panel.addKeyListener(this);
        panel.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                panel.requestFocusInWindow();
            }
        });
    }

    //remove or add tile with left or right click
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX()<board.getBoardSize()*board.getSquareSize()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                board.addTile(removeTileAfterPlace);

            } else if (e.getButton() == MouseEvent.BUTTON3) {
                board.removeTile();
            }
            panel.repaint();
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    //rotate tile with R
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            board.rotateSelectedTile(false);
        }
        panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}