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
}
