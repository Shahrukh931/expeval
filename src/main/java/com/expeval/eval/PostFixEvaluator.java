package com.expeval.eval;

import com.expeval.tokens.Token;
import com.expeval.tokenizer.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by shahrukhimam on 04/05/16.
 */
 final class PostFixEvaluator extends Evaluator {

    private List<Token> tokens = null;
    private Context context = null;

    public PostFixEvaluator(List<Token> tokens, Context context) {
        this.tokens = tokens;
        this.context = context;
    }

    @Override
    public Object eval() {
        if (tokens == null) {
            return null;
        }
        Stack<Object> stack = new Stack<Object>();
        for (Token token : tokens) {
            switch (token.getType()) {
                case STRING:
                    stack.push(token.getLexeme());
                    break;
                case NUMBER:
                    stack.push(toNumber(token));
                    break;
                case BOOLEAN:
                    stack.push(toBoolean(token));
                    break;
                case BINARY_OPERATOR:
                    Object operand1 = stack.pop();
                    Object operand2 = stack.pop();
                    stack.push(context.getBinaryOperator(token.getLexeme()).onEvaluation(operand2, operand1));
                    break;
                case UNARY_OPERATOR:
                    Object rightOperand = stack.pop();
                    stack.push(context.getUnaryOperator(token.getLexeme()).onEvaluation(rightOperand));
                    break;
                case FUNCTION:
                    List<Object> arguments = new ArrayList<Object>();
                    while (true) {
                        Object args = stack.peek();
                        if (args instanceof Token && ((Token) args).getType() == Token.TYPE.LPARN) {
                            Collections.reverse(arguments);
                            stack.push(context.getFunction(token.getLexeme()).onEvaluation(arguments));
                            break;
                        } else {
                            arguments.add(stack.pop());
                        }
                    }
                    break;
                case VAR:
                    stack.push(context.getVar(token.getLexeme()).getValue());
                    break;
                case NULL:
                    stack.push(null);
                    break;
                case LPARN:
                    stack.push(token);
                    break;
                case RPARN:
                    if (!stack.isEmpty()) {
                        Object o = stack.pop();
                        if (!stack.isEmpty()) {
                            stack.pop();
                        }
                        stack.push(o);
                    }
                    break;
            }
        }
        return stack.pop();
    }

}
