package View;

import Controller.Actions;
import Model.Games;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menu extends Actions {

  private int selected;
  private Scanner sc;

  public Menu() {
    this.selected = 0;
    sc = new Scanner(System.in);
  }

  public void selectOption() {
    System.out.println("\nO que você gostaria de fazer?");
    System.out.println("1) Carregar dados");
    System.out.println("2) Criar novo game");
    System.out.println("3) Ver game existente");
    System.out.println("4) Atualizar game existente");
    System.out.println("5) Deletar game");
    System.out.println("6) Sair");

    int input = Integer.parseInt(sc.nextLine()); // input do usuário

    if (input < 1 || input > 6) {
      System.out.println(
        "\nOpção inválida inserida, por favor tente novamente:"
      );
      input = Integer.parseInt(sc.nextLine());
    }

    selected = input;
  }

  public void executeMenu() {
    try {
      selected = 0;
      this.openFile();
      this.selectOption();
      this.executeOption();
    } catch (Exception e) {
      System.err.println("Erro ao executar Menu: " + e);
    }
  }

  public void executeOption() throws FileNotFoundException {
    try {
      switch (this.selected) {
        case 1: // Carregar dados
          this.loadData();
          this.executeMenu();
          break;
        case 2: // Criar novo game
          this.createGame(new Games());

          System.out.println("continuando o create...");
          this.executeMenu();
          break;
        case 3: // Ver game existente
          Games tmp = new Games();

          System.out.print("\nInsira o ID do game que deseja buscar: ");
          int inputIdSearch = Integer.parseInt(sc.nextLine());

          System.out.println();
          tmp = this.readGame(inputIdSearch);

          if (tmp == null) {
            System.out.println(
              "Esse game não existe ou já foi deletado! Tente outro game."
            );
          } else {
            tmp.printGame();
          }

          sc.nextLine();
          this.executeMenu();
          break;
        case 4: // Atualizar game existente
          System.out.print("\nInsira o ID do game que deseja editar: ");
          int inputIdUpdate = Integer.parseInt(sc.nextLine());
          Games aux = new Games();
          
          boolean done = this.updateGame(inputIdUpdate, aux);

          if (done) {
            System.out.println("Game atualizado com sucesso!");
          } else {
            System.out.println(
              "Não foi possível atualizar o game. Tente novamente."
            );
          }

          System.out.println("continuando o update...");
          this.executeMenu();
          break;
        case 5: // Deletar game
          System.out.print("\nInsira o ID do game que deseja deletar: ");
          int inputIdDelete = Integer.parseInt(sc.nextLine());

          System.out.println();
          this.deleteGame(inputIdDelete);

          sc.nextLine();
          this.executeMenu();
          break;
        case 6: // Sair
          System.out.println("\nObrigado por usar nosso Banco de Dados! :)");
          sc.close();
          this.closeFile();
          break;
        default:
          break;
      }
    } catch (Exception e) {
      System.err.println("Erro na função executeMenu: " + e);
    }
  }
}
