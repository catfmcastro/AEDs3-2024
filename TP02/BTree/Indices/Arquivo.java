package BTree.Indices;

import java.io.RandomAccessFile;
import Model.Games;

public class Arquivo {
    public static int dateToHours(String data) {

        String tmp[] = data.split("/");
        int year, month, day;

        year = (Integer.parseInt(tmp[2]) - 1900) * 8760;
        month = (Integer.parseInt(tmp[1])) * 730;
        day = Integer.parseInt(tmp[0]) * 24;

        return year + month + day;
    }

    public static void main(String[] args) {
        RandomAccessFile csv, write, index, readBD;

        try {
            csv = new RandomAccessFile("./TP02/DB/games.csv", "r");
            write = new RandomAccessFile("./TP02/BTree/Indices/aux.bd", "rw");
            csv.readLine();
            String str;

            write.writeLong(10); // Reserva um espaço no inicio do arquivo para inserir a posição do final do
                                 // arquivo

            System.out.println("Carregando dados para o arquivo...");

            while ((str = csv.readLine()) != null) {
                String vet[] = str.split("./;"); // Separando em vetor a string
                Games tmp = new Games(
                        false,
                        Integer.parseInt(vet[0]),
                        Integer.parseInt(vet[1]),
                        Float.parseFloat(vet[3]),
                        vet[2],
                        vet[4],
                        vet[5],
                        vet[6],
                        vet[7],
                        dateToHours(vet[8]));

                byte aux[] = tmp.byteParse(); // Insere registro no arquivo
                write.writeInt(aux.length); // Tam. do registro antes de cada vetor
                write.write(aux); // Insere o vetor de dados de byte
            }

            long last = write.getFilePointer(); // Última posição do arquivo
            write.seek(0); // Posiciona no início do arquivo
            write.writeLong(last); // Escreve a ultima posicao no início

            write.close();
            csv.close();

            index = new RandomAccessFile("./TP02/BTree/Indices/indices.bd", "rw");
            readBD = new RandomAccessFile("./TP02/BTree/Indices/aux.bd", "r");
            // lerindice = new RandomAccessFile("./TP02/BTree/Indices/indices.bd", "r");

            int tam;
            long maxtam = readBD.readLong(); // leu tamanho maximo
            System.out.println(maxtam);

            do {
                Games temp = new Games();

                long ponteiro = readBD.getFilePointer();
                tam = readBD.readInt();
                byte aux[] = new byte[tam];

                readBD.read(aux);
                temp.fromByteArray(aux);

                System.out.println(ponteiro + " " + temp.getId());

                index.writeLong(ponteiro);
                index.writeInt(temp.getId());

                // long posaux = lerindice.readLong();
                // readBD.seek(posaux);

                // Games temporario = new Games();
                // int auxtam = readBD.readInt();
                // byte teste[] = new byte[auxtam];
                // readBD.read(teste);

                // System.out.println(posaux + " - " + auxtam);
                
                // temporario.fromByteArray(teste);
                // temporario.printGame();

                // Thread.sleep(3000);
            } while (tam != maxtam);
            
            index.close();
            readBD.close();
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
