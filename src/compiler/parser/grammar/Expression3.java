package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.OperatorUnaryNode;

/**
 * Expression3 : MINUS Expression3
 *             | NOT Expression3
 *             | Expression2
 */
public class Expression3 {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return parser.check(LexemeType.MINUS, LexemeType.NOT) || Expression2.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        if (parser.check(LexemeType.MINUS, LexemeType.NOT)) {
            Lexeme op = parser.advance();

            Node expression = Expression3.match(parser);

            if (op.type == LexemeType.MINUS){
                return OperatorUnaryNode.createOperationNegation(op, expression);}
            return OperatorUnaryNode.createOperationInversion(op, expression);
        }

        return Expression2.match(parser);

    }
}
