package View.Pages;

import Model.Logic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The LevelSelectPage class is responsible for displaying the level select page.
 */
public class LevelSelectPage extends JPanel{

    private int selectedLevel = -1; // Variable to store the selected level, initialized to -1 (no level selected)

    /**
     * Constructor for the LevelSelectPage class.
     * @param mainMenu The main menu page to navigate back to.
     */
    LevelSelectPage(MainMenuPage mainMenu) {

        setLayout(new BorderLayout());

        JPanel cardPanel = new JPanel();
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        add(cardPanel, BorderLayout.CENTER);

        JLabel label = new JLabel("Level Select Page", SwingConstants.CENTER);
        add(getTopPanel(mainMenu), BorderLayout.NORTH);
        add(label, BorderLayout.CENTER);

        // Create a panel for the grid of levels
        JPanel gridPanel = new JPanel(new GridLayout(0, 5, 10, 10)); // 0 rows, 5 columns
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add some padding

        // Add 25 level squares to the grid
        for (int i = 1; i <= 25; i++) {
            JButton levelButton = new JButton(String.valueOf(i)); // Button with level number
            levelButton.setPreferredSize(new Dimension(160, 80)); // Set button size
            levelButton.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
            levelButton.setBackground(Color.WHITE); // Set background color
            levelButton.setForeground(Color.BLACK); // Set text color
            levelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Set border

            // Add ActionListener to each button
            levelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Get the text of the button (level number) and save it to selectedLevel
                    selectedLevel = Integer.parseInt(((JButton) e.getSource()).getText());
                    System.out.println("Selected Level: " + selectedLevel);
                    //Board.getInstance().setCardLevel(selectedLevel+"");
                    Board board = new Board(String.valueOf(selectedLevel));

                    goToBoardPage(mainMenu, board);

                }
            });

            // Add hover effect
            levelButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    levelButton.setBackground(Color.LIGHT_GRAY);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    levelButton.setBackground(Color.WHITE);
                }
            });

            gridPanel.add(levelButton); // Add button to grid
        }

        // Add the grid panel to the center of the LevelSelectPage
        add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Get the top panel of the LevelSelectPage.
     * @param mainMenu The main menu page to navigate back to.
     * @return The top panel of the LevelSelectPage.
     */
    protected JPanel getTopPanel(MainMenuPage mainMenu) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.add(backButton);
        JLabel textLabel = new JLabel("Please select a level: ");
        textLabel.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        topPanel.add(textLabel);
        return  topPanel;
    }
    /**
     * Go to the board page with the selected level.
     * @param mainMenu The main menu page to navigate back to.
     * @param board The board to go to.
     */
    protected void goToBoardPage(MainMenuPage mainMenu, Board board) {
        BoardPage boardPage = new BoardPage(mainMenu,true, board);
        mainMenu.getCardPanel().add(boardPage, "boardPage");
        mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
    }





}
