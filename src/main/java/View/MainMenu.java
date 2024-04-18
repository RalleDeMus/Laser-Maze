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

    JLabel logoLabel = new JLabel();


    public MainMenu() {
        setTitle("Laser Maze");
        setSize(650, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

// Obtain the background image from AssetServer
        BufferedImage backgroundImage = AssetServer.getInstance().getImage("background");
// Initialize the BackgroundPanel with the image
        backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setLayout(new BorderLayout());

// Title label at the top
        BufferedImage logoImage = AssetServer.getInstance().getImage("LaserLogo");
        ImageIcon logoIcon = new ImageIcon(logoImage);
        logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

// Initialize cardPanel with CardLayout
        cardLayout = new CardLayout();  // Make sure this is initialized before it's used
        cardPanel = new JPanel(cardLayout);  // Assign the layout manager when creating the panel
        cardPanel.setOpaque(false); // Make cardPanel transparent

// Add the main menu panel to cardPanel
        JPanel mainMenuPanel = createMainMenuPanel();
        cardPanel.add(mainMenuPanel, "mainMenu");

// Create a main content panel and add the BackgroundPanel to it
        JPanel mainContent = new JPanel(new BorderLayout());
        setContentPane(mainContent);
        mainContent.add(backgroundPanel, BorderLayout.CENTER);

// Add the logo label and card panel to the BackgroundPanel
        backgroundPanel.add(logoLabel, BorderLayout.NORTH);
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

        JButton openLastGameButton = createStyledButton("Continue Game");
        JButton campaignButton = createStyledButton("Campaign");
        JButton levelSelectButton = createStyledButton("Level Select");
        JButton levelMakerButton = createStyledButton("Level Maker");
        JButton InstructionsButton = createStyledButton("Instructions");

        openLastGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        campaignButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align buttons horizontally
        levelSelectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelMakerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InstructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);


//        // Set preferred size for the buttons
//        openLastGameButton.setPreferredSize(new Dimension(400, 60));
//        campaignButton.setPreferredSize(new Dimension(400, 60));
//        levelSelectButton.setPreferredSize(new Dimension(400, 60));
//        levelMakerButton.setPreferredSize(new Dimension(400, 60));
//        InstructionsButton.setPreferredSize(new Dimension(400, 60));


        openLastGameButton.addActionListener(this);
        campaignButton.addActionListener(this);
        levelSelectButton.addActionListener(this);
        levelMakerButton.addActionListener(this);
        InstructionsButton.addActionListener(this);


        panel.add(Box.createVerticalGlue()); // Add glue to center buttons vertically
        panel.add(openLastGameButton);
        panel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
        panel.add(campaignButton);
        panel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
        panel.add(levelSelectButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(levelMakerButton);
        panel.add(Box.createVerticalGlue());
        panel.add(InstructionsButton);
        panel.add(Box.createVerticalGlue());


        return panel;
    }

    private JButton createStyledButton(String text) {
        Color buttonColor = new Color(100, 0, 0);

        Color textColor = new Color(200, 200, 200);
        Color textExitColor = new Color(255, 255, 255);


        Color buttonExitColor = new Color(200, 0, 0);

        Color borderColor = new Color(255, 255, 255);

        JButton button = new JButton(text);
        button.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 45));
        button.setBackground(buttonColor); // Light gray
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 0, true));
        button.setOpaque(true);
        Dimension buttonSize = new Dimension(400, 60);
        Dimension buttonExitSize = new Dimension(420, 70);

        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setSize(buttonSize);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(buttonExitColor); // Slightly darker gray on hover
                button.setForeground(textExitColor);

                button.setBorder(BorderFactory.createLineBorder(borderColor, 3, true));  // Thicker border on hover

                button.getParent().revalidate(); // Recalculate the layout
                button.getParent().repaint(); // Refresh display

            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(buttonColor);
                button.setForeground(textColor);

                button.setBorder(BorderFactory.createLineBorder(borderColor, 0, true));  // Thicker border on hover

                button.getParent().revalidate(); // Recalculate the layout
                button.getParent().repaint(); // Refresh display
            }
        });
        button.addActionListener(this);
        return button;
    }


    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Campaign":
                switchToPanel("campaignPage", new BoardPage(this,true));
                break;
            case "Level Select":
                switchToPanel("levelSelectPage", new LevelSelectPage(this));
                break;
            case "Level Maker":
                switchToPanel("levelMakerPage", new LevelMakerPage(this));
                break;
            case "Instructions":
                switchToPanel("InstructionsPage", new InstructionsPage(this));
                break;
            case "Open Last Game":
                Board.getInstance().setCardLevel("game_state");
                switchToPanel("lastGame", new BoardPage(this,true));
                break;
        }
    }

    private void switchToPanel(String name, JPanel panel) {
        cardPanel.add(panel, name);
        cardLayout.show(cardPanel, name);
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





