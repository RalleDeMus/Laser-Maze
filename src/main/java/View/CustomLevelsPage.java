package View;

import Model.Logic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class CustomLevelsPage extends JPanel {
    private MainMenu mainMenu;

    CustomLevelsPage(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        setLayout(new GridBagLayout()); // Set the main layout to GridBagLayout

        GridBagConstraints constraints = new GridBagConstraints();

        // Configure and add the back button in the top left corner
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        constraints.gridx = 0; // Position on the first column
        constraints.gridy = 0; // First row
        constraints.anchor = GridBagConstraints.NORTHWEST; // Anchor to the top-left corner
        constraints.insets = new Insets(5, 5, 5, 5); // Padding
        constraints.weightx = 0; // Do not stretch horizontally
        constraints.fill = GridBagConstraints.NONE; // No resizing
        add(backButton, constraints);

        // Panel for custom levels with left alignment
        GridBagConstraints levelConstraints = new GridBagConstraints();
        levelConstraints.gridx = 0; // Start in the first column
        levelConstraints.gridy = 0; // Initialize at the first row (increment before adding)
        levelConstraints.anchor = GridBagConstraints.WEST; // Anchor to the left
        levelConstraints.fill = GridBagConstraints.HORIZONTAL; // Stretch to fill the horizontal space
        levelConstraints.insets = new Insets(2, 5, 2, 5); // Padding
        levelConstraints.weightx = 1; // Give extra horizontal space to the cell

        File customLevelsDir = new File("src/main/levels/custom/");
        if (customLevelsDir.exists() && customLevelsDir.isDirectory()) {
            File[] files = customLevelsDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".json") && !file.getName().equals("game_state.json") && !file.getName().equals("temp.json")) {
                        String levelName = file.getName().replace(".json", "");
                        JButton levelButton = new JButton(levelName);
                        levelButton.addActionListener((ActionEvent e) -> {
                            System.out.println("Pressed level " + levelName);

                            System.out.println("Selected Level: " + levelName);
                            Board.getInstance().setCardLevel(levelName);
                            BoardPage boardPage = new BoardPage(mainMenu,true);
                            mainMenu.getCardPanel().add(boardPage, "boardPage");
                            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
                        });

                        // Increment row for each button, starting from the second row
                        levelConstraints.gridy++;

                        // Add each button to the custom levels panel
                        add(levelButton, levelConstraints);
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
        add(filler, constraints);
    }
}