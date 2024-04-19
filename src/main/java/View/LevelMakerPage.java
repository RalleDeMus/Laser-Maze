package View;

import Controller.AssetServer;
import Model.Logic.Board;
import View.Board.*;
import View.Board.CostomLabels.ImageOverlayNumber;
import View.Board.CostomLabels.TargetRender;
import View.LevelMaker.LevelMakerInputHandler;
import View.LevelMaker.LevelMakerRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class LevelMakerPage extends JPanel {
    private BoardInputHandler inputHandler;
    private ToolBar toolBar;
    private Board board;
    private JPanel winPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Initialize here

    private MainMenu mainMenu;

    private int targets = 0;

    int tileCounts[] = new int[5];

    ImageOverlayNumber[] tileLabels = new ImageOverlayNumber[5];

    TargetRender targetCircle = new TargetRender(targets, new Color(222, 48, 48), Color.WHITE, 60);


    public LevelMakerPage(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        // Ensure the Board is accessible
        board = Board.getInstance();

        board.setCardLevel("0");
        BoardPage boardPage = new BoardPage(mainMenu,true);
        mainMenu.getCardPanel().add(boardPage, "boardPage");
        mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");

        board.resetWin();
        setLayout(new BorderLayout());
        int topPanelHeight = 40;


        // Setup based on feature inclusion
        BoardRenderer renderer;

        renderer = new LevelMakerRenderer();
        inputHandler = new LevelMakerInputHandler(board, this, topPanelHeight, false);
        initializeUI(mainMenu, topPanelHeight, renderer);


        this.setFocusable(true);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                LevelMakerPage.this.requestFocusInWindow();
            }
        });
    }

    // Update the target counter
    public void updateTargetCounter() {
        targetCircle.removeAll(); // Clear previous components if necessary
        targetCircle.setTargets(targets);
        targetCircle.setColor(new Color(222, 48, 48));
        targetCircle.setNumberColor(Color.WHITE);
        targetCircle.setDiameter(60);
        targetCircle.revalidate();
        targetCircle.repaint();
        System.out.println("Updated target counter");
    }

    // updateTargetCounter() NOT WORKING YET!



    public void initializeUI(MainMenu mainMenu, int topPanelHeight, BoardRenderer renderer) {
        setLayout(new GridBagLayout()); // Set the main layout to GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();

        // Top panel setup
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.setPreferredSize(new Dimension(getWidth(), topPanelHeight));
        topPanel.add(backButton);
        topPanel.add(Box.createHorizontalGlue());

        // Add top panel to the top of the layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(topPanel, gbc);

        // Renderer setup
        gbc.gridx = 0; // Reset to first column
        gbc.gridy = 1; // Move to the next row
        gbc.gridwidth = 1; // Only take up one column
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; // Give weight to expand
        gbc.weighty = 1.0;
        add(renderer, gbc); // Add renderer to the left side

        // East container setup
        JPanel eastContainer = createEastContainer(); // Method to create the east container
        gbc.gridx = 2; // Move to the last column
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(eastContainer, gbc); // Add east container to the right side

        // Circle panel setup
        JPanel circlePanel = createCirclePanel(); // Method to create the circle panel
        gbc.gridx = 0; // Move to the next column
        gbc.gridy = 2; // Stay on the same row
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; // Reset weights as this should not expand
        gbc.weighty = 0;
        add(circlePanel, gbc); // Add circle panel next to the renderer



    }

    void updateTileCount() {
        for (int i = 0; i < 5; i++) {
            tileLabels[i].setNumber(tileCounts[i]);
            tileLabels[i].revalidate();
            tileLabels[i].repaint();
        }
    }

    private JPanel createCirclePanel() {
        // Create the panel with GridBagLayout
        JPanel circlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margin between components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Create and configure the "Targets:" label
        JLabel normalLabel = new JLabel(" Targets: ");
        normalLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 40));
        circlePanel.add(normalLabel, gbc);

        // Create and configure the target circle
        targetCircle = new TargetRender(targets, new Color(222, 48, 48), Color.WHITE, 60);
        gbc.gridx++;
        circlePanel.add(targetCircle, gbc);

        // Create and configure the minus button
        JButton minusButton = new JButton("-");
        minusButton.setFont(new Font("Baloo Bhaijaan", Font.BOLD, 30));
        minusButton.setPreferredSize(new Dimension(40, 40));
        minusButton.addActionListener(e -> {
            targets--;
            System.out.println(targets);
            updateTargetCounter();
        });
        gbc.gridx++;
        circlePanel.add(minusButton, gbc);

        // Create and configure the plus button
        JButton plusButton = new JButton("+");
        plusButton.setFont(new Font("Baloo Bhaijaan", Font.BOLD, 30));
        plusButton.setPreferredSize(new Dimension(40, 40));
        plusButton.addActionListener(e -> {
            targets++;
            System.out.println(targets);
            updateTargetCounter();
        });
        gbc.gridx++;
        circlePanel.add(plusButton, gbc);

        return circlePanel;
    }

    private JPanel createEastContainer() {
        JPanel eastContainer = new JPanel(new GridBagLayout());
        GridBagConstraints ecGbc = new GridBagConstraints();
        ecGbc.insets = new Insets(5, 5, 5, 5); // Margin between components
        ecGbc.fill = GridBagConstraints.HORIZONTAL;
        ecGbc.anchor = GridBagConstraints.NORTH;

        // Create and add 5 tiles with plus and minus buttons
        for (int i = 0; i < 5; i++) {
            int index = i; // Effective final index for use in lambda expressions

            // Tile image with number
            BufferedImage tileImage = getTileImage(i);
            Image scaledImage = tileImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon tileIcon = new ImageIcon(scaledImage);
            tileLabels[i] = new ImageOverlayNumber(tileIcon, 0, 50, 50);
            tileLabels[i].setFont(new Font("Baloo Bhaijaan", Font.BOLD, 40));
            ecGbc.gridy = i;
            ecGbc.gridx = 0;
            eastContainer.add(tileLabels[i], ecGbc);

            // Minus button
            JButton minusButtonTile = new JButton("-");
            minusButtonTile.addActionListener(e -> {
                System.out.println("Tile " + (index) + " minus");
                changeTileCount(index, -1);
            });
            minusButtonTile.setPreferredSize(new Dimension(20, 20));
            ecGbc.gridy = i;
            ecGbc.gridx = 1;
            eastContainer.add(minusButtonTile, ecGbc);

            // Plus button
            JButton plusButtonTile = new JButton("+");
            plusButtonTile.addActionListener(e -> {
                System.out.println("Tile " + (index) + " plus");
                changeTileCount(index, 1);
            });            plusButtonTile.setPreferredSize(new Dimension(20, 20));
            ecGbc.gridy = i;
            ecGbc.gridx = 2;
            eastContainer.add(plusButtonTile, ecGbc);
        }

        return eastContainer;
    }

    void changeTileCount(int tileType, int change) {
        if (tileCounts[tileType] + change < 0) {
            return;
        }
        if (tileType == 4 && tileCounts[tileType] + change > 1) {
            return;
        }
        tileCounts[tileType] += change;
        updateTileCount();
    }

    BufferedImage getTileImage (int tileType) {
        BufferedImage tileImage = null;
        switch (tileType) {
            case 0:
                tileImage = AssetServer.getInstance().getImage("targetMirror");
                break;
            case 1:
                tileImage = AssetServer.getInstance().getImage("beamSplitter");
                break;
            case 2:
                tileImage = AssetServer.getInstance().getImage("checkPoint");
                break;
            case 3:
                tileImage = AssetServer.getInstance().getImage("doubleMirror");
                break;
            case 4:
                tileImage = AssetServer.getInstance().getImage("laser");
                break;
        }
        return tileImage;
    }


}
