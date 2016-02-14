package compiler.parser.node;

import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class OperatorUnaryNode extends Node {

    /**
     * @param type
     * @param lexeme
     * @param nodes
     */
    private OperatorUnaryNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static OperatorUnaryNode createOperationInversion(Lexeme op, Node operand) {
        return new OperatorUnaryNode(NodeType.OperationInversion, op, operand);
    }

    public static OperatorUnaryNode createOperationNegation(Lexeme op, Node operand) {
        return new OperatorUnaryNode(NodeType.OperationNegation, op, operand);
    }

    public ReturnTypeList eval(Environment env) {
        return null;
    }
}