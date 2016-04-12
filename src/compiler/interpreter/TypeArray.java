package compiler.interpreter;


import compiler.RunTimeException;
import compiler.lexer.Lexeme;
import compiler.parser.NodeType;


import java.util.ListIterator;

public class TypeArray extends ReturnType {

    ReturnType[] objects;

    public TypeArray(ReturnTypeList values) throws RunTimeException{
        super(new Environment());

        int count = 0;

        ListIterator<ReturnType> valueIterator = values.listIterator();
        while (valueIterator.hasNext()) {
            valueIterator.next();
            count += 1;
        }

        objects = new ReturnType[count];
        int i=0;

        valueIterator = values.listIterator();
        while (valueIterator.hasNext()) {
            objects[i] = valueIterator.next();
            i += 1;
        }

        declareMember("length", new TypeInteger(count));
    }

    public TypeArray(int count) throws RunTimeException {
        super(new Environment());
        objects = new ReturnType[count];
        for (int i=0;i<objects.length;i++) {
            objects[i] = TypeNull.getInstance();
        }
        declareMember("length", new TypeInteger(count));

    }

    public ReturnType getElement(Lexeme access, ReturnTypeList pos) throws RunTimeException{

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


    public void setElement(Lexeme lexeme, NodeType opType, ReturnTypeList pos, ReturnType value) throws RunTimeException {
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

        objects[posVal] = value;
    }

    @Override
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
