package org.text_utils.nodes;

import org.text_utils.Function;

import java.util.LinkedList;

public class FunctionNode extends Node {

    private Function func = null;

    public FunctionNode(Node parent, Function func) {
        super(parent);
        this.func = func;
    }

    @Override
    public LinkedList<Pair> getIndexes() {
        LinkedList<Pair> list = new LinkedList<Pair>();
        list.add(new Pair(func.getText()));

        return list;
    }
}