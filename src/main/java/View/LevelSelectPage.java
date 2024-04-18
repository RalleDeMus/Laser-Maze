package View;

import Model.Logic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LevelSelectPage extends JPanel{
    private MainMenu mainMenu;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private int selectedLevel = -1; // Variable to store the selected level, initialized to -1 (no level selected)

    LevelSelectPage(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.add(backButton);

        this.cardPanel = new JPanel();
        this.cardLayout = new CardLayout();
        cardPanel.setLayout(this.cardLayout);

        //JPanel mainMenuPanel = createMainMenuPanel();
        //this.cardPanel.add(mainMenuPanel, "mainMenu");



        add(this.cardPanel, BorderLayout.CENTER);

        JLabel label = new JLabel("Level Select Page", SwingConstants.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(label, BorderLayout.CENTER);

        // Create a panel for the grid of levels
        JPanel gridPanel = new JPanel(new GridLayout(0, 5, 10, 10)); // 0 rows, 5 columns
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add some padding

        // Add 50 level squares to the grid
        for (int i = 1; i <= 50; i++) {
            JButton levelButton = new JButton(String.valueOf(i)); // Button with level number
            levelButton.setPreferredSize(new Dimension(80, 80)); // Set button size
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
                    Board.getInstance().setCardLevel(selectedLevel+"");
                    BoardPage boardPage = new BoardPage(mainMenu,true);
                    mainMenu.getCardPanel().add(boardPage, "boardPage");
                    mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "boardPage");
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

    // Method to get the selected level
    public int getSelectedLevel() {
        return selectedLevel;
    }



}
