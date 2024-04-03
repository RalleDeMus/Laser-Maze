public class Cursor {
    private int cursorX = 0;
    private int cursorY = 0;

    public void setCursorPos(int x, int y) {
        cursorX = x;
        cursorY = y;
    }

    public int getCursorPosX(){return cursorX;}

    public int getCursorPosY(){return cursorY;}


}
