package scr;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Games {
    private int id, steamID, release_date;
    private float price;
    private String name, short_description, genres, publishers;

    public Games() {
        this.id = -1;
        this.steamID = -1;
        this.price = -1;
        this.name = "N";
        this.short_description = "N";
        this.genres = "N";
        this.publishers = "N";
        this.release_date = -1;
    }

    public Games(int id, int steamID, float price, String name, String short_description, String genres,
            String publishers, int release_date) {
        this.id = id;
        this.steamID = steamID;
        this.price = price;
        this.name = name;
        this.short_description = short_description;
        this.genres = genres;
        this.publishers = publishers;
        this.release_date = release_date;
    }

    // Transforma o valor em horas para data em String
    private static String reTrasnformDate(int data) {
        // Um ano tem 8766 h
        // Um mês tem 730 h
        // Um dia tem 24 h

        String year, month, day;
        year = Integer.toString((data / 8760) + 1900);
        month = Integer.toString((data % 8760) / 730);
        day = Integer.toString(((data % 8760) % 730) / 24);

        return day + "/" + month + "/" + year;
    }

    public void printScren() {
        System.out.println("Id: " + this.id + " - " + "SteamID: " + this.steamID + " - " + "Preço: R$" + this.price
                + "\n" + "Nome: " + this.name + "\n" + "Descrição: " + this.short_description + "\n" + "Gêneros: "
                + this.genres + "\n" + "Publicador: " + this.publishers + "\n" + "Data de Lançamento: "
                + reTrasnformDate(this.release_date) + "\n");
    }

    public byte[] createbyteArray () throws IOException{
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(by);
        dos.writeInt(id);
        dos.writeInt(steamID);
        dos.writeUTF(name);
        dos.writeDouble(price);
        dos.writeUTF(short_description);
        dos.writeUTF(genres);
        dos.writeUTF(publishers);
        dos.writeInt(release_date);

        dos.close();
        return by.toByteArray();
    }

    public void readBytes(byte by[]) throws IOException {
        ByteArrayInputStream vet = new ByteArrayInputStream(by);
        DataInputStream dos = new DataInputStream(vet);
        this.id = dos.readInt();
        this.steamID = dos.readInt();
        this.name = dos.readUTF();
        this.price = dos.readFloat();
        this.short_description = dos.readUTF();
        this.genres = dos.readUTF();
        this.publishers = dos.readUTF();
        this.release_date = dos.readInt();
        dos.close();
    }

}