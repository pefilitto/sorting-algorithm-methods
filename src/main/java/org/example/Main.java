package org.example;

import org.example.Array.Array;
import org.example.File.Arquivo;
import org.example.File.Report;
import org.example.List.List;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand, copia;
        int tam = 5;


       /* Array array = new Array(11);
        List list = new List();

        for (int i = 0; i < 11; i++) {
            Random random = new Random();

            list.AddElement(random.nextInt(10));
            array.AddElement(random.nextInt(10));
        }

        System.out.println("Antes da Ordenacao");
        list.PrintList();

        System.out.println("Depois da ordenacao");
        list.Selection_Sort();
        list.PrintList();
        //list.PrintList();*/

        Report report = new Report();
        report.generateFiles();

       //arqOrd = new Arquivo("ArquivoOrdenado.dat");


    }
}