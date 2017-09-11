package com.example.ag6505.staticrps;

import java.util.Random;

public class RPSPlayer {
    private Random random = new Random();

    public int nextChoice() {
        int choice = random.nextInt(3);
        if(choice == 0) {
            return RPSController.ROCK;
        } else if(choice==1) {
            return RPSController.PAPER;
        } else {
            return RPSController.SCISSORS;
        }
    }
}
