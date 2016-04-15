package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;

public class TypeString extends ReturnType{

    public final String value;

    /**
     * Instantiate a new return type to hold a double.
     * @param value the value of the double read from the lexer
     */
    public TypeString(String value){
        super(new Environment());
        this.value = value;
    }

    /**
     * Check if the string content is equal to the right value.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether the values are equal or not
     * @throws RunTimeException
     */
    public TypeBoolean equalEquals(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeString)
            return new TypeBoolean(value.equals(((TypeString) right).value));
        if (right instanceof TypeNull)
            return new TypeBoolean(false);
        throw invalidOperationException(op);
    }

    /**
     * Check if the string content is not equal to the right value.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether the values are not equal or are
     * @throws RunTimeException
     */
    public TypeBoolean notEquals(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeString)
            return new TypeBoolean(!value.equals(((TypeString) right).value));
        if (right instanceof TypeNull)
            return new TypeBoolean(true);
        throw invalidOperationException(op);
    }

    /**
     * Return if this value is greater than the right operand value.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether this value is greater or not
     * @throws RunTimeException
     */
    public ReturnType greaterThan(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeString)
            return new TypeBoolean(value.compareTo(((TypeString) right).value) > 0);
        throw invalidOperationException(op,right);
    }

    /**
     * Return if this value is greater than or equal to the right operand value.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether this value is greater/equal to or not
     * @throws RunTimeException
     */
    public ReturnType greaterThanEqualTo(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeString)
            return new TypeBoolean(value.compareTo(((TypeString) right).value) >= 0);
        throw invalidOperationException(op,right);
    }

    /**
     * Return if this value is less than the right operand value.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether this value is less or not
     * @throws RunTimeException
     */
    public ReturnType lessThan(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeString)
            return new TypeBoolean(value.compareTo(((TypeString) right).value) < 0);
        throw invalidOperationException(op,right);
    }

    /**
     * Return if this value is less than or equal to the right operand value.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether this value is less/equal to or not
     * @throws RunTimeException
     */
    public ReturnType lessThanEqualTo(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeString)
            return new TypeBoolean(value.compareTo(((TypeString) right).value) <= 0);
        throw invalidOperationException(op,right);
    }

    /**
     * Concatenate the values into a string
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return the concatenated string
     * @throws RunTimeException
     */
    public ReturnType plus(Lexeme op, ReturnType right) throws RunTimeException{
        if (right instanceof TypeDouble)
            return new TypeString(value + ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeString(value + ((TypeInteger) right).value);
        if (right instanceof TypeString)
            return new TypeString(value + ((TypeString) right).value);
        if (right instanceof TypeBoolean)
            return new TypeString(value + ((TypeBoolean) right).value);
        if (right instanceof TypeNull)
            return new TypeString(value + "null");
        throw invalidOperationException(op);
    }

    public String toString() {
        return value.toString();
    }
}
