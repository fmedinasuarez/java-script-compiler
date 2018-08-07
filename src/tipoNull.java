
public class tipoNull extends tipoResultado{

	private String value;
	
	public tipoNull(){
		this.value = "null";
	}
	
	public String getValue(){
		return this.value;
	}
	
	@Override
	public tipoExpresion getTipo() {	
		return tipoExpresion.NULL;
	}

	@Override
	public String toString() {
		/*String res = ":NULL:null";
		return res;*/
		return "null";
	}

}

