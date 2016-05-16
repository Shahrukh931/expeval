package com.expeval.tokenizer;

/**
 * Created by shahrukhimam on 14/05/16.
 */
public class UnexpectedIdentifierException extends RuntimeException {

    String identifier;
    int position;
    String inputString;

    public UnexpectedIdentifierException(String inputString, String identifier, int position, String expected) {
        super("Unexpected identifier: '" + identifier + "' found at position: " + position + " of input string: '" + inputString + "'\nExpecting: " + expected);
        this.identifier = identifier;
        this.inputString = inputString;
        this.position = position;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getPosition() {
        return position;
    }

    public String getInputString() {
        return inputString;
    }
}
