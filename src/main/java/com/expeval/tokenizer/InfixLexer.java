package com.expeval.tokenizer;

import com.expeval.tokens.Token;

import java.util.Stack;

/**
 * Created by shahrukhimam on 09/05/16.
 */
final class InfixLexer extends Lexer {

    Stack<State> stack = null;
    Context context = null;

    public InfixLexer(String input, Context context) {
        super(input, context);
        stack = new Stack<State>();
        stack.push(new State(null, 0, null));
        this.context = context;
    }

    @Override
    public boolean hasNextToken() {
        if (hasNext()) {
            return true;
        } else {
            if (!stack.isEmpty()) {
                performProperAction(ParseTable.COLUMN_$, stack.peek().getLexeme());
            }
            return false;
        }
    }

    @Override
    Token nextToken() {
        String lexeme = next();
        Token token = null;
        if (!stack.isEmpty()) {
            State stackTopState = stack.peek();
            if (context.getParseTable().hasMapping(stackTopState.getState(), ParseTable.COLUMN_FID)) {
                if (context.isRegisteredFunction(lexeme) && nextChar(i - 1) == '(') {
                    performProperAction(ParseTable.COLUMN_FID, lexeme);
                    token = context.getFunction(lexeme);
                }
            }
            if (token == null && context.getParseTable().hasMapping(stackTopState.getState(), ParseTable.COLUMN_ID)) {
                if (context.isRegisteredVar(lexeme)) {
                    performProperAction(ParseTable.COLUMN_ID, lexeme);
                    token = context.getVar(lexeme);

                } else if ("true".equals(lexeme)) {
                    performProperAction(ParseTable.COLUMN_ID, lexeme);
                    token = Token.TRUE;

                } else if ("false".equals(lexeme)) {
                    performProperAction(ParseTable.COLUMN_ID, lexeme);
                    token = Token.FALSE;

                } else if ("null".equals(lexeme)) {
                    performProperAction(ParseTable.COLUMN_ID, lexeme);
                    token = Token.NULL;

                } else if (lexeme.length() > 0 && (Character.isDigit(lexeme.charAt(0)) || '.' == lexeme.charAt(0))) {
                    performProperAction(ParseTable.COLUMN_ID, lexeme);
                    token = new Token(lexeme, Token.TYPE.NUMBER);

                } else if (lexeme.startsWith("\"")) {
                    performProperAction(ParseTable.COLUMN_ID, lexeme);
                    token = new Token(lexeme.substring(1, lexeme.length() - 1), Token.TYPE.STRING);

                }
            }
            if (token == null && context.getParseTable().hasMapping(stackTopState.getState(), ParseTable.COLUMN_BOP)) {
                if (context.isRegisteredBinaryOperator(lexeme)) {
                    performProperAction(ParseTable.COLUMN_BOP, lexeme);
                    token = context.getBinaryOperator(lexeme);

                }
            }
            if (token == null && context.getParseTable().hasMapping(stackTopState.getState(), ParseTable.COLUMN_U)) {
                if (context.isRegisteredUnaryOperator(lexeme)) {
                    performProperAction(ParseTable.COLUMN_U, lexeme);
                    token = context.getUnaryOperator(lexeme);

                }
            }
            if (token == null && context.getParseTable().hasMapping(stackTopState.getState(), ParseTable.COLUMN_LPARN)) {
                if ("(".equals(lexeme)) {
                    performProperAction(ParseTable.COLUMN_LPARN, lexeme);
                    token = Token.LEFT_PARAN;

                }
            }
            if (token == null && context.getParseTable().hasMapping(stackTopState.getState(), ParseTable.COLUMN_RPARN)) {
                if (")".equals(lexeme)) {
                    performProperAction(ParseTable.COLUMN_RPARN, lexeme);
                    token = Token.RIGHT_PARAN;

                }
            }
            if (token == null && context.getParseTable().hasMapping(stackTopState.getState(), ParseTable.COLUMN_COMMA)) {
                if (",".equals(lexeme)) {
                    performProperAction(ParseTable.COLUMN_COMMA, lexeme);
                    token = Token.COMMA;

                }
            }

            if (token == null) {
                throw new UnexpectedIdentifierException(input, lexeme, (i - lexeme.length()), context.getParseTable().getExpectedIdentifiers(stack.peek().getState(), context));
            }

        }
        return token;

    }

    void performProperAction(String nextLexemeId, String lexeme) {
        Action action = context.getParseTable().get(stack.peek().getState(), nextLexemeId);
        if (action == null) {
            if (nextLexemeId.equals("$")) {
                throw new UnexpectedEndOfInputException(input, "Expecting : " + context.getParseTable().getExpectedIdentifiers(stack.peek().getState(), context));
            } else {
                throw new UnexpectedIdentifierException(input, lexeme, (i - lexeme.length()), context.getParseTable().getExpectedIdentifiers(stack.peek().getState(), context));
            }
        }
        if (action instanceof Shift) {
            action.perform(stack, nextLexemeId, lexeme);
            return;
        } else if (action instanceof Reduce) {
            action.perform(stack, nextLexemeId, lexeme);
            if (!stack.isEmpty()) {
                State state = stack.pop();
                if (!stack.isEmpty()) {
                    Action gotoAction = context.getParseTable().get(stack.peek().getState(), state.getTokenId());
                    if (gotoAction != null) {
                        gotoAction.perform(stack, nextLexemeId, state.getLexeme());
                    }
                }
                performProperAction(nextLexemeId, lexeme);
            }
        } else if (action instanceof Accept) {
            return;
        }
    }
}
