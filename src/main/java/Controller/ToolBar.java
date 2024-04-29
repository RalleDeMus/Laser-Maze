package Controller;

import Model.Logic.Board;
import Model.Tiles.*;
import View.Renderers.BoardRenderer;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ToolBar extends MouseAdapter  {
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

                if (board.laserNeeded()) {
                    tiles.add(new LaserTile(true, true));
                }
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < board.get_game_info_by_index(i); j++) {
                        tiles.add(intToTile(i));
                    }

                }
                int y = e.getY()-yOffset;
                if(y/board.getSquareSize()<tiles.size()) {
                    board.setSelectedTile(tiles.get(y / board.getSquareSize()));
                    board.setCursorLocation(0, 0);
                }


                laserToolBarRenderer.repaint();
            }
        }
    }

    public static Tile intToTile (int i) {
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


}
