package scr;

import scr.Games;

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

    // Esqueleto da função para achar um novo jogo já recebendo o id dele
    public void read(int ID) {
        try {
            raf.seek(0);

            System.out.println("-------------");
            Games novo = new Games();

            raf.seek(0);
            raf.readLong();
            byte[] bi = new byte[raf.readInt()];
            raf.read(bi);
            novo.readByteArray(bi);
            novo.printScreen();
        } catch (Exception e) {
            System.out.println("Erro read: " + e.getMessage());
        }
    }

    // Esqueleto da função para fazer update de um novo jogo já recebendo o id dele
    public boolean Update(int ID) {

        // Posiciona o ponteiro no inicio do arquivo - (1)
        // Procura pelo ID correto - (2)
        // Compara os tamanhos para ver se vai ou nao de vasco - (3)
        // retorna se foi feito com sucesso ou nao - (4)

        boolean var = false;
        try {
            var = true;
        } catch (Exception e) {
            System.out.println("Erro " + e);
        }

        return var;
    }

    // Esqueleto da função para deletar um jogo já recebendo o id dele
    public Games Delete(int ID) {
        // Posiciona o ponteiro no inicio do arquivo - (1)
        // Procura pelo ID desejado - (2)
        // Ao encontrar atualiza o dado para true - (3)
        // Retorna o game removido (4)

        Games var = new Games();
        int pos = 8;
        try {
            raf.seek(pos);
            for (int i = 0; i < ID; i++) {
                pos += raf.readInt();
                raf.seek(pos);
            }
            raf.readInt(); // ler o tamanho do vetor de byte
            raf.readInt(); // ler o ID
            raf.writeBoolean(true); // mudar o boolean, MAS precisa dos IFs pra ver se ele já nao estava deletado

        } catch (Exception e) {
            System.out.println("Erro " + e);
        }

        return var; // (4)
    }
}