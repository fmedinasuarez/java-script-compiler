import java.util.ArrayList;


public abstract class Statement {
	
	protected Scope scope_local;
	
	public Statement() {
		this.scope_local = new Scope(this);
	}
	
	public Scope getScope_local() {
		return scope_local;
	}
	
	public tipoResultado ejecutarSentencias(ArrayList<Statement> bloque) throws ParsingException, Exception{
		tipoResultado ret = new tipoUndefined();
		
		for(Statement stm : bloque){
			tipoResultado stm_result = null;
			/** SI ES VARIABLE - PUEDE QUE ACTUALICE SCOPE LOCAL  */
			if(stm instanceof Variable){							
				/** SI SE DEFINIO CON 'VAR' ES LOCAL SEGURO  -  sino en el 'ejecutar' de la variable se encarga*/
				if(((Variable)stm).isVarDef()){
					this.scope_local.addVariable((Variable)stm); 
				}
				stm_result = stm.ejecutar();
				//System.out.println(((Variable) stm).getIdentifier().toString() +":\tresultado:\t" + stm_result.toString());
			}				
			/** SI ABRE UN SCOPE NUEVO - LE PASO EL SCOPE LOCAL */
			else if(stm instanceof if_statement || stm instanceof for_statement || stm instanceof block_statement){
				Scope scope_padre = RecursosGlobales.getInstance().getScope().peek();
				stm.getScope_local().copiarScopePadre(scope_padre);
				RecursosGlobales.getInstance().addScope(stm.getScope_local());		
				stm_result = stm.ejecutar();
				RecursosGlobales.getInstance().removeScope();
			}
			else if(stm instanceof break_statement){
				stm.ejecutar();
			}
			else if(stm instanceof continue_statement){
				stm.ejecutar();
			}
			else if(stm instanceof return_statement){
				stm_result = stm.ejecutar();
			}
			else if(stm instanceof Expression){
				if(((Expression)stm).getTipo() == tipoExpresion.FUNCTION_CALL ||
					((Expression)stm).getTipo() == tipoExpresion.FUNCTION_PREDEF ||
						(((Expression)stm).getValue().toString() == "++") ||
						(((Expression)stm).getValue().toString() == "--")){
					stm_result = ((Expression)stm).evaluar();
				}
				else {
					throw new ParsingException(ParsingException.STM_DEF+((Expression)stm).getPosicion());
				}
			}
		 }
		 return ret;
	}
	
	public abstract String toString();
	
	public abstract tipoResultado ejecutar() throws ParsingException, Exception;
	
}
