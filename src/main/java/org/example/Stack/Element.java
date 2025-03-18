package org.example.Stack;

public class Element<T> {
    private T data;
    private Element<T> next;

    public Element(T data) {
        this.data = data;
        this.next = null;
    }

    public void SetNext(Element<T> elem) {
        this.next = elem;
    }

    public Element<T> GetNext() {
        return this.next;
    }

    public T GetData() {
        return this.data;
    }
}