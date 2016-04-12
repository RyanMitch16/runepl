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

        Node head = Expression6.match(parser);

        while (parser.check(LexemeType.EQUALS_EQUALS, LexemeType.NOT_EQUAL)) {
            Lexeme op = parser.advance();

            //Set the head to a new node to create left associativity
            if (op.type == LexemeType.EQUALS_EQUALS) {
                head = OperatorBinaryNode.createOperationEquality(op, head, Expression6.match(parser));
            } else {
                head = OperatorBinaryNode.createOperationInverseEquality(op, head, Expression6.match(parser));
            }

        }

        return head;

    }
}
