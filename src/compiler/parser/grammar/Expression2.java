package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.AccessElementNode;
import compiler.parser.node.AccessMemberNode;
import compiler.parser.node.FunctionCallNode;
import compiler.parser.node.IdentifierNode;

/**
 * Expression2 : Expression2 DOT IDENTIFIER
 *             | Expression2 BRACKET_LEFT Expression BRACKET_BRACKET
 *             | Expression2 PAREN_LEFT OptExpressionList PAREN_RIGHT
 *             | Expression1
 */
public class Expression2 {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return Expression1.pending(parser);
    }


    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
    public static Node match(Parser parser) throws BuildException {

        if (Expression1.pending(parser)) {
            Node head = Expression1.match(parser);

            while (parser.check(LexemeType.DOT, LexemeType.BRACKET_LEFT, LexemeType.PAREN_LEFT)) {
                Lexeme op = parser.advance();

                if (op.type == LexemeType.DOT) {
                    if (parser.check(LexemeType.IDENTIFIER)) {
                        head = AccessMemberNode.createMemberAccessor(op, head, IdentifierNode.createIdentifier(parser.advance()));
                    } else {
                        throw new BuildException(op, "Missing identifier to be accessed");
                    }
                } else if (op.type == LexemeType.BRACKET_LEFT) {
                    if (Expression5.pending(parser)) {
                        Node expression = Expression.match(parser);
                        parser.match(LexemeType.BRACKET_RIGHT);
                        head = AccessElementNode.createElementAccessor(op, head, expression);
                    } else {
                        throw new BuildException(op, "Missing expression in element accessor");
                    }
                } else {
                    Node expressionList = OptExpressionList.match(parser);
                    parser.match(LexemeType.PAREN_RIGHT);
                    if (expressionList == null) {
                        head = FunctionCallNode.createFunctionCall(op,head);
                    } else {
                        head = FunctionCallNode.createFunctionCall(op,head,expressionList);
                    }
                }
            }

            return head;
        }

        throw new BuildException(parser.getCurrentLexeme(), "Expected an expression");
    }
}
