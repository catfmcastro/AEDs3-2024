package Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class Games {
    private int id, steamID, release_date;
    private float price;
    private String name, short_description;
    private String genres, publishers, run_linux;
    private boolean grave;

    public Games() {
        this.id = -1;
        this.steamID = -1;
        this.price = -1;
        this.release_date = -1;
        this.name = "";
        this.short_description = "";
        this.genres = "";
        this.publishers = "";
        this.run_linux = "";
        this.grave = false;
    }

    // id./;steamID./;name./;price./;short_descritiption./;genres./;publishers./;roda_linux./;release_date

    public Games(int id, int steamID, String name, float price, String short_description, String genres,
            String publishers, String run_linux, int release_date, boolean grave) {
        this.id = id;
        this.steamID = steamID;
        this.release_date = release_date;
        this.price = price;
        this.name = name;
        this.short_description = short_description;
        this.genres = genres;
        this.publishers = publishers;
        this.run_linux = run_linux;
        this.grave = grave;
    }

    public int getId() {
        return id;
    }

    public void setGrave() {
        this.grave = true;
    }

    public int getRelease_date() {
        return release_date;
    }

    public boolean isGrave() {
        return grave;
    }

    public boolean isGame(byte[] vet, int searchID) {

        try {
            ByteArrayInputStream bai = new ByteArrayInputStream(vet);
            DataInputStream dis = new DataInputStream(bai);
            this.grave = dis.readBoolean();
            this.id = dis.readInt();

            if (!grave && searchID == id) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Erro testa isGame: " + e.getMessage());
            return false;
        }

    }

    public void setBytes(byte[] vet) {
        try {
            ByteArrayInputStream bai = new ByteArrayInputStream(vet);
            DataInputStream dis = new DataInputStream(bai);
            this.grave = dis.readBoolean();
            this.id = dis.readInt();
            this.steamID = dis.readInt();
            this.release_date = dis.readInt();
            this.price = dis.readFloat();
            this.name = dis.readUTF();
            this.short_description = dis.readUTF();
            this.genres = dis.readUTF();
            this.publishers = dis.readUTF();
            this.run_linux = dis.readUTF();
        } catch (Exception e) {
            System.out.println("Erro setBytes: " + e.getMessage());
        }
    }

    public byte[] getBytes() {
        try {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bao);
            dos.writeBoolean(grave);
            dos.writeInt(id);
            dos.writeInt(steamID);
            dos.writeInt(release_date);
            dos.writeFloat(price);
            dos.writeUTF(name);
            dos.writeUTF(short_description);
            dos.writeUTF(genres);
            dos.writeUTF(publishers);
            dos.writeUTF(run_linux);

            return bao.toByteArray();
        } catch (Exception e) {
            System.out.println("Erro getBytes: " + e.getMessage());
            return null;
        }

    }

    private String convertDate(int data) {
        // Um ano tem 8766 h
        // Um mês tem 730 h
        // Um dia tem 24 h

        String year, month, day;
        year = Integer.toString((data / 8760) + 1900);
        month = Integer.toString((data % 8760) / 730);
        day = Integer.toString(((data % 8760) % 730) / 24);

        return day + "/" + month + "/" + year;
    }

    public int trasnformDate(String data) {

        // Um ano tem 8766 h
        // Um mês tem 730 h
        // Um dia tem 24 h

        String tmp[] = data.split("/");
        int year, month, day;

        year = (Integer.parseInt(tmp[2]) - 1900) * 8760;
        month = (Integer.parseInt(tmp[1])) * 730;
        day = Integer.parseInt(tmp[0]) * 24;

        return year + month + day;
    }

    public String toString() {
        return "Id: " +
                this.id +
                " - " +
                "SteamID: " +
                this.steamID +
                " - " +
                "Preço: " +
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
                convertDate(this.release_date) +
                "\n" +
                "Grave: " +
                this.grave +
                "\n" +
                "Suporte Linux: " +
                this.run_linux +
                "\n";
    }

    public boolean userInputGame(int maxId) {
        Scanner in = new Scanner(System.in);

        try {
            // Add. Id e lápide
            this.id = maxId;
            this.grave = false;

            System.out.println(
                    "\nInsira as informações pedidas. Atenção para a formatação!\n");

            System.out.println("Insira o nome do Game: ");
            String nameIn = in.nextLine();
            this.name = nameIn;

            System.out.println("Insira uma descrição curta do Game: ");
            String shortIn = in.nextLine();
            this.short_description = shortIn;

            System.out.println("Insira os gêneros do Game (no formato Gênero X/Gênero Y/ Gênero Z): ");
            String genresIn = in.nextLine();
            this.genres = genresIn;

            System.out.println("Insira os publishers do Game: ");
            String publishersIn = in.nextLine();
            this.publishers = publishersIn;

            System.out.println("Insira o preço do Game (no formato 00.00): ");
            String priceIn = in.nextLine();
            this.price = Float.parseFloat(priceIn);

            System.out.println("Insira o SteamID do Game: ");
            String steamIn = in.nextLine();
            this.steamID = Integer.parseInt(steamIn);

            System.out.println("O game roda em Linux (Sim ou Nao): ");
            String linuxIn = in.nextLine();
            this.run_linux = linuxIn;

            System.out.println("Insira a data de lançamento do Game (dd/mm/aaaa): ");
            String dateIn = in.nextLine();
            this.release_date = trasnformDate(dateIn);

            System.out.println("input de game finalizado");
            in.close();
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao inserir game: " + e);
            return false;
        }

    }

    public boolean atualiza() {
        Scanner in = new Scanner(System.in);

        try {
            this.grave = false;

            System.out.println(
                    "\nInsira as informações pedidas. Atenção para a formatação!\n");

            System.out.println("Insira o nome do Game: ");
            String nameIn = in.nextLine();
            this.name = nameIn;

            System.out.println("Insira uma descrição curta do Game: ");
            String shortIn = in.nextLine();
            this.short_description = shortIn;

            System.out.println("Insira os gêneros do Game (no formato Gênero X/Gênero Y/ Gênero Z): ");
            String genresIn = in.nextLine();
            this.genres = genresIn;

            System.out.println("Insira os publishers do Game: ");
            String publishersIn = in.nextLine();
            this.publishers = publishersIn;

            System.out.println("Insira o preço do Game (no formato 00.00): ");
            String priceIn = in.nextLine();
            this.price = Float.parseFloat(priceIn);

            System.out.println("Insira o SteamID do Game: ");
            String steamIn = in.nextLine();
            this.steamID = Integer.parseInt(steamIn);

            System.out.println("O game roda em Linux (Sim ou Nao): ");
            String linuxIn = in.nextLine();
            this.run_linux = linuxIn;

            System.out.println("Insira a data de lançamento do Game (dd/mm/aaaa): ");
            String dateIn = in.nextLine();
            this.release_date = trasnformDate(dateIn);

            System.out.println("input de game finalizado");
            in.close();
            return true;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar game: " + e);
            return false;
        }

    }

}