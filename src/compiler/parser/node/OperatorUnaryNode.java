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
     * Instantiate a node to represent the parsed expression.
     * @param type the type of expression
     * @param lexeme the lexeme to report errors with
     * @param nodes the children of this node
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

    /**
     * Evaluate the expression under the environment.
     * @param env the environment to evaluate the
     * @return the list of expression returned from the evaluated expression
     * @throws RunTimeException
     */
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