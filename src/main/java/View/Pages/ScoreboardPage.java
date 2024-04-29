package View.Pages;

import Model.Logic.Board;
import Model.Logic.MultiPlayerLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
/**
 * The ScoreboardPage class is responsible for displaying the scoreboard page for multiplayer mode.
 * The scoreboard is displayed after a multiplayer game has ended.
 */
public class ScoreboardPage extends JPanel {

    private final MainMenuPage mainMenu;
    private final MultiPlayerLogic multiPlayerLogic;

    public ScoreboardPage(MainMenuPage mainMenu, MultiPlayerLogic multiPlayerLogic) {
        this.mainMenu = mainMenu;
        this.multiPlayerLogic = multiPlayerLogic;

        setLayout(new GridBagLayout()); // Set the main layout to GridBagLayout
        addBackButton();
        addHorizontalGlue();
        addScoreboardLabels();
        addVerticalFiller();
    }

    private void addBackButton() {
        GridBagConstraints constraints = createDefaultConstraints();
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(5, 5, 5, 5);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        add(backButton, constraints);
    }

    private void addHorizontalGlue() {
        GridBagConstraints constraints = createDefaultConstraints();
        constraints.gridx = 1;
        constraints.weightx = 1; // Take up all extra horizontal space
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(Box.createHorizontalGlue(), constraints);
    }

    private void addScoreboardLabels() {
        GridBagConstraints constraints = createDefaultConstraints();
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(2, 5, 2, 5);

        add(GetJLabel("Scoreboard:", 30), constraints);
        constraints.gridy++;

        for (String playerTime : multiPlayerLogic.getSortedPlayerTimes()) {
            JLabel playerLabel = GetJLabel(playerTime, 20);
            add(playerLabel, constraints);
            constraints.gridy++;
        }
    }

    private void addVerticalFiller() {
        GridBagConstraints constraints = createDefaultConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        add(Box.createVerticalGlue(), constraints);
    }

    private GridBagConstraints createDefaultConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        return constraints;
    }

    private JLabel GetJLabel(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Baloo Bhaijaan", Font.BOLD, size));
        label.setPreferredSize(new Dimension(200, 40));
        return label;
    }

}
