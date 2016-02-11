package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.OperatorBinaryNode;

public class Expression5 {

    public static boolean pending(Parser parser){
        return Expression4.pending(parser);
    }

    public static Node match(Parser parser) throws BuildException {


        if (Expression4.pending(parser)) {

            Node head = Expression4.match(parser);

            while (parser.check(LexemeType.PLUS, LexemeType.MINUS)) {
                Lexeme op = parser.advance();

                if (Expression4.pending(parser)) {
                    Node right = Expression4.match(parser);

                    //Set the head to a new node to create left associativity
                    if (op.type == LexemeType.PLUS) {
                        head = OperatorBinaryNode.createOperationAddition(op, head, right);
                    } else {
                        head = OperatorBinaryNode.createOperationSubtraction(op, head, right);
                    }
                } else {
                    Lexeme lexeme = parser.getCurrentLexeme();
                    throw new BuildException(lexeme.beginPos, lexeme.beginLine, "Expected an expression");
                }
            }

            return head;
        }

        Lexeme lexeme = parser.getCurrentLexeme();
        throw new BuildException(lexeme.beginPos, lexeme.beginLine, "Expected an expression");
    }

}
