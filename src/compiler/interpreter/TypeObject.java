package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;

public class TypeObject extends ReturnType {

    public final Environment value;

    public TypeObject(Environment env){
        value = env;
    }

    public ReturnType plus(Lexeme op, ReturnType right) throws RunTimeException{
        throw new RunTimeException(op, "Unable to "+op.text+" "+getClass().getName()+" and "+right.getClass().getName());
    }

}