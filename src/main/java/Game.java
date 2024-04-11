import View.MainMenu;
import java.awt.event.*;
import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu();;
            /*frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // Center the window
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent event) {
                    // Handle window closing event if needed
                    frame.dispose(); // Dispose of the frame
                    System.exit(0); // Exit the application
                }
            });
            frame.setVisible(true);*/
        });


    }
}
