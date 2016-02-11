package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.IdentifierListNode;
import compiler.parser.node.IdentifierNode;

public class IdentifierList {

    public static boolean pending(Parser parser){
        return parser.check(LexemeType.IDENTIFIER);
    }

    public static NodeList match(Parser parser) throws BuildException {

        if (parser.check(LexemeType.IDENTIFIER)) {
            Lexeme pos = parser.getCurrentLexeme();
            Node head = IdentifierNode.createIdentifier(parser.advance());

            if (parser.check(LexemeType.COMMA)) {
                Lexeme comma = parser.advance();
                if (IdentifierList.pending(parser)) {
                    return IdentifierListNode.createIdentifierList(comma, head, IdentifierList.match(parser));
                } else {
                    throw new BuildException(comma.beginPos, comma.beginLine, "Expected an identifier");
                }
            }
            return IdentifierListNode.createIdentifierList(pos, head);
        }

        Lexeme lexeme = parser.getCurrentLexeme();
        throw new BuildException(lexeme.beginPos, lexeme.beginLine, "Expected an identifier or list of identifier");
    }
}
