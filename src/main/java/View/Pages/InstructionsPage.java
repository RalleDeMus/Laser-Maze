package View.Pages;

import Controller.AssetServer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
/**
 * The InstructionsPage class is responsible for displaying the instructions for the game.
 */
public class InstructionsPage extends JPanel{
    InstructionsPage(MainMenuPage mainMenu) {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Baloo Bhaijaan", Font.PLAIN, 20));
        backButton.addActionListener(e -> mainMenu.getCardLayout().show(mainMenu.getCardPanel(), "mainMenu"));
        topPanel.add(backButton);
        // Instructions text with HTML for formatting and inclusion of the image content
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
                + "<p>Select tiles from the toolbar with left-click and left-click again on the board to place the tile. Press 'R' on a tile to rotate a tile. Right-click on a tile to remove the tile. Press 'L' to fire the laser after all tiles have been used. Try to hit all tiles and targets with the laser. After firing the laser, the game will tell the player that they have won, if the solution is correct. </p>"
                +"<h2>Level Maker</h2>"
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
                + "<li><b>Laser:</b> Provides the laser beam. Press 'L' button on your keyboard to engage the beam.</li>"
                + "<li><b>Target/Mirror:</b> Can be used as a target, a mirror, or both. Only the side of the tile marked by a black triangle is a valid target.</li>"
                + "<li><b>Beam Splitter:</b> Splits the beam into two paths. One path is reflected 90 degrees and the other path passes straight through the tile.</li>"
                + "<li><b>Double-Mirror:</b> Both sides of this tile reflect the beam 90 degrees.</li>"
                + "<li><b>Checkpoint:</b> Serves as a tile that the laser beam must pass through.</li>"
                + "<li><b>Cell Blocker:</b> Prevents other tiles from being placed in the cell which it inhabits. This tile will not block the path of the laser.</li>"
                + "</ul>"
                + "</body></html>";

        // Create a label for the instructions with HTML content
        JLabel label = new JLabel(instructionsHtml, SwingConstants.CENTER);

        // Add components to the InstructionsPage layout
        add(topPanel, BorderLayout.NORTH);
        add(label, BorderLayout.CENTER);

    }
}
