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
    System.out.println("2) [INDISPONIVEL] Criar novo game");
    System.out.println("3) Ver game existente");
    System.out.println("4) Atualizar game existente");
    System.out.println("5) Deletar game");
    System.out.println("6) Sair");

    int input = Integer.parseInt(sc.nextLine()); // input do usuário

    if (input < 1 || input > 6) {
      System.out.println(
        "\nOpção inválida inserida, por favor tente novamente:"
      );
      input = sc.nextInt();
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

        case 1:
          this.loadData();
          this.executeMenu();
          break;
        case 2:
          //Games tmp = new Games();
          System.out.println(
            "Essa função está indisponível no momento, tente novamente mais tarde.\n"
          );

          sc.nextLine();
          this.executeMenu();
          break;
        case 3:
          Games tmp = new Games();

          System.out.print("\nInsira o ID do game que deseja buscar: ");
          int inputId = sc.nextInt();

          System.out.println();
          tmp = this.readGame(inputId);          
          tmp.printGame();

          sc.nextLine();
          this.executeMenu();
          break;
        case 4:
          System.out.println("\nedit\n\n");

          sc.nextLine();
          this.executeMenu();
          break;
        case 5:
          System.out.println("\ndelete\n\n");

          sc.nextLine();
          this.executeMenu();
          break;
        case 6:
          // todo leave
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
