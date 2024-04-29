package View.Pages;

import Model.Logic.Board;
import Model.Logic.MultiPlayerLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class ScoreboardPage extends JPanel {

    MainMenuPage mainMenu;
    public ScoreboardPage(MainMenuPage mainMenu, MultiPlayerLogic multiPlayerLogic) {
        this.mainMenu = mainMenu;

        setLayout(new GridBagLayout()); // Set the main layout to GridBagLayout

        GridBagConstraints constraints = new GridBagConstraints();

        // Configure and add the back button in the top left corner
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        constraints.gridx = 0; // Position on the first column
        constraints.gridy = 0; // First row
        constraints.anchor = GridBagConstraints.FIRST_LINE_START; // Anchor to the top-left corner
        constraints.insets = new Insets(5, 5, 5, 5); // Padding
        constraints.weightx = 0; // Do not stretch horizontally
        constraints.weighty = 0; // Do not stretch vertically
        constraints.fill = GridBagConstraints.NONE; // No resizing
        add(backButton, constraints);

        // Add a horizontal glue after the back button to push it to the left
        constraints.gridx = 1; // Position in the second column, next to the back button
        constraints.weightx = 1; // Take up all extra horizontal space
        constraints.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
        add(Box.createHorizontalGlue(), constraints);

        // Reset weightx for the next component
        constraints.weightx = 0;

        // Panel for custom levels with one-third line filling
        GridBagConstraints levelConstraints = new GridBagConstraints();
        levelConstraints.gridx = 0; // Start in the first column
        levelConstraints.gridy = 1; // Start from the second row (after the back button)
        levelConstraints.gridwidth = 2; // Take up one grid cell
        levelConstraints.weightx = 1; // Take up one-third of the line
        levelConstraints.fill = GridBagConstraints.HORIZONTAL; // Stretch to fill the horizontal space
        levelConstraints.insets = new Insets(2, 5, 2, 5); // Padding

        add( GetJLabel("Scoreboard:",30), levelConstraints);
        levelConstraints.gridy++;

        for (int i = 0; i < multiPlayerLogic.getSortedPlayerTimes().size(); i++){
            JLabel playerLabel = GetJLabel(multiPlayerLogic.getSortedPlayerTimes().get(i),20);
            add(playerLabel, levelConstraints);
            levelConstraints.gridy++;
        }


        // Add a filler component to push everything to the top
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.weighty = 1; // Take up all extra vertical space
        constraints.fill = GridBagConstraints.VERTICAL;
        Component filler = Box.createVerticalGlue();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.weighty = 1; // Take up all extra vertical space
        constraints.fill = GridBagConstraints.VERTICAL;
        add(filler, constraints);
    }

    private JLabel GetJLabel(String text, int size){
        JLabel label = new JLabel(text);

        // Set the preferred size of the button to influence its initial size
        label.setPreferredSize(new Dimension(200, 40));
        label.setFont(new Font("Baloo Bhaijaan", Font.BOLD, size));

        return label;
    }

}
