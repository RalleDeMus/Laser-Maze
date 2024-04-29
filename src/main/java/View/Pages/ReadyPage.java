package View.Pages;

import Model.Logic.Board;
import Model.Logic.MultiPlayerLogic;

import javax.swing.*;
import java.awt.*;

public class ReadyPage extends JPanel {

    MultiPlayerLogic multiPlayerLogic;

    MainMenuPage mainMenu;

    Board board;

    public ReadyPage(MainMenuPage mainMenu, Board board, MultiPlayerLogic multiPlayerLogic) {
        this.multiPlayerLogic = multiPlayerLogic;
        this.mainMenu = mainMenu;
        this.board = board;
        setup();
    }

    public ReadyPage(MainMenuPage mainMenu, Board board,  int players) {
        this.multiPlayerLogic = new MultiPlayerLogic(players);
        this.mainMenu = mainMenu;
        this.board  = board;
        setup();
    }

    public void setup() {
        setLayout(new GridBagLayout()); // Ensure this panel uses GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();

        // Constraints for the top panel with the back button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // This should only take up the space it needs
        gbc.anchor = GridBagConstraints.NORTHWEST; // Anchor to the top-left corner
        gbc.weightx = 0; // Do not stretch horizontally
        gbc.weighty = 1; // Do not stretch vertically
        gbc.fill = GridBagConstraints.NONE; // No resizing
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        addTopPanel(gbc);

        // Constraints for the ready button
        gbc.gridx = 0; // Center horizontally in the panel
        gbc.gridy = 1; // The second row
        gbc.weightx = 1; // Stretch to fill the panel width
        gbc.weighty = 0; // Stretch vertically to push it to the middle
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Take up the remainder of the row
        gbc.fill = GridBagConstraints.NONE; // Fill horizontally
        gbc.anchor = GridBagConstraints.CENTER; // Center this component
        JLabel currentPlayerLabel = new JLabel(multiPlayerLogic.getCurrentPlayer());
        currentPlayerLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 30));
        add(currentPlayerLabel, gbc);

        // Constraints for the ready button
        gbc.gridx = 0; // Center horizontally in the panel
        gbc.gridy = 2; // The second row
        gbc.weightx = 1; // Stretch to fill the panel width
        gbc.weighty = 0; // Stretch vertically to push it to the middle
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Take up the remainder of the row
        gbc.fill = GridBagConstraints.NONE; // Fill horizontally
        gbc.anchor = GridBagConstraints.CENTER; // Center this component
        gbc.insets = new Insets(0, 0, 200, 0); // Reduce vertical padding

        addReadyButton(gbc);

    }

    protected void addTopPanel(GridBagConstraints constraints) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.add(backButton);

        add(topPanel, constraints);  // add the top panel to the main panel with constraints
    }

    protected void addReadyButton(GridBagConstraints constraints) {
        JButton readyButton = new JButton("Ready");
        readyButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 50));

        readyButton.addActionListener(e -> {
            MultiplayerBoardPage multiplayerBoardPage = new MultiplayerBoardPage(mainMenu, true, board, multiPlayerLogic);
            mainMenu.getCardPanel().add(multiplayerBoardPage, "boardPage");
            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
        }
        );

        add(readyButton, constraints); // Add with constraints
    }

}
