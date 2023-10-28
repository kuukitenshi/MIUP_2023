import java.util.Arrays;

public class ClosetPairOfPoints {
    
    // computes a closest pair of points in a set of n points in the plane in O(n log n)
    static double bestDistance;
    static Point best1, best2;

    public static Point[] closestPoints(Point[] ps) {
        int n = ps.length;
        if (n <= 1) return null;

        Point[] pointsByX = new Point[n];
        for (int i=0; i<n; i++)
            pointsByX[i] = ps[i];
        Arrays.sort(pointsByX, (Point p1, Point p2) -> {
            if (p1.x > p2.x) 
                return (int)1;  // sort by x-coordinate (breaking ties by y-coordinate)
            if (p1.x < p2.x) 
                return (int)-1;
            return (int)Math.round(p1.y-p2.y);
        });

        // check for coincident points (bestDistance == 0)
        for(int i=0; i<n-1; i++)
            if (pointsByX[i].equals(pointsByX[i+1]))
                return new Point[] {pointsByX[i], pointsByX[i+1]};

        Point[] pointsByY = new Point[n];  // sort by y-coordinate (but not yet sorted)
        for(int i=0; i<n; i++)
            pointsByY[i] = pointsByX[i];

        bestDistance = Double.POSITIVE_INFINITY;
        Point[] aux = new Point[n];
        closest(pointsByX, pointsByY, aux, 0, n-1);
        return new Point[] {best1, best2};
    }

    // find closest pair of points in pointsByX[lo..hi]
    // precondition:  pointsByX[lo..hi] and pointsByY[lo..hi] are the same sequence of points
    // precondition:  pointsByX[lo..hi] sorted by x-coordinate
    // postcondition: pointsByY[lo..hi] sorted by y-coordinate
    private static double closest(Point[] pointsByX, Point[] pointsByY, Point[] aux, int lo, int hi) {
        if (hi <= lo) 
            return Double.POSITIVE_INFINITY;

        int mid = lo + (hi - lo) / 2;
        Point median = pointsByX[mid];

        // compute closest pair with both endpoints in left subarray or both in right subarray
        double delta1 = closest(pointsByX, pointsByY, aux, lo,    mid);
        double delta2 = closest(pointsByX, pointsByY, aux, mid+1, hi);
        double delta = Math.min(delta1, delta2);

        // merge back so that pointsByY[lo..hi] are sorted by y-coordinate
        merge(pointsByY, aux, lo, mid, hi);

        // aux[0..m-1] = sequence of points closer than delta, sorted by y-coordinate
        int m = 0;
        for (int i = lo; i <= hi; i++)
            if (Math.abs(pointsByY[i].x - median.x) < delta)
                aux[m++] = pointsByY[i];

        // compare each point to its neighbors with y-coordinate closer than delta
        for (int i = 0; i < m; i++) {
            // a geometric packing argument shows that this loop iterates at most 7 times
            for (int j = i+1; (j < m) && (aux[j].y - aux[i].y < delta); j++) {
                double distance = aux[i].distance(aux[j]);
                if (distance < delta) {
                    delta = distance;
                    if (distance < bestDistance) {
                        bestDistance = delta;
                        best1 = aux[i];
                        best2 = aux[j];
                    }
                }
            }
        }
        return delta;
    }

    // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
    // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
    private static void merge(Point[] a, Point[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++)
            aux[k] = a[k];
        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if(i > mid)
                a[k] = aux[j++];
            else if (j > hi)
                a[k] = aux[i++];
            else if (compareTo(aux[j],aux[i])<0) 
                a[k] = aux[j++];
            else
                a[k] = aux[i++];
        }
    }

    private static int compareTo(Point p1, Point p2) {
        if (p1.y < p2.y) return -1;
        if (p1.y > p2.y) return +1;
        if (p1.x < p2.x) return -1;
        if (p1.x > p2.x) return +1;
        return 0;
    }
}
