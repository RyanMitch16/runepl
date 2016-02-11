package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.node.AnonFunctionNode;

public class AnonFunctionDeclaration {

    public static boolean pending(Parser parser){
        return parser.check(LexemeType.FUNC);
    }

    public static Node match(Parser parser) throws BuildException {

        Lexeme func = parser.match(LexemeType.FUNC);
        parser.match(LexemeType.PAREN_LEFT);
        Node parameters = OptIdentifierList.match(parser);
        parser.match(LexemeType.PAREN_RIGHT);
        parser.match(LexemeType.COLON);

        if (parser.check(LexemeType.PAREN_LEFT)) {
            parser.advance();
            parser.match(LexemeType.LINE_NEW);
            Lexeme tab = parser.match(LexemeType.TAB_INC);

            if (StatementList.pending(parser)) {
                Node body = StatementList.match(parser);
                parser.match(LexemeType.TAB_DEC);
                parser.match(LexemeType.PAREN_RIGHT);

                if (parameters == null) {
                    return AnonFunctionNode.createAnonFunction(func, body);
                } else {
                    return AnonFunctionNode.createAnonFunction(func, parameters, body);
                }
            }
            throw new BuildException(tab.beginLine, tab.beginPos, "Expected a statement");
        }

        if (Statement.pending(parser)) {
            Node body = Statement.match(parser);
            if (parameters == null) {
                return AnonFunctionNode.createAnonFunction(func, body);
            } else {
                return AnonFunctionNode.createAnonFunction(func, parameters, body);
            }
        }

        throw new BuildException(func.beginLine, func.beginPos, "Expected an anonymous function declaration");
    }
}
