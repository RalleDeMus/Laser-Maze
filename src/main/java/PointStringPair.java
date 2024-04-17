import java.awt.Point;

public class PointStringPair {
    private Point point;
    private String value;

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