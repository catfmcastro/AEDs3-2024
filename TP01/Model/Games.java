package Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Games {
  private int id, steamID, release_date;
  private double price;
  private String name, short_description, genres, publishers;
  private char[] supports_linux = new char[3];
  private boolean grave; // Lápide
  private DecimalFormat dt = new DecimalFormat("R$ 0.00");

  public Games() {
    this.id = -1;
    this.steamID = -1;
    this.price = -1;
    this.name = "";
    this.short_description = "";
    this.genres = "";
    this.publishers = "";
    this.release_date = -1;
    for (int i = 0; i < 3; i++) {
      this.supports_linux[i] = '\0';
    }
    this.grave = false;
  }

  public void setId (int id){
    this.id = id;
  }

  public Games(boolean grave, int id, int steamID, float price, String name, String short_description, String genres,
      String publishers, String supports_linux, int release_date) {
    this.id = id;
    this.grave = grave;
    this.steamID = steamID;
    this.price = price;
    this.name = name;
    this.short_description = short_description;
    this.genres = genres;
    this.publishers = publishers;
    for (int i = 0; i < 3; i++) {
      this.supports_linux[i] = supports_linux.charAt(i);
    }
    this.release_date = release_date;
  }

  // Transforma o valor em horas para data em String
  private static String hoursToDate(int data) {
    // Um ano tem 8766 h
    // Um mês tem 730 h
    // Um dia tem 24 h

    String year, month, day;
    year = Integer.toString((data / 8760) + 1900);
    month = Integer.toString((data % 8760) / 730);
    day = Integer.toString(((data % 8760) % 730) / 24);

    return day + "/" + month + "/" + year;
  }

  private String printSupportsLinux() {
    String str = "";

    for (int i = 0; i < 3; i++) {
      str += supports_linux[i];
    }

    return str;
  }

  // Formata objeto em String
  public String toString() {
    return ("Id: " +
        this.id +
        " - " +
        "SteamID: " +
        this.steamID +
        " - " +
        "Preço: " +
        dt.format(this.price) +
        "\n" +
        "Nome: " +
        this.name +
        "\n" +
        "Descrição: " +
        this.short_description +
        "\n" +
        "Gêneros: " +
        this.genres +
        "\n" +
        "Publicador: " +
        this.publishers +
        "\n" +
        "Data de Lançamento: " +
        hoursToDate(this.release_date) +
        "\n" +
        "Grave: " +
        this.grave +
        "\n" +
        "Suporte Linux: " +
        printSupportsLinux() +
        "\n");
  }

  public Games inputNewGame() {
    Games tmp = new Games();
    Scanner sc = new Scanner(System.in);
    // boolean confirm = false;

    System.out.println("Insira as informações solicitadas.");

    System.out.println("\nNome do game: ");
    String name = sc.next();
    this.name = name;

    // System.out.println("\nData de lançamento: ");
    // String release_date = sc.next();
    // this.release_date = release_date;

    sc.close();

    return tmp;
  }

  // Pega so dados já salvos do game e trasnforma em um vetor de bits que é
  // retornado para ser inserido no arquivo
  public byte[] byteParse() throws IOException {

    // grave,id,steamID,name,price,short_descritiption,genres,publishers,supports_linux,release_date

    ByteArrayOutputStream by = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(by);
    dos.writeBoolean(grave);
    dos.writeInt(id);
    dos.writeInt(steamID);
    dos.writeUTF(name);
    dos.writeDouble(price);
    dos.writeUTF(short_description);
    dos.writeUTF(genres);
    dos.writeUTF(publishers);
    for (int i = 0; i < 3; i++) {
      dos.writeChar(supports_linux[i]);
    }
    dos.writeInt(release_date);
    dos.close();

    return by.toByteArray();
  }

  public void fromByteArray(byte by[]) {
    ByteArrayInputStream vet = new ByteArrayInputStream(by);
    DataInputStream dos = new DataInputStream(vet);
    try {
      this.grave = dos.readBoolean();
      this.id = dos.readInt();
      this.steamID = dos.readInt();
      this.name = dos.readUTF();
      this.price = dos.readDouble();
      this.short_description = dos.readUTF();
      this.genres = dos.readUTF();
      this.publishers = dos.readUTF();
      String aux = dos.readUTF();
      for (int i = 0; i < 3; i++) {
        this.supports_linux[i] = aux.charAt(i);
      }
      this.release_date = dos.readInt();
      dos.close();
    } catch (Exception e) {
      System.out.println("Erro readByte: " + e.getMessage());
    }
  }
}