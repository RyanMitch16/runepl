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
        throw invalidOperationException(op);
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
        throw invalidOperationException(op);
    }

    public String toString() {
        return value.toString();
    }
}
