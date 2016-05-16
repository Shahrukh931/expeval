package com.expeval.tokens;

import java.util.List;

/**
 * Created by shahrukhimam on 02/05/16.
 */
public abstract class Function extends Token {

    public Function(String identifier) {
        super(identifier,TYPE.FUNCTION);
    }

    public abstract Object onEvaluation(List<Object> arguments);
}
