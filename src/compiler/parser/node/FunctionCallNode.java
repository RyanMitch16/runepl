package compiler.parser.node;

import compiler.interpreter.Environment;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

import java.util.LinkedList;

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

    public Object eval(Environment env) {

        Object func = children[0].eval(env);

        LinkedList<String> parameters = ((AnonFunctionNode) func).getParameters();
        LinkedList<Object> arguments = (children.length == 1)? new LinkedList<>() : ((ExpressionListNode) children[1]).eval(env);

        Environment newEnv = ((AnonFunctionNode) func).env.extend(parameters, arguments); //TODO: Extend environment
        return ((AnonFunctionNode) func).getBody().eval(newEnv);
    }
}