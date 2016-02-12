package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.OperatorBinaryNode;

/**
 * Expression4 : Expression4 TIMES Expression3
 *             | Expression4 DIVIDES Expression3
 *             | Expression4 MODULUS Expression3
 *             | Expression3
 */
public class Expression4 {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return Expression3.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        if (Expression3.pending(parser)) {
            Node head = Expression3.match(parser);

            while (parser.check(LexemeType.TIMES, LexemeType.DIVIDES, LexemeType.MODULUS)) {
                Lexeme op = parser.advance();

                if (Expression3.pending(parser)) {
                    Node right = Expression3.match(parser);

                    //Set the head to a new node to create left associativity
                    if (op.type == LexemeType.TIMES) {
                        head = OperatorBinaryNode.createOperationMultiplication(op, head, right);
                    } else if (op.type == LexemeType.DIVIDES) {
                        head = OperatorBinaryNode.createOperationDivision(op, head, right);
                    } else {
                        head = OperatorBinaryNode.createOperationModulus(op, head, right);
                    }
                } else {
                    throw new BuildException(op, "Expected an expression");
                }
            }

            return head;
        }

        throw new BuildException(parser.getCurrentLexeme(), "Expected an expression");
    }
}
