package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;
import compiler.parser.NodeType;

public class TypeNull extends ReturnType {

    //Crate a singleton null object to represent nulls in the language since all nulls are the same
    private static final TypeNull instance = new TypeNull();

    /**
     * Get the singleton null instance.
     */
    private TypeNull(){
        super(null);
    }

    public ReturnType getMember(Lexeme lexeme) throws RunTimeException{
       throw new RunTimeException(lexeme, "Unable to get a member of a null");
    }

    public void setMember(Lexeme lexeme, NodeType opType, ReturnType value) throws RunTimeException{
        throw new RunTimeException(lexeme, "Unable to set a member of a null");
    }

    /**
     * Get the static type null instance.
     * @return the null return type
     */
    public static TypeNull getInstance(){
        return instance;
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
            return new TypeString("null" + ((TypeString) right).value);
        throw invalidOperationException(op,right);
    }

    /**
     * Check if the other value is also equal to null.
     * @param op the equality lexeme
     * @param right the other object to check equality to
     * @return whether the other value is also equal to null or not
     * @throws RunTimeException
     */
    public TypeBoolean equalEquals(Lexeme op, ReturnType right) throws RunTimeException {
        return new TypeBoolean(right instanceof TypeNull);
    }

    /**
     * Check if the other value is not equal to null.
     * @param op the equality lexeme
     * @param right the other object to check equality to
     * @return whether the other value is not equal to null or not
     * @throws RunTimeException
     */
    public ReturnType notEquals(Lexeme op, ReturnType right) throws RunTimeException {
        return new TypeBoolean(!(right instanceof TypeNull));
    }

    public String toString() {
        return "null";
    }
}
