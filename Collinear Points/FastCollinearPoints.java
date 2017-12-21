import edu.princeton.cs.algs4.Merge;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class FastCollinearPoints {
    private int number;
    private LinkedList<LineSegment> segments = new LinkedList<LineSegment>();

    public FastCollinearPoints(Point[] points){
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
        Merge.sort(temp);//复制并检查

        for(int i = 0;i<length;i++) {

            Point[] slopes = new Point[length-1];

            for (int j = 0; j < length; j++) {
                if (j < i) slopes[j] = temp[j];
                if (j > i) slopes[j-1] = temp[j];
            }

            Arrays.sort(slopes,temp[i].slopeOrder());//复制并排序

            if(length<4)break;
            int count = 1;
            Point last = slopes[0];//此处可能会throw java.lang.NullPointerException啊，你能确保slopes非空？
            Point first = temp[i];
            double slope = first.slopeTo(last);
            boolean re = first.compareTo(last)>0;//查找前的准备

            for(int j = 1;j<slopes.length;j++){
                double s=first.slopeTo(slopes[j]);
                if(slope == s) {
                    count++;
                    if(slopes[j].compareTo(last)>0)  last = slopes[j];
                }
                else {
                    if (count > 2 && !re) {
                        number++;
                        segments.add(new LineSegment(first, last));
                    }
                    slope = s;
                    count = 1;
                    last = slopes[j];
                    re = false;

                }
                if(slopes[j].compareTo(first)<0) re = true;
            }

            if(count>2 && !re){
                number++;
                segments.add(new LineSegment(first, last));

            }//又忘了考虑边界！！！！！！！！！！

        }
    }
    // finds all line segments containing 4 points

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
        int n = StdIn.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }


        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
        }
    }
}
