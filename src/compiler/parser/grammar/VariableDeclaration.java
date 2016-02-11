package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.VariableDeclarationNode;

public class VariableDeclaration {

    public static boolean pending(Parser parser){
        return parser.check(LexemeType.VAR);
    }

    public static Node match(Parser parser) throws BuildException {

        Lexeme var = parser.match(LexemeType.VAR);
        if (IdentifierList.pending(parser)) {
            Node variables = IdentifierList.match(parser);

            if (parser.check(LexemeType.EQUALS)) {
                Lexeme equals = parser.advance();

                if (ExpressionList.pending(parser)) {
                    return VariableDeclarationNode.createVariableDeclaration(var, variables, ExpressionList.match(parser));
                } else {
                    throw new BuildException(equals.beginPos, equals.beginLine, "Expected an expression or list of expressions");
                }
            }

            return VariableDeclarationNode.createVariableDeclaration(var, variables);
        }

        throw new BuildException(var.beginPos, var.beginLine, "Expected an variable declaration");
    }
}
