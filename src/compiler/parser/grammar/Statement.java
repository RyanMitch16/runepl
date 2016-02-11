package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.ReturnNode;

public class Statement {

    public static boolean pending(Parser parser) {
        return parser.check(LexemeType.RETURN) || VariableDeclaration.pending(parser) || AnonFunctionDeclaration.pending(parser) || Expression.pending(parser);
    }

    public static Node match(Parser parser) throws BuildException {

        if (parser.check(LexemeType.RETURN)) {
            Lexeme returnLexeme = parser.advance();
            if (ExpressionList.pending(parser)) {
                return ReturnNode.createReturnStatement(returnLexeme, ExpressionList.match(parser));
            }
            return ReturnNode.createReturnStatement(returnLexeme);
        }

        if (VariableDeclaration.pending(parser)) {
            return VariableDeclaration.match(parser);
        }

        if (AnonFunctionDeclaration.pending(parser)) {
            return AnonFunctionDeclaration.match(parser);
        }

        if (ExpressionList.pending(parser)) {
            return ExpressionList.match(parser);
        }

        Lexeme lexeme = parser.getCurrentLexeme();
        throw new BuildException(lexeme, "Expected a statement");
    }

}
