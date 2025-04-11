package org.example.Table;

import org.example.Arquivo.Arquivo;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Principal {
    long tempoInicio, tempoFinal, timeInicio, timeFinal;
    Arquivo arqOrd, arqRev, arqRand, auxRev, auxRand, copia;

    int tam = 1024;
    RandomAccessFile tabela;

    public void geraArquivos() throws IOException {
        arqOrd = new Arquivo("ArquivoOrdenado.dat");
        arqRev = new Arquivo("ArquivoReverso.dat");
        arqRand = new Arquivo("ArquivoRandomico.dat");
        auxRand = new Arquivo("copiaarqrev.dat");
        auxRev = new Arquivo("copiaarqrev.dat");

        gerarArquivos();
    }

    public void createFiles() throws IOException {
        System.out.println("Wait, creating files...\n\n");
        gerarArquivos();
        arqOrd.geraArquivoOrdenado(tam);
        arqRand.geraArquivoDesordenado(tam);
        arqRev.geraArquivoReverso(tam);
    }

    public void gerarArquivos() throws IOException {
        arqOrd = new Arquivo("ArquivoOrdenado.dat");
        arqRand = new Arquivo("ArquivoRandomico.dat");
        arqRev = new Arquivo("ArquivoReverso.dat");
        java.io.File fileCopyAux = new java.io.File("copiaArq.txt");

        /*if (fileCopyAux.exists())
            fileCopyAux.delete();*/

        copia = new Arquivo("copiaArq.dat");
        java.io.File tableFile = new java.io.File("Tabela.txt");
        if (tableFile.exists())
            tableFile.delete();
        try {
            tabela = new RandomAccessFile(tableFile, "rw");
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    void gravarTopo() throws IOException {
        tabela.writeBytes("Métodos de ordenação\n");

        tabela.writeBytes("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        tabela.writeBytes(String.format("%-25s%-80s%-80s%-80s%n", "| Algoritmos", "| Arquivo Ordenado", "| Arquivo Reverso", "| Arquivo Randomico                                                                 |"));
        tabela.writeBytes("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        tabela.writeBytes("|                        |  Comp.Prog. *  |  Comp.Equa. #  |  Mov.Prog. +  |  Mov Equa. -   |    Tempo    |  Comp.Prog. *  |  Comp.Equa. #  |  Mov.Prog. +  |  Mov Equa. -   |    Tempo    |  Comp.Prog. *  |  Comp.Equa. #  |  Mov.Prog. +  |  Mov Equa. -   |    Tempo    |\n");
        tabela.writeBytes("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    public void geraTabela() throws IOException {
        gerarArquivos();
        createFiles();
        gravarTopo();
        timeInicio = System.currentTimeMillis();
        runInsertionSort();
        runBinnaryInsertionSort();
        runSelectionSort();
        runBubbleSort();
        runShakeSort();
        runShellSort();
        runHeapSort();
        runQuickSPivo();
        runQuickCPivo();
        runFirstMerge();
        runSecondMerge();
        runCountingSort();
        runBucketSort();
        runRadixSort();
        runCombSort();
        runGnomeSort();
        runTimSort();
        timeFinal = System.currentTimeMillis();
        tabela.writeBytes("Tempo total: " + (timeFinal - timeInicio) / 1000 + "segundos");
    }

    private void runInsertionSort() throws IOException {
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.InsertionSort();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Insertion Sort", copia.getComparacoes(), tam - 1, copia.getMovimentacoes(), 3 * (tam - 1), duration));

        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.InsertionSort();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), (tam * tam + tam - 4) / 4, copia.getMovimentacoes(), (tam * tam + 3 * tam - 4) / 2, duration));

        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.InsertionSort();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), (int) ((tam * tam + tam - 2) / 4), copia.getMovimentacoes(), (tam * tam + 9 * tam - 10 ) / 4, duration));
    }

    private  void runBinnaryInsertionSort() throws IOException {
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.BinaryInsertionSort();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Binnary Insertion", copia.getComparacoes(), (int) (tam * (Math.log(tam) - Math.log(Math.E) + 0.5))
                , copia.getMovimentacoes(), 3 * (tam - 1), duration));

        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.BinaryInsertionSort();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), (int) (tam * (Math.log(tam) - Math.log(Math.E) + 0.5)), copia.getMovimentacoes(), (int) ((Math.pow(tam, 2)+ 3 * tam - 4) / 2), duration));

        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.BinaryInsertionSort();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), (int) (tam * (Math.log(tam) - Math.log(Math.E) + 0.5)), copia.getMovimentacoes(), (int) ((Math.pow(tam, 2) + 9 * tam - 10) / 4) , duration));
    }

    private void runSelectionSort() throws  IOException {
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.SelectionSort();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Selection Sort", copia.getComparacoes(), (tam*tam-tam)/2
                , copia.getMovimentacoes(), 3 * (tam - 1), duration));

        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.SelectionSort();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(),  (tam*tam-tam)/2, copia.getMovimentacoes(), tam * tam / 4 + 3 * (tam - 1), duration));

        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.SelectionSort();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), (tam*tam-tam)/2, copia.getMovimentacoes(), (int) (tam * (Math.log(tam) + 0.577216)) , duration));
    }

    private void runBubbleSort() throws IOException {
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.BubbleSort();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Bubble Sort", copia.getComparacoes(),(int) ((Math.pow(tam, 2)- tam) / 2), copia.getMovimentacoes(), 0, duration));

        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.BubbleSort();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), (int) ((Math.pow(tam, 2)- tam) / 2), copia.getMovimentacoes(), 3 * (tam * tam - tam) / 4, duration));

        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.BubbleSort();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), (int) ((Math.pow(tam, 2)- tam) / 2), copia.getMovimentacoes(), 3 * (tam * tam - tam) / 2, duration));
    }

    public void runShakeSort() throws IOException {
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.ShakeSort();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Shake Sort", copia.getComparacoes(), (tam * tam - tam) / 2, copia.getMovimentacoes(), 0, duration));

        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.ShakeSort();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), (tam * tam - tam) / 2, copia.getMovimentacoes(), 3 * (tam * tam - tam) / 4, duration));

        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.ShakeSort();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), (tam * tam - tam) / 2, copia.getMovimentacoes(), 3 * (tam * tam - tam) / 2, duration));
    }

    public void runShellSort() throws IOException {
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.ShellSort();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Shell Sort", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.ShellSort();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.ShellSort();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }

    public void runHeapSort() throws IOException {
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.HeapSort();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Heap Sort", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.HeapSort();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.HeapSort();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }

    public void runQuickSPivo() throws IOException {
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.QuickSP();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Quick s/ pivo", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.QuickSP();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.QuickSP();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }

    public void runQuickCPivo() throws IOException {
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.QuickCP();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Quick c/ pivo", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.QuickCP();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.QuickCP();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }

    public void runFirstMerge() throws IOException {
        System.out.println("Ordenado");
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.MergeSortImplem1();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Merge 1 Implementacao", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Reverso");
        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.MergeSortImplem1();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Randomico");
        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.MergeSortImplem1();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }

    public void runSecondMerge() throws IOException {
        System.out.println("Ordenado");
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.MergeSortImplem2();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Merge 2 Implementacao", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Reverso");
        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.MergeSortImplem2();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Randomico");
        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.MergeSortImplem2();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }

    public void runCountingSort() throws IOException {
        System.out.println("Ordenado");
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.CountingSort();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Counting Sort", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Reverso");
        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.CountingSort();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Randomico");
        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.CountingSort();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }

    public void runRadixSort() throws IOException {
        System.out.println("Ordenado");
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.RadixSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Radix Sort", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Reverso");
        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.RadixSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Randomico");
        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.RadixSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }

    public void runCombSort() throws IOException {
        System.out.println("Ordenado");
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.CombSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Comb Sort", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Reverso");
        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.CombSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Randomico");
        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.CombSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }

    public void runGnomeSort() throws IOException {
        System.out.println("Ordenado");
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.GnomeSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Gnome Sort", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Reverso");
        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.GnomeSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Randomico");
        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.GnomeSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }

    public void runTimSort() throws IOException {
        System.out.println("Ordenado");
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.TimSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Tim Sort", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Reverso");
        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.TimSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Randomico");
        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.TimSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
        tabela.writeBytes("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    public void runBucketSort() throws IOException {
        System.out.println("Ordenado");
        copia.copiaArq(arqOrd.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.BucketSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        long duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("| %-22s |  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", "Bucket Sort", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Reverso");
        copia.copiaArq(arqRev.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.BucketSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();

        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s ", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Randomico");
        copia.copiaArq(arqRand.getFile());
        copia.setComparacoes(0);
        copia.setMovimentacoes(0);
        tempoInicio = System.currentTimeMillis();
        copia.BucketSort();
        copia.exibirArq();
        tempoFinal = System.currentTimeMillis();
        duration = (tempoFinal - tempoInicio) / 1000;
        tabela.writeBytes(String.format("|  %-13s |  %-13s |  %-12s |  %-13s |   %-8s |\n\n", copia.getComparacoes(), -1, copia.getMovimentacoes(), -1, duration));
    }
}
