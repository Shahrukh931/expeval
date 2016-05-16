package com.expeval.tokenizer;

import java.util.Stack;

/**
 * Created by shahrukhimam on 13/05/16.
 */
 class Reduce extends Action {

    private int pops;
    private String terminal;

    public Reduce(int pops, String terminal) {
        this.pops = pops;
        this.terminal = terminal;
    }

    @Override
    Stack<State> perform(Stack<State> stack, String nextTokenId, String lexeme) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= pops; i++) {
            sb.append(stack.pop().getLexeme());
        }
        stack.push(new State(terminal, 0, sb.reverse().toString()));
        return stack;

    }
}
