package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.NodeType;

public class ExpressionListNode extends NodeList {

    /**
     * Instantiate a node to represent the parsed expression.
     * @param type the type of expression
     * @param comma the lexeme to report errors with
     * @param head the children of this node
     */
    private ExpressionListNode(NodeType type, Lexeme comma, Node head) {
        super(type, comma, head);
    }

    private ExpressionListNode(NodeType type, Lexeme comma, Node head, NodeList next) {
        super(type, comma, head, next);
    }

    public static ExpressionListNode createExpressionList(Lexeme comma, Node head) {
        return new ExpressionListNode(NodeType.ExpressionList, comma, head);
    }

    public static ExpressionListNode createExpressionList(Lexeme comma, Node head, NodeList next) {
        return new ExpressionListNode(NodeType.ExpressionList, comma, head, next);
    }

    /**
     * Evaluate the expression under the environment.
     * @param env the environment to evaluate the
     * @return the list of expression returned from the evaluated expression
     * @throws RunTimeException
     */
    public ReturnTypeList eval(Environment env) throws RunTimeException{
        ReturnTypeList expression = new ReturnTypeList();

        NodeList.Iterator expressionIterator = getIterator();
        while (expressionIterator.hasNext()) {
            expression.concat(expressionIterator.next().eval(env));
        }

        return expression;
    }

}
