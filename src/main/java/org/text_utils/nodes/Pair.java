package org.text_utils.nodes;

public class Pair {
    private int start;
    private int end;
    private String text = null;

    public Pair(){}

    public Pair(int start, int end){
        this.start = start;
        this.end = end;
    }

    public Pair(String text){
        this.text = text;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getText(){
        return text;
    }
}
