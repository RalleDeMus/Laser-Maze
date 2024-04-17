package View;
import Controller.BoardPageController;
import Model.Tiles.*;
import Model.Logic.Board;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import Model.Logic.Board;

public class LevelMakerPage extends JPanel{

    final private Board board;
    private int beamSplitterCount;
    private int cellBlockerCount;
private int checkPointCount;
private int doubleMirrorCount;
private int laserCount;
private int spotTargetMirrorCount;
private int targetMirrorCount;
    private String statusMessage = "";
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

        JPanel toolbarPanel = new JPanel(new GridLayout(0,2));
        // BeamSplitter buttons
        JButton addbeamsplitterButton = new JButton("Add beamSplitter");
        JButton removebeamsplitterButton = new JButton("Remove beamSplitter");

        addbeamsplitterButton.addActionListener(e -> {
            beamSplitterCount++;
            System.out.println("BeamSplitter : " + beamSplitterCount);
            statusMessage = "BeamSplitter count: " + beamSplitterCount;
            repaint();
        });

        removebeamsplitterButton.addActionListener(e -> {
            beamSplitterCount--;
            System.out.println("BeamSplitter : " + beamSplitterCount);
            statusMessage = "BeamSplitter count: " + beamSplitterCount;
            repaint();
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

        JButton addcheckPointButton = new JButton("Add checkPoint");
        JButton removecheckPointButton = new JButton("Remove checkPoint");
/////////////////
        addcheckPointButton.addActionListener(e -> {
            checkPointCount++;
            System.out.println("checkPoint : " + checkPointCount);
        });

        removecheckPointButton.addActionListener(e -> {
            checkPointCount--;
            System.out.println("checkPoint : " + checkPointCount);
        });

        toolbarPanel.add(addcheckPointButton);
        toolbarPanel.add(removecheckPointButton);

        //doubleMirror
        JButton adddoubleMirrorButton = new JButton("Add doubleMirror");
        JButton removedoubleMirrorButton = new JButton("Remove doubleMirror");

        adddoubleMirrorButton.addActionListener(e -> {
            doubleMirrorCount++;
            System.out.println("doubleMirror : " + doubleMirrorCount);
        });

        removedoubleMirrorButton.addActionListener(e -> {
            doubleMirrorCount--;
            System.out.println("doubleMirror : " + doubleMirrorCount);
        });

        toolbarPanel.add(adddoubleMirrorButton);
        toolbarPanel.add(removedoubleMirrorButton);

        //laser
        JButton addlaserButton = new JButton("Add laser");
        JButton removelaserButton = new JButton("Remove laser");

        addlaserButton.addActionListener(e -> {
            laserCount++;
            System.out.println("laser : " + laserCount);
        });

        removelaserButton.addActionListener(e -> {
            laserCount--;
            System.out.println("laser : " + laserCount);
        });

        toolbarPanel.add(addlaserButton);
        toolbarPanel.add(removelaserButton);

        //spotTargetMirror
        JButton addspotTargetMirrorButton = new JButton("Add spotTargetMirror");
        JButton removespotTargetMirrorButton = new JButton("Remove spotTargetMirror");

        addspotTargetMirrorButton.addActionListener(e -> {
            spotTargetMirrorCount++;
            System.out.println("spotTargetMirror : " + spotTargetMirrorCount);
        });

        removespotTargetMirrorButton.addActionListener(e -> {
            spotTargetMirrorCount--;
            System.out.println("spotTargetMirror : " + spotTargetMirrorCount);
        });

        toolbarPanel.add(addspotTargetMirrorButton);
        toolbarPanel.add(removespotTargetMirrorButton);

        //
        JButton addtargetMirrorButton = new JButton("Add targetMirror");
        JButton removetargetMirrorButton = new JButton("Remove targetMirror");

        addtargetMirrorButton.addActionListener(e -> {
            targetMirrorCount++;
            System.out.println("targetMirror : " + targetMirrorCount);

        });

        removetargetMirrorButton.addActionListener(e -> {
            targetMirrorCount--;
            System.out.println("targetMirror : " + targetMirrorCount);
        });

        toolbarPanel.add(addtargetMirrorButton);
        toolbarPanel.add(removetargetMirrorButton);


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
                        Board.addTile(board.getSelectedTile().clone());
                    } catch (CloneNotSupportedException er) {
                        // Handle the exception, e.g., log it or throw a runtime exception
                        er.printStackTrace();
                    }


                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    Board.removeTile();


                }
                repaint();
            }


        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Board.setCursorPos(e.getX(), e.getY());
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    //rotates the tile that is hovered over
                    Board.rotateSelectedTile();


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
            // Set the color for the message text
            g.setColor(Color.BLACK);
            // Display the status message at a specific location on the board
            g.drawString(statusMessage, 10, 20); // You can adjust the coordinates
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(2, 3);
        }
    }

}





