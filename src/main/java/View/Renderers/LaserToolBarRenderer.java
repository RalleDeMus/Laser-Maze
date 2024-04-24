package View.Renderers;

import Controller.AssetServer;
import Controller.ImageHandler;
import Model.Logic.PointStringPair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LaserToolBarRenderer extends BoardRenderer {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLaser(g);
        drawToolBar(g);
    }

    private void drawToolBar(Graphics g) {

        List<BufferedImage> tiles = new ArrayList<>();

        if (!board.laserExists()) {
            tiles.add(AssetServer.getInstance().getImage("laser"));
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < board.get_game_info(i); j++) {
                tiles.add(getTileImage(i));
            }

        }

        int squareSize = board.getSquareSize();

        for (int i = 0; i < tiles.size(); i++) {
            g.drawImage(tiles.get(i), board.getBoardSize() * board.getSquareSize()+board.getSquareSize()/2, i * board.getSquareSize(), board.getSquareSize(), board.getSquareSize(), null);
        }

    }

    BufferedImage getTileImage(int i) {
        switch (i) {
            case 0:
                return AssetServer.getInstance().getImage("targetMirror");
            case 1:
                return AssetServer.getInstance().getImage("beamSplitter");
            case 2:
                return AssetServer.getInstance().getImage("checkPoint");
            case 3:
                return AssetServer.getInstance().getImage("doubleMirror");
            case 4:
                return AssetServer.getInstance().getImage("laser");
        }
        return null;
    }

    void drawLaser(Graphics g){

        if(board.isLaserFired()) {
            List<PointStringPair> laserMap = board.constructLaserTree();

            if (laserMap != null) {



                for (PointStringPair pair : laserMap) {
                    int j = pair.getPoint().x;
                    int i = pair.getPoint().y;
                    int squareSize = board.getSquareSize();

                    String value = pair.getValue();

                    if (!value.equals("___")) {
                        BufferedImage image = AssetServer.getInstance().getImage("laserRay");

                        if (value.charAt(0) != '_') {
                            if (value.charAt(1) == '8') {
                                int direction = Character.getNumericValue(value.charAt(0));

                                g.drawImage(ImageHandler.rotateImage(AssetServer.getInstance().getImage("laserRayTarget"), 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);

                            } else {
                                int direction = Character.getNumericValue(value.charAt(0));
                                g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);
                            }
                        }

                        if (value.charAt(1) != '_') {
                            if (value.charAt(1) != '8') {
                                int direction = Character.getNumericValue(value.charAt(1)) + 2;

                                g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);

                            }
                        }


                        if (value.charAt(2) != '_') {
                            int direction = Character.getNumericValue(value.charAt(2)) + 2;
                            g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);
                        }
                    }

                }
                board.checkWinCondition();

            }
            else {
                System.out.println("Not all mirrors used");
            }

        }


    }
}