import java.util.Arrays;

public class Board {
    
    private final int N;
    private final int size;
    private final int[][] board;
    
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        
        N = blocks.length;
        size = N * N;   
        board = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                board[i][j] = blocks[i][j];
            }
        }
    }
    
    // board dimension N
    public int dimension() {
        return N;
    }
    
    // number of blocks out of place
    public int hamming() {

        int blocks = 0;
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (board[i][j] != 1 + (i * N) + j) { // compare to goal tile
                    if (board[i][j] != 0) {           // do not compute for 0
                        blocks++;
                    }
                } 
            }
        }
        
        return blocks;
    }    
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int mhd = 0;
        
        for (int x = 0; x < N; x++) {     // x-dimension, traversing rows (i)
            for (int y = 0; y < N; y++) { // y-dimension, traversing cols (j)
                int value = board[x][y];
                if (value != 0) {                  // do not compute mhd for 0
                    int targetX = (value - 1) / N; // expected x-coordinate (row)
                    int targetY = (value - 1) % N; // expected y-coordinate (col)
                    int dx = x - targetX;          // x-distance to expected coordinate
                    int dy = y - targetY;          // y-distance to expected coordinate
                    mhd += Math.abs(dx) + Math.abs(dy);
                }
            }
        }
        
        return mhd;
    }                 
    
    // is this board the goal board?
    public boolean isGoal() {
        return (this.hamming() == 0);
    }                
    
    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        
        int swap;
        
        // copy array
        int[][] twin = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                twin[i][j] = board[i][j];
            }
        }
        
        // swap to tiles to create a twin
        if (twin[0][0] != 0 && twin[0][1] != 0) {
            swap = twin[0][0];
            twin[0][0] = twin[0][1];
            twin[0][1] = swap;
        }
        else {
            swap = twin[1][0];
            twin[1][0] = twin[1][1];
            twin[1][1] = swap;
        }
        
        Board b = new Board(twin);
        return b;
    }                    
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return (Arrays.deepEquals(this.board, that.board));
    }        
    
    // all neighboring boards
    public Iterable<Board> neighbors() {

        Stack<Board> n = new Stack<Board>();
        Board neighbor;
        int swap;
        
        // copy array
        int[][] neighbors = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                neighbors[i][j] = board[i][j];
            }
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (neighbors[i][j] == 0) { // neighbors are around this tile
                    
                    if (j + 1 < N) {    // tile to the right
                        swap = neighbors[i][j];
                        neighbors[i][j] = neighbors[i][j+1];
                        neighbors[i][j+1] = swap;
                        
                        neighbor = new Board(neighbors);
                        n.push(neighbor);
                        
                        // restore board
                        swap = neighbors[i][j];
                        neighbors[i][j] = neighbors[i][j+1];
                        neighbors[i][j+1] = swap;
                    }
                    
                    if (j - 1 >= 0) {    // tile to the left
                        swap = neighbors[i][j];
                        neighbors[i][j] = neighbors[i][j-1];
                        neighbors[i][j-1] = swap; 
                        
                        neighbor = new Board(neighbors);
                        n.push(neighbor);
                        
                        // restore board
                        swap = neighbors[i][j];
                        neighbors[i][j] = neighbors[i][j-1];
                        neighbors[i][j-1] = swap;
                    }
                    
                    if (i - 1 >= 0) {    // tile above
                        swap = neighbors[i][j];
                        neighbors[i][j] = neighbors[i-1][j];
                        neighbors[i-1][j] = swap;
                        
                        neighbor = new Board(neighbors);
                        n.push(neighbor);
                        
                        // restore board
                        swap = neighbors[i][j];
                        neighbors[i][j] = neighbors[i-1][j];
                        neighbors[i-1][j] = swap;
                    }
                    
                    if (i + 1 < N) {    // tile below
                        swap = neighbors[i][j];
                        neighbors[i][j] = neighbors[i+1][j];
                        neighbors[i+1][j] = swap;
                        
                        neighbor = new Board(neighbors);
                        n.push(neighbor);
                        
                        // restore board
                        swap = neighbors[i][j];
                        neighbors[i][j] = neighbors[i+1][j];
                        neighbors[i+1][j] = swap;
                    }
                    
                break;
                
                }
            }
        }
        
        return n;
        
    }     
    
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }               

    // unit tests
    public static void main(String[] args) {
    }
}