package com.example.anton.labb3c;

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

    public String getContent() {
        return content;
    }

    public String getWhatToDo() {
        return whatToDo;
    }
}
