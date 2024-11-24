grammar miniC;


// Parser
program :  stmt* EOF ;


stmt    :  vardecl
        |  assign
        |  fndecl
        |  expr ';'
        |  block
        |  while
        |  cond
        |  return
        ;

vardecl :  type('*')? ID array? ('=' expr)? ';' ;
array   :  '['NUMBER']';
assign  :  ID ('['NUMBER']')? '=' expr ';' ;

fndecl  :  type ('[]')? ID '(' params? ')' block ;
params  :  type ID ('[]')? (',' type ID)* ;
return  :  'return' (expr';')|assign ;

fncall  :  ID '(' args? ')' ;
args    :  expr (',' expr)* ;

block   :  '{' stmt* '}' ;
while   :  'while' '(' expr ')' block ;
cond    :  'if' '(' expr ')' block ('else' block)? ;

expr    :  fncall
        |  expr '*' expr
        |  expr '/' expr
        |  expr '+' expr
        |  expr '-' expr
        |  expr '==' expr
        |  expr '!=' expr
        |  expr '>' expr
        |  expr '<' expr
        |  ('*'|'&')? ID (expr)?
        |  NUMBER
        |  STRING
        |  BOOL
        |  '(' expr ')'
        |  '{' expr (','expr)* '}'
        ;

type    : 'int' | 'string' | 'bool';


// Lexer
ID      :  [a-z][a-zA-Z0-9]* ;
NUMBER  :  [0-9]+ ;
STRING  :  '"' (~[\n\r"])* '"' ;
BOOL    :  'T'|'F';

COMMENT :  '#' ~[\n\r]* -> skip ;
WS      :  [ \t\n\r]+ -> skip ;




