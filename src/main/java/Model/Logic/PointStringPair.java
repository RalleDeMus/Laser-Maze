package Model.Logic;

import java.awt.Point;

public class PointStringPair {
    final Point point;
    final String value;

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