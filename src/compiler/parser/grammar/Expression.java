package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;

/**
 * Expression : AnonFunctionDeclaration
 *            | Expression7
 */
public class Expression {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return AnonFunctionDeclaration.pending(parser) || Expression7.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        if (AnonFunctionDeclaration.pending(parser)) {
            return AnonFunctionDeclaration.match(parser);
        }

        if  (Expression9.pending(parser)) {
            return Expression9.match(parser);
        }
        throw new BuildException(parser.getCurrentLexeme(), "Expected an expression");
    }
}
