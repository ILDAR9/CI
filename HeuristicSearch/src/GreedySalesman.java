/**
 * Created by ildar on 07.03.15.
 */
public class GreedySalesman extends AbstractSalesman {


    public GreedySalesman(String filePath) {
        super(filePath);
        minPathLength = 0;
    }

    @Override
    protected void runAlgo() {
        salesmanFindPath(n);
        printResult();
    }

    private void salesmanFindPath(int from) {
        int next, j = 0;
        while ((next = findNearest(from)) != -1) {
            pathTemp[j++] = next;
            from = next;
        }
        minPathLength += distancesMatrix[from][n];
    }

    private int findNearest(int from) {
        int minEdgeLength = -1, to = -1;
        for (int i = 0; i < n; i++) {
            if (i != from && !used[i] && (minEdgeLength == -1 || minEdgeLength > distancesMatrix[from][i])) {
                minEdgeLength = distancesMatrix[from][i];
                to = i;
            }
        }
        if (to != -1) {
            used[to] = true;
            minPathLength += minEdgeLength;
        }
        return to;
    }

}
