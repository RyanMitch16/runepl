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
    LiteralBoolean,
    LiteralDecimal,
    LiteralInteger,
    LiteralString,
    OperationAddition,
    OperationDivision,
    OperationEquality,
    OperationGreaterThan,
    OperationGreaterThanEqual,
    OperationInverseEquality,
    OperationInversion,
    OperationModulus,
    OperationLessThan,
    OperationLessThanEqual,
    OperationMultiplication,
    OperationNegation,
    OperationSubtraction,
    IfStatement,
    ReturnStatement,
    WhileStatement,
    StatementList,
    VariableDeclaration
}
