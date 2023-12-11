import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Processes a given file of a list of expressions and outputs their solutions
 * to a given output file
 * 
 * @author Marti Lonnemann
 * @version 2.0
 *          Final Project - Batch Calculator
 *          Fall/2023
 */
public class FileExpressionProcessor {

    public static void processExpressions(String inputFile, String outputFile) {
        Queue<String> lineQueue = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineQueue.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            while (!lineQueue.isEmpty()) {
                String line = lineQueue.poll();
                try {
                    MathValue result = ExpressionEvaluator.evaluate(line.trim());
                    writer.write(result.toString());
                } catch (Exception e) {
                    writer.write("Err: " + e.getMessage());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
}
