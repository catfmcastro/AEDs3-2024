package Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Games {

  private int id, steamID, release_date;
  private float price;
  private String name, short_description, genres, publishers, supports_linux;
  private boolean grave; // Lápide

  public Games() {
    this.id = -1;
    this.steamID = -1;
    this.price = -1;
    this.name = "N";
    this.short_description = "N";
    this.genres = "N";
    this.publishers = "N";
    this.release_date = -1;
    this.supports_linux = "N";
    this.grave = false;
  }

  public Games(
    boolean grave,
    int id,
    int steamID,
    float price,
    String name,
    String short_description,
    String genres,
    String publishers,
    String supports_linux,
    int release_date
  ) {
    this.id = id;
    this.grave = grave;
    this.steamID = steamID;
    this.price = price;
    this.name = name;
    this.short_description = short_description;
    this.genres = genres;
    this.publishers = publishers;
    this.supports_linux = supports_linux;
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

  // Formata objeto em String
  public String toString() {
    return (
      "Id: " +
      this.id +
      " - " +
      "SteamID: " +
      this.steamID +
      " - " +
      "Preço: R$" +
      this.price +
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
      this.supports_linux +
      "\n"
    );
  }

  // Converte dados do objeto para array de bytes
  public byte[] toByteArray() throws IOException {
    // grave,id,steamID,name,price,short_descritiption,genres,publishers,supports_linux,release_date

    ByteArrayOutputStream by = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(by);
    dos.writeInt(id);
    dos.writeBoolean(grave);
    dos.writeInt(steamID);
    dos.writeUTF(name);
    dos.writeDouble(price);
    dos.writeUTF(short_description);
    dos.writeUTF(genres);
    dos.writeUTF(publishers);
    dos.writeUTF(supports_linux);
    dos.writeInt(release_date);
    dos.close();

    return by.toByteArray();
  }

  // Leitura de array de bytes
  public void fromByteArray(byte by[]) throws IOException {
    ByteArrayInputStream vet = new ByteArrayInputStream(by);
    DataInputStream dos = new DataInputStream(vet);
    this.grave = dos.readBoolean();
    this.id = dos.readInt();
    this.steamID = dos.readInt();
    this.name = dos.readUTF();
    this.price = dos.readFloat();
    this.short_description = dos.readUTF();
    this.genres = dos.readUTF();
    this.publishers = dos.readUTF();
    this.supports_linux = dos.readUTF();
    this.release_date = dos.readInt();
    dos.close();
  }
}
