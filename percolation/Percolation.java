import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private final WeightedQuickUnionUF grid_full;
    private final int size;
    private int open_number;
    private boolean[][] open;                               //丧心病狂换成boolean终于满分
    private void validate(int row, int col){
        if(row < 1 || col < 1 || row > size || col > size)
            throw new java.lang.IllegalArgumentException();
    }
    private int rc_to_1D(int row, int col){
        return (row-1)*size+col-1;
    };
    public Percolation(int n){
        size = n;
        validate(n,n);
        grid = new WeightedQuickUnionUF(n*n+2);
        grid_full = new WeightedQuickUnionUF(n*n+1);
        open = new boolean[n][n];
        open_number = 0;
        for(int i = 0;i<n;i++)
            for(int j = 0;j<n;j++)
                open[i][j] = false;
    }
    // create n-by-n grid, with all sites blocked
    public    void open(int row, int col){
        validate(row,col);
        if(isOpen(row,col))return;
        open[row-1][col-1] = true;
        open_number++;
        if(row == 1) {
            grid.union(rc_to_1D(row, col), size * size);
            grid_full.union(rc_to_1D(row, col), size * size);
        }
        if(row == size)
            grid.union(rc_to_1D(row,col),size*size+1);
        int[] r_offset = {1,-1,0,0};
        int[] c_offset = {0,0,1,-1};
        for(int i = 0;i < 4; i++ ){
            int r = row + r_offset[i];
            int c = col + c_offset[i];
            if(r < 1 || c < 1 || r > size || c > size)
                continue;
            if(isOpen(r,c)) {
                grid.union(rc_to_1D(row, col), rc_to_1D(r, c));
                grid_full.union(rc_to_1D(row, col), rc_to_1D(r, c));
            }
        }
    }

    // open site (row, col) if it is not open already
    public boolean isOpen(int row, int col){
        validate(row,col);
        return open[row-1][col-1];
    }  // is site (row, col) open?
    public boolean isFull(int row, int col){
        return isOpen(row,col) && grid_full.connected(size*size,rc_to_1D(row,col));
    }  // is site (row, col) full?
    public     int numberOfOpenSites(){
        return open_number;
    }       // number of open sites
    public boolean percolates(){
        return grid.connected(size*size,size*size+1);
    }              // does the system percolate?

    public static void main(String[] args){
        int n = StdIn.readInt();
        Percolation client = new Percolation(n);
        while (!StdIn.isEmpty())
        {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            client.open(row, col);
            StdOut.println(client.isOpen(row, col));
            StdOut.println(client.isFull(row, col));
            StdOut.println(client.percolates());
        }
    }   // test client (optional)
}
