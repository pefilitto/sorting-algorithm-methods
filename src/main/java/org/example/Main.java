package org.example;

import org.example.List.List;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List list = new List();

        for (int i = 0; i < 10; i++) {
            Random random = new Random();

            list.AddElement(random.nextInt(100));
        }

        list.PrintList();
    }
}