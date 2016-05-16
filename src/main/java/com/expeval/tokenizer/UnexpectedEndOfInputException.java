package com.expeval.tokenizer;

/**
 * Created by shahrukhimam on 14/05/16.
 */
public class UnexpectedEndOfInputException extends RuntimeException {
    private String inputString;

    public UnexpectedEndOfInputException(String inputString, String expected) {
        super(expected + " at the of input string: '" + inputString+"'");
        this.inputString = inputString;
    }

    public String getInputString() {
        return inputString;
    }
}
