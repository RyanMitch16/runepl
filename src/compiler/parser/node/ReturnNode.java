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
     * Instantiate a node to represent the parsed expression.
     * @param type the type of expression
     * @param lexeme the lexeme to report errors with
     * @param nodes the children of this node
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

    /**
     * Evaluate the expression under the environment.
     * @param env the environment to evaluate the
     * @return the list of expression returned from the evaluated expression
     * @throws RunTimeException
     */
    public ReturnTypeList eval(Environment env) throws RunTimeException{
        if (children.length == 0) {
            return new ReturnTypeList();
        } else {
            return children[0].eval(env);
        }
    }

}