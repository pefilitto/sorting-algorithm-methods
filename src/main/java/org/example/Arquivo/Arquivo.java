package org.example.Arquivo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class Arquivo {

    private String nomearquivo;
    private RandomAccessFile arquivo;

    private int comparacoes = 0;
    private int movimentacoes = 0;

    public Arquivo(String nomearquivo) {
        try {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException e) {
        }
    }

    public int getComparacoes() {
        return comparacoes;
    }

    public void setComparacoes(int comparacoes) {
        this.comparacoes = comparacoes;
    }

    public int getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(int movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public RandomAccessFile getFile() {
        return arquivo;
    }

    public void truncate(long pos) {
        try {
            arquivo.setLength(pos * Registro.length());
        } catch (IOException exc) {
        }
    }

    public boolean eof() {
        boolean retorno = false;
        try {
            if (arquivo.getFilePointer() == arquivo.length())
                retorno = true;
        } catch (IOException e) {
        }
        return (retorno);
    }

    //insere um Registro no final do arquivo, passado por par�metro
    public void inserirRegNoFinal(Registro reg) {
        seekArq(filesize());//ultimo byte
        reg.gravaNoArq(arquivo);
    }

    public int filesize() {
        try {
            return (int) arquivo.length() / Registro.length();
        } catch (IOException e) {
            return 0;
        }
    }

    public void exibirArq() {
        Registro aux = new Registro();
        seekArq(0);
        int i = 0;
        while (!this.eof()) {
            //System.out.println("Posicao " + i);
            aux.leDoArq(arquivo);
            aux.exibirReg();
            i++;
        }
        System.out.println();
    }

    public void exibirUmRegistro(int pos) {
        Registro aux = new Registro();
        seekArq(pos);
        //System.out.println("Posicao " + pos);
        aux.leDoArq(arquivo);
        aux.exibirReg();
    }

    public void seekArq(int pos) {
        try {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e) {
        }
    }

    public void geraArquivoOrdenado(int tam) {
        Registro reg;
        for (int i = 1; i <= tam; i++) {
            reg = new Registro(i);
            reg.gravaNoArq(arquivo);
        }
    }

    public void geraArquivoDesordenado(int tam) {
        Registro reg;
        Random random = new Random();
        for (int i = 0; i < tam; i++) {
            reg = new Registro(random.nextInt(1000) + 1);
            reg.gravaNoArq(arquivo);
        }
    }

    public void geraArquivoReverso(int tam) {
        Registro reg;
        for (int i = tam; i > 0; i--) {
            reg = new Registro(i);
            reg.gravaNoArq(arquivo);
        }
    }


    public void copiaArq(RandomAccessFile arquivoOrigem) {
        try {
            Registro reg = new Registro();
            int i = 0, tam = (int) arquivoOrigem.length() / Registro.length();
            this.arquivo = new RandomAccessFile("temp.dat", "rw");
            truncate(0);
            arquivoOrigem.seek(0);
            while (i < tam) {
                reg.leDoArq(arquivoOrigem);
                reg.gravaNoArq(arquivo);
                i++;
            }
        } catch (IOException e) {

        }
    }

    private Registro GetMaxRegister() {
        int maior = Integer.MIN_VALUE;
        Registro auxMaior = new Registro();
        Registro aux = new Registro();

        for (int i = 0; i < filesize(); i++) {
            seekArq(i);
            aux.leDoArq(arquivo);
            if (aux.getNumero() > maior) {
                maior = aux.getNumero();
                auxMaior.setNumero(aux.getNumero());
            }
        }
        return auxMaior;
    }

    private Registro GetMinRegister() {
        int menor = Integer.MAX_VALUE;
        Registro auxMenor = new Registro();
        Registro aux = new Registro();

        for (int i = 0; i < filesize(); i++) {
            seekArq(i);
            aux.leDoArq(arquivo);
            if (aux.getNumero() < menor) {
                menor = aux.getNumero();
                auxMenor.setNumero(aux.getNumero());
            }
        }
        return auxMenor;
    }
    
    public int BinarySearch(int info, int end){
        Registro reg = new Registro();
        int start = 0, half = end / 2;
        seekArq(half);
        reg.leDoArq(arquivo);
        comparacoes++;
        while (start < end && reg.getNumero() != info) {
            comparacoes++;
            if (reg.getNumero() < info) {
                start = half + 1;
            } else {
                end = half - 1;
            }
            half = (start + end) / 2;
            seekArq(half);
            reg.leDoArq(arquivo);
            comparacoes++;
        }
        comparacoes++;
        if (info > reg.getNumero()) {
            return half + 1;
        }
        return half;
    }

    public void BinaryInsertionSort() {
        Registro reg = new Registro(), aux = new Registro();
        int i = 1, tam = filesize(), j, pos;
        while (i < tam) {
            seekArq(i);
            aux.leDoArq(arquivo);
            pos = BinarySearch(aux.getNumero(), i - 1);
            j = i;
            while (j > pos) {
                seekArq(j - 1);
                reg.leDoArq(arquivo);
                reg.gravaNoArq(arquivo);
                j--;
                movimentacoes++;
            }
            seekArq(j);
            movimentacoes++;
            aux.gravaNoArq(arquivo);
            i++;
        }
    }

    public void InsertionSort(){
        Registro actual = new Registro();
        Registro prev = new Registro();
        int i = 1, pos;

        while(i < filesize()){
            pos = i;
            seekArq(pos - 1);
            prev.leDoArq(arquivo);
            actual.leDoArq(arquivo);

            comparacoes++;
            while(pos > 0 && actual.getNumero() < prev.getNumero()) {
                seekArq(pos);
                prev.gravaNoArq(arquivo);
                movimentacoes++;
                pos--;

                if (pos > 0) {
                    seekArq(pos - 1);
                    prev.leDoArq(arquivo);
                }
            }
            seekArq(pos);
            actual.gravaNoArq(arquivo);
            movimentacoes++;
            i++;
        }
    }

    public void SelectionSort() {
        int minimumPos = 0, i = 0, j;
        Registro minimumPosRegister = new Registro(), actualJ = new Registro(), actualI =  new Registro();

        while(i < filesize()){
            j = i + 1;
            seekArq(i);
            actualI.leDoArq(arquivo);
            seekArq(i);
            minimumPosRegister.leDoArq(arquivo);
            minimumPos = i;

            while(j < filesize()){
                seekArq(j);
                actualJ.leDoArq(arquivo);

                comparacoes++;
                if(actualJ.getNumero() < minimumPosRegister.getNumero()){
                    seekArq(j);
                    minimumPosRegister.leDoArq(arquivo);
                    minimumPos = j;
                }
                j++;
            }

            movimentacoes++;
            seekArq(i);
            minimumPosRegister.gravaNoArq(arquivo);
            seekArq(minimumPos);
            actualI.gravaNoArq(arquivo);
            movimentacoes++;
            i++;
        }
    }

    public void CountingSort() {
        int max = 0;
        int i = 0;
        int size = filesize() - 1;

        int[] outputArray = new int[size];

        seekArq(0);
        Registro aux = new Registro();
        aux.leDoArq(arquivo);
        while (!eof()) {
            comparacoes++;
            if (aux.getNumero() > max)
                max = aux.getNumero();
            aux.leDoArq(arquivo);
        }

        int[] countingArray = new int[max + 1];

        seekArq(0);
        for (i = 0; i < size; i++) {
            aux.leDoArq(arquivo);
            countingArray[aux.getNumero()]++;
        }

        for (i = 1; i <= max; i++) {
            countingArray[i] += countingArray[i - 1];
        }

        seekArq(size - 1);
        for (i = size - 1; i >= 0; i--) {
            aux.leDoArq(arquivo);
            outputArray[countingArray[aux.getNumero()] - 1] = aux.getNumero();
            countingArray[aux.getNumero()]--;
            seekArq(i - 1);
        }

        seekArq(0);
        for (i = 0; i < size; i++) {
            aux.setNumero(outputArray[i]);
            aux.gravaNoArq(arquivo);
            movimentacoes++;
        }
    }

    public void BubbleSort(){
        int i = 0, filesize = filesize();
        boolean changed = true;
        Registro actual = new Registro();
        Registro next = new Registro();

        while(filesize > 1 && changed){
            changed = false;
            for (i = 0; i < filesize - 1; i++) {
                seekArq(i);
                actual.leDoArq(arquivo);
                next.leDoArq(arquivo);

                comparacoes++;
                if(actual.getNumero() > next.getNumero()){
                    seekArq(i);
                    next.gravaNoArq(arquivo);
                    actual.gravaNoArq(arquivo);
                    changed = true;
                    movimentacoes++;
                    movimentacoes++;
                }
            }
            filesize--;
        }
    }

    public void HeapSort(){
        int FD, FE, posmaior, pai, TL = filesize();
        Registro auxPai = new Registro(), auxFD = new Registro(), auxFE = new Registro(), auxPosmaior = new Registro(), auxFim = new Registro(), auxInicio = new Registro();

        while(TL > 1){
            pai = TL / 2 - 1;

            while(pai >= 0){
                FE = pai * 2 + 1;
                FD = FE + 1;
                posmaior = FE;

                if(FD < TL){
                    seekArq(FD);
                    auxFD.leDoArq(arquivo);
                    seekArq(FE);
                    auxFE.leDoArq(arquivo);

                    comparacoes++;
                    if(auxFD.getNumero() > auxFE.getNumero())
                        posmaior = FD;
                }

                seekArq(posmaior);
                auxPosmaior.leDoArq(arquivo);
                seekArq(pai);
                auxPai.leDoArq(arquivo);

                comparacoes++;
                if(auxPosmaior.getNumero() > auxPai.getNumero()){
                    seekArq(pai);
                    auxPosmaior.gravaNoArq(arquivo);
                    seekArq(posmaior);
                    auxPai.gravaNoArq(arquivo);
                    movimentacoes++;
                    movimentacoes++;
                }
                pai--;
            }

            movimentacoes++;
            seekArq(0);
            auxInicio.leDoArq(arquivo);
            seekArq(TL - 1);
            auxFim.leDoArq(arquivo);

            seekArq(0);
            auxFim.gravaNoArq(arquivo);
            seekArq(TL - 1);
            auxInicio.gravaNoArq(arquivo);
            movimentacoes++;
            movimentacoes++;

            TL--;
        }
    }


    public void ShakeSort(){
        boolean changed = true;
        int filesize = filesize(), start = 0;
        Registro actual = new Registro();
        Registro next = new Registro();
        Registro prev = new Registro();
        while(start != filesize && changed){
            changed = false;

            for (int i = 0; i < filesize - 1; i++) {
                seekArq(i);
                actual.leDoArq(arquivo);
                next.leDoArq(arquivo);

                comparacoes++;
                if(actual.getNumero() > next.getNumero()){
                    seekArq(i);
                    next.gravaNoArq(arquivo);
                    actual.gravaNoArq(arquivo);
                    changed = true;
                    movimentacoes++;
                    movimentacoes++;
                }
            }

            filesize--;

            if(changed){
                changed = false;
                for (int i = filesize; i > start; i--) {
                    seekArq(i);
                    actual.leDoArq(arquivo);
                    seekArq(i - 1);
                    prev.leDoArq(arquivo);

                    comparacoes++;
                    if(actual.getNumero() < prev.getNumero()){
                        seekArq(i);
                        prev.gravaNoArq(arquivo);
                        seekArq(i - 1);
                        actual.gravaNoArq(arquivo);
                        changed = true;
                        movimentacoes++;
                        movimentacoes++;
                    }
                }
                start++;
            }
        }
    }

    public void GnomeSort(){
        int i = 0, filesize = filesize();
        Registro actual = new Registro();
        Registro prev = new Registro();
        while(i < filesize) {
            if(i == 0){
                i++;
            }
            else{
                seekArq(i);
                actual.leDoArq(arquivo);
                seekArq(i - 1);
                prev.leDoArq(arquivo);

                comparacoes++;
                if(actual.getNumero() < prev.getNumero()){
                    seekArq(i);
                    prev.gravaNoArq(arquivo);
                    seekArq(i - 1);
                    actual.gravaNoArq(arquivo);
                    movimentacoes++;
                    movimentacoes++;
                    i--;
                }
                else{
                    i++;
                }
            }
        }
    }

    public void CountingSortToRadix(int exp){
        int filesize = filesize();
        Registro aux = new Registro();
        int[] outputArray = new int[filesize];
        int[] countingArray = new int[10];

        for (int i = 0; i < filesize; i++) {
            seekArq(i);
            aux.leDoArq(arquivo);
            int digit = (aux.getNumero() / exp) % 10;
            countingArray[digit]++;
        }

        for (int i = 1; i < 10; i++) {
            countingArray[i] += countingArray[i-1];
        }

        for (int i = filesize - 1; i >= 0; i--) {
            seekArq(i);
            aux.leDoArq(arquivo);
            int digit = (aux.getNumero() / exp) % 10;
            outputArray[countingArray[digit] - 1] = aux.getNumero();
            countingArray[digit]--;
        }

        for (int i = 0; i < filesize; i++) {
            aux.setNumero(outputArray[i]);
            seekArq(i);
            aux.gravaNoArq(arquivo);
            movimentacoes++;
        }
    }

    public void RadixSort(){
        Registro maxRegister = GetMaxRegister();

        for (int i = 1; maxRegister.getNumero() / i > 0; i *= 10) {
            CountingSortToRadix(i);
        }
    }

    public void BucketSort() {
        Arquivo[] buckets = new Arquivo[filesize()];
        Registro aux = new Registro();
        Registro max = GetMaxRegister();
        Registro min = GetMinRegister();
        int range = max.getNumero() - min.getNumero();

        for (int i = 0; i < filesize(); i++) {
            buckets[i] = new Arquivo(i + ".dat");
            buckets[i].truncate(0);
        }

        for (int i = 0; i < filesize(); i++) {
            seekArq(i);
            aux.leDoArq(arquivo);
            int position = (aux.getNumero() - min.getNumero()) * (filesize() - 1) / range;
            buckets[position].inserirRegNoFinal(aux);
        }

        for (int i = 0; i < filesize(); i++) {
            buckets[i].SelectionSort();
        }

        Registro auxBucket = new Registro();
        seekArq(0);
        for (int i = 0; i < filesize(); i++) {
            buckets[i].seekArq(0);
            while (!buckets[i].eof()) {
                auxBucket.leDoArq(buckets[i].getFile());
                auxBucket.gravaNoArq(arquivo);
                movimentacoes++;
            }
        }
    }

    public void ShellSort(){
        int size = filesize(), dist = 1;
        Registro aux = new Registro(), regDist = new Registro();

        while(dist < size){
            dist = dist * 2 + 1;
        }
        dist = dist / 2;

        while(dist > 0){
            for (int i = dist; i < size; i++) {
                int pos = i;
                seekArq(pos);
                aux.leDoArq(arquivo);

                seekArq(pos - dist);
                regDist.leDoArq(arquivo);


                comparacoes++;
                while(pos - dist >= 0 && regDist.getNumero() > aux.getNumero()){
                    seekArq(pos);
                    regDist.gravaNoArq(arquivo);
                    movimentacoes++;

                    pos -= dist;

                    seekArq(pos - dist);
                    regDist.leDoArq(arquivo);
                }
                seekArq(pos);
                aux.gravaNoArq(arquivo);
                movimentacoes++;
            }
            dist = dist / 2;
        }
    }

    public void MergeSortImplem1(){
        int seq = 1;
        while(seq < filesize()){
            Arquivo file1 = new Arquivo("file1Merge.dat");
            Arquivo file2 = new Arquivo("file2Merge.dat");

            Partition(file1, file2);
            Fusion(file1, file2, seq);

            seq *= 2;
        }

    }

    public void Partition(Arquivo file1, Arquivo file2){
        int middle = filesize() / 2;
        Registro aux = new Registro();
        for (int i = 0; i < middle; i++) {
            seekArq(i);
            aux.leDoArq(arquivo);

            file1.seekArq(i);
            aux.gravaNoArq(file1.arquivo);
            movimentacoes++;

            seekArq(i + middle);
            aux.leDoArq(arquivo);

            file2.seekArq(i);
            aux.gravaNoArq(file2.arquivo);
            movimentacoes++;
        }
    }

    public void Fusion(Arquivo file1, Arquivo file2, int sequence){
        int k = 0, i = 0, j = 0, auxSeq = sequence;
        Registro auxFile1 = new Registro(), auxFile2 = new Registro();
        while(k < filesize() - 1){
            while(i < sequence && j < sequence){
                file1.seekArq(i);
                auxFile1.leDoArq(file1.arquivo);
                file2.seekArq(j);
                auxFile2.leDoArq(file2.arquivo);

                seekArq(k);
                comparacoes++;
                if(auxFile1.getNumero() < auxFile2.getNumero()){
                    auxFile1.gravaNoArq(arquivo);
                    movimentacoes++;
                    i++;
                }
                else{
                    auxFile2.gravaNoArq(arquivo);
                    movimentacoes++;
                    j++;
                }
                k++;
            }

            while(i < sequence){
                file1.seekArq(i);
                auxFile1.leDoArq(file1.arquivo);

                seekArq(k);
                auxFile1.gravaNoArq(arquivo);
                movimentacoes++;
                i++;
                k++;
            }

            while(j < sequence){
                file2.seekArq(j);
                auxFile2.leDoArq(file2.arquivo);

                seekArq(k);
                auxFile2.gravaNoArq(arquivo);
                movimentacoes++;
                k++;
                j++;
            }

            sequence += auxSeq;
        }
    }

    public void MergeSortImplem2() {
        Merge2(0, filesize() - 1);
    }

    private void Merge2(int esq, int dir) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            Merge2(esq, meio);
            Merge2(meio + 1, dir);
            Fusion2(esq, meio, meio+1, dir);
        }
    }

    private void Fusion2(int ini1, int fim1, int ini2, int fim2) {
        Arquivo file = new Arquivo("arquivo.dat");
        int k = 0, i = ini1, j = ini2;
        Registro regI = new Registro(), regJ = new Registro();

        while (i <= fim1 && j <= fim2) {
            seekArq(i);
            regI.leDoArq(arquivo);

            seekArq(j);
            regJ.leDoArq(arquivo);

            file.seekArq(k++);
            comparacoes++;
            if (regI.getNumero() < regJ.getNumero()) {
                regI.gravaNoArq(file.arquivo);
                movimentacoes++;
                i++;
            } else {
                regJ.gravaNoArq(file.arquivo);
                movimentacoes++;
                j++;
            }
        }

        while(i <= fim1) {
            seekArq(i++);
            regI.leDoArq(arquivo);

            file.seekArq(k++);
            regI.gravaNoArq(file.arquivo);
            movimentacoes++;
        }

        while(j <= fim2) {
            seekArq(j++);
            regJ.leDoArq(arquivo);

            file.seekArq(k++);
            regJ.gravaNoArq(file.arquivo);
            movimentacoes++;
        }

        for (int l = 0; l < k; l++) {
            file.seekArq(l);
            regI.leDoArq(file.arquivo);

            seekArq(ini1 + l);
            regI.gravaNoArq(arquivo);
            movimentacoes++;
        }
    }

    private int getNextGap(int gap) {
        gap = gap * 10 / 13;
        if (gap < 1)
            return 1;
        return  gap;
    }

    public void CombSort() {
        Registro regI = new Registro();
        Registro regGap = new Registro();
        int gap = filesize();
        boolean trocou = true;


        while(gap != 1 || trocou) {
            trocou = false;
            gap = getNextGap(gap);

            for (int i = 0; i < filesize() - gap; i++) {
                seekArq(i);
                regI.leDoArq(arquivo);

                seekArq(i + gap);
                regGap.leDoArq(arquivo);

                comparacoes++;
                if (regGap.getNumero() < regI.getNumero()) {
                    seekArq(i);
                    regGap.gravaNoArq(arquivo);

                    seekArq(i + gap);
                    regI.gravaNoArq(arquivo);
                    trocou = true;
                    movimentacoes++;
                    movimentacoes++;
                }
            }
        }
    }

    public void QuickSP(){
        QuickSemPivo(0, filesize() - 1);
    }
    
    public void QuickSemPivo(int start, int end){
        Registro regI = new Registro(), regJ = new Registro();
        int i = start, j = end;

        while (i < j) {

            seekArq(i);
            regI.leDoArq(arquivo);
            seekArq(j);
            regJ.leDoArq(arquivo);

            comparacoes++;
            while (i < j && regI.getNumero() <= regJ.getNumero()) {
                i++;
                seekArq(i);
                regI.leDoArq(arquivo);
                comparacoes++;
            }

            comparacoes++;
            if (regI.getNumero() != regJ.getNumero()) {
                seekArq(i);
                regI.leDoArq(arquivo);
                seekArq(j);
                regJ.leDoArq(arquivo);
                seekArq(j);
                regI.gravaNoArq(arquivo);
                seekArq(i);
                regJ.gravaNoArq(arquivo);
                movimentacoes += 2;
            }

            seekArq(i);
            regI.leDoArq(arquivo);
            seekArq(j);
            regJ.leDoArq(arquivo);

            comparacoes++;
            while (i < j && regI.getNumero() <= regJ.getNumero()) {
                j--;
                seekArq(j);
                regJ.leDoArq(arquivo);
                comparacoes++;
            }

            comparacoes++;
            if (regI.getNumero() != regJ.getNumero()) {
                seekArq(i);
                regI.leDoArq(arquivo);
                seekArq(j);
                regJ.leDoArq(arquivo);
                seekArq(j);
                regI.gravaNoArq(arquivo);
                seekArq(i);
                regJ.gravaNoArq(arquivo);
                movimentacoes += 2;
            }
        }

        if (start < i - 1) {
            QuickSemPivo(start, i - 1);
        }
        if (j + 1 < end) {
            QuickSemPivo(j + 1, end);
        }
    }

    public void QuickCP(){
        QuickComPivo(0, filesize() - 1);
    }

    public void QuickComPivo(int start, int end){
        int i = start, j = end;
        Registro regI = new Registro(), regJ = new Registro(), regPivot = new Registro();

        seekArq((i + j) / 2);
        regPivot.leDoArq(arquivo);

        while (i < j) {
            seekArq(i);
            regI.leDoArq(arquivo);
            comparacoes++;
            while (regI.getNumero() < regPivot.getNumero()) {
                i++;
                regI.leDoArq(arquivo);
                comparacoes++;
            }

            seekArq(j);
            regJ.leDoArq(arquivo);
            comparacoes++;
            while (regJ.getNumero() > regPivot.getNumero()) {
                j--;
                seekArq(j);
                regJ.leDoArq(arquivo);
                comparacoes++;
            }

            if (i <= j) {
                seekArq(i);
                regJ.gravaNoArq(arquivo);
                seekArq(j);
                regI.gravaNoArq(arquivo);
                i++;
                j--;
                movimentacoes += 2;
            }
        }

        if (start < i) {
            QuickComPivo(start, j);
        }

        if (j < end) {
            QuickComPivo(i, end);
        }
    }

    public void TimInsertionSort(int start, int end){
        Registro reg = new Registro(), aux = new Registro();
        int i = start + 1, j;
        while (i <= end) {
            j = i;
            seekArq(j);
            reg.leDoArq(arquivo);
            seekArq(j - 1);
            aux.leDoArq(arquivo);
            comparacoes++;
            while (j > 0 && reg.getNumero() < aux.getNumero()) {
                seekArq(j);
                aux.gravaNoArq(arquivo);
                movimentacoes++;
                j--;
                if (j > 0) {
                    seekArq(j - 1);
                    aux.leDoArq(arquivo);
                    movimentacoes++;
                }
                comparacoes++;
            }
            seekArq(j);
            reg.gravaNoArq(arquivo);
            movimentacoes++;
            i++;
        }
    }

    public void TimSort(){
        int length = filesize(), run = 32;
        for (int i = 0; i < length; i += run) {
            TimInsertionSort(i, Math.min(i + run - 1, length - 1));
        }

        for (int size = run; size < length; size = 2 * size) {
            for (int left = 0; left < length; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (length - 1));
                Fusion2(left, mid, mid + 1, right);
            }
        }
    }
}