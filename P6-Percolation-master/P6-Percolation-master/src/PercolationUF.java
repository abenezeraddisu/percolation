import java.util.Arrays;

public class PercolationUF implements IPercolate {

    private IUnionFind myFinder;
    private boolean[][] myGrid;
    private final int VTOP;
    private final int VBOTTOM;
    private int myOpenCount;


    /**
     * sets all the cells as closed and initializes all variables
     *
     * @param finder is the IUnionFind object
     * @param size   the length and width of the grid
     */
    PercolationUF(IUnionFind finder, int size) {

        VTOP = size * size;
        VBOTTOM = size * size + 1;
        myGrid = new boolean[size][size];
        for (boolean[] row : myGrid)
            Arrays.fill(row, false);
        myFinder = finder;
        finder.initialize(size * size + 2);
        myOpenCount = 0;
    }

    /**
     * opens the selected cell and then checks cells around it
     * makes the cells full if in the same set as a full cell
     *
     * @param row row index in range [0,N-1]
     * @param col column index in range [0,N-1]
     */
    @Override
    public void open(int row, int col) {
        if (row < 0 || row >= myGrid.length || col < 0 || col >= myGrid.length) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }

        if (!isOpen(row, col)) {

            myGrid[row][col] = true;
            myOpenCount++;
            if (row == 0) myFinder.union(index(row, col), VTOP);
            if (row == myGrid.length - 1) myFinder.union(index(row, col), VBOTTOM);
            if (row - 1 >= 0 && row - 1 < myGrid.length && col >= 0 && col < myGrid.length) {
                if (isOpen(row - 1, col)) {
                    myFinder.union(index(row, col), index(row - 1, col));
                }
            }

            if (row + 1 >= 0 && row + 1 < myGrid.length && col >= 0 && col < myGrid.length) {
                if (isOpen(row + 1, col)) {
                    myFinder.union(index(row, col), index(row + 1, col));
                }
            }
            if (row >= 0 && row < myGrid.length && col - 1 >= 0 && col - 1 < myGrid.length) {
                if (isOpen(row, col - 1)) {
                    myFinder.union(index(row, col), index(row, col - 1));
                }
            }
            if (row >= 0 && row < myGrid.length && col + 1 >= 0 && col + 1 < myGrid.length) {
                if (isOpen(row, col + 1)) {
                    myFinder.union(index(row, col), index(row, col + 1));
                }
            }
        }
    }

    /**
     * @param row row index in range [0,N-1]
     * @param col column index in range [0,N-1]
     * @return true if the cell is open
     */
    @Override
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= myGrid.length || col < 0 || col >= myGrid.length) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        return myGrid[row][col] == true;
    }

    /**
     * @param row row index in range [0,N-1]
     * @param col column index in range [0,N-1]
     * @return true if the cell is full
     */
    @Override
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= myGrid.length || col < 0 || col >= myGrid.length) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row, col));
        }
        int index = row * (myGrid.length) + col;
        return myFinder.connected(index, VTOP);
    }

    /**
     * @return true if the top is connected to the bottom
     */
    @Override
    public boolean percolates() {
        return myFinder.connected(VTOP, VBOTTOM);
    }

    /**
     * @return # of open sites
     */
    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }

    public int index(int row, int col) {
        return (row) * (myGrid.length) + col;
    }
}
