import View.Pages.MainMenuPage;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MainMenuPage();;

        });


    }
}
