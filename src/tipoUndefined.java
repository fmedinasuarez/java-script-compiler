
public class tipoUndefined extends tipoResultado {
	
	public tipoUndefined(){
	}
	
	@Override
	public tipoExpresion getTipo() {
		return tipoExpresion.UNDEFINED;
	}
	
	@Override
	public String toString() {
		String res = ":UNDEFINED:sinValor";
		return res;
	}

}
