package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;
import compiler.parser.NodeType;

import java.util.LinkedList;
import java.util.ListIterator;

public class Environment {

    //The list of variables in the environment
    private LinkedList<String> variables;

    //The list of values of the variables in the environment
    private LinkedList<ReturnType> values;

    //The parent environment this environment came from
    private Environment parentEnvironment;

    /**
     * Initialize an empty environment.
     */
    public Environment(){
        variables = new LinkedList<>();
        values = new LinkedList<>();
        insertBuiltIn("this", new TypeObject(this));
    }

    /**
     * Initialize an environment with the default variables and values. If the variable list length exceeds the value
     * list length, then the excess variables will be set to null. If the values list exceeds the variable list, the
     * excess variables will not be added to the environment.
     * @param variables the list of variables to add to the environment
     * @param values the list of values of the variables
     */
    public Environment(LinkedList<Lexeme> variables, ReturnTypeList values){

        this.variables = new LinkedList<>();
        this.values = new LinkedList<>();
        insertBuiltIn("this", new TypeObject(this));

        ListIterator<Lexeme> identifierIterator = variables.listIterator();
        ListIterator<ReturnType> expressionIterator = values.listIterator();

        while (identifierIterator.hasNext()) {
            Lexeme identifier = identifierIterator.next();
            if (expressionIterator.hasNext()) {
                insert(identifier, expressionIterator.next());
            } else {
                insert(identifier, TypeNull.getInstance());
            }
        }
    }

    /**
     * Extend the environment by creating a sub-environment of this environment.
     * @return the sub-environment
     */
    public Environment extend(){
        Environment e = new Environment();
        e.parentEnvironment = this;
        return e;
    }

    /**
     * Extend the environment by creating a sub-environment of this environment with the specified variables and values.
     * If the variable list length exceeds the value list length, then the excess variables will be set to null.
     * If the values list exceeds the variable list, the excess variables will not be added to the environment.
     * @param variables the list of variables to add to the environment
     * @param values the list of values of the variables
     * @return the sub-environment
     */
    public Environment extend(LinkedList<Lexeme> variables, ReturnTypeList values){
        Environment e = new Environment(variables, values);
        e.parentEnvironment = this;
        return e;
    }

    /**
     * Add the variable with the specified value to the environment.
     * @param variable the list of variables to add to the environment
     * @param value the list of values of the variables
     */
    public void insert(Lexeme variable, ReturnType value){

        //TODO:Check if already declared
        variables.push(variable.text);
        values.push(value);
    }

    public void insertBuiltIn(String variable, ReturnType value) {
        variables.push(variable);
        values.push(value);
    }

    /**
     * Add the variables with the specified values to the environment.
     * If the variable list length exceeds the value list length, then the excess variables will be set to null.
     * If the values list exceeds the variable list, the excess variables will not be added to the environment.
     * @param variables the list of variables to add to the environment
     * @param values the list of values of the variables
     */
    public void insert(LinkedList<Lexeme> variables, ReturnTypeList values){
        ListIterator<Lexeme> identifierIterator = variables.listIterator();
        ListIterator<ReturnType> expressionIterator = values.listIterator();

        while (identifierIterator.hasNext()) {
            Lexeme identifier = identifierIterator.next();
            if (expressionIterator.hasNext()) {
                insert(identifier, expressionIterator.next());
            } else {
                insert(identifier, TypeNull.getInstance());
            }
        }
    }

    /**
     * Look for the value of the variable in this and parent environment.
     * @param variable the variable name to look up
     * @return the value of the most local variable
     */
    public ReturnType lookUp(Lexeme variable) throws RunTimeException{

        Environment env = this;
        while (env != null) {
            ListIterator<String> varIt = env.variables.listIterator();
            ListIterator<ReturnType> valIt = env.values.listIterator();

            while (varIt.hasNext()) {
                String var = varIt.next();
                ReturnType val = valIt.next();
                if (var.equals(variable.text)) {
                    return val;
                }
            }

            env = env.parentEnvironment;
        }

        throw new RunTimeException(variable, "Uninitialized variable "+variable.text);
    }

    /**
     * Update the value of the most local variable with this name.
     * @param variable the variable name to look up
     * @param value the value to set the variable to
     */
    public ReturnType update(Lexeme variable, NodeType assignOp, ReturnType value) throws RunTimeException{

        Environment env = this;
        while (env != null) {
            ListIterator<String> varIt = env.variables.listIterator();
            ListIterator<ReturnType> valIt = env.values.listIterator();

            while (valIt.hasNext()) {
                String var = varIt.next();
                ReturnType currentVal = valIt.next();
                if (var.equals(variable.text)) {
                    switch (assignOp) {
                        case Assignment:
                            valIt.set(value);
                            return value;
                        case AssignmentAddition:
                            valIt.set(currentVal.plus(variable, value));
                            return value;
                        case AssignmentSubtraction:
                            valIt.set(currentVal.minus(variable, value));
                            return value;
                        case AssignmentMultiplication:
                            valIt.set(currentVal.times(variable, value));
                            return value;
                        case AssignmentDivision:
                            valIt.set(currentVal.divides(variable, value));
                            return value;
                        case AssignmentModulus:
                            valIt.set(currentVal.modulus(variable, value));
                            return value;
                    }
                }
            }

            env = env.parentEnvironment;
        }

        throw new RunTimeException(variable, "Uninitialized variable "+variable.text);
    }

}
