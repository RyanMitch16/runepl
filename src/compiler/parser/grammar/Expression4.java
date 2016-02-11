package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.OperatorBinaryNode;

public class Expression4 {

    public static boolean pending(Parser parser){
        return Expression3.pending(parser);
    }

    public static Node match(Parser parser) throws BuildException {


        if (Expression4.pending(parser)) {
            Node head = Expression3.match(parser);

            while (parser.check(LexemeType.TIMES, LexemeType.DIVIDES, LexemeType.MODULUS)) {
                Lexeme op = parser.advance();

                if (Expression3.pending(parser)) {
                    Node right = Expression3.match(parser);

                    //Set the head to a new node to create left associativity
                    if (op.type == LexemeType.TIMES) {
                        head = OperatorBinaryNode.createOperationMultiplication(op, head, right);
                    } else if (op.type == LexemeType.DIVIDES) {
                        head = OperatorBinaryNode.createOperationDivision(op, head, right);
                    } else {
                        head = OperatorBinaryNode.createOperationModulus(op, head, right);
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
