/*
 * Trabalho Prático 01
 *
 * @Tupacao - Arthur Santos
 * @catfmcastro - Catarina F. M. Castro
 *
 * AEDs III
 */

import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Scanner;

import Controller.Crud;
import Model.Games;

public class App {

  static final int inicialYear = 1900;

  // Transformar data em horas a partir de 1900
  public static int dateToHours(String data) {
    // Um ano tem 8766 h
    // Um mês tem 730 h
    // Um dia tem 24 h

    String tmp[] = data.split("/");
    int year, month, day;

    year = (Integer.parseInt(tmp[2]) - inicialYear) * 8760;
    month = (Integer.parseInt(tmp[1])) * 730;
    day = Integer.parseInt(tmp[0]) * 24;

    return year + month + day;
  }

  public static void appLabel() {
    System.out.println(
      "O que você gostaria de fazer?\n1) Criar novo game\n2) Ver game existente\n3) Atualizar game existente\n4) Deletar game\n5) Sair"
    );
  }

  // PREENCHER ARQUIVO BINÁIRO
  public static void buildBinaryFile() {
    RandomAccessFile raf, write;
    try {
      // Acessando o arquivo
      raf = new RandomAccessFile("./src/games.csv", "r");
      write = new RandomAccessFile("./out/games.db", "rw");
      raf.readLine(); // ! raf.seek nao funciona por causa de algum espaço nulo

      String str;
      write.writeLong(10); // Reserva um espaço no inicio do arquivo como um long int para inserir a posição do final do arquivo

      // Preenchimento do arquivo binário
      while ((str = raf.readLine()) != null) {
        // Leitura do csv
        String vet[] = str.split("./;");
        Games tmp = new Games(
          false,
          Integer.parseInt(vet[0]),
          Integer.parseInt(vet[1]),
          Float.parseFloat(vet[3]),
          vet[2],
          vet[4],
          vet[5],
          vet[6],
          vet[7],
          dateToHours(vet[8])
        );

        byte aux[] = tmp.toByteArray(); // Insere no arquivo binário
        write.write(aux.length); // Add o tamanho de cada vetor antes dos dados
        write.write(aux); // Insere o vetor de bytes
      }

      long last = write.getFilePointer(); // Última posicao do arquivo
      write.seek(0); // Posiciona novamente no inicio do arquivo
      write.writeLong(last); // Escreve a ultima posicao na frente

      // ? testando toString

      write.close();
      raf.close();
    } catch (Exception e) {
      System.out.println("Erro ao preencher o arquivo binário: " + e);
    }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    // * Método para a construção do arquivo binário
    //buildBinaryFile();

    try {
      RandomAccessFile arq = new RandomAccessFile("./out/games.db", "rw");
      System.out.println(
        "Olá! Seja bem-vindo(a) ao Arquivo Binário da Cat e do Tupac (A.B.C.T)"
      );

      appLabel();
      int in = sc.nextInt();

      while (in != 5) {
        if (in == 1) {
          System.out.println("\nexecutar create\n\n");
        } else if (in == 2) {
          System.out.println("\nexecutar read\n\n");

          System.out.println("\nInsira o ID do game que deseja ver: ");
          int inId = sc.nextInt();
          
        } else if (in == 3) {
          System.out.println("\nexecutar update\n\n");
        } else if (in == 4) {
          System.out.println("\nexecutar delete\n\n");
        }

        appLabel();
        in = sc.nextInt();
      }

      System.out.println("\n\nObrigado, até mais!");

      arq.close();
    } catch (Exception e) {
      System.out.println("Erro ao abrir/manipular o arquivo binário" + e);
    }

    sc.close();
  }
}
