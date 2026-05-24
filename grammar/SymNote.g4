grammar SymNote;

// Parser
program: (topLevelElement | statementLVL1)* EOF;

topLevelElement: routineDecl | trackDecl;

// Routine and track declarations
routineDecl: 'routine' ID '(' parameters? ')' 'returns' type blockRoutine;
trackDecl: 'track' ID '(' parameters? ')' blockTrack;


parameters: param (',' param)*;
param: type ID;



commonStatement:
     type ID ( '=' expression)? ';'													    # declAssignStmt
	| ID '=' expression ';'																# assignStmt
	| callStmt																			# exprStmt
	| expression ';'																	# standaloneExpr
	| ';'																				# emptyStmt;


// Statements - LEVEL 1
statementLVL1:
	blockLVL1                                                                                   # blockStmt
	| 'if' '(' expression ')' statementLVL1 (
		'else' statementLVL1
	)?																					        # ifStmt
	| 'while' '(' expression ')' iterationStatementLVL1										    # whileStmt
	| 'loop' '(' type ID 'from' e1 = expression 'to' e2 = expression ')' iterationStatementLVL1	# loopStmt
	| 'parallel' '{' parallelEntry* '}'													        # parallelStmt
	| commonStatement                                                                           # commonStmt;

blockLVL1: '{' statementLVL1* '}'; // layer 1

iterationStatementLVL1:
    blockIterationLVL1                                                                               # blockIterationStmt
	| 'if' '(' expression ')' iterationStatementLVL1 (
		'else' iterationStatementLVL1
	)?                                                                                               # ifIterationStmt
	| 'while' '(' expression ')' iterationStatementLVL1                                              # whileIterationStmt
	| 'loop' '(' type ID 'from' e1 = expression 'to' e2 = expression ')' iterationStatementLVL1      # loopIterationStmt
	| 'parallel' '{' parallelEntry* '}'                                                              # parallelIterationStmt
	| commonStatement                                                                                # commonIterationStmt
	| jumpStatement                                                                                  # jumpIterationStmt;

blockIterationLVL1: '{' iterationStatementLVL1* '}'; // layer 1 - iteration statements

parallelEntry: callExpr ';';


//Routine statements
statementRoutine:
	blockRoutine                                                                                   # blockRoutineStmt
	| 'if' '(' expression ')' statementRoutine (
		'else' statementRoutine
	)?																						        # ifRoutineStmt
	| 'while' '(' expression ')' iterationStatementRoutine											# whileRoutineStmt
	| 'loop' '(' type ID 'from' e1 = expression 'to' e2 = expression ')' iterationStatementRoutine	# loopRoutineStmt
	| 'return' expression? ';'		                                                                # returnRoutineStmt
	| commonStatement                                                                               # commonRoutineStmt;

blockRoutine: '{' statementRoutine* '}'; // routines (global declarations)

iterationStatementRoutine:
    blockIterationRoutine                                                                               # blockIterationRoutineStmt
    | 'if' '(' expression ')' iterationStatementRoutine (
        'else' iterationStatementRoutine
    )?                                                                                                  # ifIterationRoutineStmt
    | 'while' '(' expression ')' iterationStatementRoutine                                              # whileIterationRoutineStmt
    | 'loop' '(' type ID 'from' e1 = expression 'to' e2 = expression ')' iterationStatementRoutine      # loopIterationRoutineStmt
    | 'return' expression? ';'		                                                                    # returnIterationRoutineStmt
    | commonStatement                                                                                   # commonIterationRoutineStmt
    | jumpStatement                                                                                     # jumpIterationRoutineStmt;

blockIterationRoutine: '{' iterationStatementRoutine* '}'; // routines - iteration statements


// Track statements - LEVEL 2
statementTrack:
	blockTrack	                                                                                    # blockTrackStmt
	| gridStmt	                                                                                    # gridTrackStmt
	| 'if' '(' expression ')' statementTrack (
		'else' statementTrack
	)?																					            # ifTrackStmt
	| 'while' '(' expression ')' iterationStatementTrack											# whileTrackStmt
	| 'loop' '(' type ID 'from' e1 = expression 'to' e2 = expression ')' iterationStatementTrack	# loopTrackStmt
	| commonStatement                                                                               # commonTrackStmt;

blockTrack: '{' statementTrack* '}'; //layer 2

iterationStatementTrack:
    blockIterationTrack                                                                               # blockIterationTrackStmt
    | gridStmt                                                                                       # gridIterationTrackStmt
    | 'if' '(' expression ')' iterationStatementTrack (
        'else' iterationStatementTrack
    )?                                                                                               # ifIterationTrackStmt
    | 'while' '(' expression ')' iterationStatementTrack                                              # whileIterationTrackStmt
    | 'loop' '(' type ID 'from' e1 = expression 'to' e2 = expression ')' iterationStatementTrack      # loopIterationTrackStmt
    | commonStatement                                                                                # commonIterationTrackStmt
    | jumpStatement                                                                                  # jumpIterationTrackStmt;

blockIterationTrack: '{' iterationStatementTrack* '}'; //layer 2 - iteration statements



// Grid statements - LEVEL 3
gridStmt:
	'grid' '(' RESOLUTION ')' '{' gridSequence? '}'; //layer 3 - grid

gridSequence:
	gridPlayable gridTailPlayable?
	| '-' gridTailNoHold?;

gridTailPlayable:
	gridPlayable gridTailPlayable?
	| '~' gridTailPlayable?
	| '-' gridTailNoHold?;

gridTailNoHold:
	gridPlayable gridTailPlayable?
	| '-' gridTailNoHold?;

gridPlayable:
	noteElement gridVolModifier?
	| gridChord gridVolModifier?;

gridChord: '[' noteElement (',' noteElement)* ']';
gridVolModifier: '.' 'vol' '(' gridVolValue ')';
gridVolValue: ID | INT | FLOAT;
noteElement: (NOTE | ID);


// Jump statements
jumpStatement:
    'break' ';'																			# breakStmt
    | 'continue' ';'																	# continueStmt;


// Calling expressions
callStmt: callExpr ';';
callExpr: ID '(' arguments? ')';
arguments: expression (',' expression)*;


// expressions
expression:
    ID '++'												    # postInc
    | ID '--'												# postDec
    | '++' ID												# preInc
    | '--' ID												# preDec
	| '(' expression ')'								    # parenExpr
    | '(' type ')' expression   							# castExprStmt
	| 'not' expression										# opNot
    | (SUB | ADD) expression                                # opUnaryMinusPlus
	| callExpr												# funcCallExpr
	| expression (MUL | DIV | MOD) expression				# opMulDivMod
	| expression (ADD | SUB) expression						# opAddSub
	| expression (GT | LT | GE | LE | EQ | NE) expression	# opCompare
	| expression 'and' expression							# opAnd
	| expression 'or' expression							# opOr
	| ID													# atomId
	| INT													# atomInt
	| FLOAT													# atomFloat
	| BOOL													# atomBool
	| STRING												# atomString
	| NOTE													# atomNote;

type:
	'int'
	| 'float'
	| 'bool'
	| 'string'
	| 'void'
	| 'note'
	| 'synth';

//Lexer

MUL: '*';
DIV: '/';
MOD: '%';
ADD: '+';
SUB: '-';
GT: '>';
LT: '<';
GE: '>=';
LE: '<=';
EQ: '==';
NE: '!=';

RESOLUTION: '1/1' | '1/2' | '1/4' | '1/8' | '1/16' | '1/32';
NOTE: [A-G] [b#]? [0-9];

BOOL: 'true' | 'false';
INT: [0-9]+;
FLOAT: [0-9]+ '.' [0-9]* | [0-9]* '.' [0-9]+;
STRING: '"' ( '\\' . | ~('\\' | '"'))* '"';
ID: [a-zA-Z_][a-zA-Z0-9_]*;

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;