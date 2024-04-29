package Model.Logic;

import java.awt.Point;


/**
 * Class for handling the pair of a point and a string. Used in the laser calculation.
 */
public class PointStringPair {
    private final Point point;
    private final String value;

    public PointStringPair(Point point, String value) {
        this.point = point;
        this.value = value;
    }

    public Point getPoint() {
        return point;
    }

    public String getValue() {
        return value;
    }
}