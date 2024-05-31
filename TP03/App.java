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
    Menu menu = new Menu();

    System.out.println(
      "Olá! Seja bem-vindo(a) ao Banco de Dados da Cat e do Tupac!"
    );

    menu.executeMenu();
  }
}
