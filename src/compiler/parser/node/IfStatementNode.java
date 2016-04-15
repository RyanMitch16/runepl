package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.interpreter.TypeBoolean;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.NodeType;

public class IfStatementNode extends Node{

    /**
     * Instantiate a node to represent the parsed expression.
     * @param type the type of expression
     * @param lexeme the lexeme to report errors with
     * @param nodes the children of this node
     */
    private IfStatementNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static IfStatementNode createIfStatement(Lexeme iff, Node expression, NodeList statementList) {
        return new IfStatementNode(NodeType.IfStatement, iff, expression, statementList);
    }

    public static IfStatementNode createIfStatement(Lexeme iff, Node expression, NodeList statementList, Node elseStatement) {
        return new IfStatementNode(NodeType.IfStatement, iff, expression, statementList, elseStatement);
    }

    /**
     * Evaluate the expression under the environment.
     * @param env the environment to evaluate the
     * @return the list of expression returned from the evaluated expression
     * @throws RunTimeException
     */
    public ReturnTypeList eval(Environment env) throws RunTimeException {

        ReturnTypeList expressionValue = children[0].eval(env);

        if (!(expressionValue.getFirst() instanceof TypeBoolean))
            throw new RunTimeException(lexeme, "Evaluation expression not a boolean value");

        if (((TypeBoolean) expressionValue.getFirst()).value) {
            return children[1].eval(env.extend());
        } else if (children.length == 3) {
            return children[2].eval(env.extend());
        }

        return null;
    }

}
