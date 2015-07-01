#!/usr/bin/perl

use strict;

#Autor: Moises Gautier Gomez
#5º Ingenieria en informatica
#Asignatura: Interfaces Software en Lenguaje Natural
#Practica 1 - Ejercicio 2


my @claves = ('madre','padre','hermano','hermana');
my $cadena;
my $coma = ",";
my $claves_generadas;
my $salir = 'adios';
my @texto;

print "Bienvenido, ¿de qué tema quiere hablar? \n";

while(<>)
{
	chomp;
	$cadena = $_;
	$cadena =~ tr/\A-Z/a-z/;
	if($cadena =~ $salir)
	{
		print "¡Hasta luego! \n";
		exit(1);
	}

	$claves_generadas = "";
	# Almaceno en el array @texto la entrada estandar en formato de lista
	@texto = split;	
	foreach my $key (@texto)
	{
		#Extraigo los elementos del array y voy comparandolos con las palabras
		# claves del ejercicio.
		
		foreach my $value (@claves)
		{
			#Ahora voy comparando el elemento anterior con cada palabra
			# clave de mi ejercicio y si hace match, compruebo que no se
			# ha repetido y si no es asi, la introduzco en claves_generadas
			# que sera la cadena salida segun los datos que reciba.

			if($key =~ $value)
			{
				if(length($key) == length($value))
				{
					if($claves_generadas !~ $key)
					{
						$claves_generadas = "$claves_generadas $key$coma";
					}
				}
			}
		}
	}

	#Si la longitud es 1 significa de que solo se ha introducido una palabra
	# reservada y por lo tanto la coma de la siguiente sobra.

	if(length($claves_generadas) > 0)
	{
		$claves_generadas =~ s/,$//; #Esto elimina la ultima coma introducida
		print "Hábleme más de su".$claves_generadas."\n";
	}

	#Si la longitud es 0 significa que no se ha introducido por la entrada estandar
	# ninguna palabra clave del ejercicio y por lo tanto estoy a la espera de mas
	# informacion.

	if(length($claves_generadas) == 0)
	{
		print "Muy interesante, sígame contando. \n";
	}
	
}
