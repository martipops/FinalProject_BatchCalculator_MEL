import java.util.Scanner;

/**
 * The main Application class for the calculator.
 * Determines whether to enter user-input mode or to process a given file.
 * 
 * @author Marti Lonnemann
 * @version 2.0
 *          Final Project - Batch Calculator
 *          Fall/2023
 */

public class Application {

    public static void main(String[] args) {
        if (args.length > 0) {
            String inputFile = args[0];
            String outputFile = "output.txt";
            FileExpressionProcessor.processExpressions(inputFile, outputFile);
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a mathematical expression (or type 'exit' to quit):");

            while (true) {
                System.out.print("> ");
                String expression = scanner.nextLine();

                if ("exit".equalsIgnoreCase(expression)) {
                    break;
                }

                try {
                    MathValue result = ExpressionEvaluator.evaluate(expression);
                    System.out.println("Result: " + result);
                } catch (Exception e) {
                    System.out.println("Err: " + e.getMessage());
                }
            }

            scanner.close();
        }
    }
}
