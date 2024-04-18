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
    private Board board;
    private JPanel winPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Initialize here

    public BoardPage(MainMenu mainMenu, boolean includeLaserFeatures) {
        // Ensure the Board is accessible
        board = Board.getInstance();
        board.resetWin();
        setLayout(new BorderLayout());
        int topPanelHeight = 40;
        initializeUI(mainMenu, topPanelHeight, board.get_game_info(5));

        // Setup based on feature inclusion
        BoardRenderer renderer;
        if (includeLaserFeatures) {
            renderer = new LaserToolBarRenderer();
            inputHandler = new LaserInputHandler(board, this, topPanelHeight);
            toolBar = new ToolBar(board, this, topPanelHeight, renderer);
        } else {
            renderer = new BoardRenderer();
            inputHandler = new BoardInputHandler(board, this, topPanelHeight);
        }

        add(renderer, BorderLayout.CENTER);
        this.setFocusable(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                BoardPage.this.requestFocusInWindow();
            }
        });
    }

    public void updateWinStatus() {
        winPanel.removeAll(); // Safely remove all components
        if (board.getWin()) {
            JLabel winLabel = new JLabel("YOU WIN!");
            System.out.println("Win: "+board.getWin());
            winLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 40));
            winPanel.add(winLabel);
        }
        winPanel.revalidate();
        winPanel.repaint();
    }



    public void initializeUI(MainMenu mainMenu, int topPanelHeight, int targets) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));


        topPanel.setPreferredSize(new Dimension(getWidth(), topPanelHeight));
        topPanel.add(backButton);
        topPanel.add(Box.createHorizontalGlue());

        // How many Targets are there?
        JPanel circlePanel = new JPanel();
        circlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        TargetRender numberedCircle = new TargetRender(targets, new Color(222, 48, 48), Color.WHITE, 60);

        JLabel normalLabel = new JLabel(" Targets: ");
        normalLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 40));

        circlePanel.add(normalLabel);
        circlePanel.add(numberedCircle);


        // Win stuff
        winPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));


        // Create a GridBagConstraints object
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Start at the first column
        gbc.gridy = 0; // Start at the first row
        gbc.weightx = 1; // Request any extra horizontal space
        gbc.weighty = 1; // Request any extra vertical space
        gbc.anchor = GridBagConstraints.CENTER; // Center the component

// Set the circlePanel to use GridBagLayout
        circlePanel.setLayout(new GridBagLayout());
// Add the normalLabel and numberedCircle to the circlePanel with constraints
        circlePanel.add(normalLabel, gbc);
        gbc.gridx++; // Move to the next column
        circlePanel.add(numberedCircle, gbc);

// Reset the GridBagConstraints 'gridx' for the winPanel
        gbc.gridx = 0; // Reset to first column
// Set the winPanel to use GridBagLayout
        winPanel.setLayout(new GridBagLayout());
// Add the winLabel to the winPanel with constraints

// Now, add the south container panel to the main BoardPage panel with BorderLayout
        JPanel southContainer = new JPanel(new BorderLayout());
        southContainer.add(circlePanel, BorderLayout.WEST);
        southContainer.add(winPanel, BorderLayout.EAST);

// Add the top panel and south container panel to the main BoardPage panel
        add(topPanel, BorderLayout.NORTH);
        add(southContainer, BorderLayout.SOUTH);


    }
}