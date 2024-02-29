import scr.CRUD;
import scr.Games;

public class App {
    public static void main(String[] args) {
        try {
            CRUD crud = new CRUD();
            Games tmp = crud.read(43200);
            tmp.printScreen();            
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
}
