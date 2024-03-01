/*
 * Trabalho Prático 01
 *
 * @Tupacao - Arthur Santos
 * @catfmcastro - Catarina F. M. Castro
 *
 * AEDs III
 */

import View.Menu;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Menu menu = new Menu();

    try {
      System.out.println(
        "Olá! Seja bem-vindo(a) ao Banco de Dados da Cat e do Tupac!'"
      );

      menu.executeMenu();
    } catch (Exception e) {
      System.out.println("Erro ao abrir/manipular o arquivo binário" + e);
    }

    sc.close();
  }
}
