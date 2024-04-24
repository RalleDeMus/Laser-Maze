/*
resetter board. Sætter ui op.
Har en function der bliver kaldt et andet sted som opdaterer UI.

 */


package View.Pages;

import Controller.BoardInputHandler;
import Controller.LaserInputHandler;
import Controller.ToolBar;
import Model.Logic.Board;
import View.Renderers.BoardRenderer;
import View.Renderers.LaserToolBarRenderer;
import View.Renderers.TargetRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class BoardPage extends JPanel {


    final private Board board;
    final private JPanel winPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Initialize here

    JTextField textField = new JTextField("test", 10); // 10 columns width


    final private MainMenuPage mainMenu;

    public BoardPage(MainMenuPage mainMenu, boolean includeLaserFeatures, Board board) {
        this.mainMenu = mainMenu;
        // Ensure the Board is accessible
        this.board = board;
        board.resetWin();
        setLayout(new BorderLayout());
        int topPanelHeight = 40;
        initializeUI(mainMenu, topPanelHeight, board.get_game_info_by_index(4));


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

    public void updateWinStatus() { // Make this method more pretty...
        winPanel.removeAll(); // Safely remove all components
        System.out.println("Win: " + board.getWin());
        if (board.getWin()) {

            JLabel winLabel = new JLabel("YOU WIN! " );
            winLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 40));
            winPanel.add(winLabel);

            if (board.get_game_info_by_index(5) != 0) {

                JButton nextLevelButton = getjButton("Next Level");
                nextLevelButton.addActionListener(e -> {
                            try{

                                board.setCardLevel(String.valueOf(Integer.parseInt(board.getLevel())+1));
                            } catch (Exception exception) {
                                board.setCardLevel(String.valueOf(board.get_game_info_by_index(5)+1));
                            }

                            BoardPage boardPage = new BoardPage(mainMenu, true, board);
                            mainMenu.getCardPanel().add(boardPage, "boardPage");
                            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");

                        }
                );
                winPanel.add(nextLevelButton);

            } else if (board.getLevel().equals("temp")){


                JButton nextLevelButton = getjButton("Save and go to Main Menu");
                nextLevelButton.addActionListener(e -> {
                            board.saveTempAs(textField.getText());
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


    public void initializeUI(MainMenuPage mainMenu, int topPanelHeight, int targets) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            Board.saveGameState("game_state",board);
            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu");
        });

        topPanel.add(backButton);


            JLabel levelText = new JLabel("   Level: " + board.getLevel());
            levelText.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
            topPanel.add(levelText);





        if (board.get_game_info_by_index(5) == 0 && board.getLevel().equals("temp")){
            JLabel textLabel = new JLabel("Level name (exit with TAB):");

            // Create a text field with initial text "test"
            textField = new JTextField("LEVEL NAME", 10); // 10 columns width
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    // When focus is lost, we schedule a request to regain focus later
                    SwingUtilities.invokeLater(() -> {
                        // Check some condition or store a flag if you need to control this behavior
                        requestFocus();
                    });
                }
            });

            topPanel.setPreferredSize(new Dimension(getWidth(), topPanelHeight));
            topPanel.add(textLabel);
            topPanel.add(textField); // Add the text field to the panel next to the back button
            topPanel.add(Box.createHorizontalGlue());
        }



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