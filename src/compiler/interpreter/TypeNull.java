package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;

public class TypeNull extends ReturnType {

    @Override
    public TypeBoolean equalEquals(Lexeme op, ReturnType right) throws RunTimeException {
        return null;
    }

    @Override
    public ReturnType plus(Lexeme op, ReturnType right) throws RunTimeException {
        return null;
    }
}
