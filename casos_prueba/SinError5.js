var b= 1;
var z = b;
if (z==1) {
	var a= z;
	{
		var a=7;
		var d=b;
		e = a;
		writeline("Valor de a en scope del bloque: ");
		writeline(a);
	}
	c = a; //no tiene var adelante entonces es global
	b = 10; //no tiene var adelante entonces es global
	writeline("\nValor de a en scope de \"if(z==1)\": ");
	writeline(a);
}
h = z;
k = e;

writeline("\nValor de e: ");
writeline(e);
writeline("\nValor de h: ");
writeline(h);
writeline("\nValor de k: ");
writeline(k);
writeline("\nValor de c: ");
writeline(c);
writeline("\nValor de b: ");
writeline(b);