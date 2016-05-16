package com.expeval.tokenizer;

import java.util.Stack;

/**
 * Created by shahrukhimam on 13/05/16.
 */
 class Goto extends Action {

    private int gotoState;
    String terminal;

    Goto(int gotoState, String terminal) {
        this.gotoState = gotoState;
        this.terminal = terminal;
    }

    @Override
    Stack<State> perform(Stack<State> stack, String nextTokenId, String lexeme) {
        stack.push(new State(this.terminal, this.gotoState, lexeme));
        return stack;
    }
}
