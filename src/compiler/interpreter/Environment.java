package compiler.interpreter;

import java.util.LinkedList;
import java.util.ListIterator;

public class Environment {

    private LinkedList<String> vars;
    private LinkedList<Object> vals;
    
    public Environment nextEnvironment;

    public Environment(){
        vars = new LinkedList<>();
        vals = new LinkedList<>();
        insert("this",this);            //TODO: MAKE A RESERVED LITERAL IN LEXER -- TEMPORARY SOLUTION
    }

    /*public Environment(LinkedList<String> vars){
        this.vars = vars;
        vals = new LinkedList<>();
    }*/

    public Environment(LinkedList<String> vars, LinkedList<Object> vals){
        this.vars = vars;
        this.vals = vals;
        insert("this",this);            //TODO: MAKE A RESERVED LITERAL IN LEXER -- TEMPORARY SOLUTION
    }

    public Environment extend(){
        Environment e = new Environment();
        e.nextEnvironment = this;
        return e;
    }

    public Environment extend(LinkedList<String> vars, LinkedList<Object> vals){
        Environment e = new Environment(vars, vals);
        e.nextEnvironment = this;
        return e;
    }

    public Object insert(String variable, Object value){
        vars.push(variable);
        vals.push(value);
        return value;
    }

    public Object lookUp(String variable){

        Environment env = this;
        while (env != null) {
            ListIterator<String> varIt = env.vars.listIterator();
            ListIterator<Object> valIt = env.vals.listIterator();

            while (varIt.hasNext()) {
                String var = varIt.next();
                Object val = valIt.next();
                if (var.equals(variable)) {
                    return val;
                }
            }

            env = env.nextEnvironment;
        }

        return null;
    }

    public void update(String variable, Object value){

        Environment env = this;
        while (env != null) {
            ListIterator<String> varIt = env.vars.listIterator();
            ListIterator<Object> valIt = env.vals.listIterator();

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
