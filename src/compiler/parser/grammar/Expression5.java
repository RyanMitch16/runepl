package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.OperatorBinaryNode;

/**
 * Expression5 : Expression5 PLUS Expression4
 *             | Expression5 MINUS Expression4
 *             | Expression4
 */
public class Expression5 {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return Expression4.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        if (Expression4.pending(parser)) {

            Node head = Expression4.match(parser);

            while (parser.check(LexemeType.PLUS, LexemeType.MINUS)) {
                Lexeme op = parser.advance();

                if (Expression4.pending(parser)) {
                    Node right = Expression4.match(parser);

                    //Set the head to a new node to create left associativity
                    if (op.type == LexemeType.PLUS) {
                        head = OperatorBinaryNode.createOperationAddition(op, head, right);
                    } else {
                        head = OperatorBinaryNode.createOperationSubtraction(op, head, right);
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
