package compiler.parser.node;

import compiler.interpreter.Environment;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class LiteralNode extends Node{

    private Object value;

    /**
     *
     * @param type
     * @param lexeme
     * @param nodes
     */
    private LiteralNode(NodeType type, Lexeme lexeme, Node... nodes){
        super(type, lexeme, nodes);
    }

    public static LiteralNode createLiteralDecimal(Lexeme lexeme){
        LiteralNode node = new LiteralNode(NodeType.LiteralDecimal, lexeme);
        node.value = new Double(lexeme.text);
        return node;
    }

    public static LiteralNode createLiteralInteger(Lexeme lexeme){
        LiteralNode node = new LiteralNode(NodeType.LiteralInteger, lexeme);
        node.value = new Integer(lexeme.text);
        return node;
    }

    public static LiteralNode createLiteralString(Lexeme lexeme){
        LiteralNode node = new LiteralNode(NodeType.LiteralString, lexeme);
        node.value = lexeme.text;
        return node;
    }

    public Object eval(Environment env) {
        return value;
    }

}
