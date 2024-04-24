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
        // Create the top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        // Add a back button to navigate to the main menu
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            Board.saveGameState("game_state",board);
            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu");
        });
        topPanel.add(backButton);
        
        // Add the level text to display the current level
        JLabel levelText = new JLabel("   Level: " + board.getLevel());
        levelText.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        topPanel.add(levelText);
        
        // Add level name input field if it's a temporary level
        if (board.get_game_info_by_index(5) == 0 && board.getLevel().equals("temp")){
            JLabel textLabel = new JLabel("Level name (exit with TAB):");
            
            textField = new JTextField("LEVEL NAME", 10); // 10 columns width
            textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                SwingUtilities.invokeLater(() -> {
                requestFocus();
                });
            }
            });
            
            topPanel.setPreferredSize(new Dimension(getWidth(), topPanelHeight));
            topPanel.add(textLabel);
            topPanel.add(textField);
            topPanel.add(Box.createHorizontalGlue());
        }
        
        // Create the circle panel to display the targets
        JPanel circlePanel = new JPanel();
        circlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Create a numbered circle to represent the targets
        TargetRender numberedCircle = new TargetRender(targets, new Color(222, 48, 48), Color.WHITE, 60);
        
        // Add a label for the targets
        JLabel normalLabel = new JLabel(" Targets: ");
        normalLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 40));
        
        circlePanel.add(normalLabel);
        circlePanel.add(numberedCircle);
        
        // Create the win panel to display win status and buttons
        winPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        // Set up the grid bag constraints for the circle panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        
        circlePanel.setLayout(new GridBagLayout());
        circlePanel.add(normalLabel, gbc);
        gbc.gridx++;
        circlePanel.add(numberedCircle, gbc);
        
        // Set up the grid bag constraints for the win panel
        gbc.gridx = 0;
        winPanel.setLayout(new GridBagLayout());
        
        // Create a container for the circle panel and win panel
        JPanel southContainer = new JPanel(new BorderLayout());
        southContainer.add(circlePanel, BorderLayout.WEST);
        southContainer.add(winPanel, BorderLayout.EAST);
        
        // Add the top panel and the container to the board page
        add(topPanel, BorderLayout.NORTH);
        add(southContainer, BorderLayout.SOUTH);


    }
}