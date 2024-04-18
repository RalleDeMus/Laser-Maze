package View;

import Model.Logic.Board;
import View.Board.*;
import View.Board.CostomLabels.TargetRender;

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

    private void initializeUI(MainMenu mainMenu, int topPanelHeight, int targets) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));


        topPanel.setPreferredSize(new Dimension(getWidth(), topPanelHeight));
        topPanel.add(backButton);
        topPanel.add(Box.createHorizontalGlue());

        // Create a separate panel to hold the NumberedCircle
        JPanel circlePanel = new JPanel();
        circlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        TargetRender numberedCircle = new TargetRender(targets, new Color(222, 48, 48), Color.WHITE, 60);

        JLabel normalLabel = new JLabel("Targets:");


        circlePanel.add(normalLabel);
        circlePanel.add(numberedCircle);


        add(topPanel, BorderLayout.NORTH);
        add(circlePanel, BorderLayout.SOUTH);  // Add the circle panel under the renderer

        // Adjust the circle panel to hold the VerticalLabel and TargetRender circle

    }
}