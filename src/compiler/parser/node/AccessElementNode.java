package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.*;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class AccessElementNode extends Node {

    /**
     * @param type
     * @param lexeme
     * @param nodes
     */
    private AccessElementNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static AccessElementNode createElementAccessor(Lexeme dot, Node expression, Node index) {
        return new AccessElementNode(NodeType.AccessElement, dot, expression, index);
    }

    public void set(Environment env, NodeType type, ReturnType value) throws RunTimeException{

        ReturnTypeList expressionValue = children[0].eval(env);
        if (expressionValue.size() == 0)
            throw new RunTimeException(lexeme, "Unable to set a member of a null reference");
        if (expressionValue.size() > 1)
            throw new RunTimeException(lexeme, "Unable to set a member of multiple reference");

        expressionValue.getFirst().setElement(children[1].lexeme, type, children[1].eval(env), value);

    }

    public ReturnTypeList eval(Environment env) throws RunTimeException {

        ReturnTypeList expressionValue = children[0].eval(env);

        if (expressionValue.size() == 0) {
            throw new RunTimeException(children[0].lexeme, "Unable to access a element of a null reference");
        }

        if (expressionValue.size() > 1) {
            throw new RunTimeException(children[0].lexeme, "Unable to access a element of multiple reference");
        }

        return new ReturnTypeList(expressionValue.getFirst().getElement(lexeme,children[1].eval(env)));
    }
}