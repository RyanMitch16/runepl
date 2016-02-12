package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.ReturnNode;

/**
 * Statement : RETURN ExpressionList
 *           | VariableDeclarationStatement
 *           | AnonFunctionDeclaration
 *           | ExpressionList
 *           | ExpressionList EQUALS ExpressionList
 *           | ExpressionList PLUS_EQUALS ExpressionList
 *           | ExpressionList MINUS_EQUALS ExpressionList
 *           | ExpressionList DIVIDES_EQUALS ExpressionList
 *           | ExpressionList TIMES_EQUALS ExpressionList
 *           | ExpressionList MODULUS_EQUALS ExpressionList
 *           | RETURN ExpressionList
 */
public class Statement {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser) {
        return parser.check(LexemeType.RETURN) || VariableDeclaration.pending(parser) || AnonFunctionDeclaration.pending(parser) || Expression.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        if (parser.check(LexemeType.RETURN)) {
            Lexeme returnLexeme = parser.advance();
            if (ExpressionList.pending(parser)) {
                return ReturnNode.createReturnStatement(returnLexeme, ExpressionList.match(parser));
            }
            return ReturnNode.createReturnStatement(returnLexeme);
        }

        if (VariableDeclaration.pending(parser)) {
            return VariableDeclaration.match(parser);
        }

        if (AnonFunctionDeclaration.pending(parser)) {
            return AnonFunctionDeclaration.match(parser);
        }

        if (ExpressionList.pending(parser)) {
            Node expressions = ExpressionList.match(parser);
            //TODO: Add assignment and check that the expressions are all identifiers
            return expressions;
        }

        throw new BuildException(parser.getCurrentLexeme(), "Expected a statement");
    }

}
