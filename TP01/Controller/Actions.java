package Controller;

import Model.Games;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Actions {

  private long finalPosition;
  private int maxId; // Ultimo jogo do arquivo, antes do fim
  static final int inicialYear = 1900; // Ano inicial para a conversão da data
  RandomAccessFile file;

  public void openFile() throws IOException {
    try {
      file = new RandomAccessFile("./db/games.db", "rw");
      finalPosition = file.readLong(); // guarda a da ultima posição do arquivo
      maxId = 59431;
    } catch (Exception e) {
      System.out.println("Erro ao abrir o .db: " + e);
    }
  }

  public void closeFile() throws IOException {
    try {
      file.close();
    } catch (Exception e) {
      System.out.println("Erro ao fechar arquivo .db: " + e);
    }
  }

  // Transformar data em horas a partir de 1900
  public static int dateToHours(String data) {
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

  // Carrega info. do .csv para o .db
  public void loadData() {
    RandomAccessFile raf, write;
    try {
      // Acessando o arquivo
      raf = new RandomAccessFile("./db/games.csv", "r");
      //write = new RandomAccessFile("./out/games.db", "rw");
      raf.readLine(); // ! raf.seek nao funciona por causa de algum espaço nulo

      System.out.println("Carregando dados para o Banco...");

      String str;
      file.writeLong(10); // Reserva um espaço no inicio do arquivo como um long int para inserir a posição do final do arquivo


      System.out.println("inciando escrita");
      // Preenchimento do arquivo binário
      while ((str = raf.readLine()) != null) {
        // Leitura do csv
        String vet[] = str.split("./;");
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
          dateToHours(vet[8])
        );

        byte aux[] = tmp.toByteArray(); // Insere no arquivo binário
        file.write(aux.length); // Add o tamanho de cada vetor antes dos dados
        file.write(aux); // Insere o vetor de bytes
        System.out.println("obj add no file!");
      }

      long last = file.getFilePointer(); // Última posicao do arquivo
      file.seek(0); // Posiciona novamente no inicio do arquivo
      file.writeLong(last); // Escreve a ultima posicao na frente

      // ? testando toString

      raf.close();
      System.out.println("Dados carregados com sucesso!");
    } catch (Exception e) {
      System.out.println("Erro ao preencher o arquivo binário: " + e);
    }
  }
}
// // Esqueleto da função para criar um novo jogo já recebendo o jogo criado para
// // passar ao arquivo
// public boolean Create(Games tmp) {
//   // Posicionar ponteiro no final - (1)
//   // Criar o jogador - (2)
//   // Escrever no arquivo o jogador - (3)
//   // Atualizar o ponteiro para o final do arquivo - (4)
//   // Incrementar o maxID (5)
//   boolean done = false;
//   try {
//     raf.seek(finalPosition); // (1)
//     byte aux[] = tmp.toByteArray(); // (2)
//     raf.write(aux.length); // (3)
//     raf.write(aux); // (3)
//     finalPosition += aux.length + 4; // (4), mais 4 é para pegar o inteiro que diz quantos bytes se devem ler
//     maxId++; // (5)
//     done = true;
//   } catch (Exception e) {
//     System.out.println("Erro na função Create:" + e);
//     done = false;
//   }
//   return done;
// }
// // Esqueleto da função para achar um novo jogo já recebendo o id dele
// public Games Read(int ID) {
//   // Mover o ponteiro para o incio do arquivo - (1)
//   // Ir lendo até chegar no ID procurado - (2)
//   // Verificar se ele é valido ou nao - (3)
//   // Retornar os dados do jogador encontrado - (4)
//   Games var = new Games(); // Jogo que deve ser retornado mesmo se nao encontrar
//   int pos = 8; // Pula o longint e já vai para o primeiro registro
//   try {
//     raf.seek(pos); // (1)
//     for (int i = 0; i < ID; i++) { // (2)
//       pos += raf.readInt();
//       raf.seek(pos);
//     }
//     int tamGame = raf.readInt(); // tamanho do vetor de bytes a ser criado caso o dado encontrado nao tenha sido
//     // apagado ou sofrido update
//     if (!raf.readBoolean()) { // (3) - se for válido ele retorna o game, caso contrário ele volta a procurar
//       byte tmp[] = new byte[tamGame];
//       raf.seek(pos);
//       var.fromByteArray(tmp);
//     } else { // Procurando se existe outro game com mesmo id disponivel
//       pos += tamGame;
//       raf.seek(pos);
//       for (int i = ID; i < maxId; i++) {
//         if (raf.readInt() == ID && !raf.readBoolean()) { // Se for o id desejado retorna o game
//           raf.seek(pos);
//           tamGame = raf.readInt();
//           byte tmp[] = new byte[tamGame];
//           var.fromByteArray(tmp);
//           break;
//         } else { // Se não muda a posição do ponteiro e vai mechendo
//           pos += raf.readInt();
//         }
//       }
//     }
//   } catch (Exception e) {
//     System.out.println("Erro na função Read: " + e);
//   }
//   return var; // (4)
// }
// // Esqueleto da função para fazer update de um novo jogo já recebendo o id dele
// public boolean Update(int ID) {
//   // Posiciona o ponteiro no inicio do arquivo - (1)
//   // Procura pelo ID correto - (2)
//   // Compara os tamanhos para ver se vai ou nao de vasco - (3)
//   // retorna se foi feito com sucesso ou nao - (4)
//   boolean var = false;
//   try {
//     var = true;
//   } catch (Exception e) {
//     System.out.println("Erro na função Update: " + e);
//   }
//   return var;
// }
// // Esqueleto da função para deletar um jogo já recebendo o id dele
// public Games Delete(int ID) {
//   // Posiciona o ponteiro no inicio do arquivo - (1)
//   // Procura pelo ID desejado - (2)
//   // Ao encontrar atualiza o dado para true - (3)
//   // Retorna o game removido (4)
//   Games var = new Games();
//   int pos = 8;
//   try {
//     raf.seek(pos);
//     for (int i = 0; i < ID; i++) {
//       pos += raf.readInt();
//       raf.seek(pos);
//     }
//     raf.readInt(); // ler o tamanho do vetor de byte
//     raf.readInt(); // ler o ID
//     raf.writeBoolean(true); // mudar o boolean, MAS precisa dos IFs pra ver se ele já nao estava deletado
//   } catch (Exception e) {
//     System.out.println("Erro na função Delete: " + e);
//   }
//   return var; // (4)
// }
