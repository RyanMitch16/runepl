package compiler.parser.node;

import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.NodeType;

import java.util.LinkedList;

public class IdentifierListNode extends NodeList {

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

    public LinkedList<String> getIdentifierNames() {
        NodeList.Iterator identifierIterator = getIterator();
        LinkedList<String> identifiers = new LinkedList<>();

        while (identifierIterator.hasNext()) {
            identifiers.add(((IdentifierNode) identifierIterator.next()).getIdentifierName());
        }

        return identifiers;
    }

    public ReturnTypeList eval(Environment env) {
        return null;
    }
}
