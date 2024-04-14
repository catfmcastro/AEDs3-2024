package View;

import Hash.HashActions;
import Model.Games;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menu extends HashActions {

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
    System.out.println("1) Carregar dados");
    System.out.println("2) Criar novo game");
    System.out.println("3) Ver game existente");
    System.out.println("4) Atualizar game existente");
    System.out.println("5) Deletar game");
    System.out.println();
    System.out.println("HASH -----------");
    System.out.println("6) Carregar dados para o Hash");
    System.out.println("7) Ver game existente utilizando Hash");
    System.out.println("8) Criar game utilizando Hash");
    System.out.println("9) Deletar game existente utilizando Hash");
    System.out.println();
    System.out.println("10) Sair");

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
      this.openFile();
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
        case 1: // Carregar dados
          this.loadData();
          this.executeMenu();
          break;
        case 2: // Criar novo game
          this.createGame(new Games());

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

          this.executeMenu();
          break;
        case 5: // Deletar game
          System.out.print("\nInsira o ID do game que deseja deletar: ");
          int inputIdDelete = Integer.parseInt(sc.nextLine());

          System.out.println();
          Games tmpDel = new Games();
          tmpDel = deleteGame(inputIdDelete);

          if (tmpDel != null) {
            System.out.println("Game deletado com sucesso!\n");
          } else {
            System.out.println(
              "Não foi possível deletar o game. Tente novamente."
            );
          }

          this.executeMenu();
          break;
        case 6: // Carregar dados para o Hash
          System.out.println("Carregando dados para Hash...");
          this.loadDataToHash();

          System.out.println(
            "Dados carregados para Hash Indexado com sucesso!"
          );

          this.executeMenu();
          break;
        case 7: // Ver game existente utilizando Hash
          Games tmpHashSearch = new Games();

          System.out.print("\nInsira o ID do game que deseja buscar: ");
          int inputIdSearchHash = Integer.parseInt(sc.nextLine());

          System.out.println();
          System.out.println("Buscando game...");
          System.out.println();

          tmpHashSearch = this.readHashh(inputIdSearchHash);

          if (tmpHashSearch == null) {
            System.out.println(
              "Esse game não existe ou já foi deletado! Tente outro game."
            );
          } else {
            tmpHashSearch.printGame();
          }

          this.executeMenu();
          break;
        case 8: // Criar game utilizando Hash
          this.createHashh(new Games());
          
          this.executeMenu();
          break;
        case 9: // Deletar game existente utilizando Hash
          System.out.print("Insira o ID do game que deseja deletar: ");
          int inputIdDeleteHash = Integer.parseInt(sc.nextLine());

          System.out.println();
          Games tmpDelHash = new Games();
          tmpDelHash = deleteHashh(inputIdDeleteHash);

          if (tmpDelHash != null) {
            System.out.println("Game deletado com sucesso!\n");
          } else {
            System.out.println(
              "Não foi possível deletar o game. Tente novamente."
            );
          }

          this.executeMenu();
          break;
        case 10: // Sair
          System.out.println("\nObrigado por usar nosso Banco de Dados! :)");
          sc.close();
          this.closeFile();
          break;
        default:
          break;
      }
    } catch (Exception e) {
      System.err.println("Erro na função executeOption: " + e);
    }
  }
}
