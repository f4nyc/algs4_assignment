import edu.princeton.cs.algs4.Merge;
import java.util.LinkedList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class BruteCollinearPoints {
    private int number;
    private LinkedList<LineSegment> segments = new LinkedList<LineSegment>();

    public BruteCollinearPoints(Point[] points){
        if (points == null) throw new java.lang.IllegalArgumentException();

        number=0;
        int length = points.length;
        Point[] temp = new Point[length];
        for(int i = 0;i<length;i++) {
            if (points[i] == null) throw new java.lang.IllegalArgumentException();
            for(int j = i-1;j > -1;j--)//没有注意到这层循环会访问没有检验过的元素吗
                if(points[i].compareTo(points[j]) == 0)throw new java.lang.IllegalArgumentException();
            temp[i] = points[i];
        }
        Merge.sort(temp);
        for(int i = 0;i<length-3;i++)
            for(int j = i+1;j<length-2;j++) {
                double d1 = temp[i].slopeTo(temp[j]);
                for (int k = j + 1; k < length - 1; k++) {
                    double d2 = temp[i].slopeTo(temp[k]);
                    for (int l = k + 1; l < length; l++) {
                        double d3 = temp[i].slopeTo(temp[l]);
                        if (d1 == d2 && d2 == d3) {
                            number++;
                            segments.add(new LineSegment(temp[i], temp[l]));
                        }
                    }
                }
            }
    }    // finds all line segments containing 4 points

    public           int numberOfSegments(){
        return number;
    }        // the number of line segments

    public LineSegment[] segments(){
        LineSegment[] seg = new LineSegment[number];
        int i = 0;
        for(LineSegment s:segments) 
            seg[i++]=s;
        return seg;
    }                // the line segments
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n-1; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }


        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}
