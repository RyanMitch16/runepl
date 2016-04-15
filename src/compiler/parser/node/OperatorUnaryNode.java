package compiler.parser.node;

import compiler.Lexer;
import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

import java.util.Map;

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

    public ReturnTypeList eval(Environment env) throws RunTimeException {

        ReturnTypeList leftValueExpressions = children[0].eval(env);
        if (leftValueExpressions.size() == 0)
            throw new RunTimeException(lexeme,"Unable to "+lexeme+" with a null operand");

        if (leftValueExpressions.size() > 1)
            throw new RunTimeException(lexeme,"Unable to "+lexeme+" with a multiple operands");

        if (type == NodeType.OperationInversion)
            return new ReturnTypeList(leftValueExpressions.getFirst().invert(lexeme));

        if (type == NodeType.OperationNegation)
            return new ReturnTypeList(leftValueExpressions.getFirst().negate(lexeme));

        return null;
    }

}