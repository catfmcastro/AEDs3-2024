import scr.CRUD;
import scr.Games;

public class App2 {
    public static void main(String[] args) {
        try {
            CRUD crud = new CRUD();
            Games str = new Games(false, 0, 0,0,"ehisso", "ehisso", "ehisso","ehisso", "ehisso",0);
            // crud.create(str);
            crud.read(140).printScreen();
            // crud.read(150).printScreen();
            // crud.update(0, str);
            // crud.delete(0);
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
}