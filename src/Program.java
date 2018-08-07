import java.util.ArrayList;
import java.util.Iterator;


public class Program {
	
	public ArrayList<Statement> statements;

	public Program() {
		this.statements = new ArrayList<Statement>();
	}
	
	public ArrayList<Statement> getStatements() {
		return statements;
	}
	
	public void setArguments(ArrayList<Statement> statements) {
		this.statements = statements;
	}
	
	public Program addStatement(Statement statement) {
		this.statements.add(statement);
		return this;
	}
	
	public void printStatements() {
		Iterator<Statement> it = this.getStatements().iterator();
		while(it.hasNext()){
			System.out.println(it.next().toString());
		}
	}
	
	public String printRecursosGlobales(){
		return RecursosGlobales.getInstance().toString();
	}
	
	public int run() throws ParsingException, Exception{
		int stm_nro = 1;
		tipoResultado stm_res = null;
		
		//System.out.println("\n>> EJECUCION");
		
		for(Statement stm :  this.statements){
			/** VARIABLE GLOBAL - SE PISAN SIEMPRE */
			if(stm instanceof Variable){
				RecursosGlobales.getInstance().addVariable((Variable)stm);	
				stm_res = stm.ejecutar();	
			}
			/** SENTENCIA QUE ABRE UN SCOPE */
			else if(stm instanceof if_statement || stm instanceof for_statement || stm instanceof block_statement){
				RecursosGlobales.getInstance().addScope(stm.getScope_local());
				stm_res = stm.ejecutar();
				//System.out.println(printRecursosGlobales());
				RecursosGlobales.getInstance().removeScope();
			}
			/** FUNCION GLOBAL - NO SE PISAN */
			else if(stm instanceof function_statement){
				if(RecursosGlobales.getInstance().FuncionEnFuncionesGlobales(((function_statement)stm).getIdentificador().toString())){
					throw new ParsingException(ParsingException.FUN_DEF.concat(((function_statement)stm).getIdentificador().toString())+((function_statement)stm).getPosicion());
				}
				else{
					RecursosGlobales.getInstance().addFuncion((function_statement)stm);
					stm_res = ((function_statement) stm).validar();
				}
			}
			else if(stm instanceof Expression){
				if(((Expression)stm).getTipo() == tipoExpresion.FUNCTION_CALL ||
					((Expression)stm).getTipo() == tipoExpresion.FUNCTION_PREDEF ||
						(((Expression)stm).getValue().toString() == "++") ||
						(((Expression)stm).getValue().toString() == "--")){
					stm_res = ((Expression)stm).evaluar();
				}
				else {
					throw new ParsingException(ParsingException.STM_DEF+((Expression)stm).getPosicion());
				}
			/**return, continue y break no pueden ser sentencias del programa principal*/
			}else if(stm instanceof return_statement){
				throw new ParsingException(ParsingException.STM_DEF+((return_statement)stm).getPosicion());
			}
			else if(stm instanceof continue_statement){
				throw new ParsingException(ParsingException.STM_DEF+((continue_statement)stm).getPosicion());
			}
			else {
				throw new ParsingException(ParsingException.STM_DEF+((break_statement)stm).getPosicion());
			}
			
			//System.out.println(RecursosGlobales.getInstance().toString());
			//System.out.println("\nsentencia " + stm_nro + ":\t" + stm.toString());
			//if(stm instanceof Variable)
			//	System.out.println("resultado:\t" + stm_res.toString()+"\n");
			//stm_nro++;
		}
		return 0;
	}

}