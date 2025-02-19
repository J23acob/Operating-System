import java.util.Stack;  // Provides the Stack data structure (LIFO)

/**
 * Builds and evaluates a binary expression tree from an infix expression.
 */
class ExpressionTree {
    
    /**
     * Node class: Each object here holds a value (number or operator) and
     * references to left and right child nodes in the tree.
     */
    static class Node {
        String value;  // Stores either a number ("5") or an operator ("+")
        Node left;     // Reference to the left child subtree
        Node right;    // Reference to the right child subtree

        // Constructor for a leaf node (just a single value)
        Node(String value) {
            this.value = value;    // Save the value (like "5" or "3")
            this.left = null;      // Leaf node initially has no children
            this.right = null;
        }

        // Constructor for an operator node with two child subtrees
        Node(String value, Node left, Node right) {
            this.value = value;    // Save operator symbol (like "+", "*", "^")
            this.left = left;      // Attach left subtree
            this.right = right;    // Attach right subtree
        }
    }

    /**
     * Checks if a string token is one of the known operators +, -, *, /, or ^.
     */
    private static boolean isOperator(String token) {
        return "+-*/^".contains(token);  // If token is in that string, it’s an operator
    }

    /**
     * Assigns numerical precedence to each operator:
     * ^ = 3, * / = 2, + - = 1
     * Higher number means higher precedence.
     */
    private static int precedence(String op) {
        if (op.equals("^")) return 3;                  // Exponent
        if (op.equals("*") || op.equals("/")) return 2; // Multiply or Divide
        if (op.equals("+") || op.equals("-")) return 1; // Add or Subtract
        return 0;  // Fallback for any unrecognized string (shouldn't happen)
    }

    /**
     * Converts an array of infix tokens into a binary expression tree.
     * Uses two stacks: one for nodes (operands/subtrees) and one for operators.
     */
    public static Node buildTree(String[] tokens) {
        Stack<Node> nodes = new Stack<>();  // Stores Node objects (numbers/subtrees)
        Stack<String> ops = new Stack<>();  // Stores operator strings (+, -, *, /, ^)

        // Process each token from left to right
        for (String token : tokens) {
            if (token.matches("\\d+")) {            // If it’s a number (one or more digits)
                nodes.push(new Node(token));        // Create a leaf node and push onto node stack
            } else if (isOperator(token)) {         // If it’s an operator
                // While there’s an operator on top of ops with >= precedence, build a subtree first
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token)) {
                    Node right = nodes.pop();       // Right child from the node stack
                    Node left = nodes.pop();        // Left child from the node stack
                    nodes.push(new Node(ops.pop(), left, right)); // Form an operator node and push back
                }
                ops.push(token); // Push the current operator onto the operator stack
            }
        }

        // If there are leftover operators, build subtrees until no operators remain
        while (!ops.isEmpty()) {
            Node right = nodes.pop();           // Right child
            Node left = nodes.pop();            // Left child
            nodes.push(new Node(ops.pop(), left, right)); // Create operator node and push
        }

        return nodes.pop(); // Final node on the stack is the root of the expression tree
    }

    /**
     * Evaluates the tree recursively in post-order: compute children first,
     * then apply the operator at the node.
     */
    public static double evaluate(Node root) {
        if (root == null) return 0;                 // Safety check if node is null

        if (!isOperator(root.value)) {              // If the node is a number
            return Double.parseDouble(root.value);  // Convert and return as double
        }

        // Otherwise, the node is an operator, so evaluate left and right subtrees first
        double leftVal = evaluate(root.left);
        double rightVal = evaluate(root.right);

        // Apply the operator stored in the node to the two subtree values
        switch (root.value) {
            case "+": return leftVal + rightVal;
            case "-": return leftVal - rightVal;
            case "*": return leftVal * rightVal;
            case "/": return leftVal / rightVal;
            case "^": return Math.pow(leftVal, rightVal); // Use Math.pow for exponent
            default:  return 0; // Fallback if something unexpected appears
        }
    }
}
