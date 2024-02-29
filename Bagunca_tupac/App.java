import scr.CRUD;
import scr.Games;

public class App {
    public static void main(String[] args) {
        try {
            CRUD crud = new CRUD();
            crud.read(10);
            
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }

    }
}
