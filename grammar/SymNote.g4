grammar SymNote;

// Parser
program: (topLevelElement | statementLVL1)* EOF;

// Functions like set_bpm, load_sample should be contained in visitor as system functions

topLevelElement: routineDecl | trackDecl;

statementLVL1:
	blockLVL1 # blockStmt
	| 'if' '(' expression ')' statementLVL1 (
		'else' statementLVL1
	)?																					# ifStmt
	| 'while' '(' expression ')' statementLVL1											# whileStmt
	| 'loop' '(' type ID 'from' e1 = expression 'to' e2 = expression ')' statementLVL1	# loopStmt
	| 'parallel' '{' parallelEntry* '}'													# parallelStmt
	| type ID ( '=' expression)? ';'													# declAssignStmt
	| ID '=' expression ';'																# assignStmt
	| callStmt																			# exprStmt;

parallelEntry: callExpr ';';

routineStatement:
	blockRoutine # blockRoutineStmt
	| 'if' '(' expression ')' routineStatement (
		'else' routineStatement
	)?																						# ifRoutineStmt
	| 'while' '(' expression ')' routineStatement											# whileRoutineStmt
	| 'loop' '(' type ID 'from' e1 = expression 'to' e2 = expression ')' routineStatement	#
		loopRoutineStmt
	| type ID ('=' expression)? ';'	# declAssignRoutineStmt
	| ID '=' expression ';'			# assignRoutineStmt
	| 'return' expression? ';'		# returnRoutineStmt
	| callStmt						# exprRoutineStmt;

statementLVL2:
	blockLVL2	# blockStmtLVL2
	| gridStmt	# gridStmtLVL2
	| 'if' '(' expression ')' statementLVL2 (
		'else' statementLVL2
	)?																					# ifStmtLVL2
	| 'while' '(' expression ')' statementLVL2											# whileStmtLVL2
	| 'loop' '(' type ID 'from' e1 = expression 'to' e2 = expression ')' statementLVL2	# loopStmtLVL2
	| type ID ( '=' expression)? ';'													# declAssignStmtLVL2
	| ID '=' expression ';'																# assignStmtLVL2
	| callStmt																			# exprStmtLVL2;

callStmt: callExpr ';';

callExpr: ID '(' arguments? ')';

// Routine and track declarations
routineDecl:
	'routine' ID '(' parameters? ')' 'returns' type blockRoutine;

trackDecl: 'track' ID '(' parameters? ')' blockLVL2;

parameters: param (',' param)*;
param: type ID;
arguments: expression (',' expression)*;

// layers
blockLVL1: '{' statementLVL1* '}'; // layer 1

blockRoutine:
	'{' routineStatement* '}'; // routines (global declarations)

blockLVL2: '{' statementLVL2* '}'; //layer 2

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

// expressions
expression:
	'(' expression ')'										# parenExpr
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
	| STRING												# atomString;

type:
	'int'
	| 'float'
	| 'bool'
	| 'string'
	| 'void'
	| 'note'
	| 'sample'
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
COMMENT: '//' .*? '\n' -> skip;