package org.example.List;

import org.example.Stack.Stack;

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

    private Node GetMin(){
        if (first == null) return null;

        Node minNode = first;
        Node aux = first;

        while (aux != null) {
            if (aux.GetData() < minNode.GetData()) {
                minNode = aux;
            }
            aux = aux.GetNext();
        }
        return minNode;
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

    private int IndexByNode(Node node){
        int cont = 0;
        Node aux = first;
        while(aux != node){
            aux = aux.GetNext();
            cont++;
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
        System.out.print(aux.GetData() + " ");
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

    public void SelectionSort(){
        Node actual = first, minimum, aux;

        while(actual != null){
            minimum = actual;

            aux = minimum.GetNext();
            while(aux != null){
                if(aux.GetData() < minimum.GetData()){
                    minimum = aux;
                }
                aux = aux.GetNext();
            }

            int auxInfo = minimum.GetData();
            minimum.SetData(actual.GetData());
            actual.SetData(auxInfo);

            actual = actual.GetNext();
        }
    }

    public void ShakeSort(){
        boolean changed = true;
        Node aux = first, auxFirst = first, auxLast = last;
        while(auxFirst != auxLast && changed){
            changed = false;

            while(aux.GetNext() != null){
                if(aux.GetData() > aux.GetNext().GetData()){
                    int auxInfo = aux.GetData();
                    aux.SetData(aux.GetNext().GetData());
                    aux.GetNext().SetData(auxInfo);
                    changed = true;
                }
                aux = aux.GetNext();
            }

            auxLast = auxLast.GetPrev();

            if(changed){
                changed = false;
                aux = last;
                while(aux.GetPrev() != null){
                    if(aux.GetData() < aux.GetPrev().GetData()){
                        int auxInfo = aux.GetData();
                        aux.SetData(aux.GetPrev().GetData());
                        aux.GetPrev().SetData(auxInfo);
                        changed = true;
                    }
                    aux = aux.GetPrev();
                }
                auxFirst = auxFirst.GetNext();
            }
        }
    }

    public void CountingSort(){
        int higher = 0, cont = 0;
        int[] countingArray = new int[0], outputArray = new int[SizeList()];
        Node aux = first;

        Node max = GetMax();
        if(max != null){
            countingArray = new int[max.GetData() + 1];
        }

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
        Node auxLast = last;
        int aux;
        boolean changed = true;
        while (auxLast != first && changed) {
            changed = false;
            for (Node j = first; j != auxLast; j = j.GetNext()) {
                if (j.GetData() > j.GetNext().GetData()) {
                    aux = j.GetData();
                    j.SetData(j.GetNext().GetData());
                    j.GetNext().SetData(aux);
                    changed = true;
                }
            }
            auxLast = auxLast.GetPrev();
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

    public void RadixSort() {
        Node maxNode = GetMax();

        for (int exp = 1; maxNode.GetData() / exp > 0; exp *= 10) {
            CountingSortToRadix(exp);
        }
    }

    public void GnomeSort(){
        Node aux = first;

        while(aux != null){
            if(aux == first){
                aux = aux.GetNext();
            }
            else if(aux.GetData() < aux.GetPrev().GetData()){
                int auxData = aux.GetData();
                aux.SetData(aux.GetPrev().GetData());
                aux.GetPrev().SetData(auxData);
                aux = aux.GetPrev();
            }
            else aux = aux.GetNext();
        }
    }

    public void BucketSort(){
        int sizeList = SizeList();

        List[] buckets = new List[sizeList];

        for (int i = 0; i < sizeList; i++) {
            buckets[i] = new List();
        }

        int min = GetMin().GetData();
        int max = GetMax().GetData();
        int range = max - min;

        Node aux = first;
        while (aux != null) {
            int pos = (aux.GetData() - min) * (sizeList - 1) / range;
            buckets[pos].AddElement(aux.GetData());
            aux = aux.GetNext();
        }

        for (int i = 0; i < buckets.length; i++) {
            buckets[i].SelectionSort();
        }

        int index = 0;
        for (int i = 0; i < buckets.length; i++) {
            Node auxBucket = buckets[i].first;
            while (auxBucket != null) {
                NodeByPos(index).SetData(auxBucket.GetData());
                auxBucket = auxBucket.GetNext();
                index++;
            }
        }
    }

    public void ShellSort() {
        int dist = 1, size = SizeList(), i, data;

        while (dist < size) {
            dist = dist * 2 + 1;
        }
        dist = dist / 2;

        while (dist > 0) {
            for (i = dist; i < size; i++) {
                int index = i;

                data = NodeByPos(index).GetData();

                while (index - dist >= 0 && NodeByPos(index - dist).GetData() > data) {
                    NodeByPos(index).SetData(NodeByPos(index - dist).GetData());
                    index -= dist;
                }
                NodeByPos(index).SetData(data);
            }

            dist = dist / 2;
        }
    }

    public void QuickSemPivoI() {
        Node start, end;
        boolean flag = true;
        Stack<Node> startStack = new Stack<>();
        Stack<Node> endStack = new Stack<>();

        startStack.Push(first);
        endStack.Push(last);

        while (!startStack.IsEmpty() && !endStack.IsEmpty()) {
            Node nodeI = startStack.Pop();
            Node nodeJ = endStack.Pop();

            int i = IndexByNode(nodeI);
            int j = IndexByNode(nodeJ);

            start = nodeI;
            end = nodeJ;

            while (i < j) {
                if(flag){
                    while (i < j && nodeI.GetData() <= nodeJ.GetData()) {
                        i++;
                        nodeI = nodeI.GetNext();
                    }
                }
                else{
                    while (i < j && nodeJ.GetData() >= nodeI.GetData()) {
                        j--;
                        nodeJ = nodeJ.GetPrev();
                    }
                }

                if (i < j) {
                    int temp = nodeI.GetData();
                    nodeI.SetData(nodeJ.GetData());
                    nodeJ.SetData(temp);
                    flag = !flag;
                }
            }

            if (start != nodeI && nodeI.GetPrev() != null) {
                startStack.Push(start);
                endStack.Push(nodeI.GetPrev());
            }
            if (end != nodeJ && nodeJ.GetNext() != null) {
                startStack.Push(nodeJ.GetNext());
                endStack.Push(end);
            }
        }
    }

    public void QuickComPivoI() {
        Stack<Node> startStack = new Stack<>(), endStack = new Stack<>();

        startStack.Push(first);
        endStack.Push(last);

        while (!startStack.IsEmpty() && !endStack.IsEmpty()) {
            Node start = startStack.Pop();
            Node end = endStack.Pop();

            Node nodeI = start;
            Node nodeJ = end;

            int i = IndexByNode(nodeI);
            int j = IndexByNode(nodeJ);

            int pivotIndex = (i + j) / 2;

            Node nodePivot = NodeByPos(pivotIndex);

            while (i <= j) {
                while (nodeI.GetData() < nodePivot.GetData()) {
                    nodeI = nodeI.GetNext();
                    i++;
                }

                while (nodeJ.GetData() > nodePivot.GetData()) {
                    nodeJ = nodeJ.GetPrev();
                    j--;
                }

                if (i <= j) {
                    int aux = nodeI.GetData();
                    nodeI.SetData(nodeJ.GetData());
                    nodeJ.SetData(aux);

                    nodeI = nodeI.GetNext();
                    nodeJ = nodeJ.GetPrev();
                    i++;
                    j--;
                }
            }

            if (IndexByNode(start) < j) {
                startStack.Push(start);
                endStack.Push(NodeByPos(j));
            }

            if (i < IndexByNode(end)) {
                startStack.Push(NodeByPos(i));
                endStack.Push(end);
            }
        }
    }

    public void MergeSortImpl1(){
        int seq = 1;
        while(seq < SizeList()){
            List list1 = new List();
            List list2 = new List();

            Partition(list1, list2);
            Fusion(list1, list2, seq);
            seq = seq * 2;
        }
    }

    private void Partition(List list1, List list2){
        int middle = SizeList() / 2;
        for (int i = 0; i < middle; i++) {
            list1.AddElement(NodeByPos(i).GetData());
            list2.AddElement(NodeByPos(middle + i).GetData());
        }
    }

    private void Fusion(List list1, List list2, int sequence){
        int k = 0, i = 0, j = 0, auxSec = sequence;

        while(k < SizeList() - 1){
            while(i < sequence && j < sequence){
                if(list1.NodeByPos(i).GetData() < list2.NodeByPos(j).GetData()){
                    NodeByPos(k++).SetData(list1.NodeByPos(i++).GetData());
                }
                else{
                    NodeByPos(k++).SetData(list2.NodeByPos(j++).GetData());
                }
            }

            while(i < sequence)
                NodeByPos(k++).SetData(list1.NodeByPos(i++).GetData());

            while(j < sequence)
                NodeByPos(k++).SetData(list2.NodeByPos(j++).GetData());

            sequence = sequence + auxSec;
        }
    }

    public void MergeSortImpl2(){
        Node auxFirst = first;
        Node auxLast = last;

        Merge2(auxFirst, auxLast);
    }

    public void Merge2(Node start, Node end){
        if(start != end){
            int middle = (IndexByNode(start) + IndexByNode(end)) / 2;
            Merge2(start, NodeByPos(middle));
            Merge2(NodeByPos(middle + 1), end);
            Partition2(start, NodeByPos(middle), NodeByPos(middle + 1), end);
        }
    }

    public void Partition2(Node ini1, Node fim1, Node ini2, Node fim2){
        List aux = new List();
        Node auxIni1 = ini1, auxIni2 = ini2;

        while(auxIni1 != null && auxIni1 != fim1.GetNext() && auxIni2 != null && auxIni2 != fim2.GetNext()){
            if(auxIni1.GetData() < auxIni2.GetData()){
                aux.AddElement(auxIni1.GetData());
                auxIni1 = auxIni1.GetNext();
            }
            else {
                aux.AddElement(auxIni2.GetData());
                auxIni2 = auxIni2.GetNext();
            }
        }

        while(auxIni1 != null && auxIni1 != fim1.GetNext()){
            aux.AddElement(auxIni1.GetData());
            auxIni1 = auxIni1.GetNext();
        }

        while(auxIni2 != null && auxIni2 != fim2.GetNext()){
            aux.AddElement(auxIni2.GetData());
            auxIni2 = auxIni2.GetNext();
        }


        Node auxList = ini1;
        Node auxList2 = aux.first;

        while(auxList2 != null){
            auxList.SetData(auxList2.GetData());
            auxList = auxList.GetNext();
            auxList2 = auxList2.GetNext();
        }
    }

    private int getNextGap(int gap) {
        gap = gap * 10 / 13;
        if (gap < 1)
            return 1;
        return  gap;
    }

    public void CombSort() {
        Node nodeGap;
        int gap = SizeList();
        boolean trocou = true;


        while(gap != 1 || trocou) {
            trocou = false;
            gap = getNextGap(gap);

            Node atual = first;
            for (int i = 0; i < SizeList() - gap; i++) {
                nodeGap =  NodeByPos(i + gap);

                if (atual.GetData() > nodeGap.GetData()) {
                    int aux = atual.GetData();
                    atual.SetData(nodeGap.GetData());
                    nodeGap.SetData(aux);
                    trocou = true;
                }

                atual = atual.GetNext();
            }
        }
    }
}
