
public class tipoInteger extends tipoResultado{
	
	private int value;
	
	public tipoInteger(int value){
		this.value = value;
	}
	
	@Override
	public tipoExpresion getTipo() {
		
		return tipoExpresion.INTEGER;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		/*String res = ":INTEGER:" + this.value + " ";
		return res;*/
		return ((Integer)this.value).toString();
	}

}

