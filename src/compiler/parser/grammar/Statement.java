package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.node.*;

/**
 * Statement : RETURN ExpressionList
 *           | VariableDeclarationStatement
 *           | AnonFunctionDeclaration
 *           | ExpressionList
 *           | ExpressionList EQUALS ExpressionList
 *           | ExpressionList PLUS_EQUALS ExpressionList
 *           | ExpressionList MINUS_EQUALS ExpressionList
 *           | ExpressionList DIVIDES_EQUALS ExpressionList
 *           | ExpressionList TIMES_EQUALS ExpressionList
 */
public class Statement {

    /**
     * Check if the grammar is pending.
     * @param parser the parser supplying the lexemes from the lexer
     * @return whether the grammar is pending
     */
    public static boolean pending(Parser parser) {
        return parser.check(LexemeType.RETURN) || VariableDeclaration.pending(parser) || AnonFunctionDeclaration.pending(parser) || Expression.pending(parser);
    }

    /**
     * Attempt to match the grammar to the lexeme stream.
     * @param parser the parser supplying the lexemes from the lexer
     * @return the node matched from the grammar
     * @throws BuildException
     */
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

        NodeList expressions = ExpressionList.match(parser);

        //Check if the statement is an assignment statement
        if (parser.check(LexemeType.EQUALS, LexemeType.PLUS_EQUALS, LexemeType.MINUS_EQUALS,
                LexemeType.DIVIDES_EQUALS, LexemeType.MODULUS_EQUALS, LexemeType.TIMES_EQUALS)) {
            Lexeme op = parser.advance();

            if (!checkOnlyIdentifiers(expressions))
                throw new BuildException(op, "Left side of assignment can only contain identifiers");

            //Convert the expression list to an identifier list
            expressions = changeToIdentifierList(expressions);

            NodeList values = ExpressionList.match(parser);

            return AssignmentNode.createAssignmentNode(op,expressions, values);
        }

        return expressions;
    }


    public static boolean checkOnlyIdentifiers(Node expression) {
        if (expression instanceof ExpressionListNode ||expression instanceof IdentifierNode ||
                expression instanceof AccessMemberNode || expression instanceof AccessElementNode) {
            for (Node child : expression.children) {
                if (!(expression instanceof AccessElementNode) && !checkOnlyIdentifiers(child))
                    return false;
            }
            return true;
        }
        return false;
    }

    public static NodeList changeToIdentifierList(Node identifiers){

        if (identifiers.children.length == 2)
            return IdentifierListNode.createIdentifierList(identifiers.lexeme, identifiers.children[0],
                    changeToIdentifierList(identifiers.children[1]));
        return IdentifierListNode.createIdentifierList(identifiers.lexeme, identifiers.children[0]);

    }
}
