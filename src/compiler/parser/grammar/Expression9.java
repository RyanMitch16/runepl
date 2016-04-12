package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.OperatorBinaryNode;

/**
 * Expression8 : Expression9 && Expression8
 *             | Expression8
 */
public class Expression9 {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return Expression8.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        Node head = Expression8.match(parser);

        while (parser.check(LexemeType.AND)) {
            Lexeme op = parser.advance();
            head = OperatorBinaryNode.createOperationAnd(op, head, Expression7.match(parser));
        }

        return head;
    }
}
