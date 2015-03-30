import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by ildar on 07.03.15.
 * <p/>
 * Given a list of cities and the distances between each pair of cities,
 * what is the shortest possible route that visits each city exactly once and returns to the origin city?
 */

public class Main {

    public static final int TEST_COUNT = 1;  //Max value is 5

    private static final String FILE_INPUT_PATH = "input_temp.txt";

    public static void main(String[] args) {
        AbstractSalesman salesmanAlgos[] = {new BruteforceSalesman(FILE_INPUT_PATH), new GreedySalesman(FILE_INPUT_PATH), new HillClimberSalesman(FILE_INPUT_PATH), new SimulatedAnnealingSalesman(FILE_INPUT_PATH)};
        for (AbstractSalesman salesmanAlgo : salesmanAlgos) {
            salesmanAlgo.exec();
        }
//        generateTests();

    }

    /**
     * Have to be generated paths only with length 10, no less or bigger than 10.
     */
    private static void generateTests() {
        final int MAX_DISTANCE = 99, MIN_DISTANCE = 1, CITY_COUNT = 10;
        final int[] seeds = {1, 2, 3, 4, 5};
        int[][] distances;
        int ranNum;
        StringBuilder sb = new StringBuilder();
        try (FileWriter fileWriter = new FileWriter(FILE_INPUT_PATH, false)) {
            for (int i = 0; i < TEST_COUNT; i++) {
                Random random = new Random(seeds[i]);
                distances = new int[CITY_COUNT][];
                for (int j = 0; j < CITY_COUNT; j++) {
                    distances[j] = new int[CITY_COUNT];
                }
                for (int row_i = 0; row_i < CITY_COUNT; row_i++) {
                    for (int col_i = row_i + 1; col_i < CITY_COUNT; col_i++) {
                        ranNum = random.nextInt(MAX_DISTANCE) + MIN_DISTANCE;
                        distances[col_i][row_i] = ranNum;
                        distances[row_i][col_i] = ranNum;
                    }
                }
                for (int[] a : distances)
                    System.out.println(Arrays.toString(a));
                System.out.println();

                sb.setLength(0); //clear
                sb.append(CITY_COUNT); //set city count
                sb.append("\n");
                for (int[] row : distances) {   //distances
                    for (int cell : row) {
                        sb.append(cell);
                        sb.append(" ");
                    }
                    sb.append("\n");
                }
                fileWriter.write(sb.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
