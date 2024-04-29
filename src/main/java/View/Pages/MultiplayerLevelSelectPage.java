package View.Pages;

import Model.Logic.Board;

import javax.swing.*;
import java.awt.*;

public class MultiplayerLevelSelectPage extends LevelSelectPage{

    SpinnerModel spinnerModel; // initial value, min, max, step


    public MultiplayerLevelSelectPage(MainMenuPage mainMenuPage){
        super(mainMenuPage);
    }

    @Override
    protected JPanel getTopPanel(MainMenuPage mainMenu) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.add(backButton);

        JLabel textLabel = new JLabel("Please select a level and number of players: ");
        textLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        topPanel.add(textLabel);

        spinnerModel = new SpinnerNumberModel(2, 2, 10, 1);

        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));

        topPanel.add(spinner);

        return  topPanel;
    }

    @Override
    protected void goToBoardPage(MainMenuPage mainMenu, Board board) {
        ReadyPage readyPage = new ReadyPage(mainMenu, board, (Integer) spinnerModel.getValue());
        mainMenu.getCardPanel().add(readyPage, "boardPage");
        mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
    }




}
