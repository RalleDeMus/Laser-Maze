import View.MainMenu;
import java.awt.event.*;
import javax.swing.*;
import java.awt.GraphicsEnvironment;

public class Game {

    public static void main(String[] args) {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        for (int i = 0; i < fonts.length; i++) {
            System.out.println(fonts[i]);
        }
        SwingUtilities.invokeLater(() -> {
            new MainMenu();;

        });


    }
}
