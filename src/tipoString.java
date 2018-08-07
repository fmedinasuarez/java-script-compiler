public class tipoString extends tipoResultado{
	
	private String value;
	
	public tipoString(String value){
		this.value = value;
	}
	
	@Override
	public tipoExpresion getTipo() {		
		return tipoExpresion.STRING;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		/*String res = ":STRING:" + this.value + " ";
		return res;*/
		return this.value;
	}
	
}

