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
      String out = "";
      String s = "";

      // criação do dicionário
      HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
      for (int i = 0; i < 256; i++) {
        dictionary.put("" + (char) i, i);
      }
      int code = 256;
      for (int i = 0; i < input.length; i++) {
        char c = (char) (input[i] & 0xff);
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
      if (!s.equals("")) {
        out += dictionary.get(s) + " ";
      }

      return out;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public byte[] decompressLZW(String input) throws Exception {
    try {
      ArrayList<Byte> out = new ArrayList<Byte>();

      // criação do dicionário
      HashMap<Integer, String> dictionary = new HashMap<Integer, String>();
      for (int i = 0; i < 256; i++) {
        dictionary.put(i, "" + (char) i);
      }

      String[] arr = input.split(" ");
      int code = 256;
      String s = "" + (char) (Integer.parseInt(arr[0]));
      out.add((byte) Integer.parseInt(arr[0]));
      
      for (int i = 1; i < arr.length; i++) {
        int k = Integer.parseInt(arr[i]);
        String entry = "";
        if (dictionary.containsKey(k)) {
          entry = dictionary.get(k);
        } else if (k == code) {
          entry = s + s.charAt(0);
        } else {
          throw new Exception("Bad compressed k: " + k);
        }
        for (int j = 0; j < entry.length(); j++) {
          out.add((byte) entry.charAt(j));
        }
        dictionary.put(code, s + entry.charAt(0));
        code++;
        s = entry;
      }
      byte[] result = new byte[out.size()];
      for (int i = 0; i < out.size(); i++) {
        result[i] = out.get(i);
      }
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
