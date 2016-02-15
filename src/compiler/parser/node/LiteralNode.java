package compiler.parser.node;

import compiler.interpreter.*;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class LiteralNode extends Node{

    private ReturnType value;

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
        node.value = new TypeDouble(lexeme.text);
        return node;
    }

    public static LiteralNode createLiteralInteger(Lexeme lexeme){
        LiteralNode node = new LiteralNode(NodeType.LiteralInteger, lexeme);
        node.value = new TypeInteger(lexeme.text);
        return node;
    }

    public static LiteralNode createLiteralString(Lexeme lexeme){
        LiteralNode node = new LiteralNode(NodeType.LiteralString, lexeme);
        node.value = new TypeString(lexeme.text);
        return node;
    }

    public static LiteralNode createLiteralBoolean(Lexeme lexeme){
        LiteralNode node = new LiteralNode(NodeType.LiteralBoolean, lexeme);
        node.value = new TypeBoolean(lexeme);
        return node;
    }

    public ReturnTypeList eval(Environment env) {
        return new ReturnTypeList(value);
    }

}
