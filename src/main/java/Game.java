import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public class Game extends JPanel {
    private final Board board;
    private JButton myButton;

    public Game(){

        int boardSize = 5;
        int squareSize = 120;
        int toolbarHeight = (int) Math.round(1.5*squareSize);
        setPreferredSize(new Dimension(boardSize * squareSize, (boardSize) * squareSize+toolbarHeight));
        this.board = new Board(boardSize, squareSize);
        board.setSelectedTile(new MirrorTile()); //use double mirror as standard selection
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if(e.getButton()==MouseEvent.BUTTON1){
                    Tile addTile = new Tile();
                    addTile.setImage(board.getSelectedTile().getImage());
                    addTile.setOrientation(board.getSelectedTile().getOrientation());
                    board.addTile(addTile);

                }
                else if (e.getButton()==MouseEvent.BUTTON3) {
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

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    board.rotateSelectedTile();

                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    //board.setPlacing();

                    repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_1) {
                    board.setSelectedTile(new LaserTile());
                    repaint();

                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    board.setSelectedTile(new MirrorTile());
                    repaint();

                }
//                if (e.getKeyCode() == KeyEvent.VK_2) {
//                    board.setSelectedTile("beamSplitter");
//                    repaint();
//                }
//                if (e.getKeyCode() == KeyEvent.VK_3) {
//                    board.setSelectedTile("checkPoint");
//                    repaint();
//
//                }
//                if (e.getKeyCode() == KeyEvent.VK_4) {
//                    board.setSelectedTile("cellBlocker");
//                    repaint();
//                }
//                if (e.getKeyCode() == KeyEvent.VK_5) {
//                    board.setSelectedTile("laser");
//                    repaint();
//
//                }
//                if (e.getKeyCode() == KeyEvent.VK_6) {
//                    board.setSelectedTile("targetMirror");
//                    repaint();
//                }

                if (e.getKeyCode() == KeyEvent.VK_L) {
                    board.constructLaserTree();
                    repaint();
                }



            }
        });

    }



    protected void paintComponent(Graphics g) {
        // Draw the board on repaint
        super.paintComponent(g);
        board.drawBoard(g);

    }


    public static void main(String[] args) {

        // Setup the game window
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Board");
            Game board = new Game();
            frame.add(board);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });

    }
}
