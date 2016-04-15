package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.*;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class AccessElementNode extends Node {

    /**
     * Instantiate a node to represent the parsed expression.
     * @param type the type of expression
     * @param lexeme the lexeme to report errors with
     * @param nodes the children of this node
     */
    private AccessElementNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    /**
     * Create a node that represents the parsed element accessing expression.
     * @param dot the lexeme to report errors with
     * @param expression the expression to set the the element of
     * @param index the index to access
     * @return the node that represents the expression
     */
    public static AccessElementNode createElementAccessor(Lexeme dot, Node expression, Node index) {
        return new AccessElementNode(NodeType.AccessElement, dot, expression, index);
    }

    /**
     * Set the value of the element at the index.
     * @param env the environment to
     * @param type the type of assignment
     * @param value the value to set the element to
     * @throws RunTimeException
     */
    public void set(Environment env, NodeType type, ReturnType value) throws RunTimeException{

        ReturnTypeList expressionValue = children[0].eval(env);
        if (expressionValue.size() == 0)
            throw new RunTimeException(lexeme, "Unable to set a member of a null reference");
        if (expressionValue.size() > 1)
            throw new RunTimeException(lexeme, "Unable to set a member of multiple reference");

        expressionValue.getFirst().setElement(children[1].lexeme, type, children[1].eval(env), value);
    }

    /**
     * Evaluate the expression under the environment.
     * @param env the environment to evaluate the
     * @return the list of expression returned from the evaluated expression
     * @throws RunTimeException
     */
    public ReturnTypeList eval(Environment env) throws RunTimeException {

        ReturnTypeList expressionValue = children[0].eval(env);
        if (expressionValue.size() == 0) {
            throw new RunTimeException(children[0].lexeme, "Unable to access a element of a null reference");
        }
        if (expressionValue.size() > 1) {
            throw new RunTimeException(children[0].lexeme, "Unable to access a element of multiple reference");
        }
        return new ReturnTypeList(expressionValue.getFirst().getElement(lexeme,children[1].eval(env)));
    }
}