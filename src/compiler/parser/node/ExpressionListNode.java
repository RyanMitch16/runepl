package compiler.parser.node;

import compiler.interpreter.Environment;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.NodeType;

import java.util.LinkedList;
import java.util.ListIterator;

public class ExpressionListNode extends NodeList {

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

    public LinkedList<Object> eval(Environment env) {
        NodeList.Iterator expressionIterator = getIterator();
        LinkedList<Object> expression = new LinkedList<>();

        while (expressionIterator.hasNext()) {

            Object result = expressionIterator.next().eval(env);
            if (result instanceof LinkedList) {

                ListIterator<Object> valuesIt = ((LinkedList<Object>) result).listIterator();
                while (valuesIt.hasNext()) {
                    expression.add(valuesIt.next());
                }
            } else {
                expression.add(result);
            }
        }

        return expression;
    }

}
