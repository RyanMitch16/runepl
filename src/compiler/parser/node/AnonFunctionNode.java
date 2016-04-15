package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.interpreter.TypeFunction;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

import java.util.LinkedList;

public class AnonFunctionNode extends Node {

    /**
     * Instantiate a node to represent the parsed expression.
     * @param type the type of expression
     * @param lexeme the lexeme to report errors with
     * @param nodes the children of this node
     */
    private AnonFunctionNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static AnonFunctionNode createAnonFunction(Lexeme func, Node body) {
        return new AnonFunctionNode(NodeType.AnonFunction, func, body);
    }

    public static AnonFunctionNode createAnonFunction(Lexeme func, Node parameters, Node body) {
        return new AnonFunctionNode(NodeType.AnonFunction, func, parameters, body);
    }

    /**
     * Get the body of the function.
     * @return the node body of the function.
     */
    public Node getBody(){
        return (children.length == 1)? children[0] : (children[1]);
    }

    /**
     * Get the parameters of the of the function.
     * @return  the list of lexemes of the parameters
     * @throws RunTimeException
     */
    public LinkedList<Lexeme> getParameters() throws RunTimeException{
        return (children.length == 1)? new LinkedList<>() : ((IdentifierListNode) children[0]).getIdentifierNames();
    }

    /**
     * Evaluate the expression under the environment.
     * @param env the environment to evaluate the
     * @return the list of expression returned from the evaluated expression
     * @throws RunTimeException
     */
    public ReturnTypeList eval(Environment env) {

        return new ReturnTypeList(new TypeFunction(env, this));
    }

}