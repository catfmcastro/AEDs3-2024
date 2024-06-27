package BoyerMoore;

public class PadraoBooyerMoore {

    private String padrao;
    private final int mod = 25;
    private Node ruim[] = new Node[25];
    private int bom[];

    public class Node {
        private char letra;
        private int posicao;

        public Node(char letra, int posicao) {
            this.letra = letra;
            this.posicao = posicao;
        }

        public String toString() {
            return "Letra (" + this.letra + ") Posicao = " + this.posicao;
        }
    }

    public PadraoBooyerMoore(String padrao) {
        this.padrao = padrao.toLowerCase();
        bom = new int[padrao.length()];
        criarRuim();
        criarBom();
    }

    public void criarRuim() {
        char[] aux = padrao.toLowerCase().toCharArray();

        for (int i = aux.length - 2; i >= 0; i--) {
            int posicao = aux[i] % this.mod;
            if (ruim[posicao] == null) {
                ruim[posicao] = new Node(aux[i], i);
            }
        }

        int lastCharPos = aux.length - 1;
        int lastCharIndex = aux[lastCharPos] % mod;

        if (ruim[lastCharIndex] == null) {
            ruim[lastCharIndex] = new Node(aux[lastCharPos], -1);
        }

        // for (Node node : ruim) {
        //     if (node != null) {
        //         System.out.println(node);
        //     }
        // }
    }

    public void criarBom() {
        int tam = padrao.length();
        bom = new int[tam];
        int[] suff = new int[tam];
    
        suff[tam - 1] = tam;
        int g = tam - 1, f = 0;
        for (int i = tam - 2; i >= 0; --i) {
            if (i > g && suff[i + tam - 1 - f] < i - g) {
                suff[i] = suff[i + tam - 1 - f];
            } else {
                if (i < g) g = i;
                f = i;
                while (g >= 0 && padrao.charAt(g) == padrao.charAt(g + tam - 1 - f)) {
                    --g;
                }
                suff[i] = f - g;
            }
        }
    
        for (int i = 0; i < tam; ++i) {
            bom[i] = tam;
        }
    
        int j = 0;
        for (int i = tam - 1; i >= -1; --i) {
            if (i == -1 || suff[i] == i + 1) {
                for (; j < tam - 1 - i; ++j) {
                    if (bom[j] == tam) {
                        bom[j] = tam - 1 - i;
                    }
                }
            }
        }
    
        for (int i = 0; i <= tam - 2; ++i) {
            bom[tam - 1 - suff[i]] = tam - 1 - i;
        }
        

        // for (int i = 0; i < bom.length; i++) {
        //     System.out.println("Posição: " + i + " Valor: " + bom[i]);
        // }

    }

    public boolean buscarPadrao(String texto) {
        texto = texto.toLowerCase();
        int m = padrao.length();
        int n = texto.length();

        int s = 0; 
        while (s <= (n - m)) {
            int j = m - 1;

            while (j >= 0 && padrao.charAt(j) == texto.charAt(s + j)) {
                j--;
            }

            if (j < 0) {
                return true;
                

            } else {
                char badChar = texto.charAt(s + j);
                int ruimIndex = badChar % mod;
                int badCharShift = ruim[ruimIndex] == null ? j + 1 : Math.max(1, j - ruim[ruimIndex].posicao);
                int goodSuffixShift = bom[j];
                s += Math.max(badCharShift, goodSuffixShift);
            }
        }
        return false;
    }


}
