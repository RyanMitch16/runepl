package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.AnonFunctionNode;
import compiler.parser.node.StatementListNode;

/**
 * AnonFunctionDeclaration : FUNC PAREN_LEFT OptIdentifierList PAREN_RIGHT COLON Statement
 *                         | FUNC PAREN_LEFT OptIdentifierList PAREN_RIGHT COLON PAREN_LEFT NEW_LINE TAB_INC StatementList TAB_DEC PAREN_RIGHT
 */
public class AnonFunctionDeclaration {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return parser.check(LexemeType.FUNC);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        Lexeme func = parser.match(LexemeType.FUNC);
        parser.match(LexemeType.PAREN_LEFT);
        Node parameters = OptIdentifierList.match(parser);
        parser.match(LexemeType.PAREN_RIGHT);
        parser.match(LexemeType.COLON);

        if (parser.check(LexemeType.PAREN_LEFT)) {
            parser.advance();
            parser.match(LexemeType.LINE_NEW);
            parser.match(LexemeType.TAB_INC);

            Node body = StatementList.match(parser);
            parser.match(LexemeType.TAB_DEC);
            parser.match(LexemeType.PAREN_RIGHT);

            if (parameters == null) {
                return AnonFunctionNode.createAnonFunction(func, body);
            } else {
                return AnonFunctionNode.createAnonFunction(func, parameters, body);
            }

        }


        Lexeme lexeme = parser.getCurrentLexeme();
        Node body = StatementListNode.createStatementList(lexeme, Statement.match(parser));
        if (parameters == null) {
            return AnonFunctionNode.createAnonFunction(func, body);
        } else {
            return AnonFunctionNode.createAnonFunction(func, parameters, body);
        }

    }
}
