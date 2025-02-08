package org.example.Array;

public class Array {
    private int[] array;
    private int TL = 0;

    public Array(){
        this.array = new int[TL];
    }

    public void AddElement(int element){
        this.array[TL] = element;
        this.TL++;
    }
}
