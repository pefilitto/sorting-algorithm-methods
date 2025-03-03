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

    private Node GetMax() {
        if (first == null) return null;

        Node maxNode = first;
        Node aux = first;

        while (aux != null) {
            if (aux.GetData() > maxNode.GetData()) {
                maxNode = aux;
            }
            aux = aux.GetNext();
        }
        return maxNode;
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

    public void CountingSortToRadix(int exp) {
        int size = SizeList();

        int[] countingArray = new int[10];
        int[] outputArray = new int[size];

        Node aux = first;
        while (aux != null) {
            int digit = (aux.GetData() / exp) % 10;
            countingArray[digit]++;
            aux = aux.GetNext();
        }

        for (int i = 1; i < 10; i++) {
            countingArray[i] += countingArray[i - 1];
        }

        aux = last;
        while (aux != null) {
            int digit = (aux.GetData() / exp) % 10;
            outputArray[countingArray[digit] - 1] = aux.GetData();
            countingArray[digit]--;
            aux = aux.GetPrev();
        }

        aux = first;
        for (int i = 0; i < size && aux != null; i++, aux = aux.GetNext()) {
            aux.SetData(outputArray[i]);
        }
    }

    public void Radix_Sort() {
        Node maxNode = GetMax();

        for (int exp = 1; maxNode.GetData() / exp > 0; exp *= 10) {
            CountingSortToRadix(exp);
        }
    }
}
