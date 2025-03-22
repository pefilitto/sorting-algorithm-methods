package org.example.Arquivo;

public class Report {
    //long tempoInicio, tempoFinal, timeInicio, timeFinal;
    Arquivo arqOrd, arqRev, arqRand, copia;

    int tam = 8;

    public void generateFiles() {
        arqRand = new Arquivo("ArquivoRandomico.dat");
        arqRand.geraArquivoDesordenado(tam);

        copia = new Arquivo("copiaArq.dat");
        copia.copiaArq(arqRand.getFile());

        System.out.println("Antes da ordenacao Copia");
        copia.exibirArq();

        copia.MergeSortImplem2();
        System.out.println("Depois da ordenacao");
        copia.exibirArq();

        System.out.println("Depois da ordenacao Original");
        arqRand.exibirArq();
    }
    private void initializeFiles() {}
}
