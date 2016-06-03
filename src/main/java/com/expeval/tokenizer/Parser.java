package com.expeval.tokenizer;

import com.expeval.tokens.Operator;
import com.expeval.tokens.Token;

import java.util.List;

/**
 * Created by shahrukhimam on 04/05/16.
 */
public abstract class Parser {

    public abstract List<Token> parse();

    public static Parser newParser(String inputExpression, Context context) {
        Lexer lexer = new InfixLexer(inputExpression, context);
        Parser parser = new InfixParser(lexer);
        return parser;
    }


    boolean inputTokenHasLowerPrecedence(Token inputToken, Token stackTop) {
        if (inputToken instanceof Operator) {
            if (stackTop instanceof Operator) {
                Operator input = (Operator) inputToken;
                Operator stTop = (Operator) stackTop;
                if (input.getPrecedence() == stTop.getPrecedence()) {
                    if (input.isLeftToRightAssociative()) {
                        return true;
                    }
                } else if (input.getPrecedence() < stTop.getPrecedence()) {
                    return true;
                }
            } else if (stackTop.getType() == Token.TYPE.FUNCTION) {
                return true;
            }
        }
        return false;
    }

    boolean inputTokenHasLowerPrecedence(int inputPrecedence, Token stackTop) {
        if (stackTop instanceof Operator) {
            if (inputPrecedence == ((Operator) stackTop).getPrecedence() || inputPrecedence < ((Operator) stackTop).getPrecedence()) {
                return true;
            }
        } else if (stackTop.getType() == Token.TYPE.FUNCTION) {
            return true;
        }
        return false;
    }
}
