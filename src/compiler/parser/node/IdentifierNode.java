package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnType;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class IdentifierNode extends Node {

    /**
     * Instantiate a node to represent the parsed expression.
     * @param type the type of expression
     * @param lexeme the lexeme to report errors with
     * @param nodes the children of this node
     */
    private IdentifierNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static IdentifierNode createIdentifier(Lexeme lexeme) {
        return new IdentifierNode(NodeType.Identifier, lexeme);
    }

    /**
     * Get the lexeme of the identifier.
     * @return the lexeme that represents the identifier
     */
    public Lexeme getIdentifierName(){
        return lexeme;
    }

    /**
     * Set the value of the identifier in the enviornemnt
     * @param env the enviornment the identifier is in
     * @param type the type of assignment operation
     * @param value the value to set the variable to
     * @throws RunTimeException
     */
    public void set(Environment env, NodeType type, ReturnType value) throws RunTimeException{
        env.update(lexeme, type, value);
    }

    /**
     * Evaluate the expression under the environment.
     * @param env the environment to evaluate the
     * @return the list of expression returned from the evaluated expression
     * @throws RunTimeException
     */
    public ReturnTypeList eval(Environment env) throws RunTimeException {
        return new ReturnTypeList(env.lookUp(lexeme));
    }

}