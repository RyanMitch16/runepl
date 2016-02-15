package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.interpreter.TypeBoolean;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.NodeType;

public class WhileStatementNode extends Node{

    /**
     * @param type
     * @param lexeme
     * @param nodes
     */
    private WhileStatementNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static WhileStatementNode createWhileStatement(Lexeme whileLexeme, Node expression, NodeList statementList) {
        return new WhileStatementNode(NodeType.WhileStatement, whileLexeme, expression, statementList);
    }

    public static WhileStatementNode createWhileStatement(Lexeme whileLexeme, Node expression, Node statement) {
        return new WhileStatementNode(NodeType.WhileStatement, whileLexeme, expression, statement);
    }

    public ReturnTypeList eval(Environment env) throws RunTimeException {

        while (true) {

            ReturnTypeList expressionValue = children[0].eval(env);

            if (!(expressionValue.getFirst() instanceof TypeBoolean))
                throw new RunTimeException(lexeme, "Evaluation expression not a boolean value");

            if (((TypeBoolean) expressionValue.getFirst()).value) {

                ReturnTypeList value = children[1].eval(env.extend());
                if (value.size() != 0) {
                    //TODO: Add break and continue statements as SpecialReturnTypes
                    return value;
                }
            } else {
                return null;
            }

        }
    }
}
