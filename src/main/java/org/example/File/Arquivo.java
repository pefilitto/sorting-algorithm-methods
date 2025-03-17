package org.example.File;

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
//.............................................................................
    /*

    insira aqui os m�todos de Ordena��o;

    */

    public void Insertion_Sort() {
        Registro regAnt = new Registro();
        Registro regAux = new Registro();

        int i = 1, pos;
        int tam = filesize();

        while(i < tam) {
            pos = i;
            seekArq(pos - 1);
            regAnt.leDoArq(arquivo);
            regAux.leDoArq(arquivo);

            while(pos > 0 && regAux.getNumero() < regAnt.getNumero()) {
                seekArq(pos);
                regAnt.gravaNoArq(arquivo);
                pos--;
                if (pos > 0) {
                    seekArq(pos - 1);
                    regAnt.leDoArq(arquivo);
                }
            }

            seekArq(pos);
            regAux.gravaNoArq(arquivo);
            i++;
        }
    }

    public void Selection_Sort() {
        Registro atual = new Registro();
        Registro menor = new Registro();
        Registro aux = new Registro();
        int i = 0, tam = filesize(), posMenor;

        while(i < tam) {
            seekArq(i);
            atual.leDoArq(arquivo);
            seekArq(i);
            menor.leDoArq(arquivo);
            posMenor = i;

            for (int j = i+1; j < tam; j++) {

                aux.leDoArq(arquivo);

                if (aux.getNumero() < menor.getNumero()) {
                    posMenor = j;
                    seekArq(j);
                    menor.leDoArq(arquivo);
                }
            }

            if (menor != atual) {
                seekArq(i);
                menor.gravaNoArq(arquivo);
                seekArq(posMenor);
                atual.gravaNoArq(arquivo);
            }

            i++;
        }
    }

    public void Bubble_Sort() {
        Registro actual = new Registro();
        Registro next =  new Registro();
        int fim = filesize();
        boolean flag = true;

        while (fim > 0 && flag) {
            flag = false;

            for (int i = 0; i < fim - 1; i++) {
                seekArq(i);
                actual.leDoArq(arquivo);
                next.leDoArq(arquivo);

                if (actual.getNumero() > next.getNumero()) {
                    seekArq(i);
                    next.gravaNoArq(arquivo);
                    actual.gravaNoArq(arquivo);
                    flag = true;
                }
            }

            fim -= 1;
        }
    }
}