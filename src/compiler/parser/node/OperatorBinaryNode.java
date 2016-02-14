package compiler.parser.node;

import compiler.BuildException;
import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnType;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

import java.util.LinkedList;

public class OperatorBinaryNode extends Node {

    /**
     *
     * @param type
     * @param lexeme
     * @param nodes
     */
    private OperatorBinaryNode(NodeType type, Lexeme lexeme, Node... nodes){
        super(type, lexeme, nodes);
    }

    public static OperatorBinaryNode createOperationAddition(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationAddition, op, left, right);
    }

    public static OperatorBinaryNode createOperationDivision(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationDivision, op, left, right);
    }

    public static OperatorBinaryNode createOperationModulus(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationModulus, op, left, right);
    }

    public static OperatorBinaryNode createOperationMultiplication(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationMultiplication, op, left, right);
    }

    public static OperatorBinaryNode createOperationSubtraction(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationSubtraction, op, left, right);
    }


    public ReturnTypeList eval(Environment env) throws RunTimeException{

        ReturnTypeList leftValueExpressions = children[0].eval(env);
        if (leftValueExpressions.size() == 0)
            throw new RunTimeException(lexeme,"Unable to "+lexeme+" with a null left operand");

        if (leftValueExpressions.size() > 1)
            throw new RunTimeException(lexeme,"Unable to "+lexeme+" with a multiple left operands");

        ReturnTypeList rightValueExpressions = children[1].eval(env);
        if (rightValueExpressions.size() == 0)
            throw new RunTimeException(lexeme,"Unable to "+lexeme+" with a right left operand");

        if (rightValueExpressions.size() > 1)
            throw new RunTimeException(lexeme,"Unable to "+lexeme+" with a multiple right operands");

        if (type == NodeType.OperationAddition)
            return new ReturnTypeList(leftValueExpressions.getFirst().plus(lexeme, rightValueExpressions.getFirst()));

        return null;
    }
}
