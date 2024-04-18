package View;

import Model.Logic.Board;
import View.Board.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BoardPage extends JPanel {
    private BoardInputHandler inputHandler;

    public BoardPage(MainMenu mainMenu, boolean includeLaserFeatures) {
        // Make sure the Board is accessible here.
        Board board = Board.getInstance(); // Ensure we have access to the Board instance.
        setLayout(new BorderLayout());
        int topPanelHeight = 40;
        initializeUI(mainMenu,topPanelHeight);

        // Conditional setup based on whether laser features are included.
        BoardRenderer renderer;
        if (includeLaserFeatures) {
            renderer = new LaserRenderer();
            inputHandler = new LaserInputHandler(board, this, topPanelHeight);
        } else {
            renderer = new BoardRenderer();
            inputHandler = new BoardInputHandler(board, this, topPanelHeight);
        }

        add(renderer, BorderLayout.CENTER);
        this.setFocusable(true);

        // Request focus in window for key listener to work properly
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                BoardPage.this.requestFocusInWindow();
            }
        });
    }

    private void initializeUI(MainMenu mainMenu,int topPanelHeight) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.setPreferredSize(new Dimension(getWidth(), topPanelHeight));
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);
    }
}