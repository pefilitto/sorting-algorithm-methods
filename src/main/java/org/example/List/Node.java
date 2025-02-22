package org.example.List;

public class Node {
    private int data;
    private Node next;
    private Node prev;

    public Node(int data){
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public Node GetNext(){
        return this.next;
    }

    public void SetNext(Node next){
        this.next = next;
    }

    public int GetData(){
        return this.data;
    }

    public void SetData(int data){
        this.data = data;
    }

    public Node GetPrev() {
        return prev;
    }

    public void SetPrev(Node prev) {
        this.prev = prev;
    }
}
