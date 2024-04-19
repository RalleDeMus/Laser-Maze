package View.Board;

import Model.Logic.Board;
import Model.Tiles.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ToolBar extends MouseAdapter implements KeyListener {
    protected Board board;
    protected JPanel panel;

    protected int yOffset;

    protected BoardRenderer laserToolBarRenderer;

    public ToolBar(Board board, JPanel panel,int yOffset, BoardRenderer laserToolBarRenderer) {
        this.board = board;
        this.panel = panel;
        this.yOffset = yOffset;
        this.laserToolBarRenderer = laserToolBarRenderer;

        // Set focusable to true so that the panel can receive key events
        panel.setFocusable(true);
        panel.addMouseListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            if(e.getX()>board.getBoardSize()*board.getSquareSize()){
                List<Tile> tiles = new ArrayList<>();

                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < board.get_game_info(i); j++) {
                        tiles.add(intToTile(i));
                    }

                }
                int y = e.getY()-yOffset;
                if(y/board.getSquareSize()<tiles.size()) {
                    board.setSelectedTile(tiles.get(y / board.getSquareSize()));
                    board.setCursorPos(0, 0);
                }


                laserToolBarRenderer.repaint();
            }
        }
    }

    Tile intToTile (int i) {
        switch(i) {
            case 0:
                return new MirrorTile(true,true);
            case 1:
                return new SplitterTile(true, true);
            case 2:
                return new CheckPointTile(true, true);
            case 3:
                return new DoubleTile(true,true);
            case 4:
                return new LaserTile(true,true);
            default:
                return null;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
