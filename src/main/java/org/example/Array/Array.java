package org.example.Array;

public class Array {
    private int[] array;
    private int TL = 0;
    private int TF;

    public Array(int TF){
        this.TF = TF;
        this.array = new int[TF];
    }

    public void AddElement(int element){
        array[TL] = element;
        TL++;
    }

    public void PrintArray(){
        for (int i = 0; i < TL; i++) {
            System.out.println(array[i]);
        }
    }

    public void InsertionSort(){
       int elem, pos;

       for (int i = 1; i < TL; i++) {
           pos = i;
           elem = array[pos];

           while(pos > 0 && elem < array[pos - 1]) {
               array[pos] = array[pos - 1];
               pos--;
           }

           array[pos] = elem;
       }
    }

    public void Selection_Sort() {
        int posMenor, aux;

        for (int i = 0; i < TL; i++) {
            posMenor = i;

            for (int j = i; j < TL; j++) {
                if (array[j] < array[posMenor])
                    posMenor = j;
            }

            aux = array[posMenor];
            array[posMenor] = array[i];
            array[i] = aux;
        }
    }

    public void CountingSort() {
        int[] countingArray, outputArray;
        int maior = 0, cont;

        for(int i = 0; i < TL; i++){
            if(array[i] > maior)
                maior = array[i];
        }

        countingArray = new int[maior + 1];

        for(int i = 0; i < TL; i++){
            cont = 0;
            for (int j = 0; j < TL; j++) {
                if(array[i] == array[j])
                    cont++;
            }
            countingArray[array[i]] = cont;
        }

        for(int i = 1; i <= maior; i++){
            countingArray[i] = countingArray[i] + countingArray[i - 1];
        }

        outputArray = new int[TL];

        for(int i = TL - 1; i >= 0; i--){
            outputArray[countingArray[array[i]] - 1] = array[i];
            countingArray[array[i]]--;
        }

        array = outputArray;
    }



}
