package BTree.Model;

import java.io.RandomAccessFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Page {
    private int tamPage, numberElement, vetNumber[];
    private long vetPointer[];
    private RandomAccessFile write, read;

    private void insertionSort() {
        for (int i = 1; i < tamPage; i++) {
            int j = i - 1;
            int temp = vetNumber[i];
            while (j <= 0 && temp < vetNumber[j]) {
                vetNumber[j + 1] = vetNumber[j];
                j--;
            }
            vetNumber[i] = temp;
        }
    }

    public Page() {
        try {
            this.write = new RandomAccessFile("./TP02/BTree/tree_bd/index.bd", "rw");
            this.read = new RandomAccessFile("./TP02/BTree/tree_bd/index.bd", "r");

            this.numberElement = 0;
            this.tamPage = 10;
            this.vetNumber = new int[tamPage];
            this.vetPointer = new long[tamPage + 1];
            vetPointer[0] = -2;
            for (int i = 0; i < tamPage; i++) {
                vetNumber[i] = -1;
                vetPointer[i + 1] = -2;
            }
        } catch (Exception e) {
            System.err.println("Erro criar pagina: " + e.getMessage());
        }
    }

    public void insereRaiz(long pos) {
        try {
            write.seek(pos);
            ByteArrayOutputStream by = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(by);
            dos.writeInt(numberElement);
            dos.writeLong(vetPointer[0]);
            for (int i = 0; i < tamPage; i++) {
                dos.writeInt(vetNumber[i]);
                dos.writeLong(vetPointer[i + 1]);
            }
            write.write(by.toByteArray());
        } catch (Exception e) {
            System.out.println("Erro inserir página: " + e.getMessage());
        }
    }

    public void readPage(long pos) {
        try {
            read.seek(pos);
            this.numberElement = read.readInt();
            this.vetPointer[0] = read.readLong();
            for (int i = 0; i < tamPage; i++) {
                vetNumber[i] = read.readInt();
                vetPointer[i + 1] = read.readLong();
            }
            read.seek(0);
        } catch (Exception e) {
            System.out.println("Erro ler página: " + e.getMessage());
        }
    }

    private long cadeRaiz() {
        try {
            read.seek(0);
            long resp = read.readLong();
            read.seek(0);
            return resp;
        } catch (Exception e) {
            System.out.println("Volta ponteriro raiz: " + e.getMessage());
            return -1;
        }
    }

    private boolean isRaiz() {
        int count = 0;
        for (int i = 0; i < vetPointer.length; i++) {
            if (vetPointer[i] == -2) {
                count++;
            }
        }

        return count == tamPage + 1;
    }

    private void insereElemento(int elemento) {
        for (int i = 0; i < vetNumber.length; i++) {
            if (vetNumber[i] == -1) {
                vetNumber[i] = elemento;
                break;
            }
        }
    }

    private void inserePonteiro(long elemento) {
        for (int i = 0; i < vetPointer.length; i++) {
            if (vetPointer[i] == -2) {
                vetPointer[i] = elemento;
                break;
            }
        }
    }

    private boolean subir(int number, long posicao, int subir, long ponteiro) {
        try {
            if(ponteiro == cadeRaiz()){
                Page aux = new Page();
                long firstpage = read.length();
                
                for (int i = (vetNumber.length / 2) + 1; i < vetNumber.length; i++) {
                    aux.insereElemento(vetNumber[i]);
                }
                aux.insereElemento(number);
                for (int i = (vetNumber.length / 2) + 1; i < vetNumber.length; i++) {
                    vetNumber[i] = -1;
                }

                aux.insereRaiz(firstpage);
                long newraiz = read.length();
                aux = new Page();
                aux.insereElemento(subir);
                aux.inserePonteiro(ponteiro);
                aux.inserePonteiro(firstpage);
                aux.insereRaiz(newraiz);
                write.seek(0);
                write.writeLong(newraiz);
                return true;
            }



            return false;
        } catch (Exception e) {
            System.err.println("Error subir " + e.getMessage());
            return false;
        }
    }

    public boolean insereID(int number, long posicao) {
        return insereID(number, posicao, cadeRaiz());
    }

    private boolean insereID(int number, long posicao, long ponteiro) {
        try {
            readPage(ponteiro);
            if (isRaiz() && numberElement < tamPage) {
                numberElement++;
                for (int i = 0; i < vetNumber.length; i++) {
                    if (vetNumber[i] == -1) {
                        vetNumber[i] = number;
                        insereRaiz(ponteiro);
                        return true;
                    }
                }
            } else if (isRaiz() && numberElement >= tamPage) {
                int subir = vetNumber[vetNumber.length / 2];
                return subir(number, posicao, subir, ponteiro);
            } else {
                for (int i = 0; i < vetNumber.length; i++) {
                    if (vetNumber[vetNumber.length-1] != -1 && number > vetNumber[vetNumber.length-1]) {
                        return insereID(number, posicao, vetPointer[vetPointer.length-1]);
                    } else if (vetNumber[i] != -1 && number < vetNumber[i]) {
                        return insereID(number, posicao, vetPointer[i]);
                    } else if (vetNumber[i] != -1 && number > vetNumber[i] && number < vetNumber[i + 1] || vetNumber[i + 1] == -1) {
                        return insereID(number, posicao, vetPointer[i + 1]);
                    }
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erro insereID: " + e.getMessage());
            return false;
        }
    }

    public void printScreen() {
        System.out.println("Quantos elementos: " + numberElement);
        System.out.print("Os elementos: ");
        for (int i = 0; i < tamPage; i++) {
            System.out.print(" " + vetNumber[i]);
        }
        System.out.print("\nOs ponteiros: ");
        for (int i = 0; i < (tamPage + 1); i++) {
            System.out.print(" " + vetPointer[i]);
        }
    }

}