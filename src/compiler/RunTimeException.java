package compiler;

import compiler.lexer.Lexeme;

public class RunTimeException extends Exception {

    public RunTimeException(Lexeme lexeme, String msg){
        super("Run time error at ("+lexeme.beginLine+":"+lexeme.beginPos+"): " +msg);
    }
}
