package Controller;

import Model.Logic.Board;
import Model.Tiles.*;
import Model.Tiles.GameTiles.*;
import View.Renderers.BoardRenderer;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Toolbar input handler class.
 * This class is responsible for handling the input from the user on the toolbar.
 * It makes sure that the user can select a tile from the toolbar given a mouse press and position.
 */
public class ToolBar extends MouseAdapter  {
    protected Board board;
    protected JPanel panel;

    protected int yOffset;

    protected BoardRenderer laserToolBarRenderer;

    /**
     * Constructor for the BoardInputHandler class.
     * @param board The board to handle input for.
     * @param panel The panel to handle input for.
     * @param yOffset The offset of the board because we have a back button.
     * @param laserToolBarRenderer The renderer for the laser toolbar.
     */
    public ToolBar(Board board, JPanel panel,int yOffset, BoardRenderer laserToolBarRenderer) {
        this.board = board;
        this.panel = panel;
        this.yOffset = yOffset;
        this.laserToolBarRenderer = laserToolBarRenderer;

        // Set focusable to true so that the panel can receive key events
        panel.setFocusable(true);
        panel.addMouseListener(this);
    }


    //select tile from toolbar with left click
    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            if(e.getX()>board.getBoardSize()*board.getSquareSize()){
                List<Tile> tiles = new ArrayList<>();

                if (board.laserNeeded()) {
                    tiles.add(new LaserTile(true, true));
                }

                //board.get_game_info().printTiles();

                List<Tile> tilesWithIsMirror = TileInfo.getTiles(true);

                System.out.println("size "+tilesWithIsMirror.size());
                board.get_game_info().printTiles();
                for (int i = 0; i < tilesWithIsMirror.size(); i++) {
                    for (int j = 0; j < board.get_game_info().getTileFromDictionary(tilesWithIsMirror.get(i).getClass().getSimpleName()); j++) {
                        tiles.add(TileInfo.TileFromKey(tilesWithIsMirror.get(i).getClass().getSimpleName()));
                    }

                }
                int y = e.getY()-yOffset;

                int toolbarSize = board.getSquareSize()/10*8;
                if(y/toolbarSize<tiles.size()) {
                    board.setSelectedTile(tiles.get(y / toolbarSize));
                    board.setCursorLocation(0, 0);
                }


                laserToolBarRenderer.repaint();
            }
        }
    }




}
