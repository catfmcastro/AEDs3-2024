package CRUD;

import Model.Games;

public class Teste {
    public static void main(String[] args) {
        CRUD crud = new CRUD();
        Games novo = crud.search(10);
        System.out.println(novo.toString());
    }
}
