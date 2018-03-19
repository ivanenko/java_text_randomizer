package org.text_utils.nodes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Node {

    protected Node parent = null;
    protected List<Node> subnodes = new ArrayList<Node>();

    public Node(Node parent) {
        this.parent = parent;
        if(this.parent != null){
            this.parent.addChild(this);
        }
    }

    public Node getParent(){
        return this.parent;
    }

    public void addChild(Node node) {
        subnodes.add(node);
    }

    public LinkedList<Pair> getIndexes() {
        LinkedList<Pair> intervals = new LinkedList<Pair>();
        for(Node child: subnodes){
            intervals.addAll(child.getIndexes());
        }
        return intervals;
    }

    public Node concat(int index) {
        return null;
    }

    public int variantsNumber() {
        int result = 0;
        for(Node node: subnodes){
            result += node.variantsNumber();
        }

        return result;
    }
}

