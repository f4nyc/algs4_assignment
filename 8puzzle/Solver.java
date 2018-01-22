import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


import java.util.Comparator;

public class Solver {
    private Stack<Board> solution;
    private boolean isSolvable;
    private int moves = -1;

    private class Node implements Comparable<Node>{
        Board board;
        int manhattan;
        Node  parent;
        int moves;
        int priority;
        boolean istwin;
        public Node(Board initial,Node parent,boolean istwin){
            this.board = initial;
            this.parent = parent;
            if(parent == null)moves = 0;
            else moves = parent.moves+1;
            this.manhattan = board.manhattan();
            this.priority = manhattan+moves;
            this.istwin = istwin;
        }
        public int compareTo(Node that) {                   // 当priority相同时优先考虑move少的
            if(this.priority>that.priority)return 1;        // 以深度优先可能会在无解的情况下浪费时间
            if(this.priority<that.priority)return -1;
            if(this.moves>that.moves)return -1;
            if(this.moves<that.moves)return 1;
            return 0;
        }
    }



    public Solver(Board initial){
        MinPQ<Node>solvePQ = new MinPQ<Node>();
        if(initial == null)throw new java.lang.IllegalArgumentException();
        solvePQ.insert(new Node(initial,null,false));
        solvePQ.insert(new Node(initial.twin(),null ,true));
        Astar(solvePQ);
    }           // find a solution to the initial board (using the A* algorithm)

    private void Astar(MinPQ<Node> solvePQ){
        while (!solvePQ.isEmpty()){
            Node temp = solvePQ.delMin();

            if(temp.board.isGoal()) {
                isSolvable = !temp.istwin;

                if (temp.istwin) return;

                solution = new Stack<Board>();
                moves = temp.moves;
                solution.push(temp.board);

                while(temp.parent != null){
                    temp = temp.parent;
                    solution.push(temp.board);
                }
                 return;
            }
            for(Board board : temp.board.neighbors()){
                if(temp.parent != null && temp.parent.board.equals(board))continue;
                solvePQ.insert(new Node(board,temp,temp.istwin));
            }


        }
    };
    public boolean isSolvable(){
        return isSolvable;
    }            // is the initial board solvable?

    public int moves(){
        return moves;
    }                     // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution(){
        if(isSolvable)return solution;
        return null;
    }// sequence of boards in a shortest solution; null if unsolvable

    public static void main(String[] args){

        // create initial board from file

        int n = StdIn.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = StdIn.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    } // solve a slider puzzle (given below)

}
