import static java.lang.Math.*;

class Point {
    public static final double EPSILON = 1e-7;
    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point closestToSegment(Point a, Point b) {
        Vector ap = new Vector(a, this),
                ab = new Vector(a, b);
        double u = ap.dot(ab) / ab.norm_sq();

        if (u < 0.0)
            return a;
        if (u > 1.0)
            return b;
        return new Line(a, b).projection(this); // return the projection
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public static double hypot(double x, double y) {
        return sqrt(x * x + y * y);
    }

    public static boolean eq(double a, double b) {
        return abs(a - b) < EPSILON;
    }

    public static double deg2rad(double degree) {
        return degree * PI / 180.0;
    }

    public static double rad2deg(double rad) {
        return rad * 180.0 / PI;
    }

    public boolean equals(Point p) {
        return eq(x, p.x) && eq(y, p.y);
    }

    public double distance(Point p) {
        return hypot(x - p.x, y - p.y);
    }

    public Point rotate(double degree) { // rotate wrt origin (in degrees)
        double rad = deg2rad(degree);
        return new Point(x * cos(rad) - y * sin(rad), x * sin(rad) + y * cos(rad));
    }

    public static boolean collinear(Point p, Point q, Point r) {
        return Math.abs(new Vector(p, q).cross(new Vector(p, r))) < EPSILON;
    }

    // counterclockwise: if a -> b -> c makes a left turn
    // if == 0 they are colinear, if negative they make a right turn (clockwise)
    public static boolean ccw(Point a, Point b, Point c) {
        Vector ab = new Vector(a, b),
                ac = new Vector(a, c);
        return ab.cross(ac) > 0;
    }

    // angle between the three points, o is the middle point
    public static double angle(Point a, Point o, Point b) {
        Vector oa = new Vector(o, a),
                ob = new Vector(o, b);
        return acos(oa.dot(ob) / sqrt(oa.norm_sq() * ob.norm_sq()));
    }
}