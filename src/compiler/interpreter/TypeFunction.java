package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;
import compiler.parser.node.AnonFunctionNode;

import java.util.LinkedList;

public class TypeFunction extends ReturnType {

    //The environment the function was declared in
    public Environment closureEnv;

    //The node which holds the parameters and body of the function
    public AnonFunctionNode node;

    /**
     * Instantiate a function object.
     * @param closureEnv environment the function was declared in
     * @param node the node which holds the parameters and body of the function
     */
    public TypeFunction(Environment closureEnv, AnonFunctionNode node){
        super(new Environment());
        this.closureEnv = closureEnv;
        this.node = node;
    }

    /**
     * Call the function with the provided arguments.
     * @param pt the function call lexeme to be used when displaying an error
     * @param arguments the arguments passed to the function call
     * @return the value of the function
     * @throws RunTimeException
     */
    public ReturnTypeList call(Lexeme pt, ReturnTypeList arguments) throws RunTimeException{

        //Get the function parameters
        LinkedList<Lexeme> parameters = node.getParameters();
        if (parameters.size() != arguments.size())
            throw new RunTimeException(pt,"Parameter count ("+parameters.size()+") does not equal " +
                    "argument count ("+arguments.size()+")");

        Environment newExecutionEnv = closureEnv.extend(parameters, arguments);
        return node.getBody().eval(newExecutionEnv);
    }

    /**
     * Check if the values are equal (pointer equality for now)
     * @param op the operator lexeme
     * @param right the value to check equality to
     * @return whether the objects are equal or not
     * @throws RunTimeException
     */
    public ReturnType equalEquals(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeNull)
            return new TypeBoolean(false);
        if (right instanceof TypeFunction)
            return new TypeBoolean(this.equals(right));
        throw invalidOperationException(op, right);
    }

    /**
     * Check if the values are not equal (pointer equality for now)
     * @param op the operator lexeme
     * @param right the value to check equality to
     * @return whether the objects are not equal or not
     * @throws RunTimeException
     */
    public ReturnType notEquals(Lexeme op, ReturnType right) throws RunTimeException {
        if (right instanceof TypeNull)
            return new TypeBoolean(true);
        if (right instanceof TypeFunction)
            return new TypeBoolean(!this.equals(right));
        throw invalidOperationException(op,right);
    }

    /**
     * Get the string representation of the object for printing to the console.
     * @return the string representation of the object
     */
    public String toString() {
        if (node == null)
            return "Built In Function";
        return node.getBody().toString();
    }
}