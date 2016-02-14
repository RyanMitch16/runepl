package compiler.interpreter;

import java.util.LinkedList;
import java.util.ListIterator;

public class Environment {

    public LinkedList<String> variables;
    public LinkedList<ReturnType> values;
    
    public Environment nextEnvironment;

    public Environment(){
        variables = new LinkedList<>();
        values = new LinkedList<>();
    }

    /*public Environment(LinkedList<String> variables){
        this.variables = variables;
        values = new LinkedList<>();
    }*/

    public Environment(LinkedList<String> variables, ReturnTypeList values){

        this.variables = new LinkedList<>();
        this.values = new LinkedList<>();

        ListIterator<String> identifierIterator = variables.listIterator();
        ListIterator<ReturnType> expressionIterator = values.listIterator();

        while (identifierIterator.hasNext()) {
            String identifier = identifierIterator.next();

            if (expressionIterator.hasNext()) {

                insert(identifier, expressionIterator.next());
            } else {
                insert(identifier, null);
            }
        }
    }

    public Environment extend(){
        Environment e = new Environment();
        e.nextEnvironment = this;
        return e;
    }

    public Environment extend(LinkedList<String> variables, ReturnTypeList values){
        Environment e = new Environment(variables, values);
        e.nextEnvironment = this;
        return e;
    }

    public ReturnType insert(String variable, ReturnType value){
        variables.push(variable);
        values.push(value);
        return value;
    }

    public void insert(LinkedList<String> variables, ReturnTypeList values){
        ListIterator<String> identifierIterator = variables.listIterator();
        ListIterator<ReturnType> expressionIterator = values.listIterator();

        while (identifierIterator.hasNext()) {
            String identifier = identifierIterator.next();

            if (expressionIterator.hasNext()) {

                insert(identifier, expressionIterator.next());
            } else {
                insert(identifier, null);
            }
        }
    }

    public ReturnType lookUp(String variable){

        Environment env = this;
        while (env != null) {
            ListIterator<String> varIt = env.variables.listIterator();
            ListIterator<ReturnType> valIt = env.values.listIterator();

            while (varIt.hasNext()) {
                String var = varIt.next();
                ReturnType val = valIt.next();
                if (var.equals(variable)) {
                    return val;
                }
            }

            env = env.nextEnvironment;
        }

        return null;
    }

    public void update(String variable, ReturnType value){

        Environment env = this;
        while (env != null) {
            ListIterator<String> varIt = env.variables.listIterator();
            ListIterator<ReturnType> valIt = env.values.listIterator();

            while (valIt.hasNext()) {
                String var = varIt.next();
                valIt.next();
                if (var.equals(variable)) {
                    valIt.set(value);
                    return;
                }
            }

            env = env.nextEnvironment;
        }
    }

}
