package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.NodeType;

import java.util.LinkedList;

public class IdentifierListNode extends NodeList {

    /**
     * Instantiate a node to represent the parsed expression.
     * @param type the type of expression
     * @param lexeme the lexeme to report errors with
     * @param head the children of this node
     */
    private IdentifierListNode(NodeType type, Lexeme lexeme, Node head) {
        super(type, lexeme, head);
    }

    private IdentifierListNode(NodeType type, Lexeme lexeme, Node head, NodeList next) {
        super(type, lexeme, head, next);
    }

    public static IdentifierListNode createIdentifierList(Lexeme comma, Node head) {
        return new IdentifierListNode(NodeType.IdentifierList, comma, head);
    }

    public static IdentifierListNode createIdentifierList(Lexeme comma, Node head, NodeList next) {
        return new IdentifierListNode(NodeType.IdentifierList, comma, head, next);
    }

    /**
     * Get the list of identifiers in the list
     * @return the list of identifier lexemes
     * @throws RunTimeException
     */
    public LinkedList<Lexeme> getIdentifierNames() throws RunTimeException{
        NodeList.Iterator identifierIterator = getIterator();
        LinkedList<Lexeme> identifiers = new LinkedList<>();

        while (identifierIterator.hasNext()) {
            identifiers.add(((IdentifierNode) identifierIterator.next()).getIdentifierName());
        }

        return identifiers;
    }

    /**
     * Evaluate the expression under the environment.
     * @param env the environment to evaluate the
     * @return the list of expression returned from the evaluated expression
     * @throws RunTimeException
     */
    public ReturnTypeList eval(Environment env) {
        return null;
    }
}
