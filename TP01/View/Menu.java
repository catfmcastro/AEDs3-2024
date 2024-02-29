package View;

import Controller.Actions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// todo extends Actions
public class Menu extends Actions {

  private int selected;
  private Scanner sc;

  public Menu() throws IOException {
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
      //this.executeOption
    } catch (Exception e) {
      System.err.println("Erro: " + e);
    }
  }

  public void executeOption() throws FileNotFoundException {
    switch (this.selected) {
      case 1:
        //todo load data
        break;
      case 2:
        //todo create game
        break;
      case 3:
        //todo 
        break;
      case 4:
        break;
      case 5:
        break;
      case 6:
        break;
      default:
        break;
    }
  }
}
