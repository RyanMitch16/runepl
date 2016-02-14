package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnType;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class AccessElementNode extends Node {

    /**
     * @param type
     * @param lexeme
     * @param nodes
     */
    private AccessElementNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static AccessElementNode createElementAccessor(Lexeme dot, Node expression, Node index) {
        return new AccessElementNode(NodeType.AccessElement, dot, expression, index);
    }

    public ReturnTypeList eval(Environment env) throws RunTimeException {
        return null;
    }
}