package com.expeval.tokenizer;

import com.expeval.tokens.Token;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shahrukhimam on 05/05/16.
 */
 class InfixParser extends Parser {

    Lexer lexer = null;

    InfixParser(Lexer lexer) {
        if (lexer == null) {
            throw new IllegalArgumentException("Lexer is null");
        }
        this.lexer = lexer;
    }

    @Override
    public List<Token> parse() {
        ArrayDeque<Token> stack = new ArrayDeque<Token>();
        List<Token> result = new ArrayList<Token>();
        result.add(Token.LEFT_PARAN);
        while (lexer.hasNextToken()) {
            Token token = lexer.nextToken();
            if (token != null) {
                switch (token.getType()) {
                    case LPARN:
                        result.add(token);
                        stack.push(token);
                        break;
                    case RPARN:
                        while (!stack.isEmpty() && (stack.peek().getType() != Token.TYPE.LPARN)) {
                            result.add(stack.pop());
                        }
                        if (!stack.isEmpty()) {
                            stack.pop();
                        }
                        if (!stack.isEmpty() && stack.peek().getType() == Token.TYPE.FUNCTION) {
                            result.add(stack.pop());
                        }
                        result.add(token);
                        break;
                    case UNARY_OPERATOR:
                    case BINARY_OPERATOR:
                    case FUNCTION:
                        while (!stack.isEmpty() && inputTokenHasLowerPrecedence(token, stack.peek())) {
                            result.add(stack.pop());
                        }
                        stack.push(token);
                        break;
                    case COMMA:
                        while (!stack.isEmpty() && inputTokenHasLowerPrecedence(Integer.MIN_VALUE, stack.peek())) {
                            result.add(stack.pop());
                        }
                        continue;
                    default:
                        result.add(token);
                        break;
                }
            }
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        result.add(Token.RIGHT_PARAN);
        return result;
    }
}
