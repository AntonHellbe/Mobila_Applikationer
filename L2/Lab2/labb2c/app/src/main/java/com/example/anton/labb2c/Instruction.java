package com.example.anton.labb2c;

/**
 * Created by Anton on 2017-09-08.
 */

class Instruction {

    private String whatToDo, content;

    public Instruction(String whatTo, String cont){
        this.whatToDo = whatTo;
        this.content = cont;
    }

    public String getWhatToDo(){
        return this.whatToDo;
    }
    public String getContent(){
        return this.content;
    }

}
