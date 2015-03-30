import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by ildar on 08.03.15.
 */
public class HillClimberSalesman extends AbstractSalesman {
    protected static final int ITER_NUM = 1000;
    protected String fileOutput;

    private static final int EXECUT_COUNT = 30;

    public HillClimberSalesman(String filePath) {
        super(filePath);
        fileOutput = getClass().getName() + ".txt";
    }

    @Override
    protected void runAlgo() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < EXECUT_COUNT; i++) {
            System.out.println("\n\t\t\tExec: " + (i + 1));
            salesmanFindPath();
            sb.append(minPathLength + "\n");
            printResult();
        }
        try (FileWriter fileWriter = new FileWriter(fileOutput)) {

            fileWriter.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected static int[] optSwap(int[] route, int k1, int k2) {
        int[] newRoute = new int[route.length];
        System.arraycopy(route, 0, newRoute, 0, k1);
        for (int j = k1, i = k2; i >= k1; i--) {
            newRoute[j++] = route[i];
        }
        System.arraycopy(route, k2 + 1, newRoute, k2 + 1, newRoute.length - k2 - 1);
        return newRoute;
    }

    protected int calculateTotalDistance(int[] path) {
        int predInd = n, distLength = 0;
        for (int i = 0; i <= n; i++) {
            distLength += distancesMatrix[predInd][path[i]];
            predInd = path[i];
        }
        distLength += distancesMatrix[path[n-1]][n];
        return distLength;
    }

    protected int[] generateRandomPath() {
        int[] ranPath = new int[n + 1];
        for (int i = 0; i < n; i++) {
            ranPath[i] = i;
        }
        Random rnd = new Random();
        for (int i = n - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ranPath[index];
            ranPath[index] = ranPath[i];
            ranPath[i] = a;
        }

        return ranPath;
    }

    protected void salesmanFindPath() {
        int newRoute[],
                newDistance,
                iterCount = 0;
        boolean improvementExists;
        pathTemp = generateRandomPath();
        minPathLength = calculateTotalDistance(pathTemp);

        do {   //every iteration is attempt to
            improvementExists = false;
            start_again:
            for (int i = 0; i < n; i++) {
                for (int k = i + 1; k < n; k++) {
                    iterCount++;
                    newRoute = optSwap(pathTemp, i, k);
                    newDistance = calculateTotalDistance(newRoute);
                    if (newDistance < minPathLength) {
                        improvementExists = true;
                        minPathLength = newDistance;
                        pathTemp = newRoute;
                        break start_again;
                    }
                }
            }
        }
        while (iterCount < ITER_NUM && improvementExists); // if improvementExists is False then there is there is not any improvements from this state
    }

}

/*
You can actually reduce the time per iteration to O(n log n) by noting that,
 if n is large, most of the swap calculations don't change from one iteration to tne next,
 so you can compute them all at the beginning, and then, on each iteration of the main loop,
 only recalculate those that have been affected. Then you keep all the calculated swaps in a MaxHeap.
 On each iteration of the main loop you are doing O(n) modifications in the MaxHeap,
 so these take O(n log n) all told. The initialization before running the first iteration of O(n2).
 If you do this, you can probably run the algorithm for tens of thousands of cities
 (depending on how many iterations of the outer loop are executed, which is hard to predict).
 */
