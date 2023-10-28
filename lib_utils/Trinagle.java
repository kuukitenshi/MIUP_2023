import static java.lang.Math.*;

class Triangle {

    Point A, B, C; // defined by points
    double a, b, c; // defined by sizes (no fixed reference)
    boolean byPoints; // informs which representation we are using

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
        byPoints = false;
    }

    public Triangle(Point a, Point b, Point c) {
        this.A = a;
        this.B = b;
        this.C = c;
        byPoints = true;
    }

    public static double area(double a, double b, double c) { // Heron's formula
        double s = (a + b + c) / 2.0; // semi-perimeter
        return sqrt(s * (s - a) * (s - b) * (s - c));
    }

    public static double area(Point p1, Point p2, Point p3) {
        return area(p1.distance(p2), p2.distance(p3), p3.distance(p1));
    }

    public double area() {
        return byPoints ? area(A, B, C) : area(a, b, c);
    }

    public static double perimeter(Point p1, Point p2, Point p3) {
        return p1.distance(p2) + p2.distance(p3) + p3.distance(p1);
    }

    public double perimeter() {
        return byPoints ? perimeter(A, B, C) : a + b + c;
    }

    // returns the triangle's incircle
    public Circle inCircle() {
        if (!byPoints)
            return null;

        Line angleBisector1 = Line.angleBisector(A, B, C),
                angleBisector2 = Line.angleBisector(B, A, C);

        Point center = angleBisector1.intersect(angleBisector2);
        double radius = Triangle.area(A, B, C) / (0.5 * Triangle.perimeter(A, B, C));
        return new Circle(center, radius);
    }

    // returns the triangle's outcircle
    public Circle outCircle() {
        if (!byPoints)
            return null;

        Line b1 = Line.bisector(A, B),
                b2 = Line.bisector(B, C);

        Point center = b1.intersect(b2);
        double radius = A.distance(B) * B.distance(C) * C.distance(A) / (4 * Triangle.area(A, B, C));
        return new Circle(center, radius);
    }
}