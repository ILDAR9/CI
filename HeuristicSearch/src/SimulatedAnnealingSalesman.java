import java.util.Random;

/**
 * Created by ildar on 09.03.15.
 */
public class SimulatedAnnealingSalesman extends HillClimberSalesman {


    public SimulatedAnnealingSalesman(String filePath) {
        super(filePath);
        fileOutput = getClass().getName() + ".txt";
    }

    @Override
    protected void salesmanFindPath() {
        int newRoute[],
                deltaDistance,
                curDistance,
                iter_num = 0;
        final double absoluteTemperature = 0.0001;
        Random random = new Random(1);
        int[] curPath = generateRandomPath();
        pathTemp = curPath;
        minPathLength = calculateTotalDistance(pathTemp);
        curDistance = minPathLength;
        for (double temperature = 1000.0, coolingRate = 0.9999; iter_num++ < ITER_NUM && temperature > absoluteTemperature;
             temperature *= coolingRate) {
            start_again:
            for (int i = 0; i < n; i++) {
                for (int k = i + 1; k < n; k++) {
                    newRoute = optSwap(curPath, i, k);
                    deltaDistance = calculateTotalDistance(newRoute) - curDistance;
                    //if the new order has a smaller distance
                    //or if the new order has a larger distance but
                    //satisfies Boltzman condition then accept the arrangement
                    if ((deltaDistance < 0) || (Math.exp(-deltaDistance / temperature) > random.nextDouble())) {
                        curDistance = curDistance + deltaDistance;
                        curPath = newRoute;
                        // Keep track of the best solution found
                        if (curDistance < minPathLength) {
                            pathTemp = curPath;
                            minPathLength = curDistance;
                            break start_again;
                        }
                    }

                }
            }
        }
    }

}



/*
satisfies Boltzman condition then accept the arrangement
 */
