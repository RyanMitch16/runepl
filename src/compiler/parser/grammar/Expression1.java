package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.IdentifierNode;
import compiler.parser.node.LiteralNode;

/**
 * Expression1 : PAREN_LEFT Expression PAREN_RIGHT
 *             | PAREN_LEFT AnonFunctionDeclaration PAREN_RIGHT
 *             | LITERAL_INTEGER
 *             | LITERAL_STRING
 *             | LITERAL_DECIMAL
 *             | IDENTIFIER
 */
public class Expression1 {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser){
        return parser.check(LexemeType.PAREN_LEFT, LexemeType.LITERAL_INTEGER, LexemeType.LITERAL_STRING,
                LexemeType.LITERAL_DECIMAL, LexemeType.IDENTIFIER);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
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

        throw new BuildException(parser.getCurrentLexeme(), "Expected an expression");
    }

}
