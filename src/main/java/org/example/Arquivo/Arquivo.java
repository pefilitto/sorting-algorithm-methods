package org.example.Arquivo;

import java.awt.image.renderable.RenderableImage;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        int i;
        Registro aux = new Registro();
        seekArq(0);
        i = 0;
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

    private Registro GetMaxRegister(){
        int maior = 0;
        Registro auxMaior = new Registro();
        Registro aux = new Registro();
        for (int i = 0; i < filesize(); i++) {
            seekArq(i);
            aux.leDoArq(arquivo);
            if(aux.getNumero()  > maior){
                maior = aux.getNumero();
                auxMaior = aux;
            }
        }
        return auxMaior;
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

            while(pos > 0 && actual.getNumero() < prev.getNumero()) {
                seekArq(pos);
                prev.gravaNoArq(arquivo);
                pos--;

                if (pos > 0) {
                    seekArq(pos - 1);
                    prev.leDoArq(arquivo);
                }
            }
            seekArq(pos);
            actual.gravaNoArq(arquivo);
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

                if(actualJ.getNumero() < minimumPosRegister.getNumero()){
                    seekArq(j);
                    minimumPosRegister.leDoArq(arquivo);
                    minimumPos = j;
                }
                j++;
            }

            seekArq(i);
            minimumPosRegister.gravaNoArq(arquivo);
            seekArq(minimumPos);
            actualI.gravaNoArq(arquivo);
            i++;
        }
    }

    public void CountingSort() {
        int max = 0;
        int i = 0;
        int size = filesize();

        int[] outputArray = new int[size];

        seekArq(0);
        Registro aux = new Registro();
        aux.leDoArq(arquivo);
        while (!eof()) {
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
                if(actual.getNumero() > next.getNumero()){
                    seekArq(i);
                    next.gravaNoArq(arquivo);
                    actual.gravaNoArq(arquivo);
                    changed = true;
                }
            }
            filesize--;
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
                if(actual.getNumero() > next.getNumero()){
                    seekArq(i);
                    next.gravaNoArq(arquivo);
                    actual.gravaNoArq(arquivo);
                    changed = true;
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
                    if(actual.getNumero() < prev.getNumero()){
                        seekArq(i);
                        prev.gravaNoArq(arquivo);
                        seekArq(i - 1);
                        actual.gravaNoArq(arquivo);
                        changed = true;
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

                if(actual.getNumero() < prev.getNumero()){
                    seekArq(i);
                    prev.gravaNoArq(arquivo);
                    seekArq(i - 1);
                    actual.gravaNoArq(arquivo);
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
        }
    }

    public void RadixSort(){
        Registro maxRegister = GetMaxRegister();

        for (int i = 1; maxRegister.getNumero() / i > 0; i *= 10) {
            CountingSortToRadix(i);
        }
    }
}