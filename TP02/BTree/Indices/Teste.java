package BTree.Indices;

import java.io.RandomAccessFile;

import javax.sound.midi.Track;

import Model.Games;

public class Teste {
    public static void main(String[] args) {
        try {
            RandomAccessFile raf = new RandomAccessFile("./TP02/BTree/Indices/indices.bd", "r");
            RandomAccessFile file = new RandomAccessFile("./TP02/BTree/Indices/aux.bd", "r");
            for(int i = 0; i < 100; i++){
                long pos = raf.readLong();
                int id = raf.readInt();
                file.seek(pos);
                int tam = file.readInt();
                byte vet[] = new byte[tam];
                file.read(vet);
                Games aux = new Games();
                aux.fromByteArray(vet);
                aux.printGame();
            }
            raf.close();
            file.close();
        } catch (Exception e) {
            System.out.println("Erro abrir: " + e.getMessage());
        }
    }
}
