package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
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

    public ReturnTypeList eval(Environment env) throws RunTimeException{
        if (children.length == 0) {
            return new ReturnTypeList();
        } else {
            return children[0].eval(env);
        }
    }

}