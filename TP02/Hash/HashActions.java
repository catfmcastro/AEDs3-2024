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
        int tam = file.readInt(); // lê o tamanho do registro
        System.out.println("tamanho do registro " + tam);
        byte[] arr = new byte[tam]; // cria um vetor de bytes do tamanho do registro
        file.read(arr); // lê o registro

        // add objeto novo
        Games aux = new Games(); // cria um novo game
        aux.fromByteArray(arr); // converte o vetor de bytes para um game
        System.out.println("objeto adicionado: id " + aux.getId());

        // insere no hash
        hash.createHash(aux.getId(), file.getFilePointer());
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

  public void getHashInfo() {
    hash.readCbAndPg();
    setHashPtrGlobal(hash.getPtrGlobal());
    setHashContBuckets(hash.getContBuckets());
  }

  public long searchGameHash(int id) {
    return hash.searchHash(id);
  }
}
