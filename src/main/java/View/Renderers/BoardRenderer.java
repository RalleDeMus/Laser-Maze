package View.Renderers;

import Controller.AssetServer;
import Controller.ImageHandler;
import Model.Logic.Board;
import Model.Tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BoardRenderer extends JPanel {
    protected Board board;

    public BoardRenderer() {
        this.board = Board.getInstance();
        setPreferredSize(new Dimension(board.getBoardSize() * board.getSquareSize(), board.getBoardSize() * board.getSquareSize()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
    }

    public void drawBoard(Graphics g) {
        int boardSize = board.getBoardSize();
        int squareSize = board.getSquareSize();
        Tile[][] tiles = board.getTiles();
        Point cursorPos = board.getCursorPos();
        Tile selectedTile = board.getSelectedTile();
        boolean laserWasFired = board.isLaserFired();


        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (tiles[row][col] != null) {
                    // Draws the tile if it is not empty
                    g.drawImage(tiles[row][col].getImage(), col * squareSize, row * squareSize, squareSize, squareSize, null);
                } else {
                    // Draws an empty tile if empty
                    g.drawImage(AssetServer.getInstance().getImage("empty"), col * squareSize, row * squareSize, squareSize, squareSize, null);

                }


            }
        }

        if(selectedTile != null) {
            BufferedImage cursorImage = ImageHandler.transImage(selectedTile.getImage(), 0.6f);
            g.drawImage(cursorImage, cursorPos.x * squareSize, cursorPos.y * squareSize, squareSize, squareSize, null);

        }



    }
}