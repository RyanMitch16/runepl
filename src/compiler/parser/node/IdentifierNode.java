package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class IdentifierNode extends Node {

    /**
     * @param type
     * @param lexeme
     * @param nodes
     */
    private IdentifierNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static IdentifierNode createIdentifier(Lexeme lexeme) {
        return new IdentifierNode(NodeType.Identifier, lexeme);
    }

    public Lexeme getIdentifierName(){
        return lexeme;
    }

    public ReturnTypeList eval(Environment env) throws RunTimeException {
        return new ReturnTypeList(env.lookUp(lexeme));
    }
}