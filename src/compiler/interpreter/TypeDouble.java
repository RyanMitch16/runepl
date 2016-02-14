package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;

public class TypeDouble extends ReturnType {

    public final Double value;

    /**
     * Instantiate a new return type to hold a double.
     * @param value the value of the double read from the lexer
     */
    public TypeDouble(String value){
        this.value = new Double(value);
        //insert("toString", )
    }

    /**
     * Instantiate a new return type to hold a double.
     * @param value the value of the double
     */
    public TypeDouble(Double value){
        this.value = value;
    }

    /**
     *
     * @param op
     * @param right the right value of the operation
     * @return
     * @throws RunTimeException
     */
    public ReturnType plus(Lexeme op, ReturnType right) throws RunTimeException{
        if (right instanceof TypeDouble)
            return new TypeDouble(value + ((TypeDouble) right).value);
        if (right instanceof TypeInteger)
            return new TypeDouble(value + ((TypeInteger) right).value);
        if (right instanceof TypeString)
            return new TypeString(value + ((TypeString) right).value);
        throw new RunTimeException(op, "Unable to "+op.text+" "+getClass().getName()+" and "+right.getClass().getName());
    }

    public String toString() {
        return value.toString();
    }
}