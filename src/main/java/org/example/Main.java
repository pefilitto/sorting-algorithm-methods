package org.example;

import org.example.Arquivo.Report;
import org.example.List.List;
import org.example.Table.Principal;

import java.io.IOException;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws IOException {
        Principal p = new Principal();
        p.geraTabela();
    }
}