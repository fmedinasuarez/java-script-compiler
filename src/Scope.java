import java.util.HashMap;
import java.util.Map;


public class Scope {
	
	private Statement statement;
	private Map<String,Variable> variablesLocales;
	
	public Scope(Statement stm){
		this.statement = stm;
		this.variablesLocales = new HashMap<String,Variable>();
	}
	
	public Scope(Scope scope){
		this.statement = scope.getStatement();
		this.variablesLocales = new HashMap<String,Variable>(scope.getVariablesLocales());
	}
	
	public Scope(Statement stm, Scope scope_padre){
		this.statement = stm;
		this.variablesLocales = new HashMap<String,Variable>(scope_padre.getVariablesLocales());
	}
	
	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}

	public Map<String, Variable> getVariablesLocales() {
		return variablesLocales;
	}

	public void setVariablesLocales(Map<String, Variable> variablesLocales) {
		this.variablesLocales = variablesLocales;
	}

	public String toString(){
		String scopeVariables = " ";
		String scope = "";
		if(this.variablesLocales.isEmpty()){
			scope = "Scope Vacio";
		}else{
			if(!this.variablesLocales.isEmpty())
				for(Variable v : this.variablesLocales.values()) {
				    scopeVariables = scopeVariables + v.toString();
				}
			scope = scope + scopeVariables;
		}
		String ret = "statement: " + this.statement.toString() + "\nscope variables: " + scope;
		return ret;
	}
	
	public void addVariable(Variable var){
		this.variablesLocales.put(var.getIdentifier().toString(),var);
	}
	
	public void replaceVariable(Variable var){
		this.variablesLocales.replace(var.getIdentifier().toString(),var);
	}
	
	public boolean variableEnScope(String name){
		return this.variablesLocales.containsKey(name);
	}
	
	public Variable getVariableEnScope(String name){
		return this.variablesLocales.get(name);
	}
	
	/**
	 * COPIA LIMPIA DEL SCOPE DEL PADRE
	 * @param scope_padre
	 */
	public void copiarScopePadre(Scope scope_padre){
		this.variablesLocales = new HashMap<String,Variable>(scope_padre.getVariablesLocales());
	}

	public boolean statementIsFunction() {
		return (this.statement instanceof function_statement);
	}
}

