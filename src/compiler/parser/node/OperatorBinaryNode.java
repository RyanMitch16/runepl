package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.interpreter.TypeBoolean;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

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

    public static OperatorBinaryNode createOperationAnd(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationAnd, op, left, right);
    }

    public static OperatorBinaryNode createOperationAddition(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationAddition, op, left, right);
    }

    public static OperatorBinaryNode createOperationDivision(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationDivision, op, left, right);
    }

    public static OperatorBinaryNode createGreaterThan(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationGreaterThan, op, left, right);
    }

    public static OperatorBinaryNode createGreaterThanEqual(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationGreaterThanEqual, op, left, right);
    }

    public static OperatorBinaryNode createLessThan(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationLessThan, op, left, right);
    }

    public static OperatorBinaryNode createLessThanEqual(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationLessThanEqual, op, left, right);
    }

    public static OperatorBinaryNode createOperationModulus(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationModulus, op, left, right);
    }

    public static OperatorBinaryNode createOperationMultiplication(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationMultiplication, op, left, right);
    }

    public static OperatorBinaryNode createOperationOr(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationOr, op, left, right);
    }

    public static OperatorBinaryNode createOperationSubtraction(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationSubtraction, op, left, right);
    }

    public static OperatorBinaryNode createOperationEquality(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationEquality, op, left, right);
    }

    public static OperatorBinaryNode createOperationInverseEquality(Lexeme op, Node left, Node right){
        return new OperatorBinaryNode(NodeType.OperationInverseEquality, op, left, right);
    }



    public ReturnTypeList eval(Environment env) throws RunTimeException{

        ReturnTypeList leftValueExpressions = children[0].eval(env);
        if (leftValueExpressions.size() == 0)
            throw new RunTimeException(lexeme,"Unable to "+lexeme+" with a null left operand");

        if (leftValueExpressions.size() > 1)
            throw new RunTimeException(lexeme,"Unable to "+lexeme+" with a multiple left operands");

        //Allow for short circuiting
        if (type == NodeType.OperationAnd) {
            if (!(leftValueExpressions.getFirst() instanceof TypeBoolean)) {
                throw new RunTimeException(lexeme, "Unable to and with a non boolean");
            }

            //Evaluate the and to false if the first expression is
            if (!((TypeBoolean) leftValueExpressions.getFirst()).value) {
                return new ReturnTypeList(new TypeBoolean(false));
            }
        }
        if (type == NodeType.OperationOr) {
            if (!(leftValueExpressions.getFirst() instanceof TypeBoolean)) {
                throw new RunTimeException(lexeme, "Unable to and with a non boolean");
            }

            //Evaluate the and to false if the first expression is
            if (((TypeBoolean) leftValueExpressions.getFirst()).value) {
                return new ReturnTypeList(new TypeBoolean(true));
            }
        }

        ReturnTypeList rightValueExpressions = children[1].eval(env);
        if (rightValueExpressions.size() == 0)
            throw new RunTimeException(lexeme,"Unable to "+lexeme+" with a right left operand");

        if (rightValueExpressions.size() > 1)
            throw new RunTimeException(lexeme,"Unable to "+lexeme+" with a multiple right operands");

        if (type == NodeType.OperationAddition)
            return new ReturnTypeList(leftValueExpressions.getFirst().plus(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationAnd)
            return new ReturnTypeList(leftValueExpressions.getFirst().and(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationDivision)
            return new ReturnTypeList(leftValueExpressions.getFirst().divides(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationEquality)
            return new ReturnTypeList(leftValueExpressions.getFirst().equalEquals(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationGreaterThan)
            return new ReturnTypeList(leftValueExpressions.getFirst().greaterThan(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationGreaterThanEqual)
            return new ReturnTypeList(leftValueExpressions.getFirst().greaterThanEqualTo(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationInverseEquality)
            return new ReturnTypeList(leftValueExpressions.getFirst().notEquals(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationOr)
            return new ReturnTypeList(leftValueExpressions.getFirst().or(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationSubtraction)
            return new ReturnTypeList(leftValueExpressions.getFirst().minus(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationLessThan)
            return new ReturnTypeList(leftValueExpressions.getFirst().lessThan(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationLessThanEqual)
            return new ReturnTypeList(leftValueExpressions.getFirst().lessThanEqualTo(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationModulus)
            return new ReturnTypeList(leftValueExpressions.getFirst().modulus(lexeme, rightValueExpressions.getFirst()));

        if (type == NodeType.OperationMultiplication)
            return new ReturnTypeList(leftValueExpressions.getFirst().times(lexeme, rightValueExpressions.getFirst()));

        return null;
    }
}
