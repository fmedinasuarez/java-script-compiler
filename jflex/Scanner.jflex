import java.util.*;
import java_cup.runtime.*;

%%

%cup
%line
%unicode
%column
%state STRING1
%state STRING2


%class Scanner
%{
	private SymbolFactory sf;
	private StringBuffer string = new StringBuffer();

	public Scanner(java.io.InputStream r, SymbolFactory sf) {
		this(r);
		this.sf=sf;
	}

	private Symbol symbol(int type) {
		return new Symbol(type, yyline, yycolumn);
	}
	private Symbol symbol(int type, Object value) {
		return new Symbol(type, yyline, yycolumn, value);
	}
%}

%eofval{
    return symbol(sym.EOF);
%eofval}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f]

Comment = {TraditionalComment} | {EndOfLineComment}

TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?

Identifier = [a-zA-Z$_][a-zA-Z$_0-9]*

IntegerLiteral = (0 | [1-9][0-9]*)
FloatLiteral = (0 | [1-9][0-9]*)\.[0-9]+

%%

<YYINITIAL> {
	"if"	 {return symbol(sym.IF, "if");}
	"else"	 {return symbol(sym.ELSE, "else");}
	"for"	 {return symbol(sym.FOR, "for");}
	"("		 {return symbol(sym.PIZQ, "(");}
	")"		 {return symbol(sym.PDER, ")");}
	"["		 {return symbol(sym.PRIZQ, "[");}
	"]"		 {return symbol(sym.PRDER, "]");}
	"{"		 {return symbol(sym.LLAVEIZQ, "{");}
	"}"		 {return symbol(sym.LLAVEDER, "}");}
	"null"	 {return symbol(sym.NULL, "null");}
	"false"	 {return symbol(sym.FALSE, "false");}
	"true"	 {return symbol(sym.TRUE, "true");}
	"return" {return symbol(sym.RETURN, "return");}
	"function" {return symbol(sym.FUNCTION, "function");}
	"break"    {return symbol(sym.BREAK, "break");}
	"continue" {return symbol(sym.CONTINUE, "continue");}
	"++"	{return symbol(sym.MASMAS, "++");}
	"--"	{return symbol(sym.MENOSMENOS, "--");}
	"."	       {return symbol(sym.PUNTO, ".");}
	"var"      {return symbol(sym.VAR, "var");}
	";"        {return symbol(sym.PUNTOYCOMA, ";"); }
	","        {return symbol(sym.COMA, ","); }
	"+"		{return symbol(sym.SUMA, "+");}
	"-"		{return symbol(sym.RESTA, "-");}
	"*"		{return symbol(sym.MULT, "*");}	
	"/"		{return symbol(sym.DIV, "/");}
	"&&"	{return symbol(sym.AND, "&&");}
	"||"	{return symbol(sym.OR, "||");}
	"!"	    {return symbol(sym.NOT, "!");}
	"=="	{return symbol(sym.IGUAL, "==");}
	"="		{return symbol(sym.ASIGN, "=");}
	">"		{return symbol(sym.MAYOR, ">");}
	">="	{return symbol(sym.MAYORIGUAL, ">=");}
	"<"		{return symbol(sym.MENOR, "<");}
	"<="	{return symbol(sym.MENORIGUAL, "<=");}
	"!="	{return symbol(sym.DISTINTO, "!=");}
	\"                  { string.setLength(0); yybegin(STRING1); }
	'                   { string.setLength(0); yybegin(STRING2); }
	{Identifier}		{ return symbol(sym.IDENTIFIER, yytext());}	
	{IntegerLiteral}	{ return symbol(sym.INTEGER, yytext()); }
	{FloatLiteral} 		{ return symbol(sym.FLOAT, yytext()); }
	 
	{Comment}    { /* ignore */ }
	{WhiteSpace} { /* ignore */ }

	. {throw new ParsingException("ERROR Lexico: simbolo " + "\"" + yytext() + "\"" + " no esperado en la linea " + (yyline+1) + ", columna " + (yycolumn+1));}
}

<STRING1> {
  \"                             { yybegin(YYINITIAL); 
                                   return symbol(sym.STRING, 
                                   string.toString()); }
  [^\n\r\"\\]+                   { string.append( yytext() ); }
  \\t                            { string.append('\t'); }
  \\n                            { string.append('\n'); }
  \\'                            { string.append('\''); }
  \\r                            { string.append('\r'); }
  \\\"                           { string.append('\"'); }
  \\\\                             { string.append('\\'); }
  . {throw new ParsingException("ERROR Lexico: simbolo " + "\"" + yytext() + "\"" + " no esperado en la linea " + (yyline+1) + ", columna " + (yycolumn+1));}
}

<STRING2> {
  \'                            { yybegin(YYINITIAL); 
                                  return symbol(sym.STRING, 
                                  string.toString()); }
  [^\n\r\'\\]+                   { string.append( yytext() ); }
  \\t                            { string.append('\t'); }
  \\n                            { string.append('\n'); }
  \\'                            { string.append('\''); }
  \\r                            { string.append('\r'); }
  \\\"                           { string.append('\"'); }
  \\\\                           { string.append('\\'); }
  . {throw new ParsingException("ERROR Lexico: simbolo " + "\"" + yytext() + "\"" + " no esperado en la linea " + (yyline+1) + ", columna " + (yycolumn+1));}
}

