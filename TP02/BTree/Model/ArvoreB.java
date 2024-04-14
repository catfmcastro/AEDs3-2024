package BTree.Model;

import BTree.Model.Page;
import java.io.RandomAccessFile;

public class ArvoreB {
    private RandomAccessFile read, create;
    private Page pagina;

    public ArvoreB() {
        try {
            create = new RandomAccessFile("./TP02/BTree/tree_bd/index.bd", "rw");
            create.writeLong(10);
            long pos = create.getFilePointer();
            pagina = new Page();
            pagina.insereRaiz(pos);
            create.seek(0);
            create.writeLong(pos);
        } catch (Exception e) {
            System.out.println("Erro abrir árvore: " + e.getMessage());
        }
    }

    public boolean createGame (int number, long posicao){
        try {
            pagina.insereID(number, posicao);

            return true;
        } catch (Exception e) {
            System.out.println("Erro create: " + e.getMessage());
            return false;
        }
    }
}
