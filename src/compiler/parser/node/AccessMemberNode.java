package compiler.parser.node;

import compiler.interpreter.Environment;
import compiler.lexer.Lexeme;
import compiler.parser.Node;
import compiler.parser.NodeType;

public class AccessMemberNode extends Node {

    /**
     * @param type
     * @param lexeme
     * @param nodes
     */
    private AccessMemberNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static AccessMemberNode createMemberAccessor(Lexeme dot, Node expression, Node member) {
        return new AccessMemberNode(NodeType.AccessMember, dot, expression, member);
    }

    public Object eval(Environment env) {

        return ((Environment) children[0].eval(env)).lookUp(((IdentifierNode) children[1]).getIdentifier());
    }
}