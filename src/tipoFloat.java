
public class tipoFloat extends tipoResultado{
	
	private float value;
	
	public tipoFloat(float value){
		this.value = value;
	}
	
	@Override
	public tipoExpresion getTipo() {
		
		return tipoExpresion.FLOAT;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		/*String res = ":FLOAT:" + this.value + " ";
		return res;*/
		return ((Float)this.value).toString();
	}
}

