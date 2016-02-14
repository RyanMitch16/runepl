package compiler.parser;

/**
 * The different types of nodes that form the parse tree.
 */
public enum NodeType {

    AccessElement,
    AccessMember,
    AnonFunction,
    ExpressionList,
    FunctionCall,
    Identifier,
    IdentifierList,
    LiteralDecimal,
    LiteralInteger,
    LiteralString,
    OperationAddition,
    OperationDivision,
    OperationInversion,
    OperationModulus,
    OperationMultiplication,
    OperationNegation,
    OperationSubtraction,
    ReturnStatement,
    StatementList,
    VariableDeclaration
}
