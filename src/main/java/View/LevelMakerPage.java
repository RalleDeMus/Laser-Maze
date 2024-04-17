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
    private int beamSplitterCount, cellBlockerCount, checkPointCount, doubleMirrorCount, laserCount, spotTargetMirrorCount, targetMirrorCount;
    private String currentSelection = "BeamSplitter"; // Default selection

    private JLabel statusLabel;  // JLabel to display status messages

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

        statusLabel = new JLabel("Welcome to Level Maker");
        toolbarPanel.add(statusLabel);

        //toggle rotate

        // Add and Remove buttons
        JButton addButton = new JButton("Add Tile");
        JButton removeButton = new JButton("Remove Tile");

        addButton.addActionListener(e -> {
            switch (currentSelection) {
                case "BeamSplitter":
                    beamSplitterCount++;
                    break;
                case "CellBlocker":
                    cellBlockerCount++;
                    break;
                case "CheckPoint":
                    checkPointCount++;
                    break;
                case "DoubleMirror":
                    doubleMirrorCount++;
                    break;
                case "Laser":
                    laserCount++;
                    break;
                case "SpotTargetMirror":
                    spotTargetMirrorCount++;
                    break;
                case "TargetMirror":
                    targetMirrorCount++;
                    break;
            }
            updateStatus();
        });

        removeButton.addActionListener(e -> {
            switch (currentSelection) {
                case "BeamSplitter":
                    beamSplitterCount--;
                    break;
                case "CellBlocker":
                    cellBlockerCount--;
                    break;
                case "CheckPoint":
                    checkPointCount--;
                    break;
                case "DoubleMirror":
                    doubleMirrorCount--;
                    break;
                case "Laser":
                    laserCount--;
                    break;
                case "SpotTargetMirror":
                    spotTargetMirrorCount--;
                    break;
                case "TargetMirror":
                    targetMirrorCount--;
                    break;
            }
            updateStatus();
        });

        toolbarPanel.add(addButton);
        toolbarPanel.add(removeButton);

        // Selection mechanism (e.g., JComboBox or JRadioButton)
        JComboBox<String> selectionBox = new JComboBox<>(new String[]{"BeamSplitter", "CellBlocker", "CheckPoint", "DoubleMirror", "Laser", "SpotTargetMirror", "TargetMirror"});
        selectionBox.addActionListener(e -> {
            currentSelection = (String) selectionBox.getSelectedItem();
        });
        toolbarPanel.add(selectionBox);


        add(topPanel, BorderLayout.NORTH);
        add(toolbarPanel, BorderLayout.EAST);
        add(new BoardPanel(), BorderLayout.CENTER);


        /////



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
                    Board.rotateSelectedTile();


                    repaint();
                    //rotates the tile that is hovered over

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

//update status efter knap klik
    private void updateStatus() {
        statusLabel.setText(String.format("<html>BeamSplitter: %d<br>CellBlocker: %d<br>CheckPoint: %d<br>DoubleMirror: %d<br>Laser: %d<br>SpotTargetMirror: %d<br>TargetMirror: %d</html>",
                beamSplitterCount, cellBlockerCount, checkPointCount, doubleMirrorCount, laserCount, spotTargetMirrorCount, targetMirrorCount));

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





