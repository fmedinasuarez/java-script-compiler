
public class return_statement extends Statement{
	
	private Expression expresion;
	private int linea;
	private int col;
	private String posicion;
	
	public return_statement(Expression expresion, int linea, int col){
		super();
		this.expresion = expresion;
		this.linea = linea;
		this.col = col;
		this.setPosicion(" en la linea "+this.linea+" y columna "+this.col);
	}
	
	public Expression getExpresion() {
		return expresion;
	}

	public void setExpresion(Expression expresion) {
		this.expresion = expresion;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	@Override
	public String toString() {
		String res = "return_statement " + this.expresion.toString();
		return res;
	}

	@Override
	public tipoResultado ejecutar() throws ParsingException, Exception {
		tipoResultado res = this.expresion.evaluar();
		function_statement fun = RecursosGlobales.getInstance().getFuncionDelStackScopes();
		if(fun != null){
			fun.setReturnValue(res);
		}else{
			/**si no hay una funcion en el scope, el return no esta dentro de una funcion*/
			throw new ParsingException(ParsingException.STM_DEF+this.posicion);
		}
		throw new ParsingException(ParsingException.RETURN.concat(this.posicion),tipoStatement.RETURN);
		//return null;
	}

}
