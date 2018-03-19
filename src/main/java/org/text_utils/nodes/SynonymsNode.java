package org.text_utils.nodes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SynonymsNode extends Node {

    private List<Integer> used_indexes = new ArrayList<Integer>();

    public SynonymsNode(Node parent) {
        super(parent);
    }

    @Override
    public LinkedList<Pair> getIndexes() {
        if(used_indexes.size() == 0){
            for(int i=0; i<subnodes.size(); i++){
                used_indexes.add(i);
            }
        }

        int ind = new Random().nextInt(used_indexes.size());
        int random_index = used_indexes.get(ind);
        used_indexes.remove(ind);

        return subnodes.get(random_index).getIndexes();
    }
}