package com.expeval.tokenizer;

import java.util.Stack;

/**
 * Created by shahrukhimam on 13/05/16.
 */
 class Shift extends Action {

    private int shiftState = 0;

    public Shift(int shiftState) {
        this.shiftState = shiftState;
    }

    @Override
    Stack<State> perform(Stack<State> stack, String nextTokenId, String lexeme) {
        State state = new State(nextTokenId, shiftState, lexeme);
        stack.push(state);
        return stack;
    }
}
