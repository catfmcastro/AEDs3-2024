package BTree;

import java.util.Scanner;

import BTree.Model.ArvoreB;

public class App {

    public static void main(String[] args) {
        int value = 0;
        ArvoreB arvore = new ArvoreB();
        int ID = 0;
        long ponteiro = 0;
        Scanner scanner = new Scanner(System.in);

        while (value != 5) {
            System.out.println("O que deseja fazer:");
            System.out.println("1 - CarregaIndices\n 2 - CriaGame\n 3 - Delete game\n 4 - Procura Jogo\n 5 - Sair");
            value = scanner.nextInt();
            switch (value) {
                case 1:
                    arvore.insereINDICES();
                    break;
                case 2:
                    ID = scanner.nextInt();
                    arvore.createGame(ID, ponteiro);
                    break;
                case 3:
                    ID = scanner.nextInt();
                    arvore.delete(ID);
                    break;
                case 4:
                    ID = scanner.nextInt();
                    arvore.seachGame(ID);
                    break;

                default:
                    break;
            }
        }
        scanner.close();
    }
}
