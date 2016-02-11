package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.parser.Node;

public class OptExpressionList {

    public static Node match(Parser parser) throws BuildException {

        if (ExpressionList.pending(parser)) {
            return ExpressionList.match(parser);
        }

        return null;
    }
}
