var a = [1,2,3];
var c = ["soy","un","array"];

writeline(a.length);
writeline("\n");

b = "hola mundo";
writeline(b.length);
writeline("\n");

writeline(b.substring(2,5));
writeline("\n");

writeline(b.concat(" ahora te concateno un string"));
writeline("\n");

writeline(b.toUpperCase());
writeline("\n");

writeline(b.charAt(2));
writeline("\n");

writeline(b.split(" "));
writeline("\n");

writeline(b.lastIndexOf('o'));
writeline("\n");

writeline(c.join(" "));
writeline("\n");

a.pop();
writeline(a);
writeline("\n");

a.push(6);
writeline(a);
writeline("\n");

a.reverse();
writeline(a);
writeline("\n");

writeline("Es NaN: ");
writeline(isNaN(20/0));
writeline("\n");

writeline(parse("1234"));
writeline("\n");