grammar SymNote;

// Parser
program: (topLevelElement | statement)* EOF;

// Functions like set_bpm, load_sample should be contained in visitor as system functions !!!!

topLevelElement
    : routineDecl
    | trackDecl
    ;

statement
    : blockLVL1                                         # blockStmt
    | 'if' '(' expression ')' statement
      ('else' statement)?                               # ifStmt
    | 'while' '(' expression ')' statement              # whileStmt
    | 'loop' '(' type ID 'from' e1=expression
      'to' e2=expression ')' statement                  # loopStmt
    | 'parallel' '{' statement* '}'                     # parallelStmt
    | type ID ( '=' expression )? ';'                   # declAssignStmt
    | ID '=' expression ';'                             # assignStmt
    | 'return' expression? ';'                          # returnStmt
    | expression ';'                                    # exprStmt
    ;

// Routine and track declarations
routineDecl
    : 'routine' ID '(' parameters? ')' 'returns' type blockLVL1
    ;

trackDecl
    : 'track' ID '(' parameters? ')' blockLVL2
    ;

parameters: param (',' param)*;
param     : type ID;
arguments : expression (',' expression)*;

// layers
blockLVL1 : '{' statement* '}' ; // layer 1

blockLVL2 : '{' (statement | gridStmt)* '}' ;  //layer 2

gridStmt : 'grid' '(' expression ')' '{' gridContent* '}' ; //layer 3 - grid

gridContent
    : noteElement                                       # gridNote
    | '-'                                               # gridRest
    | '~'                                               # gridHold
    | '[' noteElement (',' noteElement)* ']'            # gridChord
    ;

noteElement
    : (NOTE | ID) ('(' expression ')')?
    ;

// expressions
expression
    : '(' expression ')'                                # parenExpr
    | 'not' expression                                  # opNot
    | ID '(' arguments? ')'                             # funcCallExpr
    | expression (MUL|DIV|MOD) expression               # opMulDivMod
    | expression (ADD|SUB) expression                   # opAddSub
    | expression (GT|LT|GE|LE|EQ|NE) expression         # opCompare
    | expression 'and' expression                       # opAnd
    | expression 'or' expression                        # opOr
    | ID                                                # atomId
    | INT                                               # atomInt
    | FLOAT                                             # atomFloat
    | BOOL                                              # atomBool
    | STRING                                            # atomString
    ;

type : 'int' | 'float' | 'bool' | 'void' | 'note' | 'sample' | 'synth' ;

//Lexer

MUL: '*'; DIV: '/'; MOD: '%'; ADD: '+'; SUB: '-';
GT: '>'; LT: '<'; GE: '>='; LE: '<='; EQ: '=='; NE: '!=';


NOTE: [A-G] [b#]? [0-9]; // ??

BOOL: 'true' | 'false';
INT: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]* | [0-9]* '.' [0-9]+;
STRING: '"' ( '\\' . | ~('\\'|'"') )* '"';
ID: [a-zA-Z_][a-zA-Z0-9_]*;

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' .*? '\n' -> skip;