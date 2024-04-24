package Controller;

import Model.Logic.Board;
import Model.Tiles.*;
import View.Pages.LevelMakerPage;

import java.awt.event.KeyEvent;

public class LevelMakerInputHandler extends BoardInputHandler {
    public LevelMakerInputHandler(Board board, LevelMakerPage panel, int yOffset, boolean removeTileAfterPlace){
        super(board, panel, yOffset, removeTileAfterPlace);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                board.rotateSelectedTile(true);
                break;
            case KeyEvent.VK_1:
                board.setSelectedTile(new LaserTile(true, true));
                break;
            case KeyEvent.VK_2:
                board.setSelectedTile(new MirrorTile(true, true));
                break;
            case KeyEvent.VK_3:
                board.setSelectedTile(new DoubleTile(true, true));
                break;
            case KeyEvent.VK_4:
                board.setSelectedTile(new SplitterTile(true, true));
                break;
            case KeyEvent.VK_5:
                board.setSelectedTile(new CheckPointTile(true, true));
                break;
            case KeyEvent.VK_6:
                board.setSelectedTile(new CellBlockerTile(true));
                break;
        }
        panel.repaint();
    }


}
