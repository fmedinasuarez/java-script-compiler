import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Expression extends Statement {

	private Object value; 
	private List<Expression> arguments;
	private tipoExpresion tipo;
	private int linea, col;
	private String posicion;
	
	
	public Expression(tipoExpresion tipo){
		this.value = null;
		this.tipo = tipo;
		this.linea = 0;
		this.col = 0;
		this.posicion = "";
		this.arguments = new ArrayList<Expression>();
	}
	/**
	 * PARA EXPRESIONES SIMPLES (integer, float, null, identifier, etc)
	 * @param value  - El valor de la expresion
	 * @param tipo   - El tipoExpresion asociado
	 */
	public Expression(Object value, tipoExpresion tipo,int linea,int col) {
		if(tipo == tipoExpresion.NULL){
			this.value = "null";
		}else{
			this.value = value;
		}
		this.tipo = tipo;
		this.linea = linea;
		this.col = col;
		this.posicion = " en la linea " + linea + " y columna " + col;
		this.arguments = new ArrayList<Expression>();
	}
	/**
	 * PARA EXPRESIONES UNARIAS
	 * @param value - El operador unario
	 * @param exp   - El operando
	 * @param tipo  - El tipoExpresión asociado 
	 */
	public Expression(Object value, Expression exp, tipoExpresion tipo, int linea, int col) {
		this.value = value;
		this.tipo = tipo;
		this.linea = linea;
		this.col = col;
		this.posicion = " en la linea " + linea + " y columna " + col;
		this.arguments = new ArrayList<Expression>();
		this.arguments.add(exp);
	}
	/**
	 *                    PARA EXPRESION ARRAY         | ELEMENTO ARRAY                                 | FUNCTION_CALL                  
	 * @param value     - valor null (si defino ARRAY) | identificador de una expresion: a[ c + b]      | identificador de la funcion     
	 * @param arguments - los elementos del array      | una Expression de tipoExpresion = tipoInteger  | los parametros de la llamada    
	 * @param tipo      - tipoExpresion ARRAY          | tipoExpression: ARAY_ELEMENT                   | tipoExpresion: FUNCTION_CALL     
	 */
	public Expression(Object value, List<Expression> arguments, tipoExpresion tipo, int linea, int col) {
		this.value = value;
		this.tipo = tipo;
		this.arguments = arguments;
		this.linea = linea;
		this.col = col;
		this.posicion = " en la linea " + linea + " y columna " + col;
	}
	
	/**
	 * PARA EXPRESIONES BINARIAS
	 * @param value - El operador binario
	 * @param left  - El operando izquierdo de la expresión
	 * @param right - El operando derecho de la expresión
	 * @param tipo  - El tipoExpresión asociado
	 */
	public Expression(Object value, Expression left, Expression right, tipoExpresion tipo, int linea, int col) {
		this.value = value;
		this.tipo = tipo;
		this.linea = linea;
		this.col = col;
		this.arguments = new ArrayList<Expression>();
		this.arguments.add(left);
		this.arguments.add(right);
		this.posicion = " en la linea " + linea + " y columna " + col;
	}

	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public List<Expression> getArguments() {
		return arguments;
	}
	
	public void setArguments(List<Expression> arguments) {
		this.arguments = arguments;
	}
	
	public tipoExpresion getTipo() {
		return tipo;
	}
	
	public int getLinea() {
		return this.linea;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public String getPosicion() {
		return this.posicion;
	}
	
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public String toString() {
		return this.toString(0);
	}

	public String toString(int level) {
		StringBuffer sb = new StringBuffer();
		
		String tab = "";
		for (int i = 0; i < level; i++) {
			tab += "   ";
		}		
		sb.append(tab);
		if(this.value != null)
			sb.append(this.value.toString());
		
		if (this.arguments.size() > 0) {
			sb.append("( ");
			for (int i = 0; i < this.arguments.size(); i++) {
				Expression exparg = this.arguments.get(i);
				if (exparg == null) {
					sb.append(" ");
				} else {
					String exptext = exparg.toString(level + 1);
					sb.append(exptext);
				}
			}
			sb.append(tab);
			sb.append(" )");
		} 		
		sb.append(" ");		
		return sb.toString();
	}
	
	public tipoResultado evaluar() throws ParsingException, Exception{
		
		tipoResultado ret = null;
		/**
		 * SI ES ALGUN TIPO SIMPLE
		 */
		if (this.tipo == tipoExpresion.INTEGER){
			ret = new tipoInteger(Integer.valueOf(this.value.toString()).intValue());
		}
		else if (this.tipo == tipoExpresion.FLOAT){
			ret = new tipoFloat(Float.valueOf(this.value.toString()).floatValue());  //pasar el value como String al instanciar expresion
		}
		else if (this.tipo == tipoExpresion.BOOLEAN){
			ret = new tipoBoolean(Boolean.valueOf(this.value.toString()).booleanValue());
		}
		else if (this.tipo == tipoExpresion.STRING){
			ret = new tipoString(this.value.toString());
		}
		else if(this.tipo == tipoExpresion.IDENTIFIER){
			RecursosGlobales rg = RecursosGlobales.getInstance();
			String var_name = this.value.toString();
			Expression var_exp = null;
			Variable var = null;
			
			/** Busco si esta en SCOPE LOCAL */
			if(rg.variableEnScopeLocal(var_name)){
				var = rg.getVariableEnScopeLocal(var_name);
				var_exp = var.getExp();
				if(var_exp.getTipo() != tipoExpresion.UNDEFINED){
					//ret = var_exp.evaluar();
					ret = var.getValue();
				}
				else{
					throw new ParsingException(ParsingException.VAR_NO_VALUE.concat(this.value.toString())+this.posicion);
				}
			}
			/** Busco si esta en VARIABLES GLOBALES */
			else if(rg.variableEnVariablesGlobales(var_name)){
				var = rg.getVariableGlobal(var_name);
				var_exp = var.getExp();
				if(var_exp.getTipo() != tipoExpresion.UNDEFINED){
					//ret = var_exp.evaluar();
					ret = var.getValue();
				}
				else{
					throw new ParsingException(ParsingException.VAR_NO_VALUE.concat(this.value.toString())+this.posicion);
				}
			}
			/** ERROR identificador de variable NO DEFINIDO */
			else{
				throw new ParsingException(ParsingException.ID_NO_VALUE.concat(this.value.toString()+this.posicion));
			}
		}
		else if(this.tipo == tipoExpresion.ARRAY_ELEMENT){
			RecursosGlobales rg = RecursosGlobales.getInstance();
			String var_name = this.value.toString();
			Variable array = null;
			
			/** voy abuscar el ARRAY scope LOCAL */
			if(rg.variableEnScopeLocal(var_name)){
				array = rg.getVariableEnScopeLocal(var_name);
			}
			/** voy abuscar el ARRAY variables GLOBALES */
			else if(rg.variableEnVariablesGlobales(var_name)){
				array = rg.getVariableGlobal(var_name);
			}
			/** ERROR array no definido */
			else{
				throw new ParsingException(ParsingException.ARRAY_NO_DEF.concat(var_name+this.posicion));
			}
			
			if(array.getExp().getTipo() == tipoExpresion.ARRAY){
				/** evaluo la expresion dentro de a[c + b ]*/
				tipoResultado res_indice = this.arguments.get(0).evaluar();
				
				if(res_indice instanceof tipoInteger){
					int indice = ((tipoInteger)res_indice).getValue();
					/** no me fui de rango */
					if((indice > -1) && (indice < array.getExp().getArguments().size())){
						ret = ((tipoArray)array.getValue()).getValue().get(indice);
					}
					/** me fui de rango */
					else{
						throw new ParsingException(ParsingException.OUT_OF_RANGE.concat(this.posicion));
					}
				}
				/** ERROR la expresion no es un entero */
				else{
					throw new ParsingException(ParsingException.EXP_NO_INT.concat(this.posicion));
				}	
			}
			else{
				throw new ParsingException(ParsingException.ARRAY_NO_DEF.concat(array.getIdentifier().toString())+this.getPosicion());
			}
		}
		else if(this.tipo == tipoExpresion.ARRAY){
			tipoExpresion tipo_elementos_array = tipoExpresion.UNDEFINED;
			ArrayList<tipoResultado> argumentos = new ArrayList<tipoResultado>();
			
			if(!this.arguments.isEmpty()){			
				
				Iterator<Expression> it = this.arguments.iterator();
				tipoResultado res_primero = it.next().evaluar();
				argumentos.add(res_primero);
				
				tipo_elementos_array = res_primero.getTipo();
				
				boolean tipoIgual = true;	
				
				while(it.hasNext() && tipoIgual){
					tipoResultado res_elemento =  it.next().evaluar();
					tipoIgual = res_elemento.getTipo() == tipo_elementos_array;
					argumentos.add(res_elemento);
				}
				if(!tipoIgual){
					throw new ParsingException(ParsingException.ARRAY_ELEM_DIF_TYPE.concat(this.posicion));
				}
			}
			ret =  new tipoArray(tipo_elementos_array,argumentos);
		}
		else if(this.tipo == tipoExpresion.FUNCTION_CALL){
			String function_name = this.value.toString();
			/** Si existe una función con ese nombre */
			if(RecursosGlobales.getInstance().FuncionEnFuncionesGlobales(function_name)){
				function_statement fun = RecursosGlobales.getInstance().getFuncionGlobal(function_name);
				/** Si en la llamada paso la misma cantidad de parámetros esperados */
				if(fun.getArgumentos().size() == this.arguments.size()){
					/** Con cada exp argumento la evalúo y creo una variable */
					ArrayList<Variable> parametros = new ArrayList<Variable>();
					int indice = 0;
					for(Expression e : this.arguments){
						String var_name = fun.getArgumentos().get(indice).getValue().toString();
						Variable var = new Variable(var_name,e,true);
						tipoResultado tres= e.evaluar();
						var.setValue(tres);
						parametros.add(var);
						indice++;
					}
					fun.ejecutar(parametros);
					ret = fun.getReturnValue();
				}
				else{
					throw new ParsingException(ParsingException.FUN_CANT_PARAM.concat(this.value.toString()+this.posicion));
				}
			}
			else{
				/** Puede que sea writeline, readline o parse. Las reconozco aca por que se llaman sin el punto*/
				predefinedFunction predFun = new predefinedFunction(this.value,null,this.arguments,this.linea,this.col);
				ret = predFun.applayPredefinedFunction();
			}	
		}
		else if(this.tipo == tipoExpresion.FUNCTION_PREDEF) {
			tipoResultado argument = this.arguments.get(0).evaluar();

			predefinedFunction predFun = new predefinedFunction(this.arguments.get(1).getValue(),argument,
					                         this.arguments.get(1).arguments,this.linea,this.col);
			
			ret = predFun.applayPredefinedFunction();
        }
		else if(this.tipo == tipoExpresion.NULL){
			ret = new tipoNull();
		}
		/**
		 * SI ES BINARIA
		 */
	    if(this.tipo == tipoExpresion.BINARIA){
	    	
	    	tipoResultado L = this.arguments.get(0).evaluar();
	        tipoResultado R = this.arguments.get(1).evaluar();
	        
	        /**
	         * OPERADORES MATEMATICOS
	         */
	        if(this.value.toString().equals("+")){
	        	
	        	if(L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.FLOAT){
	        		ret = new tipoFloat( ((tipoInteger)L).getValue() + ((tipoFloat)R).getValue());
	        	}
	        	else if (L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoFloat( ((tipoFloat)L).getValue() + ((tipoInteger)R).getValue());
	            }
	        	else if(L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.FLOAT){
	            	ret = new tipoFloat( ((tipoFloat)L).getValue() + ((tipoFloat)R).getValue());
		    	}
	        	else if (L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoInteger( ((tipoInteger)L).getValue() + ((tipoInteger)R).getValue());
	            }
	        	else{
	        		throw new ParsingException(ParsingException.PLUS_TYPE.concat(this.posicion));
	        	}
	        	
        	}else if(this.value.toString().equals("-")){
        		
	        	if(L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.FLOAT){
	        		ret = new tipoFloat( ((tipoInteger)L).getValue() - ((tipoFloat)R).getValue());
	        	}
	        	else if (L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoFloat( ((tipoFloat)L).getValue() - ((tipoInteger)R).getValue());
	            }
	        	else if(L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.FLOAT){
	            	ret = new tipoFloat( ((tipoFloat)L).getValue() - ((tipoFloat)R).getValue());
		    	}
	        	else if (L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoInteger( ((tipoInteger)L).getValue() - ((tipoInteger)R).getValue());
	            }
	        	else{
	        		throw new ParsingException(ParsingException.MINUS_TYPE.concat(this.posicion));
	        	}
	        	
        	}else if(this.value.toString().equals("*")){
        		
	        	if(L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.FLOAT){
	        		ret = new tipoFloat( ((tipoInteger)L).getValue() * ((tipoFloat)R).getValue());
	        	}
	        	else if (L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoFloat( ((tipoFloat)L).getValue() * ((tipoInteger)R).getValue());
	            }
	        	else if(L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.FLOAT){
	            	ret = new tipoFloat( ((tipoFloat)L).getValue() * ((tipoFloat)R).getValue());
		    	}
	        	else if (L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoInteger( ((tipoInteger)L).getValue() * ((tipoInteger)R).getValue());
	            }
	        	else{
	        		throw new ParsingException(ParsingException.MULT_TYPE.concat(this.posicion));
	        	}
	        	
        	}else if(this.value.toString().equals("/")){
        		
	        	if(L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.FLOAT)
	        		ret = new tipoFloat( (float)((tipoInteger)L).getValue() / ((tipoFloat)R).getValue());
	        	else if (L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.INTEGER)
	        		ret = new tipoFloat( (float)((tipoFloat)L).getValue() / ((tipoInteger)R).getValue());
	        	else if(L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.FLOAT)
        			ret = new tipoFloat( (float)((tipoFloat)L).getValue() / ((tipoFloat)R).getValue());
	        	else if (L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.INTEGER){
	        		try{
	        			if((((tipoInteger)L).getValue() % ((tipoInteger)R).getValue()) == 0){
	        				ret = new tipoInteger( ((tipoInteger)L).getValue() / ((tipoInteger)R).getValue());
	        			}
	        			else{
	        				ret = new tipoFloat((float)((tipoInteger)L).getValue() / ((tipoInteger)R).getValue());
	        			}
	        		}catch(Exception e){
	        			throw new ParsingException(ParsingException.DIV_BY_CERO+this.posicion);
	        		}
	            }
	        	else{
	        		throw new ParsingException(ParsingException.DIV_TYPE.concat(this.posicion));
	        	}
	        /**
	         * OPERADORES LOGICOS	
	         */
        	}else if(this.value.toString().equals("&&")){
        		
        		if(L.getTipo() == tipoExpresion.BOOLEAN && R.getTipo() == tipoExpresion.BOOLEAN){	
					ret = new tipoBoolean(((tipoBoolean)L).getValue() && ((tipoBoolean)R).getValue());
				}
				else{
					throw new ParsingException(ParsingException.AND_TYPE.concat(this.posicion));
				}
        		
        	}else if(this.value.toString().equals("||")){
        		
        		if(L.getTipo() == tipoExpresion.BOOLEAN && R.getTipo() == tipoExpresion.BOOLEAN){	
					ret = new tipoBoolean(((tipoBoolean)L).getValue() || ((tipoBoolean)R).getValue());
				}
				else{
					throw new ParsingException(ParsingException.OR_TYPE.concat(this.posicion));
				}
        	}
	        /**
	         * OPERADORES RELACIONALES (>, >=, <, <=)
	         */
	        else if(this.value.toString().equals(">")){
    		
	        	if(L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.FLOAT){
	        		ret = new tipoBoolean( ((tipoInteger)L).getValue() > ((tipoFloat)R).getValue());
	        	}
	        	else if (L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoBoolean( ((tipoFloat)L).getValue() > ((tipoInteger)R).getValue());
	            }
	        	else if(L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.FLOAT){
	            	ret = new tipoBoolean( ((tipoFloat)L).getValue() > ((tipoFloat)R).getValue());
		    	}
	        	else if (L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoBoolean( ((tipoInteger)L).getValue() > ((tipoInteger)R).getValue());
	            }
	        	else{
	        		throw new ParsingException(ParsingException.MAYOR_TYPE.concat(this.posicion));
	        	}
	        }
	        else if(this.value.toString().equals("<")){
	    		
	        	if(L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.FLOAT){
	        		ret = new tipoBoolean( ((tipoInteger)L).getValue() < ((tipoFloat)R).getValue());
	        	}
	        	else if (L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoBoolean( ((tipoFloat)L).getValue() < ((tipoInteger)R).getValue());
	            }
	        	else if(L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.FLOAT){
	            	ret = new tipoBoolean( ((tipoFloat)L).getValue() < ((tipoFloat)R).getValue());
		    	}
	        	else if (L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoBoolean( ((tipoInteger)L).getValue() < ((tipoInteger)R).getValue());
	            }
	        	else{
	        		throw new ParsingException(ParsingException.MINOR_TYPE.concat(this.posicion));
	        	}
	        }
	        else if(this.value.toString().equals(">=")){
	    		
	        	if(L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.FLOAT){
	        		ret = new tipoBoolean( ((tipoInteger)L).getValue() >= ((tipoFloat)R).getValue());
	        	}
	        	else if (L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoBoolean( ((tipoFloat)L).getValue() >= ((tipoInteger)R).getValue());
	            }
	        	else if(L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.FLOAT){
	            	ret = new tipoBoolean( ((tipoFloat)L).getValue() >= ((tipoFloat)R).getValue());
		    	}
	        	else if (L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoBoolean( ((tipoInteger)L).getValue() >= ((tipoInteger)R).getValue());
	            }
	        	else{
	        		throw new ParsingException(ParsingException.MAYOR_EQUAL_TYPE.concat(this.posicion));
	        	}
	        }
	        else if(this.value.toString().equals("<=")){
	    		
	        	if(L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.FLOAT){
	        		ret = new tipoBoolean( ((tipoInteger)L).getValue() <= ((tipoFloat)R).getValue());
	        	}
	        	else if (L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoBoolean( ((tipoFloat)L).getValue() <= ((tipoInteger)R).getValue());
	            }
	        	else if(L.getTipo() == tipoExpresion.FLOAT && R.getTipo() == tipoExpresion.FLOAT){
	            	ret = new tipoBoolean( ((tipoFloat)L).getValue() <= ((tipoFloat)R).getValue());
		    	}
	        	else if (L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.INTEGER){
	            	ret = new tipoBoolean( ((tipoInteger)L).getValue() <= ((tipoInteger)R).getValue());
	            }
	        	else{
	        		throw new ParsingException(ParsingException.MINOR_EQUAL_TYPE.concat(this.posicion));
	        	}
	        }
	        /**
	         * OPERADORES RELACIONALES (==, !=)
	         */
	        else if(this.value.toString().equals("==")){
	        	
	        	if(L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.FLOAT){
        			ret = new tipoBoolean( ((tipoInteger)L).getValue() == ((tipoFloat)R).getValue());
        		}
        		else if(R.getTipo() == tipoExpresion.INTEGER && L.getTipo() == tipoExpresion.FLOAT){
        			ret = new tipoBoolean( ((tipoFloat)L).getValue() == ((tipoInteger)R).getValue());
        		}
        		else if(L.getTipo() == R.getTipo()){
	        		if(L instanceof tipoArray && R instanceof tipoArray) {
	        			boolean iguales=false;
	        			if(((tipoArray)L).getTipo() == ((tipoArray)R).getTipo()) {
							iguales = ((tipoArray)L).getValue().size() == ((tipoArray)R).getValue().size();
							Iterator<tipoResultado> it1 = ((tipoArray)L).getValue().iterator();
							Iterator<tipoResultado> it2 = ((tipoArray)R).getValue().iterator();
							while(it1.hasNext()&& it2.hasNext() && iguales){
								tipoResultado tipo1 = it1.next();
								if(tipo1.getTipo() == tipoExpresion.INTEGER){
				        			iguales =((tipoInteger)tipo1).getValue() == ((tipoInteger)it2.next()).getValue();
				        		}
				        		else if(tipo1.getTipo() == tipoExpresion.FLOAT){
				        			iguales = ((tipoFloat)tipo1).getValue() == ((tipoFloat)it2.next()).getValue();
				        		}
								else if(tipo1.getTipo() == tipoExpresion.STRING){
									iguales= ((tipoString)tipo1).getValue() == ((tipoString)it2.next()).getValue();      			
								}
								else if(tipo1.getTipo() == tipoExpresion.BOOLEAN){
									iguales= ((tipoBoolean)tipo1).getValue() == ((tipoBoolean)it2.next()).getValue();
								}
								else if(tipo1.getTipo() == tipoExpresion.NULL){
									iguales = ((tipoNull)tipo1).getValue() == ((tipoNull)it2.next()).getValue();      			
								}
							}
						}
	        			ret = new tipoBoolean(iguales);
	        		}
	        		else if(L.getTipo() == tipoExpresion.INTEGER){
	        			ret = new tipoBoolean( ((tipoInteger)L).getValue() == ((tipoInteger)R).getValue());
	        		}
	        		else if(L.getTipo() == tipoExpresion.FLOAT){
	        			ret = new tipoBoolean( ((tipoFloat)L).getValue() == ((tipoFloat)R).getValue());
	        		}
					else if(L.getTipo() == tipoExpresion.STRING){
						ret = new tipoBoolean( ((tipoString)L).getValue() == ((tipoString)R).getValue());      			
					}
					else if(L.getTipo() == tipoExpresion.BOOLEAN){
						ret = new tipoBoolean( ((tipoBoolean)L).getValue() == ((tipoBoolean)R).getValue());
					}
					else if(L.getTipo() == tipoExpresion.NULL){
						ret = new tipoBoolean( ((tipoNull)L).getValue() == ((tipoNull)R).getValue());      			
					}
	        	}
	        	else{
	        		throw new ParsingException(ParsingException.EQUAL_TYPE.concat(this.posicion));
	        	}
	        }
	        else if(this.value.toString().equals("!=")){
	        	if(L.getTipo() == tipoExpresion.INTEGER && R.getTipo() == tipoExpresion.FLOAT){
        			ret = new tipoBoolean( ((tipoInteger)L).getValue() != ((tipoFloat)R).getValue());
        		}
        		else if(R.getTipo() == tipoExpresion.INTEGER && L.getTipo() == tipoExpresion.FLOAT){
        			ret = new tipoBoolean( ((tipoFloat)L).getValue() != ((tipoInteger)R).getValue());
        		}
        		else if(L.getTipo() == R.getTipo()){
	        		if(L instanceof tipoArray && R instanceof tipoArray){
	        			boolean iguales = true;
	        			if(((tipoArray)L).getTipo() == ((tipoArray)R).getTipo()){
		        			iguales = ((tipoArray)L).getValue().size() != ((tipoArray)R).getValue().size();
							Iterator<tipoResultado> it1 = ((tipoArray)L).getValue().iterator();
							Iterator<tipoResultado> it2 = ((tipoArray)R).getValue().iterator();
							while(it1.hasNext()&& it2.hasNext() && !iguales){
								tipoResultado tipo1 = it1.next();
								if(tipo1.getTipo() == tipoExpresion.INTEGER){
				        			iguales =((tipoInteger)tipo1).getValue() != ((tipoInteger)it2.next()).getValue();
				        		}
				        		else if(tipo1.getTipo() == tipoExpresion.FLOAT){
				        			iguales = ((tipoFloat)tipo1).getValue() != ((tipoFloat)it2.next()).getValue();
				        		}
								else if(tipo1.getTipo() == tipoExpresion.STRING){
									iguales= ((tipoString)tipo1).getValue() != ((tipoString)it2.next()).getValue();      			
								}
								else if(tipo1.getTipo() == tipoExpresion.BOOLEAN){
									iguales= ((tipoBoolean)tipo1).getValue() != ((tipoBoolean)it2.next()).getValue();
								}
								else if(tipo1.getTipo() == tipoExpresion.NULL){
									iguales = ((tipoNull)tipo1).getValue() != ((tipoNull)it2.next()).getValue();      			
								}
							}
		        		}
	        			ret = new tipoBoolean(iguales);
	        		}
					else if(L.getTipo() == tipoExpresion.INTEGER){
	        			ret = new tipoBoolean( ((tipoInteger)L).getValue() != ((tipoInteger)R).getValue());
	        		}
	        		else if(L.getTipo() == tipoExpresion.FLOAT){
	        			ret = new tipoBoolean( ((tipoFloat)L).getValue() != ((tipoFloat)R).getValue());
	        		}
					else if(L.getTipo() == tipoExpresion.STRING){
						ret = new tipoBoolean( ((tipoString)L).getValue() != ((tipoString)R).getValue());      			
					}
					else if(L.getTipo() == tipoExpresion.BOOLEAN){
						ret = new tipoBoolean( ((tipoBoolean)L).getValue() != ((tipoBoolean)R).getValue());
					}
					else if(L.getTipo() == tipoExpresion.NULL){
						ret = new tipoBoolean( ((tipoNull)L).getValue() != ((tipoNull)R).getValue());      			
					}
					else if(L.getTipo() == tipoExpresion.IDENTIFIER){
						/**
						 * TODO
						 */
					} 
	        	}
	        	else{
	        		throw new ParsingException(ParsingException.NO_EQUAL_TYPE.concat(this.posicion));
	        	}
	        }
	    }
	    /**
	     * SI ES UNARIA
	     */
	    if(this.tipo == tipoExpresion.UNARIA){
	    	tipoResultado res = null;    
	    	
	    	if(this.value.toString().equals("-")){
				res = this.arguments.get(0).evaluar();
				if(res.getTipo() == tipoExpresion.INTEGER){	
					int val = -((tipoInteger)res).getValue();
					ret = new tipoInteger(val);
				}
				else if(res.getTipo() == tipoExpresion.FLOAT){	
					Float val = -((tipoFloat)res).getValue();
					ret = new tipoFloat(val);
				}
				else{
					throw new ParsingException(ParsingException.MINUS_TYPE.concat(this.posicion));
				}
			}
	    	else if(this.value.toString().equals("!")){
				res = this.arguments.get(0).evaluar();
				if(res.getTipo() == tipoExpresion.BOOLEAN){	
					ret = new tipoBoolean(!((tipoBoolean)res).getValue());
				}
				else{
					throw new ParsingException(ParsingException.NOT_TYPE.concat(this.posicion));
				}
			}
	    	else if (this.arguments.get(0).getTipo() == tipoExpresion.IDENTIFIER){	    		
	    		/** EVALUO Y CONTROL TIPOS */
	    		res = this.arguments.get(0).evaluar();    		
			    if(this.value.toString().equals("++")){
			    	if(res.getTipo() == tipoExpresion.INTEGER){
			    		ret = new tipoInteger(((tipoInteger)res).getValue() + 1);
			    	}
			    	else if(res.getTipo() == tipoExpresion.FLOAT){
			    		ret = new tipoFloat(((tipoFloat)res).getValue() + 1);
			    	}
			    	else{
						throw new ParsingException(ParsingException.PLUS_PLUS_TYPE.concat(this.posicion));
					}
			    }
			    else if(this.value.toString().equals("--")){
			    	if(res.getTipo() == tipoExpresion.INTEGER){
			    		ret = new tipoInteger(((tipoInteger)res).getValue() - 1);
			    	}
			    	else if(res.getTipo() == tipoExpresion.FLOAT){
			    		ret = new tipoFloat(((tipoFloat)res).getValue() - 1);
			    	}
			    	else{
						throw new ParsingException(ParsingException.MINUS_MINUS_TYPE.concat(this.posicion));
					}
			    }
			    /** ACTUALIZO VALOR */
	    		RecursosGlobales rg = RecursosGlobales.getInstance();
				String var_name = this.arguments.get(0).getValue().toString();
				Variable var = null;
			    if(rg.variableEnScopeLocal(var_name)){
					var = rg.getVariableEnScopeLocal(var_name);
					var.setValue(ret);
				}
				else if(rg.variableEnVariablesGlobales(var_name)){
					var = rg.getVariableGlobal(var_name);
					var.setValue(ret);
				}
				else{
					throw new ParsingException(ParsingException.ID_NO_VALUE.concat(var_name+this.posicion));
				}
	    	}
			else{
				throw new ParsingException(ParsingException.MINUS_PLUS_TYPE.concat(this.posicion));
			}
	    }
	    return ret;
	
	}
	
	@Override
	public tipoResultado ejecutar() throws ParsingException, Exception {
		return this.evaluar();
	}
}


