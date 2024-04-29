package View.Pages;

import Model.Logic.Board;
import Model.Logic.MultiPlayerLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MultiplayerBoardPage extends BoardPage{
    private int centiSecondsPassed = 0;
    private Timer timer;

    private JLabel timeLabel;
    String currentPlayer;

    protected MultiPlayerLogic multiPlayerLogic;

    public MultiplayerBoardPage(MainMenuPage mainMenu, boolean includeLaserFeatures, Board board, MultiPlayerLogic multiPlayerLogic) {
        super(mainMenu, includeLaserFeatures, board);
        this.multiPlayerLogic = multiPlayerLogic;
    }

    public MultiplayerBoardPage(MainMenuPage mainMenu, boolean includeLaserFeatures, Board board,int players) {
        super(mainMenu, includeLaserFeatures, board);
        this.multiPlayerLogic = new MultiPlayerLogic(players);
    }

    @Override
    protected JLabel getTimerText() {
        timeLabel = new JLabel("Time: 0");
        timeLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));


        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                centiSecondsPassed++;
                currentPlayer = multiPlayerLogic.getCurrentPlayer();
                timeLabel.setText("    "+currentPlayer + "    Time: " + centiSecondsPassed / 100.0 + "s");
            }
        });
        timer.start();

        return timeLabel;
    }

    @Override
    protected void stopTimer() {
        timer.stop();
    }

    @Override
    protected JButton goToNextLevelPageButton() {
        JButton nextLevelButton = new JButton("");
        if (multiPlayerLogic.playerEqualPlayers()){
            nextLevelButton.setText("Scoreboard");
        } else {
            nextLevelButton.setText("Next Player");
        }


        nextLevelButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        nextLevelButton.addActionListener(e -> {
            if (multiPlayerLogic.playerEqualPlayers()){
                multiPlayerLogic.addPlayerTime(centiSecondsPassed / 100.0f);

                ScoreboardPage multiBoardPage = new ScoreboardPage(mainMenu, multiPlayerLogic);
                mainMenu.getCardPanel().add(multiBoardPage, "boardPage");
                mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");

            } else {
                multiPlayerLogic.addPlayerTime(centiSecondsPassed / 100.0f);

                try {

                    board.setCardLevel(String.valueOf(Integer.parseInt(board.getLevel())));
                } catch (Exception exception) {
                    board.setCardLevel(String.valueOf(board.get_game_info_by_index(5)));
                }

                ReadyPage readyPage = new ReadyPage(mainMenu, board, multiPlayerLogic);
                mainMenu.getCardPanel().add(readyPage, "boardPage");
                mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
            }
        });
        return nextLevelButton;

    }

}
