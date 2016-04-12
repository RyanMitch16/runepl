package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.StatementListNode;
import compiler.parser.node.WhileStatementNode;

/**
 * IfStatement : WHILE Expression COLON Statement
 *             | WHILE Expression COLON LINE_NEW TAB_INC StatementList LINE_NEW TAB_DEC OptElseIfStatement
 */
public class WhileStatement {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return parser.check(LexemeType.WHILE);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {



        Lexeme whileLexeme = parser.match(LexemeType.WHILE);
        Node expression = Expression.match(parser);
        parser.match(LexemeType.COLON);

        if (parser.check(LexemeType.LINE_NEW)) {
            parser.advance();
            parser.match(LexemeType.TAB_INC);

            NodeList statements = StatementList.match(parser);
            parser.match(LexemeType.TAB_DEC);

            return WhileStatementNode.createWhileStatement(whileLexeme, expression, statements);
        }

        NodeList body =  StatementListNode.createStatementList(parser.getCurrentLexeme(), Statement.match(parser));
        return WhileStatementNode.createWhileStatement(whileLexeme, expression, body);

    }
}
