package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.ExpressionListNode;
import compiler.parser.node.LiteralNode;

public class LiteralArray {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser) {
        return parser.check(LexemeType.BRACKET_LEFT);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        Lexeme bracket = parser.match(LexemeType.BRACKET_LEFT);

        NodeInt elements = matchExpressionList(parser);

        //Make sure the close bracket matches the same indention level as the open bracket
        if (elements.tabCount != 0) {
            throw new BuildException(parser.getCurrentLexeme(),
                    "Close bracket must match indention level of open bracket for a multiline array.");
        }

        parser.match(LexemeType.BRACKET_RIGHT);

        return LiteralNode.createLiteralArray(bracket, elements.node);

    }

    private static class NodeInt {
        public NodeList node;
        public int tabCount;
        public boolean newLine;
        public NodeInt(){
            this.node = null;
            this.tabCount = 0;
            this.newLine = false;
        }
    }

    private static NodeInt matchExpressionList(Parser parser) throws BuildException{

        NodeInt nodeAndCount = new NodeInt();
        nodeAndCount.tabCount += eatTabs(parser);

        Node expression = Expression.match(parser);
        Lexeme start = parser.getCurrentLexeme();
        nodeAndCount.tabCount += eatTabs(parser);

        if (parser.check(LexemeType.COMMA)) {
            Lexeme comma = parser.advance();

            NodeInt next = matchExpressionList(parser);
            nodeAndCount.node = ExpressionListNode.createExpressionList(comma, expression, next.node);
            nodeAndCount.tabCount += next.tabCount;

            return nodeAndCount;
        }

        nodeAndCount.node = ExpressionListNode.createExpressionList(start, expression);
        return nodeAndCount;
    }

    public static int eatTabs(Parser parser) throws BuildException{
        int tabCount = 0;
        while (parser.check(LexemeType.LINE_NEW, LexemeType.TAB_DEC, LexemeType.TAB_INC)) {
            Lexeme lexeme = parser.advance();
            if (lexeme.type == LexemeType.TAB_INC) {
                tabCount += 1;
            } else if (lexeme.type == LexemeType.TAB_DEC) {
                tabCount -= 1;
            }
        }
        return tabCount;
    }

}
