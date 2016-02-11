package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.IdentifierNode;
import compiler.parser.node.LiteralNode;

public class Expression1 {

    public static boolean pending(Parser parser){
        return parser.check(LexemeType.PAREN_LEFT, LexemeType.LITERAL_INTEGER, LexemeType.LITERAL_STRING,
                LexemeType.LITERAL_DECIMAL, LexemeType.IDENTIFIER);
    }

    public static Node match(Parser parser) throws BuildException{

        if (parser.check(LexemeType.PAREN_LEFT)){
            parser.match(LexemeType.PAREN_LEFT);

            if (Expression.pending(parser)) {
                Node expression = Expression.match(parser);
                parser.match(LexemeType.PAREN_RIGHT);
                return expression;
            }
        }

        if (parser.check(LexemeType.LITERAL_DECIMAL)){
            return LiteralNode.createLiteralDecimal(parser.advance());
        }

        if (parser.check(LexemeType.LITERAL_INTEGER)){
            return LiteralNode.createLiteralInteger(parser.advance());
        }

        if (parser.check(LexemeType.LITERAL_STRING)){
            return LiteralNode.createLiteralString(parser.advance());
        }

        if (parser.check(LexemeType.IDENTIFIER)){
            return IdentifierNode.createIdentifier(parser.advance());
        }

        Lexeme lexeme = parser.getCurrentLexeme();
        throw new BuildException(lexeme.beginPos, lexeme.beginLine, "Expected an expression");
    }

}
