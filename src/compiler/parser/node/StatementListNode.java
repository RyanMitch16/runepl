package compiler.parser.node;

import compiler.interpreter.Environment;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.NodeType;

public class StatementListNode extends NodeList {

    private StatementListNode(NodeType type, Lexeme lexeme, Node head) {
        super(type, lexeme, head);
    }

    private StatementListNode(NodeType type, Lexeme lexeme, Node head, NodeList next) {
        super(type, lexeme, head, next);
    }

    public static StatementListNode createStatementList(Lexeme comma, Node head) {
        return new StatementListNode(NodeType.StatementList, comma, head);
    }

    public static StatementListNode createStatementList(Lexeme comma, Node head, NodeList next) {
        return new StatementListNode(NodeType.StatementList, comma, head, next);
    }

    public Object eval(Environment env){

        if (children.length == 1) {
            return children[0].eval(env); }
        children[0].eval(env);
        return children[1].eval(env);
    }
}