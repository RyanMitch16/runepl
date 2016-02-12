package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.parser.Node;

/**
 * OptIdentifierList : IdentifierList
 *                   | epsilon
 */
public class OptIdentifierList {

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        if (IdentifierList.pending(parser)) {
            return IdentifierList.match(parser);
        }
        return null;
    }
}
