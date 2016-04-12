package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.ExpressionListNode;

/**
 * ExpressionList : Expression COMMA ExpressionList
 *                | Expression
 */
public class ExpressionList {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return Expression.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static NodeList match(Parser parser) throws BuildException {

        Lexeme pos = parser.getCurrentLexeme();
        Node head = Expression.match(parser);

        if (parser.check(LexemeType.COMMA)) {
            Lexeme comma = parser.advance();
            return ExpressionListNode.createExpressionList(comma, head, ExpressionList.match(parser));
        }

        return ExpressionListNode.createExpressionList(pos, head);

    }
}
