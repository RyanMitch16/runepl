package compiler;

import compiler.lexer.Lexeme;

public class BuildException extends Exception {

    /**
     * Instantiate an exception generated from building the project.
     * @param beginLine the line where the exception occurred
     * @param beginPos the pos where the exception occurred
     * @param msg the exception message
     */
    public BuildException(int beginLine, int beginPos, String msg){
        super("Build error at ("+beginLine+":"+beginPos+"): " +msg);
    }

    /**
     * Instantiate an exception generated from building the project.
     * @param lexeme the lexeme at which the build exception occurred
     * @param msg the exception message
     */
    public BuildException(Lexeme lexeme, String msg){
        super("Build error at ("+lexeme.beginLine+":"+lexeme.beginPos+"): " +msg);
    }
}
