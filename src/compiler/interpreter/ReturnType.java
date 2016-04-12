package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.NodeType;

public abstract class ReturnType{

    public final Environment env;

    public ReturnType(Environment env){
        this.env = env;
    }

    public ReturnType getMember(Lexeme lexeme) throws RunTimeException{
        return env.lookUp(lexeme);
    }

    public void declareMember(String var, ReturnType value) throws RunTimeException{
        env.insertBuiltIn(var, value);
    }

    public void setMember(Lexeme lexeme, NodeType opType, ReturnType value) throws RunTimeException{
        env.update(lexeme, opType, value);
    }

    public void setElement(Lexeme lexeme, NodeType opType, ReturnTypeList pos, ReturnType value) throws RunTimeException{
        throw new RunTimeException(lexeme, "Unable to set element an element of a "+getClass().getSimpleName());
    }

    public ReturnType getElement(Lexeme lexeme, ReturnTypeList pos) throws RunTimeException{
        throw new RunTimeException(lexeme, "Unable to retrieve element an element of a "+getClass().getSimpleName());
    }

    public ReturnType and(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType divides(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType equalEquals(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType greaterThan(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType greaterThanEqualTo(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType invert(Lexeme op) throws RunTimeException{
        throw invalidOperationException(op);
    }

    public ReturnType lessThan(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType lessThanEqualTo(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType minus(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType modulus(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType negate(Lexeme op) throws RunTimeException{
        throw invalidOperationException(op);
    }

    public ReturnType notEquals(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType or(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType plus(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    public ReturnType times(Lexeme op, ReturnType right) throws RunTimeException{
        throw invalidOperationException(op, right);
    }

    /**
     *  Get a run time exception that states the specified binary operation is not a valid operation.
     * @param op the binary operation
     * @param right the right operand of the binary operation
     * @return the run time exception
     */
    public RunTimeException invalidOperationException(Lexeme op, ReturnType right){
        return new RunTimeException(op, "Unable to "+op.type+" "+
                getClass().getSimpleName()+" and "+right.getClass().getSimpleName());
    }

    /**
     * Get a run time exception that states the specified unary operation is not a valid operation.
     * @param op the unary operation
     * @return the run time exception
     */
    public RunTimeException invalidOperationException(Lexeme op){
        return new RunTimeException(op, "Unable to "+op.type+" "+getClass().getSimpleName());
    }

}
