package com.expeval.tokens;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by shahrukhimam on 02/05/16.
 */
public  class Var extends Token {

    private AtomicReference<Object> value = null;

    public Var(String identifier) {
        super(identifier,TYPE.VAR);
        this.value = new AtomicReference<Object>();
    }

    public void setValue(Object value) {
        this.value.set(value);
    }

    public Object getValue() {
        return this.value.get();
    }
}
