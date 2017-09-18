package se.mah.tsroax.f4startactivity;

import java.io.Serializable;

/**
 * Created by tsroax on 04/09/15.
 */
public class Question implements Serializable {
    private String question;
    private String[] alternatives;
    private int[] correctAlternatives;

    public Question(String question, String[] alternatives, int[] correctAlternatives) {
        this.question = question;
        this.alternatives = alternatives;
        this.correctAlternatives = correctAlternatives;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAlternatives() {
        return alternatives;
    }

    public int[] getCorrectAlternatives() {
        return correctAlternatives;
    }
}
