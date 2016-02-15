package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;

public class TypeObject extends ReturnType {

    public final Environment value;

    /**
     * Instantiate a new object with a pointer to the object environment.
     * @param env the environment which represents the object
     */
    public TypeObject(Environment env){
        value = env;
    }

    public Environment getEnv(){
        return value;
    }

    /**
     * Check if the two pointers represent the same object.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether the pointers are the same or not
     * @throws RunTimeException
     */
    public TypeBoolean equalEquals(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeObject)
            return new TypeBoolean((value.equals(((TypeObject) right).value)));
        throw invalidOperationException(op,right);
    }

    /**
     * Check if the two pointers do not represent the same object.
     * @param op the operator lexeme
     * @param right the right operand of the operation
     * @return whether the pointers are not the same or they are
     * @throws RunTimeException
     */
    public TypeBoolean notEquals(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeObject)
            return new TypeBoolean((!value.equals(((TypeObject) right).value)));
        throw invalidOperationException(op,right);
    }
}