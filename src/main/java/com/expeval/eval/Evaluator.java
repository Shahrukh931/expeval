package com.expeval.eval;

import com.expeval.tokenizer.Context;
import com.expeval.tokens.Token;

import java.util.List;

/**
 * Created by shahrukhimam on 04/05/16.
 */
public abstract class Evaluator {

    public abstract Object eval();

    public static Evaluator newEvaluator(List<Token> tokens, Context context) {
        return new PostFixEvaluator(tokens, context);
    }

    public Number toNumber(Token token) {
        String lexeme = token.getLexeme();
        try {
            return Integer.parseInt(lexeme);
        }catch (NumberFormatException e) {
            try {
                return Long.parseLong(lexeme);
            } catch (NumberFormatException e1) {
                return Double.parseDouble(lexeme);
            }
        }
    }

    public Boolean toBoolean(Token token) {
        return Boolean.valueOf(token.getLexeme());
    }
}
