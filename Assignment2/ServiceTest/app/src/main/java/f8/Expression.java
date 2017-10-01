package f8;

import java.io.Serializable;

/**
 * Created by Anton on 2017-09-29.
 */

public class Expression implements Serializable {
    private static final long serialVersionUID = 1L;
    private int nbr1;
    private int nbr2;
    private char operation;

    public Expression(int nbr1, char operation, int nbr2){
        this.nbr1 = nbr1;
        this.nbr2 = nbr2;
        this.operation = operation;
    }
    public int getNbr1(){
        return nbr1;
    }
    public int getNbr2(){
        return nbr2;
    }

    public char getOperation(){
        return operation;
    }
}
