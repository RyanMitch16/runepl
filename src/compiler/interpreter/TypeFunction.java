package compiler.interpreter;


import compiler.RunTimeException;
import compiler.lexer.Lexeme;
import compiler.parser.node.AnonFunctionNode;

import java.util.LinkedList;

public class TypeFunction extends ReturnType {

    public Environment declarationEnv;
    public AnonFunctionNode node;

    public TypeFunction(Environment declarationEnv, AnonFunctionNode node){
        this.declarationEnv = declarationEnv;
        this.node = node;
    }

    public ReturnTypeList call(ReturnTypeList arguments) throws RunTimeException{

        //Get the function parameters
        LinkedList<String> parameters = node.getParameters();

        if (parameters.size() != arguments.size())
            throw new RunTimeException(node.lexeme,"Parameter count ("+parameters.size()+") does not equal " +
                    "argument count ("+arguments.size()+")");

        Environment newExecutionEnv = declarationEnv.extend(parameters, arguments);

        return  node.getBody().eval(newExecutionEnv);
    }

    public ReturnType plus(Lexeme op, ReturnType value) {
        return null;
    }

    public String toString() {
        return node.getBody().toString();
    }
}