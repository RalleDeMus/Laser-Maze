package View.Board;

import Model.Logic.Board;
import Model.Logic.PointStringPair;
import Model.Tiles.*;
import Model.Logic.Board;
import View.BoardPage;

import javax.swing.*;
import java.awt.event.*;

public class BoardInputHandler extends MouseAdapter implements KeyListener {
    protected Board board;
    protected BoardPage panel;

    protected int yOffset;

    public BoardInputHandler(Board board, BoardPage panel,int yOffset) {
        this.board = board;
        this.panel = panel;
        this.yOffset = yOffset;
        setupListeners();
    }

    private void setupListeners() {
        panel.setFocusable(true);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if(e.getX()<board.getBoardSize()*board.getSquareSize()) {
                    board.setCursorPos(e.getX(), e.getY() - yOffset);
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
                    board.addTile(board.getSelectedTile().clone());
                } catch (CloneNotSupportedException ex) {
                    ex.printStackTrace();
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                board.removeTile();
            }
            panel.repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                System.out.println("Rotating tile...");
                board.rotateSelectedTile();
                break;
            case KeyEvent.VK_ENTER:
                // Additional logic if needed
                break;
            case KeyEvent.VK_1:
                board.setSelectedTile(new LaserTile(true, true));
                break;
            case KeyEvent.VK_2:
                board.setSelectedTile(new MirrorTile(true, true));
                break;
            case KeyEvent.VK_3:
                board.setSelectedTile(new DoubleTile(true, true));
                break;
            case KeyEvent.VK_4:
                board.setSelectedTile(new SplitterTile(true, true));
                break;
            case KeyEvent.VK_5:
                board.setSelectedTile(new CheckPointTile(true, true));
                break;
            case KeyEvent.VK_6:
                board.setSelectedTile(new CellBlockerTile());
                break;
        }
        panel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }
}