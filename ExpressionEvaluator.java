import java.util.Stack;

/**
 * A utility class for evaluating mathematical expressions.
 * 
 * @author Marti Lonnemann
 * @version 2.0
 *          Final Project - Batch Calculator
 *          Fall/2023
 */
public class ExpressionEvaluator {

    /**
     * Evaluates a mathematical expression and returns the result.
     * 
     * @param expression The mathematical expression to evaluate.
     * @return The result of the evaluated expression as a MathValue object.
     */
    public static MathValue evaluate(String expression) {

        // Preprocess the expression to handle cases like 3(3)(4)
        String processedExpression = preprocessExpression(expression);

        // Convert infix expression to postfix
        String postfix = infixToPostfix(processedExpression);

        // Evaluate postfix expression
        return evaluatePostfix(postfix);
    }

    /**
     * Preprocesses the input expression, handling special cases such as
     * multiplication without an operator.
     * 
     * @param expression The expression to preprocess.
     * @return A processed expression with multiplication (*) inserted where
     *         necessary.
     */
    private static String preprocessExpression(String expression) {
        StringBuilder processed = new StringBuilder();
        char prev = ' '; // Initialize with a space

        for (char current : expression.toCharArray()) {
            if ((Character.isDigit(prev) && current == '(') ||
                    (prev == ')' && (Character.isDigit(current) || current == '('))) {
                processed.append('*');
            }
            processed.append(current);
            prev = current;
        }

        return processed.toString();
    }

    /**
     * Converts an infix expression to a postfix expression.
     * 
     * @param infix The infix expression to convert.
     * @return The corresponding postfix expression.
     */
    private static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(' ');
                    postfix.append(stack.pop());
                }
                stack.pop();
            } else if  (isOperator(c)){
                while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
                    postfix.append(' ');
                    postfix.append(stack.pop());
                }
                postfix.append(' ');
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(' ').append(stack.pop());
        }

        System.out.println("Postfix: "+ postfix.toString()); //DEBUG

        return postfix.toString();
    }

    /**
     * Checks if a character is an operator (+, -, *, /).
     * 
     * @param c The character to check.
     * @return True if the character is an operator, false otherwise.
     */
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    /**
     * Determines the precedence of an operator.
     * Higher numbers indicate higher precedence.
     * 
     * @param operator The operator to check.
     * @return The precedence of the operator.
     */
    private static int getPrecedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        }
        return -1;
    }

    /**
     * Evaluates a postfix expression and returns the result.
     * 
     * @param postfix The postfix expression to evaluate.
     * @return The result of the evaluated postfix expression as a MathValue object.
     */
    private static MathValue evaluatePostfix(String postfix) {
        Stack<MathValue> stack = new Stack<>();
        for (String token : postfix.split(" ")) {
            if (token.isEmpty()) {
                continue;
            }
            if (isNumeric(token)) {
                stack.push(new MathValue(token));
            } else {
                MathValue a = stack.pop();
                MathValue b = stack.pop();

                switch (token.charAt(0)) {
                    case '+':
                        stack.push(b.add(a));
                        break;
                    case '-':
                        stack.push(b.subtract(a));
                        break;
                    case '*':
                        stack.push(b.multiply(a));
                        break;
                    case '/':
                        stack.push(b.divide(a));
                        break;
                }
            }
        }
        return stack.pop();
    }

    /**
     * Determines if a given string is numeric.
     * 
     * @param str The string to check.
     * @return True if the string is numeric, false otherwise.
     */
    private static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
