import java.util.ArrayList;


public class block_statement extends Statement {
	
	private ArrayList<Statement> sentencias;
	
	public block_statement() {
		super();
		this.sentencias = new ArrayList<Statement>();
	}
	
	public block_statement(ArrayList<Statement> sentencias) {
		this.sentencias = sentencias;
	}

	public ArrayList<Statement> getSentencias() {
		return sentencias;
	}

	public void setSentencias(ArrayList<Statement> sentencias) {
		this.sentencias = sentencias;
	}
	
	public block_statement addStatement(Statement statement) {
		this.sentencias.add(statement);
		return this;
	}

	@Override
	public String toString() {
		String res = null;
		try {		
			String bloque ="";
			for(Statement s : this.sentencias){
				bloque = bloque + s.toString();
			}		
			res = "block_statement\nBloque: "+ bloque + "\n";		
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public tipoResultado ejecutar() throws ParsingException, Exception {
		this.ejecutarSentencias(this.sentencias);
		return null;
	}

}
