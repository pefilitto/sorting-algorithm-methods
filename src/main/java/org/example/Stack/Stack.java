package org.example.Stack;

public class Stack {
    private Element first;

    public Stack() {
        this.first = null;
    }

    public void Push(int elem) {
        Element newElement = new Element(elem);
        if (first == null) {
            first = newElement;
        } else {
            newElement.SetNext(first);
            first = newElement;
        }
    }

    public int Pop() {
        int data = first.GetData();
        first = first.GetNext();
        return data;
    }

    public boolean IsEmpty() {
        return first == null;
    }
}



