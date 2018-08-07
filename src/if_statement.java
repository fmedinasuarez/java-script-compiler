import java.util.ArrayList;


public class if_statement extends Statement{
	
	private Expression condicion;
	private ArrayList<Statement> sentencias;
	private ArrayList<Statement> sentenciasElse;
	private boolean tieneElse;
	
	public if_statement(Expression condicion, ArrayList<Statement> sentencias) {
		super();
		this.condicion = condicion;
		this.sentencias = sentencias;
		this.tieneElse =false;
	}
	
	public if_statement(Expression condicion, ArrayList<Statement> sentencias, ArrayList<Statement> sentenciasElse ){
		super();
		this.condicion = condicion;
		this.sentencias = sentencias;
		this.sentenciasElse = sentenciasElse;
		this.tieneElse = true;
	}

	public Expression getCondicion() {
		return condicion;
	}

	public void setCondicion(Expression condicion) {
		this.condicion = condicion;
	}

	public ArrayList<Statement> getSentencias() {
		return sentencias;
	}

	public void setSentencias(ArrayList<Statement> sentencias) {
		this.sentencias = sentencias;
	}

	@Override
	public String toString() {
		String res = null;
		String bloque ="";
		for(Statement s : this.sentencias){
			bloque = bloque + s.toString();
		}
		
		res = "if_statement:" + "condicion: " + this.condicion.toString() +  " :Bloque: "+ bloque + "\n";
	
		if(this.tieneElse){
			String bloque_else = "";
			for(Statement s : this.sentenciasElse)
				bloque_else = bloque_else + s.toString();
			res = res + "else_statement\n" + bloque_else + "\n";
		}
		return res;
	}

	@Override
	public tipoResultado ejecutar() throws ParsingException, Exception {	
		tipoResultado res = condicion.evaluar();	
		if(res.getTipo() == tipoExpresion.BOOLEAN){			
			ArrayList<Statement> bloque = null;
			boolean run =  true;
			
			/** Si la condicion es VERDADERA */
			if(((tipoBoolean)res).getValue()){
				bloque = this.sentencias;
				
			/** Si la condicion es FALSA y tiene ELSE */
			}else if(this.tieneElse){
				bloque = this.sentenciasElse;
				
			/** Si la condicion es FALSA y NO tiene ELSE*/
			}else{
				run = false;
			}
			if(run){
				this.ejecutarSentencias(bloque);
			}
		/** ERROR SEMANTICO */
		}else{
			throw new ParsingException(ParsingException.IF_EXP+condicion.getPosicion());
		}
		return null;
	}

}
