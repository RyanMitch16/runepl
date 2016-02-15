package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnType;
import compiler.interpreter.ReturnTypeList;
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

    public ReturnTypeList eval(Environment env) throws RunTimeException{

        ReturnTypeList value = children[0].eval(env);
        if (value != null && (children[0] instanceof ReturnNode || children[0] instanceof IfStatementNode))
            return value;

        if (children.length == 2) {
            value = children[1].eval(env);
            if (value != null)
                return value;
        }

        return new ReturnTypeList();
    }
}