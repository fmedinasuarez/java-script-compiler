import java.util.ArrayList;

public class tipoArray extends tipoResultado {
	
	private tipoExpresion tipo_elementos;
	private ArrayList<tipoResultado> value;
	
	public tipoArray(tipoExpresion tipo, ArrayList<tipoResultado> value){
		this.tipo_elementos = tipo;
		this.value = value;
	}
	
	public tipoExpresion getTipo_elementos() {
		return tipo_elementos;
	}

	public void setTipo_elementos(tipoExpresion tipo_elementos) {
		this.tipo_elementos = tipo_elementos;
	}

	public ArrayList<tipoResultado> getValue() {
		return value;
	}

	public void setValue(ArrayList<tipoResultado> value) {
		this.value = value;
	}

	@Override
	public tipoExpresion getTipo() {
		return this.tipo_elementos;
	}

	@Override
	public String toString() {
		/*String res = ":ARRAY DE " +  this.tipo_elementos.toString() + ":"+this.value + " ";
		return res;*/
		return this.value.toString();
	}

}
