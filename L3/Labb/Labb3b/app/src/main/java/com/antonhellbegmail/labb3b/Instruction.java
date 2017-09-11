package com.antonhellbegmail.labb3b;

/**
 * Created by Anton on 2017-09-11.
 */

public class Instruction {
    private String content;
    private String whatToDo;

    public Instruction(String content, String whatToDo){
        this.content = content;
        this.whatToDo = whatToDo;
    }

    public String getContent(){
        return this.content;
    }

    public String getWhatToDo(){
        return this.whatToDo;
    }
}
