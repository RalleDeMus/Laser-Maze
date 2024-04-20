package Model.Logic;

import java.awt.Point;

public class Laser {
    private int x;
    private int y;
    private int orientation;   // An integer label for the point

    // Constructor to initialize the point and label
    public Laser(int x, int y, int orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    // Getter for the point
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    // Getter for the label
    public int getOrientation() {
        return orientation;
    }

    // Setter for the point
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean Equals(Laser l1) {
        return l1.getX() == this.getX() && l1.getY() == this.getY() && l1.getOrientation() == this.getOrientation();
    }

    // Setter for the label
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    // A method to return a string representation of the LabeledPoint
    @Override
    public String toString() {
        return "Model.Logic.Laser{" +
                "x,y = " + x + "," + y +
                " ; orientation = " + orientation +
                '}';
    }
}