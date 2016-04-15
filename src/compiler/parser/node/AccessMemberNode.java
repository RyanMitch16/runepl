package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnType;
import compiler.interpreter.ReturnTypeList;
import compiler.interpreter.TypeObject;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class AccessMemberNode extends Node {

    /**
     * Instantiate a node to represent the parsed expression.
     * @param type the type of expression
     * @param lexeme the lexeme to report errors with
     * @param nodes the children of this node
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
     * Set the member of the object at the index.
     * @param env the environment to
     * @param type the type of assignment
     * @param value the  member of the object
     * @throws RunTimeException
     */
    public void set(Environment env, NodeType type, ReturnType value) throws RunTimeException{

        ReturnTypeList expressionValue = children[0].eval(env);

        if (expressionValue.size() == 0) {
            throw new RunTimeException(lexeme, "Unable to set a member of a null reference");
        }

        if (expressionValue.size() > 1) {
            throw new RunTimeException(lexeme, "Unable to set a member of multiple reference");
        }

        expressionValue.getFirst().setMember(children[1].lexeme, type, value);
    }

    /**
     * Evaluate the expression under the environment.
     * @param env the environment to evaluate the
     * @return the list of expression returned from the evaluated expression
     * @throws RunTimeException
     */
    public ReturnTypeList eval(Environment env) throws RunTimeException {

        ReturnTypeList expressionValue = children[0].eval(env);

        if (expressionValue.size() == 0) {
            throw new RunTimeException(lexeme, "Unable to retrieve a member of a null reference");
        }

        if (expressionValue.size() > 1) {
            throw new RunTimeException(lexeme, "Unable to retrieve a member of multiple reference");
        }

        return new ReturnTypeList(expressionValue.getFirst().getMember(
                ((IdentifierNode) children[1]).getIdentifierName()));
    }
}