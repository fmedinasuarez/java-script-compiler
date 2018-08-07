
public class break_statement extends Statement{
	
	private int linea;
	private int col;
	private String posicion;
	
	public break_statement(int linea,int col){
		super();
		this.linea = linea;
		this.col = col;
		this.setPosicion(" en la linea "+this.linea+" y columna "+this.col);
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	@Override
	public String toString() {
		String res = "break_statement ";
		return res;
	}

	@Override
	public tipoResultado ejecutar() throws ParsingException, Exception {
		throw new ParsingException(ParsingException.BREAK.concat(this.posicion),tipoStatement.BREAK);
		//return null;
	}

	
	
}
