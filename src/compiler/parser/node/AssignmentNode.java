package compiler.parser.node;

import compiler.RunTimeException;
import compiler.interpreter.Environment;
import compiler.interpreter.ReturnType;
import compiler.interpreter.ReturnTypeList;
import compiler.lexer.Lexeme;
import compiler.lexer.LexemeType;
import compiler.parser.Node;
import compiler.parser.NodeList;
import compiler.parser.NodeType;

import java.util.LinkedList;
import java.util.ListIterator;

public class AssignmentNode extends Node {

    /**
     * Instantiate a node to represent the parsed expression.
     * @param type the type of expression
     * @param lexeme the lexeme to report errors with
     * @param nodes the children of this node
     */
    private AssignmentNode(NodeType type, Lexeme lexeme, Node... nodes) {
        super(type, lexeme, nodes);
    }

    public static AssignmentNode createAssignmentNode(Lexeme op, NodeList identifiers, NodeList values){
        switch (op.type) {
            case EQUALS:
                return new AssignmentNode(NodeType.Assignment, op, identifiers, values);
            case PLUS_EQUALS:
                return new AssignmentNode(NodeType.AssignmentAddition, op, identifiers, values);
            case MINUS_EQUALS:
                return new AssignmentNode(NodeType.AssignmentSubtraction, op, identifiers, values);
            case TIMES_EQUALS:
                return new AssignmentNode(NodeType.AssignmentMultiplication, op, identifiers, values);
            case MODULUS_EQUALS:
                return new AssignmentNode(NodeType.AssignmentModulus, op, identifiers, values);
            default:
                return new AssignmentNode(NodeType.AssignmentDivision, op, identifiers, values);
        }
    }

    /**
     * Evaluate the expression under the environment.
     * @param env the environment to evaluate the
     * @return the list of expression returned from the evaluated expression
     * @throws RunTimeException
     */
    public ReturnTypeList eval(Environment env) throws RunTimeException {

        NodeList.Iterator identifiers = ((IdentifierListNode) children[0]).getIterator();
        ReturnTypeList expressions = children[1].eval(env);
        ListIterator<ReturnType> expressionIterator = expressions.listIterator();


        while(expressionIterator.hasNext()){
            if (!identifiers.hasNext()) break;

            Node identifier = identifiers.next();


            if (identifier instanceof AccessMemberNode)
                ((AccessMemberNode) identifier).set(env, type, expressionIterator.next());
            else if (identifier instanceof AccessElementNode)
                ((AccessElementNode) identifier).set(env, type, expressionIterator.next());
            else if (identifier instanceof IdentifierNode)
                ((IdentifierNode) identifier).set(env, type, expressionIterator.next());
            else
                throw new RunTimeException(lexeme,"Attempting to set value of something that cannot be set... somehow...");
        }

        return null;
    }
}
