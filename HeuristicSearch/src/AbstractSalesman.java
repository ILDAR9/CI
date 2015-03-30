import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by ildar on 01.03.15.
 * <p/>
 * The start point is always the last point in matrix and it is named as "Current place"
 */

abstract class AbstractSalesman {
    protected final String FILE_PATH;
    protected int[][] distancesMatrix;
    protected int n;							 /* n places and n+1 indicates the current place */
    protected boolean used[];   /* for dynamics generating of permutations */
    protected int pathTemp[];  /* save found best path */
    protected int minPathLength;

    public AbstractSalesman(String filePath) {
        FILE_PATH = filePath;
    }

    private void readMatrix(String filePath, int test_num) {
        try (Scanner scanner = new Scanner(new FileReader(filePath))) {
            passRows(scanner, test_num);
            int placeCount = scanner.nextInt();
            used = new boolean[placeCount];
            pathTemp = new int[placeCount];
            n = placeCount - 1;
            scanner.nextLine();
            distancesMatrix = new int[placeCount][];
            for (int i = 0; i < placeCount; i++) {
                distancesMatrix[i] = new int[placeCount];
                for (int j = 0; j < placeCount; j++) {
                    distancesMatrix[i][j] = scanner.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void passRows(Scanner scanner, int test_num) {
        for (int i = 0; i < test_num * 10 + test_num; i++) {
            scanner.nextLine();
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(n + 1);
        sb.append("\n");
        for (int i = 1; i <= n; i++) {
            System.out.println(i);
        }
        for (int i = 0; i <= n; i++) {
            sb.append('\n');
            for (int j = 0; j <= n; j++) {
                sb.append(distancesMatrix[i][j] + " ");
            }
        }
        return sb.toString();
    }

    public void exec() {
        for (int test_num = 0; test_num < Main.TEST_COUNT; test_num++) {
            System.out.println("|Test #" + (test_num + 1));
            readMatrix(FILE_PATH, test_num);
            runAlgo();
        }
    }

    protected abstract void runAlgo();


    public void printResult() {
        System.out.println(getClass().getName());
        int index;
        System.out.println("____________________________\nResult:");
        System.out.print(n + 1);
        for (int i = 0; i < n; i++) {
            index = pathTemp[i];
            System.out.print(" " + (index + 1));
        }
        System.out.println(" " + (n + 1));
        System.out.println("Path length: " + minPathLength + "\n");
    }
}
