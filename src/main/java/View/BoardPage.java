package View;
import Controller.AssetServer;
import Controller.BoardPageController;
import Controller.ImageHandler;
import Model.Logic.PointStringPair;
import Model.Tiles.*;
import Model.Logic.Board;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;


public class BoardPage extends JPanel {
    final private Board board;

    BoardPage(MainMenu mainMenu) {
        new BoardPageController();
        int boardSize = 5;
        int squareSize = 100;
        int toolbarHeight = (int) Math.round(1.5 * squareSize);
        setPreferredSize(new Dimension(boardSize * squareSize, (boardSize) * squareSize + toolbarHeight));
        this.board = Board.getInstance();

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.add(backButton);


        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawBoard(g);
            }
        };

        add(topPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        int yOffset = backButton.getHeight();


        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                BoardPage.this.requestFocusInWindow();
            }
        });


        setFocusable(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) {
                    try {
                        // Assuming board.addTile() accepts a Model.Tiles.Tile object as its parameter
                        // and board.getSelectedTile() returns a Model.Tiles.Tile object
                        board.addTile(board.getSelectedTile().clone());
                    } catch (CloneNotSupportedException er) {
                        // Handle the exception, e.g., log it or throw a runtime exception
                        er.printStackTrace();
                    }


                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    board.removeTile();


                }
                repaint();
            }


        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                board.setCursorPos(e.getX(), e.getY() - topPanel.getHeight());
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    //rotates the tile that is hovered over
                    board.rotateSelectedTile();


                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //board.setPlacing();

                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_1) {
                    board.setSelectedTile(new LaserTile(true, true));
                    repaint();

                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    board.setSelectedTile(new MirrorTile(true, true));
                    repaint();

                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    board.setSelectedTile(new DoubleTile(true, true));
                    repaint();

                }
                if (e.getKeyCode() == KeyEvent.VK_4) {
                    board.setSelectedTile(new SplitterTile(true, true));
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_5) {
                    board.setSelectedTile(new CheckPointTile(false, true));
                    repaint();

                }
                if (e.getKeyCode() == KeyEvent.VK_6) {
                    board.setSelectedTile(new CellBlockerTile());
                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_L) {
                    board.fireLaser();
                    repaint();
                }


            }
        });





    }

    void drawLaser(Graphics g){
        List<PointStringPair> laserMap = board.constructLaserTree();

        for (PointStringPair pair : laserMap) {
            System.out.println("LaserMap: " + pair.getPoint() + " " + pair.getValue());
            int j = pair.getPoint().x;
            int i = pair.getPoint().y;
            int squareSize = board.getSquareSize();

            String value = pair.getValue();

            if (!value.equals("___")) {
                BufferedImage image = AssetServer.getInstance().getImage("laserRay");

                if (value.charAt(0) != '_') {
                    if (value.charAt(1) == '8') {
                        int direction = Character.getNumericValue(value.charAt(0));

                        g.drawImage(ImageHandler.rotateImage(AssetServer.getInstance().getImage("laserRayTarget"),90*direction), j * squareSize, i * squareSize, squareSize, squareSize, null);

                    } else {
                        int direction = Character.getNumericValue(value.charAt(0));
                        g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j *  squareSize, i * squareSize, squareSize, squareSize, null);
                    }
                }

                if (value.charAt(1) != '_') {
                    if (value.charAt(1) != '8') {
                        int direction = Character.getNumericValue(value.charAt(1)) + 2;
                        System.out.println("Laserdir: " + direction );

                        g.drawImage(ImageHandler.rotateImage(image, 90 * direction), j * squareSize, i * squareSize, squareSize, squareSize, null);

                    }
                }


                if (value.charAt(2) != '_') {
                    int direction = Character.getNumericValue(value.charAt(2))+2;
                    System.out.println("Laserdir: " + direction );
                    g.drawImage(ImageHandler.rotateImage(image,90*direction), j * squareSize, i * squareSize, squareSize, squareSize, null);
                }
            }

        }


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

        BufferedImage cursorImage = ImageHandler.transImage(selectedTile.getImage(), 0.6f);
        g.drawImage(cursorImage, cursorPos.x * squareSize, cursorPos.y * squareSize, squareSize, squareSize, null);

        if(laserWasFired) {
            drawLaser(g);
        }
    }


    protected void paintComponent (Graphics g){
        // Draw the board on repaint
        super.paintComponent(g);
        drawBoard(g);

    }

}

