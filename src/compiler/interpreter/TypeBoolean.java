package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;

public class TypeBoolean extends ReturnType{

    public final Boolean value;

    /**
     * Instantiate a new return type to hold a boolean.
     * @param value the value of the boolean read from the lexer
     */
    public TypeBoolean(Lexeme value){
        this.value = (value.type == LexemeType.TRUE);
    }

    /**
     * Instantiate a new return type to hold a boolean.
     * @param value the value of the boolean
     */
    public TypeBoolean(Boolean value){
        this.value = value;
    }

    /**
     * Return the and of the two values.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return the and of the two values
     * @throws RunTimeException
     */
    public ReturnType and(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeBoolean)
            return new TypeBoolean(((boolean) value) && ((TypeBoolean) right).value);
        throw invalidOperationException(op, right);
    }

    /**
     * Return if the two boolean values are equal.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether the two values are equal or not
     * @throws RunTimeException
     */
    public TypeBoolean equalEquals(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeBoolean)
            return new TypeBoolean(((boolean) value) == ((TypeBoolean) right).value);
        throw invalidOperationException(op, right);
    }

    /**
     * Return the inverse of the value.
     * @param op the operator lexeme
     * @return  the inverse of the value
     * @throws RunTimeException
     */
    public ReturnType invert(Lexeme op) throws RunTimeException {
        return new TypeBoolean(!value);
    }

    /**
     * Return if the two boolean values are not equal.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether the two values are not equal or they are
     * @throws RunTimeException
     */
    public TypeBoolean notEquals(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeBoolean)
            return new TypeBoolean(((boolean) value) != ((TypeBoolean) right).value);
        throw invalidOperationException(op, right);
    }

    /**
     * Return the or of the two values.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return the or of the two values
     * @throws RunTimeException
     */
    public ReturnType or(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeBoolean)
            return new TypeBoolean(((boolean) value) || ((TypeBoolean) right).value);
        throw invalidOperationException(op, right);
    }

    public String toString() {
        return value.toString();
    }
}
