package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.ExpressionListNode;
import compiler.parser.node.StatementListNode;

public class StatementList {

    public static boolean pending(Parser parser){
        return Statement.pending(parser);
    }

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

        Lexeme lexeme = parser.getCurrentLexeme();
        throw new BuildException(lexeme.beginPos, lexeme.beginLine, "Expected a statement or multiple statements");
    }
}
