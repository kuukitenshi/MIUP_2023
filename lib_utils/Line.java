class Line {
   public static final double EPSILON = 1e-7;
   public double a, b, c;

   public static boolean eq(double a, double b) {
      return Math.abs(a - b) < EPSILON;
   }

   public Line(double a, double b, double c) {
      this.a = a;
      this.b = b;
      this.c = c;
   }

   // create line defined by two points
   public Line(Point p, Point q) {
      if (eq(p.x, q.x)) { // vertical line
         a = 1.0;
         b = 0.0;
         c = -p.x; // default values
      } else {
         a = -(p.y - q.y) / (p.x - q.x);
         b = 1.0; // we fix the value of b to 1.0
         c = -a * p.x - p.y;
      }
   }

   public String toString() {
      return String.format("%1$.3f x + %2$.3f y + %3$.3f == 0", a, b, c);
   }

   public boolean isColinear(Point p) {
      return distanceTo(p) < EPSILON;
   }

   public boolean isParallel(Line m) {
      return eq(a, m.a) && eq(b, m.b);
   }

   public boolean equals(Line m) {
      return isParallel(m) && eq(c, m.c);
   }

   public double distanceTo(Point p) {
      return Math.abs(a * p.x + b * p.y + c) / Math.sqrt(a * a + b * b);
   }

   // the projection of p onto line
   public Point projection(Point p) {
      double d = -c - a * p.x - b * p.y;
      double z = 1 / (a * a + b * b);
      return new Point(p.x + z * a * d, p.y + z * b * d);
   }

   // the line perpendicular to 'this', passing thru p
   public Line perpendicular(Point p) {
      return new Line(p, projection(p));
   }

   // the point that intersects two lines (or null if parallel)
   public Point intersect(Line m) {
      if (isParallel(m))
         return null;

      double px = (m.b * c - b * m.c) / (m.a * b - a * m.b);
      double py = b > EPSILON ? -(a * px + c) : -(m.a * px + m.c);
      return new Point(px, py);
   }

   // do two segments intersect?
   public static boolean doSegmentsIntersect(Point a1, Point b1, Point a2, Point b2) {
      Point intersection = new Line(a1, b1).intersect(new Line(a2, b2));
      if (intersection == null)
         return false; // they are parallel
      Point closest1 = intersection.closestToSegment(a1, b1),
            closest2 = intersection.closestToSegment(a2, b2);
      return closest1.equals(closest2);
   }

   public static Line bisector(Point p, Point q) {
      Line m = new Line(p, q);
      double midx = (p.x + q.x) / 2,
            midy = (p.y + q.y) / 2;
      if (m.a == 0)
         return new Line(1.0, 0.0, -midx); // horizontal line
      return new Line(-m.b / m.a, 1.0, -(m.a * midy - m.b * midx) / m.a);
   }

   // the line that bisects the angle made by points p2p1p3 (p1 is in the middle)
   public static Line angleBisector(Point p1, Point p2, Point p3) {
      double ratio = p1.distance(p2) / p1.distance(p3);
      Point p4 = new Vector(p2, p3).scale(ratio / (1 + ratio)).translate(p2);

      return new Line(p1, p4);
   }

}