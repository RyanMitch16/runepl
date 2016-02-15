package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnType;
import compiler.interpreter.ReturnTypeList;
import compiler.interpreter.TypeBoolean;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.NodeType;

public class IfStatementNode extends Node{

    /**
     * @param type
     * @param lexeme
     * @param nodes
     */
    private IfStatementNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static IfStatementNode createIfStatement(Lexeme iff, Node expression, NodeList statementList) {
        return new IfStatementNode(NodeType.IfStatement, iff, expression, statementList);
    }

    public static IfStatementNode createIfStatement(Lexeme iff, Node expression, Node statement) {
        return new IfStatementNode(NodeType.IfStatement, iff, expression, statement);
    }

    public ReturnTypeList eval(Environment env) throws RunTimeException {

        ReturnTypeList expressionValue = children[0].eval(env);

        if (!(expressionValue.getFirst() instanceof TypeBoolean))
            throw new RunTimeException(lexeme, "Evaluation expression not a boolean value");

        if (((TypeBoolean) expressionValue.getFirst()).value) {

            return children[1].eval(env.extend());
        }

        return null;
    }
}
