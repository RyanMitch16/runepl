package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnType;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.NodeType;
import compiler.parser.grammar.IdentifierList;

import java.util.LinkedList;
import java.util.ListIterator;

public class VariableDeclarationNode extends Node{

    /**
     * @param type
     * @param lexeme
     * @param nodes
     */
    private VariableDeclarationNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static VariableDeclarationNode createVariableDeclaration(Lexeme var, Node identifiers, Node expression) {
        return new VariableDeclarationNode(NodeType.VariableDeclaration, var, identifiers, expression);
    }

    public static VariableDeclarationNode createVariableDeclaration(Lexeme var, Node identifiers) {
        return new VariableDeclarationNode(NodeType.VariableDeclaration, var, identifiers);
    }

    public ReturnTypeList eval(Environment env) throws RunTimeException{

        LinkedList<Lexeme> identifiers = ((IdentifierListNode) children[0]).getIdentifierNames();
        ReturnTypeList expressions = (children.length == 2) ? children[1].eval(env) : new ReturnTypeList();

        env.insert(identifiers, expressions);

        return null;
    }
}
