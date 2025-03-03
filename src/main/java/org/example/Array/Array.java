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
        int inicio = 0, elem, j;

        for (int i = 1; i < TL; i++) {
            elem = array[i];
            j = i;

            while(j > inicio && elem < array[j - 1]){
                array[j] = array[j - 1];
                j--;
            }

            array[j] = elem;
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

    public void HeapSort() {
        int childLeft, childRight, father, TL2 = TL, higherPos, aux;

        while (TL2 > 1) {
            father = TL2 / 2 - 1;

            while (father >= 0) {
                childLeft = father * 2 + 1;
                childRight = childLeft + 1;
                higherPos = childLeft;

                if (childRight < TL2 && array[childRight] > array[childLeft])
                    higherPos = childRight;

                if (array[higherPos] > array[father]) {
                    aux = array[higherPos];
                    array[higherPos] = array[father];
                    array[father] = aux;
                }

                father--;
            }

            aux = array[0];
            array[0] = array[TL2 - 1];
            array[TL2 - 1] = aux;
            TL2--;
        }
    }

}
