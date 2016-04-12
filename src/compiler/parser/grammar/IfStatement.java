package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.IfStatementNode;
import compiler.parser.node.StatementListNode;

/**
 * IfStatement : IF Expression COLON Statement
 *             | IF Expression COLON LINE_NEW TAB_INC StatementList LINE_NEW TAB_DEC OptElseIfStatement
 *             |
 */
public class IfStatement {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return parser.check(LexemeType.IF);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        Lexeme iff = parser.match(LexemeType.IF);
        Node expression = Expression.match(parser);
        parser.match(LexemeType.COLON);

        if (parser.check(LexemeType.LINE_NEW)) {
            parser.advance();
            parser.match(LexemeType.TAB_INC);

            NodeList statements = StatementList.match(parser);
            parser.match(LexemeType.TAB_DEC);

            if (parser.check(LexemeType.ELSE)) {
                parser.advance();

                if (IfStatement.pending(parser)) {
                    return IfStatementNode.createIfStatement(iff, expression, statements, IfStatement.match(parser));
                } else {

                    parser.match(LexemeType.COLON);
                    parser.match(LexemeType.LINE_NEW);
                    parser.match(LexemeType.TAB_INC);

                    NodeList elseStatements = StatementList.match(parser);
                    parser.match(LexemeType.TAB_DEC);

                    return IfStatementNode.createIfStatement(iff, expression, statements, elseStatements);
                }
            }

            return IfStatementNode.createIfStatement(iff, expression, statements);
        }

        NodeList body =  StatementListNode.createStatementList(parser.getCurrentLexeme(), Statement.match(parser));
        return IfStatementNode.createIfStatement(iff, expression, body);
    }
}
