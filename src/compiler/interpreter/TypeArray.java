package compiler.interpreter;

import compiler.RunTimeException;
import compiler.lexer.Lexeme;
import compiler.parser.NodeType;


import java.util.ListIterator;

public class TypeArray extends ReturnType {

    //The internal array of values
    ReturnType[] objects;

    /**
     * Instantiate an array to hold the specified values.
     * @param values the values to place in the array
     * @throws RunTimeException
     */
    public TypeArray(ReturnTypeList values) throws RunTimeException{
        super(new Environment());

        //Get the count of the values returned
        int count = values.size();
        objects = new ReturnType[count];

        //Copy the values into the array
        int i=0;
        ListIterator<ReturnType> valueIterator = values.listIterator();
        while (valueIterator.hasNext()) {
            objects[i] = valueIterator.next();
            i += 1;
        }

        //Create a length member that returns the count
        declareMember("length", new TypeInteger(count));
    }

    /**
     * Instantiate an empty array of the given length.
     * @param count the length of the array
     * @throws RunTimeException
     */
    public TypeArray(int count) throws RunTimeException {
        super(new Environment());

        //Create the array and fill the entries with nulls
        objects = new ReturnType[count];
        for (int i=0;i<objects.length;i++) {
            objects[i] = TypeNull.getInstance();
        }

        //Create a length member that returns the count
        declareMember("length", new TypeInteger(count));

    }

    /**
     * Get the value at the specified index in the array.
     * @param access the lexeme to report an error with
     * @param pos the index in the array
     * @return the value of the index in the array
     * @throws RunTimeException
     */
    public ReturnType getElement(Lexeme access, ReturnTypeList pos) throws RunTimeException{

        //Check for invalid indices
        if (pos.size() == 0)
            throw new RunTimeException(access, "No array index provided");
        if (pos.size() > 1)
            throw new RunTimeException(access, "Multiple array indices provided");
        if (!(pos.getFirst() instanceof TypeInteger))
            throw new RunTimeException(access, "Index not an integer");
        int value = ((TypeInteger) pos.getFirst()).value;
        if (value >= objects.length)
            throw new RunTimeException(access, "Array index out of bounds");
        if (value < 0)
            throw new RunTimeException(access, "Array index cannot be negative");

        return objects[value];
    }

    /**
     * Set the value at the specified index in the array.
     * @param lexeme the lexeme to report an error with
     * @param assignOp the type of assignment to use
     * @param pos the index in the array
     * @param value the value to set the spot to
     * @throws RunTimeException
     */
    public void setElement(Lexeme lexeme, NodeType assignOp, ReturnTypeList pos, ReturnType value) throws RunTimeException {

        //Check for invalid indices
        if (pos.size() == 0)
            throw new RunTimeException(lexeme, "No array index provided");
        if (pos.size() > 1)
            throw new RunTimeException(lexeme, "Multiple array indices provided");
        if (!(pos.getFirst() instanceof TypeInteger))
            throw new RunTimeException(lexeme, "Index not an integer");

        int posVal = ((TypeInteger) pos.getFirst()).value;
        if (posVal >= objects.length)
            throw new RunTimeException(lexeme, "Array index out of bounds");
        if (posVal < 0)
            throw new RunTimeException(lexeme, "Array index cannot be negative");

        //Run the correct assignment operation
        switch (assignOp) {
            case Assignment:
                objects[posVal] = value;
                return;
            case AssignmentAddition:
                objects[posVal] = getElement(lexeme,pos).plus(lexeme,value);
                return;
            case AssignmentSubtraction:
                objects[posVal] = getElement(lexeme,pos).minus(lexeme,value);
                return;
            case AssignmentMultiplication:
                objects[posVal] = getElement(lexeme,pos).times(lexeme,value);
                return;
            case AssignmentDivision:
                objects[posVal] = getElement(lexeme,pos).divides(lexeme,value);
                return;
            default:
                objects[posVal] = getElement(lexeme,pos).modulus(lexeme,value);
        }
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
        if (right instanceof TypeArray)
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
        if (right instanceof TypeArray)
            return new TypeBoolean(!this.equals(right));
        throw invalidOperationException(op,right);
    }

    /**
     * Create a string representation of the array elements enclosed in brackets and delimited by commas.
     * @return the string representation
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i=0;i<objects.length;i++){
            if (i != 0) builder.append(' ');
            builder.append(objects[i].toString());
            if (i != objects.length-1) builder.append(',');
        }
        builder.append(']');
        return builder.toString();
    }
}
