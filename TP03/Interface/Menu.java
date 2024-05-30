package Interface;

import Interface.Actions;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menu {

  private int selected;
  private Scanner sc;

  public Menu() {
    this.selected = 0;
    sc = new Scanner(System.in);
  }

  // Interface para seleção de opção pelo usuário
  public void selectOption() {
    System.out.println(
      "\n----------------- O que você gostaria de fazer? -----------------"
    );
    System.out.println("1) Criar novo game");
    System.out.println("2) Ver game existente");
    System.out.println("3) Atualizar game existente");
    System.out.println("4) Deletar game");
    System.out.println("5) Sair");

    int input = Integer.parseInt(sc.nextLine()); // input do usuário

    if (input < 1 || input > 10) {
      System.out.println(
        "\nOpção inválida inserida, por favor tente novamente:"
      );
      input = Integer.parseInt(sc.nextLine());
    }

    selected = input;
  }

  // Execução do menu
  public void executeMenu() {
    try {
      selected = 0;
      this.selectOption();
      this.executeOption();
    } catch (Exception e) {
      System.err.println("Erro ao executar Menu: " + e);
    }
  }

  // Execução das opções
  public void executeOption() throws FileNotFoundException {
    try {
      switch (this.selected) {
        case 1: // Criar novo game
          System.out.println("EM CONSTRUÇÃO");
          this.executeMenu();
          break;
        case 2: // Ver game existente
          Actions.searchGame(sc);
          this.executeMenu();
          break;
        case 3: // Atualizar game existente
          Actions.updateGame(sc);
          this.executeMenu();
          break;
        case 4: // Deletar game
          Actions.deleteGame(sc);
          this.executeMenu();
          break;
        case 5: // Sair
          System.out.println("\nObrigado por usar nosso Banco de Dados! :)");
          sc.close();
          break;
        default:
          break;
      }
    } catch (Exception e) {
      System.err.println("Erro na função executeOption: " + e);
    }
  }
}
