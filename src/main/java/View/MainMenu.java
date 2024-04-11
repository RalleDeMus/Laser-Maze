package View;
import Model.Logic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MainMenu() {
        setTitle("Laser Maze");
        setSize(650, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window


        // Set background image
        setContentPane(new JLabel(new ImageIcon("background.png")));
        setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Laser Maze");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        JPanel mainMenuPanel = createMainMenuPanel();
        cardPanel.add(mainMenuPanel, "mainMenu");

        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                // Handle window closing event if needed
                Board.getInstance().saveGameState();
                dispose(); // Dispose of the frame
                System.exit(0); // Exit the application
            }
        });
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton campaignButton = new JButton("Campaign");
        JButton levelSelectButton = new JButton("Level Select");
        JButton levelMakerButton = new JButton("Level Maker");

        campaignButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align buttons horizontally
        levelSelectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelMakerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        campaignButton.addActionListener(this);
        levelSelectButton.addActionListener(this);
        levelMakerButton.addActionListener(this);

        panel.add(Box.createVerticalGlue()); // Add glue to center buttons vertically
        panel.add(campaignButton);
        panel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
        panel.add(levelSelectButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(levelMakerButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Campaign")) {
            BoardPage campaignPage = new BoardPage(this);
            cardPanel.add(campaignPage, "campaignPage");
            cardLayout.show(cardPanel, "campaignPage");
        } else if (e.getActionCommand().equals("Level Select")) {
            LevelSelectPage levelSelectPage = new LevelSelectPage(this);
            cardPanel.add(levelSelectPage, "levelSelectPage");
            cardLayout.show(cardPanel, "levelSelectPage");
        } else if (e.getActionCommand().equals("Level Maker")) {
            LevelMakerPage levelMakerPage = new LevelMakerPage(this);
            cardPanel.add(levelMakerPage, "levelMakerPage");
            cardLayout.show(cardPanel, "levelMakerPage");
        }
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }



}





