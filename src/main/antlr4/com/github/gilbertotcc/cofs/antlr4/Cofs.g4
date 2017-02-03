grammar Cofs;

cofs_program
	: (statement EndOfStatement)* # CofsProgram
	;

statement
	: add_user_expression
	| add_transaction_expression
	;



/*
 * Add user
 */

add_user_expression
	: ADD_USER_COMMAND userId=UserId ( credit=credit_expression )? # AddUserExpression
	;

credit_expression
	: BlockOpen Int BlockClose
	;



/*
 * Add transaction
 */

add_transaction_expression
	: ADD_TRANSACTION_COMMAND offeror=UserId TransactionToSymbol recipients=recipients_list # AddTransactionExpression
	;

recipients_list
	: UserId ( ',' UserId )*
	;



/*
 * Domain specific lexicon
 */

ADD_USER_COMMAND : 'add user' ;

ADD_TRANSACTION_COMMAND : 'add transaction' ;

UserId
	: ALPHA ALPHANUMERIC*
	;

BlockOpen
	: '('
	;

BlockClose
	: ')'
	;

TransactionToSymbol
	: '->'
	;

EndOfStatement
	: ';'
	;



Int
	: '-'? DIGIT+
	;

fragment
DIGIT
	: [0-9]
	;

fragment
ALPHA
	: [a-zA-Z]
	;

fragment
ALPHANUMERIC
	: DIGIT
	| ALPHA
	;



WS
	: [ \t\r\n]+ -> skip
	;

LINE_COMMENT
    : '#' ~[\r\n]* -> channel(HIDDEN)
    ;