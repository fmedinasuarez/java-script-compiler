var mensaje = "gana la de fuera"; 
function muestraMensaje() { 
	var mensaje = "gana la de dentro"; 
	writeline(mensaje.concat("\n")) //Se omite el puntoycoma
} 
writeline(mensaje.concat("\n")); 
muestraMensaje(); 
writeline(mensaje.concat("\n"));
