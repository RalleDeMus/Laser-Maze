package View;
import Controller.AssetServer;
import Model.Logic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MainMenu extends JFrame implements ActionListener {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private BackgroundPanel backgroundPanel;
    public MainMenu() {
        setTitle("Laser Maze");
        setSize(650, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Obtain the background image from AssetServer
        BufferedImage backgroundImage = AssetServer.getInstance().getImage("background");
        // Initialize the BackgroundPanel with the image
        backgroundPanel = new BackgroundPanel(backgroundImage);
        setContentPane(backgroundPanel);
        backgroundPanel.setLayout(new BorderLayout());

        // Title label at the top
        JLabel titleLabel = new JLabel("Laser Maze");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Initialize cardPanel with CardLayout
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setOpaque(false); // Make cardPanel transparent

        // Add the main menu panel to cardPanel
        JPanel mainMenuPanel = createMainMenuPanel();
        cardPanel.add(mainMenuPanel, "mainMenu");

        // Add components to the BackgroundPanel
        backgroundPanel.add(titleLabel, BorderLayout.NORTH);
        backgroundPanel.add(cardPanel, BorderLayout.CENTER);

        // Make the frame visible
        setVisible(true);

        // Handle window closing event
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                Board.getInstance().saveGameState();
                dispose();
                System.exit(0);
            }
        });
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton campaignButton = new JButton("Campaign");
        JButton levelSelectButton = new JButton("Level Select");
        JButton levelMakerButton = new JButton("Level Maker");

        campaignButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align buttons horizontally
        levelSelectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelMakerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set preferred size for the buttons
        campaignButton.setPreferredSize(new Dimension(400, 100));
        levelSelectButton.setPreferredSize(new Dimension(400, 100));
        levelMakerButton.setPreferredSize(new Dimension(400, 100));

        JPanel openLastGamePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton openLastGameButton = new JButton("Open Last Game");
        openLastGamePanel.setOpaque(false);
        openLastGameButton.setOpaque(false);
        openLastGameButton.setPreferredSize(new Dimension(150, 30)); // Set preferred size for the button

        openLastGamePanel.add(openLastGameButton);
        panel.add(openLastGamePanel);
        campaignButton.addActionListener(this);
        levelSelectButton.addActionListener(this);
        levelMakerButton.addActionListener(this);
        openLastGameButton.addActionListener(this);

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
        } else if (e.getActionCommand().equals("Open Last Game")) {
            Board.getInstance().setCardLevel("game_state");
            BoardPage lastGame = new BoardPage(this);
            cardPanel.add(lastGame, "lastGame");
            cardLayout.show(cardPanel, "lastGame");
        }
    }

    static class BackgroundPanel extends JPanel {
        private final BufferedImage image;

        public BackgroundPanel(BufferedImage image) {
            this.image = image;
            setPreferredSize(new Dimension(650, 800)); // Make sure the panel prefers to be the size of the frame
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }



}





