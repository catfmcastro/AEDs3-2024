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
        this.padrao = padrao;
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

        for (Node node : ruim) {
            if (node != null) {
                System.out.println(node);
            }
        }
    }

    public void criarBom() {
        int tam = padrao.length() - 1;
        this.bom[tam] = 1;
        int posicao = padrao.length();

        for (int i = tam; i > 0; i--) {
            boolean var = false;
            int j = i - 1;

            for (; j >= 0; j--) {

                if (padrao.charAt(i) == padrao.charAt(j)) {
                    posicao = j;

                    if (j > 0 && padrao.charAt(i - 1) != padrao.charAt(j - 1)) {
                        int temp = j + 1;
                        var = true;
                        for (int k = i + 1; k < tam; k++, temp++) {
                            if (padrao.charAt(k) != padrao.charAt(temp)) {
                                var = false;
                                break;
                            }
                        }

                        if (var) {
                            bom[i - 1] = i - posicao;
                            break;
                        }

                    }
                    
                    if(!var){
                        for (int k = i; k < tam; k++) {
                            if (padrao.charAt(k) == padrao.charAt(0)) {
                                int temp = 1;
                                var = true;
                                for (int k2 = k + 1; k < tam; k2++, temp++) {
                                    if (padrao.charAt(k2) != padrao.charAt(temp)) {
                                        var = false;
                                        break;
                                    }
                                }
                            }
                        }
                        
                        if (var) {
                            bom[i - 1] = i;
                            break;
                        }
                    }

                }
            }
            if (!var) {
                bom[i - 1] = tam + 1;
            }
        }

        // int m = padrao.length();
        // bom = new int[m];
        // int[] suffix = new int[m];

        // // Passo 1: Inicializa a tabela `suffix`
        // for (int i = 0; i < m; i++) {
        // suffix[i] = -1;
        // }

        // // Passo 2: Preenche a tabela `suffix`
        // for (int i = 0; i < m - 1; i++) {
        // int j = i;
        // int k = 0; // Tamanho do sufixo correspondente
        // while (j >= 0 && padrao.charAt(j) == padrao.charAt(m - 1 - k)) {
        // j--;
        // k++;
        // suffix[k] = j + 1;
        // }
        // }

        // // Passo 3: Inicializa a tabela `bom`
        // for (int i = 0; i < m; i++) {
        // bom[i] = m; // Valor padrão: comprimento do padrão
        // }

        // // Passo 4: Preenche a tabela `bom` baseada nos sufixos
        // int j = 0;
        // for (int k = 0; k < m - 1; k++) {
        // if (suffix[k] != -1) {
        // bom[m - 1 - k] = m - 1 - suffix[k];
        // } else {
        // bom[m - 1 - k] = m - k;
        // }
        // }

        for (int i = 0; i < bom.length; i++) {
            System.out.println("Posição: " + i + " Valor: " + bom[i]);
        }

    }

}
