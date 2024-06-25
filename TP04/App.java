/*
 * Trabalho Prático 03
 *
 * @Tupacao - Arthur Santos
 * @catfmcastro - Catarina F. M. Castro
 *
 * AEDs III
 */

import Interface.Menu;

public class App {

  public static void main(String[] args) {
    
    System.out.println(
      "Olá! Seja bem-vindo(a) ao Banco de Dados da Cat e do Tupac!"
    );

    System.out.println(
      "Vamos carregar a base da dados para que você possa desfrutar de todas as funções!!"
    );

    Menu menu = new Menu();

    menu.executeMenu();
  }
}
