
@SuppressWarnings("serial")
public class ParsingException extends RuntimeException {
	
	private tipoStatement statementType;
	
	static final String VAR_NO_VALUE = "Ha ocurrido un error sem�ntico: no hay valor asignado para la variable ";
	static final String ID_NO_VALUE = "Ha ocurrido un error sem�ntico: no se ha encontrado definido el identificador ";
	static final String OUT_OF_RANGE = "Ha ocurrido un error sem�ntico: al intentar acceder a una posici�n fuera de rango ";
	static final String EXP_NO_INT = "Ha ocurrido un error sem�ntico: tipo inv�lido para expresi�n, se esperaba tipo integer en ";
	static final String ARRAY_NO_DEF = "Ha ocurrido un error sem�ntico: no hay un array definido para el identificador ";
	static final String ARRAY_ELEM_DIF_TYPE = "Ha ocurrido un error sem�ntico: tipos diferentes para elementos del array en ";
	static final String FUN_CANT_PARAM = "Ha ocurrido un error sem�ntico: cantidad incorrecta de par�metros para la funci�n: ";
	static final String PLUS_TYPE = "Ha ocurrido un error sem�ntico: el operador '+' debe utilizarse con tipos INTEGER o FLOAT";
	static final String MINUS_TYPE = "Ha ocurrido un error sem�ntico: el operador '-' debe utilizarse con tipos INTEGER o FLOAT";
	static final String MULT_TYPE = "Ha ocurrido un error sem�ntico: el operador '*' debe utilizarse con tipos INTEGER o FLOAT";
	static final String DIV_TYPE = "Ha ocurrido un error sem�ntico: el operador '/' debe utilizarse con tipos INTEGER o FLOAT ";
	static final String AND_TYPE = "Ha ocurrido un error sem�ntico: el operador '&&' debe utilizarse con tipos BOOLEAN ";
	static final String OR_TYPE = "Ha ocurrido un error sem�ntico: el operador '||' debe utilizarse con tipos BOOLEAN ";
	static final String MAYOR_TYPE = "Ha ocurrido un error sem�ntico: el operador '>' debe utilizarse con tipos INTEGER o FLOAT ";
	static final String MINOR_TYPE = "Ha ocurrido un error sem�ntico: el operador '<' debe utilizarse con tipos INTEGER o FLOAT ";
	static final String MAYOR_EQUAL_TYPE = "Ha ocurrido un error sem�ntico: el operador '>=' debe utilizarse con tipos INTEGER o FLOAT ";
	static final String MINOR_EQUAL_TYPE = "Ha ocurrido un error sem�ntico: el operador '<=' debe utilizarse con tipos INTEGER o FLOAT ";
	static final String EQUAL_TYPE = "Ha ocurrido un error sem�ntico: el operador '==' debe utilizarse con operandos del mismo tipo ";
	static final String NO_EQUAL_TYPE = "Ha ocurrido un error sem�ntico: el operador '!=' debe utilizarse con operandos del mismo tipo ";
	static final String NOT_TYPE = "Ha ocurrido un error sem�ntico: el operador '!' debe utilizarse con tipos BOOLEAN ";
	static final String PLUS_PLUS_TYPE = "Ha ocurrido un error sem�ntico: el operador '++' debe utilizarse con tipos INTEGER o FLOAT ";
	static final String MINUS_MINUS_TYPE = "Ha ocurrido un error sem�ntico: el operador '--' debe utilizarse con tipos INTEGER o FLOAT ";
	static final String MINUS_PLUS_TYPE = "Ha ocurrido un error sem�ntico: los operadores '++'y '--' deben utilizarse con identificadores definidos ";
	static final String IF_EXP = "Ha ocurrido un error sem�ntico: tipo de expresi�n debe ser BOOLEAN al ejecutar una sentencia IF";
	static final String BREAK = "Ha ocurrio un error sem�ntico: sentencia BREAK mal utilizada";
	static final String FOR_ACT = "Ha ocurrido un error sem�ntico: la actualizaci�n en sentencia FOR debe retornar un valor INTEGER";
	static final String FOR_EXP = "Ha ocurrido un error sem�ntico: la expresi�n asociada a la condici�n de la sentencia FOR debe retornar un valor BOOELAN";
	static final String FOR_INI = "Ha ocurrido un error sem�ntico: la inicializacion de la sentencia FOR debe retornar un valor INTEGER";
	static final String CONTINUE = "Ha ocurrido un error sem�ntico: sentencia CONTINUE mal utilizada";
	static final String RETURN = "Ha ocurrido un error sem�ntico: sentencia return no se encuentra dentro del cuerpo de una funcion";
	static final String FUN_NO_DEF = "Ha ocrurrido un error, no se encuentra definida la funci�n ";
	static final String UNDEFINED_ASIGN = "Ha ocurrido un error sem�ntico: no se puede asignar una funcion sin retorno a una variable,";
	static final String FUN_DEF = "Ha ocurrido un error sem�ntico: ya se encuentra definida la funci�n ";
	static final String STM_DEF = "Ha ocurrido un error sem�ntico: sentencia invalida,";
	static final String DIV_BY_CERO = "Ha ocurrido un error sem�ntico: division por cero";
	static final String FUN_APPLY = "Ha ocurrido un error sem�ntico: no es correcto el parametro a el cual fue aplicada la funcion ";
	static final String FUN_NOT_APPLY = "Ha ocurrido un error sem�ntico: no debe aplicarse a ningun parametro la funcion ";
	static final String FUN_TYPE = "Ha ocurrido un error sem�ntico: alguno/s de los parametros son incorrectos, en la funcion ";
	static final String ARRAY_EMPTY = "Ha ocurrido un error sem�ntico: arreglo vacio, no se puede aplicar la funcion ";
	static final String FUN_PARAM_DUPLICATE = "Ha ocurrido un error sem�ntico: uno o mas parametros estan duplicados en la declaracion de la funcion ";
	
	public ParsingException(String message){
		super(message);
		this.statementType = tipoStatement.NONE;
	}
	
	public ParsingException(String message, tipoStatement tipo){
		super(message);
		this.statementType = tipo;
	}
	
	public tipoStatement getStatementType(){
		return this.statementType;
	}


}
