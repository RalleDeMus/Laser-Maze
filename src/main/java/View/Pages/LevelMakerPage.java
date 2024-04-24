package View.Pages;

import Controller.*;
import Model.Logic.Board;
import Model.Tiles.Tile;
import View.Renderers.BoardRenderer;
import View.Renderers.ImageOverlayNumber;
import View.Renderers.TargetRender;

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

    private MainMenuPage mainMenu;

    private int targets = 0;

    int tileCounts[] = new int[4];

    ImageOverlayNumber[] tileLabels = new ImageOverlayNumber[5];

    TargetRender targetCircle = new TargetRender(targets, new Color(222, 48, 48), Color.WHITE, 60);


    public LevelMakerPage(MainMenuPage mainMenu) {
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
        targetCircle.setTargets(targets);
        targetCircle.revalidate();
        targetCircle.repaint();
    }




    public void initializeUI(MainMenuPage mainMenu, int topPanelHeight, BoardRenderer renderer) {
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
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(topPanel, gbc);

// Renderer setup
        gbc.gridx = 0;
        gbc.gridy = 1; // Moved down by one to make space for the allTiles image
        gbc.gridwidth = 2; // Spanning two columns to provide space for the board
        gbc.gridheight = 1; // Spanning only one row high
        gbc.weightx = 1.0; // Allow horizontal expansion
        gbc.weighty = 1.0; // Allow vertical expansion - important for the board
        gbc.fill = GridBagConstraints.BOTH;
        add(renderer, gbc);



// East container setup
        JPanel eastContainer = createEastContainer(); // Method to create the east container
        gbc.gridx = 2; // Move to the last column
        gbc.gridy = 1; // Adjusted to the new row
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(eastContainer, gbc); // Add east container to the right side

// Circle panel setup
        JPanel circlePanel = createCirclePanel(); // Method to create the circle panel

// New button setup in the bottom left of the same row
        JButton newButton = new JButton("Save and play level");
        newButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        newButton.addActionListener(e -> {
            saveLevel();
        });

        gbc.gridx = 0;
        gbc.gridy = 3; // Adjusted for the new row
        gbc.gridwidth = 1;
        gbc.gridheight = 1; // Make sure it's set to 1 to not affect other rows
        gbc.weightx = 0; // No horizontal expansion for the circle panel
        gbc.weighty = 0; // No vertical expansion for the circle panel
        gbc.anchor = GridBagConstraints.SOUTHWEST; // Align to the bottom left
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally if needed
        gbc.insets = new Insets(10, 10, 10, 0); // Adjust for padding
        add(circlePanel, gbc);

        gbc.gridx = 1; // Adjust the position based on your layout
        gbc.gridy = 3; // Adjusted for the new row
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Take up the remainder of the row
        gbc.anchor = GridBagConstraints.SOUTHEAST; // Align to the bottom right
        gbc.fill = GridBagConstraints.NONE; // Do not stretch the button
        gbc.insets = new Insets(10, 10, 10, 10); // Adjust for padding
        add(newButton, gbc);





    }

    void updateTileCount() {
        for (int i = 0; i < tileCounts.length; i++) {
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
        minusButton.setFont(new Font("Baloo Bhaijaan", Font.BOLD, 20));
        minusButton.setMargin(new Insets(0,0,0,0));
        minusButton.setPreferredSize(new Dimension(40, 40));
        minusButton.addActionListener(e -> {
            if(targets == 0) return;
            targets--;
            updateTargetCounter();
            LevelMakerPage.this.requestFocusInWindow();
        });
        gbc.gridx++;
        circlePanel.add(minusButton, gbc);

        // Create and configure the plus button
        JButton plusButton = new JButton("+");
        plusButton.setFont(new Font("Baloo Bhaijaan", Font.BOLD, 20));
        plusButton.setMargin(new Insets(0,0,0,0));
        plusButton.setPreferredSize(new Dimension(40, 40));
        plusButton.addActionListener(e -> {
            targets++;
            updateTargetCounter();
            LevelMakerPage.this.requestFocusInWindow();
        });
        gbc.gridx++;
        circlePanel.add(plusButton, gbc);

        return circlePanel;

    }

    private JPanel createEastContainer() {
        JPanel eastContainer = new JPanel(new GridBagLayout());
        GridBagConstraints ecGbc = new GridBagConstraints();
        //ecGbc.insets = new Insets(5, 5, 5, 5); // Margin between components


        // Add a simple text label below the allTiles image
        JLabel textTestLabel = new JLabel("Tiles for toolbar:");
        ecGbc.gridx = 0; // Span from the first column
        ecGbc.gridy = 0; // The row after allTilesPanel
        ecGbc.gridwidth = 3; // Span across all columns
        ecGbc.fill = GridBagConstraints.HORIZONTAL;
        ecGbc.insets = new Insets(0, 0, 10, 0);

        eastContainer.add(textTestLabel, ecGbc);



        ecGbc.fill = GridBagConstraints.HORIZONTAL;
        ecGbc.anchor = GridBagConstraints.NORTH;

        // Create and add 5 tiles with plus and minus buttons
        for (int i = 0; i < 4; i++) {
            int index = i; // Effective final index for use in lambda expressions
            ecGbc.insets = new Insets(0, 0, 0, 5);
            // Tile image with number
            BufferedImage tileImage = getTileImage(i);
            Image scaledImage = tileImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon tileIcon = new ImageIcon(scaledImage);
            tileLabels[i] = new ImageOverlayNumber(tileIcon, 0, 50, 50);
            tileLabels[i].setFont(new Font("Baloo Bhaijaan", Font.BOLD, 40));
            ecGbc.gridy = i+1;
            ecGbc.gridx = 0;
            ecGbc.gridwidth = 1;
            ecGbc.fill = GridBagConstraints.NONE;
            ecGbc.weightx = 0;
            eastContainer.add(tileLabels[i], ecGbc);

            int buttonsize = 35;
            // Minus button
            JButton minusButtonTile = new JButton("-");
            minusButtonTile.setFont(new Font("Baloo Bhaijaan", Font.BOLD, 20));
            minusButtonTile.setMargin(new Insets(0,0,0,0));
            minusButtonTile.addActionListener(e -> {
                System.out.println("Tile " + (index) + " minus");
                changeTileCount(index, -1);
                LevelMakerPage.this.requestFocusInWindow();
            });
            minusButtonTile.setPreferredSize(new Dimension(buttonsize, buttonsize));
            ecGbc.gridy = i+1;
            ecGbc.gridx = 1;
            ecGbc.weightx = 0; // No expansion
            ecGbc.fill = GridBagConstraints.NONE;
            eastContainer.add(minusButtonTile, ecGbc);

            // Plus button
            JButton plusButtonTile = new JButton("+");
            plusButtonTile.setFont(new Font("Baloo Bhaijaan", Font.BOLD, 20));
            plusButtonTile.setMargin(new Insets(0,0,0,0));
            plusButtonTile.addActionListener(e -> {
                System.out.println("Tile " + (index) + " plus");
                changeTileCount(index, 1);
                LevelMakerPage.this.requestFocusInWindow();
            });
            plusButtonTile.setPreferredSize(new Dimension(buttonsize, buttonsize));
            ecGbc.gridy = i+1;
            ecGbc.gridx = 2;
            ecGbc.weightx = 0; // No expansion
            ecGbc.fill = GridBagConstraints.NONE;
            eastContainer.add(plusButtonTile, ecGbc);

            ecGbc.insets = new Insets(0, 0, 0, 0);
        }

        ecGbc.insets = new Insets(10, 0, 0, 0);


        // Add a simple text label below the allTiles image
        textTestLabel = new JLabel("Keys for each tile:");
        ecGbc.gridx = 0; // Span from the first column
        ecGbc.gridy = 6; // The row after allTilesPanel
        ecGbc.gridwidth = 3; // Span across all columns
        ecGbc.fill = GridBagConstraints.HORIZONTAL;
        eastContainer.add(textTestLabel, ecGbc);

        // Add the allTiles image below the buttons
        BufferedImage allTilesImage = AssetServer.getInstance().getImage("allTiles");
        Image scaledAllTilesImage = allTilesImage.getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        JLabel allTilesLabel = new JLabel(new ImageIcon(scaledAllTilesImage));
        JPanel allTilesPanel = new JPanel();
        allTilesPanel.add(allTilesLabel);
        ecGbc.gridx = 0; // Span from the first column
        ecGbc.gridy = 7; // The row after the last button
        ecGbc.gridwidth = 3; // Span across all columns
        ecGbc.fill = GridBagConstraints.HORIZONTAL;
        eastContainer.add(allTilesPanel, ecGbc);






        return eastContainer;

    }

    void changeTileCount(int tileType, int change) {
        int count = 0;
        for(int i = 0; i < 4; i++) {
            count+=tileCounts[i];
        }
        if (count+change > 5){
            return;
        }

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

    void saveLevel() {
        Tile[][] tiles = board.getTiles();


        // Set rotateable based on orientation
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (tiles[i][j] != null) {

                    System.out.println("Tile at: " +i + " " + j + " rotation: " + tiles[i][j].getOrientation());
                    if (tiles[i][j].getOrientation() == 4) {
                        tiles[i][j].setIsRotateable(true);
                        System.out.println("TILE AT: " + i + " " + j + " is rotateable");
                    } else {
                        tiles[i][j].setIsRotateable(false);
                    }
                }
            }
        }

        // Set game info to match
        // game info is array with len 6.
        board.set_game_info(new int[]{tileCounts[0], tileCounts[1], tileCounts[2], tileCounts[3], targets, 0});

        board.saveGameState("temp");

        board.setCardLevel("temp");
        BoardPage boardPage = new BoardPage(mainMenu, true);
        mainMenu.getCardPanel().add(boardPage, "boardPage");
        mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
    }


}