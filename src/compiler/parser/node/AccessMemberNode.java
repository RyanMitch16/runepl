package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnType;
import compiler.interpreter.ReturnTypeList;
import compiler.interpreter.TypeObject;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class AccessMemberNode extends Node {

    /**
     *
     * @param type
     * @param lexeme
     * @param nodes
     */
    private AccessMemberNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    /**
     *
     * @param dot
     * @param expression
     * @param member
     * @return
     */
    public static AccessMemberNode createMemberAccessor(Lexeme dot, Node expression, Node member) {
        return new AccessMemberNode(NodeType.AccessMember, dot, expression, member);
    }

    /**
     *
     * @param env
     * @return
     * @throws RunTimeException
     */
    public ReturnTypeList eval(Environment env) throws RunTimeException {

        ReturnTypeList expressionValue = children[0].eval(env);

        if (expressionValue.size() == 0) {
            throw new RunTimeException(children[0].lexeme, "Unable to retrieve a member of a null reference");
        }

        if (expressionValue.size() > 1) {
            throw new RunTimeException(children[0].lexeme, "Unable to retrieve a member of multiple reference");
        }

        if (!(expressionValue.getFirst() instanceof TypeObject))
            throw new RunTimeException(children[0].lexeme, "Unable to retrieve a member a non object reference");


        return new ReturnTypeList(((TypeObject) expressionValue.getFirst()).getEnv().lookUp(
                ((IdentifierNode) children[1]).getIdentifierName()));
    }
}