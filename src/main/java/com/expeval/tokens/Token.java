package com.expeval.tokens;

/**
 * Created by shahrukhimam on 02/05/16.
 */
public class Token {

    private String lexeme;
    private TYPE type;

    public Token(String lexeme, TYPE type) {
        if (lexeme == null || type == null) {
            throw new IllegalArgumentException("Token identifier is  null");
        }
        this.lexeme = lexeme;
        this.type = type;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public TYPE getType() {
        return this.type;
    }

    public String toString(){
        return this.lexeme;
    }


    public static Token TRUE = new Token("true", TYPE.BOOLEAN);

    public static Token FALSE = new Token("false", TYPE.BOOLEAN);

    public static Token LEFT_PARAN = new Token("(", TYPE.LPARN);

    public static Token RIGHT_PARAN = new Token(")", TYPE.RPARN);

    public static Token NULL = new Token("null", TYPE.NULL);

    public static Token COMMA = new Token(",", TYPE.COMMA);


    public enum TYPE {
        BINARY_OPERATOR,
        UNARY_OPERATOR,
        FUNCTION,
        VAR,//1
        LPARN,
        RPARN,//2
        COMMA,
        NULL,//6
        STRING,//3
        NUMBER,//4
        BOOLEAN;
    }

}
