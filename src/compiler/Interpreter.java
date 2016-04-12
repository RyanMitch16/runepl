package compiler;

import compiler.interpreter.*;
import compiler.lexer.Lexeme;
import compiler.parser.Node;

import java.io.File;

public class Interpreter {

    Node treeRoot;
    Environment env;

    public Interpreter(Node tree){
        treeRoot = tree;
        env = new Environment();

        //TODO: Add default functions

        env.insertBuiltIn("array", new TypeFunction(env, null) {

            public ReturnTypeList call(Lexeme lexeme, ReturnTypeList arguments) throws RunTimeException {

                if (arguments.size() == 1) {

                    if (arguments.getFirst() instanceof TypeInteger) {
                        //TODO: negative sizes
                        return new ReturnTypeList(new TypeArray(((TypeInteger) arguments.getFirst()).value));
                    } else if (arguments.getFirst() instanceof TypeDouble) {
                        return new ReturnTypeList(new TypeArray((((TypeDouble) arguments.getFirst()).value).intValue()));
                    }

                    throw new RunTimeException(lexeme, "Size must be a number");
                }

                throw new RunTimeException(lexeme, "Incorrect # of arguments -- fix error message later :P");
            }
        });

        env.insertBuiltIn("print", new TypeFunction(env, null) {

            public ReturnTypeList call(Lexeme lexeme, ReturnTypeList arguments) throws RunTimeException {
                if (arguments.size() == 1) {
                    System.out.println(arguments.getFirst().toString());
                    return new ReturnTypeList();
                }

                throw new RunTimeException(lexeme, "Incorrect # of arguments -- fix error message later :P");
            }
        });

        env.insertBuiltIn("null", TypeNull.getInstance());
    }

    public void begin() throws RunTimeException{
        ReturnTypeList result = treeRoot.eval(env);
        System.out.println(result);
    }

    public static void main(String args[]){

        try {
            Lexer lexer = new Lexer(new File(args[0]));
            Parser parser = new Parser(lexer);
            Node tree = parser.parse();
            //System.out.println(tree);

            Interpreter interpreter = new Interpreter(tree);
            interpreter.begin();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
