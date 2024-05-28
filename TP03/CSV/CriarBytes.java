import java.io.RandomAccessFile;
import Model.Games;

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

            raf = new RandomAccessFile("./CSV/games.csv", "r");
            write = new RandomAccessFile("./BD/games.db", "rw");
            raf.readLine();

            String str;

            write.writeLong(10);

            System.out.println("iniciando load...");
            
            // id./;steamID./;name./;price./;short_descritiption./;genres./;publishers./;roda_linux./;release_date

            while ((str = raf.readLine()) != null) {

                String vet[] = str.split("./;"); // Separando em vetor a string
                
                Games tmp = new Games(Integer.parseInt(vet[0]), Integer.parseInt(vet[1]), vet[2], Float.parseFloat(vet[3]), vet[4], vet[5], vet[6], vet[7], trasnformDate(vet[8]), false);
                
                byte aux[] = tmp.getBytes();
                
                write.writeInt(aux.length);
                
                write.write(aux);
            }

            long pointer = write.getFilePointer(); // Pega a ultima posicao do arquivo
            write.seek(0); // Posiciona novamente no inicio do arquivo
            write.writeLong(pointer); // Escreve a ultima posicao na frente
            
            write.close(); 
            raf.close();

            System.out.println("load finalizado com sucesso");

        } catch (Exception e) {

            System.out.println("Erro nos arquivos" + e.getMessage());
        }
    }
}