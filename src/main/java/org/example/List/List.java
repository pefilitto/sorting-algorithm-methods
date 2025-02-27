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
            System.out.print(aux.GetData() + " ");

            aux = aux.GetNext();
        }
        System.out.print(aux.GetData());
        System.out.println();
    }

    public void InsertionSort(){
        Node noPos, atual = first.GetNext();
        int elem;

        while (atual != null) {
            noPos = atual;
            elem = noPos.GetData();

            while(noPos != first && elem < noPos.GetPrev().GetData()) {
                noPos.SetData(noPos.GetPrev().GetData());
                noPos = noPos.GetPrev();
            }

            noPos.SetData(elem);
            atual = atual.GetNext();
        }
    }

    public void Selection_Sort() {
        Node current = first, smaller, auxCurrent;
        int auxInfo;

        while (current != null) {
            smaller = current;

            auxCurrent = current;
            while(auxCurrent != null) {
                if (auxCurrent.GetData() < smaller.GetData())
                    smaller = auxCurrent;
                auxCurrent = auxCurrent.GetNext();
            }

            auxInfo = current.GetData();
            current.SetData(smaller.GetData());
            smaller.SetData(auxInfo);
            current = current.GetNext();
        }
    }
}
