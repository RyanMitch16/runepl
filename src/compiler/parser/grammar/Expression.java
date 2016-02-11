package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;

public class Expression {

    public static boolean pending(Parser parser){
        return AnonFunctionDeclaration.pending(parser) || Expression5.pending(parser);
    }

    public static Node match(Parser parser) throws BuildException {

        if (AnonFunctionDeclaration.pending(parser)) {
            return AnonFunctionDeclaration.match(parser);
        }

        if  (Expression5.pending(parser)) {
            return Expression5.match(parser);
        }

        Lexeme lexeme = parser.getCurrentLexeme();
        throw new BuildException(lexeme.beginPos, lexeme.beginLine, "Expected an expression");

    }
}
