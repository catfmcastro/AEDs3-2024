package RSA;

import java.util.Base64;
import java.util.Random;

public class RSA {

    private static final int BIT_LENGTH = 1024;
    private static final Random RANDOM = new Random();

    private long p, q, n, phi, e, d;

    public RSA() {
        p = generatePrime();
        q = generatePrime();
        n = p * q;
        phi = (p - 1) * (q - 1);
        e = findE(phi);
        d = modInverse(e, phi);
    }

    public String encrypt(String message) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            long encryptedChar = modPow(message.charAt(i), e, n);
            encrypted.append(encryptedChar).append(" ");
        }
        return Base64.getEncoder().encodeToString(encrypted.toString().getBytes());
    }

    public String decrypt(String encryptedMessage) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedMessage);
        String[] encryptedChars = new String(decodedBytes).split(" ");
        StringBuilder decrypted = new StringBuilder();
        for (String encryptedChar : encryptedChars) {
            if (!encryptedChar.isEmpty()) {
                long decryptedChar = modPow(Long.parseLong(encryptedChar), d, n);
                decrypted.append((char) decryptedChar);
            }
        }
        return decrypted.toString();
    }

    private long generatePrime() {
        long prime;
        do {
            prime = Math.abs(RANDOM.nextLong() % BIT_LENGTH) + BIT_LENGTH;
        } while (!isPrime(prime));
        return prime;
    }

    private boolean isPrime(long num) {
        if (num <= 1) return false;
        for (long i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    private long findE(long phi) {
        long e;
        do {
            e = Math.abs(RANDOM.nextLong() % phi);
        } while (gcd(e, phi) != 1);
        return e;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private long modInverse(long a, long m) {
        long m0 = m, t, q;
        long x0 = 0, x1 = 1;

        if (m == 1) return 0;

        while (a > 1) {
            q = a / m;
            t = m;
            m = a % m;
            a = t;
            t = x0;
            x0 = x1 - q * x0;
            x1 = t;
        }

        if (x1 < 0) x1 += m0;

        return x1;
    }

    private long modPow(long base, long exponent, long modulus) {
        if (modulus == 1) return 0;
        long result = 1;
        base = base % modulus;
        while (exponent > 0) {
            if ((exponent % 2) == 1) result = (result * base) % modulus;
            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }
        return result;
    }
}
