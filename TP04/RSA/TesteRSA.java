package RSA;

public class TesteRSA {
    public static void main(String[] args) {
        CriptografiaRSA rsa = new CriptografiaRSA();
        String aux = "Oi cara";
        String temp = rsa.criptografarRSA(aux);
        String des = rsa.descriptografarRSA(temp);
        System.out.println(aux);
        System.out.println(temp);
        System.out.println(des);
    }
}
