package compiler.interpreter;


import compiler.RunTimeException;
import compiler.lexer.Lexeme;
import compiler.parser.node.AnonFunctionNode;

import java.util.LinkedList;

public class TypeFunction extends ReturnType {

    //The environment the function was declared in
    public Environment declarationEnv;

    //The node which holds the parameters and body of the function
    public AnonFunctionNode node;

    /**
     * Instantiate a function object.
     * @param declarationEnv environment the function was declared in
     * @param node the node which holds the parameters and body of the function
     */
    public TypeFunction(Environment declarationEnv, AnonFunctionNode node){
        this.declarationEnv = declarationEnv;
        this.node = node;
    }

    /**
     * Call the function with the provided arguments.
     * @param lexeme the function call lexeme to be used when displaying an error
     * @param arguments the arguments passed to the function call
     * @return the value of the function
     * @throws RunTimeException
     */
    public ReturnTypeList call(Lexeme lexeme, ReturnTypeList arguments) throws RunTimeException{

        //Get the function parameters
        LinkedList<Lexeme> parameters = node.getParameters();
        if (parameters.size() != arguments.size())
            throw new RunTimeException(lexeme,"Parameter count ("+parameters.size()+") does not equal " +
                    "argument count ("+arguments.size()+")");

        Environment newExecutionEnv = declarationEnv.extend(parameters, arguments);
        return  node.getBody().eval(newExecutionEnv);
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