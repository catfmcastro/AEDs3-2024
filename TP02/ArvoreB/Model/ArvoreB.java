package ArvoreB.Model;

public class ArvoreB {
    private long vetPonterios[];
    private int vetElemets[];

    public ArvoreB(){
        this.vetElemets = new int[100];
        this.vetPonterios = new long[100];
    }

    private void insertionSort (){
        for(int i = 1; i < vetElemets.length; i++){
            int j = i - 1;
            int temp = vetElemets[i];
            while (j >= 0 && temp < vetElemets[j]) {
                vetElemets[i] = vetElemets[j];
                j--;
            }
            vetElemets[i] = temp;
        }
    }

    // private byte[] tobyteArray (){
    //     byte vet[] 

    //     return vet;
    // }

    

}
