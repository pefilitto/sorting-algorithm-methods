package org.example;

import org.example.Arquivo.Report;
import org.example.Array.Array;
import org.example.List.List;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Array array = new Array(11);
        List list = new List();

        for (int i = 0; i < 11; i++) {
            Random random = new Random();

            list.AddElement(random.nextInt(50));
            array.AddElement(random.nextInt(10));
        }

        System.out.println("Antes da Ordenacao");
        //array.PrintArray();
        //list.PrintList();


        System.out.println();

        System.out.println("Depois da ordenacao");
        //list.ShakeSort();
        //list.PrintList();

        Report report = new Report();
        report.generateFiles();
    }
}