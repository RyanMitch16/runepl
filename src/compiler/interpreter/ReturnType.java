package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;

public abstract class ReturnType extends Environment{


    public Object value;

    /**
     * Run the plus operation with this as the left operand and value as the right operand, and return a new object.
     * @param op
     * @param right the right value of the operation
     * @return the result of the operation
     */
    public abstract ReturnType plus(Lexeme op, ReturnType right) throws RunTimeException;

}
