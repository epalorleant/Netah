/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

grammar ESCExpr;

expression : esc_expression ('parameters' '{' params '}')?;

esc_expression: streamdef | operator_def '(' esc_expression (',' esc_expression)* ')' (':' window_specification)? ;

streamdef: STREAM '(' ID (',' ID)? ')' ;

window_specification: batch_n | mbatch | timefixed | slidingwin;

batch_n : BATCH '(' INT ')'  ;

mbatch: MBATCH '(' INT ',' INT ')' ;

timefixed: WITHIN '(' INT ',' INT ')' ;

slidingwin: SLIDING '(' INT ',' INT ')' ;

operator_def : ID '@' operator_type;

operator_type: aggregate | filterdef | AND | OR | SEQ | FIRST | LAST ;

filterdef: FILTER '[' predicate ']';  

predicate: or_expression;

or_expression: and_expression ('||' and_expression)*;

and_expression: term ('&&' term)*;

term:  ID sign=(LT | GT | EQ |GEQ| LEQ| NEQ) val=(INT| DOUBLE|STRING) | '(' predicate')' | TRUE | FALSE ;

//condition: (ID sign=('<' | '>' | '=' |'>='| '<=') val=(INT| DOUBLE|STRING))| TRUE | FALSE;

aggregate: average | sum | count| min | max;
sum : 'sum' '[' ID ',' ID ']';
average: 'avg' '[' ID ',' ID ']';
count : 'count' '[' ID ']';
min: 'min' '[' ID ',' ID ']';
max: 'max' '[' ID ',' ID ']';
         
params : param (',' param)* ;

param: ID '['dimension (',' dimension)* ']' ;

dimension: memory_spec | cpu_spec | selection_mode;

memory_spec: 'memory' '=' INT;

cpu_spec: 'cpu_time' '=' INT;

selection_mode: 'selection_mode' '=' SELECTIONMODE;
// lexer rules
SELECTIONMODE: 'continous' | 'chronologic' | 'priority' | 'recent' ;
//AGGREGATE_TYPE: 'sum' | 'avg' | 'count' | 'min' |'max' ;
OR: [oO][rR];
AND: [aA][nD][dD];
FILTER: [Ff]'ilter';
FIRST: 'first';
SEQ: 'seq';
LAST: 'last';
STREAM: [sS]'tream';
BATCH: 'batch';
MBATCH: 'mbatch';
WITHIN: 'within';
SLIDING: 'sliding';
ID  :   LETTER (LETTER | DIGIT)* ;
STRING: '"' (ESC|.)*? '"' ;
TRUE: 'true';
FALSE: 'false';
LT : '<';
GT : '>';
EQ : '=';
NEQ : '!=';
LEQ : '<=';
GEQ : '>=';

INT : DIGIT+[Ll]?; // match integers

DOUBLE: DIGIT+'.'DIGIT+ ;

fragment
DIGIT: [0-9];

fragment
ESC : '\\"' | '\\\\' ; // 2-char sequences \" and \\

fragment
LETTER: [a-zA-Z] ;      // match characters

WS  :   [' '\t\r\n]+ -> skip ; // toss out whitespace