package Interface;

import CRUD.CRUD;
import Compresao.LZW;
import Model.Games;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Actions {

  public static void searchGame(Scanner sc) {
    System.out.print("Insira o ID do game que quer buscar: ");
    int id = Integer.parseInt(sc.nextLine());

    while (id < 0) {
      System.out.println("ID inválido, tente novamente.");
      id = Integer.parseInt(sc.nextLine());
    }

    CRUD crud = new CRUD();
    Games tmp = crud.search(id);

    if (tmp == null) {
      System.out.println("Game não encontrado.");
    } else {
      System.out.println(tmp.toString());
    }
  }

  public static void deleteGame(Scanner sc) {
    System.out.print("Insira o ID do game que quer deletar: ");
    int id = Integer.parseInt(sc.nextLine());

    while (id < 0) {
      System.out.println("ID inválido, tente novamente.");
      id = Integer.parseInt(sc.nextLine());
    }

    CRUD crud = new CRUD();
    Games tmp = crud.delete(id);

    if (tmp == null) {
      System.out.println("Game não encontrado.");
    } else {
      System.out.println(tmp.toString());
      System.out.println("Game deletado com sucesso!");
    }
  }

  // !! mudança na lógica da classe Games
  public static void updateGame(Scanner sc) {
    System.out.print("Insira o ID do game que quer atualizar: ");
    int id = Integer.parseInt(sc.nextLine());

    Games aux = new Games();
    CRUD crud = new CRUD();

    // !! CRIAR A FUNÇÃO USER INPUT TO OBJECT
    //aux.userInputGame();

    boolean done = crud.update(id, aux);

    if (done) {
      System.out.println("Game atualizado com sucesso!");
    } else {
      System.out.println("Não foi possível atualizar o game. Tente novamente.");
    }
  }

  // !! mudança na lógica da classe Games
  public static void createGame(Scanner sc) {}

  // comprimir dados com LZW
  public static void compressLzw() {
    try {
      RandomAccessFile raf = new RandomAccessFile("./TP03/BD/games.db", "rw");
      RandomAccessFile compressedRaf = new RandomAccessFile(
        "./TP03/BD/compressed_games_lzw.db",
        "rw"
      );
      LZW lzw = new LZW();
      int size = (int) raf.length();
      byte[] data = new byte[size];

      // percorrendo .db
      raf.seek(0);
      raf.read(data);

      System.out.println("Iniciando compressão com LZW...");
      long startTime = System.nanoTime(); // inicio contagem de tempo
      String compressed = lzw.compressLZW(data); // compressão
      long endTime = System.nanoTime(); // fim contagem de tempo

      double executionTime = (endTime - startTime) / 1_000_000_000.0; // calculo do tempo de execução em segundos

      System.out.println("Tempo de execução: " + executionTime + " seconds");
      lzw.writeFile(compressed, compressedRaf); // escrita em file
      compressedRaf.seek(0);
      System.out.println("Compressão concluída com sucesso!");

      raf.close();
      compressedRaf.close();
    } catch (Exception e) {
      System.err.println("Error na compressão com LZW: " + e.getMessage());
    }
  }

  public static void decompressLzw() throws Exception {
    RandomAccessFile raf = new RandomAccessFile("./TP03/BD/games.db", "rw");
    RandomAccessFile compressedRaf = new RandomAccessFile(
      "./TP03/BD/compressed_games_lzw.db",
      "rw"
    );
    RandomAccessFile decompressedRaf = new RandomAccessFile(
      "./TP03/BD/decompressed_games_lzw.db",
      "rw"
    );
    LZW lzw = new LZW();

    decompressedRaf.setLength(0);
    compressedRaf.seek(0);
    int size = (int) compressedRaf.length();
    byte[] compressed = new byte[size];

    // lê file comprimido
    compressedRaf.read(compressed);
    String compressedString = new String(compressed);

    System.out.println("Iniciando descompressão com LZW...");
    long startTime2 = System.nanoTime(); // inicia contagem de tempo
    decompressedRaf.write(lzw.decompressLZW(compressedString)); // descompressão
    long endTime2 = System.nanoTime(); // finaliza contagem de tempo

    double executionTime2 = (endTime2 - startTime2) / 1_000_000_000.0; // calculo de tempo de execução em segundos
    System.out.println("Descompressão concluída com sucesso!");
    System.out.println(
      "Tempo de execução: " + executionTime2 + " seconds"
    );

    raf.close();
    compressedRaf.close();
    decompressedRaf.close();
  }
}
