package compiler;

public class RunTimeException extends Exception {

    public RunTimeException(int beginLine, int beginPos, String msg){
        super("Run time error at ("+beginLine+":"+beginPos+"): " +msg);
    }
}
