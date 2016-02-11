package compiler.parser.node;

import compiler.interpreter.Environment;
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

    public Object eval(Environment env){

        LinkedList<String> identifiers = (LinkedList<String>) children[0].eval(env);
        LinkedList<Object> expressions = (children.length == 2) ? (LinkedList<Object>) children[1].eval(env) : null;

        ListIterator<String> identifierIterator = identifiers.listIterator();
        ListIterator<Object> expressionIterator = expressions.listIterator();

        while (identifierIterator.hasNext()) {
            String identifier = identifierIterator.next();

            if (expressionIterator.hasNext()) {

                env.insert(identifier, expressionIterator.next());
            } else {
                env.insert(identifier, null);
            }
        }

        return null;
    }
}
