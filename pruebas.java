package src;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.bouncycastle.crypto.digests.*;
import org.bouncycastle.util.encoders.*;
import java.security.SecureRandom;


public class pruebas {
	
	public void check(){
		
		GOST3411Digest prueba = new GOST3411Digest();
    	String reconstitutedString;
    	byte[] myvar = "This is message, length=32 bytes".getBytes();
    	System.out.println("El array de bytes: " + Arrays.toString(myvar));
		reconstitutedString = new String(myvar);
		System.out.println("Siendo su representacion en String: " + "\"" +reconstitutedString + "\"");
    	System.out.println("Lo ciframos usando el algoritmo de cifrado (funcion hash): " + prueba.getAlgorithmName());
    	System.out.println("Dando como solucion:");
    	
    	prueba.reset();
    	prueba.update(myvar, 0, myvar.length);
    	byte[] digested = new byte[prueba.getDigestSize()];
    	prueba.doFinal(digested, 0);
		
    	System.out.println("El array de bytes cifrado: " + Arrays.toString(digested));
		reconstitutedString = new String(Hex.encode(digested));
    	System.out.println("El bloque hexadecimal cifrado: " + reconstitutedString);
		
	}
	
	
	public static int getHammingDistance(byte[] sequence1, byte[] sequence2) {

        if (sequence1 == null || sequence2 == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        if (sequence1.length != sequence2.length) {
            throw new IllegalArgumentException("Strings must have the same length");
        }
	    
	    int shorter = Math.min(sequence1.length, sequence2.length);
	    int longest = Math.max(sequence1.length, sequence2.length);

	    int result = 0;
	    for (int i=0; i<shorter; i++) {
	        if (sequence1[i] != sequence2[i]) result++;
	    }

	    result += longest - shorter;

	    return result;
	}
	
	
	public int hammingDistanceRandomStringsIterations(){
		
		GOST3411Digest cifrador = new GOST3411Digest();
    	byte[] digested1 = null, digested2 = null;
		SecureRandom random = new SecureRandom();
		String reconstructedString = null;
		byte[] randomBytes1 = new byte[32];
		byte[] randomBytes2 = new byte[32];
		int numeroIteraciones= 512;
		int[] histograma = new int[numeroIteraciones];
		int[] significaciones = new int[numeroIteraciones];
		boolean significacionMayor = false;
		int signif = 0;
		int ultimaIteracion = 0;
		for(int i=0; numeroIteraciones > 0 && !significacionMayor; i++){
		random.nextBytes(randomBytes1);
		randomBytes2=randomBytes1.clone();	
		randomBytes2[0]= (byte) (randomBytes1[0] + 1);
		cifrador.update(randomBytes1, 0, randomBytes1.length);
		digested1 = new byte[cifrador.getDigestSize()];
		cifrador.doFinal(digested1, 0);
		cifrador.update(randomBytes2, 0, randomBytes2.length);
		digested2 = new byte[cifrador.getDigestSize()];
		cifrador.doFinal(digested2, 0);
		histograma[i] = pruebas.getHammingDistance(digested1, digested2);
		numeroIteraciones--;
		signif=pruebas.getHammingDistance(digested1, digested2)*100/32;
		if(signif > 90 && signif < 95) significacionMayor=true;
		significaciones[i]=signif;
		ultimaIteracion=i;
		}
		return ultimaIteracion;
	}
	
	
	public double mediaIterations(int numeroIteraciones){
		
		pruebas prueba = new pruebas();
		int numeroIntentos=numeroIteraciones;
		int media = 0;
		int[] arrayIteraciones = new int[numeroIntentos];
		for(int i=0;i<arrayIteraciones.length;i++)
			arrayIteraciones[i]=prueba.hammingDistanceRandomStringsIterations();
		System.out.println("\n"+Arrays.toString(arrayIteraciones)+"\n");
		for(int i=0;i<arrayIteraciones.length;i++)
			media+=arrayIteraciones[i];
		media=media/arrayIteraciones.length;
		return media;
	}
	
	
	public void hammingDistanceRandomStrings(){
		
		GOST3411Digest cifrador = new GOST3411Digest();
    	byte[] digested1 = null, digested2 = null;
		SecureRandom random = new SecureRandom();
		String reconstructedString = null;
		byte[] randomBytes1 = new byte[32];
		byte[] randomBytes2 = new byte[32];
		int numeroIteraciones= 500;
		int[] histograma = new int[numeroIteraciones];
		int[] significaciones = new int[numeroIteraciones];
		boolean significacionMayor = false;
		int signif = 0;
		int ultimaIteracion = 0;
		for(int i=0; numeroIteraciones > 0 && !significacionMayor; i++){
		random.nextBytes(randomBytes1);
		randomBytes2=randomBytes1.clone();	
		randomBytes2[0]= (byte) (randomBytes1[0] + 1);
		cifrador.update(randomBytes1, 0, randomBytes1.length);
		digested1 = new byte[cifrador.getDigestSize()];
		cifrador.doFinal(digested1, 0);
		cifrador.update(randomBytes2, 0, randomBytes2.length);
		digested2 = new byte[cifrador.getDigestSize()];
		cifrador.doFinal(digested2, 0);
		reconstructedString = new String(Hex.encode(digested1));
    	System.out.println("El bloque hexadecimal cifrado del String aleatorio original: " + reconstructedString);
		reconstructedString = new String(Hex.encode(digested2));
    	System.out.println("El bloque hexadecimal cifrado del String aleatorio modificado: " + reconstructedString);
		histograma[i] = pruebas.getHammingDistance(digested1, digested2);
		numeroIteraciones--;
		signif=pruebas.getHammingDistance(digested1, digested2)*100/32;
		if(signif > 90 && signif < 95) significacionMayor=true;
		significaciones[i]=signif;
		ultimaIteracion=i;
		}
		System.out.println(Arrays.toString(histograma));
		System.out.println(Arrays.toString(significaciones));
		System.out.println("La cantidad de iteraciones necesarias han sido: " + ultimaIteracion);
	}
	
	public static void main(String[] args){
    	
		pruebas prueba = new pruebas();
		GOST3411Digest cifrador = new GOST3411Digest();
		cifrador.reset();
		System.out.println("1.Calcular el numero de iteraciones de comparaciones entre dos Strings random quiere comparar con la distancia de Hamming: ");
		System.out.println("2.Cifrar un parametro de entrada por consola con el algoritmo: " + cifrador.getAlgorithmName());
		Scanner scanIn = new Scanner(System.in);
		int Command;
		try{
			Command = scanIn.nextInt();
			switch(Command){
			case 1: prueba.mediaIter(null); break;
			case 2: prueba.prueba(null); break;
			default: System.out.println("Error, introduzca una opcion valida.");
			}
		}
    	catch(InputMismatchException ie){
			System.out.println("Error, caracter no valido."); main(null);
		}
}
	
	public static void mediaIter(String[] args){
	    	
			pruebas prueba = new pruebas();
			double mediaIteraciones=0;
			int numeroIteraciones;
			System.out.println("Introduzca el numero de iteraciones de comparaciones entre dos Strings random quiere comparar con la distancia de Hamming: ");
	    	Scanner scanIn = new Scanner(System.in);
			try{
				numeroIteraciones = scanIn.nextInt();
				mediaIteraciones=prueba.mediaIterations(numeroIteraciones);
				System.out.println(mediaIteraciones+"\n");
				main(null);
			}
	    	catch(InputMismatchException ie){
				System.out.println("Error, caracter no valido."); main(null);
			}
	}
	
	public static void prueba(String[] args){
    	
    	GOST3411Digest cifrador = new GOST3411Digest();
    	String reconstitutedString, StringACifrar;
    	byte[] tobytes, digested;
    	System.out.println("\nIntroduzca el texto a crifrar por el algoritmo: " + cifrador.getAlgorithmName());			
    	Scanner scanIn = new Scanner(System.in);
		try{
			StringACifrar = scanIn.next();
			tobytes = StringACifrar.getBytes();
			cifrador.reset();
			cifrador.update(tobytes, 0, tobytes.length);
			digested = new byte[cifrador.getDigestSize()];
			cifrador.doFinal(digested, 0);
			reconstitutedString = new String(Hex.encode(digested));
			System.out.println("\nEl bloque hexadecimal cifrado: " + reconstitutedString + "\n");
			main(null);
		}
    	catch(InputMismatchException ie){
			System.out.println("Error, caracter no valido."); main(null);
		}
}

}
