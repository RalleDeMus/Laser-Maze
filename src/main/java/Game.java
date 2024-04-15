import View.MainMenu;
import java.awt.event.*;
import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu();;

        });


    }
}
