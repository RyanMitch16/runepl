package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.OperatorBinaryNode;

/**
 * Expression8 : Expression8 || Expression7
 *             | Expression7
 */
public class Expression8 {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return Expression7.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        Node head = Expression7.match(parser);

        while (parser.check(LexemeType.OR)) {
            Lexeme op = parser.advance();
            head = OperatorBinaryNode.createOperationOr(op, head, Expression7.match(parser));
        }

        return head;

    }
}
