package org.example.Stack;

public class Stack<T> {
    private Element<T> first;

    public Stack() {
        this.first = null;
    }

    public void Push(T elem) {
        Element<T> newElement = new Element<>(elem);
        if (first == null) {
            first = newElement;
        } else {
            newElement.SetNext(first);
            first = newElement;
        }
    }

    public T Pop() {
        T data = first.GetData();
        first = first.GetNext();
        return data;
    }

    public boolean IsEmpty() {
        return first == null;
    }

    public T Top() {
        return first.GetData();
    }
}




