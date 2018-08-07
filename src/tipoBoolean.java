
public class tipoBoolean extends tipoResultado{
	
	private boolean value;
	
	public tipoBoolean(boolean value){
		this.value = value;
	}
	
	@Override
	public tipoExpresion getTipo() {
		
		return tipoExpresion.BOOLEAN;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		/*String aux = null;
		if(this.value)
			aux = "true";
		else
			aux = "false";
		String res = ":BOOLEAN:" + aux + " ";
		return res;*/
		return ((Boolean)this.value).toString();
	}
}

