
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PersonalityTest {
    private static final String[] DIMENSIONS = {"Extrovert/Introvert", "Sensing/iNtuition", "Thinking/Feeling", "Judging/Perceiving"};
    private static final String[] DIMENSION_CODES = {"EI", "SN", "TF", "JP"};

    public static void main(String[] args) {
        try {
            File file = new File("small_input.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                String answers = scanner.nextLine();
                processAnswers(name, answers);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public static void processAnswers(String name, String answers) {
        int[] aCounts = new int[4];
        int[] bCounts = new int[4];
        for (int i = 0; i < answers.length(); i++) {
            char answer = Character.toUpperCase(answers.charAt(i));
            if (answer == 'A') {
                incrementCount(aCounts, i);
            } else if (answer == 'B') {
                incrementCount(bCounts, i);
            }
        }
        double[] bPercentages = calculateBPercentages(aCounts, bCounts);
        String personalityType = getPersonalityType(bPercentages);
        System.out.println(name + ": " + personalityType);
    }

    private static void incrementCount(int[] counts, int index) {
        int dimension = getDimension(index);
        counts[dimension]++;
    }

    private static int getDimension(int index) {
        int group = index / 7;
        int offset = index % 7;
        if (offset == 0) {
            return 0; // Extrovert/Introvert
        } else if (offset <= 2) {
            return 1; // Sensing/iNtuition
        } else if (offset <= 4) {
            return 2; // Thinking/Feeling
        } else {
            return 3; // Judging/Perceiving
        }
    }

    private static double[] calculateBPercentages(int[] aCounts, int[] bCounts) {
        double[] bPercentages = new double[4];
        for (int i = 0; i < 4; i++) {
            int total = aCounts[i] + bCounts[i];
            bPercentages[i] = (double) bCounts[i] / total * 100;
        }
        return bPercentages;
    }

    private static String getPersonalityType(double[] bPercentages) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int roundedPercentage = (int) Math.round(bPercentages[i]);
            if (roundedPercentage > 50) {
                sb.append(DIMENSION_CODES[i].charAt(1));
            } else {
                sb.append(DIMENSION_CODES[i].charAt(0));
            }
        }
        return sb.toString();
    }
}