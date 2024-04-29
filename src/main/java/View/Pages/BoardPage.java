/*
resetter board. Sætter ui op.
Har en function der bliver kaldt et andet sted som opdaterer UI.

 */


package View.Pages;

import Controller.BoardInputHandler;
import Controller.LaserInputHandler;
import Controller.ToolBar;
import Model.Logic.Board;
import Model.Logic.JSONSaving;
import View.Renderers.BoardRenderer;
import View.Renderers.LaserToolBarRenderer;
import View.Renderers.TargetRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
/**
 * The BoardPage class is responsible for displaying the board and handling the input from the user on the board.
 */

public class BoardPage extends JPanel {


    final protected Board board;
    final private JPanel winPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Initialize here

    JTextField textField = new JTextField("test", 10); // 10 columns width


    final protected MainMenuPage mainMenu;

    /**
     * Constructor for the BoardPage class.
     * @param mainMenu The main menu page to navigate back to.
     * @param includeLaserFeatures Whether to include laser features in the board.
     * @param board The board to display.
     */
    public BoardPage(MainMenuPage mainMenu, boolean includeLaserFeatures, Board board) {
        this.mainMenu = mainMenu;
        // Ensure the Board is accessible
        this.board = board;
        board.resetWin();
        setLayout(new BorderLayout());
        int topPanelHeight = 40;

        initializeUI(topPanelHeight, board.get_game_info_by_index(4));


        // Setup based on feature inclusion
        BoardRenderer renderer;
        if (includeLaserFeatures) {
            renderer = new LaserToolBarRenderer(board);
            new LaserInputHandler(board, this, topPanelHeight, true);
            new ToolBar(board, this, topPanelHeight, renderer);
        } else {
            renderer = new BoardRenderer(board);
            new BoardInputHandler(board, this, topPanelHeight, true);
        }

        add(renderer);
        this.setFocusable(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                BoardPage.this.requestFocusInWindow();
            }
        });
    }

    // Initialize the UI of the board page
    public void initializeUI(int topPanelHeight, int targets) {
        setupTopPanel(topPanelHeight);
        setupTargetDisplay(targets);
        setupWinPanel();

    }


    // Creating the Top Panel
    private void setupTopPanel(int topPanelHeight) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = createBackButton();
        topPanel.add(backButton);

        JLabel levelText = createLevelLabel();
        topPanel.add(levelText);

        if (board.get_game_info_by_index(5) == 0 && board.getLevel().equals("temp")) {
            addTemporaryLevelFields(topPanel);
        }

        topPanel.add(getMultiPlayerText());
        add(topPanel, BorderLayout.NORTH);
    }

    // Only get text if it is a multiplayer game
    protected JLabel getMultiPlayerText() {
        return new JLabel("");
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        backButton.addActionListener(e -> {
            JSONSaving.saveGameState("game_state", board);
            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu");
        });
        return backButton;
    }

    private JLabel createLevelLabel() {
        JLabel levelText = new JLabel("   Level: " + board.getLevel(), JLabel.LEFT);
        levelText.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));

        return levelText;
    }

    private void addTemporaryLevelFields(JPanel panel) {
        JLabel textLabel = new JLabel("Level name (exit with TAB):");
        textField.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                    requestFocus();
                });
            }
        });
        panel.add(textLabel);
        panel.add(textField);
        panel.add(Box.createHorizontalGlue());
    }

    // Setting Up the Target Display
    private void setupTargetDisplay(int targets) {
        JPanel circlePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel normalLabel = new JLabel(" Targets: ", JLabel.LEFT);
        normalLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 40));
        circlePanel.add(normalLabel, gbc);

        TargetRender numberedCircle = new TargetRender(targets, new Color(222, 48, 48), Color.WHITE, 60);
        gbc.gridx++;
        circlePanel.add(numberedCircle, gbc);

        JPanel southContainer = new JPanel(new BorderLayout());
        southContainer.add(circlePanel, BorderLayout.WEST);
        southContainer.add(winPanel, BorderLayout.EAST);
        add(southContainer, BorderLayout.SOUTH);
    }

    // Configuring the Win Panel
    private void setupWinPanel() {
        winPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
    }

    // Update the win status of the board and notify the player that they have won
    public void updateWinStatus() { // Make this method more pretty...
        winPanel.removeAll(); // Safely remove all components
        System.out.println("Win: " + board.getWin());
        if (board.getWin()) {

            JLabel winLabel = new JLabel("YOU WIN! ");
            winLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 40));
            winPanel.add(winLabel);

            stopTimer();

            //check if the player is playing a newly made custom level
            if (board.get_game_info_by_index(5) != 0) {

                JButton nextLevelButton = goToNextLevelPageButton();
                winPanel.add(nextLevelButton);

            } else if (board.getLevel().equals("temp")){


                JButton nextLevelButton = getjButton("Save and go to Main Menu");
                nextLevelButton.addActionListener(e -> {
                            JSONSaving.saveTempAs(textField.getText());
                            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu");
                        }
                );
                winPanel.add(nextLevelButton);
            } else {


                JButton nextLevelButton = getjButton("Go to Main Menu");
                nextLevelButton.addActionListener(e -> {
                            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu");
                        }
                );
                winPanel.add(nextLevelButton);
            }
        }
        winPanel.revalidate();
        winPanel.repaint();
    }

    private JButton getjButton(String buttonText) {
        JButton nextLevelButton = new JButton(buttonText);
        nextLevelButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));

        return nextLevelButton;
    }

    // when a level is completed the user can go to the next level
    protected JButton goToNextLevelPageButton() {
        JButton nextLevelButton = new JButton("Next Level");


        nextLevelButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        nextLevelButton.addActionListener(e -> {
            try{

                board.setCardLevel(String.valueOf(Integer.parseInt(board.getLevel())+1));
            } catch (Exception exception) {
                board.setCardLevel(String.valueOf(board.get_game_info_by_index(5)+1));
            }

            BoardPage boardPage = new BoardPage(mainMenu, true, board);
            mainMenu.getCardPanel().add(boardPage, "boardPage");
            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
        });
        return nextLevelButton;

    }

    // Setup method to be overwritten by subclasses
    protected void stopTimer() {
    }




}