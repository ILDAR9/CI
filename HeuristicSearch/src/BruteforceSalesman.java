/**
 * Created by ildar on 07.03.15.
 */
public class BruteforceSalesman extends AbstractSalesman {

    private int path[];       /* backtracking */

    public BruteforceSalesman(String filePath) {
        super(filePath);
        minPathLength = Integer.MAX_VALUE;
    }

    @Override
    protected void runAlgo() {
        path = new int[n + 1];
        salesmanFindPath(0);
        printResult();
    }

    void salesmanFindPath(int pos) {
        int i;
        if (pos == n) {
            traceGenPath();
            return;
        }
        for (i = 0; i < n; i++) {
            if (!used[i]) {
                used[i] = true;
                path[pos] = i;

                salesmanFindPath(pos + 1);

                path[pos] = 0;
                used[i] = false;
            }
        }
    }

    void traceGenPath() {
        int pred_i = n, path_length = 0;
        for (int i = 0; i < n; i++) {
            path_length += distancesMatrix[pred_i][path[i]];
            pred_i = path[i];		/* persist index */
        }
        path_length += distancesMatrix[path[n-1]][n];
        if (path_length < minPathLength) {
            minPathLength = path_length;
            System.arraycopy(path, 0, pathTemp, 0, pathTemp.length);
        }
    }
}

/* First test right ans:
5 Current Place
2 Cars r Us
1 Top Bike Shop
4 The Hot Chocolate Emporium
3 Everything Electronic
5 Current Place
Path length: 240
 */
