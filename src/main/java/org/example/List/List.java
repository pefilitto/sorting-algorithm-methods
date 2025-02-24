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

    private int SizeList(){
        int cont = 0;
        Node aux = first;
        while(aux != null){
            cont++;

            aux = aux.GetNext();
        }

        return cont;
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
            last = newElement;
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

    public void CountingSort(){
        int higher = 0, cont = 0;
        Node aux = first;

        while(aux != null){
            if(aux.GetData() > higher)
                higher = aux.GetData();

            aux = aux.GetNext();
        }

        int[] countingArray = new int[higher + 1];
        int[] outputArray = new int[SizeList()];

        aux = first;
        while(aux != null){
            countingArray[aux.GetData()]++;

            aux = aux.GetNext();
        }

        for (int i = 1; i < higher + 1; i++) {
            countingArray[i] += countingArray[i - 1];
        }

        for (aux = last; aux != null; aux = aux.GetPrev()) {
            outputArray[countingArray[aux.GetData()] - 1] = aux.GetData();
            countingArray[aux.GetData()]--;
        }

        aux = first;
        for (int i = 0; i < SizeList() && aux != null; i++, aux = aux.GetNext()) {
            aux.SetData(outputArray[i]);
        }
    }
}
