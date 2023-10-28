class Vector {
   public double x,y;
   
   public Vector(double x, double y) { this.x = x;         this.y = y;         }
   public Vector(Point a, Point b)   { this.x = b.x - a.x; this.y = b.y - a.y; }
   
   public Vector scale(double s)     { return new Vector(x*s,y*s);    }
   public Point  translate(Point p)  { return new Point(x+p.x,y+p.y); }
   
   public double dot(Vector v)       { return x*v.x + y*v.y; }  // dot product
   public double norm_sq()           { return x*x   + y*y;   }  // square of vector's norm
   public double cross(Vector v)     { return x*v.y - y*v.x; }  // cross product
}