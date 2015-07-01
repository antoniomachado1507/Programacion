% Como calcular las raices es bastante complicado, utitilizaremos
% directamente la funcion residuez para hallar la descomposicion
% de los vectores de coeficientes.


a = [1 -0.8741 0.9217 0.26732];
b = [0.1866 0.2336 0.2336 0.1866];

[r,p,k] = residuez(b,a);
r
p
k
