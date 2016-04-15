package compiler;

import compiler.interpreter.*;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;

import java.io.File;
import java.util.ListIterator;

public class Interpreter {

    Node treeRoot;
    Environment env;

    /**
     * Create an interpreter to run the parse tree.
     * @param tree the to evaluate
     */
    public Interpreter(Node tree){
        treeRoot = tree;
        env = new Environment();

        //Add a constructor for an n length array
        env.insertBuiltIn("array", new TypeFunction(env, null) {

            public ReturnTypeList call(Lexeme lexeme, ReturnTypeList arguments) throws RunTimeException {

                if (arguments.size() == 1) {
                    if (arguments.getFirst() instanceof TypeInteger) {
                        if (((TypeInteger) arguments.getFirst()).value < 0) {
                            throw new RunTimeException(lexeme, "Size cannot be negative.");
                        }
                        return new ReturnTypeList(new TypeArray(((TypeInteger) arguments.getFirst()).value));
                    } else if (arguments.getFirst() instanceof TypeDouble) {
                        return new ReturnTypeList(new TypeArray((((TypeDouble) arguments.getFirst()).value).intValue()));
                    }
                    throw new RunTimeException(lexeme, "Size must be a number");
                }
                throw new RunTimeException(lexeme, "Incorrect # of arguments -- fix error message later :P");
            }
        });

        //Add a function to print out the string representations of the arguments provided
        env.insertBuiltIn("print", new TypeFunction(env, null) {

            public ReturnTypeList call(Lexeme lexeme, ReturnTypeList arguments) throws RunTimeException {
                ListIterator<ReturnType> args = arguments.listIterator();
                while (args.hasNext()){
                    System.out.print(args.next().toString());
                }
                return new ReturnTypeList();
            }
        });

        //Add a function to println out the string representations of the arguments provided
        env.insertBuiltIn("println", new TypeFunction(env, null) {

            public ReturnTypeList call(Lexeme lexeme, ReturnTypeList arguments) throws RunTimeException {
                ListIterator<ReturnType> args = arguments.listIterator();
                while (args.hasNext()){
                    System.out.print(args.next().toString());
                }
                System.out.println("");
                return new ReturnTypeList();

            }
        });

        //Add null to the environment
        env.insertBuiltIn("null", TypeNull.getInstance());
    }

    /**
     * Begin interpreting the parse tree
     * @throws RunTimeException
     */
    public void begin() throws RunTimeException{
        ReturnTypeList result = treeRoot.eval(env);
        //System.out.println(result);
    }

    public static void main(String args[]){

        try {
            Lexer lexer = new Lexer(new File(args[0]));
            Lexeme lexeme = null;

            /*while ((lexeme == null) || lexeme.type != LexemeType.EOF) {
                lexeme = lexer.lex();
                System.out.println(lexeme.text+" : "+lexeme.type);
            }
            */

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
