package org.example.Stack;

public class Element {
    private int data;
    private Element next;

    public Element(int data) {
        this.data = data;
        this.next = null;
    }

    public void SetNext(Element elem) {
        this.next = elem;
    }

    public Element GetNext() {
        return this.next;
    }

    public int GetData() {
        return this.data;
    }
}