package Interface;

import CRUD.CRUD;
import Model.Games;
import java.util.Scanner;

public class Actions {

  public static void searchGame(Scanner sc) {
    System.out.print("Insira o ID do game que quer buscar: ");
    int id = Integer.parseInt(sc.nextLine());

    while (id < 0) {
      System.out.println("ID inválido, tente novamente.");
      id = Integer.parseInt(sc.nextLine());
    }

    CRUD crud = new CRUD();
    Games tmp = crud.search(id);

    if (tmp == null) {
      System.out.println("Game não encontrado.");
    } else {
      System.out.println(tmp.toString());
    }
  }

  public static void deleteGame(Scanner sc) {
    System.out.print("Insira o ID do game que quer deletar: ");
    int id = Integer.parseInt(sc.nextLine());

    while (id < 0) {
      System.out.println("ID inválido, tente novamente.");
      id = Integer.parseInt(sc.nextLine());
    }

    CRUD crud = new CRUD();
    Games tmp = crud.delete(id);

    if (tmp == null) {
      System.out.println("Game não encontrado.");
    } else {
      System.out.println(tmp.toString());
      System.out.println("Game deletado com sucesso!");
    }
  }

  public static void updateGame(Scanner sc) {
    System.out.print("Insira o ID do game que quer atualizar: ");
    int id = Integer.parseInt(sc.nextLine());

    Games aux = new Games();
    CRUD crud = new CRUD();

    // !! CRIAR A FUNÇÃO USER INPUT TO OBJECT
    //aux.userInputGame();

    boolean done = crud.update(id, aux);

    if (done) {
      System.out.println("Game atualizado com sucesso!");
    } else {
      System.out.println("Não foi possível atualizar o game. Tente novamente.");
    }
  }

  public static void createGame(Scanner sc) {

  }
}
