grammar SymNote;

program: statement* EOF;

statement
    : blockLVL1     # blockStmt
    | 'if' '(' expression ')'
        statement ('else' statement)?  # ifStmt
    | type ID ';' # declAssignStmt
    | type ID '=' expression ';' # declAssignInitStmt
    | ID '=' expression ';'  # assignStmt
    | routineDecl # routineDeclStmt
    | ID '(' arguments? ')' ';'  #callRoutineStmt
    | 'return' (ID | expression)? ';' # returnStmt
    | 'track' ID '(' parameters ')' blockLVL2 # trackStmt
    ;

routineDecl
    : 'routine' ID '(' (parameters)? ')' 'returns' type blockLVL1
    ;

parameters
    : type ID (',' type ID)*
    ;

arguments
    : expression (',' expression)*
    ;

expression
    : ID
    ;

type
    : 'int' | 'float' | 'bool'
    ;

blockLVL1
    : '{' statement* '}'
    ;

blockLVL2
    : '{' (statement | statementLVL2)* '}'
    ;

statementLVL2
    : 'wait' expression ';' //Temporary statement
    ;

blockLVL3
    : '{' statement* '}'
    ;