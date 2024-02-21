package scr;

import java.util.Date;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Games {
    private int id, steamID;
    private float price;
    private String name, short_description, genres, publishers;
    private Date release_date;

    public Games() {
        this.id = -1;
        this.steamID = -1;
        this.price = -1;
        this.name = "N";
        this.short_description = "N";
        this.genres = "N";
        this.publishers = "N";
        // Date nova = new Date();
        // this.release_date = nova;
    }

    public Games(int id, int steamID, float price, String name, String short_description, String genres,
            String publishers, Date release_date) {
        this.id = id;
        this.steamID = steamID;
        this.price = price;
        this.name = name;
        this.short_description = short_description;
        this.genres = genres;
        this.publishers = publishers;
        this.release_date = release_date;
    }

    public void printScren() {
        System.out.println("Id: " + this.id + " - " + "SteamID: " + this.steamID + " - " + "Preço: R$" + this.price
                + "\n" + "Nome: " + this.name + "\n" + "Descrição: " + this.short_description + "\n" + "Gêneros: "
                + this.genres + "\n" + "Publicador: " + this.publishers + "\n" + "Data de Lançamento: "
                + this.release_date + "\n");
    }

    public void writeBytes() throws IOException {
        FileOutputStream arq = new FileOutputStream("./scr/db/games.db");
        ByteArrayOutputStream by = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(by);
        dos.writeInt(this.id);
        dos.writeInt(this.steamID);
        dos.writeFloat(this.price);
        dos.writeUTF(this.name);
        dos.writeUTF(this.short_description);
        dos.writeUTF(this.genres);
        dos.writeUTF(this.publishers);
        by.toByteArray();
        System.out.println("Salvado com Sucesso");
        dos.close();
    }

    public void readBytes() throws IOException {
        FileInputStream arq = new FileInputStream("./scr/db/games.db");
        DataInputStream dos = new DataInputStream(arq);

        this.id = dos.readInt();
        this.steamID = dos.readInt();
        this.price = dos.readFloat();
        this.name = dos.readUTF();
        this.short_description = dos.readUTF();
        this.genres = dos.readUTF();
        this.publishers = dos.readUTF();

        dos.close();
    }

}