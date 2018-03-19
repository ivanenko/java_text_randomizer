package org.text_utils.nodes;

import java.util.LinkedList;

public class SeriesNode extends Node {

    public SeriesNode(Node parent) {
        super(parent);
    }

    public Node concat(int index){
        return new StringNode(this, index);
    }

    public int variantsNumber(){
        int result = 1;
        for(Node node: this.subnodes){
            result *= node.variantsNumber();
        }

        return result;
    }
}