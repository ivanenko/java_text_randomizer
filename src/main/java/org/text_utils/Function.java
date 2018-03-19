package org.text_utils;

import java.util.List;

public abstract class Function {

    private String name = null;
    protected List<String> args = null;

    public Function(){
        //this.name = name;
    }

    public void setArgs(List<String> args){
        this.args = args;
    }

    public abstract String getText();
}
