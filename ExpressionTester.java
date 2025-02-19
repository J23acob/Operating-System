/**
 * A simple class to demonstrate how to use ExpressionTree.
 */
public class ExpressionTester {

    /**
     * Splits the user's expression on spaces, builds a tree, evaluates it, and prints the result.
     */
    public static void evaluateExpression(String expression) {
        String[] tokens = expression.split(" ");                // Breaks the string into tokens by spaces
        ExpressionTree.Node root = ExpressionTree.buildTree(tokens); // Build the tree from those tokens
        double result = ExpressionTree.evaluate(root);          // Evaluate the resulting expression tree
        System.out.println("Expression: " + expression);        // Print original expression
        System.out.println("Result: " + result);                // Print computed value
    }

    /**
     * Main just suggests how to use evaluateExpression, but doesn't read input.
     * You can customize it to test a hardcoded expression if you want.
     */
    public static void main(String[] args) {
        System.out.println("Use evaluateExpression() with a string argument (e.g., \"3 + 4\").");
    }
}
