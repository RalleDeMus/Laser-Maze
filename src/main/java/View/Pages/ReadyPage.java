package View.Pages;

import Model.Logic.Board;
import Model.Logic.MultiPlayerLogic;

import javax.swing.*;
import java.awt.*;
/**
 * The ReadyPage class is responsible for displaying the ready page for multiplayer mode.
 * The page is made so the players can choose when they are ready to play.
 */
public class ReadyPage extends JPanel {

    MultiPlayerLogic multiPlayerLogic;

    MainMenuPage mainMenu;

    Board board;
    /**
     * Create the ready page for multiplayer mode
     * @param mainMenu The main menu page
     * @param board The board to play on
     * @param multiPlayerLogic The multiplayer logic
     */
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

    private void setup() {
        setLayout(new GridBagLayout()); // Set the layout for the panel

        // Add components to the panel
        addTopPanel();
        addCurrentPlayerLabel();
        addReadyButton();
    }

    private void addTopPanel() {
        GridBagConstraints gbc = createDefaultConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        // Assuming addTopPanel method adds the component and sets the constraints
        addTopPanel(gbc);
    }

    private void addCurrentPlayerLabel() {
        GridBagConstraints gbc = createDefaultConstraints();
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel currentPlayerLabel = new JLabel(multiPlayerLogic.getCurrentPlayer());
        currentPlayerLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 30));
        add(currentPlayerLabel, gbc);
    }

    private void addReadyButton() {
        GridBagConstraints gbc = createDefaultConstraints();
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 0, 200, 0);

        addReadyButton(gbc); // Assuming addReadyButton method adds the button and sets the constraints
    }

    private GridBagConstraints createDefaultConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Default column
        gbc.fill = GridBagConstraints.NONE; // Default fill
        gbc.weighty = 0; // Default weighty
        gbc.weightx = 0; // Default weightx, overridden when necessary

        return gbc;
    }

    private void addTopPanel(GridBagConstraints constraints) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.add(backButton);

        add(topPanel, constraints);  // add the top panel to the main panel with constraints
    }
    // Add the ready button to the panel and start the game when pressed
    private void addReadyButton(GridBagConstraints constraints) {
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
