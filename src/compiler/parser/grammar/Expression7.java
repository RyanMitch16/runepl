package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.OperatorBinaryNode;

/**
 * Expression7 : Expression7 == Expression6
 *             | Expression7 != Expression6
 *             | Expression6
 */
public class Expression7 {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return Expression6.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        if (Expression6.pending(parser)) {
            Node left = Expression6.match(parser);

            if (parser.check(LexemeType.EQUALS_EQUALS)) {
                Lexeme op = parser.advance();
                return OperatorBinaryNode.createOperationEquality(op, left, Expression6.match(parser));
            }

            if (parser.check(LexemeType.NOT_EQUAL)) {
                Lexeme op = parser.advance();
                return OperatorBinaryNode.createOperationInverseEquality(op, left, Expression6.match(parser));
            }

            return left;
        }

        throw new BuildException(parser.getCurrentLexeme(), "Expected an expression");
    }
}
