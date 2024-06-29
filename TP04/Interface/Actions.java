package Interface;

import CRUD.CRUD;
import Model.Games;
import RSA.RSA;

import java.util.ArrayList;
import java.util.Scanner;

public class Actions {

  public static void searchGame(Scanner sc, RSA rsa) {
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
      System.out.println(tmp.toString(rsa));
    }
  }

  public static void deleteGame(Scanner sc, RSA rsa) {
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
      System.out.println(tmp.toString(rsa));
      System.out.println("Game deletado com sucesso!");
    }
  }

  public static void updateGame(Scanner sc) {
    System.out.print("Insira o ID do game que quer atualizar: ");
    int id = Integer.parseInt(sc.nextLine());

    Games aux = null;
    CRUD crud = new CRUD();

    aux = crud.search(id);
    aux.atualiza();
    boolean done = crud.update(id, aux);


    if (done) {
      System.out.println("Game atualizado com sucesso!");
    } else {
      System.out.println("Não foi possível atualizar o game. Tente novamente.");
    }
  }

  public static void createGame(int maxID) {
    Games aux = new Games();
    aux.userInputGame(maxID);
    CRUD crud = new CRUD();
    if(crud.create(aux)){
      System.out.println("Criado com sucesso");
    } else{
      System.out.println("Erro ao criar");
    }
  }

  public static void searchByName (Scanner sc, RSA rsa){
    System.out.println("Insira o padrao que se deseja buscar:");
    String str = sc.nextLine();
    CRUD crud = new CRUD();
    ArrayList<Games> games = crud.searchByName(str, rsa);
    for (Games game : games) {
      System.out.println(game.toString(rsa));
    }
  }

}
