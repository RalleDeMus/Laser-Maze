package View;
import Controller.BoardPageController;
import Model.Tiles.*;
import Model.Logic.Board;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LevelMakerPage extends JPanel{

    final private Board board;
    private int beamSplitterCount;
    private int cellBlockerCount;

    LevelMakerPage(MainMenu mainMenu) {
        new BoardPageController();
        int boardSize = 5;
        int squareSize = 120;
        int toolbarHeight = (int) Math.round(1.5 * squareSize);
        setPreferredSize(new Dimension(boardSize * squareSize, (boardSize) * squareSize + toolbarHeight));
        this.board = Board.getInstance();
        this.beamSplitterCount = 0;
        this.cellBlockerCount = 0;

        setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.add(backButton);

        JPanel toolbarPanel = new JPanel(new GridLayout(0,1));

        // BeamSplitter buttons
        JButton addbeamsplitterButton = new JButton("Add beamSplitter");
        JButton removebeamsplitterButton = new JButton("Remove beamSplitter");

        addbeamsplitterButton.addActionListener(e -> {
            beamSplitterCount++;
                    System.out.println("BeamSplitter : " + beamSplitterCount);
        });

        removebeamsplitterButton.addActionListener(e -> {
            beamSplitterCount--;
            System.out.println("BeamSplitter : " + beamSplitterCount);
        });

        toolbarPanel.add(addbeamsplitterButton);
        toolbarPanel.add(removebeamsplitterButton);

        // CellBlocker buttons

        JButton addcellblockerButton = new JButton("Add CellBlocker");
        JButton removecellblockerButton = new JButton("Remove CellBlocker");

        addcellblockerButton.addActionListener(e -> {
            cellBlockerCount++;
            System.out.println("CellBlocker : " + cellBlockerCount);
        });

        removecellblockerButton.addActionListener(e -> {
            cellBlockerCount--;
            System.out.println("CellBlocker : " + cellBlockerCount);
        });

        toolbarPanel.add(addcellblockerButton);
        toolbarPanel.add(removecellblockerButton);

        add(topPanel, BorderLayout.NORTH);
        add(toolbarPanel, BorderLayout.EAST);
        add(new BoardPanel(), BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                LevelMakerPage.this.requestFocusInWindow();
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
                board.setCursorPos(e.getX(), e.getY());
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
                    board.constructLaserTree();
                    repaint();
                }


            }
        });





    }


    protected void paintComponent (Graphics g){
        // Draw the board on repaint
        super.paintComponent(g);
        board.drawBoard(g);

    }

    class BoardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            board.drawBoard(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(2, 3);
        }
    }

}





