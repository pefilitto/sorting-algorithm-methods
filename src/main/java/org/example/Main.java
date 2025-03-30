package org.example;

import org.example.Arquivo.Report;
import org.example.List.List;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List list = new List();

        for (int i = 0; i < 1031; i++) {
            Random random = new Random();

            list.AddElement(random.nextInt(10000));
        }

        System.out.println("Antes da Ordenacao");
        //array.PrintArray();
        //list.PrintList();


        System.out.println();

        System.out.println("Depois da ordenacao");
        //list.TimSort();
        //list.PrintList();

        Report report = new Report();
        report.generateFiles();
    }
}