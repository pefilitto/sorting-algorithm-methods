package org.example;

import org.example.Array.Array;
import org.example.List.List;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Array array = new Array(101);
        //List list = new List();

        for (int i = 0; i < 100; i++) {
            Random random = new Random();

            array.AddElement(random.nextInt(100));
        }

        System.out.println("Antes da Ordenacao");
        array.PrintArray();

        System.out.println("Depois da ordenacao");
        array.InsertionSort();
        array.PrintArray();
        //list.PrintList();
    }
}