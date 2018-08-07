var j=0;
var h=0;
for(var i= 0; i<20;i=i+1){
	j++;
	if(j<5){
			continue;
	}
	else{
		h++;
	}
	if(j==10){
		break;
	}
}

writeline("Valor de j: ");
writeline(j);/*j con valor 10*/
writeline("\nValor de h: ");
writeline(h);/*h con valor 6*/
