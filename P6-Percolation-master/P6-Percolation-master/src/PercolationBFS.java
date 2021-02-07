import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast {
    public PercolationBFS(int size) {
        super(size);
    }

    @Override
    protected void dfs(int row, int col) {

        Queue<int[]> qp = new LinkedList<>();
        myGrid[row][col] = FULL;
        qp.add(new int[]{row, col});
        while (qp.size() > 0) {
            int[] holder = qp.remove();
            int dr = holder[0];
            int dc = holder[1];
            if (inBounds(dr - 1, dc) && !isFull(dr - 1, dc) && isOpen(dr - 1, dc)) {
                myGrid[dr - 1][dc] = FULL;
                qp.add(new int[]{dr - 1, dc});
            }
            if (inBounds(dr + 1, dc) && !isFull(dr + 1, dc) && isOpen(dr + 1, dc)) {
                myGrid[dr + 1][dc] = FULL;
                qp.add(new int[]{dr + 1, dc});
            }
            if (inBounds(dr, dc - 1) && !isFull(dr, dc - 1) && isOpen(dr, dc - 1)) {
                myGrid[dr][dc - 1] = FULL;
                qp.add(new int[]{dr, dc - 1});
            }
            if (inBounds(dr, dc + 1) && !isFull(dr, dc + 1) && isOpen(dr, dc + 1)) {
                myGrid[dr][dc + 1] = FULL;
                qp.add(new int[]{dr, dc + 1});
            }
        }
    }
}