import java.util.ArrayList;
import java.util.Collections;


public class function_statement extends Statement {
	
	private ArrayList<Statement> sentencias;
	private ArrayList<Expression> argumentos; /** expresiones que son tipoExpresion.IDENTIFIER */
	private Object identificador;
	private boolean enScopePadre;
	private tipoResultado returnValue;
	private int linea;
	private int col;
	private String posicion;
	
	public function_statement(Object identificador, ArrayList<Expression> argumentos, ArrayList<Statement> sentencias,int linea, int col) {
		super(); 
		this.sentencias = sentencias;
		this.argumentos = argumentos;
		this.identificador = identificador;
		this.enScopePadre = false;
		this.returnValue = new tipoUndefined();
		this.linea = linea;
		this.col = col;
		this.setPosicion(" en la linea "+this.linea+" y columna "+this.col);
	}
	
	
	public tipoResultado getReturnValue() {
		return returnValue;
	}

	public ArrayList<Statement> getSentencias() {
		return sentencias;
	}

	public void setSentencias(ArrayList<Statement> sentencias) {
		this.sentencias = sentencias;
	}

	public ArrayList<Expression> getArgumentos() {
		return argumentos;
	}

	public void setArgumentos(ArrayList<Expression> argumentos) {
		this.argumentos = argumentos;
	}

	public Object getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Object identificador) {
		this.identificador = identificador;
	}

	public boolean getEnScopePadre() {
		return enScopePadre;
	}

	public void setEnScopePadre(boolean enScopePadre) {
		this.enScopePadre = enScopePadre;
	}

	public String getPosicion() {
		return posicion;
	}


	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}


	@Override
	public String toString() {
		String res = "";
		String bloque = "";
		for(Statement s : this.sentencias){
			bloque = bloque + s.toString() + " ";
		}
		res = "function "+ this.identificador.toString() + "(" + this.argumentos.toString() + "){ " +
		       bloque + "}";
		return res;
	}

	public tipoResultado validar() throws ParsingException, Exception {
		/** controlamos no se hayan parametros con mismo nombre */
		boolean iguales = false;
		ArrayList<String> list = new ArrayList<String>();
        for (Expression elem : this.argumentos) {
        	list.add(elem.getValue().toString());
        }
        for (String key : list) {
            iguales = iguales || Collections.frequency(list, key)>1;
        }
		
		if(iguales){
			throw new ParsingException(ParsingException.FUN_PARAM_DUPLICATE+this.identificador.toString()+this.posicion);
		}	
		return null;
	}
	
	public tipoResultado ejecutar(ArrayList<Variable> parametros) throws ParsingException, Exception {
		tipoResultado ret = new tipoUndefined();
		try{
			/** agrego los parámetros como variables locales */
			for(Variable v : parametros){
				this.scope_local.addVariable(v);
			}
			/** abro scope - ejecuto - cierro scope*/
			RecursosGlobales.getInstance().addScope(this.scope_local);
			ret = this.ejecutarSentencias(this.sentencias);
			RecursosGlobales.getInstance().removeScope();
		}catch(ParsingException e){
			if(e.getStatementType() == tipoStatement.RETURN){
				RecursosGlobales.getInstance().removeScope();
				ret = this.returnValue;
			}else{
				throw e;
			}
		}
		return ret;
	}

	@Override
	public tipoResultado ejecutar() throws ParsingException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setReturnValue(tipoResultado res) {
		this.returnValue = res;
	}

	
}
