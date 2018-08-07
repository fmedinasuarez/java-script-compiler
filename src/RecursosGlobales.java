import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class RecursosGlobales {
	
	private static RecursosGlobales instance;
	private Stack<Scope> scope;
	private Map<String,Variable> variables;
	private Map<String, function_statement> funciones;
	
	private RecursosGlobales() {
		this.variables = new HashMap<String,Variable>();
		this.funciones = new HashMap<String,function_statement>();
		this.scope = new Stack<Scope>();
	}
	
	public static RecursosGlobales getInstance(){
		if(instance == null)
			instance = new RecursosGlobales();
		return instance;
	}
	 
	public Stack<Scope> getScope() {
		return scope;
	}

	public void setScope(Stack<Scope> scope) {
		this.scope = scope;
	}

	public Map<String, Variable> getVariables() {
		return variables;
	}

	public void setVariables(Map<String, Variable> variables) {
		this.variables = variables;
	}
	
	public Map<String, function_statement> getFunciones() {
		return funciones;
	}

	public void setFunciones(Map<String, function_statement> funciones) {
		this.funciones = funciones;
	}
	
	public void addScope(Scope scope){
		this.scope.push(scope);
	}
	
	public void removeScope() {
		if(!this.scope.empty()){
			this.scope.pop();
		}
	}
	
	public String toString(){
		String ret = "\n>> RECURSOS GLOBALES\n\nVARIABLES: ";
		
		if(this.variables.isEmpty()){
			ret = ret + " no hay.";
		}else{
			for(Variable v : this.variables.values()){
				ret = ret + v.toString();
			}
		}
		
		ret = ret + "\n\nFUNCIONES:\n";
		if(this.funciones.isEmpty()){
			ret = ret + " no hay.";
		}else{
			for(function_statement fun : this.funciones.values()){
				ret = ret + fun.toString();
			}
		}
		
		ret = ret + "\n\nSTACK DE SCOPES(orden FI):\n";
		if(this.scope.empty()){
			ret = ret + "es vacio";
		}else{
			int s_num = 1;
			for(Scope s : this.scope){
				ret = ret + "scope " + s_num + "\n" +s.toString() + "\n";
				s_num++;
			}
		}
		return ret;
	}

	public void addVariable(Variable variable) {
		this.variables.put(variable.getIdentifier().toString(),variable);
		variable.setGlobal(true);
	}
	
	public void replaceVariableScope(Variable var){
		Scope scope_top = this.scope.peek();
		scope_top.replaceVariable(var);
	}
	
	public void replaceVariableGlobal(Variable var){
		this.variables.replace(var.getIdentifier().toString(),var);
	}
	
	public boolean variableEnScopeLocal(String name) {
		boolean ret = false;
		if(!this.scope.empty()){
			ret = this.scope.peek().variableEnScope(name);
		}
		return ret;
	}
	
	public boolean variableEnVariablesGlobales(String name){
		return this.variables.containsKey(name);
	}
	
	public Variable getVariableGlobal(String name){
		return this.variables.get(name);
	}
	
	public Variable getVariableEnScopeLocal(String name){
		Variable var = null;
		if(!this.scope.empty()){
			var = this.scope.peek().getVariableEnScope(name);
		}
		return var;
	}

	public void addFuncion(function_statement fun) {
		this.funciones.put(fun.getIdentificador().toString(),fun);
	}
	
	public boolean FuncionEnFuncionesGlobales(String name){
		return this.funciones.containsKey(name);
	}
	
	public function_statement getFuncionGlobal(String name){
		return this.funciones.get(name);
	}

	public function_statement getFuncionDelStackScopes() {
		function_statement fun = null;
		boolean encontre = false;
		Stack<Scope> auxiliar = new Stack<Scope>();
		
		if(!this.scope.isEmpty()){
			while(!encontre && !this.scope.isEmpty()){
				if(!encontre && !this.scope.isEmpty()){
					Scope scope_aux = this.scope.peek();
					if(scope_aux.statementIsFunction()){
						fun = (function_statement)scope_aux.getStatement();
						encontre = true;
					}else{
						auxiliar.push(scope_aux);
						this.scope.pop();
					}
				}
			}
			if(!auxiliar.isEmpty()){
				while(!auxiliar.isEmpty()){
					Scope aux = auxiliar.peek();
					this.scope.push(aux);
					auxiliar.pop();
				}
			}
		}
		return fun;
	}

}

