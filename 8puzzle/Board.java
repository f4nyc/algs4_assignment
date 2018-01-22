import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

public class Board {
    private int dimension;
    private int[][] blocks;
    private int x,y;

    public Board(int[][] blocks){
        if(blocks == null)
            throw new java.lang.IllegalArgumentException();
        dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        for(int i = 0;i<dimension;i++)
            for(int j = 0;j<dimension;j++) {
                this.blocks[i][j] = blocks[i][j];
                if(blocks[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }

    };           // construct a blocks from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public int dimension(){
        return dimension;
    };                 // blocks dimension n

    public int hamming(){
        int hamming = 0;
        for(int i = 0;i<dimension;i++)
            for(int j = 0;j<dimension;j++)
                if(blocks[i][j] != i*dimension+j+1)
                    hamming++;

        return --hamming;
    };                   // number of blocks out of place

    public int manhattan(){
        int manhattan = 0;
        for(int i = 0;i<dimension;i++)
            for(int j = 0;j<dimension;j++)
                if(blocks[i][j] != 0)
                    manhattan += Math.abs(i-(blocks[i][j]-1)/dimension)+Math.abs(j-(blocks[i][j]-1)%dimension);
        return manhattan;
    };                 // sum of Manhattan distances between blocks and goal

    public boolean isGoal(){
        return hamming()==0;
    };                                          // is this blocks the goal blocks?
    // 其实可以用个变量单独存储省的多次调用重复计算

    public Board twin() {
        Board twin = new Board(blocks);                           //不想浪费空间了
        if(twin.blocks[0][1] != 0 && twin.blocks[0][0] != 0){      //这样很不优雅
            twin.blocks[0][0] = blocks[0][1];
            twin.blocks[0][1] = blocks[0][0];
        }
        else{                                                    //反正 n>=2
            twin.blocks[1][0] = blocks[1][1];
            twin.blocks[1][1] = blocks[1][0];
        }
        return twin;
    };                  // a blocks that is obtained by exchanging any pair of blocks


    public boolean equals(Object y){
        if(y == this)
            return true;
        if(y == null)
            return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if(this.dimension != that.dimension)return false;

        boolean flag = true;
        for(int i = 0;i<dimension;i++)
            for(int j = 0;j<dimension;j++)
                if(this.blocks[i][j] != that.blocks[i][j])
                    flag = false;

        return flag;
    };                                          // does this blocks equal y?

    public Iterable<Board> neighbors(){
        Queue<Board> neighbors = new Queue<Board>();
        if(x>0){
            Board neighbor = new Board(blocks);
            neighbor.blocks[x][y] = blocks[x-1][y];
            neighbor.blocks[x-1][y] = 0;
            neighbor.x--;                                                         //愚蠢的我忘记了改x y
            neighbors.enqueue(neighbor);
        }
        if(x<dimension-1){
            Board neighbor = new Board(blocks);
            neighbor.blocks[x][y] = blocks[x+1][y];
            neighbor.blocks[x+1][y] = 0;
            neighbor.x++;
            neighbors.enqueue(neighbor);
        }
        if(y>0){
            Board neighbor = new Board(blocks);
            neighbor.blocks[x][y] = blocks[x][y-1];
            neighbor.blocks[x][y-1] = 0;
            neighbor.y--;
            neighbors.enqueue(neighbor);
        }
        if(y<dimension-1){
            Board neighbor = new Board(blocks);
            neighbor.blocks[x][y] = blocks[x][y+1];
            neighbor.blocks[x][y+1] = 0;
            neighbor.y++;
            neighbors.enqueue(neighbor);
        }

        return neighbors;

    };     // all neighboring blockss

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(dimension+"\n");
        for(int i = 0;i<dimension;i++) {
            for (int j = 0; j < dimension; j++)
                s.append(String.format("%2d ", blocks[i][j]));
            s.append("\n");
        }
        return s.toString();
    };                                          // string representation of this blocks (in the output format specified below)
    //这里用stringbuilder一次性创建字符串节约性能

    public static void main(String[] args){
        int n = StdIn.readInt();
        int[][] blocks = new int[n][n];
        int[][] blocks_s = new int[n][n];
        for(int i = 0;i<n;i++)
            for(int j = 0;j<n;j++)
                blocks[i][j] = StdIn.readInt();
        Board initial = new Board(blocks);
        for(int i = 0;i<n;i++)
            for(int j = 0;j<n;j++)
                blocks_s[i][j] = StdIn.readInt();
        Board secound = new Board(blocks_s);

        StdOut.println(initial.equals(secound));
        StdOut.println(initial);
        StdOut.println(secound);

    }; // unit tests (not graded)
}