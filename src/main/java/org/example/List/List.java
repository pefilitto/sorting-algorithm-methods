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

    private Node NodeByPos(int position){
        Node node = first;
        int cont = 0;
        while(node != null && cont < position){
            node = node.GetNext();
            cont++;
        }
        return node;
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

    public void BubbleSort() {
        int aux;
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Node j = first; j.GetNext() != null; j = j.GetNext()) {
                if (j.GetData() > j.GetNext().GetData()) {
                    aux = j.GetData();
                    j.SetData(j.GetNext().GetData());
                    j.GetNext().SetData(aux);
                    changed = true;
                }
            }
        }
    }

    public void HeapSort() {
        int TL = SizeList(), childLeft, childRight, higherPos, father;

        while (TL > 1) {
            father = TL / 2 - 1;

            while (father >= 0) {
                childLeft = father * 2 + 1;
                childRight = childLeft + 1;
                higherPos = childLeft;

                if (childRight < TL && NodeByPos(childRight).GetData() > NodeByPos(higherPos).GetData())
                    higherPos = childRight;

                if (NodeByPos(higherPos).GetData() > NodeByPos(father).GetData()) {
                    int aux = NodeByPos(higherPos).GetData();
                    NodeByPos(higherPos).SetData(NodeByPos(father).GetData());
                    NodeByPos(father).SetData(aux);
                }

                father--;
            }

            int aux = NodeByPos(0).GetData();
            NodeByPos(0).SetData(NodeByPos(TL - 1).GetData());
            NodeByPos(TL - 1).SetData(aux);

            TL--;
        }
    }

}
