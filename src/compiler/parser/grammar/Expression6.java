package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.OperatorBinaryNode;

/**
 * Expression6 : Expression5 LESS_THAN Expression5
 *             | Expression5 LESS_THAN_EQUAL Expression5
 *             | Expression5 GREATER_THAN Expression5
 *             | Expression5 GREATER_THAN_EQUAL_TO Expression5
 *             | Expression5
 */
public class Expression6 {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return Expression5.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        Node left = Expression5.match(parser);

        if (parser.check(LexemeType.GREATER_THAN)) {
            Lexeme op = parser.advance();
            return OperatorBinaryNode.createGreaterThan(op, left, Expression5.match(parser));
        }

        if (parser.check(LexemeType.GREATER_THAN_EQUAL)) {
            Lexeme op = parser.advance();
            return OperatorBinaryNode.createGreaterThanEqual(op, left, Expression5.match(parser));
        }

        if (parser.check(LexemeType.LESS_THAN)) {
            Lexeme op = parser.advance();
            return OperatorBinaryNode.createLessThan(op, left, Expression5.match(parser));
        }

        if (parser.check(LexemeType.LESS_THAN_EQUAL)) {
            Lexeme op = parser.advance();
            return OperatorBinaryNode.createLessThanEqual(op, left, Expression5.match(parser));
        }

        return left;

    }
}
