package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.StatementListNode;

/**
 * StatementList : Statement NEW_LINE StatementList
 *               | Statement
 */
public class StatementList {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return Statement.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static NodeList match(Parser parser) throws BuildException {

        if (Statement.pending(parser)) {
            Lexeme pos = parser.getCurrentLexeme();
            Node head = Statement.match(parser);

            if (parser.check(LexemeType.LINE_NEW)) {
                Lexeme newLine = parser.advance();

                if (StatementList.pending(parser)) {
                    return StatementListNode.createStatementList(newLine, head, StatementList.match(parser));
                }
            }

            return StatementListNode.createStatementList(pos, head);
        }

        throw new BuildException(parser.getCurrentLexeme(), "Expected a statement or multiple statements");
    }
}
