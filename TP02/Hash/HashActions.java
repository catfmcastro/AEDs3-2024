package Hash;

import Controller.Actions;
import Model.Games;

import java.io.RandomAccessFile;

public class HashActions extends Actions {

  private int hashPtrGlobal;
  private int hashContBuckets;

  Hash hash = new Hash();

  // * Getters e setters
  public int getHashPtrGlobal() {
    return hashPtrGlobal;
  }

  public void setHashPtrGlobal(int hashPtrGlobal) {
    this.hashPtrGlobal = hashPtrGlobal;
  }

  public int getHashContBuckets() {
    return hashContBuckets;
  }

  public void setHashContBuckets(int hashContBuckets) {
    this.hashContBuckets = hashContBuckets;
  }

  // * Load dados do DB no Hash indexado
  public void loadDataToHash() {
    try {
      RandomAccessFile file = new RandomAccessFile("./TP02/DB/games.db", "rw"); // abre arquivo
      long ultimaPos = file.readLong(); // guarda a ultima posição do arquivo
      file.seek(8); // posicona o ptr no inicio do arq, pulando o ponteiro global

      while (file.getFilePointer() < ultimaPos) {
        long pos = file.getFilePointer(); // guarda a posição do registro
        int tam = file.readInt(); // lê o tamanho do registro
        System.out.println("tamanho do registro " + tam);
        byte[] arr = new byte[tam]; // cria um vetor de bytes do tamanho do registro
        file.read(arr); // lê o registro

        // add objeto novo
        Games aux = new Games(); // cria um novo game
        aux.fromByteArray(arr); // converte o vetor de bytes para um game

        System.out.println("objeto adicionado: id " + aux.getId());

        // insere no hash
        hash.createInHash(aux.getId(), pos);
        System.out.println("inserido no hash");
        System.out.println();
      }

      file.close(); // fecha arquivo
    } catch (Exception e) {
      System.err.println("Erro ao carregar dados para o Hash: " + e);
    }
  }

  // * Buscar registro usando hash indexado
  public Games readHash(int id) {
    try {
      long pos = hash.searchHash(id);

      if (pos != -1) {
        file.seek(pos + 1); // posiciona ptr no end. correto, pulando a lápide
        int tam = file.readInt(); // tamanho do registro
        byte[] arr = new byte[tam];
        file.read(arr);

        Games aux = new Games();

        System.out.println("O game encontrado foi: ");
        aux.printGame();

        aux.fromByteArray(arr);

        return aux;
      } else {
        // jogo não encontrado
        return null;
      }
    } catch (Exception e) {
      System.err.println("Erro ao buscar registro no Hash: " + e);
    }

    return null;
  }

  public Games readHashh (int id) {
    try {
      Games aux = new Games();
      aux = this.readGame(id);
      return aux;
    } catch (Exception e) {
      System.err.println("Erro ao buscar registro no Hash: " + e);
      return null;
    }
  }

  // * Deletar registro no Hash indexado
  public Games deleteHash(int id) {
    try {
      long pos = hash.searchHash(id);

      if (pos != -1) {
        file.seek(pos); // posiciona ptr no end. correto, pulando a lápide
        file.writeByte(-1); // marca como deletado
        hash.deleteInHash(id, pos);
        System.out.println("Registro deletado com sucesso!");
        return null;
      } else {
        System.out.println("Registro não encontrado!");
        return null;
      }
    } catch (Exception e) {
      System.err.println("Erro ao deletar registro no Hash: " + e);
      return null;
    }
  }

  public Games deleteHashh(int id) {
    try {
      Games aux = this.deleteGame(id);
      return aux;
    } catch (Exception e) {
      System.err.println("Erro ao deletar registro no Hash: " + e);
      return null;
    }
  }

  // * Cria registro no Hash indexado
  public boolean createHash(Games tmp) {
    try {
      file.seek(file.length()); // Posiciona o ponteiro no final do arquivo
      long pos = file.getFilePointer();

      byte[] arr = tmp.byteParse(); // Converte o game para vetor de bytes
      file.writeInt(arr.length); // Escreve o tamanho do registro

      file.write(arr); // Escreve o registro

      hash.createInHash(tmp.getId(), pos); // Insere no hash
      return true; // Return true if the hash creation is successful
    } catch (Exception e) {
      System.err.println("Erro ao criar registro no Hash: " + e);
      return false; // Return false if there is an error in hash creation
    }
  }

  public boolean createHashh(Games tmp) {
    try {
      boolean aux = this.createGame(tmp);
      return aux;
    } catch (Exception e) {
      System.err.println("Erro ao criar registro no Hash: " + e);
      return false;
    }
  }


  public void getHashInfo() {
    hash.readCbAndPg();
    setHashPtrGlobal(hash.getPtrGlobal());
    setHashContBuckets(hash.getContBuckets());
  }

  public long searchGameHash(int id) {
    return hash.searchHash(id);
  }
}
