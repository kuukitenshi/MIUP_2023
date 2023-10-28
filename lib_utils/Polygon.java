import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.lang.Math.*;

class Polygon {

    public Point[] vs;

    // @pre: first and last points must be the same
    public Polygon(Point[] vs) {
        this.vs = vs;
    }

    public double perimeter() {
        double result = 0.0;
        for (int i = 0; i < vs.length - 1; i++)
            result += vs[i].distance(vs[i + 1]);
        return result;
    }

    // cf. https://en.wikipedia.org/wiki/Shoelace_formula
    public double area() {
        double result = 0.0, x1, y1, x2, y2;
        for (int i = 0; i < vs.length - 1; i++)
            result += (vs[i].x * vs[i + 1].y) - (vs[i + 1].x * vs[i].y);
        return abs(result) / 2.0;
    }

    // returns true if all three consecutive vertices form the same turns
    public boolean isConvex() {
        int sz = (int) vs.length;
        if (sz <= 3)
            return false; // it's a point or a segment

        boolean isLeft = Point.ccw(vs[0], vs[1], vs[2]);
        for (int i = 1; i < sz - 1; i++)
            if (Point.ccw(vs[i], vs[i + 1], vs[(i + 2) == sz ? 1 : i + 2]) != isLeft)
                return false;
        return true;
    }

    public boolean inPolygon(Point p) {
        if ((int) vs.length == 0)
            return false;
        double sum = 0;
        for (int i = 0; i < vs.length - 1; i++)
            if (Point.ccw(p, vs[i], vs[i + 1]))
                sum += Point.angle(vs[i], p, vs[i + 1]); // left turn/ccw
            else
                sum -= Point.angle(vs[i], p, vs[i + 1]); // right turn/cw
        return abs(abs(sum) - 2 * PI) < Point.EPSILON;
    }

    // cuts polygon along the line formed by points a b
    public Polygon cutPolygon(Point a, Point b) {
        ArrayList<Point> P = new ArrayList<Point>();

        for (int i = 0; i < this.vs.length; i++) {
            double left1 = new Vector(a, b).cross(new Vector(a, vs[i])),
                    left2 = 0.0;
            if (i != vs.length - 1)
                left2 = new Vector(a, b).cross(new Vector(a, vs[i + 1]));
            if (left1 > -Point.EPSILON)
                P.add(vs[i]); // vs[i] is on the left of ab
            if (left1 * left2 < -Point.EPSILON) // edge (vs[i], vs[i+1]) crosses line ab
                P.add(Line.lineIntersectSegment(vs[i], vs[i + 1], a, b));
        }
        if (!P.isEmpty() && !(P.get(0).equals(P.get(P.size() - 1))))
            P.add(P.get(0)); // make Polygon's first point = last point
        return new Polygon(P.toArray(new Point[0])); // toArray() needs to know what type of array
    }

    // used in convex hull
    private static int comparePoints(Point p, Point q) {
        if (abs(p.x - q.x) > Point.EPSILON) // useful to sort...
            return (int) ceil(p.x - q.x); // ...first by x-coordinate
        else if (abs(p.y - q.y) > Point.EPSILON)
            return (int) ceil(p.y - q.y); // ... and second by y-coordinate
        return 0;
    }

    Point pivot = new Point(0, 0); // used in convex hull

    Polygon convexHull(List<Point> P) {
        int i, j, n = (int) P.size();

        if (n <= 3) {
            if (comparePoints(P.get(0), P.get(n - 1)) != 0)
                P.add(P.get(0)); // safeguard from corner case
            return new Polygon((Point[]) P.toArray()); // special case, the convex hull is P itself
        }

        // first, find P0 = point with lowest Y and if tie: rightmost X
        int P0 = 0;
        for (i = 1; i < n; i++)
            if (P.get(i).y < P.get(P0).y ||
                    (P.get(i).y == P.get(P0).y && P.get(i).x > P.get(P0).x))
                P0 = i;

        Point temp = P.get(0);
        P.set(0, P.get(P0));
        P.set(P0, temp); // swap P[P0] with P[0]

        // second, sort points by angle w.r.t. P0
        pivot = P.get(0); // use this attribute variable as reference
        Collections.sort(P, new Comparator<Point>() {
            public int compare(Point a, Point b) { // angle-sorting function
                if (Point.collinear(pivot, a, b))
                    return pivot.distance(a) < pivot.distance(b) ? -1 : 1; // which one is closer?
                double d1x = a.x - pivot.x, d1y = a.y - pivot.y;
                double d2x = b.x - pivot.x, d2y = b.y - pivot.y;
                return (atan2(d1y, d1x) - atan2(d2y, d2x)) < 0 ? -1 : 1;
            }
        });

        // third, the ccw tests
        List<Point> S = new ArrayList<Point>();
        S.add(P.get(n - 1));
        S.add(P.get(0));
        S.add(P.get(1)); // initial S

        i = 2; // then, we check the rest
        while (i < n) { // note: n must be >= 3 for this method to work
            j = S.size() - 1;
            if (Point.ccw(S.get(j - 1), S.get(j), P.get(i)))
                S.add(P.get(i++)); // left turn, accept
            else
                S.remove(S.size() - 1); // or pop the top of S until we have a left turn
        }
        return new Polygon(S.toArray(new Point[0]));
    }
}