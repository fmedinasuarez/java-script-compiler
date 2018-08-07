
public class Variable extends Statement {
	
	private Object identifier;
	private Expression exp;
	private boolean varDef;
	private boolean global;
	private tipoResultado value;

	public Variable(Object identifier, Expression exp, boolean varDef) {
		this.identifier = identifier;
		this.exp = exp;
		this.varDef = varDef;
		this.global = false;
		value = new tipoUndefined();
	}
	
	public Variable(Variable v) {
		this.identifier = v.identifier;
		this.exp = v.exp;
		this.varDef = v.varDef;
		this.global = v.global;
		this.value = v.value;
	}

	public Object getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Object identifier) {
		this.identifier = identifier;
	}

	public Expression getExp() {
		return exp;
	}

	public void setExp(Expression exp) {
		this.exp = exp;
	}

	public boolean isVarDef() {
		return varDef;
	}
	
	public void setGlobal(boolean global){
		this.global = global;
	}
	
	public tipoResultado getValue() {
		return value;
	}

	public void setValue(tipoResultado value) {
		this.value = value;
	}

	public String toString() {
		String res = this.identifier.toString();
		if (this.exp.getTipo() != tipoExpresion.NULL) {
			res = res + this.value.toString();
		}else {
			res = res + "sinExpresion";
		}
		return res;
	}

	@Override
	public tipoResultado ejecutar() throws ParsingException, Exception{
		tipoResultado ret = new tipoUndefined();
		/** SI TIENE EXPR ASIGNADA EVALUO */
		if(this.exp.getTipo() != tipoExpresion.UNDEFINED){
			ret = exp.evaluar(); /** DEBE EVALUAR TIPO NULL */
			if(ret.getTipo() != tipoExpresion.UNDEFINED)
				this.setValue(ret);
			else
				throw new ParsingException(ParsingException.UNDEFINED_ASIGN+exp.getPosicion());
		}
		RecursosGlobales rg = RecursosGlobales.getInstance();
		/** Busco variable en scope LOCAL */
		if(rg.variableEnScopeLocal(this.identifier.toString())){
			rg.replaceVariableScope(this);
		}
		/** Busco variable en Variables GLOBALES */
		else if(rg.variableEnVariablesGlobales(this.identifier.toString())){
			rg.replaceVariableGlobal(this);
		}
		/** Sino encontre es una variable GLOBAL NUEVA */
		else{
			rg.addVariable(this);
		}
		return ret;
	}

}
