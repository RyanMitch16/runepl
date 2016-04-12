package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;

public class TypeDouble extends ReturnType {

    public final Double value;

    /**
     * Instantiate a new return type to hold a double.
     * @param value the value of the double read from the lexer
     */
    public TypeDouble(String value){
        super(new Environment());
        this.value = new Double(value);
    }

    /**
     * Instantiate a new return type to hold a double.
     * @param value the value of the double
     */
    public TypeDouble(Double value){
        super(new Environment());
        this.value = value;
    }

    /**
     * Divide the this value by the right operand.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return the sum of the two numbers or the concatenation of a string is the right operand
     * @throws RunTimeException
     */
    public ReturnType divides(Lexeme op, ReturnType right) throws RunTimeException{
        if (right instanceof TypeDouble)
            return new TypeDouble(value / ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeDouble(value / ((TypeInteger) right).value);
        throw invalidOperationException(op,right);
    }

    /**
     * Return if the two double values are equal.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether the two values are equal or not
     * @throws RunTimeException
     */
    public TypeBoolean equalEquals(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeDouble)
            return new TypeBoolean(((double) value) == ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeBoolean(value == (double) ((TypeInteger) right).value);
        if (right instanceof TypeNull)
            return new TypeBoolean(false);
        throw invalidOperationException(op,right);
    }

    /**
     * Return if this value is greater than the right operand value.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether this value is greater or not
     * @throws RunTimeException
     */
    public ReturnType greaterThan(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeDouble)
            return new TypeBoolean(((double) value) > ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeBoolean(value > (double) ((TypeInteger) right).value);
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
        if (right instanceof TypeDouble)
            return new TypeBoolean(((double) value) >= ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeBoolean(value >= (double) ((TypeInteger) right).value);
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
        if (right instanceof TypeDouble)
            return new TypeBoolean(((double) value) < ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeBoolean(value < (double) ((TypeInteger) right).value);
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
        if (right instanceof TypeDouble)
            return new TypeBoolean(((double) value) <= ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeBoolean(value <= (double) ((TypeInteger) right).value);
        throw invalidOperationException(op,right);
    }

    /**
     * Subtract the right operand from this value.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return the difference of the two numbers
     * @throws RunTimeException
     */
    public ReturnType minus(Lexeme op, ReturnType right) throws RunTimeException{
        if (right instanceof TypeDouble)
            return new TypeDouble(value - ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeDouble(value - ((TypeInteger) right).value);
        throw invalidOperationException(op,right);
    }

    /**
     * Mod the left value by the right value.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return the modulus of the two numbers
     * @throws RunTimeException
     */
    public ReturnType modulus(Lexeme op, ReturnType right) throws RunTimeException{
        if (right instanceof TypeDouble)
            return new TypeDouble(value % ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeDouble(value % ((TypeInteger) right).value);
        throw invalidOperationException(op,right);
    }

    /**
     * Return the negation the decimal value.
     * @param op the operator lexeme
     * @return the negation the decimal value
     * @throws RunTimeException
     */
    public ReturnType negate(Lexeme op) throws RunTimeException{
        return new TypeDouble(-value);
    }

    /**
     * Return if the two double values are not equal.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether the two values are not equal or they are
     * @throws RunTimeException
     */
    public TypeBoolean notEquals(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeDouble)
            return new TypeBoolean(((double) value) != ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeBoolean(value != (double) ((TypeInteger) right).value);
        if (right instanceof TypeNull)
            return new TypeBoolean(true);
        throw invalidOperationException(op, right);
    }

    /**
     * Add the two values together.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return the sum of the two numbers or the concatenation of a string is the right operand
     * @throws RunTimeException
     */
    public ReturnType plus(Lexeme op, ReturnType right) throws RunTimeException{
        if (right instanceof TypeDouble)
            return new TypeDouble(value + ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeDouble(value + ((TypeInteger) right).value);
        if (right instanceof TypeString)
            return new TypeString(value + ((TypeString) right).value);
        throw invalidOperationException(op,right);
    }

    /**
     * Multiply the two values together.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return the product of the two numbers
     * @throws RunTimeException
     */
    public ReturnType times(Lexeme op, ReturnType right) throws RunTimeException{
        if (right instanceof TypeDouble)
            return new TypeDouble(value * ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeDouble(value * ((TypeInteger) right).value);
        throw invalidOperationException(op,right);
    }


    public String toString() {
        return value.toString();
    }
}