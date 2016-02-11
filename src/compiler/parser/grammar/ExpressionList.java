package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.ExpressionListNode;

public class ExpressionList {

    public static boolean pending(Parser parser){
        return Expression.pending(parser);
    }

    public static NodeList match(Parser parser) throws BuildException {

        if (Expression.pending(parser)) {
            Lexeme pos = parser.getCurrentLexeme();
            Node head = Expression.match(parser);

            if (parser.check(LexemeType.COMMA)) {
                Lexeme comma = parser.advance();
                //TODO CHECK PENDING
                return ExpressionListNode.createExpressionList(comma, head, ExpressionList.match(parser));
            }

            return ExpressionListNode.createExpressionList(pos, head);
        }

        Lexeme lexeme = parser.getCurrentLexeme();
        throw new BuildException(lexeme.beginPos, lexeme.beginLine, "Expected a expression or list of expressions");
    }
}
