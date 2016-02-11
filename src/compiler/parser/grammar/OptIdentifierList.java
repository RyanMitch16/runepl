package compiler.parser.grammar;

import compiler.BuildException;
import compiler.Parser;
import compiler.parser.Node;

public class OptIdentifierList {

    public static Node match(Parser parser) throws BuildException {

        if (IdentifierList.pending(parser)) {
            return IdentifierList.match(parser);
        }

        return null;
    }
}
