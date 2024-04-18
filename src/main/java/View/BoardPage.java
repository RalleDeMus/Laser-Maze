package View;

import Model.Logic.Board;
import View.Board.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BoardPage extends JPanel {
    private BoardInputHandler inputHandler;

    private ToolBar toolBar;

    public BoardPage(MainMenu mainMenu, boolean includeLaserFeatures) {
        // Make sure the Board is accessible here.
        Board board = Board.getInstance(); // Ensure we have access to the Board instance.
        setLayout(new BorderLayout());
        int topPanelHeight = 40;
        initializeUI(mainMenu,topPanelHeight, board.get_game_info(5));

        // Conditional setup based on whether laser features are included.
        BoardRenderer renderer;
        if (includeLaserFeatures) {
            renderer = new LaserToolBarRenderer();
            inputHandler = new LaserInputHandler(board, this, topPanelHeight);
            toolBar = new ToolBar(board, this, topPanelHeight,renderer);


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

    private void initializeUI(MainMenu mainMenu, int topPanelHeight,int targets) {
        // Create the top panel with a FlowLayout aligned to the left
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));

        // Create a label for displaying text on the right
        JLabel textLabel = new JLabel("Targets: "+targets);
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Set the preferred size of the top panel
        topPanel.setPreferredSize(new Dimension(getWidth(), topPanelHeight));

        // Add the back button to the top panel
        topPanel.add(backButton);

        // Add an invisible component to push the label to the right
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(textLabel);

        // Add the top panel to the main container
        add(topPanel, BorderLayout.NORTH);
    }
}