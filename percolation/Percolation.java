/**
 * Created by fanyc on 17-5-22.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
public class Percolation {
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF gridnobottom;
    private int size;
    private int[] status;
    private int openumber;

    private int isvalid(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IndexOutOfBoundsException("Grid size out of range");
        int index = (row-1)*size+col-1;
        return index;
    }
    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("Grid size out of range");
        size = n;
        openumber = 0;
        grid = new WeightedQuickUnionUF(n * n + 2);
        gridnobottom = new WeightedQuickUnionUF(n * n + 1);
        status = new int[n * n];
        for (int i = 0; i < n * n; i++)
            status[i] = 0;
    }

    public    void open(int row, int col){
        if(isOpen(row, col))
            return;
        int index=isvalid(row, col);
        status[index] = 1;
        openumber++;
        int[] offsetx = {1,-1,0,0};
        int[] offsety = {0,0,1,-1};
        if(row==1) {
            grid.union(index, size * size);
            gridnobottom.union(index, size * size);
        }
        if(row==size)
            grid.union(index,size*size+1);
        for(int i = 0;i < 4;i++) {
            int offsetrow =row+offsetx[i];
            int offsetcol = col+offsety[i];
            if (offsetrow < 1 || offsetrow > size || offsetcol < 1 || offsetcol > size)
                continue;
            int offsetindex = (offsetrow-1)*size+offsetcol-1;
            if(isOpen(offsetrow,offsetcol)) {
                grid.union(offsetindex, index);
                gridnobottom.union(offsetindex, index);
            }
        }

    }

    public boolean isOpen(int row, int col)
    {
        int index = isvalid(row,col);
        return status[index]==1;
    }

    public boolean isFull(int row, int col)
    {
        int index = isvalid(row,col);
        return isOpen(row,col) && gridnobottom.connected(index,size*size);
    }

    public int numberOfOpenSites() {
        return openumber;
    }

    public boolean percolates(){
        return grid.connected(size*size,size*size+1);
    }              // does the system percolate?

    public static void main(String[] args)
    {
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
    }
}
