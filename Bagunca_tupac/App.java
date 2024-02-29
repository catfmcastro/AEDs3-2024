import scr.CRUD;
import scr.Games;

public class App {
    public static void main(String[] args) {
        try {
            CRUD crud = new CRUD();
            Games teste = new Games(false, 10, 10, 10, "opa", "opa", "opa", "opa", "sim", 0);
            crud.read(10);
            
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }

    }
}
