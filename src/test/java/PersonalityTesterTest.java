import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PersonalityTesterTest {

    @Test
    public void testProcessAnswers() {
        try {
            File inputFile = new File("big_input.txt");
            Scanner inputScanner = new Scanner(inputFile);
            Scanner solutionScanner = new Scanner(new File("big_correct_output.txt"));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            while (inputScanner.hasNextLine()) {
                String name = inputScanner.nextLine();
                String answers = inputScanner.nextLine();
                PersonalityTest.processAnswers(name, answers);
            }

            String[] output = outputStream.toString().split("\n");
            while (solutionScanner.hasNextLine()) {
                String expectedLine = solutionScanner.nextLine();
                if (output.length > 0) {
                    String actualLine = output[0].trim();
                    assertEquals(expectedLine, actualLine);
                    output = removeFirstLine(output);
                } else {
                    break;
                }
            }

            for (String line : output) {
                assertTrue(line.trim().isEmpty());
            }

            assertFalse(solutionScanner.hasNextLine());

            inputScanner.close();
            solutionScanner.close();
        } catch (FileNotFoundException e) {
            throw new AssertionError("Cannot open input files.", e);
        }
    }

    private String[] removeFirstLine(String[] lines) {
        String[] newLines = new String[lines.length - 1];
        System.arraycopy(lines, 1, newLines, 0, newLines.length);
        return newLines;
    }
}