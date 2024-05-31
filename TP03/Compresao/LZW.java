package Compresao;

import java.io.ByteArrayInputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

public class LZW {

  public LZW() {}

  // escreve informações no .db
  public void writeFile(String input, RandomAccessFile raf) throws Exception {
    try {
      byte[] data = input.getBytes();
      raf.write(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String compressLZW(byte[] input) throws Exception {
    try {
      String out = ""; // saída
      String s = ""; // strings para adicionar no dictionary

      // criação do dicionário
      HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
      for (int i = 0; i < 256; i++) { // todos os caracteres ASCII
        dictionary.put("" + (char) i, i);
      }

      int code = 256;
      for (int i = 0; i < input.length; i++) {
        char c = (char) (input[i] & 0xff); // bytes convertidos em char
        String sc = s + c;
        if (dictionary.containsKey(sc)) {
          s = sc;
        } else { // adiciona padrão novo ao dicionário
          out += dictionary.get(s) + " ";
          dictionary.put(sc, code);
          code++;
          s = "" + c;
        }
      }
      
      // se s não estiver vazia, adiciona a out
      if (!s.equals("")) {
        out += dictionary.get(s) + " ";
      }

      return out;
    } catch (Exception e) {
      System.err.println("Erro na compressão com LZW: " + e.getMessage());
      return null;
    }
  }

  public byte[] decompressLZW(String input) throws Exception {
    try {
      ArrayList<Byte> out = new ArrayList<Byte>();

      // criação do dicionário
      HashMap<Integer, String> dictionary = new HashMap<Integer, String>();
      for (int i = 0; i < 256; i++) { // todos os caracteres ASCII
        dictionary.put(i, "" + (char) i);
      }

      String[] arr = input.split(" "); // dados codificados separados por espaço
      int code = 256;
      String s = "" + (char) (Integer.parseInt(arr[0]));
      out.add((byte) Integer.parseInt(arr[0]));
      
      for (int i = 1; i < arr.length; i++) {
        int k = Integer.parseInt(arr[i]);
        String entry = "";

        // descompressão propriamente dita
        if (dictionary.containsKey(k)) { // se k está no dicionário, decodifica
          entry = dictionary.get(k);
        } else if (k == code) { // se k é igual ao código, adiciona s + s[0] ao dicionário
          entry = s + s.charAt(0);
        } else {
          throw new Exception("Erro na compressão: " + k);
        }

        // update saída
        for (int j = 0; j < entry.length(); j++) {
          out.add((byte) entry.charAt(j));
        }

        // update dicionário
        dictionary.put(code, s + entry.charAt(0));
        code++;
        s = entry;
      }

      // convere resultado em um array de bytes
      byte[] result = new byte[out.size()];
      for (int i = 0; i < out.size(); i++) {
        result[i] = out.get(i);
      }

      return result;
    } catch (Exception e) {
      System.err.println("Erro na descompressão com LZW: " + e.getMessage();
      return null;
    }
  }
}
