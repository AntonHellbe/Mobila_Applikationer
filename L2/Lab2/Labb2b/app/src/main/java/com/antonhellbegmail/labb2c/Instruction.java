package com.antonhellbegmail.labb2c;

/**
 * Created by Anton on 2017-09-07.
 */

public class Instruction {
    private String whatToDo;
    private String content;

    public Instruction(String toDo, String content){
        this.whatToDo = toDo;
        this.content = content;
    }

    public String getWhatToDo(){
        return this.whatToDo;
    }

    public String getContent(){
        return this.content;
    }

}
