package compiler.parser.node;

import compiler.interpreter.Environment;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

import java.util.LinkedList;

public class ReturnNode extends Node {

    /**
     * @param type
     * @param lexeme
     * @param nodes
     */
    private ReturnNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static ReturnNode createReturnStatement(Lexeme ret) {
        return new ReturnNode(NodeType.ReturnStatement, ret);
    }

    public static ReturnNode createReturnStatement(Lexeme ret, Node expressions) {
        return new ReturnNode(NodeType.ReturnStatement, ret, expressions);
    }

    public Object eval(Environment env) {
        if (children.length == 0) {
            return new LinkedList();
        } else {

            Object val = children[0].eval(env);
            if (val instanceof AnonFunctionNode && ((AnonFunctionNode) val).env == null) {
                ((AnonFunctionNode) val).env = env;
                System.out.println("env");
            }

            return val;
        }
    }
}