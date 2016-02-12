package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.parser.Node;

/**
 * OptExpressionList : ExpressionList
 *                   | epsilon
 */
public class OptExpressionList {

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        if (ExpressionList.pending(parser)) {
            return ExpressionList.match(parser);
        }

        return null;
    }
}
