package com.expeval.tokenizer;

/**
 * Created by shahrukhimam on 13/05/16.
 */
 class State {

    private int state;
    private String tokenId;
    private String lexeme;

    public State(String tokenId, int state, String lexeme) {
        this.tokenId = tokenId;
        this.state = state;
        this.lexeme = lexeme;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }
}
