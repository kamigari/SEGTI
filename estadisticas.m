function estadisticas(x)
% ESTADISTICAS saca la media aritmetica, la moda, la desviacion media
% respecto a la media aritmetica, la desviacion tipica, el coeficiente
% de asimetria de Pearson y la curtosis.
%
% Para ejecutar solo se necesita pasar como parametro un vector de datos estadisticos.
if(nargin == 0)
disp('Error, tiene que introducir un vector de datos.')
end
hist(x)
fprintf('La media aritmetica es:')
disp(mean(x))
fprintf('La moda es:')
disp(median(x))
fprintf('La desviacion media respecto a la media aritmetica es:')
disp(sum(abs(x-mean(x)))/length(x))
fprintf('La varianza es:')
disp(var(x))
fprintf('La desviacion tipica es:')
disp(std(x))
%----------------------ASIMETRIA DE PEARSON----------------------------%
dum=x-mean(x);% diferencias respecto a la media
dum=dum.^3;% diferencias al cubo
dum=sum(dum);% suma de las diferencias al cubo
m3=dum/length(x);% momento de orden tres
s=std(x);% desviacion estandar
CoefFisher=m3/(s^3);% coeficiente de asimetria de Fisher
fprintf('El coeficiente de asimetria de Fisher es:')
disp(CoefFisher)
moda=mode(x);% moda del vector de datos muestrales
fprintf('La moda es: ')
disp(moda)
As=(mean(x)-moda)/s;% coeficiente de asimetria de pearson
fprintf('El coeficiente de asimetria de PEARSON es:')
disp(As)
%----------------------------CURTOSIS----------------------------------%
dum=x-mean(x);% diferencias respecto a la media
dum=dum.^4;% diferencias a la cuarta
dum=sum(dum);% suma de las diferencias a la cuarta
m4=dum/length(x);% momento de orden cuatro
s=std(x);% desviacion estandar
g2=m4/(s^4);% coeficiente de curtosis
fprintf('El coeficiente de curtosis es:')
disp(g2)
return