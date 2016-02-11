package compiler.parser.node;

import compiler.BuildException;
import compiler.interpreter.Environment;
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


    public Object evalAddition(Environment env){

        Object leftValue = children[0].eval(env);
        if (leftValue instanceof LinkedList && ((LinkedList) leftValue).size() == 1) {
            leftValue = ((LinkedList) leftValue).peek();
        }

        Object rightValue = children[1].eval(env);
        if (rightValue instanceof LinkedList && ((LinkedList) rightValue).size() == 1) {
            rightValue = ((LinkedList) rightValue).peek();
        }

        if (leftValue instanceof Double) {
            if (rightValue instanceof Integer){
                return (Double) leftValue + (Integer) rightValue;
            } else if (rightValue instanceof Double){
                return (Double) leftValue + (Double) rightValue;
            } else if (rightValue instanceof String) {
                return (Double) leftValue + (String) rightValue;
            }
        } else if (leftValue instanceof Integer) {
            if (rightValue instanceof Integer){
                return (Integer) leftValue + (Integer) rightValue;
            } else if (rightValue instanceof Double){
                return (Integer) leftValue + (Double) rightValue;
            } else if (rightValue instanceof String) {
                return (Integer) leftValue + (String) rightValue;
            }
        } else if (leftValue instanceof String) {
            if (rightValue instanceof Integer) {
                return (String) leftValue + (Integer) rightValue;
            } else if (rightValue instanceof Double) {
                return (String) leftValue + (Double) rightValue;
            } else if (rightValue instanceof String) {
                return (String) leftValue + (String) rightValue;
            }
        }

        return null;
    }

    public Object eval(Environment env) {

        if (type == NodeType.OperationAddition) {
            return evalAddition(env);
        }

        return null;
    }
}
