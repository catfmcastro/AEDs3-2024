package BTree.Model;

import BTree.Model.Page;
import java.io.RandomAccessFile;

public class ArvoreB {
    private RandomAccessFile read, create;
    private Page pagina;
    private int maxID = 0;

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

    public void insereINDICES (){
        try {
            RandomAccessFile indices = new RandomAccessFile("./TP02/BTree/Indices/indices.bd", "r");
            for (int i = 0; i < 40; i++) {
                long aux = indices.readLong();
                int id = indices.readInt(); 
                pagina.insereID(id,aux);
                if(maxID < id){
                    maxID = id;
                }
            }
            indices.close();
        } catch (Exception e) {
            System.out.println("Erro insereIndices: " + e.getMessage());
        }

    }

    public boolean createGame (int number, long posicao){
        try {
            return pagina.insereID(number, posicao);
            
        } catch (Exception e) {
            System.out.println("Erro create: " + e.getMessage());
            return false;
        }
    }

    public boolean seachGame (int number){
        try {
            return pagina.procura(number);
        } catch (Exception e) {
            System.out.println("Erro search: " + e.getMessage());
            return false;
        }
    }

    public boolean delete (int number){
        try {
            return pagina.delete(number);
        } catch (Exception e) {
            System.out.println("Erro delete: " + e.getMessage());
            return false;
        }
    }

}
