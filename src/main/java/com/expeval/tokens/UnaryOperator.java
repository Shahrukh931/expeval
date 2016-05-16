package com.expeval.tokens;

/**
 * Created by shahrukhimam on 08/05/16.
 */
public abstract class UnaryOperator extends Operator {

    public UnaryOperator(String identifier, int precedence) {
        super(identifier, precedence, TYPE.UNARY_OPERATOR, false);
    }

    public abstract Object onEvaluation(Object rightOperand);


    public static final UnaryOperator NEGATE = new UnaryOperator("!", 51) {
        @Override
        public Object onEvaluation(Object rightOperand) {
            return !((Boolean) rightOperand);
        }
    };

    public static final UnaryOperator PLUS = new UnaryOperator("+", 51) {
        @Override
        public Object onEvaluation(Object rightOperand) {
            return 0 + ((Number) rightOperand).doubleValue();
        }
    };

    public static final UnaryOperator MINUS = new UnaryOperator("-", 51) {
        @Override
        public Object onEvaluation(Object rightOperand) {
            return 0 - ((Number) rightOperand).doubleValue();
        }
    };

    public static final UnaryOperator BITWISE_COMPLEMENT = new UnaryOperator("~", 51) {
        @Override
        public Object onEvaluation(Object rightOperand) {
            return ~((Number) rightOperand).longValue();
        }
    };

}
