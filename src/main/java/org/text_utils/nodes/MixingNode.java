package org.text_utils.nodes;

import java.util.Collections;
import java.util.LinkedList;

public class MixingNode extends Node {

    // Pair(0, 0) = " "
    private Pair separator = new Pair(0, 0);

    public MixingNode(Node parent, Pair separator) {
        super(parent);
        if(separator != null)
            this.separator = separator;
    }

    @Override
    public LinkedList<Pair> getIndexes() {
        Collections.shuffle(subnodes);

        LinkedList<Pair> intervals = new LinkedList<Pair>();
        for(Node child: subnodes){
            intervals.addAll(child.getIndexes());
            intervals.add(separator);
        }
        intervals.removeLast();
        return intervals;
    }

    @Override
    public int variantsNumber() {
        int result = 1;

        for(int i=2; i<=subnodes.size(); i++) {
            result *= i;
        }

        for(Node node: subnodes){
            result *= node.variantsNumber();
        }

        return result;
    }
}