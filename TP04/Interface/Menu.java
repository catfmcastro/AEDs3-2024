package Interface;

import java.io.FileNotFoundException;
import java.util.Scanner;

import CSV.CriarBytes;
import RSA.RSA;

public class Menu {

  private int selected;
  private int maxID;
  private Scanner sc;
  private RSA rsa = new RSA();

  public Menu() {
    this.selected = 0;
    CriarBytes createbase = new CriarBytes();
    this.maxID = createbase.carregarBase(rsa);
    sc = new Scanner(System.in);
  }

  // Interface para seleção de opção pelo usuário
  public void selectOption() {

    System.out.println(
        "\n----------------- O que você gostaria de fazer? -----------------");
    System.out.println("1) Criar novo game");
    System.out.println("2) Ver game existente");
    System.out.println("3) Atualizar game existente");
    System.out.println("4) Deletar game");
    System.out.println("5) Busca pelo nome com Booyer Moore");
    System.out.println("6) Sair");

    int input = Integer.parseInt(sc.nextLine()); // input do usuário

    if (input < 1 || input > 10) {
      System.out.println(
          "\nOpção inválida inserida, por favor tente novamente:");
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
          Actions.createGame(maxID++);
          this.executeMenu();
          break;
        case 2: // Ver game existente
          Actions.searchGame(sc, rsa);
          this.executeMenu();
          break;
        case 3: // Atualizar game existente
          Actions.updateGame(sc);
          this.executeMenu();
          break;
        case 4: // Deletar game
          Actions.deleteGame(sc, rsa);
          this.executeMenu();
          break;
        case 5: // Reconhecer padrões por nome
          Actions.searchByName(sc, rsa);
          this.executeMenu();
          break;
        case 6: // Sair
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
