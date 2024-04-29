package Model.Logic;


/**
 * Laser class to represent a laser beam in the game. Not to be confused with the laser tile.
 * The laser class is used to represent the path of the laser beam in the game.
 */
public class Laser {
    private int x; // The x coordinate of the beam
    private int y; // The y coordinate of the beam
    private int orientation;   // An integer label for the point

    /**
     * Constructs a new Laser instance with specified x, y and orientation.
     *
     * @param x the x coordinate of the beam
     * @param y the y coordinate of the beam
     * @param orientation the orientation of the beam.
     */
    public Laser(int x, int y, int orientation) { //done
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    // Getter for the point
    public int getX() {
        return x;
    } //done
    public int getY() {
        return y;
    } //done

    // Getter for the label
    public int getOrientation() {
        return orientation;
    }

    // Setter for the point
    public void setPos(int x, int y) { //done
        this.x = x;
        this.y = y;
    }

    // Setter for the label
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public boolean Equals(Laser l1) {
        return l1.getX() == this.getX() && l1.getY() == this.getY() && l1.orientation == this.orientation;
    }



    // A method to return a string representation of the LabeledPoint
    @Override
    public String toString() { // done
        return "Laser{" +
                "x,y = " + x + "," + y +
                " ; orientation = " + orientation +
                '}';
    }
}