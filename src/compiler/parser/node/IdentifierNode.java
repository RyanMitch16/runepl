package compiler.parser.node;

import compiler.interpreter.Environment;
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

    public String getIdentifier(){
        return lexeme.text;
    }

    public Object eval(Environment env) {
        return env.lookUp(lexeme.text);
    }
}