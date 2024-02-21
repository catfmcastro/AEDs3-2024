package scr;

import java.util.Date;


public class Main {
    public static void main(String[] args){
        Date data = new Date(); 
        Date data2 = new Date(); 
        Games j1 = new Games(10, 20, 10, "Alguem", "descricao", "Horror", "Eu mesmo", data);
        Games j2 = new Games(20, 30, 15, "Alguem2", "descricao2", "Horror2", "Eu mesmo2", data2);
        Games j3 = new Games();
        
        try {
            j1.writeBytes();
            j2.writeBytes();
            j3.readBytes();
            j3.printScren();
        } catch (Exception e) {
            System.out.println("Erro");
        }
    }
}
