package compiler.interpreter;

public abstract class ReturnType {

    /**
     * Run the plus operation with this as the left operand and value as the right operand, and return a new object.
     * @param value the left value of the  operation
     * @return the result of the operation
     */
    public abstract ReturnType plus(ReturnType value);
}
