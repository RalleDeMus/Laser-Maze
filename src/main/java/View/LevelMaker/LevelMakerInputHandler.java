package View.LevelMaker;

import Model.Logic.Board;
import View.Board.BoardInputHandler;
import View.BoardPage;
import View.LevelMakerPage;

import java.awt.event.KeyEvent;

public class LevelMakerInputHandler extends BoardInputHandler {
    public LevelMakerInputHandler(Board board, LevelMakerPage panel, int yOffset, boolean removeTileAfterPlace){
        super(board, panel, yOffset, removeTileAfterPlace, true);
    }


}
