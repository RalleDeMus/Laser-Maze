package View;
import Model.Logic.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        // Create a label with an ImageIcon and set it as the content pane
        JLabel backgroundLabel = new JLabel(new ImageIcon("background.png"));
        setContentPane(backgroundLabel);
        setLayout(new BorderLayout(10, 10)); // Add spacing between components


        // Title label with modern font and color
        JLabel titleLabel = new JLabel("Laser Maze");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0x2D2D2D)); // Dark gray color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setOpaque(false); // Make panel transparent

        JPanel mainMenuPanel = createMainMenuPanel();
        cardPanel.add(mainMenuPanel, "mainMenu");

        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);

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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false); // Set transparency


        JButton campaignButton = createStyledButton("Campaign");
        JButton levelSelectButton = createStyledButton("Level Select");
        JButton levelMakerButton = createStyledButton("Level Maker");
        JButton InstructionsButton = createStyledButton("Instructions");

        campaignButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align buttons horizontally
        levelSelectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        levelMakerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        InstructionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        // Set preferred size for the buttons
        campaignButton.setPreferredSize(new Dimension(200, 50));
        levelSelectButton.setPreferredSize(new Dimension(200, 50));
        levelMakerButton.setPreferredSize(new Dimension(200, 50));
        InstructionsButton.setPreferredSize(new Dimension(200, 50));

        JPanel openLastGamePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        openLastGamePanel.setOpaque(false); // Set transparency
        JButton openLastGameButton = createStyledButton("Open Last Game");
        openLastGameButton.setPreferredSize(new Dimension(200, 30));
        openLastGamePanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding around the panel if needed


        openLastGamePanel.add(openLastGameButton);
        panel.add(openLastGamePanel);
        panel.add(Box.createVerticalGlue());
        panel.add(campaignButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(levelSelectButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(levelMakerButton);
        panel.add(Box.createVerticalGlue());
        panel.add(InstructionsButton);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setBackground(new Color(0xE0E0E0)); // Light gray
        button.setForeground(Color.DARK_GRAY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0xD3D3D3)); // Slightly darker gray on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0xE0E0E0));
            }
        });
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Campaign":
                switchToPanel("campaignPage", new BoardPage(this));
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
                switchToPanel("lastGame", new BoardPage(this));
                break;
        }
    }

    private void switchToPanel(String name, JPanel panel) {
        cardPanel.add(panel, name);
        cardLayout.show(cardPanel, name);
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }
}