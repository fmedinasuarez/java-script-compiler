import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class predefinedFunction {
	
	private Object idFunction;
	private tipoResultado argument;
	private List<Expression> arguments;
	private int linea;
	private int col;
	String posicion;

	public predefinedFunction(Object idFunction,tipoResultado argument, List<Expression> arguments, int linea, int col) {
		this.idFunction = idFunction;
		this.argument = argument;
		this.arguments = arguments;
		this.linea = linea;
		this.col = col;
		this.posicion = " de la linea "+this.linea+" columna "+this.col;
	}
	
	public Object getIdFunction() {
		return idFunction;
	}

	public void setIdFunction(Object idFunction) {
		this.idFunction = idFunction;
	}

	public List<Expression> getArguments() {
		return arguments;
	}

	public void setArguments(ArrayList<Expression> arguments) {
		this.arguments = arguments;
	}

	public tipoResultado getArgument() {
		return argument;
	}

	public void setArgument(tipoResultado argument) {
		this.argument = argument;
	}
	
	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public tipoResultado length(){
		tipoResultado res = new tipoUndefined();
		if(this.arguments.isEmpty()){
			if(this.argument instanceof tipoArray) {
				res = new tipoInteger(((tipoArray)this.argument).getValue().size());
			}
			else if(this.argument.getTipo() == tipoExpresion.STRING) {
				res = new tipoInteger(((tipoString)this.argument).getValue().length());
			}
			else {
				throw new ParsingException(ParsingException.FUN_APPLY+"length,"+this.posicion);
			}
		}
		else{
			throw new ParsingException(ParsingException.FUN_CANT_PARAM+"length,"+this.posicion);
		}
		return res;
	}
	
	public tipoResultado writeline() throws ParsingException, Exception{
		if(this.arguments.size() == 1){
			if(this.argument==null) {
				System.out.print(this.arguments.get(0).evaluar().toString());
			}
			else {
				throw new ParsingException(ParsingException.FUN_NOT_APPLY+"writeline,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_CANT_PARAM+"writeline,"+this.posicion);
		}
		
		return new tipoUndefined();
			
	}
	
	public tipoResultado readline() throws Exception{
		tipoResultado res = new tipoUndefined();
		if(this.arguments.size() == 0){
			if(this.argument==null) {
				InputStreamReader in=new InputStreamReader(System.in);
			    BufferedReader br=new BufferedReader(in);
			    res= new tipoString(br.readLine());
			}
			else {
				throw new ParsingException(ParsingException.FUN_NOT_APPLY+"readline,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_CANT_PARAM+"readline,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado concat() throws ParsingException, Exception {
		tipoResultado res = new tipoUndefined();
		if(this.arguments.size() == 1){
			if(this.argument.getTipo() == tipoExpresion.STRING) {
				tipoResultado tipoParam = this.arguments.get(0).evaluar();
				if(tipoParam.getTipo() == tipoExpresion.STRING){
					res = new tipoString(((tipoString)this.argument).getValue().concat(((tipoString)tipoParam).getValue()));
				}
				else{
					throw new ParsingException(ParsingException.FUN_TYPE+"concat,"+this.posicion);
				}
			}
			else if(this.argument instanceof tipoArray) {
				tipoResultado tipoParam = this.arguments.get(0).evaluar();
				
				if(tipoParam instanceof tipoArray){
						ArrayList<tipoResultado> argument2 = new ArrayList<tipoResultado>(((tipoArray)this.argument).getValue());
						argument2.addAll(((tipoArray)tipoParam).getValue());
						res = new tipoArray(argument2.get(0).getTipo(),argument2);
				}
				else{
					throw new ParsingException(ParsingException.FUN_TYPE+"concat,"+this.posicion);
				}
			}
			else {
				throw new ParsingException(ParsingException.FUN_APPLY+"concat,"+this.posicion);
			}
		}
		else{
			throw new ParsingException(ParsingException.FUN_CANT_PARAM+"concat,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado toUpperCase(){
		tipoResultado res = new tipoUndefined();
		if(this.arguments.size() == 0){
			
			if(this.argument.getTipo() == tipoExpresion.STRING){
				String argument2 = ((tipoString)this.argument).getValue();
				res = new tipoString(argument2.toUpperCase());
			}
			else{
				throw new ParsingException(ParsingException.FUN_APPLY+"concat,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_CANT_PARAM+"toUpperCase,"+this.posicion);
		}
		return res;
	}
	
	public tipoResultado toLowerCase(){
		tipoResultado res = new tipoUndefined();
		
		if(this.arguments.size() == 0){
			
			if(this.argument.getTipo() == tipoExpresion.STRING){
				String argument2 = ((tipoString)this.argument).getValue();
				res = new tipoString(argument2.toLowerCase());
			}
			else{
				throw new ParsingException(ParsingException.FUN_APPLY+"toLowerCase,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_CANT_PARAM+"toLowerCase,"+this.posicion);
		}
		return res;
	}
	
	public tipoResultado charAt() throws ParsingException, Exception{
		tipoResultado res = new tipoUndefined();
		
		if(this.arguments.size() == 1){
			
			if(this.argument.getTipo() == tipoExpresion.STRING){
				tipoResultado tipoParam = this.arguments.get(0).evaluar();
				
				if(tipoParam.getTipo() == tipoExpresion.INTEGER){
					int indice  = ((tipoInteger)tipoParam).getValue();
					String argument2 = ((tipoString)this.argument).getValue();
					
					if(indice>=0 && indice < argument2.length()) {
						res = new tipoString(""+argument2.charAt(indice));
					}
					else{
						throw new ParsingException(ParsingException.OUT_OF_RANGE+"en charAt,"+this.posicion);
					}
				}
				else {
					throw new ParsingException(ParsingException.FUN_TYPE+"charAt,"+this.posicion);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_APPLY+"charAt,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_CANT_PARAM+"charAt,"+this.posicion);
		}
		return res;
	}
	
	public tipoResultado indexOf() throws ParsingException, Exception{
		tipoResultado res = new tipoUndefined();
		
		if(this.arguments.size() == 1){
			
			if(this.argument.getTipo() == tipoExpresion.STRING){
				tipoResultado tipoParam = this.arguments.get(0).evaluar();
				
				if(tipoParam.getTipo() == tipoExpresion.STRING){
					String ch  = ((tipoString)tipoParam).getValue();
					
					res = new tipoInteger(((tipoString)this.argument).getValue().indexOf(ch));
				}
				else {
					throw new ParsingException(ParsingException.FUN_TYPE+"indexOf,"+this.posicion);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_APPLY+"indexOf,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_CANT_PARAM+"indexOf,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado lastIndexOf() throws ParsingException, Exception{
		tipoResultado res = new tipoUndefined();
		
		if(this.arguments.size() == 1){
			
			if(this.argument.getTipo() == tipoExpresion.STRING){
				tipoResultado tipoParam = this.arguments.get(0).evaluar();
				
				if(tipoParam.getTipo() == tipoExpresion.STRING){
					String ch  = ((tipoString)tipoParam).getValue();
					
					res = new tipoInteger(((tipoString)this.argument).getValue().lastIndexOf(ch));
				}
				else {
					throw new ParsingException(ParsingException.FUN_TYPE+"lastIndexOf,"+this.posicion);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_APPLY+"lastIndexOf,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_CANT_PARAM+"lastIndexOf,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado substring() throws ParsingException, Exception{
		tipoResultado res = new tipoUndefined();
		
		if(this.argument.getTipo() == tipoExpresion.STRING){
			if(this.arguments.size() == 1){
				tipoResultado argument1 = this.arguments.get(0).evaluar();
				if(argument1.getTipo() == tipoExpresion.INTEGER){
					int indice = ((tipoInteger)argument1).getValue();
					if(indice>=0 && indice < ((tipoString)this.argument).getValue().length())
						res = new tipoString(((tipoString)this.argument).getValue().substring(indice));
					else
						res = new tipoString(((tipoString)this.argument).getValue());
				}
				else{
					throw new ParsingException(ParsingException.FUN_TYPE+"substring,"+this.posicion);
				}
			}
			else if(this.arguments.size() == 2){
				tipoResultado argument1 = this.arguments.get(0).evaluar();
				tipoResultado argument2 = this.arguments.get(1).evaluar();
				
				if(argument1.getTipo() == tipoExpresion.INTEGER && argument2.getTipo() == tipoExpresion.INTEGER){
					int indice1 = ((tipoInteger)argument1).getValue();
					int indice2 = ((tipoInteger)argument2).getValue();
					int length = ((tipoString)this.argument).getValue().length();
					if(indice1>=0 && indice1 < length && indice2>=0 && indice2 < length && indice1<=indice2)
						res = new tipoString(((tipoString)this.argument).getValue().substring(indice1,indice2));
					else if(indice1<0 && indice2>=0 && indice2 < length)
						res = new tipoString(((tipoString)this.argument).getValue().substring(0, indice2));
					else if(indice1>=0 &&  indice1 < length && indice2>=length)
						res = new tipoString(((tipoString)this.argument).getValue().substring(indice1, length));
					else
						res = new tipoString(((tipoString)this.argument).getValue());
				}
				else{
					throw new ParsingException(ParsingException.FUN_TYPE+"substring,"+this.posicion);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_CANT_PARAM+"substring,"+this.posicion);
			}
		}
		else{
			throw new ParsingException(ParsingException.FUN_APPLY+"substring,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado split() throws ParsingException, Exception {
		tipoResultado res = new tipoUndefined();
		if(this.arguments.size() == 1){
			
			if(this.argument.getTipo() == tipoExpresion.STRING){
				tipoResultado tipoParam = this.arguments.get(0).evaluar();
				
				if(tipoParam.getTipo() == tipoExpresion.STRING){
					String separador  = ((tipoString)tipoParam).getValue();
					
					List<String> list = Arrays.asList(((tipoString)this.argument).getValue().split(separador));
					ArrayList<tipoResultado> result = new ArrayList<tipoResultado>();
					for(String str : list){
						tipoString strResult = new tipoString(str);
						result.add(strResult);
					}
					res = new tipoArray(tipoExpresion.STRING,result);
				}
				else {
					throw new ParsingException(ParsingException.FUN_TYPE+"split,"+this.posicion);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_APPLY+"split,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_CANT_PARAM+"split,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado join() throws ParsingException, Exception{
		tipoResultado res = new tipoUndefined();
		if((this.argument instanceof tipoArray) && (((tipoArray)this.argument).getTipo() == tipoExpresion.STRING)) {
			if(this.arguments.size() == 1){
				tipoResultado tipoParam = this.arguments.get(0).evaluar();
				
				if(tipoParam.getTipo() == tipoExpresion.STRING){
					String separador = ((tipoString)tipoParam).getValue();
					ArrayList<tipoResultado> list = ((tipoArray)this.argument).getValue();
					res = new tipoString("");
					int cont = 0;
					for(tipoResultado tRes : list){
						String str = ((tipoString)tRes).getValue();
						((tipoString)res).setValue(((tipoString)res).getValue()+str);
						if(cont<list.size()-1)
							((tipoString)res).setValue(((tipoString)res).getValue()+separador);
						cont++;
					}
				}
				else{
					throw new ParsingException(ParsingException.FUN_TYPE+"join,"+this.posicion);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_CANT_PARAM+"join,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_APPLY+"join,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado pop(){
		tipoResultado res = new tipoUndefined();
		
		if((this.argument instanceof tipoArray)) {
			if(this.arguments.size() == 0){
				if(((tipoArray)this.argument).getValue().size() > 0) {
					if(((tipoArray)this.argument).getTipo_elementos() == tipoExpresion.INTEGER){
						res = ((tipoInteger)((tipoArray) this.argument).getValue().remove(((tipoArray) this.argument).getValue().size()-1));
					}
					else if(((tipoArray)this.argument).getTipo_elementos() == tipoExpresion.STRING){
						res = ((tipoString)((tipoArray) this.argument).getValue().remove(((tipoArray) this.argument).getValue().size()-1));
					}
					else if(((tipoArray)this.argument).getTipo_elementos() == tipoExpresion.FLOAT){
						res = ((tipoFloat)((tipoArray) this.argument).getValue().remove(((tipoArray) this.argument).getValue().size()-1));
					}
					else if(((tipoArray)this.argument).getTipo_elementos() == tipoExpresion.BINARIA){
						res = ((tipoBoolean)((tipoArray) this.argument).getValue().remove(((tipoArray) this.argument).getValue().size()-1));
					}
					else if(((tipoArray)this.argument).getTipo_elementos() == tipoExpresion.NULL){
						res = ((tipoNull)((tipoArray) this.argument).getValue().remove(((tipoArray) this.argument).getValue().size()-1));
					}
					else{
						throw new ParsingException(ParsingException.FUN_TYPE+"pop,"+this.posicion);
					}
				}
				else{
					throw new ParsingException(ParsingException.ARRAY_EMPTY+"pop,"+this.posicion);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_CANT_PARAM+"pop,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_APPLY+"pop,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado push() throws ParsingException, Exception {
		tipoResultado res = new tipoUndefined();
		
		if((this.argument instanceof tipoArray)) {
			if(this.arguments.size() == 1){
				tipoResultado tipoParam = this.arguments.get(0).evaluar();
				if(tipoParam.getTipo( )== ((tipoArray)this.argument).getTipo()) {
					((tipoArray)this.argument).getValue().add(tipoParam);
				}
				else{
					throw new ParsingException(ParsingException.FUN_TYPE+"push,"+this.posicion);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_CANT_PARAM+"push,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_APPLY+"push,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado shift(){
		tipoResultado res = new tipoUndefined();
		
		if((this.argument instanceof tipoArray)) {
			if(this.arguments.size() == 0){
				if(((tipoArray)this.argument).getValue().size() > 0) {
					if(((tipoArray)this.argument).getTipo_elementos() == tipoExpresion.INTEGER){
						res = ((tipoInteger)((tipoArray) this.argument).getValue().remove(0));
					}
					else if(((tipoArray)this.argument).getTipo_elementos() == tipoExpresion.STRING){
						res = ((tipoString)((tipoArray) this.argument).getValue().remove(0));
					}
					else if(((tipoArray)this.argument).getTipo_elementos() == tipoExpresion.FLOAT){
						res = ((tipoFloat)((tipoArray) this.argument).getValue().remove(0));
					}
					else if(((tipoArray)this.argument).getTipo_elementos() == tipoExpresion.BINARIA){
						res = ((tipoBoolean)((tipoArray) this.argument).getValue().remove(0));
					}
					else if(((tipoArray)this.argument).getTipo_elementos() == tipoExpresion.NULL){
						res = ((tipoNull)((tipoArray) this.argument).getValue().remove(0));
					}
					else{
						throw new ParsingException(ParsingException.FUN_TYPE+"shift,"+this.posicion);
					}
				}
				else{
					throw new ParsingException(ParsingException.ARRAY_EMPTY+"shift,"+this.posicion);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_CANT_PARAM+"shift,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_APPLY+"shift,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado reverse(){
		tipoResultado res = new tipoUndefined();
		
		if((this.argument instanceof tipoArray)) {
			if(this.arguments.size() == 0){
				Collections.reverse(((tipoArray)this.argument).getValue());
			}
			else{
				throw new ParsingException(ParsingException.FUN_CANT_PARAM+"reverse,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_APPLY+"reverse,"+this.posicion);
		}
		
		return res;
	}
	
	public tipoResultado isNaN() throws ParsingException, Exception{
		tipoResultado res = new tipoUndefined();
		
		if((this.argument == null)) {
			if(this.arguments.size() == 1){
				try {
					tipoResultado tipoArgument = this.arguments.get(0).evaluar();
					if(tipoArgument.getTipo() == tipoExpresion.STRING){
						String str = ((tipoString)tipoArgument).getValue();
						Double.parseDouble(str);
						res = new tipoBoolean(false);
					}
					else if(tipoArgument.getTipo() == tipoExpresion.FLOAT){
						Float value = ((tipoFloat)tipoArgument).getValue();				
						res = new tipoBoolean(value.isNaN());
					}
					else if(tipoArgument.getTipo() == tipoExpresion.BOOLEAN){
						res = new tipoBoolean(true);
					}
					else
						res = new tipoBoolean(false);
					
				}catch (NumberFormatException nfe){
					res = new tipoBoolean(true);
				}
				catch (ParsingException ex){
					res = new tipoBoolean(true);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_CANT_PARAM+"isNaN,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_NOT_APPLY+"isNaN,"+this.posicion);
		}
		return res;
	}
	
	public tipoResultado parse() throws Exception{
		tipoResultado res = new tipoUndefined();
		
		if((this.argument == null)) {
			if(this.arguments.size() == 1){
				tipoResultado tipoArgument = this.arguments.get(0).evaluar();
				if(tipoArgument.getTipo() == tipoExpresion.STRING){
					String str = ((tipoString)tipoArgument).getValue();
					try{
						int val = Integer.parseInt(str);
						res = new tipoInteger(val);
					}catch (NumberFormatException nfe){
						Float val = Float.parseFloat(str);
						res = new tipoFloat(val);
					}
				}
				else{
					throw new ParsingException(ParsingException.FUN_TYPE+"parse,"+this.posicion);
				}
			}
			else{
				throw new ParsingException(ParsingException.FUN_CANT_PARAM+"parse,"+this.posicion);
			}
		}
		else {
			throw new ParsingException(ParsingException.FUN_NOT_APPLY+"parse,"+this.posicion);
		}
		return res;
	}
	
	public tipoResultado applayPredefinedFunction() throws ParsingException, Exception{
		tipoResultado ret = new tipoUndefined();
		if(this.idFunction.toString().equals("length") ){
			ret = this.length();
		}
		else if(this.idFunction.toString().equals("writeline") ){
			ret = this.writeline();
		}
		else if(this.idFunction.toString().equals("readline") ){
			ret = this.readline();
		}
		else if(this.idFunction.toString().equals("concat") ){
			ret = this.concat();
		}
		else if(this.idFunction.toString().equals("toUpperCase") ){
			ret = this.toUpperCase();
		}
		else if(this.idFunction.toString().equals("toLowerCase") ){
			ret = this.toLowerCase();
		}
		else if(this.idFunction.toString().equals("charAt") ){
			ret = this.charAt();
		}
		else if(this.idFunction.toString().equals("indexOf") ){
			ret = this.indexOf();
		}
		else if(this.idFunction.toString().equals("lastIndexOf") ){
			ret = this.lastIndexOf();
		}
		else if(this.idFunction.toString().equals("substring") ){
			ret = this.substring();
		}
		else if(this.idFunction.toString().equals("split") ){
			ret = this.split();
		}
		else if(this.idFunction.toString().equals("join") ){
			ret = this.join();
		}
		else if(this.idFunction.toString().equals("pop") ){
			ret = this.pop();
		}
		else if(this.idFunction.toString().equals("push") ){
			ret = this.push();
		}
		else if(this.idFunction.toString().equals("shift") ){
			ret = this.shift();
		}
		else if(this.idFunction.toString().equals("reverse") ){
			ret = this.reverse();
		}
		else if(this.idFunction.toString().equals("isNaN") ){
			ret = this.isNaN();
		}
		else if(this.idFunction.toString().equals("parse") ){
			ret = this.parse();
		}
		else {
			throw new ParsingException(ParsingException.FUN_NO_DEF+this.idFunction.toString()+this.posicion);
		}
		
		return ret;
	}
	
}
