package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.OperatorUnaryNode;

public class Expression3 {

    public static boolean pending(Parser parser){
        return parser.check(LexemeType.MINUS, LexemeType.NOT) || Expression2.pending(parser);
    }

    public static Node match(Parser parser) throws BuildException {


        if (parser.check(LexemeType.MINUS, LexemeType.NOT)) {
            Lexeme op = parser.advance();

            Node expression = Expression3.match(parser);

            if (op.type == LexemeType.MINUS){
                return OperatorUnaryNode.createOperationNegation(op, expression);}
            return OperatorUnaryNode.createOperationInversion(op, expression);
        }

        if (Expression2.pending(parser)) {
            return Expression2.match(parser);
        }

        Lexeme lexeme = parser.getCurrentLexeme();
        throw new BuildException(lexeme.beginPos, lexeme.beginLine, "Expected an expression");
    }
}
