package com.expeval.tokenizer;

import java.util.Stack;

/**
 * Created by shahrukhimam on 13/05/16.
 */
abstract class Action {

    abstract Stack<State> perform(Stack<State> stack , String nextTokenId , String lexeme);
}
