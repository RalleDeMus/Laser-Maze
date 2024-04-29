package View.Pages;

import Controller.AssetServer;
import Model.Logic.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
/**
 * The MainMenuPage class is responsible for displaying the main menu and handling the input from the user on the main menu.
 */
public class MainMenuPage extends JFrame implements ActionListener {
    final private JPanel cardPanel;
    final private CardLayout cardLayout;
    /**
     * Constructor for the MainMenuPage class.
     */
    public MainMenuPage() {
        setTitle("Laser Maze");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

// Obtain the background image from AssetServer
        BufferedImage backgroundImage = AssetServer.getInstance().getImage("background");
// Initialize the BackgroundPanel with the image
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setLayout(new BorderLayout());

// Title label at the top
        BufferedImage logoImage = AssetServer.getInstance().getImage("LaserLogo");
        ImageIcon logoIcon = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(logoIcon);
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


    }

    /**
     * Create the main menu panel with all buttons.
     * @return The main menu panel.
     */
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Add buttons with spacing
        panel.add(Box.createVerticalGlue());  // Add glue to center buttons vertically
        addButtonWithSpacing(panel, createAndSetupButton("Continue Game"), 10);
        addButtonWithSpacing(panel, createAndSetupButton("Campaign"), 10);
        addButtonWithSpacing(panel, createAndSetupButton("Level Select"), 10);
        addButtonWithSpacing(panel, createAndSetupButton("Level Maker"), 10);
        addButtonWithSpacing(panel, createAndSetupButton("Multiplayer"), 10);
        addButtonWithSpacing(panel, createAndSetupButton("Custom levels"), 50);
        addButtonWithSpacing(panel, createAndSetupButton("Instructions"), 0);  // Last button with no extra strut below
        panel.add(Box.createVerticalGlue());  // Add glue to center buttons vertically

        return panel;
    }
    private JButton createAndSetupButton(String text) {
        JButton button = createStyledButton(text);  // Assuming createStyledButton already handles button styling
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(this);
        return button;
    }
    private void addButtonWithSpacing(JPanel panel, JButton button, int strutHeight) {
        panel.add(button);
        if (strutHeight > 0) {
            panel.add(Box.createVerticalStrut(strutHeight));  // Adds spacing between buttons
        }
    }

    //create cooler buttons
    private JButton createStyledButton(String text) {
        Color buttonColor = new Color(100, 0, 0);

        Color textColor = new Color(200, 200, 200);
        Color textExitColor = new Color(255, 255, 255);


        Color buttonExitColor = new Color(200, 0, 0);

        Color borderColor = new Color(255, 255, 255);

        JButton button = new JButton(text);
        button.setFont(new Font("Baloo Bhaijaan", Font.BOLD, 45));
        button.setBackground(buttonColor); // Light gray
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 0, true));
        button.setOpaque(true);
        Dimension buttonSize = new Dimension(400, 60);

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

    //action listener for the buttons that open the different pages
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {

            case "Campaign":
                Board board = new Board( "1");
                switchToPanel("campaignPage", new BoardPage(this,true,board));
                break;
            case "Level Select":
                switchToPanel("levelSelectPage", new LevelSelectPage(this));
                break;
            case "Level Maker":
                switchToPanel("levelMakerPage", new LevelMakerPage(this));
                break;
            case "Multiplayer":
                switchToPanel("CustomLevelsPage", new MultiplayerLevelSelectPage(this));
                break;
            case "Instructions":
                switchToPanel("InstructionsPage", new InstructionsPage(this));
                break;
            case "Custom levels":
                switchToPanel("CustomLevelsPage", new CustomLevelsPage(this));
                break;
            case "Continue Game":

                Board board2 = new Board("game_state");
                switchToPanel("lastGame", new BoardPage(this,true, board2));
                break;
        }
    }

    //switch to a specific panel
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





