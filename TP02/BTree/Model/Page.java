package BTree.Model;

import java.io.RandomAccessFile;

public class Page {
    private RandomAccessFile read, write;
    
    public Page (){
        try {
            this.read = new RandomAccessFile("./AEDs3-2024/TP02/BTree/tree_bd/index.bd", "r");
            this.write = new RandomAccessFile("./AEDs3-2024/TP02/BTree/tree_bd/index.bd", "rw");
        } catch (Exception e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
        }
    }

    protected void finalize() {
        try {
            read.close();
            write.close();
        } catch (Exception e) {
            System.out.println("Erro finalizar classe Page: " + e.getMessage());
        }
    }

}
