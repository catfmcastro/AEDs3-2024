package scr;

import scr.Games;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class CRUD {

    private long finalPosition; // Ponteiro que marca o final do arquivo
    private int maxId; // Ultimo jogo do arquivo antes do fim
    RandomAccessFile raf = new RandomAccessFile("./Bagunca_tupac/scr/db/games.db", "rw"); // Arquivo BD

    public CRUD() throws IOException {
        finalPosition = raf.readLong(); // Ao ser criado ele lê do arquivo e guarda o local da ultima posição
        maxId = 59431;
    }

    protected void finalize (){
        try {
            raf.close();
        } catch (Exception e) {
            System.out.println("Erro ao fechar arquivo");
        }
    }

    // Esqueleto da função para criar um novo jogo já recebendo o jogo criado
    public boolean create(Games tmp) {
        try {
            maxId++;
            raf.seek(finalPosition);
            byte[] aux = tmp.createbyteArray();
            raf.writeInt(aux.length);
            raf.write(aux);
            finalPosition += aux.length;
            return true;
        } catch (IOException e) {
            System.out.println("Erro create");
            return false; 
        }
    }

    private boolean isID(byte array[], int ID) {
        ByteArrayInputStream var = new ByteArrayInputStream(array);
        DataInputStream dos = new DataInputStream(var);

        try {
            if (!dos.readBoolean() && dos.readInt() == ID) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro checkID: " + e.getMessage());
            return false;
        }
    }

    // Esqueleto da função para achar um novo jogo já recebendo o id dele
    public Games read(int ID) {

        Games aux = new Games();
        byte[] tempVet;
        long pos = 8;

        try {
            raf.seek(pos);
            for(int i = 0; i < maxId; i++){
                int tam = raf.readInt();
                tempVet = new byte[tam];
                raf.read(tempVet);
                if(isID(tempVet, ID)){
                    aux.readByteArray(tempVet);
                    return aux;
                }
                pos += tam;
            }
            return aux;
        } catch (Exception e) {
            System.out.println("Erro read: " + e.getMessage());
            return aux;
        }
    }

    // Esqueleto da função para fazer update de um novo jogo já recebendo o id dele
    public boolean update(int ID, Games insert) {

        // Posiciona o ponteiro no inicio do arquivo - (1)
        // Procura pelo ID correto - (2)
        // Compara os tamanhos para ver se vai ou nao de vasco - (3)
        // retorna se foi feito com sucesso ou nao - (4)
        Games aux = new Games();
        byte[] temp;
        long pos = 8;

        try {
            raf.seek(pos);

            for (int i = 0; i < maxId; i++) {
                int tam = raf.readInt();
                temp = new byte[tam];
                raf.read(temp);
                if (isID(temp, ID)) {
                    if(tam >= insert.createbyteArray().length){
                        raf.seek(pos+4);
                        raf.write(insert.createbyteArray());
                        return true;
                    } else {
                        raf.seek(pos+4);
                        aux.readByteArray(temp);
                        aux.setGrave(true);
                        raf.write(aux.createbyteArray());
                        create(insert);
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erro update: " + e.getMessage());
            return false;
        }
    }

    // Esqueleto da função para deletar um jogo já recebendo o id dele
    public Games delete(int ID) {
        // Posiciona o ponteiro no inicio do arquivo - (1)
        // Procura pelo ID desejado - (2)
        // Ao encontrar atualiza o dado para true - (3)
        // Retorna o game removido (4)
        Games aux = new Games();
        byte[] temp;
        long pos = 8;

        try {
            raf.seek(pos);
            for (int i = 0; i < maxId; i++) {
                int tam = raf.readInt();
                temp = new byte[tam];
                raf.read(temp);
                if (isID(temp, ID)) {
                    raf.seek(pos+4);
                    aux.readByteArray(temp);
                    aux.setGrave(true);
                    raf.write(aux.createbyteArray());
                    return aux;
                }
            }
            return aux;
        } catch (Exception e) {
            System.out.println("Erro delete: " + e.getMessage());
            return aux;
        }

    }
}