%%

%class MyLexer
%function next_token
%type PvToken
%line
%column

%{
    KWTable kwTable = new KWTable();
%}

// Lexer States
%xstate COMMENT
ALPHA = [a-zA-Z]
DIGIT = [0-9]
ID = {ALPHA}({ALPHA}|{DIGIT})*
INT_CONST = [0-9]+ | 0[0-7]+ | 0x[0-9a-fA-F]+
FLOAT_CONST = 0\.[0-9]+(E[+-]?[0-9]+)?
BOOL_CONST = true|false

%%

// Rules for handling comments
"//" { yybegin(COMMENT); }
<COMMENT>[^\n]* { /* consume until end of line */ }
<COMMENT>\n { yybegin(YYINITIAL); }

// Skip whitespace
[\t\n\r ]+ { /* ignore whitespace */ }

// Keywords and Identifiers
"main" { return new PvToken(sym.MAIN, yytext(), yyline, yycolumn); }
"exit" { return new PvToken(sym.EXIT, yytext(), yyline, yycolumn); }
"int" { return new PvToken(sym.INT, yytext(), yyline, yycolumn); }
"float" { return new PvToken(sym.FLOAT, yytext(), yyline, yycolumn); }
"bool" { return new PvToken(sym.BOOL, yytext(), yyline, yycolumn); }
"for" { return new PvToken(sym.FOR, yytext(), yyline, yycolumn); }
"in" { return new PvToken(sym.IN, yytext(), yyline, yycolumn); }
"apply" { return new PvToken(sym.APPLY, yytext(), yyline, yycolumn); }
{ID} {
    int tokenType = kwTable.find(yytext());
    return new PvToken(tokenType, yytext(), yyline, yycolumn);
}

// Constants
{INT_CONST} { return new PvToken(sym.CONST, yytext(), yyline, yycolumn); }
{FLOAT_CONST} { return new PvToken(sym.CONST, yytext(), yyline, yycolumn); }
{BOOL_CONST} { return new PvToken(sym.CONST, yytext(), yyline, yycolumn); }

// Operators and Symbols
"+" { return new PvToken(sym.PLUS, yytext(), yyline, yycolumn); }
"-" { return new PvToken(sym.MINUS, yytext(), yyline, yycolumn); }
":=" { return new PvToken(sym.ASSIGN, yytext(), yyline, yycolumn); }
";" { return new PvToken(sym.SEMICOLON, yytext(), yyline, yycolumn); }
"," { return new PvToken(sym.COMMA, yytext(), yyline, yycolumn); }
"[" { return new PvToken(sym.LBRACKET, yytext(), yyline, yycolumn); }
"]" { return new PvToken(sym.RBRACKET, yytext(), yyline, yycolumn); }

// End of file
<<EOF>> { return new PvToken(sym.EOF, null, yyline, yycolumn); }

// Error handling
. { System.out.println("Unexpected character: " + yytext()); }