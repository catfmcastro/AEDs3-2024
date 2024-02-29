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

    // Esqueleto da função para criar um novo jogo já recebendo o jogo criado
    public void create(Games tmp) {
        try {
            System.out.println("Posicao antes: " + finalPosition);
            maxId++;
            raf.seek(finalPosition);
            byte[] aux = tmp.createbyteArray();
            raf.writeInt(aux.length);
            raf.write(aux);
            finalPosition += aux.length;
            System.out.println("Posicao depois: " + finalPosition);
        } catch (IOException e) {
            System.out.println("Erro create");
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
                pos += tam + 4;
            }
            return aux;
        } catch (Exception e) {
            System.out.println("Erro read: " + e.getMessage());
            return aux;
        }
    }

    // Esqueleto da função para fazer update de um novo jogo já recebendo o id dele
    public void update(int ID) {

        // Posiciona o ponteiro no inicio do arquivo - (1)
        // Procura pelo ID correto - (2)
        // Compara os tamanhos para ver se vai ou nao de vasco - (3)
        // retorna se foi feito com sucesso ou nao - (4)
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
                }
                pos += tam + 4;
            }
        } catch (Exception e) {
            System.out.println("");
        }


    }

    // Esqueleto da função para deletar um jogo já recebendo o id dele
    public void delete(int ID) {
        // Posiciona o ponteiro no inicio do arquivo - (1)
        // Procura pelo ID desejado - (2)
        // Ao encontrar atualiza o dado para true - (3)
        // Retorna o game removido (4)
    }
}