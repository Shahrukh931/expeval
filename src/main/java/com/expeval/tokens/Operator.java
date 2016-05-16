package com.expeval.tokens;

/**
 * Created by shahrukhimam on 02/05/16.
 */
public abstract class Operator extends Token {

    private int precedence = 0;
    private boolean leftToRightAssociative = true;

    public Operator(String identifier, int precedence, TYPE type, boolean leftToRightAssociative) {
        super(identifier, type);
        this.precedence = precedence;
        this.leftToRightAssociative = leftToRightAssociative;
    }

    public int getPrecedence() {
        return this.precedence;
    }

    public boolean isLeftToRightAssociative() {
        return this.leftToRightAssociative;
    }


}
