package View.Pages;

import Model.Logic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class CustomLevelsPage extends JPanel {


    CustomLevelsPage(MainMenuPage mainMenu) {

        setLayout(new GridBagLayout()); // Set the main layout to GridBagLayout

        GridBagConstraints constraints = new GridBagConstraints();

        // Configure and add the back button in the top left corner
        JButton backButton = new JButton("Back");
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

        File customLevelsDir = new File("src/main/levels/custom/");
        if (customLevelsDir.exists() && customLevelsDir.isDirectory()) {
            File[] files = customLevelsDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".json") && !file.getName().equals("game_state.json") && !file.getName().equals("temp.json")) {
                        String levelName = file.getName().replace(".json", "");
                        JButton levelButton = new JButton(levelName);
                        levelButton.addActionListener((ActionEvent e) -> {

                            System.out.println("Selected Level: " + levelName);
                            Board.getInstance().setCardLevel(levelName);
                            BoardPage boardPage = new BoardPage(mainMenu,true);
                            mainMenu.getCardPanel().add(boardPage, "boardPage");
                            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
                        });

                        // Set the preferred size of the button to influence its initial size
                        levelButton.setPreferredSize(new Dimension(200, 40));
                        levelButton.setFont(new Font("Arial", Font.BOLD, 20));

                        // Add each button on a new line, using one-third of the line
                        add(levelButton, levelConstraints);

                        // Increment the row counter after each button
                        levelConstraints.gridy++;
                    }
                }
            }
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
}