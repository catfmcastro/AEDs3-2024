package RSA;

import java.util.concurrent.ThreadLocalRandom;

public class CriptografiaRSA {

    private long p, q, n, z, d, e;

    public CriptografiaRSA() {
        // Gera números primos p e q
        p = generatePrime(10000, 100000);
        q = generatePrime(10000, 100000);
        n = p * q;  // Calcula o produto dos primos p e q
        z = (p - 1) * (q - 1);  // Função totiente de n
        d = findNextPrime(z);  // Encontra um primo próximo a z para d
        e = findE();  // Calcula e tal que (e * d) % z == 1
    }

    public String criptografarRSA(String texto) {
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            resultado.append(calculoRSAcripto(c, e, n));
        }
        return resultado.toString();
    }

    public String descriptografarRSA(String textoCriptografado) {
        StringBuilder resultado = new StringBuilder();
        for (char c : textoCriptografado.toCharArray()) {
            resultado.append(calculoRSAdescripto(c, d, n));
        }
        return resultado.toString();
    }

    private char calculoRSAdescripto(char letra, long chave, long n) {
        long acumulador = 1;
        String binary = longToBinaryString(chave);
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                acumulador = (acumulador * acumulador * letra) % n;
            } else {
                acumulador = (acumulador * acumulador) % n;
            }
        }
        return (char) acumulador;
    }

    private char calculoRSAcripto(char letra, long chave, long n) {
        long acumulador = 1;
        String binary = longToBinaryString(chave);
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                acumulador = (acumulador * acumulador * letra) % n;
            } else {
                acumulador = (acumulador * acumulador) % n;
            }
        }
        return (char) acumulador;
    }

    public static String longToBinaryString(long number) {
        // Converte o número long para uma string binária
        StringBuilder sb = new StringBuilder(Long.toBinaryString(number));
        // Remove os zeros à esquerda
        int startIdx = sb.indexOf("1");
        if (startIdx != -1) {
            return sb.substring(startIdx);
        } else {
            return "0";
        }
    }

    public static long generatePrime(int lowerBound, int upperBound) {
        long prime;
        do {
            prime = ThreadLocalRandom.current().nextLong(lowerBound, upperBound);
        } while (!isPrime(prime));
        return prime;
    }

    public static boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    public static long findNextPrime(long n) {
        if (n <= 1) {
            return 2;
        }
        long prime = n + 1;
        while (!isPrime(prime)) {
            prime++;
        }
        return prime;
    }

    public long findE() {
        long e = 2;  // Começa com um valor inicial para e
        while ((e * d) % z != 1) {
            e++;
        }
        return e;
    }
}
