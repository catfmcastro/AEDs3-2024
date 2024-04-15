package BTree.Model;

import java.io.RandomAccessFile;

import Model.Games;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Page {
    private int tamPage, numberElement, vetNumber[];
    private long vetPointer[], vetElementPointer[];
    private RandomAccessFile write, read;

    public Page() {
        try {
            this.write = new RandomAccessFile("./TP02/BTree/tree_bd/index.bd", "rw");
            this.read = new RandomAccessFile("./TP02/BTree/tree_bd/index.bd", "r");

            this.numberElement = 0;
            this.tamPage = 10;
            this.vetNumber = new int[tamPage];
            this.vetPointer = new long[tamPage + 1];
            this.vetElementPointer = new long[tamPage];
            vetPointer[0] = -2;
            for (int i = 0; i < tamPage; i++) {
                vetNumber[i] = -1;
                vetElementPointer[i] = -1;
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
                dos.writeLong(vetElementPointer[i]);
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
                vetElementPointer[i] = read.readLong();
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

    private void insereElemento(int elemento, long elementPointer) {
        for (int i = 0; i < vetNumber.length; i++) {
            if (vetNumber[i] == -1) {
                vetNumber[i] = elemento;
                vetElementPointer[i] = elementPointer;
                numberElement++;
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

    private long getAnterior(long pagina, int number, long achar) {
        Page tmp = new Page();
        tmp.readPage(pagina);
        for (int i = 0; i < tmp.vetPointer.length; i++) {
            if (tmp.vetPointer[i] == achar) {
                return pagina;
            }
        }

        for (int i = 0; i < tmp.vetNumber.length; i++) {
            if (tmp.vetNumber[tmp.vetNumber.length - 1] != -1 && number > tmp.vetNumber[tmp.vetNumber.length - 1]) {
                return getAnterior(tmp.vetPointer[tmp.vetPointer.length - 1], number, achar);
            } else if (tmp.vetNumber[i] != -1 && number < tmp.vetNumber[i]) {
                return getAnterior(tmp.vetPointer[i], number, achar);
            } else if (tmp.vetNumber[i] != -1 && number > tmp.vetNumber[i] && number < tmp.vetNumber[i + 1]
                    || tmp.vetNumber[i + 1] == -1) {
                return getAnterior(tmp.vetPointer[i + 1], number, achar);
            }
        }

        return -1;
    }

    private boolean subir(int number, long posicao, int subir, long subirPonteiro, long ponteiro) {
        try {
            if (ponteiro == cadeRaiz()) {
                Page aux = new Page();
                long firstpage = read.length();

                for (int i = (vetNumber.length / 2) + 1; i < vetNumber.length; i++) {
                    aux.insereElemento(vetNumber[i], vetElementPointer[i]);
                }
                aux.insereElemento(number, posicao);
                for (int i = (vetNumber.length / 2) + 1; i < vetNumber.length; i++) {
                    vetNumber[i] = -1;
                    vetElementPointer[i] = -1;
                    numberElement--;
                }

                aux.insereRaiz(firstpage);
                long newraiz = read.length();
                aux = new Page();
                aux.insereElemento(subir, subirPonteiro);
                aux.inserePonteiro(ponteiro);
                aux.inserePonteiro(firstpage);
                aux.insereRaiz(newraiz);
                write.seek(0);
                write.writeLong(newraiz);
                return true;
            }

            Page aux = new Page();
            long temp = getAnterior(cadeRaiz(), number, ponteiro);
            aux.readPage(temp);
            if (aux.numberElement < tamPage) {
                for (int i = 0; i < vetNumber.length; i++) {
                    if (aux.vetNumber[i] == -1) {
                        aux.insereElemento(number, posicao);
                        long firstpage = read.length();
                        aux.inserePonteiro(firstpage);
                        aux.insereRaiz(temp);

                        Page temp1 = new Page();
                        Page temp2 = new Page();

                        temp1.readPage(ponteiro);

                        for (int j = (temp1.vetNumber.length / 2) + 1; j < temp1.vetNumber.length; j++) {
                            temp2.insereElemento(vetNumber[j], vetElementPointer[j]);
                        }

                        for (int j = (vetNumber.length / 2) + 1; j < vetNumber.length; j++) {
                            temp1.vetNumber[j] = -1;
                            temp1.vetElementPointer[j] = -1;
                            temp1.numberElement--;
                        }
                        temp1.insereRaiz(ponteiro);
                        temp2.insereRaiz(firstpage);
                        return true;
                    }
                }
            } else if (aux.numberElement >= tamPage) {
                return subir(number, temp, aux.vetNumber[vetNumber.length / 2],
                        aux.vetElementPointer[vetElementPointer.length / 2], ponteiro);
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
                long subirPonteiro = vetElementPointer[vetElementPointer.length / 2];
                return subir(number, posicao, subir, subirPonteiro, ponteiro);
            } else {
                for (int i = 0; i < vetNumber.length; i++) {
                    if (vetNumber[vetNumber.length - 1] != -1 && number > vetNumber[vetNumber.length - 1]) {
                        return insereID(number, posicao, vetPointer[vetPointer.length - 1]);
                    } else if (vetNumber[i] != -1 && number < vetNumber[i]) {
                        return insereID(number, posicao, vetPointer[i]);
                    } else if (vetNumber[i] != -1 && number > vetNumber[i] && number < vetNumber[i + 1]
                            || vetNumber[i + 1] == -1) {
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

    public boolean procura(int value) {
        return procura(value, cadeRaiz());
    }

    private boolean procura(int number, long ponteiro) {
        readPage(ponteiro);

        for (int i = 0; i < vetNumber.length; i++) {
            if (vetNumber[i] == number) {
                return true;
            }
        }

        for (int i = 0; i < vetNumber.length; i++) {
            if (vetNumber[vetNumber.length - 1] != -1 && number > vetNumber[vetNumber.length - 1]) {
                return procura(number, vetPointer[vetPointer.length - 1]);
            } else if (vetNumber[i] != -1 && number < vetNumber[i]) {
                return procura(number, vetPointer[i]);
            } else if (vetNumber[i] != -1 && number > vetNumber[i] && number < vetNumber[i + 1]
                    || vetNumber[i + 1] == -1) {
                return procura(number, vetPointer[i + 1]);
            }
        }

        return false;
    }

    public boolean updade (int number) {
        insereID(number, cadeRaiz());
        return true;
    }

    public boolean delete(int number) {
        try {
            long game = delete(number, cadeRaiz());
            RandomAccessFile readBD = new RandomAccessFile("./TP02/BTree/Indices/aux.bd", "r");
            RandomAccessFile writedBD = new RandomAccessFile("./TP02/BTree/Indices/aux.bd", "rw");
            readBD.seek(game);

            Games temporario = new Games();
            int auxtam = readBD.readInt();
            byte teste[] = new byte[auxtam];
            readBD.read(teste);

            temporario.fromByteArray(teste);
            temporario.printGame();

            temporario.setGrave(true);
            writedBD.seek(game);
            writedBD.write(temporario.byteParse());
            readBD.close();
            writedBD.close();

            return true;
        } catch (Exception e) {
            System.out.println("Erro delte: " + e.getMessage());
            return false;
        }
    }

    private long delete(int number, long pos) {
        readPage(pos);

        for (int i = 0; i < vetNumber.length; i++) {
            if (vetNumber[i] == number) {
                return vetElementPointer[i];
            }
        }

        for (int i = 0; i < vetNumber.length; i++) {
            if (vetNumber[vetNumber.length - 1] != -1 && number > vetNumber[vetNumber.length - 1]) {
                return delete(number, vetPointer[vetPointer.length - 1]);
            } else if (vetNumber[i] != -1 && number < vetNumber[i]) {
                return delete(number, vetPointer[i]);
            } else if (vetNumber[i] != -1 && number > vetNumber[i] && number < vetNumber[i + 1]
                    || vetNumber[i + 1] == -1) {
                return delete(number, vetPointer[i + 1]);
            }
        }

        return -1;
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