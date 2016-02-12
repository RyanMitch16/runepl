package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.VariableDeclarationNode;

/**
 * VariableDeclaration : VAR IdentifierList EQUALS ExpressionList
 *                     | VAR IdentifierList
 */
public class VariableDeclaration {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return parser.check(LexemeType.VAR);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        Lexeme var = parser.match(LexemeType.VAR);
        if (IdentifierList.pending(parser)) {
            Node variables = IdentifierList.match(parser);

            if (parser.check(LexemeType.EQUALS)) {
                Lexeme equals = parser.advance();

                if (ExpressionList.pending(parser)) {
                    return VariableDeclarationNode.createVariableDeclaration(var, variables, ExpressionList.match(parser));
                } else {
                    throw new BuildException(equals, "Expected an expression or list of expressions");
                }
            }

            return VariableDeclarationNode.createVariableDeclaration(var, variables);
        }

        throw new BuildException(var, "Expected an variable declaration");
    }
}
