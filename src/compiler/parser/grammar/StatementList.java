package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.StatementListNode;

/**
 * StatementList : Statement LINE_NEW StatementList
 *               | ControlStatement StatementList
 *               | Statement
 *               | ControlStatement
 */
public class StatementList {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return Statement.pending(parser) | ControlStatement.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static NodeList match(Parser parser) throws BuildException {

        Lexeme pos = parser.getCurrentLexeme();
        if (Statement.pending(parser)) {
            Node head = Statement.match(parser);

            if (parser.check(LexemeType.LINE_NEW)) {
                parser.advance();

                if (StatementList.pending(parser)) {
                    return StatementListNode.createStatementList(pos, head, StatementList.match(parser));
                }
            }

            return StatementListNode.createStatementList(pos, head);
        }

        if (ControlStatement.pending(parser)) {
            Node head = ControlStatement.match(parser);

            if (StatementList.pending(parser)) {
                return StatementListNode.createStatementList(pos, head, StatementList.match(parser));
            }

            return StatementListNode.createStatementList(pos, head);
        }

        throw new BuildException(parser.getCurrentLexeme(), "Expected a statement or multiple statements");
    }
}
