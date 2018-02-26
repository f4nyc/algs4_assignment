import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int size;
    private double mean,stddev,confidenceLo,confidenceHi;
    private double threshold(int n){
        Percolation grid = new Percolation(n);

        while (!grid.percolates()){

            int row = StdRandom.uniform(n)+1;
            int col = StdRandom.uniform(n)+1;

            grid.open(row,col);
        }
        return (double)grid.numberOfOpenSites() / (double)(n*n);
    }
    public PercolationStats(int n, int trials){

        if(n < 1 || trials < 1 )
            throw new java.lang.IllegalArgumentException();

        double[] results = new double[trials];

        for(int i = 0; i < trials; i++)
            results[i] = threshold(n);

        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        confidenceLo = mean - 1.96*stddev / Math.sqrt(trials);
        confidenceHi = mean + 1.96*stddev / Math.sqrt(trials);

    }    // perform trials independent experiments on an n-by-n grid

    public double mean(){
        return mean;
    }                          // sample mean of percolation threshold
    public double stddev(){
        return stddev;
    }                        // sample standard deviation of percolation threshold
    public double confidenceLo(){
        return confidenceLo;
    }                  // low  endpoint of 95% confidence interval
    public double confidenceHi(){
        return confidenceHi;
    }                  // high endpoint of 95% confidence interval

    public static void main(String[] args){
        int n,trials;
        n = StdIn.readInt();
        trials = StdIn.readInt();

        PercolationStats client = new PercolationStats(n,trials);

        StdOut.println(client.mean());
        StdOut.println(client.stddev());
        StdOut.println('['+client.confidenceLo()+','+client.confidenceHi()+']');

    }        // test client (described below)
}