package Controller;

import Model.Games;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Actions {

  private long lastPos;
  private int maxId; // Ultimo jogo do arquivo, antes do fim
  private int gamesCount;
  static final int inicialYear = 1900; // Ano inicial para a conversão da data
  RandomAccessFile file;

  // Abertura do arquivo .db
  public void openFile() throws IOException {
    file = new RandomAccessFile("./TP01/out/games.db", "rw");
    lastPos = file.readLong(); // Guarda a da ultima posição do arquivo
    maxId = 59431;
    gamesCount = maxId;
  }

  // Fechamento do arquivo .db
  public void closeFile() throws IOException {
    try {
      file.close();
    } catch (Exception e) {
      System.err.println("Erro ao fechar arquivo .db: " + e);
    }
  }

  // Carrega info. do .csv para o .db
  public void loadData() {
    RandomAccessFile csv, write;

    try {
      csv = new RandomAccessFile("./TP01/db/games.csv", "r");
      write = new RandomAccessFile("./TP01/out/games.db", "rw");
      csv.readLine();
      String str;

      write.writeLong(10); // Reserva um espaço no inicio do arquivo para inserir a posição do final do arquivo

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
          dateToHours(vet[8])
        );

        byte aux[] = tmp.byteParse(); // Insere registro no arquivo
        write.writeInt(aux.length); // Tam. do registro antes de cada vetor
        write.write(aux); // Insere o vetor de dados de byte
      }

      long last = write.getFilePointer(); // Última posição do arquivo
      write.seek(0); // Posiciona no início do arquivo
      write.writeLong(last); // Escreve a ultima posicao no início

      write.close();
      csv.close();

      System.out.println("Dados carregados com sucesso!\n");
    } catch (Exception e) {
      System.err.println("Erro ao carregar dados: " + e);
    }
  }

  // Transformar data em horas, a partir de 1900
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

  // Checa se o game é válido ou não, quanto a lápide e ao ID
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

  // * --------------------------------------------------------- CRUD ------------------------------------------------------------- *

  /*
   * Cria game novo
   *
   * 1) Recebe game vazio e preenche com input do usuário
   * 2) Percorre até a última posição do arquivo
   * 3) Insere o registro
   */
  public boolean createGame(Games tmp) {
    try {
      gamesCount++;
      maxId++;
      tmp.userInputGame(gamesCount);
      file.seek(lastPos);

      // Add. o registro no fim do arquivo
      byte[] aux = tmp.byteParse();
      file.writeInt(aux.length);
      file.write(aux);
      lastPos += aux.length;

      return true;
    } catch (IOException e) {
      System.err.println("Erro na função create: " + e);
      return false;
    }
  }

  /*
   * Ver game existente
   *
   * 1) Posiciona o ponteiro no início do arquivo
   * 2) Procura pelo ID desejado
   * 3) Retorna o game (ou null, se não encontrado)
   */
  public Games readGame(int searchId) throws IOException {
    Games tmp = new Games();
    byte[] arr;
    long pos = 8; // Pos. inicial
    try {
      file.seek(pos);
      for (int i = 0; i < gamesCount; i++) {
        int size = file.readInt(); // Lê o tamanho do registro

        arr = new byte[size];
        file.read(arr); // Lê o registro

        // Checa a validade do game
        if (isGameValid(arr, searchId)) {
          tmp.fromByteArray(arr); // Transforma bytes em objeto
          return tmp;
        }
        pos += size;
      }
    } catch (Exception e) {
      System.err.println("Erro na função Read: " + e);
    }

    return null;
  }

  /*
   * Atualiza um game existente
   *
   * 1) Posiciona o ponteiro no inicio do arquivo
   * 2) Procura pelo ID desejado
   * 3) Compara os tamanhos para determinar o tipo de update
   * 4) Retorna se foi bem sucedido ou não
   */
  public boolean updateGame(int id, Games insert) {
    Games aux = new Games();
    byte[] arr;
    long pos = 8;

    // Input de usuário para objeto
    insert.userInputGame(id);

    try {
      file.seek(pos); // Pos. inicial

      // Percorre arquivo
      for (int i = 0; i < gamesCount; i++) {
        int tam = file.readInt();
        arr = new byte[tam];
        file.read(arr);

        // Checa se o game é válido
        if (isGameValid(arr, id)) {
          if (tam >= insert.byteParse().length) { // Salva o game no mesmo lugar
            file.seek(pos + 4);
            file.write(insert.byteParse());
            return true;
          } else { // Salva o game no fim do arquivo
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
      System.err.println("Erro na função update: " + e);
      return false;
    }
  }

  /*
   * Deletar logicamente um game
   *
   * Posiciona o ponteiro no inicio do arquivo - (1)
   * Procura pelo ID desejado - (2)
   * Ao encontrar atualiza a lápide para true - (3)
   * Retorna o game removido (4)
   */
  public Games deleteGame(int id) {
    Games aux = new Games();
    byte[] temp;
    long pos = 8;

    try {
      file.seek(pos); // Ptr. no início dos registros

      // Percorre o arquivo
      while (file.getFilePointer() < file.length()) {
        // Leitura dos registros
        int tam = file.readInt();
        temp = new byte[tam];
        file.read(temp);

        // Checagem de validade do game
        if (isGameValid(temp, id)) {
          // Delete propriamente dito
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
      System.err.println("Erro na função Delete: " + e);
      return aux;
    }
  }
}
