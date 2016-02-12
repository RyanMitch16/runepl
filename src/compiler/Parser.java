package compiler;

import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.grammar.Expression;
import compiler.parser.grammar.Expression5;
import compiler.parser.grammar.StatementList;
import compiler.parser.grammar.VariableDeclaration;

import java.io.File;
import java.io.IOException;

public class Parser {

    private Lexer lexer;

    private Lexeme currentLexeme;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Node parse() throws BuildException{

        currentLexeme = lexer.lex();
        Node head = StatementList.match(this);
        if (check(LexemeType.LINE_NEW)) {
            match(LexemeType.LINE_NEW);
        }
        match(LexemeType.EOF);
        return head;
    }

    /**
     *
     * @return
     */
    public Lexeme advance() throws BuildException {
        Lexeme old = currentLexeme;
        currentLexeme = lexer.lex();
        return old;
    }

    /**
     *
     * @param type
     * @return
     */
    public boolean check(LexemeType type){
        return type == currentLexeme.type;
    }

    /**
     *
     * @param types
     * @return
     */
    public boolean check(LexemeType... types){
        for (LexemeType type : types) {
            if (type == currentLexeme.type)
                return true;
        }
        return false;
    }

    public Lexeme getCurrentLexeme(){
        return currentLexeme;
    }

    /**
     *
     * @param type
     * @throws BuildException
     */
    public Lexeme match(LexemeType type) throws BuildException{
        if (check(type))
            return advance();
        throw new BuildException(currentLexeme.beginLine,currentLexeme.beginPos,"Expecting "+type+" but found "+currentLexeme.type);
    }

    public static void main(String[] args){

        try {
            Lexer lexer = new Lexer(new File("test.txt"));
            Parser parser = new Parser(lexer);
            System.out.println(parser.parse());


        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
