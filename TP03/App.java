import java.util.Scanner;

import CRUD.CRUD;
import Model.Games;

public class App {
    public static void main(String[] args) {

        System.out.println("Bem vindo ao Nosso CRUD de AEDS3 \n");
        System.out.println("Vamos carregar os seus dados primeiros para você testar.");
        CriarBytes createBD = new CriarBytes();
        int maxtam = createBD.carreagrBase();
        Scanner sc = new Scanner(System.in);
        HuffmanFuncional huff = new HuffmanFuncional();
        CRUD crud = new CRUD();
        Games aux = new Games();
        int opcao = 0;

        while (opcao != 7) {

            System.out.println("1) Criar novo game");
            System.out.println("2) Ver game existente");
            System.out.println("3) Atualizar game existente");
            System.out.println("4) Deletar game");
            System.out.println("Huffman -----------");
            System.out.println("5) Criar Huffman");
            System.out.println("6) Descompactar Huffman");
            System.out.println("7) SAIR da aplicacao, tmj");

            opcao = sc.nextInt();
            sc.nextLine();  // Consumir a nova linha após nextInt

            switch (opcao) {
                case 1:
                    aux.userInputGame(maxtam);
                    crud.create(aux);
                    break;
                case 2:
                    System.out.print("\nInsira o ID do game que deseja buscar: ");
                    int inputIdSearch = Integer.parseInt(sc.nextLine());
                    System.out.println(crud.search(inputIdSearch));
                    break;
                case 3:
                    System.out.print("\nInsira o ID do game que deseja editar: ");
                    int inputIdUpdate = Integer.parseInt(sc.nextLine());
                    aux = crud.search(inputIdUpdate);
                    System.out.println("\n" + aux.toString() + "\n");
                    aux.atualiza();
                    crud.update(inputIdUpdate, aux);
                    break;
                case 4:
                    System.out.print("\nInsira o ID do game que deseja deletar: ");
                    int inputIdDelete = Integer.parseInt(sc.nextLine());
                    crud.delete(inputIdDelete);
                    break;
                case 5:
                    huff.createCompressed();
                    break;
                case 6:
                    huff.createDecompressed();
                    break;
                case 7:
                    System.out.println("Até a próxima");
                    break;
                default:
                    System.out.println("Valor errado");
                    break;
            }
        }

        sc.close();
    }
}
