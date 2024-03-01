package Controller;

import Model.Games;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Actions {

  private long lastPos;
  private int maxId; // Ultimo jogo do arquivo, antes do fim
  static final int inicialYear = 1900; // Ano inicial para a conversão da data
  RandomAccessFile file;

  public void openFile() throws IOException {
      file = new RandomAccessFile("./TP01/out/games.db", "rw");
      lastPos = file.readLong(); // guarda a da ultima posição do arquivo
      maxId = 59431;
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
    RandomAccessFile csv;
    try {
      // Acessando o arquivo
      csv = new RandomAccessFile("./TP01/db/games.csv", "r");
      csv.readLine(); // ! raf.seek nao funciona por causa de algum espaço nulo

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

  // Cria game novo, com base no input de user
  // todo receber tmp do user, já formatado
  public void createGame(Games tmp) throws IOException {
    try {
      file.seek(lastPos);
      maxId++;
      tmp.setId(maxId);
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
    byte [] arr;
    long pos = 8; // primeira posição do file, pulando os metadados
    try {
      file.seek(pos);
      for (int i = 0; i < maxId; i++) {
        int size = file.readInt(); // lê o tamanho do registro
        System.out.println("\n\nsize is: " + size );

        arr = new byte[size];
        System.out.println("o arr lido é: " + arr);

        System.out.println("lendo registro do .db...");
        file.read(arr); // lê o registro que ocupa o tamanho "size"
        System.out.println("fim da leitura do registro.");

        System.out.println("checagem de validade");
        if (isGameValid(arr, searchId)) {
          System.out.println("passou na checagem");
          tmp.fromByteArray(arr);
          System.out.println("fim fromByteArray");
          return tmp;
        }
        pos += size;
      }
    } catch (Exception e) {
      System.err.println("Erro na função Read: " + e);
    }

    return null;
  }

  // Checa se o game é válido ou não, quanto a lápide
  public boolean isGameValid(byte arr[], int id) {
    boolean resp = false;
    ByteArrayInputStream by = new ByteArrayInputStream(arr);
    DataInputStream dis = new DataInputStream(by);

    try {
      // System.out.println("boolean lido é: " + dis.readBoolean());
      // System.out.println("id lido é " + dis.readInt());
      if(!dis.readBoolean() && dis.readInt() == id) {
        resp = true;
      }
    } catch (Exception e) {
      System.err.println("Erro na checagem de validade do Game: " + e);
    }

    return resp;
  }

  // Editar game existente
  public void updateGame() {}

  // Deletar logicamente um game
  public void deleteGame() {}
}
