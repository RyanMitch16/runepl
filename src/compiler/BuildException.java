package compiler;

import compiler.lexer.Lexeme;

public class BuildException extends Exception {

    public BuildException(int beginLine, int beginPos, String msg){
        super("Build error at ("+beginLine+":"+beginPos+"): " +msg);
    }

    public BuildException(Lexeme lexeme, String msg){
        super("Build error at ("+lexeme.beginLine+":"+lexeme.beginPos+"): " +msg);
    }
}
