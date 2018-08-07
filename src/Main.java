
import java.io.FileInputStream;


import java_cup.runtime.Symbol;

public class Main {

	public static void main(String[] args) {
		try {
			FileInputStream file = new FileInputStream(args[0]);

			parser parser = new parser(new Scanner(file));
			Symbol topsym = parser.parse();

			Program prog = (Program) topsym.value;
			//prog.printStatements();
			//System.out.println(">> RECURSOS ANTES RUN\n" + prog.printRecursosGlobales());
			prog.run();
			//System.out.println("\n>> RECURSOS DESPUES RUN\n" + prog.printRecursosGlobales());
			
		} catch (ParsingException ex) {
			System.out.println(ex.getMessage());
			//ex.printStackTrace();
		}
		 catch (Exception ex) {
			 System.out.println("Ocurrio un error inesperado. Exception." + ex.getMessage());
			 //ex.printStackTrace();
		}
		//System.out.println(exptext);
	}
	
}
