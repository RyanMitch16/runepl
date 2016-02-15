package compiler;

import compiler.interpreter.Environment;
import compiler.interpreter.ReturnTypeList;
import compiler.interpreter.TypeFunction;
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

        env.insertBuiltIn("print",new TypeFunction(env, null) {

            public ReturnTypeList call(Lexeme lexeme, ReturnTypeList arguments) throws RunTimeException {
                if (arguments.size() == 1) {
                    System.out.println(arguments.getFirst().toString());
                    return new ReturnTypeList();
                }

                throw new RunTimeException(lexeme, "Incorrect # of arguments -- fix error message later :P");
            }
        });
    }

    public void begin() throws RunTimeException{
        System.out.println(treeRoot.eval(env));
    }

    public static void main(String args[]){

        try {
            Lexer lexer = new Lexer(new File("test.txt"));
            Parser parser = new Parser(lexer);
            Node tree = parser.parse();

            Interpreter interpreter = new Interpreter(tree);
            interpreter.begin();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
