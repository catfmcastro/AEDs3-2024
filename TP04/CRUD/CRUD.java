package CRUD;

import Model.Games;
import RSA.RSA;

import java.io.RandomAccessFile;
import java.util.ArrayList;

import BoyerMoore.PadraoBooyerMoore;

public class CRUD {

  RandomAccessFile raf;

  public Games search(int id) {
    Games temp = new Games();

    try {
      raf = new RandomAccessFile("./TP04/BD/games.db", "rw");
      long position = raf.readLong();

      do {
        byte[] vet = new byte[raf.readInt()];
        raf.read(vet);

        if (temp.isGame(vet, id)) {
          temp.setBytes(vet);
          return temp;
        }
      } while (raf.getFilePointer() != position);

    } catch (Exception e) {
      System.err.println("Erro ao pesquisar game: " + e.getMessage());
    }

    return temp;
  }

  public Games delete(int id) {
    Games temp = new Games();

    try {
      raf = new RandomAccessFile("./TP04/BD/games.db", "rw");
      long pointerEnd = raf.readLong();

      do {
        byte[] vet = new byte[raf.readInt()];
        long aux = raf.getFilePointer();
        raf.read(vet);

        if (temp.isGame(vet, id)) {
          temp.setBytes(vet);
          temp.setGrave();
          raf.seek(aux);
          raf.write(temp.getBytes());
          return temp;
        }
      } while (raf.getFilePointer() != pointerEnd);
    } catch (Exception e) {
      System.err.println("Erro ao deletar game: " + e.getMessage());
    }

    return temp;
  }

  public boolean update(int id, Games novo) {
    Games temp = new Games();

    try {
      raf = new RandomAccessFile("./TP04/BD/games.db", "rw");
      long pointerEnd = raf.readLong();

      do {
        byte[] vet = new byte[raf.readInt()];
        long aux = raf.getFilePointer();
        raf.read(vet);

        if (temp.isGame(vet, id)) {
          temp.setBytes(vet);
          temp.setGrave();
          raf.seek(aux);
          raf.write(temp.getBytes());
          create(novo);
          return true;
        }
      } while (raf.getFilePointer() != pointerEnd);
    } catch (Exception e) {
      System.err.println("Erro ao atualizar game: " + e.getMessage());
    }

    return false;
  }

  public boolean create(Games novo) {
    try {
      raf = new RandomAccessFile("./TP04/BD/games.db", "rw");
      long position = raf.readLong();
      raf.seek(position);
      byte[] temp = novo.getBytes();
      raf.writeInt(temp.length);
      raf.write(temp);
      position = raf.getFilePointer();
      raf.seek(0);
      raf.writeLong(position);
      return true;
    } catch (Exception e) {
      System.err.println("Erro ao criar game novo: " + e.getMessage());
    }

    return false;
  }

  public ArrayList<Games> searchByName(String str, RSA rsa) {
    ArrayList<Games> games = new ArrayList<Games>();
    
    try {
      raf = new RandomAccessFile("./TP04/BD/games.db", "rw");
      long position = raf.readLong();
      PadraoBooyerMoore pdm = new PadraoBooyerMoore(str);
      
      do {
        byte[] vet = new byte[raf.readInt()];
        raf.read(vet);
        
        Games temp = new Games();
        temp.setBytes(vet);

        if(pdm.buscarPadrao(temp.getName(rsa))){
          games.add(temp);
        }
      } while (raf.getFilePointer() != position);

    } catch (Exception e) {
      System.err.println("Erro ao pesquisar game: " + e.getMessage());
    }

    return games;
  }

}
