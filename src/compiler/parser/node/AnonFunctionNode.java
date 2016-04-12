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
     * @param type
     * @param lexeme
     * @param nodes
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

    public Node getBody(){
        return (children.length == 1)? children[0] : (children[1]);
    }

    public LinkedList<Lexeme> getParameters() throws RunTimeException{
        return (children.length == 1)? new LinkedList<>() : ((IdentifierListNode) children[0]).getIdentifierNames();
    }

    public ReturnTypeList eval(Environment env) {

        return new ReturnTypeList(new TypeFunction(env, this));
    }

}