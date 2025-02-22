package org.example.List;

public class List {
    private Node first;
    private Node last;

    public List(){
        this.first = null;
        this.last = null;
    }

    private boolean IsEmpty(){
        return this.first == null && this.last == null;
    }

    public void AddElement(int element){
        Node newElement = new Node(element);
        if(IsEmpty()){
            this.first = this.last = newElement;
        }
        else{
            Node aux = this.first;

            while(aux.GetNext() != null){
                aux = aux.GetNext();
            }

            aux.SetNext(newElement);
            newElement.SetPrev(aux);
        }
    }

    public void PrintList(){
        Node aux = this.first;
        while(aux.GetNext() != null){
            System.out.println(aux.GetData());

            aux = aux.GetNext();
        }
        System.out.println(aux.GetData());
    }

    public void InsertionSort(){
        Node node, aux;

        node = first.GetNext();
        while(node != null){
            aux = node;

            while(aux != first && node.GetData() < aux.GetPrev().GetData()){
                aux.SetData(aux.GetPrev().GetData());
                aux = aux.GetPrev();
            }

            aux.SetData(node.GetData());
            node = node.GetNext();
        }
    }
}
