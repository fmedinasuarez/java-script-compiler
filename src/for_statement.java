import java.util.ArrayList;


public class for_statement extends Statement{
	private ArrayList<Statement> sentencias;
	private Expression condicion;
	private Variable inicializacion;
	private Variable actualizacion; /** TIENE QUE SER expresion UNARIA con ++ ó -- */
	
	public for_statement(Variable inicializacion, Expression condicion, Variable actualizacion, ArrayList<Statement> sentencias) {
		super();
		this.sentencias = sentencias;
		this.condicion = condicion;
		this.inicializacion = inicializacion;
		this.actualizacion = actualizacion;
	}
	
	@Override
	public String toString() {
		String res = null;				
		String bloque ="";
		for(Statement s : this.sentencias){
			bloque = bloque + s.toString();
		}	
		res = "for_statement\n" + "inicializacion: " + this.inicializacion.toString()
			 +"condicion: " + this.condicion.toString() +  "actualizacion: "+ this.actualizacion.toString()
			 +"\nBloque: "+ bloque + "\n";			
		return res;
	}

	@Override
	public tipoResultado ejecutar() throws ParsingException, Exception {
		/** INICIALIZACION  */
		if(this.inicializacion.getExp().getTipo() != tipoExpresion.NULL){
			if(this.inicializacion.isVarDef()){
				this.scope_local.addVariable(this.inicializacion);
			}
			this.inicializacion.ejecutar();
		}
		/** CONDICION NO NULA*/
		if(this.condicion.getTipo() != tipoExpresion.NULL) {
			tipoResultado res_cond = this.condicion.evaluar();
			if(res_cond.getTipo() == tipoExpresion.BOOLEAN){
				
				/** ACTUALIZACION NO NULA */
				if(this.actualizacion.getExp().getTipo() != tipoExpresion.NULL) {
					if(this.actualizacion.isVarDef()){
						this.scope_local.addVariable(this.actualizacion);
					}
					this.actualizacion.ejecutar();
						
					/** SIMULACION FOR */
					boolean exp = ((tipoBoolean)res_cond).getValue();
					while(exp){
						try{
							this.ejecutarSentencias(this.sentencias);
							exp = ((tipoBoolean)this.condicion.evaluar()).getValue();
							if(exp)
								this.actualizacion.ejecutar();
						}catch (ParsingException e){
							if(e.getStatementType() == tipoStatement.BREAK ){
								exp = false;
							}else if(e.getStatementType() == tipoStatement.CONTINUE){
								this.actualizacion.ejecutar();
							}else{
								throw e;
							}
						}
					}
				}
				else{ /**ACTUALIZACION NULA*/
					boolean exp = ((tipoBoolean)res_cond).getValue();
					while(exp){
						try{
							this.ejecutarSentencias(this.sentencias);
							exp = ((tipoBoolean)this.condicion.evaluar()).getValue();
						}catch (ParsingException e){
							if(e.getStatementType() == tipoStatement.BREAK ){
								exp = false;
							}else if(e.getStatementType() == tipoStatement.CONTINUE){
								/**no hago nada, la actualizacion es nula*/
							}else{
								throw e;
							}
						}
					}
				}
			/** ERROR SEMANTICO */
			}else{
				throw new ParsingException(ParsingException.FOR_EXP+condicion.getPosicion());
			}
		}
		else{ /**CONDICION NULA*/
			/**ACTUALIZACION NO NULA*/
			if(this.actualizacion.getExp().getTipo() != tipoExpresion.NULL) {
				if(this.actualizacion.isVarDef()){
					this.scope_local.addVariable(this.actualizacion);
				}
				this.actualizacion.ejecutar();
					
				/** SIMULACION FOR */
				boolean cond = true;/**for infinito, solo lo para el break*/
				while(cond){
					try{
						this.ejecutarSentencias(this.sentencias);
						this.actualizacion.ejecutar();
					}catch (ParsingException e){
						if(e.getStatementType() == tipoStatement.BREAK ){
							cond = false;
						}else if(e.getStatementType() == tipoStatement.CONTINUE){
							this.actualizacion.ejecutar();
						}else{
							throw e;
						}
					}
				}
			} /**ACTUALIZACION NULA*/
			else{
				boolean cond = true;/**for infinito, solo lo para un break*/
				while(cond){
					try{
						this.ejecutarSentencias(this.sentencias);
					}catch (ParsingException e){
						if(e.getStatementType() == tipoStatement.BREAK ){
							cond = false;
						}else if(e.getStatementType() == tipoStatement.CONTINUE){
							/**no hago nada, la actualizacion es nula*/
						}else{
							throw e;
						}
					}
				}
			}
		}
			
		return null;
	}

}
