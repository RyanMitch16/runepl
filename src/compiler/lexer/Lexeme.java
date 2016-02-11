package compiler.lexer;

/**
 * The lexeme class that holds an individual token created by the token
 */
public class Lexeme {

    //he type of lexeme
    public LexemeType type;

    //The text of the lexeme
    public String text;

    //The position of the lexeme in the file
    public final int beginLine;
    public final int beginPos;

    /**
     * Initialize a lexeme with the type, syntax type, and the position in the file.
     * @param type          the type of the lexeme
     * @param beginLine     the line where the lexeme starts in the file
     * @param beginPos      the position in the starting line where the lexeme starts
     */
    public Lexeme(LexemeType type, int beginLine, int beginPos){
        this.type = type;
        this.beginLine = beginLine;
        this.beginPos = beginPos;
    }

    /**
     * Initialize a lexeme with the type, syntax type, and the position in the file.
     * @param type          the type of the lexeme
     * @param text          the text of the lexeme
     * @param beginLine     the line where the lexeme starts in the file
     * @param beginPos      the position in the starting line where the lexeme starts
     */
    public Lexeme(LexemeType type, String text, int beginLine, int beginPos){
        this.type = type;
        this.text = text;
        this.beginLine = beginLine;
        this.beginPos = beginPos;
    }
}