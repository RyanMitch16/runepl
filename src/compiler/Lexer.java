package compiler;

import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;

import java.io.*;
import java.math.BigInteger;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Lexer {

    /**
     * The tree map of all the possible symbols used in the language.
     */
    public static final TreeMap<String, LexemeType> symbolMap = new TreeMap<>();
    static {
        symbolMap.put("&&", LexemeType.AND);
        symbolMap.put("{", LexemeType.BRACE_LEFT);
        symbolMap.put("}", LexemeType.BRACE_RIGHT);
        symbolMap.put("[", LexemeType.BRACKET_LEFT);
        symbolMap.put("]", LexemeType.BRACKET_RIGHT);
        symbolMap.put(":", LexemeType.COLON);
        symbolMap.put(",", LexemeType.COMMA);
        symbolMap.put("/*", LexemeType.COMMENT_MULTI);
        symbolMap.put("//", LexemeType.COMMENT_SINGLE);
        symbolMap.put("/", LexemeType.DIVIDES);
        symbolMap.put("/=", LexemeType.DIVIDES_EQUALS);
        symbolMap.put(".", LexemeType.DOT);
        symbolMap.put("==", LexemeType.EQUALS_EQUALS);
        symbolMap.put("=", LexemeType.EQUALS);
        symbolMap.put(">", LexemeType.GREATER_THAN);
        symbolMap.put(">=", LexemeType.GREATER_THAN_EQUAL);
        symbolMap.put("<", LexemeType.LESS_THAN);
        symbolMap.put("<=", LexemeType.LESS_THAN_EQUAL);
        symbolMap.put("-", LexemeType.MINUS);
        symbolMap.put("-=", LexemeType.MINUS_EQUALS);
        symbolMap.put("%", LexemeType.MODULUS);
        symbolMap.put("%=", LexemeType.MODULUS_EQUALS);
        symbolMap.put("->", LexemeType.LINE_CONT);
        symbolMap.put("!", LexemeType.NOT);
        symbolMap.put("!=", LexemeType.NOT_EQUAL);
        symbolMap.put("||", LexemeType.OR);
        symbolMap.put("(", LexemeType.PAREN_LEFT);
        symbolMap.put(")", LexemeType.PAREN_RIGHT);
        symbolMap.put("+", LexemeType.PLUS);
        symbolMap.put("+=", LexemeType.PLUS_EQUALS);
        symbolMap.put("*", LexemeType.TIMES);
        symbolMap.put("*=", LexemeType.TIMES_EQUALS);
    }

    /**
     * The hashmap of all the possible keywords in the language.
     */
    public static final HashMap<String, LexemeType> keywordMap = new HashMap<>();
    static {
        keywordMap.put("else", LexemeType.ELSE);
        keywordMap.put("false", LexemeType.FALSE);
        keywordMap.put("func", LexemeType.FUNC);
        keywordMap.put("if", LexemeType.IF);
        keywordMap.put("return", LexemeType.RETURN);
        keywordMap.put("true", LexemeType.TRUE);
        keywordMap.put("var", LexemeType.VAR);
        keywordMap.put("while", LexemeType.WHILE);
    }

    //The count of the number of spaces that equals a tab
    private static int spaceForTabs = 4;

    //Whether to count multiple spaces as a tab.
    private static boolean useSpaces = true;

    //The states of the token matching
    private StringBuilder lexemeBuilder;
    private int currentLine;
    private int currentPosition;
    private int lineTabLast;
    private int lineTabCurrent;
    private int lineTabsToBeReturned;

    //Create the buffered reader to read from the file
    private static BufferedReader fileReader;

    //Check if the end of the file has been reached
    private boolean eof;

    //Whether a line continue symbol was just read indicating the new line that should follow is to be skipped
    private boolean lineContinue;

    //The last character read from the file
    private Character lastCharRead;

    //Set when a period is read after an integer but what followed was not an integer
    private boolean returnDot;

    //Set when the last line was a new line to indicate the next should be ignored
    private boolean lastWasNewLine;

    /**
     * Initiates the lexer to read in the specified file.
     * @param file  the file to lex
     */
    public Lexer(File file) throws FileNotFoundException{

        //Create the buffered reader to read from the file
        fileReader = new BufferedReader(new FileReader(file));

        //Initiate the default states
        lexemeBuilder = new StringBuilder();
        currentLine = 1;
        currentPosition = 0;
        lineTabLast = 0;
        lineTabCurrent = 0;
        lineTabsToBeReturned = 0;

        //Set that the end of the file has not been reached
        eof = false;
        lineContinue = false;
        lastCharRead = null;
        returnDot = false;
        lastWasNewLine = true;
    }

    /**
     * Set whether to count multiple spaces as a tab.
     * @param use whether to count multiple spaces as a tab
     */
    public void setUseSpaces(boolean use){
        useSpaces = use;
    }

    /**
     * Set the number of spaces that equals a tab
     * @param spaces the number of spaces that equals a tab
     */
    public void setSpaceForTabs(int spaces){
        spaceForTabs = spaces;
    }

    /**
     * Check whether the character is a number or not.
     * @param c the character to check
     * @return whether the character is a number or not
     */
    public static boolean isNumber(char c){
        return (c >= 48 && c<= 57);
    }

    /**
     * Check whether the character is a letter or not.
     * @param c the character to check
     * @return whether the character is a letter or not
     */
    public static boolean isLetter(char c){
        return (c >= 65 && c<= 90) || (c >= 97 && c<= 122);
    }

    /**
     * Check whether the character is a symbol besides underscore or not.
     * @param c the character to check
     * @return whether the character is a symbol besides underscore or not
     */
    public static boolean isSymbol(char c){
        return (c >= 33 && c <= 47) || (c >= 58 && c <= 64) || (c >= 91 && c <= 96 && c != '_') || (c >= 123 && c <= 126);
    }

    /**
     *c Reads the next character from the input.
     * @return the integer value of the character or -1 if the end of the file has been reached
     */
    private char readChar() throws IOException {

        if (lastCharRead != null) {

            //Increase the line and position count
            if (lastCharRead == '\n') {
                currentLine += 1;
                currentPosition = 0;
            } else {
                currentPosition += 1;
            }
        }

        //Return a new line symbol if the end of the file has been reached
        if (eof) return '\n';

        //Read the next value from the file
        int c = fileReader.read();
        if (c == -1) {
            eof = true;
            return '\n';
        }
        lastCharRead = (char) c;
        return (char) c;
    }

    /**
     * Get the last character read in from the file.
     * @return the last character read
     * @throws IOException
     */
    private char getLastCharRead() throws IOException {
        if (lastCharRead == null) {
            lastCharRead = readChar();
            return lastCharRead;
        } else {
            return lastCharRead;
        }
    }

    /**
     * Check if there are no more characters to read.
     * @return  whether the end of the input has been reached or not
     */
    private boolean reachedEOF() {
        return eof;
    }

    /**
     * Lex a symbol from the file at the current position.
     * @return the lexeme that represents the symbol
     * @throws IOException
     * @throws BuildException
     */
    public Lexeme lexSymbol() throws IOException, BuildException {
        //Clear the string buffer
        lexemeBuilder.delete(0, lexemeBuilder.length());
        char c = getLastCharRead();

        //Append the character character if it still creates a valid symbol
        while (!reachedEOF()) {
            String token = lexemeBuilder.toString() + c;
            Map.Entry<String, LexemeType> entry = symbolMap.ceilingEntry(token);
            if (entry.getKey().startsWith(token)) {
                lexemeBuilder.append(c);
                c = readChar();
            } else {
                break;
            }
        }

        //Finish building the symbol and add it to the correct list
        LexemeType type = symbolMap.get(lexemeBuilder.toString());
        if (type != null) {
            return new Lexeme(type, currentLine, currentPosition);
        } else {
            //Log an unknown symbol error since the operator is not an operator or a structure
            throw new BuildException(currentLine, currentPosition, "Unknown symbol "+lexemeBuilder.toString());
        }
    }

    /**
     * Lex a single line comment from the file at the current position.
     * @throws IOException
     */
    public void lexSingleLineComment() throws IOException{
        //Read the characters until the end of the file or the end of the line
        char c = getLastCharRead();
        while (!reachedEOF() && c != '\n') {
            c = readChar();
        }
    }

    /**
     * Lex a multi line comment from the file at the current position.
     * @throws IOException
     * @throws BuildException
     */
    public void lexMultiLineComment() throws IOException, BuildException {
        //Save the location of the comment
        int bl = currentLine;
        int bp = currentPosition;

        //Read the characters until the end of the file or the end of the comment
        char c = getLastCharRead();
        while (!reachedEOF()) {
            if (c == '*') {
                char c2 = readChar();
                if (c2 == '/') {
                    readChar();
                    return;
                }
                continue;
            }
            c = readChar();
        }

        //Throw an exception because the comment was not closed
        throw new BuildException(bl, bp, "Unclosed comment");
    }

    /**
     * Lex an identifier from the file at the current position.
     * @return  the lexeme that represents the identifier
     * @throws IOException
     * @throws BuildException
     */
    public Lexeme lexIdentifier() throws IOException, BuildException {
        //Clear the string buffer
        lexemeBuilder.delete(0, lexemeBuilder.length());

        //Save the location of the identifier
        int bl = currentLine;
        int bp = currentPosition;

        //Read the characters until the end of the file or the end of the identifier
        char c = getLastCharRead();
        while (!reachedEOF()) {
            if (c == '_' || isLetter(c) || isNumber(c)) {
                lexemeBuilder.append(c);
            } else {
                break;
            }
            c = readChar();
        }

        //Return the keyword lexeme if the text is a keyword
        String text = lexemeBuilder.toString();
        LexemeType type = keywordMap.getOrDefault(text, null);
        if (type != null) {
            return new Lexeme(type, bl, bp);
        }
        return new Lexeme(LexemeType.IDENTIFIER, lexemeBuilder.toString(), bl, bp);
    }

    /**
     * Check if the text represents an integer literal
     * @param text  the text to check
     * @return  if the text represents an integer literal or not
     */
    public static boolean isInteger(String text) {
        int length = text.length();
        for (int i=0;i<length;i++) {
            if (!isNumber(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the text represents an hexadecimal literal
     * @param text  the text to check
     * @return  if the text represents an hexadecimal literal or not
     */
    public static boolean isHexadecimal(String text) {

        int length = text.length();
        if (length < 3)
            return false;

        if (text.charAt(0) != '0')
            return false;

        if (text.charAt(1) != 'x')
            return false;

        for (int i=2;i<length;i++) {
            char c = text.charAt(i);
            if ((c < 65 || c > 70) && (c < 48 || c > 57))
                return false;
        }
        return true;
    }

    /**
     * Check if the text represents an binary literal
     * @param text  the text to check
     * @return  if the text represents an binary literal or not
     */
    public static boolean isBinary(String text) {
        int length = text.length();
        if (length < 3)
            return false;

        if (text.charAt(0) != '0')
            return false;

        if (text.charAt(1) != 'b')
            return false;

        for (int i=2;i<length;i++) {
            char c = text.charAt(i);
            if ((c != 48 && c != 49))
                return false;
        }
        return true;
    }

    /**
     * Converts a hexadecimal representation of an integer to an decimal representation
     * @param text the hexadecimal integer
     * @return the decimal representation of the integer
     */
    public static String hexadecimalToDecimal(String text) {

        //Make the length of the hexadecimal even to fill all bytes
        if (text.length() % 2 == 1)
            text = "0"+text.substring(2);
        else
            text = text.substring(2);

        //Make the length of the bytes one longer so the MSB is a 0 so it is positive
        byte[] bytes = new byte[text.length()/2+1];

        //Convert each letter or number
        for (int i=0; i<bytes.length-1; i++) {
            byte c1 = (byte) ((text.charAt(2*i) >= 65) ? (text.charAt(2*i)-55) : (text.charAt(2*i)-48));
            byte c2 = (byte) ((text.charAt(2*i+1) >= 65) ? (text.charAt(2*i+1)-55) : (text.charAt(2*i+1)-48));
            bytes[i+1] = (byte) (c1 << 4 | c2);
        }
        return (new BigInteger(bytes)).toString();
    }

    /**
     * Converts a binary representation of an integer to an decimal representation
     * @param text the binary integer
     * @return the decimal representation of the integer
     */
    public static String binaryToDecimal(String text) {

        //Make the length of the binary a multiple of eight to fill all bytes
        if ((text.length()-2) % 8 != 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < (8 - ((text.length()-2) % 8)); i++) {
                builder.append("0");
            }
            builder.append(text.substring(2));
            text = builder.toString();
        } else {
            text = text.substring(2);
        }

        //Make the length of the bytes one longer so the MSB is a 0 so it is positive
        byte[] bytes = new byte[text.length()/8+1];

        //Convert each bit
        for (int i=0; i<bytes.length-1; i++) {
            byte sum = 0;
            for (int j = 0; j < 8; j++) {
                sum |= ((byte) ((text.charAt(j + i * 8) == 48) ? 0 : 1)) << (7-j);
            }
            bytes[i + 1] = sum;
        }
        return (new BigInteger(bytes)).toString();
    }

    /**
     * Lex a number from the file at the current position.
     * @return the lexeme that represents the number
     * @throws IOException
     * @throws BuildException
     */
    public Lexeme lexNumber() throws IOException, BuildException {
        //Get the location of the number
        int bl = currentLine;
        int bp = currentPosition;

        //Clear the string buffer
        lexemeBuilder.delete(0, lexemeBuilder.length());

        //Read the characters until the end of the file or the end of the identifier
        char c = getLastCharRead();
        while (!reachedEOF()) {
            if (isLetter(c) || isNumber(c)) {
                lexemeBuilder.append(c);
            } else {
                break;
            }
            c = readChar();
        }

        String numberText = lexemeBuilder.toString();
        //Convert the hexadecimal number to decimal number
        if (isHexadecimal(numberText)) {
            return new Lexeme(LexemeType.LITERAL_INTEGER, hexadecimalToDecimal(numberText), bl, bp);
        }
        //Convert the binary number to decimal number
        else if (isBinary(numberText)) {
            return new Lexeme(LexemeType.LITERAL_INTEGER, binaryToDecimal(numberText), bl, bp);
        }
        //Return an integer or a decimal number
        else if (isInteger(numberText)) {
            if (getLastCharRead() == '.') {
                c = readChar();
                if (isNumber(c)) {
                    //Add the decimal places until the end of the file or a non letter is read
                    int decimalPos = lexemeBuilder.length();
                    lexemeBuilder.append('.');
                    lexemeBuilder.append(c);
                    while (!reachedEOF() && (isNumber(c) || isLetter(c))) {
                        lexemeBuilder.append(c);
                        c = readChar();
                    }
                    //Return the decimal number if the decimal place characters are all integers
                    if (isInteger(lexemeBuilder.substring(decimalPos+1,lexemeBuilder.length())))
                        return new Lexeme(LexemeType.LITERAL_DECIMAL, lexemeBuilder.toString(), bl, bp);
                }
                else {
                    //Since the dot was read earlier, set a flag to return the dot next iteration and return the integer
                    returnDot = true;
                    return new Lexeme(LexemeType.LITERAL_INTEGER, numberText, bl, bp);
                }
            } else {
                return new Lexeme(LexemeType.LITERAL_INTEGER, numberText, bl, bp);
            }
        }
        //Throw an exception if the number does not match any format
        throw new BuildException(bl, bp, "Unknown number literal format \""+lexemeBuilder.toString()+"\"");
    }

    //May be implemented later
    /*public Lexeme lexChar() throws IOException, BuildException {

    }*/

    /**
     * Lex a string from the file at the current position.
     * @return the lexeme that represents the string
     * @throws IOException
     * @throws BuildException
     */
    public Lexeme lexString() throws IOException, BuildException {
        //Save the location of the comment
        int bl = currentLine;
        int bp = currentPosition;

        //Clear the string buffer
        lexemeBuilder.delete(0, lexemeBuilder.length());

        //Read the characters until the end of the file or the end of the comment
        char c = readChar();
        while (!reachedEOF()) {
            if (c == '\\') {
                lexemeBuilder.append(c);
                lexemeBuilder.append(readChar());
            } else if (c == '"'){
                readChar();
                return new Lexeme(LexemeType.LITERAL_STRING,lexemeBuilder.toString(),bl,bp);
            } else {
                lexemeBuilder.append(c);
            }
            c = readChar();
        }

        //Throw an exception because the comment was not closed
        throw new BuildException(bl, bp, "Unclosed string");
    }

    /**
     * Count the difference of indentions between the current line and the previous.
     * @return the integer difference between this line and the last
     * @throws IOException
     * @throws BuildException
     */
    public int countTabs() throws IOException, BuildException {

        char c = getLastCharRead();
        if (useSpaces) {

            //Count the number of spaces leading up to a character that is not a new line
            int spaceCount = 0;
            while (!reachedEOF()) {
                if (c == '\t') {
                    spaceCount += spaceForTabs;
                } else if (c == ' ') {
                    spaceCount += 1;
                } else if (c == '\n') {
                    spaceCount = 0;
                } else {
                    break;
                }
                c = readChar();
            }

            //Throw an exception if the space count not acceptable
            if ((spaceCount % spaceForTabs) != 0) {
                throw new BuildException(currentLine, 0, "Space count not a multiple of the number of " +
                        "spaces counted as a tab (" + spaceForTabs + ")");
            }
            return (spaceCount / spaceForTabs);
        }

        //Count the number of tabs leading up to a character that is not a new line
        int tabCount = 0;
        int spaceCount = 0;
        while (!reachedEOF()) {
            if (c == '\t') {
                tabCount += 1;
            } else if (c == '\n') {
                tabCount = 0;
                spaceCount = 0;
            } else if (c != ' ') {
                spaceCount += 1;
            } else {
                break;
            }
            c = readChar();
        }

        //Throw an exception if the spaces are used
        if (spaceCount != 0) {
            throw new BuildException(currentLine, currentPosition, "Spaces not allowed to be used with tabs " +
                "while use spaces is not enabled.");
        }
        return tabCount;
    }

    /**
     * Lex the next lexeme from the file.
     * @return the next lexeme from the file or EOF
     */
    public Lexeme lex() throws BuildException{


        try {
            //Read in characters until the end of the file has been reached
            char c;
            while (!reachedEOF() || lineTabsToBeReturned < 0) {

                //Return a dot (period, full stop) if the integer return previously did not have a decimal place when checked
                if (returnDot) {
                    returnDot = false;
                    lastWasNewLine = false;
                    return new Lexeme(LexemeType.DOT, currentLine, currentPosition-1);
                }

                //Count the number of tabs
                if (currentPosition == 0 && lineTabsToBeReturned == 0) {
                    lineTabLast = lineTabCurrent;
                    lineTabCurrent = countTabs();
                    lineTabsToBeReturned = lineTabCurrent - lineTabLast;
                }

                //Return tab increase lexemes if the tab count increased this line
                if (lineTabsToBeReturned > 0) {
                    lineTabsToBeReturned -= 1;
                    return new Lexeme(LexemeType.TAB_INC, currentLine, 0);
                }

                //Return tab decrease lexemes if the tab count decreased this line
                if (lineTabsToBeReturned < 0) {
                    lineTabsToBeReturned += 1;
                    return new Lexeme(LexemeType.TAB_DEC, currentLine, 0);
                }

                //Get the current character
                c = getLastCharRead();

                //Skip whitespace
                while (c == ' ') {
                    c = readChar();
                }

                if (lastWasNewLine && c == '\n') {
                    c = readChar();
                    continue;
                }

                //Catch if after the line continuation symbol, the new line that should follow is missing
                if (lineContinue && c == '\n') {
                    throw new BuildException(currentLine,currentPosition,"New line expected after line continuation symbol");
                }

                //Lex new line characters if there is not a line continuation symbol before them
                if (c == '\n') {
                    lastWasNewLine = true;
                    if (!lineContinue) {
                        int bl = currentLine;
                        int bp = currentPosition;
                        readChar();
                        return new Lexeme(LexemeType.LINE_NEW, bl, bp);
                    }
                    lineContinue = false;
                    readChar();
                }

                //Attempt to lex a string
                else if (c == '"') {
                    lastWasNewLine = false;
                    return lexString();
                }

                //Attempt to lex a symbol or a comment
                else if (isSymbol(c)) {
                    Lexeme lexeme = lexSymbol();
                    if (lexeme.type == LexemeType.COMMENT_MULTI) {
                        lexMultiLineComment();
                        /*if (!lastWasNewLine) {
                            lastWasNewLine = true;
                            return new Lexeme(LexemeType.LINE_NEW, currentLine, currentPosition);
                        }*/
                    } else if (lexeme.type == LexemeType.COMMENT_SINGLE) {
                        lexSingleLineComment();
                        /*if (!lastWasNewLine) {
                            lastWasNewLine = true;
                            return new Lexeme(LexemeType.LINE_NEW, currentLine, currentPosition);
                        }*/
                    } else if (lexeme.type == LexemeType.LINE_CONT) {
                        lineContinue = true;
                    } else {
                        lastWasNewLine = false;
                        return lexeme;
                    }
                }

                //Attempt to lex an identifier
                else if (isLetter(c) || c == '_') {
                    lastWasNewLine = false;
                    return lexIdentifier();
                }

                //Attempt to lex a number
                else if (isNumber(c)) {
                    lastWasNewLine = false;
                    return lexNumber();
                }

                //Return an exception because whatever character that was read is not allowed
                else {
                    throw new BuildException(currentLine,currentPosition,"Unknown token ("+c+")");
                }
            }
        } catch (IOException e) {
            System.out.println("IOE");
        }

        if (lineTabCurrent > 0) {
            lineTabCurrent -= 1;
            return new Lexeme(LexemeType.TAB_DEC, currentLine+1, 0);
        }

        return new Lexeme(LexemeType.EOF, currentLine, currentPosition);
    }

    public static void main(String[] args){

        try {
            Lexer lex = new Lexer(new File(args[0]));
            Lexeme lexeme = null;
            while ((lexeme == null) || lexeme.type != LexemeType.EOF) {
                lexeme = lex.lex();
                System.out.println(lexeme.text+" : "+lexeme.type);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
