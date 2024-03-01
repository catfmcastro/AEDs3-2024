package Controller;

import Model.Games;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Actions {

  private long lastPos;
  private int maxId; // Ultimo jogo do arquivo, antes do fim
  private int gamesCount;
  static final int inicialYear = 1900; // Ano inicial para a conversão da data
  RandomAccessFile file;

  public void openFile() throws IOException {
    file = new RandomAccessFile("./TP01/out/games.db", "rw");
    lastPos = file.readLong(); // guarda a da ultima posição do arquivo
    maxId = 59431;
    gamesCount = maxId;
  }

  public void closeFile() throws IOException {
    try {
      file.close();
    } catch (Exception e) {
      System.out.println("Erro ao fechar arquivo .db: " + e);
    }
  }

  // !!!!!! PROBLEMA! não está carregando o cabeçalho corretamente
  // Carrega info. do .csv para o .db
  public void loadData() {
    RandomAccessFile csv;
    try {
      csv = new RandomAccessFile("./TP01/db/games.csv", "r");
      csv.readLine();

      System.out.println("Carregando dados para o Banco...");

      String str;
      file.writeLong(10); // Insere a posição final do arquivo no início

      // Preenchimento do arquivo binário
      while ((str = csv.readLine()) != null) {
        // Leitura do csv
        String vet[] = str.split("./;");
        Games tmp = new Games(
          false, // lápide
          Integer.parseInt(vet[0]), // id
          Integer.parseInt(vet[1]), //steamID
          Float.parseFloat(vet[3]), // price
          vet[2], // name
          vet[4], // short_description
          vet[5], // genres
          vet[6], // publishers
          vet[7], // supports_linux
          dateToHours(vet[8]) // release_date
        );

        byte aux[] = tmp.byteParse(); // Insere no arquivo binário
        file.writeInt(aux.length); // Add o tamanho de cada registro antes dos dados
        file.write(aux); // Escrita do registro
      }

      long last = file.getFilePointer(); // Última posicao do arquivo
      file.seek(0); // Posiciona novamente no inicio do arquivo
      file.writeLong(last); // Escreve a ultima posicao na frente

      csv.close();
      System.out.println("Dados carregados com sucesso!\n");
    } catch (Exception e) {
      System.out.println("Erro ao preencher o arquivo binário: " + e);
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

  // Checa se o game é válido ou não, quanto a lápide
  public boolean isGameValid(byte arr[], int id) {
    boolean resp = false;
    ByteArrayInputStream by = new ByteArrayInputStream(arr);
    DataInputStream dis = new DataInputStream(by);

    try {
      if (!dis.readBoolean() && dis.readInt() == id) {
        resp = true;
      }
    } catch (Exception e) {
      System.err.println("Erro na checagem de validade do Game: " + e);
    }

    return resp;
  }

  // Cria game novo e registrar no file, com base no input de user
  // todo receber tmp do user, já formatado
  public void createGame(Games tmp) throws IOException {
    try {
      maxId++;
      gamesCount++;
      tmp.userInputGame(maxId);
      file.seek(lastPos);
      byte[] array = tmp.byteParse();

      // Escrita no arquivo
      file.writeInt(array.length);
      file.write(array);

      // Update última posição
      this.lastPos += (array.length);

      System.out.println("\nGame criado com sucesso!");
    } catch (Exception e) {
      System.err.println("Erro na função Create: " + e);
    }
  }

  // Ver game existente
  public Games readGame(int searchId) throws IOException {
    Games tmp = new Games();
    byte[] arr;
    long pos = 8; // primeira posição do file, pulando os metadados
    try {
      file.seek(pos);
      for (int i = 0; i < gamesCount; i++) {
        int size = file.readInt(); // lê o tamanho do registro

        arr = new byte[size];
        file.read(arr); // lê o registro que ocupa o tamanho "size"

        if (isGameValid(arr, searchId)) {
          tmp.fromByteArray(arr);
          return tmp;
        }
        pos += size;
      }
    } catch (Exception e) {
      System.err.println("Erro na função Read: " + e);
    }

    return null;
  }

  // Editar game existente
  public boolean updateGame(int id, Games insert) {
    // Posiciona o ponteiro no inicio do arquivo - (1)
    // Procura pelo ID correto - (2)
    // Compara os tamanhos para ver se vai ou nao de vasco - (3)
    // retorna se foi feito com sucesso ou nao - (4)


    Games aux = new Games();
    byte[] arr;
    long pos = 8;

    insert.userInputGame(id);

    try {
      file.seek(pos);

      for (int i = 0; i < gamesCount; i++) {
        int tam = file.readInt();
        arr = new byte[tam];
        file.read(arr);

        if (isGameValid(arr, id)) {
          if (tam >= insert.byteParse().length) {
            // Se o game for maior, salva no fim do file
            file.seek(pos + 4);
            file.write(insert.byteParse());
            return true;
          } else {
            // Se game o menor, salva no mesmo local que o file
            file.seek(pos + 4);
            aux.setGrave(true);
            aux.fromByteArray(arr);
            file.write(aux.byteParse());
            createGame(insert);
            gamesCount++;
            return true;
          }
        }
      }
      return false;
    } catch (Exception e) {
      System.out.println("Erro na função update: " + e.getMessage());
      return false;
    }
  }

  // Deletar logicamente um game
  public Games deleteGame(int id) {
    // Posiciona o ponteiro no inicio do arquivo - (1)
    // Procura pelo ID desejado - (2)
    // Ao encontrar atualiza o dado para true - (3)
    // Retorna o game removido (4)
    Games aux = new Games();
    byte[] temp;
    long pos = 8;

    try {
      file.seek(pos);
      for (int i = 0; i < maxId; i++) {
        int tam = file.readInt();
        temp = new byte[tam];
        file.read(temp);
        if (isGameValid(temp, id)) {
          file.seek(pos + 4);
          aux.fromByteArray(temp);
          aux.setGrave(true);
          file.write(aux.byteParse());
          return aux;
        }
      }

      System.out.println("Game deletado com sucesso!\n");
      return aux;
    } catch (Exception e) {
      System.out.println("Erro na função Delete: " + e.getMessage());
      return aux;
    }
  }
}
