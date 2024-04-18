package View.Board;

import Controller.AssetServer;
import Controller.ImageHandler;
import Model.Logic.PointStringPair;
import View.Board.BoardRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class LaserRenderer extends BoardRenderer {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLaser(g);
    }

    void drawLaser(Graphics g){
        if(board.isLaserFired()) {
            List<PointStringPair> laserMap = board.constructLaserTree();

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
        }


    }
}