package View;

import javax.swing.*;
import java.awt.*;
public class InstructionsPage extends JPanel{
    InstructionsPage(MainMenu mainMenu) {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.add(backButton);
        String instructionsHtml = "<html><head>"
                + "<style>"
                + "body {"
                + "   width: 600px;" // Sets the width of the body to control text alignment
                + "   margin: auto;" // Centers the body within the page
                + "   font-family: Arial, sans-serif;" // Sets a readable font
                + "}"
                + "h1, h2 {"
                + "   color: navy;" // Styles for headers
                + "}"
                + "ul {"
                + "   padding-left: 20px;" // Proper padding for lists
                + "}"
                + "li {"
                + "   margin-bottom: 10px;" // Space between list items
                + "}"
                + "</style>"
                + "</head><body>"
                + "<h1>Game Instructions</h1>"
                + "<h2>Picking Levels and Campaign Mode</h2>"
                + "<p>Select pieces from the toolbar. Press 'R' to rotate a piece. Right-click to remove a piece. "
                + "You will be notified when you win the game. You must hit the targets as indicated.</p>"
                + "<h2>Level Maker</h2>"
                + "<p>Select different pieces using the keyboard as follows:<br>"
                + "1 = Laser Tile<br>"
                + "2 = Target/Mirror Tile<br>"
                + "3 = Double Mirror Tile<br>"
                + "4 = Beam Splitter Tile<br>"
                + "5 = Checkpoint Tile<br>"
                + "6 = Cell Blocker Tile</p>"
                + "<p>After selecting the pieces, choose which pieces should be in the toolbar. "
                + "Also decide how many targets need to be hit. "
                + "If you rotate a piece 5 times, it gets a new icon making the piece rotatable.</p>"
                + "<h2>Game Components</h2>"
                + "<ul>"
                + "<li><b>Laser:</b> Provides the laser beam. Push the button at the top of this token to engage the beam.</li>"
                + "<li><b>Target/Mirror:</b> Can be used as a target, a mirror, or both. Only the transparent red side of the token is a valid target.</li>"
                + "<li><b>Beam Splitter:</b> Splits the beam into two paths. One path is reflected 90 degrees and the other path passes straight through the token.</li>"
                + "<li><b>Double-Mirror:</b> Both sides of this token reflect the beam 90 degrees.</li>"
                + "<li><b>Checkpoint:</b> Serves as a token that the laser beam must pass through.</li>"
                + "<li><b>Cell Blocker:</b> Prevents other tokens from being placed in the cell which it inhabits. This token will not block the path of the laser.</li>"
                + "</ul>"
                + "</body></html>";

        JLabel label = new JLabel(instructionsHtml, SwingConstants.CENTER);

        // Images panel
        JPanel imagesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // Assuming the 'Direction' files are located in the 'assets' directory
        String[] directionFiles = {"BeamSplitterDirection.png", "CheckpointDirections.png",
                "DoubleMirrorDirection.png", "LaserDirection.png",
                "TargetMirrorDirection.png"};
        for (String filename : directionFiles) {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/assets/" + filename));
            JLabel imageLabel = new JLabel(imageIcon);
            imagesPanel.add(imageLabel);
        }

        // Add components to the InstructionsPage layout
        add(topPanel, BorderLayout.NORTH);
        add(label, BorderLayout.CENTER);
        add(imagesPanel, BorderLayout.SOUTH); // Add the images panel to the bottom
    }
}