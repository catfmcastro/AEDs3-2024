/*
 * Trabalho Prático 01
 * 
 * @Tupacao - Arthur Santos
 * @catfmcastro - Catarina F. M. Castro
 * 
 * AEDs III
*/

import java.io.RandomAccessFile;

import src.Games;
import src.Crud;

public class App {
    public static void main(String[] args) {
        RandomAccessFile raf, write;

        // try {
        //     // Acessando o arquivo
        //     raf = new RandomAccessFile("./TP01/src/games.csv", "r");
        //     write = new RandomAccessFile("./TP01/out/games.db", "rw");
        //     raf.readLine(); // Não estou usando raf.seek devido a algum espaço nulo no arquivo que não sei
        //                     // onde está
        //     String str;

        //     while ((str = raf.readLine()) != null) {
        //         String vet[] = str.split("./;"); // Separando em vetor a string
        //         Games tmp = new Games(Integer.parseInt(vet[0]), Integer.parseInt(vet[1]), Float.parseFloat(vet[3]),
        //                 vet[2], vet[4], vet[5], vet[6], trasnformDate(vet[7])); // Salva o valor em um game temporario
        //         byte aux[] = tmp.createbyteArray(); // Insere no arquivo DB
        //         write.write(aux.length); // Coloca o tamanho antes do vetor de dados de byte
        //         write.write(aux); // Insere o vetor de dados de byte
        //     }

        //     write.close();
        //     raf.close();
        // } catch (Exception e) {
        //     System.out.println(e);
        // }
    }
}
