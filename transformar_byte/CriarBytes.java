package transformar_byte;

import java.io.RandomAccessFile;
import scr.Games;

public class CriarBytes {
    public static void main(String[] args) {
        
        RandomAccessFile raf;
        
        try {
            raf = new RandomAccessFile ("./transformar_byte/gamesNAOAPAGAR.csv", "r");
            raf.seek(80);
            
            while (raf.readLine() != null) {
                String aux = raf.readLine();
                String vet[] = aux.split("./;");
                Games novo = new Games(Integer.parseInt(vet[0]), Integer.parseInt(vet[1]), Integer.parseInt(vet[2]), vet[3], vet[4], vet[5], vet[6], null);
                novo.writeBytes();
            }
            
            raf.close();
        } catch (Exception e) {
            System.out.println("Erro");
        }


    }
    
}