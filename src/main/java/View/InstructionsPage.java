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

        JLabel label = new JLabel("Instuctions hrherekrkeje", SwingConstants.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(label, BorderLayout.CENTER);
    }
}
