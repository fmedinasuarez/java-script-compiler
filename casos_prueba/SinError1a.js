var x;
var y;
var z;

function fun(a){
	if(a<=1){
		return 1;
	}
	else{
	    return 1+fun(a-1);
	}
}
//Tres casos bordes, a<1, a=1 y a>1
x=fun(-3);//x con valor 1
writeline("Valor de x: ");writeline(x);

y=fun(1);//y con valor 1
writeline("\nValor de y: ");writeline(y);

z=fun(3); //z ahora deberia de tener valor 3
writeline("\nValor de z: ");writeline(z);
