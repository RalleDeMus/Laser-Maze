package View.Pages;

import Controller.*;
import Model.Logic.Board;
import Model.Logic.JSONSaving;
import Model.Logic.LevelMakerLogic;
import Model.Tiles.TileInfo;
import View.Renderers.BoardRenderer;
import View.Renderers.ImageOverlayNumber;
import View.Renderers.TargetRender;
import io.cucumber.java.sl.In;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * The LevelMakerPage class is responsible for displaying the UI for the level maker.
 */
public class LevelMakerPage extends JPanel {


    final private Board board;

    final private MainMenuPage mainMenu;

    final private LevelMakerLogic levelMakerLogic;

    final private Map<String,ImageOverlayNumber> tileLabels = new HashMap<>();

    private TargetRender targetCircle = new TargetRender(0, new Color(222, 48, 48), Color.WHITE, 60);

    final private AssetServer assetServer = AssetServer.getInstance();

    /**
     * Constructor for the LevelMakerPage class.
     * @param mainMenu The main menu page to navigate back to.
     */
    public LevelMakerPage(MainMenuPage mainMenu) {
        levelMakerLogic = new LevelMakerLogic();
        this.mainMenu = mainMenu;
        // Ensure the Board is accessible
        board = new Board("0");

        board.setCardLevel("0");
        BoardPage boardPage = new BoardPage(mainMenu,true, board);
        mainMenu.getCardPanel().add(boardPage, "boardPage");
        mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");

        board.resetWin();
        setLayout(new BorderLayout());
        int topPanelHeight = 40;


        // Setup based on feature inclusion
        BoardRenderer renderer;

        renderer = new BoardRenderer(board);
        new LevelMakerInputHandler(board, this, topPanelHeight, false);
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
        targetCircle.setTargets(levelMakerLogic.getTargets());
        targetCircle.revalidate();
        targetCircle.repaint();
    }




    public void initializeUI(MainMenuPage mainMenu, int topPanelHeight, BoardRenderer renderer) {
        setLayout(new GridBagLayout());

        // Add top panel
        JPanel topPanel = createTopPanel(mainMenu, topPanelHeight);
        addComponent(topPanel, 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0));

        // Add renderer
        addComponent(renderer, 0, 1, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0));

        // Add east container
        JPanel eastContainer = createEastContainer();
        addComponent(eastContainer, 2, 1, 1, 1, 0, 1.0, GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0));

        // Add circle panel
        JPanel circlePanel = createCirclePanel();
        addComponent(circlePanel, 0, 3, 1, 1, 0, 0, GridBagConstraints.SOUTHWEST, GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 0));

        // Add "Save and play level" button
        JButton newButton = createButton("Save and play level", new Font("Baloo Bhaijaan", Font.PLAIN, 20), new Dimension(200, 40), e -> {
            saveLevel();
        });
        addComponent(newButton, 1, 3, GridBagConstraints.REMAINDER, 1, 0, 0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(10, 10, 10, 10));
    }

    private JPanel createTopPanel(MainMenuPage mainMenu, int topPanelHeight) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = createButton("Back", new Font("Baloo Bhaijaan", Font.PLAIN, 20), null, e -> {
            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu");
        });
        topPanel.setPreferredSize(new Dimension(getWidth(), topPanelHeight));
        topPanel.add(backButton);
        topPanel.add(Box.createHorizontalGlue());
        return topPanel;
    }

    private void addComponent(JComponent component, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = insets;
        add(component, gbc);
    }


    //update the displayed tile count for extra tiles
    void updateTileCount() {
        levelMakerLogic.getTileCounts().forEach((key, value) -> {
            tileLabels.get(key).setNumber(value);
            tileLabels.get(key).revalidate();
            tileLabels.get(key).repaint();
        });
    }

    //create the circle panel for the targets
    private JPanel createCirclePanel() {
        JPanel circlePanel = new JPanel(new GridBagLayout());

        // Add "Targets:" label
        JLabel normalLabel = createLabel("Targets: ", new Font("Baloo Bhaijaan", Font.PLAIN, 40));
        circlePanel.add(normalLabel, getDefaultGBC(0, 0));

        // Add target circle
        targetCircle = createTargetCircle();
        circlePanel.add(targetCircle, getDefaultGBC(1, 0));

        // Add minus button
        JButton minusButton = createButton("-", new Font("Baloo Bhaijaan", Font.BOLD, 20), new Dimension(40, 40), e -> {
            levelMakerLogic.decrementTargets();
            updateTargetCounter();
            LevelMakerPage.this.requestFocusInWindow();
        });
        circlePanel.add(minusButton, getDefaultGBC(2, 0));

        // Add plus button
        JButton plusButton = createButton("+", new Font("Baloo Bhaijaan", Font.BOLD, 20), new Dimension(40, 40), e -> {
            levelMakerLogic.incrementTargets();
            updateTargetCounter();
            LevelMakerPage.this.requestFocusInWindow();
        });
        circlePanel.add(plusButton, getDefaultGBC(3, 0));

        return circlePanel;
    }

    private TargetRender createTargetCircle() {
        // Assuming TargetRender is a valid class that extends JComponent
        return new TargetRender(levelMakerLogic.getTargets(), new Color(222, 48, 48), Color.WHITE, 60);
    }

    private GridBagConstraints getDefaultGBC(int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        return gbc;
    }

    //Create a small area to show what buttons to push for the different tiles
    private JPanel createEastContainer() {
        JPanel eastContainer = new JPanel(new GridBagLayout());
        GridBagConstraints ecGbc = new GridBagConstraints();
    
        JLabel textTestLabel = createLabel("Tiles for toolbar:", new Font("Baloo Bhaijaan", Font.BOLD, 20));
        eastContainer.add(textTestLabel, getGridBagConstraints(0, 0, 3, GridBagConstraints.HORIZONTAL, 0, new Insets(0, 0, 10, 0)));
    
        // Create and add 5 tiles with plus and minus buttons
        for (int i = 0; i < TileInfo.getTiles(true).size(); i++) {
            addTileWithButtons(eastContainer, TileInfo.getTiles(true).get(i).getClass().getSimpleName(), i, getGridBagConstraints(0, i + 1, 1, GridBagConstraints.NONE, 0, new Insets(0, 0, 0, 5)));
        }


    
        textTestLabel = createLabel("Keys for each tile:", new Font("Baloo Bhaijaan", Font.BOLD, 20));
        eastContainer.add(textTestLabel, getGridBagConstraints(0, 6, 3, GridBagConstraints.HORIZONTAL, 0, new Insets(10, 0, 0, 0)));
    
        BufferedImage allTilesImage = assetServer.getImage("allTiles");
        Image scaledAllTilesImage = allTilesImage.getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        JLabel allTilesLabel = new JLabel(new ImageIcon(scaledAllTilesImage));
        JPanel allTilesPanel = new JPanel();
        allTilesPanel.add(allTilesLabel);
        eastContainer.add(allTilesPanel, getGridBagConstraints(0, 7, 3, GridBagConstraints.HORIZONTAL, 0, new Insets(0, 0, 0, 0)));
    
        return eastContainer;
    }

    private GridBagConstraints getGridBagConstraints(int gridx, int gridy, int gridwidth, int fill, int weightx, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.insets = insets;
        return gbc;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JButton createButton(String text, Font font, Dimension size, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setPreferredSize(size);
        button.addActionListener(action);
        return button;
    }

    private void addTileWithButtons(JPanel panel, String type, Integer index, GridBagConstraints gbc) {
        // Tile image with number
        BufferedImage tileImage = TileInfo.TileFromKey(type).getImage();
        Image scaledImage = tileImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        ImageIcon tileIcon = new ImageIcon(scaledImage);

        ImageOverlayNumber tileLabel = new ImageOverlayNumber(tileIcon, 0, 50, 50);
        tileLabel.setFont(new Font("Baloo Bhaijaan", Font.BOLD, 40));
        tileLabels.put(type, tileLabel);

        JButton minusButton = createButton("-", new Font("Baloo Bhaijaan", Font.BOLD, 20), new Dimension(35, 35), (ActionListener) e -> {
            levelMakerLogic.changeTileCount(type, false);
            LevelMakerPage.this.requestFocusInWindow();
            updateTileCount();
        });

        JButton plusButton = createButton("+", new Font("Baloo Bhaijaan", Font.BOLD, 20), new Dimension(35, 35), (ActionListener) e -> {
            levelMakerLogic.changeTileCount(type, true);
            LevelMakerPage.this.requestFocusInWindow();
            updateTileCount();
        });

        panel.add(tileLabels.get(type), gbc);
        panel.add(minusButton, getGridBagConstraints(1, index+1, 1, GridBagConstraints.NONE, 0, new Insets(0, 0, 0, 5)));
        panel.add(plusButton, getGridBagConstraints(2, index+1, 1, GridBagConstraints.NONE, 0, new Insets(0, 0, 0, 5)));
    }


    //get the image for the tile
//    private BufferedImage getTileImage (int tileType) {
//        BufferedImage tileImage = null;
//
//        switch (tileType) {
//            case 0:
//                tileImage = assetServer.getImage("targetMirror");
//                break;
//            case 1:
//                tileImage = assetServer.getImage("beamSplitter");
//                break;
//            case 2:
//                tileImage = assetServer.getImage("checkPoint");
//                break;
//            case 3:
//                tileImage = assetServer.getImage("doubleMirror");
//                break;
//            case 4:
//                tileImage = assetServer.getImage("laser");
//                break;
//        }
//        return tileImage;
//    }

    //save the level and start playing it
    private void saveLevel() {
        JSONSaving.saveLevelWithFreeRotations(board, levelMakerLogic.getTileCounts(), levelMakerLogic.getTargets());

        board.setCardLevel("temp");
        BoardPage boardPage = new BoardPage(mainMenu, true,board);
        mainMenu.getCardPanel().add(boardPage, "boardPage");
        mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }


}
