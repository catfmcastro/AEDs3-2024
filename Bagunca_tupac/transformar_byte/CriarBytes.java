package transformar_byte;

import java.io.RandomAccessFile;
import scr.Games;

public class CriarBytes {

    // Constante para manipulação de data a partir de 1900, pois o dado mais recente
    // do BD é de 1999
    static final int inicialYear = 1900;

    // Transformar uma data em horas a partir de 1900
    public static int trasnformDate(String data) {

        // Um ano tem 8766 h
        // Um mês tem 730 h
        // Um dia tem 24 h

        String tmp[] = data.split("/");
        int year, month, day;

        year = (Integer.parseInt(tmp[2]) - inicialYear) * 8760;
        month = (Integer.parseInt(tmp[1])) * 730;
        day = Integer.parseInt(tmp[0]) * 24;

        return year + month + day;
    }
    
    public static void main(String[] args) {

        RandomAccessFile raf, write;

        try {
            // Acessando o arquivo para poder realizar a leitura e colocar no BD em bytes
            raf = new RandomAccessFile("./Bagunca_tupac/transformar_byte/games_tobyte.csv", "r");
            write = new RandomAccessFile("./TP01/out/games.db", "rw");
            raf.readLine(); // Não estou usando raf.seek devido a algum espaço nulo no arquivo que não sei onde está
            String str;

            write.writeLong(10); // Reserva um espaço no inicio do arquivo como um long int para inserir a posição do final do arquivo

            System.out.println("iniciando load...");
            while ((str = raf.readLine()) != null) {
                String vet[] = str.split("./;"); // Separando em vetor a string
                Games tmp = new Games(false, Integer.parseInt(vet[0]), Integer.parseInt(vet[1]), Float.parseFloat(vet[3]), vet[2], vet[4], vet[5], vet[6], vet[7], trasnformDate(vet[8])); // Salva o valor em um game temporario
                byte aux[] = tmp.createbyteArray(); // Insere no arquivo DB
                write.writeInt(aux.length); // Coloca o tamanho antes do vetor de dados de byte
                write.write(aux); // Insere o vetor de dados de byte
            }

            long pointer = write.getFilePointer(); // Pega a ultima posicao do arquivo
            write.seek(0); // Posiciona novamente no inicio do arquivo
            write.writeLong(pointer); // Escreve a ultima posicao na frente
            
            write.close(); 
            raf.close();
            System.out.println("load finalizado com sucesso");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}