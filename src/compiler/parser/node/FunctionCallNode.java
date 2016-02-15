package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnType;
import compiler.interpreter.ReturnTypeList;
import compiler.interpreter.TypeFunction;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class FunctionCallNode extends Node {

    /**
     * @param type
     * @param lexeme
     * @param nodes
     */
    private FunctionCallNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static FunctionCallNode createFunctionCall(Lexeme dot, Node expression, Node arguments) {
        return new FunctionCallNode(NodeType.FunctionCall, dot, expression, arguments);
    }

    public static FunctionCallNode createFunctionCall(Lexeme dot, Node expression) {
        return new FunctionCallNode(NodeType.FunctionCall, dot, expression);
    }

    public ReturnTypeList eval(Environment env) throws RunTimeException{

        ReturnTypeList func = children[0].eval(env);

        if (func.size() == 0)
            throw new RunTimeException(children[0].lexeme, "Unable to call function on a null reference");

        if (func.size() > 1)
            throw new RunTimeException(children[0].lexeme, "Unable to call function on multiple references");

        ReturnType functionObject = func.getFirst();
        if (!(functionObject instanceof TypeFunction))
            throw new RunTimeException(children[0].lexeme, "Unable to call function on a non-function reference");

        ReturnTypeList arguments = (children.length == 1)? new ReturnTypeList() : children[1].eval(env);
        return ((TypeFunction) functionObject).call(lexeme,arguments);
    }
}