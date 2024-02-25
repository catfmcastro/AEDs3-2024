package src;

import java.io.IOException;
import java.io.RandomAccessFile;
import src.Games;

public class CRUD {

  private long finalPosition;
  private int maxId; // Ultimo jogo do arquivo, antes do fim
  RandomAccessFile raf = new RandomAccessFile("./db/games.db", "rw");

  public CRUD() throws IOException {
    finalPosition = raf.readLong(); // guarda a da ultima posição do arquivo
    maxId = 59431;
  }

  // Esqueleto da função para criar um novo jogo já recebendo o jogo criado para
  // passar ao arquivo
  public boolean Create(Games tmp) {
    // Posicionar ponteiro no final - (1)
    // Criar o jogador - (2)
    // Escrever no arquivo o jogador - (3)
    // Atualizar o ponteiro para o final do arquivo - (4)
    // Incrementar o maxID (5)

    boolean done = false;
    try {
      raf.seek(finalPosition); // (1)
      byte aux[] = tmp.toByteArray(); // (2)
      raf.write(aux.length); // (3)
      raf.write(aux); // (3)
      finalPosition += aux.length + 4; // (4), mais 4 é para pegar o inteiro que diz quantos bytes se devem ler
      maxId++; // (5)
      done = true;
    } catch (Exception e) {
      System.out.println("Erro na função Create:" + e);
      done = false;
    }
    return done;
  }

  // Esqueleto da função para achar um novo jogo já recebendo o id dele
  public Games Read(int ID) {
    // Mover o ponteiro para o incio do arquivo - (1)
    // Ir lendo até chegar no ID procurado - (2)
    // Verificar se ele é valido ou nao - (3)
    // Retornar os dados do jogador encontrado - (4)

    Games var = new Games(); // Jogo que deve ser retornado mesmo se nao encontrar
    int pos = 8; // Pula o longint e já vai para o primeiro registro

    try {
      raf.seek(pos); // (1)

      for (int i = 0; i < ID; i++) { // (2)
        pos += raf.readInt();
        raf.seek(pos);
      }

      int tamGame = raf.readInt(); // tamanho do vetor de bytes a ser criado caso o dado encontrado nao tenha sido
      // apagado ou sofrido update

      if (!raf.readBoolean()) { // (3) - se for válido ele retorna o game, caso contrário ele volta a procurar
        byte tmp[] = new byte[tamGame];
        raf.seek(pos);
        var.fromByteArray(tmp);
      } else { // Procurando se existe outro game com mesmo id disponivel
        pos += tamGame;
        raf.seek(pos);
        for (int i = ID; i < maxId; i++) {
          if (raf.readInt() == ID && !raf.readBoolean()) { // Se for o id desejado retorna o game
            raf.seek(pos);
            tamGame = raf.readInt();
            byte tmp[] = new byte[tamGame];
            var.fromByteArray(tmp);
            break;
          } else { // Se não muda a posição do ponteiro e vai mechendo
            pos += raf.readInt();
          }
        }
      }
    } catch (Exception e) {
      System.out.println("Erro na função Read: " + e);
    }

    return var; // (4)
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
      System.out.println("Erro na função Update: " + e);
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
      System.out.println("Erro na função Delete: " + e);
    }

    return var; // (4)
  }
}
