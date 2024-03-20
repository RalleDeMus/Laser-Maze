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
    AssetServer assetServer;

    public Game(){
        assetServer = new AssetServer();

        int boardSize = 5;
        int squareSize = 120;
        int toolbarHeight = (int) Math.round(1.5*squareSize);
        setPreferredSize(new Dimension(boardSize * squareSize, (boardSize) * squareSize+toolbarHeight));
        this.board = new Board(boardSize, squareSize,assetServer);


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
