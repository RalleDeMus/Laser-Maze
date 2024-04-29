package View.Pages;

import Model.Logic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
/**
 * The CustomLevelsPage class is responsible for displaying the custom levels.
 */
public class CustomLevelsPage extends JPanel {

    private MainMenuPage mainMenu;

    CustomLevelsPage(MainMenuPage mainMenu) {
        this.mainMenu = mainMenu;
        setLayout(new GridBagLayout());

        addBackButton();
        addLevelButtons();
        addVerticalFiller();
    }

    private void addBackButton() {
        GridBagConstraints constraints = new GridBagConstraints();
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        add(backButton, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(Box.createHorizontalGlue(), constraints);
    }

    private void addLevelButtons() {
        GridBagConstraints levelConstraints = new GridBagConstraints();
        levelConstraints.gridx = 0;
        levelConstraints.gridy = 1;
        levelConstraints.gridwidth = 2;
        levelConstraints.weightx = 1;
        levelConstraints.fill = GridBagConstraints.HORIZONTAL;
        levelConstraints.insets = new Insets(2, 5, 2, 5);

        File customLevelsDir = new File("src/main/levels/custom/");
        if (customLevelsDir.exists() && customLevelsDir.isDirectory()) {
            File[] files = customLevelsDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".json") && !file.getName().equals("game_state.json") && !file.getName().equals("temp.json")) {
                        String levelName = file.getName().replace(".json", "");
                        JButton levelButton = createLevelButton(levelName);
                        add(levelButton, levelConstraints);
                        levelConstraints.gridy++;
                    }
                }
            }
        }
    }

    private JButton createLevelButton(String levelName) {
        JButton levelButton = new JButton(levelName);
        levelButton.addActionListener(e -> {
            System.out.println("Selected Level: " + levelName);
            Board board = new Board(levelName);
            BoardPage boardPage = new BoardPage(mainMenu, true, board);
            mainMenu.getCardPanel().add(boardPage, "boardPage");
            mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
        });
        levelButton.setPreferredSize(new Dimension(200, 40));
        levelButton.setFont(new Font("Arial", Font.BOLD, 20));
        return levelButton;
    }

    private void addVerticalFiller() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.VERTICAL;
        Component filler = Box.createVerticalGlue();
        add(filler, constraints);
    }
}