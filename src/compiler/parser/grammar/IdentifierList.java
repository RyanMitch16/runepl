package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.IdentifierListNode;
import compiler.parser.node.IdentifierNode;

/**
 * IdentifierList : IDENTIFIER COMMA IdentifierList
 *                | IDENTIFIER
 */
public class IdentifierList {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return parser.check(LexemeType.IDENTIFIER);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static NodeList match(Parser parser) throws BuildException {

        if (parser.check(LexemeType.IDENTIFIER)) {
            Lexeme pos = parser.getCurrentLexeme();
            Node head = IdentifierNode.createIdentifier(parser.advance());

            if (parser.check(LexemeType.COMMA)) {
                Lexeme comma = parser.advance();
                if (IdentifierList.pending(parser)) {
                    return IdentifierListNode.createIdentifierList(comma, head, IdentifierList.match(parser));
                } else {
                    throw new BuildException(comma, "Expected an identifier");
                }
            }
            return IdentifierListNode.createIdentifierList(pos, head);
        }

        throw new BuildException(parser.getCurrentLexeme(), "Expected an identifier or list of identifiers");
    }
}
