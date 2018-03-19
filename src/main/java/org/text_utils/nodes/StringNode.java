package org.text_utils.nodes;

import java.util.LinkedList;

public class StringNode extends Node {

    private LinkedList<Pair> list = new LinkedList<Pair>();

    public StringNode(Node parent, int index) {
        super(parent);
        list.add(new Pair(index, index+1));
    }

    @Override
    public LinkedList<Pair> getIndexes() {
        return list;
    }

    @Override
    public Node concat(int index) {
        int end_index = list.getLast().getEnd();
        if(end_index == index){
            list.getLast().setEnd(end_index+1);
        } else {
            list.add(new Pair(index, index+1));
        }

        return this;
    }

    @Override
    public int variantsNumber() {
        return 1;
    }
}