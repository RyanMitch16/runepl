package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;

public class TypeNull extends ReturnType {

    private static TypeNull instance = new TypeNull();

    private TypeNull(){
        super(null);
    }

    /**
     * Get the static type null instance.
     * @return the null return type
     */
    public static TypeNull getInstance(){
        return instance;
    }


    public TypeBoolean equalEquals(Lexeme op, ReturnType right) throws RunTimeException {
        return new TypeBoolean(right instanceof TypeNull);
    }


    public ReturnType notEquals(Lexeme op, ReturnType right) throws RunTimeException {
        return new TypeBoolean(!(right instanceof TypeNull));
    }

    public String toString() {
        return "null";
    }
}
