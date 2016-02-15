package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.parser.Node;

/**
 * ControlStatement : IfStatement
 */
public class ControlStatement {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser) {
        return IfStatement.pending(parser) || WhileStatement.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {
        if (IfStatement.pending(parser))
            return IfStatement.match(parser);

        return WhileStatement.match(parser);
    }

}
