package compiler.parser;

/**
 * The different types of nodes that form the parse tree.
 */
public enum NodeType {

    AccessElement,
    AccessMember,
    AnonFunction,
    Assignment,
    AssignmentAddition,
    AssignmentSubtraction,
    AssignmentMultiplication,
    AssignmentDivision,
    AssignmentModulus,
    ElseStatement,
    ExpressionList,
    FunctionCall,
    Identifier,
    IdentifierList,
    LiteralArray,
    LiteralBoolean,
    LiteralDecimal,
    LiteralInteger,
    LiteralString,
    OperationAddition,
    OperationAnd,
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
    OperationOr,
    OperationSubtraction,
    IfStatement,
    ReturnStatement,
    WhileStatement,
    StatementList,
    VariableDeclaration
}
