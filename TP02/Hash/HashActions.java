package Hash;

import java.io.RandomAccessFile;
import java.util.RandomAccess;

import Controller.Actions;
import Model.Games;

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
    try{
        RandomAccessFile file = new RandomAccessFile("./TP02/DB/games.db", "rw"); // abre arquivo
        long ultimaPos = file.readLong(); // guarda a ultima posição do arquivo
        file.seek(8); // posicona o ptr no inicio do arq, pulando o ponteiro global

        while(file.getFilePointer() < ultimaPos) {
            int tam = file.readInt(); // lê o tamanho do registro
            byte[] arr = new byte[tam]; // cria um vetor de bytes do tamanho do registro
            file.read(arr); // lê o registro

            // add objeto novo
            Games aux = new Games(); // cria um novo game
            aux.fromByteArray(arr); // converte o vetor de bytes para um game

            // insere no hash
            hash.createHash(aux.getId(), file.getFilePointer());
        }

        file.close(); // fecha arquivo
    } catch (Exception e) {
      System.err.println("Erro ao carregar dados para o Hash: " + e);
    }
  }

  public void getHashInfo() {
    hash.readCbAndPg();;
    setHashPtrGlobal(hash.getPtrGlobal());
    setHashContBuckets(hash.getContBuckets());
  }

  public long searchGameHash(int id) {
    return hash.searchHash(id);
  }
}
