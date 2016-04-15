package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;

public class TypeBoolean extends ReturnType{

    //The internal value of the boolean
    public final Boolean value;

    /**
     * Instantiate a new return type to hold a boolean.
     * @param value the value of the boolean read from the lexer
     */
    public TypeBoolean(Lexeme value){
        super(new Environment());
        this.value = (value.type == LexemeType.TRUE);
    }

    /**
     * Instantiate a new return type to hold a boolean.
     * @param value the value of the boolean
     */
    public TypeBoolean(Boolean value){
        super(new Environment());
        this.value = value;
    }

    /**
     * Add the two values together.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return the sum of the two numbers or the concatenation of a string is the right operand
     * @throws RunTimeException
     */
    public ReturnType plus(Lexeme op, ReturnType right) throws RunTimeException{
        if (right instanceof TypeString)
            return new TypeString(value + ((TypeString) right).value);
        throw invalidOperationException(op,right);
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
        if (right instanceof TypeNull)
            return new TypeBoolean(false);
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
        if (right instanceof TypeNull)
            return new TypeBoolean(true);
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

    /**
     * Return a string representation of the boolean.
     * @return the string representation
     */
    public String toString() {
        return value.toString();
    }
}
