package Controller;

import Model.Logic.Board;

import javax.swing.*;
import java.awt.event.*;

public class BoardInputHandler extends MouseAdapter implements KeyListener {
    protected Board board;
    protected JPanel panel;

    protected int yOffset;

    final private boolean removeTileAfterPlace;


    public BoardInputHandler(Board board, JPanel panel,int yOffset, boolean removeTileAfterPlace) {
        this.board = board;
        this.panel = panel;
        this.yOffset = yOffset;
        this.removeTileAfterPlace = removeTileAfterPlace;
        setupListeners();
    }

    private void setupListeners() {
        panel.setFocusable(true);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(e.getX()<board.getBoardSize()*board.getSquareSize()) {
                    Board.setCursorPos(e.getX(), e.getY() - yOffset);
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

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX()<board.getBoardSize()*board.getSquareSize()) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                try {
                    board.addTile(board.getSelectedTile().clone(),removeTileAfterPlace);
                } catch (CloneNotSupportedException ex) {
                    ex.printStackTrace();
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                Board.removeTile();
            }
            panel.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            Board.rotateSelectedTile(false);
        }
        panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}