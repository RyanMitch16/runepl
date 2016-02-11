package compiler.parser.node;

import compiler.interpreter.Environment;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;
import compiler.parser.grammar.IdentifierList;

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

    public LinkedList<String> getParameters(){
        return (children.length == 1)? new LinkedList<>() : ((IdentifierListNode) children[0]).eval(null);
    }

    public Environment env = null;

    public Object eval(Environment env) {
        if (this.env == null){
            this.env = env;
        }
        return this;
    }

}