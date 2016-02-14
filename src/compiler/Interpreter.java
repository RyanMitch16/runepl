package compiler;

import compiler.interpreter.Environment;
import compiler.parser.Node;

import java.io.File;

public class Interpreter {

    Node treeRoot;
    Environment env;

    public Interpreter(Node tree){
        treeRoot = tree;
        env = new Environment();

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
